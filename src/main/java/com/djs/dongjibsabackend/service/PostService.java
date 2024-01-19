package com.djs.dongjibsabackend.service;

import com.djs.dongjibsabackend.domain.dto.image.ImageDto;
import com.djs.dongjibsabackend.domain.dto.ingredient.IngredientDto;
import com.djs.dongjibsabackend.domain.dto.post.PostDto;
import com.djs.dongjibsabackend.domain.dto.post.PostResponse;
import com.djs.dongjibsabackend.domain.dto.post.RegisterPostRequest;
import com.djs.dongjibsabackend.domain.dto.post_ingredient.PostIngredientRequest;
import com.djs.dongjibsabackend.domain.dto.recipe_calorie.RecipeCalorieDto;
import com.djs.dongjibsabackend.domain.entity.ImageEntity;
import com.djs.dongjibsabackend.domain.entity.IngredientEntity;
import com.djs.dongjibsabackend.domain.entity.PostEntity;
import com.djs.dongjibsabackend.domain.entity.PostIngredientEntity;
import com.djs.dongjibsabackend.domain.entity.RecipeCalorieEntity;
import com.djs.dongjibsabackend.domain.entity.MemberEntity;
import com.djs.dongjibsabackend.exception.AppException;
import com.djs.dongjibsabackend.exception.ErrorCode;
import com.djs.dongjibsabackend.repository.IngredientRepository;
import com.djs.dongjibsabackend.repository.PostIngredientRepository;
import com.djs.dongjibsabackend.repository.PostRepository;
import com.djs.dongjibsabackend.repository.RecipeCalorieRepository;
import com.djs.dongjibsabackend.repository.MemberRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.swing.text.html.Option;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    private final PostIngredientRepository postIngredientRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeCalorieRepository recipeCalorieRepository;
    private final PostImageService postImageService;
    private final double defaultCalorie = 0.0;

    @Transactional
    public PostDto register (RegisterPostRequest req) throws MissingServletRequestPartException {

        /* memberId */
        MemberEntity writer = memberRepository.findById(req.getMemberId())
            .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND, String.format("%d는 존재하지 않는 유저입니다.", req.getMemberId())));
        log.info("writer: {}", writer.getId()); // PASS

        /* 게시글 엔티티 생성 */
        /**
         * title, content, expectingPrice, pricePerOne, member, recipeCalorie, peopleCount
         */
        PostEntity post = PostEntity.builder()
                                         .title(req.getTitle())
                                         .content(req.getContent())
                                         .expectingPrice(req.getExpectingPrice())
                                         .pricePerOne(req.getPricePerOne())
                                         .member(writer)
                                         .peopleCount(req.getPeopleCount())
                                         .build();
        log.info("savedPost: {}", post.getTitle()); // PASS

        /* 새로 입력된 레시피와 칼로리
        레시피 db에 존재하면 칼로리 입력, 없으면 0으로 계산하는 로직 작성 */
        String recipeName = req.getRecipeName();
        log.info("recipeName: {}", recipeName); // PASS

        RecipeCalorieDto recipeCalorieDto = RecipeCalorieDto.builder()
                                                            .recipeName(recipeName)
                                                            .calorie(defaultCalorie)
                                                            .build();

        RecipeCalorieEntity recipeCalorieEntity = recipeCalorieRepository.findByRecipeName(recipeName)
                                                                         .orElseGet(() -> recipeCalorieRepository.save(RecipeCalorieDto.toEntity(recipeCalorieDto)));

        post.updateRecipeCalorie(recipeCalorieEntity);

        /* 재료 리스트 엔티티 생성 (List<PostIngredientEntity>)*/
        List<PostIngredientEntity> ingredients = new ArrayList<>();
        List<PostIngredientRequest> dtos = req.getIngredients();

        /* 요청의 재료가 DB에 없으면 dto를 엔티티로 변환하여 사용한다. */
        for (PostIngredientRequest ingredient: dtos) {
            IngredientDto ingredientDto = IngredientDto.builder()
                                                       .name(ingredient.getIngredientName())
                                                       .build();

            /*IngredientEntity*/
            IngredientEntity ingredientEntity = ingredientRepository.findByIngredientName(ingredient.getIngredientName())
                                                                    .orElseGet(() ->ingredientRepository.save(IngredientDto.toEntity(ingredientDto)));

            /* 게시글, 재료, 수량데이터 엔티티 생성 (PostIngredientEntity)*/
            IngredientEntity savedIngredient = ingredientRepository.save(ingredientEntity);
            PostIngredientEntity postIngredientEntity =
                PostIngredientEntity.addIngredientToPost (post,
                                                          savedIngredient,
                                                          ingredient.getTotalQty(),
                                                          ingredient.getRequiredQty(),
                                                          ingredient.getSharingAvailableQty());
            ingredients.add(postIngredientEntity);
        }

        /* 게시글에 재료 리스트 저장 */
        post.updatePostIngredients(ingredients);
        PostEntity savedPost = postRepository.save(post); // DB에 저장

        /* 요청에 사진 파일이 있는 경우 업로드 (imgUrl) */
        if (!CollectionUtils.isEmpty(req.getImages())) {
            List<ImageDto> imgUrls = postImageService.uploadAndSaveToDB(req.getImages(), savedPost.getId());
            // List<ImageDto> -> List<ImageEntity>
            List<ImageEntity> imgEntityList = ImageDto.toEntity(imgUrls, savedPost);
            savedPost.updatePostImageUrl(imgEntityList);
            PostEntity savedPostWithImages = postRepository.save(savedPost);
            PostDto savedPostDto = PostDto.toDto(savedPostWithImages);
            return savedPostDto;
        } else {
            // postRepository.save(savedPost);
            PostDto savedPostDto = PostDto.toDto(savedPost);
            return savedPostDto;
        }

//        PostDto savedPostDto = PostDto.toDto(savedPostWithImages);
//        return savedPostDto;
    }

    /* 게시글 전체 조회*/
    public List<PostDto> getRecipeList() {

        List<PostEntity> postEntityList = postRepository.findAll();
        List<PostDto> postDtoList = PostDto.toDtoList(postEntityList);
        return postDtoList;
    }

    /* 게시글 상세조회 */
    public PostDto getPostDetail(Long postId) {
        PostEntity post = postRepository.findById(postId)
                                        .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND, "게시물이 존재하지 않습니다."));
        PostDto postDto = PostDto.toDto(post);
        return postDto;
    }
}
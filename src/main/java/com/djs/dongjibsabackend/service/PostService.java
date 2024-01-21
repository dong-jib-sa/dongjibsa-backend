package com.djs.dongjibsabackend.service;

import com.djs.dongjibsabackend.domain.dto.image.ImageDto;
import com.djs.dongjibsabackend.domain.dto.ingredient.IngredientDto;
import com.djs.dongjibsabackend.domain.dto.post.EditPostRequest;
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
import com.djs.dongjibsabackend.repository.ImageRepository;
import com.djs.dongjibsabackend.repository.IngredientRepository;
import com.djs.dongjibsabackend.repository.PostIngredientRepository;
import com.djs.dongjibsabackend.repository.PostRepository;
import com.djs.dongjibsabackend.repository.RecipeCalorieRepository;
import com.djs.dongjibsabackend.repository.MemberRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final ImageRepository imageRepository;

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

        /* DB에 저장 */
        PostEntity savedPost = postRepository.save(post);

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

    /* 게시글 수정 */
    public PostDto editPost(Long postId, EditPostRequest editPostRequest) throws MissingServletRequestPartException {

        /* 작성자 검증 */
        MemberEntity writer = memberRepository.findById(editPostRequest.getMemberId())
                                              .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND, String.format("%s는 존재하지 않는 " +
                                                                                                                              "회원입니다.",
                                                                                                                          editPostRequest.getMemberId())));

        /* 게시글 작성자 일치 여부 검증*/
        PostEntity originalPost = postRepository.findById(postId)
                                                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND,
                                                                                    String.format("%s는 존재하지 않는 게시글입니다.", postId)));

        /* 요청 유저가 게시글 작성자인지 검증 */
        if (!originalPost.getMember().getId().equals(writer.getId())) {
            throw new AppException(ErrorCode.USER_UNAUTHORIZED, String.format("수정 권한이 없습니다."));
        }

        /* 요청 내용으로 게시글 내용 변경 */
        originalPost.updatePost(editPostRequest);

        /* 1. 레시피명 변경 */
        String updatedRecipeName = editPostRequest.getRecipeName();
        log.info("updatedRecipeName: {}", updatedRecipeName);
        RecipeCalorieDto updatedRecipeCalorieDto = RecipeCalorieDto.builder()
                                                            .recipeName(updatedRecipeName)
                                                            .calorie(defaultCalorie) // 0.0
                                                            .build();
        RecipeCalorieEntity recipeCalorieEntity = recipeCalorieRepository.findByRecipeName(updatedRecipeName)
                                                                         .orElseGet(() -> recipeCalorieRepository.save(RecipeCalorieDto.toEntity(updatedRecipeCalorieDto)));

        originalPost.updateRecipeCalorie(recipeCalorieEntity);

        /* 2. 재료 변경 */

        // List<PostIngredientEntity> updatedIngredients = new ArrayList<>();
        List<PostIngredientEntity> ingredients = originalPost.getRecipeIngredients();
        ingredients.clear(); // 객체를 그대로 이용하기 위해 그대로 삭제..
        List<PostIngredientRequest> updatedIngredientDtoList = editPostRequest.getIngredients();
        for (PostIngredientRequest ingredient: updatedIngredientDtoList) {
            IngredientDto ingredientDto = IngredientDto.builder()
                                                       .name(ingredient.getIngredientName())
                                                       .build();

            /*IngredientEntity*/
            IngredientEntity ingredientEntity = ingredientRepository.findByIngredientName(ingredient.getIngredientName())
                                                                    .orElseGet(() ->ingredientRepository.save(IngredientDto.toEntity(ingredientDto)));

            /* 게시글, 재료, 수량데이터 엔티티 생성 (PostIngredientEntity)*/
            IngredientEntity savedIngredient = ingredientRepository.save(ingredientEntity);
            PostIngredientEntity postIngredientEntity =
                PostIngredientEntity.addIngredientToPost (originalPost,
                                                          savedIngredient,
                                                          ingredient.getTotalQty(),
                                                          ingredient.getRequiredQty(),
                                                          ingredient.getSharingAvailableQty());
            ingredients.add(postIngredientEntity);
        }
        originalPost.updatePostIngredients(ingredients);

        /* 3. 이미지 변경 */

        if (CollectionUtils.isEmpty(editPostRequest.getImages())) { // 변경한 image 없음
            // keep going
            /* 변경된 포스트를 DB에 저장 */
            PostEntity editedPost = postRepository.save(originalPost);
            return PostDto.toDto(editedPost);
        } else {
            List<ImageEntity> originalImages =
                imageRepository.findAllByPostId(postId).orElseThrow(() -> new AppException(ErrorCode.IMAGE_NOT_FOUND,
                                                                                           String.format("%d번 게시글의 이미지가 존재하지 않습니다.", postId)));
            // 원래 Post에서 저장한 이미지를 삭제한다.
            for(ImageEntity image: originalImages) {
                postImageService.deleteFile(image.getUrl());
            }

            // 요청 내 이미지를 새롭게 저장하는 List<ImageEntity>를 만든다.
            List<ImageDto> newImageDtos = postImageService.uploadAndSaveToDB(editPostRequest.getImages(), postId);
            List<ImageEntity> newImages = ImageDto.toEntity(newImageDtos, originalPost);

            // DB 저장
            originalPost.updatePostImageUrl(newImages);
            PostEntity editedPostWithNewImages = postRepository.save(originalPost);
            return PostDto.toDto(editedPostWithNewImages);
        }
    }

    /* 게시글 삭제 */
    @Transactional
    public String deletePost(Long postId, Long memberId) {

        log.debug("서비스 진입, deletePost 메서드 작동 시작");

        /* 유저 검증 */
        MemberEntity member = memberRepository.findById(memberId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND,
                                                                                                     String.format("%d는 존재하지 않는 유저입니다.", memberId)));
        log.debug("사용자 검증 완료: {}", member.getId());

        /* 게시글 검증 */
        PostEntity postEntity = postRepository.findById(postId).orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND,
                                                                                                   String.format("%d는 존재하지 않는 게시물입니다.", postId)));
        log.debug("게시글 검증 완료: {}", postEntity.getId());

        /* 요청 유저가 게시글 작성자인지 검증 */
        if (!postEntity.getMember().getId().equals(member.getId())) {
            throw new AppException(ErrorCode.USER_UNAUTHORIZED, String.format("본인의 게시글만 삭제할 수 있습니다. "));
        }
        log.debug("권한 검증 완료");

        postRepository.deleteById(postId);
        log.debug("게시글 삭제 완료");

        return String.format("%d번 게시글이 삭제되었습니다.", postId);
    }
}
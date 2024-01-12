package com.djs.dongjibsabackend.service;

import com.djs.dongjibsabackend.domain.dto.post.PostDto;
import com.djs.dongjibsabackend.domain.dto.post.RegisterPostRequest;
import com.djs.dongjibsabackend.domain.dto.post_ingredient.PostIngredientRequest;
import com.djs.dongjibsabackend.domain.entity.IngredientEntity;
import com.djs.dongjibsabackend.domain.entity.LocationEntity;
import com.djs.dongjibsabackend.domain.entity.PostEntity;
import com.djs.dongjibsabackend.domain.entity.PostIngredientEntity;
import com.djs.dongjibsabackend.domain.entity.RecipeCalorieEntity;
import com.djs.dongjibsabackend.domain.entity.MemberEntity;
import com.djs.dongjibsabackend.exception.AppException;
import com.djs.dongjibsabackend.exception.ErrorCode;
import com.djs.dongjibsabackend.repository.IngredientRepository;
import com.djs.dongjibsabackend.repository.LocationRepository;
import com.djs.dongjibsabackend.repository.PostIngredientRepository;
import com.djs.dongjibsabackend.repository.PostRepository;
import com.djs.dongjibsabackend.repository.RecipeCalorieRepository;
import com.djs.dongjibsabackend.repository.MemberRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final LocationRepository locationRepository;
    private final PostIngredientRepository postIngredientRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeCalorieRepository recipeCalorieRepository;
    private final PostImageService postImageService;

    public PostDto register (RegisterPostRequest req) throws MissingServletRequestPartException {

        LocationEntity validateLocation = locationRepository.findLocationByDong(req.getDong());

        MemberEntity validateUser = memberRepository.findByNickName("지예로운사람");

        List<PostIngredientEntity> ingredients = new ArrayList<>();

        RecipeCalorieEntity recipeCalorieEntity = recipeCalorieRepository.findByRecipeName(req.getRecipeName());

        PostEntity savedPost = PostEntity.builder()
                                           .title(req.getTitle())
                                           .content(req.getContent())
                                           .expectingPrice(req.getExpectingPrice())
                                           .pricePerOne(req.getPricePerOne())
                                           .member(validateUser)
                                           .recipeCalorie(recipeCalorieEntity)
                                           .peopleCount(req.getPeopleCount())
                                           .location(validateLocation)
                                           .build();

        List<PostIngredientRequest> dtos = req.getIngredients();

        for (PostIngredientRequest ingredient: dtos) {
            IngredientEntity ingredientEntity = ingredientRepository.findByIngredientName(ingredient.getIngredientName())
                .orElseThrow(() -> new AppException(ErrorCode.INGREDIENT_NOT_AVAILABLE, "해당 재료는 입력할 수 없습니다."));

            if (ingredientEntity != null) {
                PostIngredientEntity postIngredientEntity =
                    PostIngredientEntity.addIngredientToPost (savedPost, // 재료 리스트가 비어 있는 레시피 객체
                                                              ingredientEntity,
                                                              ingredient.getTotalQty(),
                                                              ingredient.getRequiredQty(),
                                                              ingredient.getSharingAvailableQty());
                ingredients.add(postIngredientEntity);
            }
        }

        // 재료 목록 저장
        savedPost.updatePostIngredients(ingredients);

        if (req.getImage() != null) {

            String imgUrl = postImageService.uploadAndSaveToDB(req.getImage(), savedPost.getId());

            savedPost.updatePostImageUrl(imgUrl);

            postRepository.save(savedPost);

            // entity -> dto
            PostDto savedPostDto = PostDto.toDto(savedPost);

            return savedPostDto;

        } else {

            postRepository.save(savedPost);

            PostDto savedPostDto = PostDto.toDto(savedPost);

            return savedPostDto;
        }
    }

//    public PostDto register(WritePostRequest writePostRequest) {
//
//        // 동 이름을 기준으로 Location 객체 생성
//        LocationEntity validateLocation = locationRepository.findLocationByDong(writePostRequest.getDong());
//
//        // 사용자가 입력한 이름으로 User 객체 생성
//        // UserEntity validateUser = userRepository.findByUserName(writePostRequest.getUserName());
//        UserEntity validateUser = userRepository.findByUserName("박보검");
//
//        // RecipyIngredientEntity 리스트 생성
//        List<PostIngredientEntity> ingredients = new ArrayList<>();
//
//        // 입력 받은 레시피 이름으로 recpiecalorie 엔티티 생성
//        String recipeName = writePostRequest.getRecipeName();
//        RecipeCalorieEntity recipeCalorieEntity = recipeCalorieRepository.findByRecipeName(recipeName);
//
//        // 레시피 엔터티 생성
//        PostEntity savedPost = PostEntity.builder()
//                                           .title(writePostRequest.getTitle())
//                                           .content(writePostRequest.getContent())
//                                           .expectingPrice(writePostRequest.getExpectingPrice())
//                                           .pricePerOne(writePostRequest.getPricePerOne())
//                                           .user(validateUser)
//                                           .recipeCalorie(recipeCalorieEntity)
//                                           .peopleCount(writePostRequest.getPeopleCount())
//                                           .location(validateLocation)
//                                           .imgUrl(writePostRequest.getImgUrl())
//                                           .build();
//
//        // request 내 입력된 재료
//        List<PostIngredientDto> dtoList = writePostRequest.getPostIngredientDtos();
//
//        // 재료 dto 리스트를 순회하며 레시피 재료 리스트를 채운다.
//        for (PostIngredientDto dto: dtoList) {
//            // 재료명으로 재료 코드 조회
//            IngredientEntity ingredientEntity = ingredientRepository.findByIngredientName(dto.getIngredientName())
//                .orElseThrow(() -> new AppException(ErrorCode.INGREDIENT_NOT_AVAILABLE, "해당 재료는 입력할 수 없습니다."));
//
//            if (ingredientEntity != null) {
//                PostIngredientEntity postIngredientEntity =
//                    PostIngredientEntity.addIngredientToPost (savedPost, // 재료 리스트가 비어 있는 레시피 객체
//                                                               ingredientEntity,
//                                                               dto.getTotalQty(),
//                                                               dto.getRequiredQty(),
//                                                               dto.getSharingAvailableQty());
//                ingredients.add(postIngredientEntity);
//            }
//        }
//
//        savedPost.updatePostIngredients(ingredients);
//
//        postRepository.save(savedPost); // db에 저장
//
//        PostDto savedPostDto = PostDto.toDto(savedPost);
//
//        return savedPostDto;
//    }

    // 조회
    // List<RecipeIngredientEntity> recipeIngredients = recipeIngredientRepository.findAllIngredientsByRecipeId() -> 조회 로직에 사용할 것
    public List<PostDto> searchByLocation(String dongName) {
        LocationEntity location = locationRepository.findLocationByDong(dongName);

        Long locationId = location.getId();

        List<PostEntity> postEntityList = postRepository.findAllByLocationId(locationId);

        List<PostDto> postDtoList = postEntityList.stream().map(PostDto::toDto).collect(Collectors.toList());

        return postDtoList;
    }

    // 게시글 상세조회
    public PostDto getPostDetail(Long postId) {
        PostEntity post = postRepository.findById(postId)
                                        .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND, "게시물이 존재하지 않습니다."));
        PostDto postDto = PostDto.toDto(post);
        return postDto;
    }
}
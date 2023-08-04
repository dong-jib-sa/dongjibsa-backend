package com.djs.dongjibsabackend.service;

import com.djs.dongjibsabackend.domain.dto.myPage.MyIndicatorResponse;
import com.djs.dongjibsabackend.domain.dto.post.PostDto;
import com.djs.dongjibsabackend.domain.dto.post_ingredient.PostIngredientDto;
import com.djs.dongjibsabackend.domain.dto.user.UserDto;
import com.djs.dongjibsabackend.domain.entity.PostIngredientEntity;
import com.djs.dongjibsabackend.domain.entity.UserEntity;
import com.djs.dongjibsabackend.exception.AppException;
import com.djs.dongjibsabackend.exception.ErrorCode;
import com.djs.dongjibsabackend.repository.PostIngredientRepository;
import com.djs.dongjibsabackend.repository.PostRepository;
import com.djs.dongjibsabackend.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final UserRepository userRepository;
    private final PostIngredientRepository postIngredientRepository;
    private final PostRepository postRepository;

    public UserEntity findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
            () -> new AppException(ErrorCode.USER_NOT_FOUND, "존재하지 않는 사용자입니다."));
    }

    public MyIndicatorResponse calculate(Long userId) {
        UserEntity user = findUserById(userId);
        UserDto userDto = UserDto.toDto(user);

        // 유저가 작성한 PostList
        List<PostDto> postDtoList = userDto.getPostDtoList(); // postList -> post -> ingredient List

        // PostIdList
        List<Long> postIdList = new ArrayList<>();

        // PostDto의 칼로리 리스트, postId 리스트 생성
        List<Double> calories = new ArrayList<>();
        for (PostDto postDto : postDtoList) {
            double calorie = postDto.getCalorie();
            calories.add(calorie);

            postIdList.add(postDto.getId());
        }
        // 칼로리 평균 계산
        double calorieAvg = calories.stream().mapToDouble(i -> i).average()
                                    .orElse(0);

        // 총 나눔 건수
        // 유저가 작성한 PostDto들의 postId

        // 유저가 작성한 Post별 재료 목록 리스트..
        List<List<PostIngredientEntity>> postIngredientEntityList = new ArrayList<>(); // [[1, 2, 3], [4, 5, 6]]
        List<PostIngredientDto> postIngredientsList = new ArrayList<>();

        // 게시글의 모든 재료의 총 나눔 건수의 총합
//        for (Long postId: postIdList) {
//            List<PostIngredientEntity> postIngredient = postIngredientRepository.findAllIngredientsByPostId(postId);
//            Integer sumOfSharingAvailableQty = 0;
//            postIngredient.forEach(postIngredientEntity -> sumOfSharingAvailableQty += postIngredientEntity.getSharingAvailableQty());
//
//
//        }
//        List<PostIngredientDto> postIngredients = postDto.getRecipeIngredients();
//        postIngredientsList.add(postIngredients);




    }
}

package com.djs.dongjibsabackend.service;

import static java.util.stream.Collectors.reducing;

import com.djs.dongjibsabackend.domain.dto.myPage.MyIndicatorResponse;
import com.djs.dongjibsabackend.domain.dto.post.PostDto;
import com.djs.dongjibsabackend.domain.dto.post_ingredient.PostIngredientDto;
import com.djs.dongjibsabackend.domain.dto.user.UserDto;
import com.djs.dongjibsabackend.domain.entity.PostEntity;
import com.djs.dongjibsabackend.domain.entity.PostIngredientEntity;
import com.djs.dongjibsabackend.domain.entity.UserEntity;
import com.djs.dongjibsabackend.exception.AppException;
import com.djs.dongjibsabackend.exception.ErrorCode;
import com.djs.dongjibsabackend.repository.PostIngredientRepository;
import com.djs.dongjibsabackend.repository.PostRepository;
import com.djs.dongjibsabackend.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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

    // public List<PostDto> getMyPostList(Long userId) {
    public List<PostDto> getMyPostList() {
        // 유저가 작성한 Post 리스트 객체 생성
        List<PostEntity> postEntityList = postRepository.findAllByUserId(3L);

        // PostDto 리스트로 변환
        List<PostDto> postDtoList = postEntityList.stream().map(PostDto::toDto).collect(Collectors.toList());

        return postDtoList;
    }

    // public MyIndicatorResponse calculate(Long userId) {
    public MyIndicatorResponse calculate() {
        UserEntity user = findUserById(3L); // 유저
        UserDto userDto = UserDto.toDto(user); // 유저 -> dto

        // 유저가 작성한 PostList
        List<PostDto> postDtoList = userDto.getPostDtoList(); // [{1, [[재료, 나눔수량], [재료, 나눔수량], [재료, 나눔수량], 제목, 내용, ...},
                                                              // {2, [[재료, 나눔수량], [재료, 나눔수량], [재료, 나눔수량], 제목, 내용, ...},
                                                              // {3, [[재료, 나눔수량], [재료, 나눔수량], [재료, 나눔수량], 제목, 내용, ...},
                                                              // {4, [[재료, 나눔수량], [재료, 나눔수량], [재료, 나눔수량], 제목, 내용, ...}]
        // PostIdList : 유저가 작성한 모든 게시글(postId)
        List<Long> postIdList = new ArrayList<>();

        // PostDto의 칼로리 리스트, postId 리스트 생성
        List<Double> calories = new ArrayList<>();

        for (PostDto postDto : postDtoList) {
            double calorie = postDto.getRecipeCalorie().getCalorie();
            calories.add(calorie); // {칼로리1, 칼로리2, 칼로리3, ...}
            postIdList.add(postDto.getId()); // {1, 2, 3, 4, ...}
        }

        // ------------------------ 칼로리 평균 계산 ------------------------
        double calorieAvg = calories.stream().mapToDouble(i -> i).average()
                                    .orElse(0);

        // 유저가 작성한 Post별 재료 목록 리스트..
        List<List<PostIngredientEntity>> postIngredientEntityList = new ArrayList<>(); // { [[포스트1, 재료1, 나눔수량],
                                                                                       //   [포스트1, 재료2, 나눔수량],
                                                                                       //   [포스트1, 재료3, 나눔수량]],
                                                                                       //
                                                                                       //   [[포스트2, 재료1, 나눔수량],
                                                                                       //   [포스트2, 재료2, 나눔수량],
                                                                                       //   [포스트2, 재료3, 나눔수량]], ... }
        List<PostIngredientDto> postIngredientsList = new ArrayList<>();
        List<Double> sumOfSharingAvailableQtyPerPost = new ArrayList<>();

        // 게시글의 모든 재료의 총 나눔 건수의 총합
        for (Long postId: postIdList) {
            List<PostIngredientEntity> postIngredient = postIngredientRepository.findAllIngredientsByPostId(postId);
            double sumOfSharingAvailableQty = postIngredient.stream()
                                                            .map(PostIngredientEntity::getSharingAvailableQty)
                                                                .reduce((double) 0, (a, b) -> a+b);
//                                                            .collect(reducing(Double::sum)).get(); // 레시피 별 재료 나눔 건수의 총합
            sumOfSharingAvailableQtyPerPost.add(sumOfSharingAvailableQty);
        }

        // ------------------------ 총 나눔 건수 ------------------------
        double sumOfSharingAvailableQtyPerUser = sumOfSharingAvailableQtyPerPost.stream().reduce((double) 0, (a, b) -> a+b);



        return MyIndicatorResponse.builder()
                                  .calorieAvg(calorieAvg)
                                  .sumOfSharingAvailableQty(sumOfSharingAvailableQtyPerUser)
                                  .build();
    }
}

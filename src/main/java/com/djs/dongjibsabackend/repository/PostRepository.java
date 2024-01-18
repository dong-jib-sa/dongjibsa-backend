package com.djs.dongjibsabackend.repository;

import com.djs.dongjibsabackend.domain.entity.PostEntity;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
    // List<PostEntity> findAllByLocationId(Long locationId);
    List<PostEntity> findAll();
    List<PostEntity> findAllByMemberId(Long memberId);
}

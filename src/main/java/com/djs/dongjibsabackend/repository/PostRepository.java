package com.djs.dongjibsabackend.repository;

import com.djs.dongjibsabackend.domain.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity, Long> {

}

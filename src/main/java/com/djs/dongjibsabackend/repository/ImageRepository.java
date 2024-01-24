package com.djs.dongjibsabackend.repository;

import com.djs.dongjibsabackend.domain.entity.ImageEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ImageEntity, Long> {

    Optional<List<ImageEntity>> findAllByPostId(Long postId);

    void deleteAllByPostId(Long postId);
}

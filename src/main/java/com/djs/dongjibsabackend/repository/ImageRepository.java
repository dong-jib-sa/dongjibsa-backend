package com.djs.dongjibsabackend.repository;

import com.djs.dongjibsabackend.domain.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ImageEntity, Long> {

}

package com.djs.dongjibsabackend.repository;

import com.djs.dongjibsabackend.domain.entity.LocationEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<LocationEntity, Long> {

    Optional<LocationEntity> findLocationByDong(String dong);
}

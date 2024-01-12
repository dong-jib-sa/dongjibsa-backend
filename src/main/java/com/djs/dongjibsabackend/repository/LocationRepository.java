package com.djs.dongjibsabackend.repository;

import com.djs.dongjibsabackend.domain.entity.LocationEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LocationRepository extends JpaRepository<LocationEntity, Long> {

//    @Query("select l from LocationEntity l where l.dong = :name")
//    LocationEntity findLocationByDong(@Param("name") String dong);
}

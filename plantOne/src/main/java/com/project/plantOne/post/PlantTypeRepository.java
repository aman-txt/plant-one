package com.project.plantOne.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

import static com.project.plantOne.constants.Constants.CHECK_PLANT_TYPE_TABLE;

@Repository
public interface PlantTypeRepository extends JpaRepository<PlantType,Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO plant_type (plant_type_id, plant_type) VALUES (:plant_type_id, :plantType)", nativeQuery = true)
    int insertPlantType(@Param("plant_type_id") String uuid, @Param("plantType") String plantType);

    @Query(value =CHECK_PLANT_TYPE_TABLE)
    int findPlantTypeTableSize();



}

package com.project.plantOne.post;


import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "PlantType")
public class PlantType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "plant_type_id", updatable = false, nullable = false,  columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID plantTypeId;
    @Column(nullable = false)
    private String plantType;

    public PlantType() {
    }

    public PlantType(UUID plantTypeId, String plantType) {
        this.plantTypeId = plantTypeId;
        this.plantType = plantType;
    }

    public UUID getPlantTypeId() {
        return plantTypeId;
    }

    public void setPlantTypeId(UUID plantTypeId) {
        this.plantTypeId = plantTypeId;
    }

    public String getPlantType() {
        return plantType;
    }

    public void setPlantType(String plantType) {
        this.plantType = plantType;
    }
}

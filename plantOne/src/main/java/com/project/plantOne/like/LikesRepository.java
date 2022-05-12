package com.project.plantOne.like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import static com.project.plantOne.constants.Constants.*;


@Repository
public interface
LikesRepository extends JpaRepository<Likes, UUID> {

    @Query(value = CHECK_USER_ALREADY_LIKED_POST)
    int isUserAlreadyLiked(@Param("postUUID") UUID postUUID, @Param("userId") UUID userUUID);

    @Query(value = GET_LIKESID_OF_USER_AND_POST)
    UUID getLikeIdByUserAndPost(@Param("postUUID") UUID blogUUID, @Param("userId") UUID userUUID);
}


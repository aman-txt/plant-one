package com.project.plantOne.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static com.project.plantOne.constants.Constants.*;


@Repository
public interface PostRepository extends JpaRepository<Post,UUID> {

    Post findBypostUUID(UUID postUUID);

    @Modifying
    @Transactional
    @Query(value = UPDATE_BUYER_FOR_POST)
    int updatePostBuyer(UUID buyerUUID, UUID postUUID);


    @Query(value = FIND_ACTIVE_POSTS)
    List<Post> getActivePosts(@Param("userID") UUID userUUID);

}





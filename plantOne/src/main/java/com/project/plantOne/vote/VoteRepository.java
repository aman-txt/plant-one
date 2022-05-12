package com.project.plantOne.vote;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import static com.project.plantOne.constants.Constants.CHECK_USER_ALREADY_VOTED;
import static com.project.plantOne.constants.Constants.GET_VOTEID_OF_USER_AND_BLOG;


@Repository
public interface
VoteRepository extends JpaRepository<Vote, UUID>{

    @Query(value = CHECK_USER_ALREADY_VOTED)
    int isUserAlreadyUpvoted(@Param("blogUUID") UUID blogUUID, @Param("userId") UUID userUUID);

    @Query(value = GET_VOTEID_OF_USER_AND_BLOG)
    UUID getVoteIdByUserAndBlog(@Param("blogUUID") UUID blogUUID, @Param("userId") UUID userUUID);

//        Optional<Vote> findTopByBlogAndUserOrderByVoteIdDesc(Blog blog, User currentUser);
}

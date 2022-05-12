package com.project.plantOne.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

import static com.project.plantOne.constants.Constants.CHECK_POST_TYPE_TABLE;

public interface PostTypeRepository extends JpaRepository<PostType, Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO post_type (post_type_id, post_type) VALUES (:post_type_id, :post_type)", nativeQuery = true)
    int insertPostType(@Param("post_type_id") String uuid, @Param("post_type") String postType);

    @Query(value = CHECK_POST_TYPE_TABLE)
    int findPostTypeTableSize();


}

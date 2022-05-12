
package com.project.plantOne.like;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.project.plantOne.constants.Constants.LIKE_URL;


@RestController
@RequestMapping(path = LIKE_URL)
public class LikesController {

    @Autowired
    private LikesServiceImpl likesService;

    @PostMapping(value = "/{postUUID}/{user_id}")
    public String postLikeClick(@PathVariable( "postUUID") UUID postUUID, @PathVariable  ("user_id") UUID userUUID)
    {
        return likesService.likesClick(postUUID, userUUID);
    }
}

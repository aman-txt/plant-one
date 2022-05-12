package com.project.plantOne.vote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.project.plantOne.constants.Constants.VOTE_URL;


@RestController
@RequestMapping(path = VOTE_URL)
public class VoteController {

    @Autowired
    private VoteServiceImpl voteService;

    @PostMapping(value = "/{blogUUID}/{user_id}")
    public String upVoteClick(@PathVariable( "blogUUID") UUID blogUUID, @PathVariable  ("user_id") UUID userUUID)
    {
        return voteService.upVoteClick(blogUUID, userUUID);
    }


}

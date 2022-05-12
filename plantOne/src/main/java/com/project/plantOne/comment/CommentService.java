package com.project.plantOne.comment;

import java.util.UUID;

public interface CommentService {

    public Comment addComment(UUID postUUID, Comment comment);

    public void deleteComment(UUID commentUUID);
}

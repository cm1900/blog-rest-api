package za.co.springhaus.blog.service;

import za.co.springhaus.blog.dto.CommentDto;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);
}

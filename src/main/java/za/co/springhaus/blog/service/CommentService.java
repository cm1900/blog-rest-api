package za.co.springhaus.blog.service;

import za.co.springhaus.blog.dto.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);

    List<CommentDto> getCommentById(long postId);

    CommentDto getCommentsByPostId(long postId, long commentId);

    CommentDto updateComment(long postId, long commentId, CommentDto commentDto);
}

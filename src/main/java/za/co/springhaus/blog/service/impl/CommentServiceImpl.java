package za.co.springhaus.blog.service.impl;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import za.co.springhaus.blog.dto.CommentDto;
import za.co.springhaus.blog.entity.Comment;
import za.co.springhaus.blog.entity.Post;
import za.co.springhaus.blog.exception.BlogAPIException;
import za.co.springhaus.blog.exception.ResourceNotFoundException;
import za.co.springhaus.blog.repository.CommentRepository;
import za.co.springhaus.blog.repository.PostRepository;
import za.co.springhaus.blog.service.CommentService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final ModelMapper mapper;
    private static final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public CommentDto createComment(Long postId, CommentDto commentDto) {
        logger.info("Creating comment for postId: {}", postId);
        Comment comment = mapToEntity(commentDto);

        Post post = getPostOrThrow(postId);

        comment.setPost(post);

        return mapToDto(commentRepository.save(comment));
    }

    @Override
    public List<CommentDto> getCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);

        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        Post post = getPostOrThrow(postId);
        Comment comment = getCommentOrThrow(commentId);
        validateCommentBelongsToPost(post, comment);

        return mapToDto(comment);
    }

    @Override
    public CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto) {

        Post post = getPostOrThrow(postId);
        Comment comment = getCommentOrThrow(commentId);
        validateCommentBelongsToPost(post, comment);


        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        return mapToDto(commentRepository.save(comment));
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        Post post = getPostOrThrow(postId);
        Comment comment = getCommentOrThrow(commentId);
        validateCommentBelongsToPost(post, comment);

        commentRepository.delete(comment);
    }

    private CommentDto mapToDto(Comment comment){
        return mapper.map(comment, CommentDto.class);
    }

    private Comment mapToEntity(CommentDto commentDto){
        return mapper.map(commentDto, Comment.class);
    }

    private Post getPostOrThrow(long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
    }

    private Comment getCommentOrThrow(long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
    }

    private void validateCommentBelongsToPost(Post post, Comment comment) {
        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }
    }

}

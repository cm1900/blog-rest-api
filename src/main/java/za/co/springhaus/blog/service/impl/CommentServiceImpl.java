package za.co.springhaus.blog.service.impl;

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

    private CommentRepository commentRepository;
    private PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment = mapToEntity(commentDto);

        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "id", postId));

        comment.setPost(post);

        return mapToDto(commentRepository.save(comment));
    }

    @Override
    public List<CommentDto> getCommentById(long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);

        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentsByPostId(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }
        return mapToDto(comment);
    }

    private CommentDto mapToDto(Comment comment){
        CommentDto commentDto = new CommentDto();

        commentDto.setId(comment.getId());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        commentDto.setBody(comment.getBody());
        return commentDto;
    }

    private Comment mapToEntity(CommentDto commentDto){
        Comment comment = new Comment();

        comment.setId(commentDto.getId());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        return comment;
    }
}

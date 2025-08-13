package za.co.springhaus.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.springhaus.blog.entity.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(long postId);
}

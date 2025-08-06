package za.co.springhaus.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.springhaus.blog.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}

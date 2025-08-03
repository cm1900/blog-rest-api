package za.co.springhaus.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.springhaus.blog.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

    //No need for any code here since JPA repository has all the standard methods
}

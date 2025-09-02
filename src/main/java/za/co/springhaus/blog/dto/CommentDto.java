package za.co.springhaus.blog.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDto {
    private Long id;

    @NotEmpty(message = "A name must be provided")
    @Size(min = 2, message = "The commenter's name must have at least 2 characters")
    private String name;

    @NotEmpty(message = "An email address must be provided")
    @Email(message = "A valid email address must be provided")
    private String email;

    @NotEmpty
    @Size(min = 10, message = "The comment body must have at least 10 characters")
    private String body;
}

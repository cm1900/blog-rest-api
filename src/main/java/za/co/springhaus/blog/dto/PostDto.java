package za.co.springhaus.blog.dto;

import lombok.Data;

@Data
public class PostDto {
    private long id;
    private String title;
    private String description;
    private String content;
}

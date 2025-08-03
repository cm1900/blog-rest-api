package za.co.springhaus.blog.service;

import za.co.springhaus.blog.dto.PostDto;

public interface PostService {
    PostDto createPost(PostDto postDto);
}

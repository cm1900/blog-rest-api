package za.co.springhaus.blog.service;

import za.co.springhaus.blog.dto.LoginDto;

public interface AuthService {
    String login(LoginDto loginDto);
}

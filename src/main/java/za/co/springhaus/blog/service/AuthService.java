package za.co.springhaus.blog.service;

import za.co.springhaus.blog.dto.LoginDto;
import za.co.springhaus.blog.dto.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);
    String register(RegisterDto registerDto);
}

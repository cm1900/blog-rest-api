package za.co.springhaus.blog.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import za.co.springhaus.blog.dto.JwtAuthResponseDto;
import za.co.springhaus.blog.dto.LoginDto;
import za.co.springhaus.blog.dto.RegisterDto;
import za.co.springhaus.blog.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Login REST API
    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<JwtAuthResponseDto> login(@RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);

        JwtAuthResponseDto jwtAuthResponseDto = new JwtAuthResponseDto();
        jwtAuthResponseDto.setAccessToken(token);
        return ResponseEntity.ok(jwtAuthResponseDto);
    }

    // Register REST API
    @PostMapping(value = {"/register", "/signup"})
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        String response = authService.register(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}

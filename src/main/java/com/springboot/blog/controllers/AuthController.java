package com.springboot.blog.controllers;

import com.springboot.blog.dtos.JWTAuthResponse;
import com.springboot.blog.dtos.LoginDto;
import com.springboot.blog.dtos.RegisterDto;
import com.springboot.blog.exceptions.BlogApiException;
import com.springboot.blog.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
@Tag(
        name = "CRUD REST API's for Auth Resource"
)
public class AuthController {
    private AuthService authService;
    public AuthController(AuthService authService){
        this.authService =authService;
    }
    @PostMapping(value = {"login", "signin"})
    @Operation(
            summary = "Login Rest API",
            description = "Login Rest API is used to Login/Signin"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 Successfully"
    )
    public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);

        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        return ResponseEntity.ok(jwtAuthResponse);
    }
    @PostMapping(value = {"register", "signup"})
    @Operation(
            summary = "Register Rest API",
            description = "Register Rest API is used to Register/Signup"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 Successfully"
    )
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) throws BlogApiException {
        String response = authService.register(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}

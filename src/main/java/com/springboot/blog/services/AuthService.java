package com.springboot.blog.services;

import com.springboot.blog.dtos.LoginDto;
import com.springboot.blog.dtos.RegisterDto;
import com.springboot.blog.exceptions.BlogApiException;

public interface AuthService {
    String login(LoginDto loginDto);
    String register(RegisterDto registerDto) throws BlogApiException;
}

package com.springboot.security.service;

import com.springboot.security.data.dto.SignInResultDto;
import com.springboot.security.data.dto.SignUpResultDto;

public interface SignService {

    //회원가입
    SignUpResultDto signUp(String id, String password, String name, String role);
    //로그인
    SignInResultDto signIn(String id, String password) throws RuntimeException;
}

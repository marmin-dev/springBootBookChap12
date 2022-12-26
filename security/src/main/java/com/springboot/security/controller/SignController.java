package com.springboot.security.controller;

import com.springboot.security.data.dto.SignInResultDto;
import com.springboot.security.data.dto.SignUpResultDto;
import com.springboot.security.service.SignService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/sign-api")
public class SignController {

    private final Logger logger = LoggerFactory.getLogger(SignController.class);
    private final SignService signService;

    @Autowired
    public SignController(SignService signService){
        this.signService = signService;
    }

    @PostMapping(value = "/sign-in")
    public SignInResultDto signIn(
            @ApiParam(value = "ID",required = true)@RequestParam String id,
            @ApiParam(value = "Password",required = true)@RequestParam String password
    )throws RuntimeException {
        logger.info("signIn 로그인을 시도하고 있습니다. id={},pw:**** ", id);
        SignInResultDto signInResultDto = signService.signIn(id, password);

        if (signInResultDto.getCode() == 0) {
            logger.info("signIn 정상적으로 로그인 되었습니다 id:{}, token:{}");
            signInResultDto.getToken();
        }
        return signInResultDto;
    }
    @PostMapping(value = "/sign-up")
    public SignUpResultDto signUp(
            @ApiParam(value = "ID",required = true)@RequestParam String id,
            @ApiParam(value = "비밀번호",required = true)@RequestParam String password,
            @ApiParam(value = "이름",required = true)@RequestParam String name,
            @ApiParam(value = "권한",required = true)@RequestParam String role
    ){
        logger.info("회원 가입을 수행한다");
        SignUpResultDto signUpResultDto = signService.signUp(id, password, name, role);
        logger.info("SignUp 회원 가입을 완료하였습니다 id:{}",id);
        return signUpResultDto;
    }

    @GetMapping(value = "/exception")
    public void exceptionTest() throws RuntimeException{
        throw new RuntimeException("접근이 금지되었다");
    }
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Map<String, String>>ExceptionHandler(RuntimeException e){
        HttpHeaders responseHeaders = new HttpHeaders();
        //responseHeaders.add(HttpHeaders.CONTENT_TYPE,"application/json");
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        logger.error("ExceptionHandler 호출 {} {}",e.getCause(),e.getMessage());

        Map<String,String> map= new HashMap<>();
        map.put("errorType",httpStatus.getReasonPhrase());
        map.put("code","400");
        map.put("message","에러발생");

        return new ResponseEntity<>(map,responseHeaders,httpStatus);
    }
}

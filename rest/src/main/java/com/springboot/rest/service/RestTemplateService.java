package com.springboot.rest.service;

import com.springboot.rest.dto.MemberDto;
import io.swagger.models.Response;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class RestTemplateService {

    public String getName(){
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("/api/v1/crud-api")
                .encode()
                .build()
                .toUri();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri,String.class);
        return responseEntity.getBody();
    }

    public String getNameWithPathVariable(){
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("/api/v1/crud-api/{name}")
                .encode()
                .build()
                .expand("marmin") //복수값을 넣어야 할경우네는 ,를 추가하여 구분
                .toUri();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri,String.class);
        return responseEntity.getBody();
    }

    public String getNameWithParameter(){
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("api/v1/crud-api/param")
                .queryParam("name","marmin")
                .encode()
                .build()
                .toUri();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri,String.class);
        return responseEntity.getBody();
    }
    public ResponseEntity<MemberDto> postWithParamAndBody(){
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("/api/v1/crud-api")
                .queryParam("name","marmin")
                .queryParam("email","marmin@gmail.com")
                .queryParam("organization","killa")
                .encode().build().toUri();

        MemberDto memberDto = new MemberDto();
        memberDto.setName("marmin!!");
        memberDto.setEmail("marmin@gmail.com");
        memberDto.setOrganization("Around Hub Studio");

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<MemberDto> responseEntity = restTemplate.postForEntity(uri,memberDto
        ,MemberDto.class);
        return responseEntity;
    }

    public ResponseEntity<MemberDto> postWithHeader(){
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("api/v1/crud-api/add-header")
                .encode()
                .build()
                .toUri();

        MemberDto memberDto = new MemberDto();
        memberDto.setName("marmin");
        memberDto.setEmail("marmin@gmail.com");
        memberDto.setOrganization("Around hub Studio");

        RequestEntity<MemberDto> requestEntity = RequestEntity
                .post(uri)
                .header("my-header","MarminApi")
                .body(memberDto);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<MemberDto> responseEntity = restTemplate.exchange(requestEntity,
                MemberDto.class);
        return responseEntity;
    }
}

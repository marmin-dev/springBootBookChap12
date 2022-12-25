package com.springboot.security.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false,unique = true)
    private String uid;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
    //계정이 가지고 있는 권한 목록을 리턴한다
    @Override
    public String getPassword() {
        return this.password;
    }
    //계정의 비밀번호를 리턴한다
    @Override
    public String getUsername() {
        return this.uid;
    }
    //계정으 이름을 리턴.일반적으로 아이디를 리턴한다
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    //계정이 만료되었는지 리턴 true 는 만료되지 않음
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    //계정이 잠겨있는지 리턴한다. true 는 잠기지 않았다는 의미이다
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    //비밀번호가 만료되었는지 리턴한다. true는 만료되지 않았다는 의미이다
    @Override
    public boolean isEnabled() {
        return true;
    }//계정이 활성화 되어있는지 리턴한다. true는 활성화 상태를 의미한다
}

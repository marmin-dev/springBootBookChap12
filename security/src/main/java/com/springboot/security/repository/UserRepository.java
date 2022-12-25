package com.springboot.security.repository;

import com.springboot.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    User getByUid(String uid);//uid를 통해 id를 가져오는 서비스
}

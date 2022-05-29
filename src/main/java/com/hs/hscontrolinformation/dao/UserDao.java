package com.hs.hscontrolinformation.dao;


import com.hs.hscontrolinformation.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Long> {

    User findByUsername(String username);
}

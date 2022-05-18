package com.miw.gildedrose.dao;

import com.miw.gildedrose.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<UserEntity, Long> {

    UserEntity findFirstByAccessToken(Object accessToken);

    UserEntity findDistinctFirstByEmail(String email);
}

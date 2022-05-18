package com.miw.gildedrose.service;

import com.miw.gildedrose.dao.UserDao;
import com.miw.gildedrose.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public Optional<UserDetails> findUserByAccessToken(String accessToken) {
        UserEntity userEntity = userDao.findFirstByAccessToken(accessToken);
        if(userEntity!=null) {
            User user = new User(userEntity.getEmail(), userEntity.getAccessToken(), true, true, true, true, AuthorityUtils.createAuthorityList("USER"));
            return Optional.of(user);
        }
        return Optional.empty();
    }
}

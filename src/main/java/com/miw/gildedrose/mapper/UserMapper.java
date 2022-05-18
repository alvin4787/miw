package com.miw.gildedrose.mapper;

import com.miw.gildedrose.entity.UserEntity;
import com.miw.gildedrose.model.UserModel;

public class UserMapper {

    public UserEntity toEntity(UserEntity entity, UserModel model) {
        entity.setFirstName(model.getFirstName());
        entity.setLastName(model.getLastName());
        entity.setEmail(model.getEmail());
        entity.setAccessToken(model.getAccessToken());
        entity.setUid(model.getUid());

        return entity;
    }

    public UserModel toModel(UserEntity entity, UserModel model) {
        model.setFirstName(entity.getFirstName());
        model.setLastName(entity.getLastName());
        model.setEmail(entity.getEmail());
        model.setAccessToken(entity.getAccessToken());
        model.setUid(entity.getUid());

        return model;
    }
}

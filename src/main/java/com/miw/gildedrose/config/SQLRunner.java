package com.miw.gildedrose.config;

import com.miw.gildedrose.dao.ItemDao;
import com.miw.gildedrose.dao.UserDao;
import com.miw.gildedrose.entity.ItemEntity;
import com.miw.gildedrose.entity.UserEntity;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class SQLRunner implements ApplicationRunner {

    private UserDao userDao;

    private ItemDao itemDao;

    @Autowired
    public SQLRunner(UserDao userDao, ItemDao itemDao) {
        this.userDao = userDao;
        this.itemDao = itemDao;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        userDao.save(new UserEntity("Alvin", "Paul", "alvin@gmail.com", RandomStringUtils.randomAlphabetic(20)));
//        userDao.save(new UserEntity("Ryan", "Philips", "ryan@gmail.com", RandomStringUtils.randomAlphabetic(20)));
//        userDao.save(new UserEntity("Greg", "George", "greg@gmail.com", RandomStringUtils.randomAlphabetic(20)));
//        userDao.save(new UserEntity("Mathew", "Jocob", "mathew@gmail.com", RandomStringUtils.randomAlphabetic(20)));
//        userDao.save(new UserEntity("Allen", "Paul", "allen@gmail.com", RandomStringUtils.randomAlphabetic(20)));
//
//        itemDao.save(new ItemEntity("Rose","Rose", 10));
//        itemDao.save(new ItemEntity("Lily", "Lily", 12));
//        itemDao.save(new ItemEntity("Orchid", "Orchid", 7));
    }
}

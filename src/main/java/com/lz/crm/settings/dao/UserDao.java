package com.lz.crm.settings.dao;

import com.lz.crm.settings.domain.User;

import java.util.List;
import java.util.Map;

public interface UserDao {
    User login(Map<String, String> map);

    List<User> getUserList();
    int save(User user);

    User get(String username);

    int update(String uid);
}

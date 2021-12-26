package com.lz.crm.settings.service;

import com.lz.crm.exception.LoginException;
import com.lz.crm.settings.domain.User;

import java.util.List;

public interface UserService {
    User login(String loginAct, String loginPwd, String ip)throws LoginException;

    List<User> getUserList();
    Boolean regist(User user);

    String activeUser(String uid);
}

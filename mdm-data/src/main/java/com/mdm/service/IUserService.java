package com.mdm.service;

import java.util.List;

import com.mdm.entity.User;

public interface IUserService {
    
    List<User> userlist() throws Exception;

    int insert(User user) throws Exception;

    User selectByUserId(Integer userid) throws Exception;
}

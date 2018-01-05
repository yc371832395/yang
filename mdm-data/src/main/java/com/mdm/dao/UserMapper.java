package com.mdm.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mdm.entity.User;

@Repository
public interface UserMapper {

    List<User> userlist();
    
    int insert(User user);
    
    User selectByUserId(Integer userid);
}

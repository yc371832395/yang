package com.mdm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mdm.dao.UserMapper;
import com.mdm.entity.User;
import com.mdm.service.IUserService;


@Service("iUserservice")
public class UserServiceImpl implements IUserService {

    private final UserMapper userMapper;
    
    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
    
    
    @Override
    public List<User> userlist() throws Exception {
        return userMapper.userlist();
    }

    @Override
    public int insert(User user) throws Exception {
        return userMapper.insert(user);
    }

    @Override
    public User selectByUserId(Integer userid) throws Exception {
        return userMapper.selectByUserId(userid);
    }

}

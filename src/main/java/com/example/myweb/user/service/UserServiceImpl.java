package com.example.myweb.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myweb.user.dto.UserVo;
import com.example.myweb.user.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService {
	private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public List<UserVo> findAll() {
        return userMapper.findAll();
    }

    @Override
    public Optional<UserVo> findById(String id) {
        return Optional.ofNullable(userMapper.findById(id));
    }

    @Override
    public void save(UserVo user) {
        if (user.getId() == null) {
            userMapper.insert(user);
        } else {
            userMapper.update(user);
        }
    }

    @Override
    public void deleteById(String id) {
        userMapper.delete(id);
    }
}

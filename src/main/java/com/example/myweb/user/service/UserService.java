package com.example.myweb.user.service;

import com.example.myweb.user.dto.UserVo;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserVo> findAll();
    Optional<UserVo> findById(String id);
    void save(UserVo user);
    void deleteById(String id);
}

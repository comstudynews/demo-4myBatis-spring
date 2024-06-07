package com.example.myweb.user.mapper;

import org.apache.ibatis.annotations.*;

import com.example.myweb.user.dto.UserVo;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM users")
    List<UserVo> findAll();

    @Select("SELECT * FROM users WHERE id = #{id}")
    UserVo findById(String id);

    @Insert("INSERT INTO users(id, password, name, role) VALUES(#{id}, #{password}, #{name}, #{role})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(UserVo user);

    @Update("UPDATE users SET password=#{passpword}, name=#{name}, role=#{role} WHERE id=#{id}")
    void update(UserVo user);

    @Delete("DELETE FROM users WHERE id = #{id}")
    void delete(String id);
}

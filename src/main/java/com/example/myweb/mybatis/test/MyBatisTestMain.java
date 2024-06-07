package com.example.myweb.mybatis.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.example.myweb.user.dto.UserVo;

public class MyBatisTestMain {

	public static void main(String[] args) throws IOException {
		// MyBatis 설정 테스트 메인
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		
		try (SqlSession mybatis = sqlSessionFactory.openSession()) {
			//UserVo user = mybatis.selectOne("UserMapper.findById", "test2");
			//System.out.println("user => " + user);
			
			List<UserVo> userList = mybatis.selectList("UserMapper.selectAll");
			for(int i=0; i<userList.size(); i++) {
				System.out.println(userList.get(i));
			}
		}
	}

}

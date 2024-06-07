


https://cute-almanac-e54.notion.site/Day05-Spring-Mybatis-JPA-bca0e3798c074928ae6e1ba715a4a32c?pvs=4

![image](https://github.com/comstudynews/demo-4myBatis-spring/assets/96456853/776bdd37-40d4-446b-9701-f374f949fe75)

# Spring과 MyBatis

Spring과 MyBatis를 통합하여 사용하는 방법과 MyBatis 매퍼를 구현하는 예제를 제공하겠습니다. MyBatis는 SQL을 직접 작성할 수 있도록 지원하는 데이터 매핑 프레임워크로, Spring과 쉽게 통합할 수 있습니다.

### 새 프로젝트 생성

- STS4 IDE - Spring Starter Project
- JDK 17
- Maven - jar

![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/73d32148-7b0b-42ac-9b0b-2bfaa5903e80/36398254-34ab-4df1-98db-26ad85f6f9b7/Untitled.png)

### H2데이터 베이스에 Users 테이블 생성

```sql
DROP TABLE IF EXISTS Users;

CREATE TABLE IF NOT EXISTS Users (
ID VARCHAR(20) PRIMARY KEY,
PASSWORD VARCHAR(20),
NAME VARCHAR(50),
ROLE VARCHAR(20)
);

INSERT INTO USERS VALUES('test','test123','관리자','Admin');
INSERT INTO USERS VALUES('user1','user1','홍길동','User');
```

### 1. 의존성 추가

**pom.xml** (Spring Boot 프로젝트를 사용하는 경우)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>3.3.0</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>
	<groupId>com.example</groupId>
	<artifactId>demo-mybatis-spring</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>demo-4myBatis-spring</name>
	<description>mybatis project for Spring Boot</description>
	<properties>
		<java.version>17</java.version>
	</properties>
	<dependencies>
		**<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>**
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
			<scope>runtime</scope>
			<optional>true</optional>
		</dependency>
		**<dependency>
			<groupId>org.projectlombok</groupId>
			<artifactId>lombok</artifactId>
			<optional>true</optional>
		</dependency>**
		<dependency
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
		<!-- mybatis-spring-boot-starter -->
		**<dependency>
		    <groupId>org.mybatis.spring.boot</groupId>
		    <artifactId>mybatis-spring-boot-starter</artifactId>
		    <version>3.0.3</version>
		</dependency>
		<dependency>
			<groupId>com.h2database</groupId>
			<artifactId>h2</artifactId>
			<scope>runtime</scope>
		</dependency>**
	</dependencies>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
				<configuration>
					<excludes>
						<exclude>
							<groupId>org.projectlombok</groupId>
							<artifactId>lombok</artifactId>
						</exclude>
					</excludes>
				</configuration>
			</plugin>
		</plugins>
	</build>

</project>
```

### 2. 데이터베이스 설정

**application.properties**

```
spring.application.name=demo-4myBatis-spring

spring.datasource.url=jdbc:h2:tcp://localhost/~/test
spring.datasource.username=sa
spring.datasource.password=
spring.datasource.driver-class-name=org.h2.Driver

mybatis.mapper-locations=classpath:mapper/*.xml
mybatis.type-aliases-package=com.example.myweb.user.dto
```

### 3. 엔터티 클래스 작성

**UserVo.java**

```java
package com.example.myweb.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo {
	private String id;
	private String password;
	private String name;
	private String role;
}
```

### 4. 매퍼 인터페이스 또는 XML 파일 작성

**UserMapper.java**

```java
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

```

또는 기존 방식대로 XML 파일로 매퍼를 정의할 수 있습니다.

**UserMapper.xml**

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
  PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
  "https://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="UserMapper">
	<resultMap id="userResultMap" type="com.example.myweb.user.dto.UserVo">
		<id property="id" column="id" />
		<result property="password" column="password" />
		<result property="name" column="name" />
		<result property="role" column="role" />
	</resultMap>

	<select id="selectById" resultType="com.example.myweb.user.dto.UserVo">
		select * from Users where id = #{id}
	</select>

	<select id="selectAll"
		resultType="com.example.myweb.user.dto.UserVo">
		select * from Users;
	</select>

	<select id="findAll" resultMap="userResultMap">
		SELECT * FROM users
	</select>

	<select id="findById" resultMap="userResultMap">
		SELECT * FROM users WHERE id = #{id}
	</select>

	<insert id="insert" useGeneratedKeys="true" keyProperty="id">
		INSERT INTO users(id, password, name, role) VALUES(#{id}, #{password}, #{name}, #{role})
	</insert>

	<update id="update">
		UPDATE users SET password=#{password},  name=#{name}, role=#{role} WHERE id=#{id}
	</update>

	<delete id="delete">
		DELETE FROM users WHERE id = #{id}
	</delete>
</mapper>
```

### 5. 서비스 인터페이스 및 구현 클래스 작성

**UserService.java**

```java
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

```

**UserServiceImpl.java**

```java
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
```

### 6. RESTful 컨트롤러 작성

**UserController.java**

```java
package com.example.myweb.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.myweb.user.dto.UserVo;
import com.example.myweb.user.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserVo> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserVo> getUserById(@PathVariable String id) {
        Optional<UserVo> user = userService.findById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserVo> createUser(@RequestBody UserVo user) {
        userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserVo> updateUser(@PathVariable String id, @RequestBody UserVo user) {
        Optional<UserVo> existingUser = userService.findById(id);
        if (existingUser.isPresent()) {
            user.setId(id);
            userService.save(user);
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
```

### 7. 스프링 부트 메인 클래스

**Application.java**

```java
package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

```

### 설명

1. **의존성 추가**: `spring-boot-starter-web`과 `spring-boot-starter-data-mybatis`를 포함하여 MyBatis와 Spring Boot를 통합할 수 있습니다.
2. **데이터베이스 설정**: `application.properties` 파일에서 데이터베이스 연결 정보와 MyBatis 설정을 정의합니다.
3. **엔터티 클래스**: `UserVo` 클래스를 정의하여 사용자 정보를 저장합니다.
4. **매퍼 인터페이스 및 XML 파일**: `UserMapper` 인터페이스와 XML 매퍼 파일을 사용하여 데이터베이스 연산을 정의합니다. 이 예제에서는 주석 기반의 SQL과 XML 파일을 모두 사용하여 매퍼를 정의했습니다.
5. **서비스 인터페이스 및 구현 클래스**: `UserService` 인터페이스를 정의하고, `UserServiceImpl` 클래스에서 이 인터페이스를 구현합니다. `UserServiceImpl` 클래스는 `UserMapper`를 주입받아 데이터베이스 연산을 수행합니다.
6. **RESTful 컨트롤러**: `UserController` 클래스는 RESTful 웹 서비스 엔드포인트를 제공합니다. 이 클래스는 `UserService`를 사용하여 사용자 데이터를 처리합니다.
7. **스프링 부트 메인 클래스**: `Application` 클래스를 정의하여 스프링 부트 애플리케이션을 실행합니다.

### 실행 결과

![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/73d32148-7b0b-42ac-9b0b-2bfaa5903e80/6fa3b76c-6c90-456f-a689-6eb1d20799c9/Untitled.png)

### 소스코드

[demo-4myBatis-spring.zip](https://prod-files-secure.s3.us-west-2.amazonaws.com/73d32148-7b0b-42ac-9b0b-2bfaa5903e80/8d5fa59d-9d55-4304-94ab-c44403381a00/demo-4myBatis-spring.zip)

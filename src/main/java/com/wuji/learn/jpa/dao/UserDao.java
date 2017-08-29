package com.wuji.learn.jpa.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wuji.learn.jpa.model.User;

public interface UserDao extends JpaRepository<User, Long> {
	User findByUserName(String userName);
}

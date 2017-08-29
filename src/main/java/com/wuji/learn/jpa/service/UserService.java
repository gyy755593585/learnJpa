package com.wuji.learn.jpa.service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import com.wuji.learn.jpa.model.Pager;
import com.wuji.learn.jpa.model.User;

public interface UserService extends BaseService<User> {
	User findByUserName(String userName);

	Pager<User> findByPager();

	void update(User user, long[] roleIds);

	/**
	 * @param excelObject
	 * @throws NoSuchAlgorithmException
	 */
	void addUserByExcel(List<List<Object>> excelObject) throws NoSuchAlgorithmException;
}

package com.wuji.learn.jpa.service.impl;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wuji.learn.jpa.dao.RoleDao;
import com.wuji.learn.jpa.dao.UserDao;
import com.wuji.learn.jpa.dao.UserRoleDao;
import com.wuji.learn.jpa.model.Pager;
import com.wuji.learn.jpa.model.Role;
import com.wuji.learn.jpa.model.SystemException;
import com.wuji.learn.jpa.model.User;
import com.wuji.learn.jpa.model.UserRole;
import com.wuji.learn.jpa.service.UserService;
import com.wuji.learn.jpa.shiro.PasswordHelper;

@Service("userService")
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private UserRoleDao userRoleDao;
	@Autowired
	private PasswordHelper passwordHelper;

	@Override
	public User add(User user) {
		User existUser = this.userDao.findByUserName(user.getUserName());
		if (existUser != null) {
			throw new SystemException("用户名称已存在");
		}
		this.passwordHelper.encryptPassword(user);
		return this.userDao.save(user);
	}

	@Override
	public void update(User t) {
		this.passwordHelper.encryptPassword(t);
		this.userDao.saveAndFlush(t);
	}

	@Override
	public void delete(Long id) {
		this.userDao.delete(id);
	}

	@Override
	public User load(Long id) {
		return this.userDao.findOne(id);
	}

	@Override
	public List<User> findAll() {
		return this.userDao.findAll();
	}

	@Override
	public User findByUserName(String userName) {
		return this.userDao.findByUserName(userName);
	}

	@Override
	public Pager<User> findByPager() {
		Page<User> findAll = this.userDao.findAll(this.getPageAble());
		return this.toEasyUiPage(findAll);
	}

	@Override
	public void update(User user, long[] roleIds) {
		this.userRoleDao.deleteByUserId(user.getId());
		UserRole userRole = null;
		for (Long roleId : roleIds) {
			userRole = new UserRole();
			Role role = this.roleDao.getOne(roleId);
			userRole.setRole(role);
			userRole.setUser(user);
			this.userRoleDao.save(userRole);
		}
		if (user.getPassword() != null) {
			this.passwordHelper.encryptPassword(user);
		}
		this.userDao.saveAndFlush(user);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.wuji.authority.service.UserService#addUserByExcel(java.util.List)
	 */
	@Override
	public void addUserByExcel(List<List<Object>> excelObject) throws NoSuchAlgorithmException {
		String jmsg = "";
		int length = excelObject.size();
		this.logger.debug(String.valueOf(length));
		User user = null;
		for (int i = 0; i < length; i++) {
			jmsg = String.format("第 %d/%d 行数据,", i, length);
			user = new User();
			List<Object> list = excelObject.get(i);
			user.setUserName(list.get(0).toString());
			User listUsers = this.userDao.findByUserName(user.getUserName());
			if (listUsers != null) {
				throw new SystemException("用户名称已存在" + jmsg);
			}
			user.setNickName(list.get(1).toString());
			user.setPassword(list.get(2).toString());
			user.setType(1);
			user.setStatus(0);
			this.passwordHelper.encryptPassword(user);
			this.add(user);
		}
	}
}

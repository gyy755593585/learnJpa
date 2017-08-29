
/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package com.wuji.learn.jpa.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wuji.learn.jpa.dao.PermitDao;
import com.wuji.learn.jpa.dao.RoleDao;
import com.wuji.learn.jpa.dao.RolePermitDao;
import com.wuji.learn.jpa.dao.UserRoleDao;
import com.wuji.learn.jpa.model.Pager;
import com.wuji.learn.jpa.model.Permit;
import com.wuji.learn.jpa.model.Role;
import com.wuji.learn.jpa.model.RolePermit;
import com.wuji.learn.jpa.model.User;
import com.wuji.learn.jpa.model.UserRole;
import com.wuji.learn.jpa.service.RoleService;
import com.wuji.learn.jpa.vo.Tree;

/**
 * @author Yayun
 *
 */
@Transactional
@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private UserRoleDao userRoleDao;

	@Autowired
	private RolePermitDao rolePermitDao;

	@Autowired
	private PermitDao permitDao;

	@Override
	public Role add(Role t) {
		return this.roleDao.save(t);
	}

	@Override
	public void update(Role t) {
		this.roleDao.saveAndFlush(t);
	}

	/*
	 * 删除角色 同时删除角色和权限关联表,角色和用户关联表中的数据
	 */
	@Override
	public void delete(Long id) {
		this.rolePermitDao.deleteByRoleId(id);
		this.userRoleDao.deleteByRoleId(id);
		this.roleDao.delete(id);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Role load(Long id) {
		return this.roleDao.getOne(id);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public List<Role> findAll() {
		return this.roleDao.findAll();
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public List<Role> findRoleByUserName(String userName) {
		return this.userRoleDao.findRoleByUserUserName(userName);
	}

	@Override
	public UserRole addRoleForUser(User user, Role role) {
		UserRole userRole = new UserRole();
		userRole.setRole(role);
		userRole.setUser(user);
		return this.userRoleDao.save(userRole);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public List<Long> findPermitIdListByRoleId(Long id) {
		return this.rolePermitDao.findPermitIdListByRoleId(id);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public List<Tree> findAllTree() {
		List<Tree> result = new ArrayList<Tree>();
		List<Role> roles = this.roleDao.findAll();
		Tree tree = null;
		for (Role role : roles) {
			tree = new Tree();
			tree.setId(role.getId());
			tree.setText(role.getRoleName());
			result.add(tree);
		}
		return result;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Pager<Role> findByPager() {
		Page<Role> page = this.roleDao.findAll(this.getPageAble());
		Pager<Role> result = this.toEasyUiPage(page);
		return result;
	}

	@Override
	public void updateRolePermit(Long id, long[] permitIds) {
		this.rolePermitDao.deleteByRoleId(id);
		Role role = this.load(id);
		RolePermit rolePermit = null;
		for (long permitId : permitIds) {
			rolePermit = new RolePermit();
			Permit permit = this.permitDao.getOne(permitId);
			rolePermit.setPermit(permit);
			rolePermit.setRole(role);
			this.rolePermitDao.save(rolePermit);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Pager<Role> findByPermitId(Long permitId) {
		Page<RolePermit> page = this.rolePermitDao.findByPermitId(permitId, this.getPageAble());
		Pager<Role> result = new Pager<Role>();
		List<Role> roles = new ArrayList<>();
		List<RolePermit> rows = page.getContent();
		for (RolePermit rolePermit : rows) {
			roles.add(rolePermit.getRole());
		}
		result.setRows(roles);
		result.setTotal(page.getTotalElements());
		return result;
	}

}

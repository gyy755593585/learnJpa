/**
 *
 */
package com.wuji.authority.test;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wuji.learn.jpa.config.MyDaoConfig;
import com.wuji.learn.jpa.model.Permit;
import com.wuji.learn.jpa.model.Role;
import com.wuji.learn.jpa.model.SystemRequest;
import com.wuji.learn.jpa.model.SystemRequestHolder;
import com.wuji.learn.jpa.model.User;
import com.wuji.learn.jpa.service.PermitService;
import com.wuji.learn.jpa.service.RoleService;
import com.wuji.learn.jpa.service.UserService;

/**
 * @author Yayun
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { MyDaoConfig.class })
public class TestService {

	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;

	@Autowired
	private PermitService permitService;

	@Before
	public void test() {
		SystemRequest systemRequest = new SystemRequest();
		systemRequest.setCurrentUser("admin");
		SystemRequestHolder.initRequestHolder(systemRequest);
	}

	@Test
	public void testUserAdd() throws NoSuchAlgorithmException {
		User user = new User();
		user.setUserName("admin");
		user.setNickName("管理员");
		user.setStatus(0);
		user.setType(1);
		user.setPassword("test");
		this.userService.add(user);
	}

	@Test
	public void testUpdateUser() {
		User user = this.userService.load(1L);
		user.setPassword("test");
		this.userService.update(user);
	}

	@Test
	public void testFindByUserName() {
		User user = this.userService.findByUserName("admin");
		Assert.assertEquals(user.getNickName(), "管理员");
	}

	@Test
	public void testAddRole() {
		Role role = new Role();
		role.setRoleName("admin");
		role.setType(1);
		role = this.roleService.add(role);
		System.out.println(role.getId());
	}

	@Test
	public void testAddRoleForUser() {
		User user = this.userService.load(1L);
		Role role = this.roleService.load(1L);
		this.roleService.addRoleForUser(user, role);
	}

	@Test
	public void testAddPermit() {
		Permit permit = new Permit();
		permit.setPermitCode("/userAction");
		permit.setPermitName("用户管理");
		this.permitService.add(permit);
	}

	@Test
	public void TestAddPermitForRole() {
		Role role = this.roleService.load(1L);
		Permit permit = this.permitService.load(1L);
		this.permitService.addPermitForRole(role, permit);
	}

	@Test
	public void TestFindRoleByUserName() {
		List<Role> list = this.roleService.findRoleByUserName("admin");
		Assert.assertNotNull(list);
	}

}

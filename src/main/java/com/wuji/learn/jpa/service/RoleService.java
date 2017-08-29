/**
 *
 */
package com.wuji.learn.jpa.service;

import java.util.List;

import com.wuji.learn.jpa.model.Pager;
import com.wuji.learn.jpa.model.Role;
import com.wuji.learn.jpa.model.User;
import com.wuji.learn.jpa.model.UserRole;
import com.wuji.learn.jpa.vo.Tree;

/**
 * @author Yayun
 *
 */
public interface RoleService extends BaseService<Role> {

	List<Role> findRoleByUserName(String userName);

	UserRole addRoleForUser(User user, Role role);

	/**
	 * @param id
	 * @param permitIds
	 */
	void updateRolePermit(Long id, long[] permitIds);

	/**
	 * @param id
	 * @return
	 */
	List<Long> findPermitIdListByRoleId(Long id);

	/**
	 * @return
	 */
	List<Tree> findAllTree();

	/**
	 * @return
	 */
	Pager<Role> findByPager();

	/**
	 * @param permitId
	 * @return
	 */
	Pager<Role> findByPermitId(Long permitId);
}

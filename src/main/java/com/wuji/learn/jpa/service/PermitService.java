/**
 *
 */
package com.wuji.learn.jpa.service;

import java.util.List;

import com.wuji.learn.jpa.model.Permit;
import com.wuji.learn.jpa.model.Role;
import com.wuji.learn.jpa.model.RolePermit;
import com.wuji.learn.jpa.vo.Tree;

/**
 * @author Yayun
 *
 */
public interface PermitService extends BaseService<Permit>

{

	/**
	 * @param id
	 * @return
	 */
	List<Permit> findPermitByRoleId(Long roleId);

	RolePermit addPermitForRole(Role role, Permit permit);

	/**
	 * @return
	 */
	List<Tree> findAllTree(Long id);

}

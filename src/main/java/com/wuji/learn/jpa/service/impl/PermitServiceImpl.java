/**
 *
 */
package com.wuji.learn.jpa.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wuji.learn.jpa.dao.PermitDao;
import com.wuji.learn.jpa.dao.RolePermitDao;
import com.wuji.learn.jpa.model.Permit;
import com.wuji.learn.jpa.model.Role;
import com.wuji.learn.jpa.model.RolePermit;
import com.wuji.learn.jpa.service.PermitService;
import com.wuji.learn.jpa.vo.Tree;

/**
 * @author Yayun
 *
 */
@Transactional
@Service("permitService")
public class PermitServiceImpl extends BaseServiceImpl<Permit> implements PermitService {

	@Autowired
	private PermitDao permitDao;

	@Autowired
	private RolePermitDao rolePermitDao;

	@Override
	public Permit add(Permit t) {
		return this.permitDao.save(t);
	}

	@Override
	public void update(Permit t) {
		this.permitDao.saveAndFlush(t);
	}

	@Override
	public void delete(Long id) {
		this.rolePermitDao.deleteByPermitId(id);
		this.permitDao.delete(id);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Permit load(Long id) {
		return this.permitDao.findOne(id);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public List<Permit> findAll() {
		return this.permitDao.findAll();
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public List<Permit> findPermitByRoleId(Long roleId) {
		return this.rolePermitDao.findPermitByRoleId(roleId);
	}

	@Override
	public RolePermit addPermitForRole(Role role, Permit permit) {
		RolePermit rolePermit = new RolePermit();
		rolePermit.setPermit(permit);
		rolePermit.setRole(role);
		return this.rolePermitDao.save(rolePermit);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public List<Tree> findAllTree(Long id) {
		List<Tree> result = new ArrayList<>();
		Tree tree = null;
		List<Permit> pemits = this.permitDao.findByParentPermitId(id);
		for (Permit permit : pemits) {
			tree = new Tree();
			tree.setId(permit.getId());
			tree.setText(permit.getPermitName());
			if (permit.getParentPermit() != null) {
				tree.setPid(permit.getParentPermit().getId());
			}
			if (this.hasChild(permit.getId())) {
				tree.setState("closed");
			} else {
				tree.setState("open");
			}
			result.add(tree);
		}
		return result;
	}

	private boolean hasChild(Long id) {
		if (this.permitDao.countByParentPermitId(id) > 0) {
			return true;
		}
		return false;
	}

}

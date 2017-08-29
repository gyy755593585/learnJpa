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

package com.wuji.learn.jpa.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wuji.learn.jpa.model.Permit;
import com.wuji.learn.jpa.model.RolePermit;

/**
 * @author Yayun
 *
 */
public interface RolePermitDao extends JpaRepository<RolePermit, Long> {

	/**
	 * 通过角色id查询权限
	 *
	 * @param roleId
	 * @return
	 */
	@Query("select rp.permit from RolePermit rp where rp.role.id=?1")
	List<Permit> findPermitByRoleId(Long roleId);

	void deleteByRoleId(Long roleId);

	void deleteByPermitId(Long permitId);

	/**
	 * @param id
	 * @return
	 */
	@Query("select rp.permit.id from RolePermit rp where rp.role.id=?1")
	List<Long> findPermitIdListByRoleId(Long id);

	/**
	 * @param permitId
	 * @return
	 */
	Page<RolePermit> findByPermitId(Long permitId, Pageable pageable);

}

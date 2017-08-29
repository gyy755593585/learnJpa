/**
 *
 */
package com.wuji.learn.jpa.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.wuji.learn.jpa.model.Pager;
import com.wuji.learn.jpa.model.SystemRequest;
import com.wuji.learn.jpa.model.SystemRequestHolder;

/**
 * @author Yayun
 *
 */
public abstract class BaseServiceImpl<T> {

	protected final Logger logger;
	{
		this.logger = LoggerFactory.getLogger(this.getClass());
	}

	protected Pageable getPageAble() {
		SystemRequest systemRequest = SystemRequestHolder.getSystemRequest();
		PageRequest pageable = new PageRequest(systemRequest.getPage(), systemRequest.getPageSize());
		return pageable;
	}

	protected Pager<T> toEasyUiPage(Page<T> page) {
		Pager<T> pager = new Pager<>();
		pager.setRows(page.getContent());
		pager.setTotal(page.getTotalElements());
		return pager;
	}
}

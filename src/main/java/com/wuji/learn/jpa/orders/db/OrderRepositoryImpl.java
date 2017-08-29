package com.wuji.learn.jpa.orders.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.wuji.learn.jpa.orders.Order;

public class OrderRepositoryImpl implements OrderOperations {

	@Autowired
	private MongoOperations mongoOperations;

	@Override
	public List<Order> findOrdersByType(String t) {
		String type = t.equals("NET") ? "WEB" : t;
		Criteria criteria = Criteria.where("type").is(type);
		Query query = Query.query(criteria);
		return this.mongoOperations.find(query, Order.class);
	}

}

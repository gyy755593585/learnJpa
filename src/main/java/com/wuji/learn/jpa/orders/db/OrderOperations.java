package com.wuji.learn.jpa.orders.db;

import java.util.List;

import com.wuji.learn.jpa.orders.Order;

public interface OrderOperations {

	List<Order> findOrdersByType(String type);
}

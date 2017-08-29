package com.wuji.learn.jpa.orders.db;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.wuji.learn.jpa.orders.Order;

public interface OrderRepository extends MongoRepository<Order, String>, OrderOperations {

	List<Order> findByCustomer(String c);

	List<Order> findByCustomerLike(String c);

	List<Order> findByCustomerAndType(String c, String t);

	List<Order> findByCustomerLikeAndType(String c, String t);

	@Query("{'customer':'Chuck Wagon','type':?0}")
	List<Order> findChucksOrders(String t);
}

package com.wuji.learn.jpa.orders;

import java.util.Collection;
import java.util.LinkedHashSet;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class Order {

	@Id
	private String id;

	@Field("client")
	private String customer;

	private String type;

	private Collection<Item> itesm = new LinkedHashSet<Item>();

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCustomer() {
		return this.customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Collection<Item> getItesm() {
		return this.itesm;
	}

	public void setItesm(Collection<Item> itesm) {
		this.itesm = itesm;
	}

}

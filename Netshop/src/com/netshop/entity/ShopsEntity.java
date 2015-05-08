package com.netshop.entity;

import java.util.List;

public class ShopsEntity extends BaseEntity {
	public List<Shop> shop;

	public List<Shop> getShop() {
		return shop;
	}

	public void setShop(List<Shop> shop) {
		this.shop = shop;
	}
}

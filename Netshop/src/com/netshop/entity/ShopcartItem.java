package com.netshop.entity;

import com.netshop.entity.ShopEntity.Shop;

public class ShopcartItem {
	public Shop shop;
	public Product product;
	public String num;
	public Shop getShop() {
		return shop;
	}
	public void setShop(Shop shop) {
		this.shop = shop;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	
}

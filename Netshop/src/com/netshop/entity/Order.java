package com.netshop.entity;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable{
	public String oid;
	public String time;
	public String status;
	public String statusdes;
	public String shopid;
	public String shopname;
	public String total;
	public Object detaillist;
	public List<Product> products;
	public List<Product> getProducts() {
		return products;
	}
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusdes() {
		return statusdes;
	}
	public void setStatusdes(String statusdes) {
		this.statusdes = statusdes;
	}
	public String getShopid() {
		return shopid;
	}
	public void setShopid(String shopid) {
		this.shopid = shopid;
	}
	public String getShopname() {
		return shopname;
	}
	public void setShopname(String shopname) {
		this.shopname = shopname;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public Object getDetaillist() {
		return detaillist;
	}
	public void setDetaillist(Object detaillist) {
		this.detaillist = detaillist;
	}
}

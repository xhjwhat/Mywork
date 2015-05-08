package com.netshop.entity;

import java.util.List;

public class OrderEntity extends BaseEntity {
	public String oid;
	public String time;
	public String status;
	public String statusdes;
	public String shopid;
	public String shopname;
	public String total;
	
	public List<Product> detail;

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

	public List<Product> getDetail() {
		return detail;
	}

	public void setDetail(List<Product> detail) {
		this.detail = detail;
	}
	
	
}

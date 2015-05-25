package com.netshop.entity;

public class Addr extends BaseEntity{
	public Addr(){
		
	}
	public String id;
	public String name;
	public String phone;
	public String address;
	public String isde;
	public String isde() {
		return isde;
	}
	public String getIsde() {
		return isde;
	}
	public void setIsde(String isde) {
		this.isde = isde;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
}

package com.netshop.entity;

import java.io.Serializable;

public class BaseEntity implements Serializable{
	public String description;
	public String error;
	public String repertory;
	public String oid;
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public String getRepertory() {
		return repertory;
	}
	public void setRepertory(String repertory) {
		this.repertory = repertory;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
}

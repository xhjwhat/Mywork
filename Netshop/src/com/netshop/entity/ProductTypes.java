package com.netshop.entity;

import java.io.Serializable;
import java.util.List;

public class ProductTypes extends BaseEntity {
	
	public Object list;
	
	
	public Object getPtype() {
		return list;
	}
	public void setPtype(Object list) {
		this.list = list;
	}
	public static  class ProductType implements Serializable{
		public String name;
		public String id;
		public Object clist;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public Object getCtype() {
			return clist;
		}
		public void setCtype(Object ctype) {
			this.clist = ctype;
		}
		
		
	}
	public static class CType{
		public String name;
		public String id;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		
		
	}
}

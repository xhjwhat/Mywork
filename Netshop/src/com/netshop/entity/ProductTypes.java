package com.netshop.entity;

import java.util.List;

public class ProductTypes extends BaseEntity {
	
	public List<ProductType> list;
	
	
	public List<ProductType> getPtype() {
		return list;
	}
	public void setPtype(List<ProductType> list) {
		this.list = list;
	}
	public class ProductType{
		public String name;
		public String id;
		public List<CType> clist;
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
		public List<CType> getCtype() {
			return clist;
		}
		public void setCtype(List<CType> ctype) {
			this.clist = ctype;
		}
		
		
	}
	public class CType{
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

package com.netshop.entity;

import java.util.List;

public class ProductTypes extends BaseEntity {
	
	public List<ProductType> ptype;
	
	
	public List<ProductType> getPtype() {
		return ptype;
	}
	public void setPtype(List<ProductType> ptype) {
		this.ptype = ptype;
	}
	public class ProductType{
		public String name;
		public String id;
		public List<CType> ctype;
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
			return ctype;
		}
		public void setCtype(List<CType> ctype) {
			this.ctype = ctype;
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

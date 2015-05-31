package com.netshop.entity;

import java.util.List;

public class ProductEntity extends BaseEntity {
public Lists list;
	
	public Lists getList() {
		return list;
	}

	public void setList(Lists list) {
		this.list = list;
	}

	public class Lists {
		public String currentpage;
		public String totalpage;
		public String totalnum;
		public Object product;
		public String getCurrentpage() {
			return currentpage;
		}
		public void setCurrentpage(String currentpage) {
			this.currentpage = currentpage;
		}
		public String getTotalpage() {
			return totalpage;
		}
		public void setTotalpage(String totalpage) {
			this.totalpage = totalpage;
		}
		public String getTotalnum() {
			return totalnum;
		}
		public void setTotalnum(String totalnum) {
			this.totalnum = totalnum;
		}
		public Object getProduct() {
			return product;
		}
		public void setProduct(Object product) {
			this.product = product;
		}
		
	}
}

package com.netshop.entity;

import java.util.List;


public class OrderEntity extends BaseEntity {
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
		public Object order;
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
		public Object getOrder() {
			return order;
		}
		public void setOrder(Object order) {
			this.order = order;
		}
		
		
	}
}

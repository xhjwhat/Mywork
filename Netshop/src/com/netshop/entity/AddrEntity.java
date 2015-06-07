package com.netshop.entity;

import java.util.List;


public class AddrEntity extends BaseEntity {
	public AddrEntity() {

	}

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
		public Addr delivery;

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

		public Addr getDelivery() {
			return delivery;
		}

		public void setDelivery(Addr delivery) {
			this.delivery = delivery;
		}


	}

}

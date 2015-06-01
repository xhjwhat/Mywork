package com.netshop.entity;

import com.netshop.entity.ProductEntity.Lists;

public class CommentEntity extends BaseEntity{
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
		public Object evaluate;
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
		public Object getEvaluate() {
			return evaluate;
		}
		public void setEvaluate(Object evaluate) {
			this.evaluate = evaluate;
		}
		
		
	}
}

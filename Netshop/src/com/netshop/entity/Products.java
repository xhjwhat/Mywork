package com.netshop.entity;

import java.util.List;

public class Products extends BaseEntity{
	public Products(){
		
	}
	public List<Product> list;
	
	
	public List<Product> getList() {
		return list;
	}

	public void setList(List<Product> list) {
		this.list = list;
	}

	public class Product{
		public String pid;
		public String pname;
		public String price;
		public String pimg;
		public String weight;
		
		public Product(String pid,String pname,String price,String pimg,String weight){
			this.pid = pid;
			this.pname = pname;
			this.price = price;
			this.pimg = pimg;
			this.weight = weight;
		}
		
		
		public String getPid() {
			return pid;
		}
		public void setPid(String pid) {
			this.pid = pid;
		}
		public String getPname() {
			return pname;
		}
		public void setPname(String pname) {
			this.pname = pname;
		}
		public String getPrice() {
			return price;
		}
		public void setPrice(String price) {
			this.price = price;
		}
		public String getPimg() {
			return pimg;
		}
		public void setPimg(String pimg) {
			this.pimg = pimg;
		}
		public String getWeight() {
			return weight;
		}
		public void setWeight(String weight) {
			this.weight = weight;
		}
	}
	
}

package com.netshop.entity;

import java.io.Serializable;

public class ShopEntity extends BaseEntity{
	public Shop shop;
	
	public ShopEntity() {
	}
	
	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public static class Shop implements Serializable{
		public String id;
		public String name;
		public String img;
		public String address;
		public String phone;
		public String linkname;
		public String isnew;

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

		public String getImg() {
			return img;
		}

		public void setImg(String img) {
			this.img = img;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getLinkname() {
			return linkname;
		}

		public void setLinkname(String linkname) {
			this.linkname = linkname;
		}

		public String getIsnew() {
			return isnew;
		}

		public void setIsnew(String isnew) {
			this.isnew = isnew;
		}
	}
	

}

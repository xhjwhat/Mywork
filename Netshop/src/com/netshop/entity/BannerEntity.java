package com.netshop.entity;

import java.util.List;


public class BannerEntity extends BaseEntity {
	
	public List<Banner> list;
	
	public List<Banner> getLists() {
		return list;
	}

	public void setLists(List<Banner> list) {
		this.list = list;
	}

	public class Banner{
		public String id;
		public String img;
		public String url;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getImg() {
			return img;
		}
		public void setImg(String img) {
			this.img = img;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		
		
	}
}

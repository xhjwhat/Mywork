package com.netshop.entity;

import java.util.List;

public class PlantAreaEntity extends BaseEntity {
	public List<PlantArea> list;
	
	
	public List<PlantArea> getBean() {
		return list;
	}


	public void setBean(List<PlantArea> list) {
		this.list = list;
	}


	public class PlantArea{
		String id;
		String name;
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
		
	}
}

package com.netshop.entity;

import java.io.Serializable;

import android.support.mdroid.cache.CachedModel;
import android.support.mdroid.cache.ModelCache;

public class Product extends CachedModel{
	public Product() {

	}

	public String pid;
	public String pname;
	public String price;
	public String pimg;
	public String weight;
	
	public String amount;
	
	public String id;
	public String img;
	public String url;
	public String num = "1";


	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

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

	public String intro;
	public String desc;
	public String demo;

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getDesc() {
		return desc;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getDemo() {
		return demo;
	}

	public void setDemo(String demo) {
		this.demo = demo;
	}

	public Product(String pid, String pname, String price, String pimg,
			String weight) {
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

	@Override
	public String createKey(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean reloadFromCachedModel(ModelCache modelCache,
			CachedModel cachedModel) {
		Product product = (Product)cachedModel;
		this.pid=product.getPid();
		this.pimg = product.getPid();
		this.weight = product.getWeight();
		this.price = product.getPrice();
		this.pname = product.getPname();
		this.num = product.getNum();
		return false;
	}

}

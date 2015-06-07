package com.netshop.entity;

import android.support.mdroid.cache.CachedModel;
import android.support.mdroid.cache.ModelCache;

public class Account extends CachedModel{
	public String nickname;
	public String sex;
	public String realname;
	public String birth;
	public String telphone;
	public String area;
	public String crops;
	public String integrating;
	public String province;
	public String city;
	public String country;
	public String address;
	public String status;
	public String fixPhone;
	
	
	public Account(String id){
		super(id);
	}
	public Account(){}
	
	public String getFixPhone() {
		return fixPhone;
	}
	public void setFixPhone(String fixPhone) {
		this.fixPhone = fixPhone;
	}
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getCrops() {
		return crops;
	}

	public void setCrops(String crops) {
		this.crops = crops;
	}

	public String getIntegrating() {
		return integrating;
	}

	public void setIntegrating(String integrating) {
		this.integrating = integrating;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public boolean reloadFromCachedModel(ModelCache modelCache,
			CachedModel cachedModel) {
		Account info = (Account) cachedModel;
		nickname = info.getNickname();
		sex = info.getSex();
		realname = info.getRealname();
		birth = info.getBirth();
		address = info.getAddress();
		province = info.getProvince();
		city = info.getCity();
		country = info.getCountry();
		crops = info.getCrops();
		telphone = info.getTelphone();
		fixPhone = info.getFixPhone();
		integrating = info.getIntegrating();
		status = info.getStatus();
		area = info.getArea();
		return false;
	}

	@Override
	public String createKey(String id) {
		return "Account_" + id;
	}

}

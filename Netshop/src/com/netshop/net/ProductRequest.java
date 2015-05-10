package com.netshop.net;

import com.google.l99gson.Gson;
import com.netshop.entity.Products;

public class ProductRequest extends HttpRequest<Products> {

	@Override
	public String combineUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object pasreRespone(String json) {
		Gson gson = new Gson();
		Products product = gson.fromJson(json, Products.class);
		if(product != null){
			return product;
		}
		return null;
	}

}

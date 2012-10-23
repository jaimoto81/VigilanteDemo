package com.jmoto.android.test.model;

public class Product {
	
	
	public String id;
	public String name;
	public String desc;
	public double price;
	
	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Creates a product based on the code
	 * @param desc
	 */
	public Product(String code) {
		super();
		if(code==null){
			code = "No+Existe:-1";
		}	
		String[] res = code.split("[:]");
		if(res != null && res.length == 2){
			this.name  = res[0].replace("+" , " ");
			this.price = new Double(res[1]);
		}
		
	}

	
}

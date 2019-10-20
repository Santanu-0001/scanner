package com.bally;

public class alertRecord {
	private int id;
	private String barcode;
	private String price;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String val) {
		this.barcode = val;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String val) {
		this.price= val;
	}

	// Will be used by the ArrayAdapter in the ListView
	@Override
	public String toString() {
		return id + "/" + barcode + "/" + price + "/";
	}


}

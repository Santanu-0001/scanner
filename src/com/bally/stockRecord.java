package com.bally;

public class stockRecord {
	private int id;
	private String barcode;
	private String shopno;
	
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

	public String getShopno() {
		return shopno;
	}

	public void setShopno(String val) {
		this.shopno= val;
	}

	// Will be used by the ArrayAdapter in the ListView
	@Override
	public String toString() {
		return id + "/" + barcode + "/" + shopno + "/";
	}

}

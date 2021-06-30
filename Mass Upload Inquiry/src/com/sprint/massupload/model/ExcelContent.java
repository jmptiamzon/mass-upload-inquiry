package com.sprint.massupload.model;

public class ExcelContent {
	private String sku;
	private long qty;
	private long asurionAccept;
	private long okToAuction;
	private long omimQty;
	private String status;
	
	public ExcelContent() {
		sku = "";
		qty = 0;
		asurionAccept = 0;
		okToAuction = 0;
		omimQty = 0;
		status = "Inactive";
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public long getQty() {
		return qty;
	}

	public void setQty(long qty) {
		this.qty = qty;
	}

	public long getAsurionAccept() {
		return asurionAccept;
	}

	public void setAsurionAccept(long asurionAccept) {
		this.asurionAccept = asurionAccept;
	}

	public long getOkToAuction() {
		return okToAuction;
	}

	public void setOkToAuction(long okToAuction) {
		this.okToAuction = okToAuction;
	}

	public long getOmimQty() {
		return omimQty;
	}

	public void setOmimQty(long omimQty) {
		this.omimQty = omimQty;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}

package com.batch.entity;




public class Product {

	
	private Integer prodId;
	private String prodCode;
	private Double prodCost;
	
	private Double prodDisc;
	private Double prodGst;

	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getProdId() {
		return prodId;
	}

	public void setProdId(Integer prodId) {
		this.prodId = prodId;
	}

	public String getProdCode() {
		return prodCode;
	}

	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}

	public Double getProdCost() {
		return prodCost;
	}

	public void setProdCost(Double prodCost) {
		this.prodCost = prodCost;
	}

	public Double getProdDisc() {
		return prodDisc;
	}

	public void setProdDisc(Double prodDisc) {
		this.prodDisc = prodDisc;
	}

	public Double getProdGst() {
		return prodGst;
	}

	public void setProdGst(Double prodGst) {
		this.prodGst = prodGst;
	}

	@Override
	public String toString() {
		return "Product [prodId=" + prodId + ", prodCode=" + prodCode + ", prodCost=" + prodCost + ", prodDisc="
				+ prodDisc + ", prodGst=" + prodGst + "]";
	}
	
	
	
}

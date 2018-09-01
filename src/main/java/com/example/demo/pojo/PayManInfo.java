package com.example.demo.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class PayManInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long pid;
	
	private String name;
	
	private double tradeAmt;//订单金额
	
	private double actualAmt;//实际金额
	
	private String remarks;//备注
	
	private String tradeType;//支付类型
	
	private String accessPayNo;//接入商订单号
	
	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	
	public String getAccessPayNo() {
		return accessPayNo;
	}

	public void setAccessPayNo(String accessPayNo) {
		this.accessPayNo = accessPayNo;
	}


	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getTradeAmt() {
		return tradeAmt;
	}

	public void setTradeAmt(double tradeAmt) {
		this.tradeAmt = tradeAmt;
	}

	public double getActualAmt() {
		return actualAmt;
	}

	public void setActualAmt(double actualAmt) {
		this.actualAmt = actualAmt;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
	
}

package com.example.demo.pojo;

import java.util.Date;

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
	
	private Integer tradeAmt;//订单金额
	
	private Integer actualAmt;//实际金额
	
	private String remarks;//备注
	
	private String tradeType;//支付类型
	
	private String accessPayNo;//接入商订单号
	
	private String payNo;//平台订单号
	
	private String tradeStatus; //订单状态 1.成功 0.失败
	
	private Date payTime;//支付时间
	
	private String goodsName;//商品名称
	
//	private String kefu;//客服
	
//	public String getKefu() {
//		return kefu;
//	}
//
//	public void setKefu(String kefu) {
//		this.kefu = kefu;
//	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	
	public String getPayNo() {
		return payNo;
	}

	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}

	public String getTradeStatus() {
		return tradeStatus;
	}

	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
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

	public Integer getTradeAmt() {
		return tradeAmt;
	}

	public void setTradeAmt(Integer tradeAmt) {
		this.tradeAmt = tradeAmt;
	}

	public Integer getActualAmt() {
		return actualAmt;
	}

	public void setActualAmt(Integer actualAmt) {
		this.actualAmt = actualAmt;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
	
}

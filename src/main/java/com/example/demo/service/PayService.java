package com.example.demo.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.pojo.PayManInfo;
import com.example.demo.repository.PayRepository;

@Service
public class PayService {
	
	@Autowired
	PayRepository payRepository;

	public boolean savePay(PayManInfo payManInfo) {
		if(payRepository.save(payManInfo) != null) {
			return true;
		}else
			return false;
	}
	
	public boolean savePay1(String PayNo, Integer TradeAmt,Integer tActualAmt,
							String TradeStatus,Date PayTime,
						    String TradeType, String access_pay_no) {
		if(payRepository.saveadd(PayNo,TradeAmt,tActualAmt,TradeStatus,PayTime,TradeType,access_pay_no) == 1) {
			return true;
		}else
			return false;
	}
	
	public List<PayManInfo> showPayList(){
		return payRepository.findMyAll();
	}
	
	public List<PayManInfo> showmyPayList(String kefu,String days){
		return payRepository.findMyNewAll(kefu,days);
	}
	
	public List<PayManInfo> showPayListByKefu(String kefu){
		return payRepository.findMyAllByKefu(kefu);
	}


	public Map<String ,Object> isPay(String accessPayNo){
			Map<String,Object> map = new HashMap<>();
			PayManInfo ps = new PayManInfo();
			ps = payRepository.findByAccessPayNo(accessPayNo);
			if ("-99".equals(ps.getTradeStatus()))
					map.put("state", false);
			else
					map.put("state", true);
		System.out.println("==================================");
			return map;
	}

	public List<PayManInfo> showPayListByDays(String days) {
		return payRepository.showPayListByDays(days);
	}
}

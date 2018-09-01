package com.example.demo.service;

import java.util.List;

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
	
	public List<PayManInfo> showPayList(){
		return payRepository.findMyAll();
	}
}

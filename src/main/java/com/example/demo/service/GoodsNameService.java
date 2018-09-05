package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.pojo.GoodsName;
import com.example.demo.repository.GoodsNameRepository;

@Service
public class GoodsNameService {
	
	@Autowired
	GoodsNameRepository goodsNameRepository;
	
	public GoodsName findOne(Integer gid) {
		if(goodsNameRepository.existsById(1)) {
			return goodsNameRepository.findById(gid).get();
		}else
			return null;
	}
	
	public boolean saveGoodsName(GoodsName goodsName) {
		if(goodsNameRepository.save(goodsName) != null)
			return true;
		else
			return false;
	}
}

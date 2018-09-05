package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.pojo.GoodsName;
import com.example.demo.service.GoodsNameService;

@RestController
public class GoodsNameController {
	
	@Autowired
	GoodsNameService goodsNameService;

	@RequestMapping("/modifyName")
	public boolean modifyName(String count) {
		GoodsName goodsName = new GoodsName();
		goodsName.setGid(1);
		goodsName.setGoodsName(count);
	    if(goodsNameService.saveGoodsName(goodsName)) {
	    	return true;
	    }else
	    	return false;
	}
}

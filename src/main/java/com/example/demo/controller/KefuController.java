package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.pojo.GoodsName;
import com.example.demo.pojo.Kefu;
import com.example.demo.repository.KefuRepository;
import com.example.demo.service.GoodsNameService;
import com.example.demo.service.KefuService;

@RestController
public class KefuController {
	@Autowired
	KefuService kefuService;
	
	@Autowired
	KefuRepository kefuRepository;
	
	@Autowired
	GoodsNameService goodsNameService;
	
	@RequestMapping("/addkefu")
	public boolean addkefu(String name){
		if(kefuRepository.findByKname(name) != null)
			return false;
		Kefu kefu = new Kefu();
		kefu.setKname(name);
		return kefuService.save(kefu);
	}
	
	@RequestMapping("/showKefuList")
	public JSONObject showKefuList(Integer limit, Integer offset) {
		JSONObject jsonObject = new JSONObject();
		List<Kefu> KefuList = kefuService.showKefuList();
		int sie = limit * offset;
		List<Kefu> KefuList1 = new ArrayList<>();
		if (KefuList.size() > sie){
            System.out.println(limit+"========================="+offset);
            int k = KefuList.size()-sie;
            if (k > limit)
                for (int i = 0; i < limit;i++)
                	KefuList1.add(KefuList.get(i+sie));
            else
                for (int i = 0 ; i < k; i++)
                	KefuList1.add(KefuList.get(i+sie));
        }else{
            System.out.println(limit+"========================="+offset);
            int j = limit*(offset);
            int k = KefuList.size() - j;
            for (int i = 0 ; i < k ; i++)
            	KefuList1.add(KefuList.get(i+j));
        }
        jsonObject.put("total",KefuList.size());
        jsonObject.put("rows",KefuList1);
        
        return jsonObject;
	}	
	
	@RequestMapping("/deleteKefu")
	public boolean deleteKefu(Integer pid) {
		if(kefuRepository.deleteByMyId(pid) == 1) {
			return true;
		}else
			return false;
	}
	
	@RequestMapping("/getKefus")
	public Map<String,Object> getKefus() {
		Map<String,Object> map = new HashMap<>();
		List<Kefu> list = kefuService.showKefuList();
		GoodsName goodsName = goodsNameService.findOne(1);
		map.put("list", list);
		map.put("leng", list.size());
		map.put("title", goodsName.getGoodsName());
		return map;
	}
}

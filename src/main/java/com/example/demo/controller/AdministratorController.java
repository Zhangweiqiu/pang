package com.example.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.AdministratorService;


@RestController
public class AdministratorController {
	
	@Autowired
	AdministratorService administratorService;
	
	@RequestMapping("/login")
	public Map<String,Object> checklogin(String aid, String password){
		Map<String,Object> map = administratorService.checklogin(aid, password);
		return map;
	}
	
//	@RequestMapping("/addkefu")
//	public Map<String,Object> addkefu(String name){
//		Map<String,Object> map = administratorService.checklogin(aid, password);
//		return map;
//	}

}

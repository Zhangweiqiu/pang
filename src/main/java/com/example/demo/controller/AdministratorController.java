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
	public Map<String,Object> checklogin(String ucount, String password){
		Map<String,Object> map = administratorService.checklogin(ucount, password);
		return map;
	}

}

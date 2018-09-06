package com.example.demo.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.pojo.Administor;
import com.example.demo.pojo.Kefu;
import com.example.demo.repository.AdministratorRepository;
import com.example.demo.repository.KefuRepository;
import com.example.demo.service.AdministratorService;


@RestController
public class AdministratorController {
	
	@Autowired
	AdministratorService administratorService;
	
	@Autowired
	AdministratorRepository administratorRepository;
	
	@Autowired
	KefuRepository kefuRepository;
	
	@RequestMapping("/login")
	public Map<String,Object> checklogin(String aid, String password){
		Map<String,Object> map = administratorService.checklogin(aid, password);
		return map;
	}
	
	@RequestMapping("/addkefu")
	public boolean addkefu(String name){
		Kefu kefu = new Kefu();
		kefu.setKname(name);
		if(kefuRepository.save(kefu) != null) {
			return true;
		}else
			return false;
	}
	
	@RequestMapping("/addAdmin")
	public boolean addAdmin(String name,String aid, String password){
	    Administor administor = new Administor();
	    administor.setUname(name);
	    administor.setUcount(aid);
	    administor.setUpassword(password);
	    administor.setRole("normal");
	    if(administratorRepository.save(administor) != null) {
	    	return true;
		}else
			return false;
	}

}

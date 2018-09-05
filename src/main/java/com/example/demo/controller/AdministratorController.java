package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.pojo.Administor;
import com.example.demo.repository.AdministratorRepository;


@RestController
public class AdministratorController {
	
	@Autowired
	AdministratorRepository administratorRepository;
	
	@RequestMapping("/login")
	public Map<String,Object> checklogin(Integer aid, String password){
		Optional<Administor> administor = administratorRepository.findById(aid);
		Map<String,Object> map = new HashMap<>();
		if(administor.get() != null) {
			if(administor.get().getUpassword().equals(password)) {
				map.put("states", true);
				return map;
			}
		}
		map.put("states", false);
		return map;
	}

}

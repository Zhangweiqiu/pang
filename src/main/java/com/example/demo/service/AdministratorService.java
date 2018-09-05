package com.example.demo.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.pojo.Administor;
import com.example.demo.repository.AdministratorRepository;

@Service
public class AdministratorService {
	
	@Autowired
	AdministratorRepository administratorRepository;
	
	public Map<String,Object> checklogin(String ucount, String password){
		Optional<Administor> administor = administratorRepository.findByUcount(ucount);
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
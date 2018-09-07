package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.pojo.Administor;
import com.example.demo.repository.AdministratorRepository;
import com.example.demo.service.AdministratorService;


@RestController
public class AdministratorController {
	
	@Autowired
	AdministratorService administratorService;
	
	@Autowired
	AdministratorRepository administratorRepository;
	
	
	
	@RequestMapping("/login")
	public Map<String,Object> checklogin(String aid, String password,HttpSession session){
		Map<String,Object> map = administratorService.checklogin(aid, password,session);
		return map;
	}
	
	
	
	@RequestMapping("/addAdmin")
	public boolean addAdmin(String name,String aid){
	    Administor administor = new Administor();
	    if(administratorRepository.findByUcount(aid).isPresent()) {
	    	return false;
	    }
	    administor.setUname(name);
	    administor.setUcount(aid);
	    administor.setUpassword("123456");
	    administor.setRole("normal");
	    if(administratorRepository.save(administor) != null) {
	    	return true;
		}else
			return false;
	}
	
	@RequestMapping("/deleteAdmin")
	public boolean deleteAdmin(Integer uid) {
		if(administratorRepository.deleteByMyId(uid) == 1) {
			return true;
		}else
			return false;
	}
	
	@RequestMapping("/showAdminList")
	public JSONObject showAdminList(Integer limit, Integer offset) {
		JSONObject jsonObject = new JSONObject();
		List<Administor> AdminList = administratorService.showAdminList();
		int sie = limit * offset;
		List<Administor> AdminList1 = new ArrayList<>();
		if (AdminList.size() > sie){
            System.out.println(limit+"========================="+offset);
            int k = AdminList.size()-sie;
            if (k > limit)
                for (int i = 0; i < limit;i++)
                	AdminList1.add(AdminList.get(i+sie));
            else
                for (int i = 0 ; i < k; i++)
                	AdminList1.add(AdminList.get(i+sie));
        }else{
            System.out.println(limit+"========================="+offset);
            int j = limit*(offset);
            int k = AdminList.size() - j;
            for (int i = 0 ; i < k ; i++)
            	AdminList1.add(AdminList.get(i+j));
        }
        jsonObject.put("total",AdminList.size());
        jsonObject.put("rows",AdminList1);
        
        return jsonObject;
	}	
	
	

}

package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.pojo.Kefu;
import com.example.demo.repository.KefuRepository;

@Service
public class KefuService {
	
	@Autowired
	KefuRepository kefuRepository;
	
	public boolean save(Kefu kefu) {
		if(kefuRepository.save(kefu) != null) {
			return true;
		}else
			return false;
	}

	public List<Kefu> showKefuList() {
		List<Kefu> KefuList = new ArrayList();
	    Iterable<Kefu> KefuIterable = kefuRepository.findAll();
	    KefuIterable.forEach(single ->{KefuList.add(single);});
		return KefuList;
	}
	

}

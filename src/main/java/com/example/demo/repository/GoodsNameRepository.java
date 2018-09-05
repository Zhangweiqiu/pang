package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.demo.pojo.GoodsName;


public interface GoodsNameRepository extends CrudRepository<GoodsName,Integer>{
	
	
}

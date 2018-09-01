package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.demo.pojo.GoodsName;
import com.example.demo.pojo.PayManInfo;


public interface PayRepository extends CrudRepository<PayManInfo,Long>{
	@Modifying
	@Query(nativeQuery = true,value = "SELECT   *   FROM   paymaninfo ")  
	List<PayManInfo> findMyAll();
}

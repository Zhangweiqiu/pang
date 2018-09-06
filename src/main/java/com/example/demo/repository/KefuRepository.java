package com.example.demo.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.demo.pojo.Kefu;


public interface KefuRepository extends CrudRepository<Kefu,Integer>{

	@Modifying
	@Transactional
	@Query(nativeQuery = true,value = "delete from kefu where kid=?1")
	Integer deleteByMyId(Integer kid);

	Kefu findByKname(String name);

}

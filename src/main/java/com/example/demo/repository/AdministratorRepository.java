package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.demo.pojo.Administor;


public interface AdministratorRepository  extends CrudRepository<Administor,Integer>{

	Optional<Administor> findByUcount(String ucount);

	@Modifying
	@Transactional
	@Query(nativeQuery = true,value = "delete from administor where uid=?1")
	Integer deleteByMyId(Integer uid);

	@Modifying
	@Query(nativeQuery = true,value = "SELECT   *   FROM   administor where role <> 'super' ")  
	List<Administor> findMyAll();

}

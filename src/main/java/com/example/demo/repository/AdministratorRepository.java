package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.pojo.Administor;


public interface AdministratorRepository  extends CrudRepository<Administor,Integer>{

	Optional<Administor> findByUcount(String ucount);

}

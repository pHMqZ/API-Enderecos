package com.pms.dio.gof.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pms.dio.gof.model.Endereco;

@Repository
public interface EnderecoRepository extends CrudRepository<Endereco, String>{
	

}

package com.barbearia.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.barbearia.model.Prestador;

import java.util.List;

@Repository
public interface PrestadorRepository extends CrudRepository<Prestador, String>{
	public List<Prestador> findAll();
	public Prestador findByCpf(String cpf);
	public Prestador deleteByCpf(String cpf);
	public void deleteAll();
}

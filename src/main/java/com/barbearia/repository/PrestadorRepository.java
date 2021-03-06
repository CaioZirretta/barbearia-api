package com.barbearia.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.barbearia.model.Prestador;

import java.util.List;

import javax.transaction.Transactional;

@Repository
public interface PrestadorRepository extends CrudRepository<Prestador, String> {
	public List<Prestador> findAll();

	public Prestador findByCpf(String cpf);

	@Query("SELECT p FROM Prestador p WHERE p.endereco.cep = :codigoPostal ORDER BY p.cpf")
	public List<Prestador> findAllByPostal(String codigoPostal);
	
	@Transactional
	@Modifying
	public void deleteByCpf(String cpf);
}

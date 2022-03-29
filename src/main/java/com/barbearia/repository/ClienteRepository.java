package com.barbearia.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.barbearia.model.Cliente;
import java.util.List;

import javax.transaction.Transactional;

@Repository
public interface ClienteRepository extends CrudRepository<Cliente, String> {
	public List<Cliente> findAll();

	public Cliente findByCpf(String cpf);

	@Transactional
	@Modifying
	public void deleteByCpf(String cpf);
	
	@Transactional
	@Modifying
	public void deleteAll();

}

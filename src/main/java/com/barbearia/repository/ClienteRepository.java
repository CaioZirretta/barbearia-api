package com.barbearia.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.barbearia.model.Cliente;
import java.util.List;


@Repository
public interface ClienteRepository extends CrudRepository<Cliente, String> {
	public List<Cliente> findAll();
	public Cliente findByCpf(String cpf);
	public Cliente deleteByCpf(String cpf);
	public void deleteAll();
}

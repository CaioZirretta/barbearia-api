package com.barbearia.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.barbearia.model.Agendamento;
import java.util.List;

@Repository
public interface AgendamentoRepository extends CrudRepository<Agendamento, Long>{
	public List<Agendamento> findAll(); 
	public Agendamento findByCpfCliente(String cpf);
	public Agendamento findByCpfPrestador(String cpf);
	public void deleteAll();
}
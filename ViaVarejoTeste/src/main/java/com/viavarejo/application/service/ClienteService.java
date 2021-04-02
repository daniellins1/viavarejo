
package com.viavarejo.application.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viavarejo.application.model.Cliente;
import com.viavarejo.application.repository.ClienteRepository;

@Service
public class ClienteService {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(ClienteService.class);

	@Autowired
	private ClienteRepository repository;

	public Cliente salvar(Cliente cliente) {
		return repository.save(cliente);
	}

	public List<Cliente> findAll() {
		return repository.findAll();
	}

	public Optional<Cliente> findById(String id) {
		LOGGER.info("Procurando cliente pelo ID: {}",id);
		return repository.findById(id);
	}

	public void delete(String id) {
		repository.deleteById(id);
	}

	public void deleteAll() {
		repository.deleteAll();
	}
}

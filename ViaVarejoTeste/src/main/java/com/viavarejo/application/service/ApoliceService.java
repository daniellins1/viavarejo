
package com.viavarejo.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viavarejo.application.model.Apolice;
import com.viavarejo.application.repository.ApoliceRepository;

@Service
public class ApoliceService {

	@Autowired
	private ApoliceRepository repository;
	
	public Apolice salvar(Apolice apolice) {
		return repository.save(apolice);
	}

	public List<Apolice> findAll() {
		return repository.findAll();
	}

	public Optional<Apolice> findById(String id) {
		return repository.findById(id);
	}

	public void delete(String id) {
		repository.deleteById(id);
	}

	public void deleteAll() {
		repository.deleteAll();
	}
}

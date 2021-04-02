
  package com.viavarejo.application.repository;
  
  import org.springframework.data.mongodb.repository.MongoRepository;

import com.viavarejo.application.model.Cliente;
  
  public interface ClienteRepository extends MongoRepository<Cliente, String> {
  
  }
 
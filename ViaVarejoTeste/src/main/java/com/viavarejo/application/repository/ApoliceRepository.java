
  package com.viavarejo.application.repository;
  
  import org.springframework.data.mongodb.repository.MongoRepository;

import com.viavarejo.application.model.Apolice;
  
  public interface ApoliceRepository extends MongoRepository<Apolice, String> {
  
  }
 
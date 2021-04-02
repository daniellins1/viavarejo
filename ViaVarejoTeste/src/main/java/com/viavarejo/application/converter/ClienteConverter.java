package com.viavarejo.application.converter;

import java.util.ArrayList;
import java.util.List;

import com.viavarejo.application.dto.ClienteDTO;
import com.viavarejo.application.model.Cliente;

public class ClienteConverter {
	
	public static ClienteConverter getInstance() {
		return new ClienteConverter();
	}
	
	public ClienteDTO converter(Cliente cliente) {
		ClienteDTO clienteDTO = new ClienteDTO();
		clienteDTO.setCidade(cliente.getCidade());
		clienteDTO.setCpf(cliente.getCpf());
		clienteDTO.setId(cliente.getId());
		clienteDTO.setNome(cliente.getNome());
		clienteDTO.setUf(cliente.getUf());
		
		return clienteDTO;
	}
	
	public List<ClienteDTO> converterList(List<Cliente> clientes) {
		List<ClienteDTO> clientesDTO = new ArrayList<>();
		
		for (Cliente cliente : clientes) {
			clientesDTO.add(converter(cliente));
		}
		
		return clientesDTO;
	}
}

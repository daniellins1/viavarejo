package com.viavarejo.application.converter;

import java.util.ArrayList;
import java.util.List;

import com.viavarejo.application.dto.ApoliceDTO;
import com.viavarejo.application.model.Apolice;
import com.viavarejo.application.service.ClienteService;

public class ApoliceConverter {
	
	public static ApoliceConverter getInstance() {
		return new ApoliceConverter();
	}
	
	public ApoliceDTO converter(Apolice apolice) {
		ApoliceDTO apoliceDTO = new ApoliceDTO();
		
		apoliceDTO.setDataFimVigencia(apolice.getDataFimVigencia());
		apoliceDTO.setDataInicioVigencia(apolice.getDataInicioVigencia());
		apoliceDTO.setNumero(apolice.getNumero());
		apoliceDTO.setPlacaVeiculo(apolice.getPlacaVeiculo());
		apoliceDTO.setValor(apolice.getValor());
		
		return apoliceDTO;
	}
	
	public List<ApoliceDTO> converterList(List<Apolice> apolices, ClienteService clienteService) {
		List<ApoliceDTO> apolicesDTO = new ArrayList<>();
		
		for (Apolice apolice : apolices) {
			System.out.println("Convertendo apolices");
			apolicesDTO.add(converter(apolice));
		}
		
		return apolicesDTO;
	}
}

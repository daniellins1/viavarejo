
package com.viavarejo.application.model;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "apolice")
public class Apolice {

	@Id
	private String numero;

	@NotNull(message = "Data Inicio Vigencia é obrigatório")
	private String dataInicioVigencia;

	@NotNull(message = "Data Fim Vigencia é obrigatório")
	private String dataFimVigencia;

	@NotNull(message = "Placa de Veiculo é obrigatório")
	private String placaVeiculo;

	@NotNull(message = "Valor do Seguro é obrigatório")
	private String valor;

	@NotNull(message = "ID do Cliente é obrigatório")
	private String clienteID;
	
	public Apolice(@NotNull String dataInicioVigencia, @NotNull String dataFimVigencia,
			@NotNull String placaVeiculo, @NotNull String valor, @NotNull String clienteID) {
		this.dataInicioVigencia = dataInicioVigencia;
		this.dataFimVigencia = dataFimVigencia;
		this.placaVeiculo = placaVeiculo;
		this.valor = valor;
		this.clienteID = clienteID;
	}
	
	public Apolice() {
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getDataInicioVigencia() {
		return dataInicioVigencia;
	}

	public void setDataInicioVigencia(String dataInicioVigencia) {
		this.dataInicioVigencia = dataInicioVigencia;
	}

	public String getDataFimVigencia() {
		return dataFimVigencia;
	}

	public void setDataFimVigencia(String dataFimVigencia) {
		this.dataFimVigencia = dataFimVigencia;
	}

	public String getPlacaVeiculo() {
		return placaVeiculo;
	}

	public void setPlacaVeiculo(String placaVeiculo) {
		this.placaVeiculo = placaVeiculo;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getClienteID() {
		return clienteID;
	}

	public void setClienteID(String clienteID) {
		this.clienteID = clienteID;
	}
	
	


}

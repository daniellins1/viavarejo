package com.viavarejo.application.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class ApoliceDTO {

	private String numero;

	private String dataInicioVigencia;

	private String dataFimVigencia;
	
	private String placaVeiculo;

	private String valor;

	private ClienteDTO cliente;
	
	public String getVencimento() {
		return retornaVencimento(this.dataFimVigencia);
	}
	
	public static long getDiasVencidos(LocalDate dataVencimento, boolean jaVenceu) {
		LocalDate dataAtual = LocalDate.now();
		if (jaVenceu) {
			return ChronoUnit.DAYS.between(dataVencimento, dataAtual);
		} else {
			return ChronoUnit.DAYS.between(dataAtual, dataVencimento);
		}
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

	public ClienteDTO getCliente() {
		return cliente;
	}

	public void setCliente(ClienteDTO cliente) {
		this.cliente = cliente;
	}

	public String getPrazoVencimento() {
		return retornaDiasVencimento(this.dataFimVigencia);
	}
	
	public String retornaVencimento(String dataFimVigencia) {
		LocalDate dataAtual = LocalDate.now();
		LocalDate dataVencimento = retornData(dataFimVigencia);
		
		if (dataVencimento.isBefore(dataAtual)) {
			return "Apólice vencida";
		} else if (dataVencimento.isAfter(dataAtual)){
			return "Apólice não vencida";
		} else {
			return "Apólice vence hoje";
		}
	}
	
	public String retornaDiasVencimento(String dataFimVigencia) {
		LocalDate dataAtual = LocalDate.now();
		LocalDate dataVencimento = retornData(dataFimVigencia);
		
		if (dataVencimento.isBefore(dataAtual)) {
			long diasVencida = getDiasVencidos(dataVencimento, true);
			return "Vencida a " + diasVencida + " dia(s)";
		} else if (dataVencimento.isAfter(dataAtual)){
			long diasParaVencer = getDiasVencidos(dataVencimento, false);
			return "Vai vencer em " + diasParaVencer + " dia(s)";
		} else {
			return "Apólice vence hoje";
		}
	}
	
	public LocalDate retornData(String dataFimVigencia) {
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy"); 
		Date fimVigenciaDate = null;
		try {
			fimVigenciaDate = formato.parse(dataFimVigencia);
		} catch (ParseException e) {
			return LocalDate.now();
		}
		
		return fimVigenciaDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}
}

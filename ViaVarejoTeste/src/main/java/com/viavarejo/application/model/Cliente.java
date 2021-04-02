
package com.viavarejo.application.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Document(collection = "cliente")
public class Cliente {

	@Id
	private String id;

	@NotNull(message = "Nome é obrigatório")
	private String nome;

	@NotNull(message = "CPF é obrigatório")
	@Indexed(unique = true)
	private String cpf;

	@NotNull(message = "Cidade é obrigatório")
	private String cidade;

	@NotNull(message = "UF é obrigatório")
	private String uf;

	public Cliente(@NotNull String nome, @NotNull String cpf, @NotNull String cidade, @NotNull String uf) {
		this.nome = nome;
		this.cpf = cpf;
		this.cidade = cidade;
		this.uf = uf;
	}
	
	public Cliente() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

}

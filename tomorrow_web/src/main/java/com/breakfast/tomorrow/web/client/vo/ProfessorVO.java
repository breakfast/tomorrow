package com.breakfast.tomorrow.web.client.vo;

import java.io.Serializable;

public class ProfessorVO implements Serializable{
	
	private String idPessoa;
	private String nome;
	private String telefone;
	private String celular;
	private String endereco;
	private String numeroEndereco;
	private String complemento;
	private String distrito;
	private String cidade;
	private String codigoPostal;
	private String profissao;
	private String observacao;
	private String email;

	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getIdPessoa() {
		return idPessoa;
	}



	public void setIdPessoa(String idPessoa) {
		this.idPessoa = idPessoa;
	}



	public String getNome() {
		return nome;
	}



	public void setNome(String nome) {
		this.nome = nome;
	}



	public String getTelefone() {
		return telefone;
	}



	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}



	public String getCelular() {
		return celular;
	}



	public void setCelular(String celular) {
		this.celular = celular;
	}



	public String getEndereco() {
		return endereco;
	}



	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}



	public String getNumeroEndereco() {
		return numeroEndereco;
	}



	public void setNumeroEndereco(String numeroEndereco) {
		this.numeroEndereco = numeroEndereco;
	}



	public String getComplemento() {
		return complemento;
	}



	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}



	public String getDistrito() {
		return distrito;
	}



	public void setDistrito(String distrito) {
		this.distrito = distrito;
	}



	public String getCidade() {
		return cidade;
	}



	public void setCidade(String cidade) {
		this.cidade = cidade;
	}



	public String getCodigoPostal() {
		return codigoPostal;
	}



	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}



	public String getProfissao() {
		return profissao;
	}



	public void setProfissao(String profissao) {
		this.profissao = profissao;
	}



	public String getObservacao() {
		return observacao;
	}



	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}


	private static final long serialVersionUID = 5428096194969701895L;
	
}

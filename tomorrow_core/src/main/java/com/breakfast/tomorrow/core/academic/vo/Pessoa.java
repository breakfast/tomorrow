package com.breakfast.tomorrow.core.academic.vo;

import java.io.Serializable;

import com.breakfast.tomorrow.core.database.FieldNode;
import com.breakfast.tomorrow.core.database.IdNode;
import com.breakfast.tomorrow.core.database.IndexNode;

public class Pessoa implements Serializable{

	
	@IdNode private Long id;
	@IndexNode private String nome;
	//@IndexNode private String sobreNome;
	@IndexNode private String email;
	@FieldNode private String telefone;
	@FieldNode private String celular;
	@FieldNode private String endereco;
	@FieldNode private int numeroEndereco;
	@FieldNode private String complemento;
	@FieldNode private String distrito;
	@FieldNode private String cidade;
	@FieldNode private String codigoPostal;
	@FieldNode private String profissao;
	@FieldNode private String observacao;
	@FieldNode private String diretorioFoto;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	/*
	public String getSobreNome() {
		return sobreNome;
	}
	public void setSobreNome(String sobreNome) {
		this.sobreNome = sobreNome;
	}
	*/
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public int getNumeroEndereco() {
		return numeroEndereco;
	}
	public void setNumeroEndereco(int numeroEndereco) {
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
	public String getDiretorioFoto() {
		return diretorioFoto;
	}
	public void setDiretorioFoto(String diretorioFoto) {
		this.diretorioFoto = diretorioFoto;
	}
	
	private static final long serialVersionUID = 1L;
	
}

package com.breakfast.base;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.index.Index;


import com.breakfast.tomorrow.core.database.DataBase;
import com.breakfast.tomorrow.core.database.EntityRelashionship;

/**
 * Classe Abstrata represntativa de uma pessoa.
 * Nesta classe, contém atributos genericos de pessoas
 * @author kleberilario@gmail.com
 *
 */
public abstract class Pessoa {
	
	public Pessoa(){}
	
	/**
	 * Constants for indexes fields
	 */
	public final static String INDEX_NOME_PESSOA = "nomePessoa";
	public final static String INDEX_SOBRENOME_PESSOA = "sobreNomePessoa";
	public final static String INDEX_PESSOA_ID = "pessoaId";
	
	
	/**
	 * Constants for fields
	 */
	public final static String ID_PESSOA = "idPessoa";
	public final static String NOME = "nome";
	public final static String TELEFONE = "telefone";
	public final static String CELULAR = "celular";
	public final static String ENDERECO = "endereco";
	public final static String NUMERO_ENDERECO = "numeroEndereco";
	public final static String COMPLEMENTO = "complemento";
	public final static String DISTRITO = "distrito";
	public final static String CIDADE = "cidade";
	public final static String CODIGO_POSTAL = "codigoPostal";
	public final static String PROFISSAO = "profissao";
	public final static String OBSERVACAO = "observacao";
	public final static String EMAIL = "email";
	public final static String FOTO_SOURCE = "fotoSource";
	
	
	/**
	 * fields of class
	 */
	private long idPessoa;
	private String nome;
	private String telefone;
	private String celular;
	private String endereco;
	private int numeroEndereco;
	private String complemento;
	private String distrito;
	private String cidade;
	private String codigoPostal;
	private String profissao;
	private String observacao;
	private String email;
	private String fotoSource;
	

	/**
	 * Prepare Node creating index for the class entity and setter properties in the node.
	 * this method used by persist elements in graph data base
	 * The node, can exist loaded from database or can it created for new node. 
	 * Note : The SubClasses  
	 * @param node
	 */
	protected void prepareNode(Node node){
		
		Utils.setNodeProperty(node,ID_PESSOA,this.idPessoa);
		Utils.setNodeProperty(node,NOME,this.nome);
		Utils.setNodeProperty(node,TELEFONE, this.telefone);
		Utils.setNodeProperty(node,CELULAR, this.celular);
		Utils.setNodeProperty(node,ENDERECO, this.endereco);
		Utils.setNodeProperty(node,NUMERO_ENDERECO, this.numeroEndereco);
		Utils.setNodeProperty(node,COMPLEMENTO, this.complemento);
		Utils.setNodeProperty(node,DISTRITO, this.distrito);
		Utils.setNodeProperty(node,CIDADE, this.cidade);
		Utils.setNodeProperty(node,CODIGO_POSTAL, this.codigoPostal);
		Utils.setNodeProperty(node,PROFISSAO, this.profissao);
		Utils.setNodeProperty(node,OBSERVACAO, this.observacao);
		Utils.setNodeProperty(node,EMAIL, this.email);
		Utils.setNodeProperty(node,FOTO_SOURCE, this.fotoSource);
		
	}
	
	/**
	 * Prepare Index for current values, if Node different of the null, remove a index for node.
	 * Use this method in subclasses if persist
	 * @param node
	 */
	protected void prepareIndex(Node node){
		Index<Node> names = DataBase.get().index().forNodes(INDEX_NOME_PESSOA);
		Index<Node> ids = DataBase.get().index().forNodes(INDEX_PESSOA_ID);
		if(node != null){
			names.remove(node, NOME, node.getProperty(NOME));
			ids.remove(node, ID_PESSOA, node.getProperty(ID_PESSOA));
		}
	}
	
	
	/***
	 * Add Index at creating new Node.
	 * Use this class in subclass method if persist;
	 * @param node
	 */
	protected void addIndex(Node node){
		Index<Node> names = DataBase.get().index().forNodes(INDEX_NOME_PESSOA);
		Index<Node> ids = DataBase.get().index().forNodes(INDEX_PESSOA_ID);
		names.add(node, NOME, node.getProperty(NOME));
		ids.add(node, ID_PESSOA, node.getProperty(ID_PESSOA));
		//TODO verificar se ao atualizar o node, ou seja, o ALuno, ver se ele estah criando outra relacao
		DataBase.get().getReferenceNode().createRelationshipTo(node, EntityRelashionship.ALUNOS);
	}
	
	protected void removeIndex(Node node){
		Index<Node> nomes = DataBase.get().index().forNodes(INDEX_NOME_PESSOA);
		Index<Node> ids = DataBase.get().index().forNodes(INDEX_PESSOA_ID);
		nomes.remove(node, NOME, node.getProperty(NOME));
		ids.remove(node, ID_PESSOA, node.getProperty(ID_PESSOA));
	}
	
	
	/**
	 * Graph Node
	 */
	protected Node node;
	
	/**
	 * Default Constructor for People 
	 */
	public Pessoa(Pessoa pessoa){
		
	}
	
	/**
	 * Constructor for people with Node argument, used in get object in database
	 * @param node
	 */
	public Pessoa(Node node) {
		this.node = node;
	}
	
	public long getIdPessoa() {
		this.idPessoa = node != null ? ((Long) node.getProperty(ID_PESSOA)).longValue() : this.idPessoa;
		return this.idPessoa;
	}

	public void setIdPessoa(long id) {
		this.idPessoa = id;
	}

	public String getNome() {
		this.nome = node != null ? (String) node.getProperty(NOME) : this.nome;
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		this.telefone = node != null ? (String) node.getProperty(TELEFONE) : this.telefone;
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCelular() {
		this.celular = node != null ? (String) node.getProperty(CELULAR) : this.celular;
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getEndereco() {
		this.endereco = node != null ? (String) node.getProperty(ENDERECO) : this.endereco;
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public Integer getNumeroEndereco() {
		this.numeroEndereco = node != null ? (Integer) node.getProperty(NUMERO_ENDERECO) : this.numeroEndereco;
		return numeroEndereco;
	}

	public void setNumeroEndereco(Integer numeroEndereco) {
		this.numeroEndereco = numeroEndereco;
	}

	public String getComplemento() {
		this.complemento = node != null ? (String) node.getProperty(COMPLEMENTO) : this.complemento;
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getDistrito() {
		this.distrito = node != null ? (String) node.getProperty(DISTRITO) : this.distrito;
		return distrito;
	}

	public void setDistrito(String distrito) {
		this.distrito = distrito;
	}

	public String getCidade() {
		this.cidade = node != null ? (String) node.getProperty(CIDADE) : this.cidade;
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getCodigoPostal() {
		this.codigoPostal = node != null ? (String) node.getProperty(CODIGO_POSTAL) : this.codigoPostal;
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getProfissao() {
		this.profissao = node != null ? (String) node.getProperty(PROFISSAO) : this.profissao;
		return profissao;
	}

	public void setProfissao(String profissao) {
		this.profissao = profissao;
	}

	public String getObservacao() {
		this.observacao= node != null ? (String) node.getProperty(OBSERVACAO) : this.observacao;
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	public String getEmail() {
		this.email= node != null ? (String) node.getProperty(EMAIL) : this.email;
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getFotoSource() {
		this.fotoSource = node != null ? (String) node.getProperty(FOTO_SOURCE) : this.fotoSource;
		return fotoSource;
	}

	public void setFotoSource(String fotoSource) {
		this.fotoSource = fotoSource;
	}

	public Node getNode() {
		return this.node;
	}
	
	
}

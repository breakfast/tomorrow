package com.breakfast.base;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;
import com.breakfast.tomorrow.core.database.DataBase;

/**
 * Classe Represenativa de um usuario no sistema pra controle de acessos 
 * @author Maicon
 *
 */
public class Usuario{

	private static Logger LOG = Logger.getLogger(Usuario.class);
	
	/**
	 * Constant for index EmailUser field
	 */
	public final static String INDEX_EMAIL_USUARIO = "emailUsuario";

	/**
	 * Constants for fields
	 */
	public final static String EMAIL_USUARIO = "emailUsuario";
	public final static String SENHA = "senha";
	public final static String TIPO = "tipo";

	/**
	 * fields of class
	 */
	private String emailUsuario;
	private String senha;
	
	/**
	 * String que contem o classname da classe representaviba que utiliza o sistema
	 * ex:Alunos, Professores, Diretores etc.. utilizam o sistema 
	 * para o Aluno, o tipo de usuario guarda<code>Aluno.class</code>
	 *Classes devem implementar Login
	 */
	private String tipo;

	/**
	 * Prepare Node creating index for the class entity and setter properties in
	 * the node. this method used by persist elements in graph data base The
	 * node, can exist loaded from database or can it created for new node. Note
	 * : The SubClasses
	 * 
	 * @param node
	 */
	protected void prepareNode(Node node) {

		Utils.setNodeProperty(node, EMAIL_USUARIO, this.emailUsuario);
		Utils.setNodeProperty(node, SENHA, this.senha);
		Utils.setNodeProperty(node, TIPO, this.tipo);

	}

	/**
	 * Prepare Index for current values, if Node different of the null, remove a
	 * index for node. Use this method in subclasses if persist
	 * 
	 * @param node
	 */

	protected void prepareIndex(Node node) {
		Index<Node> emails = DataBase.get().index().forNodes(INDEX_EMAIL_USUARIO);

		if (node != null) {
			emails.remove(node, EMAIL_USUARIO, node.getProperty(EMAIL_USUARIO));
		}
	}

	/***
	 * Add Index at creating new Node. Use this class in subclass method if
	 * persist;
	 * 
	 * @param node
	 */

	protected void addIndex(Node node) {
		Index<Node> emails = DataBase.get().index().forNodes(INDEX_EMAIL_USUARIO);

		emails.add(node, EMAIL_USUARIO, this.getEmailUsuario());

	}

	protected void removeIndex(Node node) {
		Index<Node> emails = DataBase.get().index().forNodes(INDEX_EMAIL_USUARIO);
		emails.remove(node,EMAIL_USUARIO, node.getProperty(EMAIL_USUARIO));

	}

	/**
	 * Graph Node
	 */
	protected Node node;

	/**
	 * Default Constructor for User
	 */
	public Usuario() {
	}

	/**
	 * Constructor for people with Node argument, used in get object in database
	 * 
	 * @param node
	 */

	public Usuario(Node node) {
		this.node = node;
	}

	/**
	 * Get and Set an node
	 * 
	 * @param node
	 */

	public String getEmailUsuario() {
		this.emailUsuario = node != null ? (String) node.getProperty(EMAIL_USUARIO)
				: this.emailUsuario;
		return this.emailUsuario;
	}

	public void setEmailUsuario(String emailUsuario) {
		this.emailUsuario = emailUsuario;
	}

	public String getSenha() {
		this.senha = node != null ? (String) node.getProperty(SENHA)
				: this.senha;
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getTipo() {
		this.tipo = node != null ? (String) node.getProperty(TIPO) : this.tipo;
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public void persist(Usuario user) {
		String info = "NOT A INFO";
		boolean hasNode = user.node != null;
		Transaction tx = DataBase.get().beginTx();
		user.prepareIndex(user.node);

		try {
			Node node;
			if (hasNode) {
				node = user.node;
				info = Utils.UPDATED;
			} else {
				node = DataBase.get().createNode();
				info = Utils.PESISTED;
			}
			user.prepareNode(node);
			user.addIndex(node);
			tx.success();
			LOG.info(user.toString() + info);
		} catch (Exception e) {
			tx.failure();
			LOG.error("Erro at Persist Student", e);
		} finally {
			tx.finish();
		}

	}

	public static void delete(Usuario user) {
		Transaction transaction = DataBase.get().beginTx();
		try {
			String info = user.toString();
			Utils.deleteRelantionShips(user.node);
			user.removeIndex(user.node);
			user.node.delete();
			transaction.success();
			LOG.info(info + Utils.DELETED);
		} catch (Exception e) {
			transaction.failure();
			LOG.error("Erro at Delete Student", e);
		}
		transaction.finish();
	}

}

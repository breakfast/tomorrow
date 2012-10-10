package com.breakfast.tomorrow.core.database;

/**
 * Exceção a ser tratada pela camada de serviço ao acessar o modelo
 * Desta maneira, é possivel o serviço capturar o erro.
 * @author administrador
 *
 */
public class RepositoryException extends RuntimeException{
	
	public RepositoryException(String message) {
		super(message);
	}
	
	public RepositoryException(Throwable throwable){
		super(throwable);
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5580536145468981043L;

}

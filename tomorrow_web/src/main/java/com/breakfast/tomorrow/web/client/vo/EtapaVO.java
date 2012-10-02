package com.breakfast.tomorrow.web.client.vo;

import java.util.Date;


public class EtapaVO {
	
	private String nomeEtapa;
	private Date inicioEtapa;
	private Date fimEtapa;
	private int indice;
	
	public String getNomeEtapa() {
		return nomeEtapa;
	}
	public void setNomeEtapa(String nomeEtapa) {
		this.nomeEtapa = nomeEtapa;
	}
	public Date getInicioEtapa() {
		return inicioEtapa;
	}
	public void setInicioEtapa(Date inicioEtapa) {
		this.inicioEtapa = inicioEtapa;
	}
	public Date getFimEtapa() {
		return fimEtapa;
	}
	public void setFimEtapa(Date fimEtapa) {
		this.fimEtapa = fimEtapa;
	}
	public int getIndice() {
		return indice;
	}
	public void setIndice(int indice) {
		this.indice = indice;
	}
	
	
	

}

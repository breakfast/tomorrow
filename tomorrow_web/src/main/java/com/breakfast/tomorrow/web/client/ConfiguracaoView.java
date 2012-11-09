package com.breakfast.tomorrow.web.client;

import java.util.ArrayList;
import java.util.Collection;

import com.breakfast.gwt.user.client.OptionPanel;
import com.breakfast.tomorrow.core.academic.vo.Configuracao;
import com.breakfast.tomorrow.core.academic.vo.Professor;
import com.breakfast.tomorrow.web.client.ProfessorView.Driver;
import com.breakfast.tomorrow.web.client.async.ConfiguracaoService;
import com.breakfast.tomorrow.web.client.async.ConfiguracaoServiceAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class ConfiguracaoView extends Composite implements Editor<Configuracao> {

	private static ConfiguracaoViewUiBinder uiBinder = GWT.create(ConfiguracaoViewUiBinder.class);
	interface ConfiguracaoViewUiBinder extends UiBinder<Widget, ConfiguracaoView> {}
	interface Driver extends SimpleBeanEditorDriver<Configuracao,ConfiguracaoView>{}
	private ConfiguracaoServiceAsync service = GWT.create(ConfiguracaoService.class);
	private Driver driver = GWT.create(Driver.class);
	
	Configuracao bean = new Configuracao();
	Collection<Configuracao> listBean = new ArrayList<Configuracao>();
	
	public ConfiguracaoView() {
		initWidget(uiBinder.createAndBindUi(this));
		registerTab();
		driver.initialize(this);
		driver.edit(bean);
		confiAvancado.setVisible(false);
		
		
	}
	
	@UiField Button tabCadastro;
	@UiField Button tabLista;
	@UiField HTMLPanel cadastro;
	@UiField HTMLPanel confiAvancado;
	
	@UiField @Path("stringId") TextBox id;
	@UiField @Path("email") TextBox email;
	@UiField @Path("senha") TextBox senha;
	@UiField @Path("localBanco") TextBox localBanco;
	@UiField @Path("localBackup") TextBox localBachup;
	
	@Override
	protected void onLoad() {
		super.onLoad();
		service.lista(new AsyncCallback<Collection<Configuracao>>() {
			@Override
			public void onFailure(Throwable caught) {
			}

			@Override
			public void onSuccess(Collection<Configuracao> result) {
				for(Configuracao c : result){
					bean = c;
					driver.edit(bean);
					break;
				}
			}
		});
	}
	
	
	@UiHandler("btnSalvar") void btnSalvarOnClick(ClickEvent e){
		bean = driver.flush();
		salvarConfiguracao(bean);
		
		
	}
	

	ClickHandler handler = new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			cadastro.setVisible(false);
			confiAvancado.setVisible(false);
			if (event.getSource().equals(tabCadastro)) {
				cadastro.setVisible(true);
			} else if (event.getSource().equals(tabLista)) {
				confiAvancado.setVisible(true);
			}
			//btnListTabOnClick();
		}
	};
	public void registerTab() {
		tabCadastro.addClickHandler(handler);
		tabLista.addClickHandler(handler);
	}
	
	public void salvarConfiguracao(final Configuracao configuracao){
		//validarAluno();
		service.persistir(configuracao, new AsyncCallback<Configuracao>() {
			@Override
			public void onSuccess(Configuracao configuracao) {
				bean = configuracao;
				driver.edit(bean);
				OptionPanel.showMessage("Configuração salvo ");
			}

			@Override
			public void onFailure(Throwable e) {
				OptionPanel.showMessage("Erro ao tentar salvar o Configuração ");
			}
		});
		
	}
	
	public Configuracao getConfiguracao(){
		
		return null;
	}
	
}

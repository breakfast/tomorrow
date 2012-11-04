package com.breakfast.tomorrow.web.client;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import com.breakfast.gwt.user.client.OptionPanel;
import com.breakfast.tomorrow.core.academic.vo.Professor;
import com.breakfast.tomorrow.core.academic.vo.Responsavel;

import com.breakfast.tomorrow.web.client.async.ResponsavelService;
import com.breakfast.tomorrow.web.client.async.ResponsavelServiceAsync;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;

import com.google.gwt.event.dom.client.ClickEvent;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.ProvidesKey;


public class ResposnsavelView extends Composite implements Editor<Responsavel> {

	private static ResposnsavelViewUiBinder uiBinder = GWT.create(ResposnsavelViewUiBinder.class);
	interface ResposnsavelViewUiBinder extends UiBinder<Widget, ResposnsavelView> {}
	interface Driver extends SimpleBeanEditorDriver<Responsavel, ResposnsavelView>{}
	private ResponsavelServiceAsync service = GWT.create(ResponsavelService.class);
	private Driver driver =GWT.create(Driver.class);
	
	
	Responsavel bean = new  Responsavel();
	Collection<Responsavel> listBean = new ArrayList<Responsavel>();
	
	public ResposnsavelView() {
		initWidget(uiBinder.createAndBindUi(this));
		driver.initialize(this);
		driver.edit(bean);
		
		
	}
	
	@UiField(provided = true) SimplePager pager = new SimplePager(TextLocation.LEFT);
	@UiField(provided = true) DataGrid<Responsavel> dataGrid = new DataGrid<Responsavel>();
	@UiField @Path("stringId") TextBox id;
	@UiField @Path("nome") TextBox nome;
	@UiField @Path("endereco") TextBox endereco;
	@UiField @Path("telefone") TextBox telefone;
	@UiField @Path("complemento") TextBox complemento;
	@UiField @Path("distrito") TextBox bairro;
	@UiField @Path("cidade") TextBox cidade;
	@UiField @Path("codigoPostal") TextBox cep;
	@UiField @Path("email") TextBox email;

	public static int TAB_LIST = 1;
	public static int TAB_CAD = 0;
	
	@UiHandler("btnNovo") void btnNovoOnClick(ClickEvent e){
		bean = new Responsavel();
		driver.edit(bean);
	}
	
	@UiHandler("btnSalvar") void btnSalvarOnClick(ClickEvent e){
		bean = driver.flush();
		salvarResponsavel(bean);
		listarResponsaveis(dataGrid);  		
	}
	
	@UiHandler("btnCancelar") void btnCancelarOnClick(ClickEvent e){
		driver.edit(bean);
	} 
	
	public void salvarResponsavel(final Responsavel responsavel){
		
		service.persistir(responsavel, new AsyncCallback<Responsavel>() {
			@Override
			public void onSuccess(Responsavel responsavel) {
				bean = responsavel;
				driver.edit(bean);
				OptionPanel.showMessage("Registro salvo com sucesso.");
			}

			@Override
			public void onFailure(Throwable e) {
				OptionPanel.showMessage("Erro ao tentar salvar o registro" + e);
			}
		});
		
	}
	
	public void listarResponsaveis(final DataGrid<Responsavel> dataGrid){	
		
		service.lista(new AsyncCallback<Collection<Responsavel>>() {
       
			@Override
			public void onFailure(Throwable e) {
				OptionPanel.showMessage("Erro ao tentar listar os responsaveis.");
			}

			@Override
			public void onSuccess(Collection<Responsavel> list) {
				if(list!=null){
					listBean = list;
					dataGrid.setRowCount(list.size(), true);
					dataGrid.setRowData(new ArrayList<Responsavel>(list));
				}
			}
		
		});
	}
	
	ProvidesKey<Responsavel> keyProvider = new ProvidesKey<Responsavel>() {
		@Override
		public Object getKey(Responsavel responsavel) {
			return responsavel;
		}
	};
	
	
}

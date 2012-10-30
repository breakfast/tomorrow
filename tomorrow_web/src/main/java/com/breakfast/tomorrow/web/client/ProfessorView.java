package com.breakfast.tomorrow.web.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import com.breakfast.gwt.user.client.OptionPanel;
import com.breakfast.tomorrow.core.academic.vo.Professor;
import com.breakfast.tomorrow.web.client.async.ProfessorService;
import com.breakfast.tomorrow.web.client.async.ProfessorServiceAsync;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.ProvidesKey;

public class ProfessorView extends Composite implements Editor<Professor> {
	
	

	private static ProfessorViewUiBinder uiBinder = GWT.create(ProfessorViewUiBinder.class);
	interface ProfessorViewUiBinder extends UiBinder<Widget, ProfessorView>{}
	interface Driver extends SimpleBeanEditorDriver<Professor, ProfessorView>{}
	private ProfessorServiceAsync service = GWT.create(ProfessorService.class);
	private Driver driver = GWT.create(Driver.class);

	Professor bean = new Professor();
	Collection<Professor> listBean = new ArrayList<Professor>();
	
	

	public ProfessorView() {		
		createColumns(dataGrid);
		setSelectionModel(dataGrid, selectionModel);		
		initWidget(uiBinder.createAndBindUi(this));
		driver.initialize(this);
		driver.edit(bean);
	
	}
	
	@UiField(provided = true) SimplePager pager = new SimplePager(TextLocation.LEFT);
	@UiField(provided = true) DataGrid<Professor> dataGrid = new DataGrid<Professor>();
	@UiField TabLayoutPanel tab;
	@UiField Button btnSalvar;
	@UiField Button btnCancelar;
	@UiField Button btnExcluir;
	@UiField Button btnNovo;
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
		bean = new Professor();
		driver.edit(bean);
	}
	
	@UiHandler("btnSalvar") void btnSalvarOnClick(ClickEvent e){
		bean = driver.flush();
		salvarProfessor(bean);
		listarProfessores(dataGrid);  	
		
	}
	
	@UiHandler("btnCancelar") void btnCancelarOnClick(ClickEvent e){
		driver.edit(bean);
	} 
	
	@UiHandler("tab") void btnListTabOnClick(SelectionEvent<Integer> e){
		if(e.getSelectedItem()==TAB_LIST){
			listarProfessores(dataGrid);
		}
		btnSalvar.setVisible(e.getSelectedItem() == TAB_CAD);
		btnCancelar.setVisible(e.getSelectedItem() == TAB_CAD);
		btnNovo.setVisible(e.getSelectedItem() == TAB_CAD);
	//	btnRelatorio.setVisible(e.getSelectedItem() == TAB_LIST);
		
	}
	
	/**
	 * Atraves do servico, persisti professores no repositório de dados.
	 * @param aluno
	 */
	public void salvarProfessor(final Professor professor){
		//validarAluno();
		service.persistir(professor, new AsyncCallback<Professor>() {
			@Override
			public void onSuccess(Professor professor) {
				bean = professor;
				driver.edit(bean);
				OptionPanel.showMessage("Registro salvo com sucesso.");
			}

			@Override
			public void onFailure(Throwable e) {
				OptionPanel.showMessage("Erro ao tentar salvar o registro");
			}
		});
		
	}
	
	@UiHandler("btnExcluir") void btnExcluirOnClick(ClickEvent e){
		if(tab.getSelectedIndex() == TAB_CAD){
			Set<Professor> professores = new TreeSet<Professor>();
			professores.add(bean);
			excluirProfessor(professores);
			btnNovoOnClick(null);
		}
		else if(tab.getSelectedIndex() == TAB_LIST){
			@SuppressWarnings("unchecked")
			MultiSelectionModel<Professor> selection = ((MultiSelectionModel<Professor>) dataGrid.getSelectionModel());
			excluirProfessor(selection.getSelectedSet());
			listarProfessores(dataGrid);
		}
	}
	
	/**
	 * Chama o servico listar 
	 * @param dataGrid
	 */
	
	public void listarProfessores(final DataGrid<Professor> dataGrid){	
		
		service.lista(new AsyncCallback<Collection<Professor>>() {
       
			@Override
			public void onFailure(Throwable e) {
				OptionPanel.showMessage("Erro ao tentar listar alunos.");
			}

			@Override
			public void onSuccess(Collection<Professor> list) {
				if(list!=null){
					listBean = list;
					dataGrid.setRowCount(list.size(), true);
					dataGrid.setRowData(new ArrayList<Professor>(list));
				}
			}
		
		});
	}
	
	public void excluirProfessor(Set<Professor> professor){
		service.excluir(professor, new AsyncCallback<Void>() {
			@Override
			public void onSuccess(Void arg0) {
				OptionPanel.showMessage("Registro excluído com sucesso.");
			}
			
			@Override
			public void onFailure(Throwable e) {
				OptionPanel.showMessage("Erro ao tentar excluir registro.", e);
			}
		});
	}
	
	ProvidesKey<Professor> keyProvider = new ProvidesKey<Professor>() {
		@Override
		public Object getKey(Professor professor) {
			return professor;
		}
	};
	
	/**
	 * Selection Model usado para a seleção de varias linhas (objetos) no dataGrid.
	 */
	MultiSelectionModel<Professor> selectionModel = new MultiSelectionModel<Professor>(keyProvider);
	
	/**
	 * Cria a colunas do dataGrid.
	 */
	private void createColumns(final DataGrid<Professor> dataGrid){
	
		Column<Professor, Boolean> checkColumn = new Column<Professor, Boolean>(new CheckboxCell(true,false)){

			@Override
			public Boolean getValue(Professor professor) {

				return selectionModel.isSelected(professor);
			}			
		};
		
		dataGrid.addColumn(checkColumn);
		dataGrid.setColumnWidth(checkColumn, 1, Unit.PX);
		
		Column<Professor, String> idColumn = new Column<Professor, String>( new TextCell()) {
			@Override
			public String getValue(Professor professor) {
				return "" +  professor.getStringId();
			}
		
		};
		dataGrid.addColumn(idColumn,"Id");
		dataGrid.setColumnWidth(idColumn, 1, Unit.PX);
		
		
		Column<Professor, String> nomeColumn = new Column<Professor, String>( new ClickableTextCell()) {

			@Override
			public String getValue(Professor professor) {
				
				return professor.getNome();
			}
			
			@Override
			public void onBrowserEvent(Context context, Element elem, Professor object, NativeEvent event) {
				super.onBrowserEvent(context, elem, object, event);
				if("click".equals(event.getType())){
					bean = object;
					driver.edit(bean);
					tab.selectTab(TAB_CAD);
				}
			}
			
		};
		dataGrid.addColumn(nomeColumn,"Nome");
		dataGrid.setColumnWidth(nomeColumn, 1, Unit.PX);
		
	}	
	
	private void setSelectionModel(final DataGrid<Professor> dataGrid, final MultiSelectionModel<Professor> selectionModel){
		dataGrid.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
		dataGrid.setSelectionModel(selectionModel, DefaultSelectionEventManager.<Professor>createCheckboxManager());
	}
}

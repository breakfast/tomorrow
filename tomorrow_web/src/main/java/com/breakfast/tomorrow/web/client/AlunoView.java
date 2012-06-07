package com.breakfast.tomorrow.web.client;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.breakfast.gwt.user.client.OptionPanel;
import com.breakfast.tomorrow.web.client.async.AlunoService;
import com.breakfast.tomorrow.web.client.async.AlunoServiceAsync;
import com.breakfast.tomorrow.web.client.vo.AlunoVO;
import com.breakfast.tomorrow.web.shared.ClientValidator;
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
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.ProvidesKey;


/**
 * Classe de Apresentação e controle de alunos.
 * Essa classe implementa a Interface com usuário para a chamadas de serviços e acesso ao modelo de dados
 * @author kleberilario@gmail.com
 *
 */
public class AlunoView extends Composite implements Editor<AlunoVO>{

	private static AlunoViewUiBinder uiBinder = GWT.create(AlunoViewUiBinder.class);
	interface AlunoViewUiBinder extends UiBinder<Widget, AlunoView> {}
	interface Driver extends SimpleBeanEditorDriver<AlunoVO, AlunoView>{}
	private AlunoServiceAsync service = GWT.create(AlunoService.class);
	private Driver driver = GWT.create(Driver.class);
	
	AlunoVO bean = new AlunoVO();
	
	public AlunoView() {
		initWidget(uiBinder.createAndBindUi(this));
		createColumns(dataGrid);
		setSelectionModel(dataGrid, selectionModel);
		driver.initialize(this);
		driver.edit(bean);
		id.setReadOnly(true);
	} 
	
	public static int TAB_LIST = 1;
	public static int TAB_CAD = 0;
	
	@UiField(provided = true) SimplePager pager = new SimplePager(TextLocation.LEFT);
	@UiField(provided = true) DataGrid<AlunoVO> dataGrid = new DataGrid<AlunoVO>();
	@UiField TabLayoutPanel tab;
	@UiField Button btnSalvar;
	@UiField Button btnCancelar;
	@UiField Button btnExcluir;
	@UiField Button btnNovo;
	@UiField @Path("idPessoa") TextBox id;
	@UiField @Path("nome") TextBox nome;
	@UiField @Path("endereco") TextBox endereco;
	@UiField @Path("telefone") TextBox telefone;
	@UiField @Path("numeroEndereco") TextBox numero;
	@UiField @Path("complemento") TextBox complemento;
	@UiField @Path("distrito") TextBox bairro;
	@UiField @Path("cidade") TextBox cidade;
	@UiField @Path("codigoPostal") TextBox cep;

	@UiHandler("btnNovo") void btnNovoOnClick(ClickEvent e){
		bean = new AlunoVO();
		driver.edit(bean);
	}
	
	@UiHandler("btnSalvar") void btnSalvarOnClick(ClickEvent e){
		bean = driver.flush();
		salvarAluno(bean);
		listarAlunos(dataGrid);
	}
	
	@UiHandler("btnCancelar") void btnCancelarOnClick(ClickEvent e){
		driver.edit(bean);
	} 

	@UiHandler("tab") void list(SelectionEvent<Integer> e){
		if(e.getSelectedItem()==TAB_LIST){
			listarAlunos(dataGrid);
		}
		btnSalvar.setVisible(e.getSelectedItem() == TAB_CAD);
		btnCancelar.setVisible(e.getSelectedItem() == TAB_CAD);
		btnNovo.setVisible(e.getSelectedItem() == TAB_CAD);
	}
	
	@UiHandler("btnExcluir") void btnExcluirOnClick(ClickEvent e){
		if(tab.getSelectedIndex() == TAB_CAD){
			Set<AlunoVO> alunos = new TreeSet<AlunoVO>();
			alunos.add(bean);
			excluirAluno(alunos);
			btnNovoOnClick(null);
		}
		else if(tab.getSelectedIndex() == TAB_LIST){
			@SuppressWarnings("unchecked")
			MultiSelectionModel<AlunoVO> selection = ((MultiSelectionModel<AlunoVO>) dataGrid.getSelectionModel());
			excluirAluno(selection.getSelectedSet());
			listarAlunos(dataGrid);
		}
	}
	
	ProvidesKey<AlunoVO> keyProvider = new ProvidesKey<AlunoVO>() {
		@Override
		public Object getKey(AlunoVO aluno) {
			return aluno;
		}
	};
	MultiSelectionModel<AlunoVO> selectionModel = new MultiSelectionModel<AlunoVO>(keyProvider);
	
	
	/**
	 * Chama o serviço persistir
	 * @param aluno
	 */
	public void salvarAluno(final AlunoVO aluno){
		
		//OptionPanel.showMessage("Aluno Inserido Com Sucesso");
		
		validarAluno();
		
		/*
		service.persistir(aluno, new AsyncCallback<AlunoVO>() {
			@Override
			public void onSuccess(AlunoVO aluno) {
				//TODO Trocar Wineow Alert
				bean = aluno;
				driver.edit(bean);
				Window.alert("Aluno "+ bean.getNome() + " Inserido com sucesso!");
			}
			
			@Override
			public void onFailure(Throwable e) {
				//TODO Trocar Wineow Alert
				Window.alert("Erro ao Inserir aluno\n" + e.getMessage());
			}
		});
		*/
		
	}
	
	/**
	 * Chama o servico listar 
	 * @param dataGrid
	 */
	public void listarAlunos(final DataGrid<AlunoVO> dataGrid){	
		
		service.lista(new AsyncCallback<List<AlunoVO>>() {

			@Override
			public void onFailure(Throwable e) {
				Window.alert(e.getMessage());
			}

			@Override
			public void onSuccess(List<AlunoVO> list) {
				if(list!=null){
					dataGrid.setRowCount(list.size(), true);
					dataGrid.setRowData(list);
				}
			}
		
		});
	}
	
	public void excluirAluno(Set<AlunoVO> aluno){
		service.excluir(aluno, new AsyncCallback<Void>() {
			@Override
			public void onSuccess(Void arg0) {
				// TODO Trocar Wineow Alert
				Window.alert("Aluno Excluido com sucesso!");
			}
			
			@Override
			public void onFailure(Throwable arg0) {
				// TODO Trocar Wineow Alert
				Window.alert("Erro ao Excluir aluno");
			}
		});
	}
	
	public void gerarRelatorio(List<AlunoVO> lista){
		/*
		service.gerarRelatorio(lista, new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable e) {
				OptionPanel.showMessage(e.getMessage())';'
			}

			@Override
			public void onSuccess(String caminho) {
				Window.open(caminho, "", "");
				
			}
		});
		*/
		
	}
	
		
	/**
	 * Cria a colunas do dataGrid.
	 */
	private void createColumns(final DataGrid<AlunoVO> dataGrid){
		
		Column<AlunoVO, Boolean> checkColumn = new Column<AlunoVO, Boolean>(new CheckboxCell(true,false)) {

			@Override
			public Boolean getValue(AlunoVO aluno) {
				return selectionModel.isSelected(aluno);
			}
		
		
		};
		dataGrid.addColumn(checkColumn);
		dataGrid.setColumnWidth(checkColumn, 40, Unit.PX);
		
		Column<AlunoVO, String> idColumn = new Column<AlunoVO, String>( new TextCell()) {
			@Override
			public String getValue(AlunoVO aluno) {
				return aluno.getIdPessoa().toString();
			}
		
		};
		dataGrid.addColumn(idColumn,"Id");
		dataGrid.setColumnWidth(idColumn, 10, Unit.PCT);
		
		Column<AlunoVO, String> nomeColumn = new Column<AlunoVO, String>( new ClickableTextCell()) {
			
			@Override
			public String getValue(AlunoVO aluno) {
				return aluno.getNome();
			}

			@Override
			public void onBrowserEvent(Context context, Element elem, AlunoVO object, NativeEvent event) {
				super.onBrowserEvent(context, elem, object, event);
				if("click".equals(event.getType())){
					bean = object;
					driver.edit(bean);
					tab.selectTab(TAB_CAD);
				}
			} 
			
		
		};
		dataGrid.addColumn(nomeColumn,"Nome");
		dataGrid.setColumnWidth(nomeColumn, 40, Unit.PCT);
		
		Column<AlunoVO, String> enderecoColumn = new Column<AlunoVO, String>( new TextCell()) {
			@Override
			public String getValue(AlunoVO aluno) {
				return aluno.getEndereco();
			}
		
		};
		dataGrid.addColumn(enderecoColumn,"Endereço");
		dataGrid.setColumnWidth(enderecoColumn, 40, Unit.PCT);
		
		Column<AlunoVO, String> telefoneColumn = new Column<AlunoVO, String>( new TextCell()) {
			@Override
			public String getValue(AlunoVO aluno) {
				return aluno.getTelefone();
			}
		
		};
		dataGrid.addColumn(telefoneColumn,"Telefone");
		dataGrid.setColumnWidth(telefoneColumn, 10,	Unit.PCT);
		
		dataGrid.addHandler(new DoubleClickHandler() {
			
			@Override
			public void onDoubleClick(DoubleClickEvent arg0) {
				Window.alert(bean.getNome());
			}
		}, DoubleClickEvent.getType());
		
	}
	
	/**
	 * Registra o selectionModel no DataGrid.
	 * MultiSelectionModel gerencia as linhas selecionadas no datagrid. 
	 * @param dataGrid
	 * @param selectionModel
	 */
	private void setSelectionModel(final DataGrid<AlunoVO> dataGrid, final MultiSelectionModel<AlunoVO> selectionModel){
		dataGrid.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
		dataGrid.setSelectionModel(selectionModel, DefaultSelectionEventManager.<AlunoVO>createCheckboxManager());
	}
	
	//TODO verificar validação no cliente.
	private boolean validarAluno(){
		new ClientValidator().register(nome, "error", "notnull", "length > 8","length < 3");
		//return ClientValidator.validate(nome, "error", "notnull", "length > 8","length < 3");
		return false;
	}
 
	
}
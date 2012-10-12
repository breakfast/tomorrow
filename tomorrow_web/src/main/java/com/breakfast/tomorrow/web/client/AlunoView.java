package com.breakfast.tomorrow.web.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.breakfast.gwt.user.client.OptionPanel;
import com.breakfast.tomorrow.web.client.async.AlunoService;
import com.breakfast.tomorrow.web.client.async.AlunoServiceAsync;
import com.breakfast.tomorrow.web.client.resources.ImageBundle;
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
import com.google.gwt.event.dom.client.ClickHandler;
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
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
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
	private ImageBundle bundle = GWT.create(ImageBundle.class);
	
	AlunoVO bean = new AlunoVO();
	List<AlunoVO> listBean = new ArrayList<AlunoVO>();
	
	public AlunoView() {
		initWidget(uiBinder.createAndBindUi(this));
		createColumns(dataGrid);
		setSelectionModel(dataGrid, selectionModel);
		initFotoPanel();
		driver.initialize(this);
		driver.edit(bean);
		id.setReadOnly(true);
		btnRelatorio.setVisible(false);
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
	@UiField Button btnRelatorio;
	@UiField @Path("idPessoa") TextBox id;
	@UiField @Path("nome") TextBox nome;
	@UiField @Path("endereco") TextBox endereco;
	@UiField @Path("telefone") TextBox telefone;
	@UiField @Path("complemento") TextBox complemento;
	@UiField @Path("distrito") TextBox bairro;
	@UiField @Path("cidade") TextBox cidade;
	@UiField @Path("codigoPostal") TextBox cep;
	@UiField @Path("email") TextBox email;
	
	@UiField VerticalPanel fotoPanel;

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

	@UiHandler("tab") void btnListTabOnClick(SelectionEvent<Integer> e){
		if(e.getSelectedItem()==TAB_LIST){
			listarAlunos(dataGrid);
		}
		btnSalvar.setVisible(e.getSelectedItem() == TAB_CAD);
		btnCancelar.setVisible(e.getSelectedItem() == TAB_CAD);
		btnNovo.setVisible(e.getSelectedItem() == TAB_CAD);
		btnRelatorio.setVisible(e.getSelectedItem() == TAB_LIST);
		
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
	
	@UiHandler("btnRelatorio") void btnRelatorioOnClick(ClickEvent e){
		gerarRelatorio(listBean);
	}
	
	ProvidesKey<AlunoVO> keyProvider = new ProvidesKey<AlunoVO>() {
		@Override
		public Object getKey(AlunoVO aluno) {
			return aluno;
		}
	};
	
	/**
	 * Selection Model usado para a seleção de varias linhas (objetos) no dataGrid.
	 */
	MultiSelectionModel<AlunoVO> selectionModel = new MultiSelectionModel<AlunoVO>(keyProvider);
	
	
	/**
	 * Atraves do servico, persisti alunos no repositório de dados.
	 * @param aluno
	 */
	public void salvarAluno(final AlunoVO aluno){
		validarAluno();
		service.persistir(aluno, new AsyncCallback<AlunoVO>() {
			@Override
			public void onSuccess(AlunoVO aluno) {
				bean = aluno;
				driver.edit(bean);
				OptionPanel.showMessage("Registro salvo com sucesso.");
			}

			@Override
			public void onFailure(Throwable e) {
				OptionPanel.showMessage("Erro ao tentar salvar o registro");
			}
		});
		
	}
	
	/**
	 * Chama o servico listar 
	 * @param dataGrid
	 */
	public void listarAlunos(final DataGrid<AlunoVO> dataGrid){	
		
		service.lista(new AsyncCallback<List<AlunoVO>>() {

			@Override
			public void onFailure(Throwable e) {
				OptionPanel.showMessage("Erro ao tentar listar alunos.");
			}

			@Override
			public void onSuccess(List<AlunoVO> list) {
				if(list!=null){
					listBean = list;
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
				OptionPanel.showMessage("Registro excluído com sucesso.");
			}
			
			@Override
			public void onFailure(Throwable e) {
				OptionPanel.showMessage("Erro ao tentar salvar registro.", e);
			}
		});
	}
	
	/**
	 * Atraves do servico, gera relatorio a partir de uma lista de alunoVO.
	 * @param lista
	 */
	private void gerarRelatorio(List<AlunoVO> lista){
		
		service.gerarRelatorio(lista, new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable e) {
				OptionPanel.showMessage("Erro ao excluir Registro", e);
			}

			@Override
			public void onSuccess(String caminho) {
				Window.open("../" + caminho, "", "");	
			}
		
		});
		
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
	
	
	 
	private void validarAluno(){
		assert ClientValidator.validate(nome, 
									   "Campo não pode ser nulo",
									   "error", 
									   "notnull", "length > 8","length < 3");
		
	}
	
	//FileUpload uploadFoto = new FileUpload();
	private void initFotoPanel(){
		Image foto = new Image(bundle.fotoLoginMedio());
		foto.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent arg0) {
				SeletorFoto.getPathFoto();
			}
		});
		this.fotoPanel.add(foto);
		//this.fotoPanel.add(uploadFoto);
	}
 
	
}
package com.breakfast.tomorrow.web.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;


import com.breakfast.gwt.user.client.OptionPanel;
import com.breakfast.tomorrow.core.academic.vo.Aluno;
import com.breakfast.tomorrow.web.client.async.AlunoService;
import com.breakfast.tomorrow.web.client.async.AlunoServiceAsync;
import com.breakfast.tomorrow.web.client.resources.ImageBundle;
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
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
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
public class AlunoView extends Composite implements Editor<Aluno>{

	private static AlunoViewUiBinder uiBinder = GWT.create(AlunoViewUiBinder.class);
	interface AlunoViewUiBinder extends UiBinder<Widget, AlunoView> {}
	interface Driver extends SimpleBeanEditorDriver<Aluno, AlunoView>{} 
	private AlunoServiceAsync service = GWT.create(AlunoService.class);
	private Driver driver = GWT.create(Driver.class);
	private ImageBundle bundle = GWT.create(ImageBundle.class);
	
	Aluno bean = new Aluno();
	Collection<Aluno> listBean = new ArrayList<Aluno>();
	
	public AlunoView() {
		initWidget(uiBinder.createAndBindUi(this));
		createColumns(dataGrid);
		setSelectionModel(dataGrid, selectionModel);
		initFotoPanel();
		registerTab();
		driver.initialize(this);
		driver.edit(bean);
		
		btnRelatorio.setVisible(false);
		lista.setVisible(false);
	} 
	
	@UiField HTMLPanel cadastro;
	@UiField HTMLPanel lista;
	
	@UiField Button btnSalvar;
	@UiField Button btnCancelar;
	@UiField Button btnExcluir;
	@UiField Button btnNovo;
	@UiField Button btnMatricular;
	@UiField Button btnRelatorio;
	@UiField @Path("stringId") TextBox id;
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
		bean = new Aluno();
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
	
	@UiHandler("btnExcluir") void btnExcluirOnClick(ClickEvent e){
		if(cadastro.isVisible()){
			Set<Aluno> alunos = new TreeSet<Aluno>();
			alunos.add(bean);
			excluirAluno(alunos);
			btnNovoOnClick(null);
		}
		else if(lista.isVisible()){
			@SuppressWarnings("unchecked")
			MultiSelectionModel<Aluno> selection = ((MultiSelectionModel<Aluno>) dataGrid.getSelectionModel());
			excluirAluno(selection.getSelectedSet());
			listarAlunos(dataGrid);
		}
	}
	
	@UiHandler("btnRelatorio") void btnRelatorioOnClick(ClickEvent e){
		gerarRelatorio(listBean);
	}
	
	@UiHandler("btnMatricular") void btnMatricularOnClick(ClickEvent e){
		WebApp app = WebApp.getWebApp();
		Widget w = app.mapWidget.get("matricular");
		((MatriculaView)w).alunoBean = bean;
		History.newItem("matricular");
		app.setContent(w);
	}
	
	void btnListTabOnClick(){
		if(lista.isVisible()){
			listarAlunos(dataGrid);
		}
		btnSalvar.setVisible(cadastro.isVisible());
		btnCancelar.setVisible(cadastro.isVisible());
		btnNovo.setVisible(cadastro.isVisible());
		btnRelatorio.setVisible(lista.isVisible());
		
	}
	
	
	/**
	 * Atraves do servico, persisti alunos no repositório de dados.
	 * @param aluno
	 */
	public void salvarAluno(final Aluno aluno){
		if(!validarAluno())return;
		service.persistir(aluno, new AsyncCallback<Aluno>() {
			@Override
			public void onSuccess(Aluno aluno) {
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

	public void listarAlunos(final DataGrid<Aluno> dataGrid){	
		
		service.lista(new AsyncCallback<Collection<Aluno>>() {

			@Override
			public void onFailure(Throwable e) {
				OptionPanel.showMessage("Erro ao tentar listar alunos.");
			}

			@Override
			public void onSuccess(Collection<Aluno> list) {
				if(list!=null){
					listBean = list;
					dataGrid.setRowCount(list.size(), true);
					dataGrid.setRowData(new ArrayList<Aluno>(list));
				}
			}
		
		});
	}
	
	public void excluirAluno(Set<Aluno> aluno){
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
	

	private void gerarRelatorio(Collection<Aluno> lista){
		
		service.gerarRelatorio(lista, new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable e) {
				OptionPanel.showMessage("Erro ao Gerar Relatório", e);
			}

			@Override
			public void onSuccess(String caminho) {
				Window.open("../" + caminho, "", "");	
			}
		
		});
		
	}
	
	
	ProvidesKey<Aluno> keyProvider = new ProvidesKey<Aluno>() {
		@Override
		public Object getKey(Aluno aluno) {
			return aluno;
		}
	};
	@UiField(provided = true) SimplePager pager = new SimplePager(TextLocation.LEFT);
	@UiField(provided = true) DataGrid<Aluno> dataGrid = new DataGrid<Aluno>();
	MultiSelectionModel<Aluno> selectionModel = new MultiSelectionModel<Aluno>(keyProvider);

	private void createColumns(final DataGrid<Aluno> dataGrid){
		
		Column<Aluno, Boolean> checkColumn = new Column<Aluno, Boolean>(new CheckboxCell(true,false)) {

			@Override
			public Boolean getValue(Aluno aluno) {
				return selectionModel.isSelected(aluno);
			}
		
		
		};
		dataGrid.addColumn(checkColumn);
		dataGrid.setColumnWidth(checkColumn, 40, Unit.PX);
		
		Column<Aluno, String> idColumn = new Column<Aluno, String>( new TextCell()) {
			@Override
			public String getValue(Aluno aluno) {
				return "" +  aluno.getStringId();
			}
		
		};
		dataGrid.addColumn(idColumn,"Id");
		dataGrid.setColumnWidth(idColumn, 10, Unit.PCT);
		
		Column<Aluno, String> nomeColumn = new Column<Aluno, String>( new ClickableTextCell()) {
			
			@Override
			public String getValue(Aluno aluno) {
				return aluno.getNome();
			}

			@Override
			public void onBrowserEvent(Context context, Element elem, Aluno object, NativeEvent event) {
				super.onBrowserEvent(context, elem, object, event);
				if("click".equals(event.getType())){
					bean = object;
					driver.edit(bean);
					tabCadastro.click();
				}
			} 
			
		
		};
		dataGrid.addColumn(nomeColumn,"Nome");
		dataGrid.setColumnWidth(nomeColumn, 40, Unit.PCT);
		
		Column<Aluno, String> enderecoColumn = new Column<Aluno, String>( new TextCell()) {
			@Override
			public String getValue(Aluno aluno) {
				return aluno.getEndereco();
			}
		
		};
		dataGrid.addColumn(enderecoColumn,"Endereço");
		dataGrid.setColumnWidth(enderecoColumn, 40, Unit.PCT);
		
		Column<Aluno, String> telefoneColumn = new Column<Aluno, String>( new TextCell()) {
			@Override
			public String getValue(Aluno aluno) {
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
	private void setSelectionModel(final DataGrid<Aluno> dataGrid, final MultiSelectionModel<Aluno> selectionModel){
		dataGrid.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
		dataGrid.setSelectionModel(selectionModel, DefaultSelectionEventManager.<Aluno>createCheckboxManager());
	}
	
	
	private boolean validarAluno(){
		boolean valid = ClientValidator.validate(nome, "Campo não pode ser nulo\nMinimo 5 Caracteres ","error", "notnull", "length > 50","length < 5");
		valid = ClientValidator.validate(email, "Campo não pode ser nulo","error", "notnull") && valid;
		valid = ClientValidator.validate(endereco, "Campo não pode ser nulo","error", "notnull") && valid;
		valid = ClientValidator.validate(telefone, "Campo não pode ser nulo","error", "notnull") && valid;
		valid = ClientValidator.validate(bairro, "Campo não pode ser nulo","error", "notnull") && valid;
		valid = ClientValidator.validate(cidade, "Campo não pode ser nulo","error", "notnull") && valid;
		return valid;
	}
	
	
	private void initFotoPanel(){
		Image foto = new Image(bundle.fotoLoginMedio());
		foto.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent arg0) {
				SeletorFoto.getPathFoto();
			}
		});
		this.fotoPanel.add(foto);

	}
	
	@UiField Button tabCadastro;
	@UiField Button tabLista;
	ClickHandler handler = new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			cadastro.setVisible(false);
			lista.setVisible(false);
			if(event.getSource().equals(tabCadastro)){
				cadastro.setVisible(true);
			}
			else if(event.getSource().equals(tabLista)){
				lista.setVisible(true);
			}
			btnListTabOnClick();
		}
	};
	
	public void registerTab(){
		tabCadastro.addClickHandler(handler);
		tabLista.addClickHandler(handler);
	}
 
	
}
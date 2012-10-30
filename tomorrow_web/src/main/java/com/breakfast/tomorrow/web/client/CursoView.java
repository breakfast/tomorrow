package com.breakfast.tomorrow.web.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.breakfast.gwt.user.client.OptionPanel;
import com.breakfast.tomorrow.core.academic.vo.Curso;
import com.breakfast.tomorrow.core.academic.vo.Disciplina;
import com.breakfast.tomorrow.core.academic.vo.Etapa;
import com.breakfast.tomorrow.web.client.async.CursoService;
import com.breakfast.tomorrow.web.client.async.CursoServiceAsync;
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
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
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
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.ProvidesKey;


public class CursoView extends Composite implements Editor<Curso>{

	private static CursoViewUiBinder uiBinder = GWT.create(CursoViewUiBinder.class);
	interface CursoViewUiBinder extends UiBinder<Widget, CursoView> {}
	interface Driver extends com.google.gwt.editor.client.SimpleBeanEditorDriver<Curso, CursoView>{}
	private CursoServiceAsync service = GWT.create(CursoService.class);
	private Driver driver = GWT.create(Driver.class);
	
	private Curso bean = new Curso();
	Collection<Curso> listBean = new ArrayList<Curso>();
	
	public CursoView() {
		initWidget(uiBinder.createAndBindUi(this));
		createColumns(dataGrid);
		setSelectionModel(dataGrid, selectionModel);
		initConfig();
		registerTab();
		
		lista.setVisible(false);
		driver.initialize(this);
		driver.edit(bean);
	}
	
	
	@UiField HTMLPanel cadastro;
	@UiField HTMLPanel lista;
	
	@UiField Button btnSalvar;
	@UiField Button btnCancelar;
	@UiField Button btnExcluir;
	@UiField Button btnNovo;
	
	@UiField @Path("stringId") TextBox id;
	@UiField @Path("nomeCurso") TextBox nome;
	@UiField @Path("descricao") TextBox descricao;
	@UiField @Path("duracao") TextBox duracao;
	@UiField @Path("stringMedia") TextBox media;
	@UiField @Path("stringQtdeDependenciaReprovacao") TextBox qtdeMaxima;
	
	@UiHandler("btnSalvar") void btnSalvarOnClick(ClickEvent e){
		bean = driver.flush();
		salvarCurso(bean);
	}
	
	@UiHandler("btnCancelar") void btnCancelarOnClick(ClickEvent e){
		driver.edit(bean);
	} 
	
	@UiHandler("btnNovo") void btnNovoOnClick(ClickEvent e){
		bean = new Curso();
		driver.edit(bean);
		configuracao.clear();
	}
	
	@UiHandler("btnExcluir") void btnExluirOnClick(ClickEvent e){
		if(cadastro.isVisible()){
			Set<Curso> cursos = new TreeSet<Curso>();
			cursos.add(bean);
			excluirCursos(cursos);
		}
		else if(lista.isVisible()){
			@SuppressWarnings("unchecked")
			MultiSelectionModel<Curso> selection = ((MultiSelectionModel<Curso>) dataGrid.getSelectionModel());
			excluirCursos(selection.getSelectedSet());
			listarCursos(dataGrid);
		}
		btnNovoOnClick(null);
	}
	
	void carregarBean(Curso bean){
		this.bean = bean;
		driver.edit(bean);
		carregaConfiguracao(configuracao, bean);
	}
	
	private void btnListTabOnClick(){
		if(lista.isVisible()){
			listarCursos(dataGrid);
		}
		btnSalvar.setVisible(cadastro.isVisible());
		btnCancelar.setVisible(cadastro.isVisible());
		btnNovo.setVisible(cadastro.isVisible());
	}
	
	private boolean validarCurso(){
		boolean valid = ClientValidator.validate(nome, "Campo não pode Ser nulo", "error", "notnull");
		valid = ClientValidator.validate(qtdeMaxima, "Campo não pode Ser nulo", "error", "notnull") && valid;
		valid = ClientValidator.validate(media,"Campo não pode Ser nulo", "error", "notnull") && valid;
		return valid;
	}
	
	public void salvarCurso(Curso curso){
		if(!validarCurso()) return;
		salvarConfiguracao(configuracao, curso);
		service.persistir(curso, new AsyncCallback<Curso>() {
			@Override
			public void onSuccess(Curso curso) {
				bean = curso;
				driver.edit(bean);
				OptionPanel.showMessage("Registro salvo com sucesso.");
			}

			@Override
			public void onFailure(Throwable e) {
				OptionPanel.showMessage("Erro ao tentar salvar o registro");
			}
		});
	}
	
	public void listarCursos(final DataGrid<Curso> dataGrid){
		service.lista(new AsyncCallback<Collection<Curso>>() {

			@Override
			public void onFailure(Throwable e) {
				OptionPanel.showMessage("Erro ao tentar listar alunos.");
			}

			@Override
			public void onSuccess(Collection<Curso> list) {
				if(list!=null){
					listBean = list;
					dataGrid.setRowCount(list.size(), true);
					dataGrid.setRowData(new ArrayList<Curso>(list));
				}
			}
		
		});
	}
	
	public void excluirCursos(Set<Curso> curso){
		service.excluir(curso, new AsyncCallback<Void>() {
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
	
	public void carregaConfiguracao(ConfiguracaoUI panelConfig, Curso curso){
		panelConfig.clear();
		final Map<Etapa,Collection<Disciplina>> map = curso.getConfiguracao();
		for(Etapa etapa : map.keySet()){
			EtapaUI ui = new EtapaUI();
			ui.id = etapa.getId();
			ui.setText(etapa.getNomeEtapa());
			for(Disciplina dis : map.get(etapa)){
				DisciplinaUI dUi = new DisciplinaUI(dis.getNomeDisciplina());
				dUi.id = dis.getId();
				ui.add(dUi);
			}
			panelConfig.add(ui);
		}
	}
	
	public void salvarConfiguracao(ConfiguracaoUI configUI, Curso curso){
		Map<Etapa,Collection<Disciplina>> map = new HashMap<Etapa, Collection<Disciplina>>();
		for(EtapaUI etapaUI : configUI.childs){
			Collection<Disciplina> listaDis = new ArrayList<Disciplina>();
			Etapa e = new Etapa();
			e.setNomeEtapa(etapaUI.getText());
			e.setId(etapaUI.id);
			for(DisciplinaUI dis : etapaUI.childs){
				Disciplina disciplina = new Disciplina(dis.getText());
				disciplina.setId(dis.id);
				listaDis.add(disciplina);
			}
			map.put(e, listaDis);
		}
		curso.setConfiguracao(map);
	}
	
	
	public class ConfiguracaoUI extends VerticalPanel{
		Collection<EtapaUI> childs = new ArrayList<CursoView.EtapaUI>();
		@Override
		public void add(Widget child) {
			super.add(child);
			if(child instanceof EtapaUI){
				childs.add((EtapaUI)child);
			}
		}
		public ConfiguracaoUI(){
			super();
		}
		@Override
		public void clear() {
			super.clear();
			childs.clear();
		}
		@Override
		public boolean remove(Widget w) {
			childs.remove(w);
			return super.remove(w);
		}
	}
	
	private ConfiguracaoUI configuracao = new ConfiguracaoUI(); 
	Hyperlink addEtapa = new Hyperlink("Adicionar Etapa","");
	private void initConfig(){
		cadastro.add(addEtapa);
		cadastro.add(configuracao);
		configuracao.setSpacing(10);
		configuracao.setWidth("100%");
		this.configuracao.setWidth("100%");
		addEtapa.addHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent arg0){
				final EtapaUI etapa = new EtapaUI();
				configuracao.add(etapa);
			}
		}, ClickEvent.getType());
		SimplePanel fotter = new SimplePanel();
		fotter.setHeight("100px");
		cadastro.add(fotter);
	}
	
	class EtapaUI extends VerticalPanel implements HasText{
		
		class Flow extends FlowPanel{
			@Override
			public boolean remove(Widget w) {
				childs.remove(w);
				return super.remove(w);
			}
			
		}
		
		final Flow flow = new Flow();
		long id = 0;
		TextBox nomeEtapa = new TextBox();
		Collection<DisciplinaUI> childs = new ArrayList<CursoView.DisciplinaUI>();
		public EtapaUI() {
			this.setWidth("100%");
			this.setHeight("100px");
			this.getElement().getStyle().setBackgroundColor("#F5F5F5");
			this.getElement().getStyle().setPadding(10, Unit.PX);
			flow.setWidth("100%");
			Hyperlink addDisciplina = new Hyperlink("Adicionar Disciplina", "");
			nomeEtapa.setWidth("80%");
			addDisciplina.addHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					DisciplinaUI disciplina = new DisciplinaUI("");
					add(disciplina);
				}
			}, ClickEvent.getType());
			nomeEtapa.addKeyDownHandler(new KeyDownHandler() {
				@Override
				public void onKeyDown(KeyDownEvent event) {
					if(event.isControlKeyDown() &&((event.getNativeKeyCode() == KeyCodes.KEY_DELETE) ||
							(event.getNativeKeyCode() == KeyCodes.KEY_BACKSPACE))){
						remove();
					}
				}
			});
			HorizontalPanel h = new HorizontalPanel();
			h.setWidth("100%");
			h.add(nomeEtapa);
			h.add(addDisciplina);
			this.add(h);
			this.add(flow);
		}
		
		@Override
		public void add(Widget w) {
			if(w instanceof DisciplinaUI){
				childs.add((DisciplinaUI)w);
				flow.add(w);
			}
			else super.add(w);
		}
		

		@Override
		public String getText() {
			return this.nomeEtapa.getText();
		}

		@Override
		public void setText(String text) {
			this.nomeEtapa.setText(text);
		}
		
		public void remove(){
			if(this.getParent() instanceof ConfiguracaoUI){
				((ConfiguracaoUI)this.getParent()).childs.remove(this);
			}
			this.removeFromParent();
		}
		
		@Override
		public boolean remove(Widget w) {
			childs.remove(w);
			return super.remove(w);
		}
		
		public Collection<DisciplinaUI> getChilds(){
			Collection<DisciplinaUI> childs = new ArrayList<CursoView.DisciplinaUI>();
			for(int x = 0; x <= flow.getWidgetCount(); x++){
				Widget w = getWidget(x);
				if(w instanceof DisciplinaUI){
					childs.add((DisciplinaUI) w);
				}
			}
			return childs;
		}
	}
	
	

	class DisciplinaUI extends TextBox{
		
		long id = 0;
		public DisciplinaUI(String name){
			this.setText(name);
			this.setWidth("310px");
			this.getElement().getStyle().setBackgroundColor("#f5f5c4");
			this.addKeyDownHandler(new KeyDownHandler() {
				@Override
				public void onKeyDown(KeyDownEvent event) {
					if(event.isControlKeyDown() &&((event.getNativeKeyCode() == KeyCodes.KEY_DELETE) ||
							(event.getNativeKeyCode() == KeyCodes.KEY_BACKSPACE))){
						remove();
					}
				}
			});
		}
		public void remove(){
			if(this.getParent() instanceof EtapaUI){
				((EtapaUI)this.getParent()).childs.remove(this);
			}
			this.removeFromParent();
		}
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
	
	
	private void setSelectionModel(final DataGrid<Curso> dataGrid, final MultiSelectionModel<Curso> selectionModel){
		dataGrid.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
		dataGrid.setSelectionModel(selectionModel, DefaultSelectionEventManager.<Curso>createCheckboxManager());
	}
	
	ProvidesKey<Curso> keyProvider = new ProvidesKey<Curso>() {
		@Override
		public Object getKey(Curso curso) {
			return curso;
		}
	};
	@UiField(provided = true) SimplePager pager = new SimplePager(TextLocation.LEFT);
	@UiField(provided = true) DataGrid<Curso> dataGrid = new DataGrid<Curso>();
	MultiSelectionModel<Curso> selectionModel = new MultiSelectionModel<Curso>(keyProvider);
	
	public void createColumns(final DataGrid<Curso> dataGrid){
		Column<Curso, Boolean> checkColumn = new Column<Curso, Boolean>(new CheckboxCell(true,false)) {

			@Override
			public Boolean getValue(Curso curso) {
				return selectionModel.isSelected(curso);
			}
		
		
		};
		dataGrid.addColumn(checkColumn);
		dataGrid.setColumnWidth(checkColumn, 40, Unit.PX);
		
		Column<Curso, String> idColumn = new Column<Curso, String>( new TextCell()) {
			@Override
			public String getValue(Curso curso) {
				return "" +  curso.getStringId();
			}
		
		};
		dataGrid.addColumn(idColumn,"Id");
		dataGrid.setColumnWidth(idColumn, 10, Unit.PCT);
		
		Column<Curso, String> nomeColumn = new Column<Curso, String>( new ClickableTextCell()) {
			
			@Override
			public String getValue(Curso curso) {
				return curso.getNomeCurso();
			}

			@Override
			public void onBrowserEvent(Context context, Element elem, Curso object, NativeEvent event) {
				super.onBrowserEvent(context, elem, object, event);
				if("click".equals(event.getType())){
					carregarBean(object);
					tabCadastro.click();
				}
			} 
		};
		dataGrid.addColumn(nomeColumn,"Curso");
		dataGrid.setColumnWidth(nomeColumn, 40, Unit.PCT);
		
		Column<Curso, String> descricaoColumn = new Column<Curso, String>( new TextCell()) {
			@Override
			public String getValue(Curso curso) {
				return "" +  curso.getDescricao();
			}
		
		};
		dataGrid.addColumn(descricaoColumn,"Descrição");
		dataGrid.setColumnWidth(descricaoColumn, 50, Unit.PCT);
		
	}
	

}

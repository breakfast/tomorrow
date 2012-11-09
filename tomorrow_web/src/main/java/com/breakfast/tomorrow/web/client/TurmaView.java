package com.breakfast.tomorrow.web.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.breakfast.gwt.user.client.OptionPanel;
import com.breakfast.tomorrow.core.academic.vo.Disciplina;
import com.breakfast.tomorrow.core.academic.vo.Etapa;
import com.breakfast.tomorrow.core.academic.vo.Professor;
import com.breakfast.tomorrow.core.academic.vo.Turma;
import com.breakfast.tomorrow.web.client.async.ProfessorService;
import com.breakfast.tomorrow.web.client.async.ProfessorServiceAsync;
import com.breakfast.tomorrow.web.client.async.TurmaService;
import com.breakfast.tomorrow.web.client.async.TurmaServiceAsync;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.SelectionCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.ProvidesKey;

public class TurmaView extends Composite implements Editor<Turma> {

	private static TurmaViewUiBinder uiBinder = GWT
			.create(TurmaViewUiBinder.class);

	interface Driver extends SimpleBeanEditorDriver<Turma, TurmaView> {
	}

	interface TurmaViewUiBinder extends UiBinder<Widget, TurmaView> {
	}

	private TurmaServiceAsync service = GWT.create(TurmaService.class);
	private ProfessorServiceAsync serviceProfessor = GWT
			.create(ProfessorService.class);
	private Driver driver = GWT.create(Driver.class);

	Turma bean = new Turma();
	Collection<Turma> listBean = new ArrayList<Turma>();

	public TurmaView() {
		initWidget(uiBinder.createAndBindUi(this));
		driver.initialize(this);
		driver.edit(bean);
		lista.setVisible(false);
		setSelectionModel(dataGrid, selectionModel);
		createColumns(dataGrid);
		setListaProfessor();
		registerTab();
	}

	@UiField(provided = true)
	SimplePager pager = new SimplePager(TextLocation.LEFT);
	@UiField(provided = true)
	DataGrid<Turma> dataGrid = new DataGrid<Turma>();
	@UiField(provided = true)
	CellTable<Disciplina> gridDisciplina = new CellTable<Disciplina>();
	@UiField
	ListBox listaEtapa;
	@UiField
	Button btnSalvar;
	@UiField
	Button btnCancelar;
	@UiField
	Button btnExcluir;
	@UiField
	Button btnNovo;
	@UiField
	@Path("stringId")
	TextBox id;
	@UiField
	@Path("nomeTurma")
	TextBox nome;
	@UiField
	@Path("inicio")
	DateBox dataInicio;
	@UiField
	@Path("turno")
	TextBox turno;
	@UiField
	@Path("observacao")
	TextArea observacao;

	@UiField
	Button tabCadastro;
	@UiField
	Button tabLista;
	@UiField
	HTMLPanel cadastro;
	@UiField
	HTMLPanel lista;

	@UiHandler("btnNovo")
	void btnNovoOnClick(ClickEvent e) {
		bean = new Turma();
		driver.edit(bean);
	}

	@UiHandler("btnSalvar")
	void btnSalvarOnClick(ClickEvent e) {
		bean = driver.flush();
		salvarTurma(bean);
		listarTurmas(dataGrid);

	}

	private void btnListTabOnClick() {
		if (lista.isVisible()) {
			listarTurmas(dataGrid);
		}
		btnSalvar.setVisible(cadastro.isVisible());
		btnCancelar.setVisible(cadastro.isVisible());
		btnNovo.setVisible(cadastro.isVisible());

	}

	@UiHandler("btnCancelar")
	void btnCancelarOnClick(ClickEvent e) {
		driver.edit(bean);
	}

	ProvidesKey<Turma> keyProvider = new ProvidesKey<Turma>() {
		@Override
		public Object getKey(Turma turma) {
			return turma;
		}
	};

	/**
	 * Selection Model usado para a seleção de varias linhas (objetos) no
	 * dataGrid.
	 */
	MultiSelectionModel<Turma> selectionModel = new MultiSelectionModel<Turma>(
			keyProvider);

	/**
	 * Cria a colunas do dataGrid.
	 */

	public void salvarTurma(final Turma turma) {
		// validarAluno();
		service.persistir(turma, new AsyncCallback<Turma>() {
			@Override
			public void onSuccess(Turma turma) {
				bean = turma;
				driver.edit(bean);
				OptionPanel.showMessage("Registro salvo com sucesso.");
			}

			@Override
			public void onFailure(Throwable e) {
				OptionPanel.showMessage("Erro ao tentar salvar o registro", e);
			}
		});

	}

	public void listarTurmas(final DataGrid<Turma> dataGrid) {

		service.lista(new AsyncCallback<Collection<Turma>>() {

			@Override
			public void onFailure(Throwable e) {
				OptionPanel.showMessage("Erro ao tentar listar turmas.", e);
			}

			@Override
			public void onSuccess(Collection<Turma> list) {
				if (list != null) {
					listBean = list;
					dataGrid.setRowCount(list.size(), true);
					dataGrid.setRowData(new ArrayList<Turma>(list));
				}
			}

		});
	}

	public void excluirTurma(Set<Turma> turma) {
		service.excluir(turma, new AsyncCallback<Void>() {
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

	@UiHandler("btnExcluir")
	void btnExcluirOnClick(ClickEvent e) {
		if (cadastro.isVisible()) {
			Set<Turma> turmas = new TreeSet<Turma>();
			turmas.add(bean);
			excluirTurma(turmas);
			btnNovoOnClick(null);
		} else if (lista.isVisible()) {
			@SuppressWarnings("unchecked")
			MultiSelectionModel<Turma> selection = ((MultiSelectionModel<Turma>) dataGrid
					.getSelectionModel());
			excluirTurma(selection.getSelectedSet());
			listarTurmas(dataGrid);
		}

	}

	Map<Integer, Etapa> mapEtapa = new HashMap<Integer, Etapa>();

	public void setListaEtapa(final ListBox box, Collection<Etapa> list) {
		box.clear();
		int key = 0;
		for (Etapa etapa : list) {
			mapEtapa.put(key, etapa);
			box.addItem(etapa.getNomeEtapa());
			key++;
		}
		box.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent e) {
				Etapa et = mapEtapa.get(box.getSelectedIndex());
				gridDisciplina.setRowData((List<Disciplina>)et.getDiciplinas());
			}
		});
	}

	Map<String, Professor> mapNomeProfessor = new HashMap<String, Professor>();
	List<String> listNomeProfessor = new ArrayList<String>();
	public void setListaProfessor() {
		serviceProfessor.lista(new AsyncCallback<Collection<Professor>>() {
			@Override
			public void onFailure(Throwable arg0) {}

			@Override
			public void onSuccess(Collection<Professor> list) {
				String sem = new String("Sem Professor");
				listNomeProfessor.add(sem);
				mapNomeProfessor.put(sem, null);
				for(Professor p : list){
					listNomeProfessor.add(p.getNome());
					mapNomeProfessor.put(p.getNome(), p);
				}
				createColumns(gridDisciplina);
			}
		});
	}

	private void createColumns(final DataGrid<Turma> dataGrid) {

		Column<Turma, Boolean> checkColumn = new Column<Turma, Boolean>(
				new CheckboxCell(true, false)) {

			@Override
			public Boolean getValue(Turma turma) {
				return selectionModel.isSelected(turma);
			}
		};

		dataGrid.addColumn(checkColumn);
		dataGrid.setColumnWidth(checkColumn, 40, Unit.PX);

		Column<Turma, String> idColumn = new Column<Turma, String>(
				new TextCell()) {
			@Override
			public String getValue(Turma turma) {
				return "" + turma.getStringId();
			}

		};

		dataGrid.addColumn(idColumn, "Id");
		dataGrid.setColumnWidth(idColumn, 10, Unit.PCT);

		Column<Turma, String> nomeColumn = new Column<Turma, String>(
				new ClickableTextCell()) {

			@Override
			public String getValue(Turma turma) {

				return turma.getNomeTurma();
			}

			@Override
			public void onBrowserEvent(Context context, Element elem,
					Turma object, NativeEvent event) {
				super.onBrowserEvent(context, elem, object, event);
				if ("click".equals(event.getType())) {
					bean = object;
					driver.edit(bean);
					setListaEtapa(listaEtapa, bean.getEtapas());
					tabCadastro.click();
				}
			}

		};

		dataGrid.addColumn(nomeColumn, "Nome Turma");
		dataGrid.setColumnWidth(nomeColumn, 40, Unit.PCT);

		Column<Turma, String> inicioData = new Column<Turma, String>(
				new TextCell()) {

			@Override
			public String getValue(Turma turma) {

				return "" + turma.getInicio();
			}

		};

		dataGrid.addColumn(inicioData, "Inicio Turma");
		dataGrid.setColumnWidth(nomeColumn, 20, Unit.PCT);

		Column<Turma, String> curso = new Column<Turma, String>(new TextCell()) {

			@Override
			public String getValue(Turma turma) {
				return turma.getCurso() != null ? ""
						+ turma.getCurso().getNomeCurso() : "SEM CURSO";
			}

		};
		dataGrid.addColumn(curso, "Curso");
		dataGrid.setColumnWidth(curso, 30, Unit.PCT);
	}

	private void createColumns(CellTable<Disciplina> grid) {
		Column<Disciplina, String> nome = new Column<Disciplina, String>(
				new TextCell()) {

			@Override
			public String getValue(Disciplina d) {
				return d.getNomeDisciplina();
			}
		};
		grid.addColumn(nome, "Disciplina");
		grid.setColumnWidth(nome, 50, Unit.PCT);
		
		SelectionCell selectionCell = new SelectionCell(listNomeProfessor);
		Column<Disciplina, String> professor = new Column<Disciplina, String>(
				selectionCell) {
			@Override
			public String getValue(Disciplina o) {
				return o.getProfessor() != null ? ""
						+ o.getProfessor().getNome() : "";
			}
		};
		grid.addColumn(professor, "Professor");
		professor.setFieldUpdater(new FieldUpdater<Disciplina, String>() {
			@Override
			public void update(int x, Disciplina d, String s) {
				d.setProfessor(mapNomeProfessor.get(s));
			}
		});
		grid.setColumnWidth(professor, 50, Unit.PCT);
	}

	private void setSelectionModel(final DataGrid<Turma> dataGrid,
			final MultiSelectionModel<Turma> selectionModel) {
		dataGrid.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
		dataGrid.setSelectionModel(selectionModel,
				DefaultSelectionEventManager.<Turma> createCheckboxManager());
	}

	ClickHandler handler = new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			cadastro.setVisible(false);
			lista.setVisible(false);
			if (event.getSource().equals(tabCadastro)) {
				cadastro.setVisible(true);
			} else if (event.getSource().equals(tabLista)) {
				lista.setVisible(true);
			}
			btnListTabOnClick();
		}
	};

	public void registerTab() {
		tabCadastro.addClickHandler(handler);
		tabLista.addClickHandler(handler);
	}
}

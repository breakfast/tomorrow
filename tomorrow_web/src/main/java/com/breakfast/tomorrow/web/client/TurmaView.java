package com.breakfast.tomorrow.web.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import com.breakfast.gwt.user.client.OptionPanel;
import com.breakfast.tomorrow.core.academic.vo.Turma;
import com.breakfast.tomorrow.web.client.async.TurmaService;
import com.breakfast.tomorrow.web.client.async.TurmaServiceAsync;
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
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.ProvidesKey;

public class TurmaView extends Composite implements Editor<Turma> {

	private static TurmaViewUiBinder uiBinder = GWT.create(TurmaViewUiBinder.class);
	interface Driver extends SimpleBeanEditorDriver<Turma, TurmaView> {}
	interface TurmaViewUiBinder extends UiBinder<Widget, TurmaView> {}
	private TurmaServiceAsync service = GWT.create(TurmaService.class);
	private Driver driver = GWT.create(Driver.class);

	Turma bean = new Turma();
	Collection<Turma> listBean = new ArrayList<Turma>();

	public TurmaView() {
		initWidget(uiBinder.createAndBindUi(this));
		driver.initialize(this);
		driver.edit(bean);
		setSelectionModel(dataGrid, selectionModel);
		createColumns(dataGrid);
	}

	@UiField(provided = true)
	SimplePager pager = new SimplePager(TextLocation.LEFT);
	@UiField(provided = true)
	DataGrid<Turma> dataGrid = new DataGrid<Turma>();
	@UiField TabLayoutPanel tab;
	@UiField Button btnSalvar;
	@UiField Button btnCancelar;
	@UiField Button btnExcluir;
	@UiField Button btnNovo;
	@UiField @Path("stringId") TextBox id;
	@UiField @Path("nomeTurma")TextBox nome;
	@UiField @Path("inicio") DateBox dataInicio;
	@UiField @Path("turno") TextBox turno;
	@UiField @Path("observacao")TextArea observacao;

	public static int TAB_LIST = 1;
	public static int TAB_CAD = 0;

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

	@UiHandler("tab")
	void btnListTabOnClick(SelectionEvent<Integer> e) {
		if (e.getSelectedItem() == TAB_LIST) {
			listarTurmas(dataGrid);
		}
		btnSalvar.setVisible(e.getSelectedItem() == TAB_CAD);
		btnCancelar.setVisible(e.getSelectedItem() == TAB_CAD);
		btnNovo.setVisible(e.getSelectedItem() == TAB_CAD);
		// btnRelatorio.setVisible(e.getSelectedItem() == TAB_LIST);

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
				OptionPanel.showMessage("Erro ao tentar salvar o registro");
			}
		});

	}

	public void listarTurmas(final DataGrid<Turma> dataGrid) {

		service.lista(new AsyncCallback<Collection<Turma>>() {

			@Override
			public void onFailure(Throwable e) {
				OptionPanel.showMessage("Erro ao tentar listar turmas.");
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
		if (tab.getSelectedIndex() == TAB_CAD) {
			Set<Turma> turmas = new TreeSet<Turma>();
			turmas.add(bean);
			excluirTurma(turmas);
			btnNovoOnClick(null);
		} else if (tab.getSelectedIndex() == TAB_LIST) {
			@SuppressWarnings("unchecked")
			MultiSelectionModel<Turma> selection = ((MultiSelectionModel<Turma>) dataGrid
					.getSelectionModel());
			excluirTurma(selection.getSelectedSet());
			listarTurmas(dataGrid);
		}

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
					tab.selectTab(TAB_CAD);
				}
			}

		};

		dataGrid.addColumn(nomeColumn, "Nome Turma");
		dataGrid.setColumnWidth(nomeColumn, 90, Unit.PCT);
		
		
		Column<Turma, String> inicioData = new Column<Turma, String>(
				new TextCell()) {

			@Override
			public String getValue(Turma turma) {

				return "" + turma.getInicio();
			}

		};

		dataGrid.addColumn(inicioData, "Inicio Turma");
		dataGrid.setColumnWidth(nomeColumn, 90, Unit.PCT);
		
		
		Column<Turma, String> observacao = new Column<Turma, String>(
				new TextCell()) {

			@Override
			public String getValue(Turma turma) {

				return "" + turma.getObservacao();
			}

		};

		dataGrid.addColumn(observacao, "Observação");
		dataGrid.setColumnWidth(nomeColumn, 90, Unit.PCT);


	}

	private void setSelectionModel(final DataGrid<Turma> dataGrid,
			final MultiSelectionModel<Turma> selectionModel) {
		dataGrid.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
		dataGrid.setSelectionModel(selectionModel,
				DefaultSelectionEventManager.<Turma> createCheckboxManager());
	}
}

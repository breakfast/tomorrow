package com.breakfast.tomorrow.web.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import com.breakfast.gwt.user.client.OptionPanel;
import com.breakfast.tomorrow.core.academic.vo.Unidade;
import com.breakfast.tomorrow.web.client.async.UnidadeService;
import com.breakfast.tomorrow.web.client.async.UnidadeServiceAsync;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
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
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.ProvidesKey;

public class UnidadeView extends Composite implements Editor<Unidade> {

	private static UnidadeViewUiBinder uiBinder = GWT
			.create(UnidadeViewUiBinder.class);

	interface UnidadeViewUiBinder extends UiBinder<Widget, UnidadeView> {
	}

	interface Driver extends SimpleBeanEditorDriver<Unidade, UnidadeView> {
	}

	private UnidadeServiceAsync service = GWT.create(UnidadeService.class);
	private Driver driver = GWT.create(Driver.class);

	public UnidadeView() {
		initWidget(uiBinder.createAndBindUi(this));
		driver.initialize(this);
		driver.edit(bean);
		createColumns(dataGrid);
		setSelectionModel(dataGrid, selectionModel);	
		registerTab();
		lista.setVisible(false);
	}

	Unidade bean = new Unidade();
	Collection<Unidade> listBean = new ArrayList<Unidade>();

	@UiField(provided = true)SimplePager pager = new SimplePager(TextLocation.LEFT);
	@UiField(provided = true) DataGrid<Unidade> dataGrid = new DataGrid<Unidade>();
	@UiField Button btnSalvar;
	@UiField Button btnCancelar;
	@UiField Button btnExcluir;
	@UiField Button btnNovo;
	@UiField @Path("stringId")TextBox id;
	@UiField @Path("nomeUnidade")TextBox nomeUnidade;
	@UiField @Path("local") TextBox local;

	@UiField HTMLPanel cadastro;
	@UiField HTMLPanel lista;

	@UiHandler("btnNovo")
	void btnNovoOnClick(ClickEvent e) {
		bean = new Unidade();
		driver.edit(bean);
	}

	@UiHandler("btnSalvar")
	void btnSalvarOnClick(ClickEvent e) {
		bean = driver.flush();
		salvarUnidade(bean);
		listarUnidade(dataGrid);
	}

	@UiHandler("btnCancelar")
	void btnCancelarOnClick(ClickEvent e) {
		driver.edit(bean);
	}
	
	@UiHandler("btnExcluir")
	void btnExcluirOnClick(ClickEvent e) {
		if (cadastro.isVisible()) {
			Set<Unidade> unidades = new TreeSet<Unidade>();
			unidades.add(bean);
			excluirUnidade(unidades);
			btnNovoOnClick(null);
		} else if (lista.isVisible()) {
			@SuppressWarnings("unchecked")
			MultiSelectionModel<Unidade> selection = ((MultiSelectionModel<Unidade>) dataGrid.getSelectionModel());
			excluirUnidade(selection.getSelectedSet());
			listarUnidade(dataGrid);
		}
	}

	public void salvarUnidade(final Unidade unidade) {
		// validarAluno();
		service.persistir(unidade, new AsyncCallback<Unidade>() {
			@Override
			public void onSuccess(Unidade unidade) {
				bean = unidade;
				driver.edit(bean);
				OptionPanel.showMessage("Unidade salvada com sucesso.");
			}

			@Override
			public void onFailure(Throwable e) {
				OptionPanel.showMessage("Erro ao tentar salvar");
			}
		});

	}

	public void excluirUnidade(Set<Unidade> unidade) {
		service.excluir(unidade, new AsyncCallback<Void>() {
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

	public void listarUnidade(final DataGrid<Unidade> dataGrid) {

		service.lista(new AsyncCallback<Collection<Unidade>>() {

			@Override
			public void onFailure(Throwable e) {
				OptionPanel.showMessage("Erro ao tentar listar unidades.");
			}

			@Override
			public void onSuccess(Collection<Unidade> list) {
				if (list != null) {
					listBean = list;
					dataGrid.setRowCount(list.size(), true);
					dataGrid.setRowData(new ArrayList<Unidade>(list));
				}
			}

		});
	}

	ProvidesKey<Unidade> keyProvider = new ProvidesKey<Unidade>() {
		@Override
		public Object getKey(Unidade unidade) {
			return unidade;
		}
	};
	
	void btnListTabOnClick() {
		if (lista.isVisible()) {
			listarUnidade(dataGrid);
		}
		btnSalvar.setVisible(cadastro.isVisible());
		btnCancelar.setVisible(cadastro.isVisible());
		btnNovo.setVisible(cadastro.isVisible());
		//btnRelatorio.setVisible(lista.isVisible());

	}

	MultiSelectionModel<Unidade> selectionModel = new MultiSelectionModel<Unidade>(
			keyProvider);

	private void createColumns(final DataGrid<Unidade> dataGrid) {

		Column<Unidade, Boolean> checkColumn = new Column<Unidade, Boolean>(
				new CheckboxCell(true, false)) {

			@Override
			public Boolean getValue(Unidade unidade) {

				return selectionModel.isSelected(unidade);
			}
		};

		dataGrid.addColumn(checkColumn);
		dataGrid.setColumnWidth(checkColumn, 40, Unit.PX);

		Column<Unidade, String> idColumn = new Column<Unidade, String>(
				new TextCell()) {
			@Override
			public String getValue(Unidade unidade) {
				return "" + unidade.getStringId();
			}

		};
		dataGrid.addColumn(idColumn, "Id");
		dataGrid.setColumnWidth(idColumn, 10, Unit.PCT);

		Column<Unidade, String> nomeUni = new Column<Unidade, String>(
				new TextCell()) {
			@Override
			public String getValue(Unidade unidade) {
				return "" + unidade.getNomeUnidade();
			}

		};
		dataGrid.addColumn(nomeUni, "Nome Unidade");
		dataGrid.setColumnWidth(idColumn, 20, Unit.PCT);

		Column<Unidade, String> local = new Column<Unidade, String>(
				new TextCell()) {
			@Override
			public String getValue(Unidade unidade) {
				return "" + unidade.getLocal();
			}

		};
		dataGrid.addColumn(local, "Local");
		dataGrid.setColumnWidth(idColumn, 20, Unit.PCT);
	}
	
	private void setSelectionModel(final DataGrid<Unidade> dataGrid, final MultiSelectionModel<Unidade> selectionModel){
		dataGrid.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
		dataGrid.setSelectionModel(selectionModel, DefaultSelectionEventManager.<Unidade>createCheckboxManager());
	}

	@UiField Button tabCadastro;
	@UiField Button tabLista;
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

package com.breakfast.tomorrow.web.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import com.breakfast.gwt.user.client.OptionPanel;
import com.breakfast.tomorrow.core.academic.vo.Usuario;
import com.breakfast.tomorrow.web.client.async.UsuarioService;
import com.breakfast.tomorrow.web.client.async.UsuarioServiceAsync;
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
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.ProvidesKey;

public class UsuarioView extends Composite implements Editor<Usuario> {

	private static UsuarioViewUiBinder uiBinder = GWT.create(UsuarioViewUiBinder.class);
	interface UsuarioViewUiBinder extends UiBinder<Widget, UsuarioView> {}
	interface Driver extends SimpleBeanEditorDriver<Usuario, UsuarioView> {}
	private UsuarioServiceAsync service = GWT.create(UsuarioService.class);
	private Driver driver = GWT.create(Driver.class);
	//private ImageBundle bundle = GWT.create(ImageBundle.class);

	Usuario bean = new Usuario();
	Collection<Usuario> listBean = new ArrayList<Usuario>();

	public UsuarioView() {
		createColumns(dataGrid);
		setSelectionModel(dataGrid, selectionModel);		
		initWidget(uiBinder.createAndBindUi(this));
		driver.initialize(this);
		driver.edit(bean);
		registerTab();
		lista.setVisible(false);
	

	}

	
	@UiField(provided = true) SimplePager pager = new SimplePager(TextLocation.LEFT);
	@UiField(provided = true) DataGrid<Usuario> dataGrid = new DataGrid<Usuario>();
	
	@UiField HTMLPanel cadastro;
	@UiField HTMLPanel lista;

	
	@UiField Button btnSalvar;
	@UiField Button btnCancelar;
	@UiField Button btnExcluir;
	@UiField Button btnNovo;

    @UiField @Path("stringId") TextBox id;
	@UiField @Path("nome") TextBox nome;
	@UiField @Path("email") TextBox email;
	@UiField @Path("apelido")TextBox apelido;
	@UiField @Path("senha") PasswordTextBox senha;
	@UiField @Path("perfil") TextBox perfil;
	

	public static int TAB_LIST = 1;
	public static int TAB_CAD = 0;

	@UiHandler("btnNovo")
	void btnNovoOnClick(ClickEvent e) {
		bean = new Usuario();
		driver.edit(bean);
	}

	@UiHandler("btnSalvar")
	void btnSalvarOnClick(ClickEvent e) {
		bean = driver.flush();
		salvarUsuario(bean);
		listarUsuario(dataGrid);
	}
	
	
    @UiHandler("btnExcluir")
	void btnExcluirOnClick(ClickEvent e) {
		if (cadastro.isVisible()) {
			Set<Usuario> usuarios = new TreeSet<Usuario>();
			usuarios.add(bean);
			excluirUsuario(usuarios);
			btnNovoOnClick(null);
		} else if (lista.isVisible()) {
			@SuppressWarnings("unchecked")
			MultiSelectionModel<Usuario> selection = ((MultiSelectionModel<Usuario>) dataGrid.getSelectionModel());
			excluirUsuario(selection.getSelectedSet());
			listarUsuario(dataGrid);
		}
	}

	public void salvarUsuario(final Usuario usuario) {
		service.persistir(usuario, new AsyncCallback<Usuario>() {
			@Override
			public void onSuccess(Usuario usuario) {
				bean = usuario;
				driver.edit(bean);
				OptionPanel.showMessage("Registro salvo com sucesso.");
			}

			@Override
			public void onFailure(Throwable e) {
				OptionPanel.showMessage("Erro ao tentar salvar o registro");
			}
		});

	}

	public void listarUsuario(final DataGrid<Usuario> dataGrid) {

		service.lista(new AsyncCallback<Collection<Usuario>>() {

			@Override
			public void onFailure(Throwable e) {
				OptionPanel.showMessage("Erro ao tentar listar usuario.");
			}

			@Override
			public void onSuccess(Collection<Usuario> list) {
				if (list != null) {
					listBean = list;
					dataGrid.setRowCount(list.size(), true);
					dataGrid.setRowData(new ArrayList<Usuario>(list));
				}
			}

		});
	}

	public void excluirUsuario(Set<Usuario> usuario) {
		service.excluir(usuario, new AsyncCallback<Void>() {
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

	
	ProvidesKey<Usuario> keyProvider = new ProvidesKey<Usuario>() {
		@Override
		public Object getKey(Usuario usuario) {
			return usuario;
		}
	};

	MultiSelectionModel<Usuario> selectionModel = new MultiSelectionModel<Usuario>(
			keyProvider);

	private void createColumns(final DataGrid<Usuario> dataGrid) {

		Column<Usuario, Boolean> checkColumn = new Column<Usuario, Boolean>(
				new CheckboxCell(true, false)) {

			@Override
			public Boolean getValue(Usuario usuario) {
				return selectionModel.isSelected(usuario);
			}

		};

		dataGrid.addColumn(checkColumn);
		dataGrid.setColumnWidth(checkColumn, 40, Unit.PX);

		Column<Usuario, String> idColumn = new Column<Usuario, String>(
				new TextCell()) {
			@Override
			public String getValue(Usuario usuario) {
				return "" + usuario.getStringId();
			}

		};
		dataGrid.addColumn(idColumn, "Id");
		dataGrid.setColumnWidth(idColumn, 10, Unit.PCT);

		Column<Usuario, String> nomeUsuario = new Column<Usuario, String>(
				new ClickableTextCell()) {
			@Override
			public String getValue(Usuario usuario) {
				return "" + usuario.getNome();
			}
			
			@Override
			public void onBrowserEvent(Context context, Element elem,
					Usuario object, NativeEvent event) {
				super.onBrowserEvent(context, elem, object, event);
				if ("click".equals(event.getType())) {
					bean = object;
					driver.edit(bean);
					tabCadastro.click();
				}
			}

		};
		dataGrid.addColumn(nomeUsuario, "Nome");
		dataGrid.setColumnWidth(idColumn, 10, Unit.PCT);
		
		Column<Usuario, String> emailUsuario = new Column<Usuario, String>(
				new TextCell()) {
			@Override
			public String getValue(Usuario usuario) {
				return "" + usuario.getEmail();
			}

		};
		dataGrid.addColumn(emailUsuario, "Email");
		dataGrid.setColumnWidth(idColumn, 25, Unit.PCT);
		
		Column<Usuario, String> apelido = new Column<Usuario, String>(
				new TextCell()) {
			@Override
			public String getValue(Usuario usuario) {
				return "" + usuario.getApelido();
			}

		};
		dataGrid.addColumn(apelido, "Apelido");
		dataGrid.setColumnWidth(idColumn, 10, Unit.PCT);
		
		Column<Usuario, String> perfil = new Column<Usuario, String>(
				new TextCell()) {
			@Override
			public String getValue(Usuario usuario) {
				return "" + usuario.getPerfil();
			}

		};
		dataGrid.addColumn(perfil, "Perfil");
		dataGrid.setColumnWidth(idColumn, 20, Unit.PCT);


	}

	private void setSelectionModel(final DataGrid<Usuario> dataGrid,final MultiSelectionModel<Usuario> selectionModel) {
		dataGrid.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
		dataGrid.setSelectionModel(selectionModel,DefaultSelectionEventManager.<Usuario> createCheckboxManager());
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
	
	void btnListTabOnClick() {
		if (lista.isVisible()) {
			listarUsuario(dataGrid);
		}
		btnSalvar.setVisible(cadastro.isVisible());
		btnCancelar.setVisible(cadastro.isVisible());
		btnNovo.setVisible(cadastro.isVisible());
		//btnRelatorio.setVisible(lista.isVisible());

	}
}

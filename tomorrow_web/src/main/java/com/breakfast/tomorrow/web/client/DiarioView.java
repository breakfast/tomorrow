package com.breakfast.tomorrow.web.client;

import java.util.ArrayList;
import java.util.Collection;

import com.breakfast.gwt.user.client.OptionPanel;
import com.breakfast.tomorrow.core.academic.vo.Disciplina;
import com.breakfast.tomorrow.web.client.async.DiarioService;
import com.breakfast.tomorrow.web.client.async.DiarioServiceAsync;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class DiarioView extends Composite {

	private static DiarioViewUiBinder uiBinder = GWT.create(DiarioViewUiBinder.class);

	interface DiarioViewUiBinder extends UiBinder<Widget, DiarioView> {
	}
	
	private DiarioServiceAsync service = GWT.create(DiarioService.class);

	public DiarioView() {
		initWidget(uiBinder.createAndBindUi(this));
		createColumns(gridDiario);
	}
	
	@UiField CellTable<Disciplina> gridDiario = new CellTable<Disciplina>();
	
	@Override
	protected void onLoad() {
		super.onLoad();
		listar();
	}
	
	private void listar(){
		service.getDisciplinas(new AsyncCallback<Collection<Disciplina>>() {
			@Override
			public void onSuccess(Collection<Disciplina> disciplinas) {
				gridDiario.setRowData(new ArrayList<Disciplina>(disciplinas));
			}
			
			@Override
			public void onFailure(Throwable t) {
				OptionPanel.showMessage("Erro ao Listar Diarios", t);
			}
		});
	}
	
	private void createColumns(CellTable<Disciplina> gridDiario){
		
		Column<Disciplina, String> id = new Column<Disciplina, String>(new TextCell()) {
			@Override
			public String getValue(Disciplina d) {
				return "" + d.getId();
			}
		};
		gridDiario.addColumn(id,"id");
		gridDiario.setColumnWidth(id, 40, Unit.PX);
		
		Column<Disciplina, String> nome = new Column<Disciplina, String>(new TextCell()) {
			@Override
			public String getValue(Disciplina d) {
				return "" + d.getNomeDisciplina();
			}
		};
		gridDiario.addColumn(nome,"Nome");
		gridDiario.setColumnWidth(nome, 50, Unit.PCT);
		
		Column<Disciplina, String> professor = new Column<Disciplina, String>(new TextCell()) {
			@Override
			public String getValue(Disciplina d) {
				return d.getProfessor() != null ? d.getProfessor().getNome() : "Sem Professor";
			}
		};
		gridDiario.addColumn(professor,"Professor");
		gridDiario.setColumnWidth(professor, 50, Unit.PCT);
		
	}
	


}

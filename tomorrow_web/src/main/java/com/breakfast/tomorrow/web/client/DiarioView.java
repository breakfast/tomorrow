package com.breakfast.tomorrow.web.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.breakfast.gwt.user.client.OptionPanel;
import com.breakfast.tomorrow.core.academic.vo.Aluno;
import com.breakfast.tomorrow.core.academic.vo.Aula;
import com.breakfast.tomorrow.core.academic.vo.Disciplina;
import com.breakfast.tomorrow.web.client.async.DiarioService;
import com.breakfast.tomorrow.web.client.async.DiarioServiceAsync;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.datepicker.client.DateBox.Format;
import com.google.gwt.user.datepicker.client.DatePicker;

public class DiarioView extends Composite {

	private static DiarioViewUiBinder uiBinder = GWT.create(DiarioViewUiBinder.class);

	interface DiarioViewUiBinder extends UiBinder<Widget, DiarioView> {
	}
	
	private DiarioServiceAsync service = GWT.create(DiarioService.class);

	public DiarioView() {
		initWidget(uiBinder.createAndBindUi(this));
		createColumns(gridDiario);
		registerTab();
		panelAula.add(aulaTable);
	}

	@UiField Button btnSalvar;
	@UiField Button btnCancelar;
	@UiField SimplePanel panelAula;
	@UiField Button tabCadastro;
	@UiField Button tabLista;
	@UiField HTMLPanel cadastro;
	@UiField HTMLPanel lista;
	@UiField CellTable<Disciplina> gridDiario = new CellTable<Disciplina>();
	
	@UiField FlexTable tableDiario;
	@UiField TextBox qtde;
	
	AulaTable aulaTable = new AulaTable();
	
	@Override
	protected void onLoad() {
		super.onLoad();
		listar();
		alunos.clear();
		aulas.clear();
		//avaliacoes.clear()
		tableDiario.clear();
		montaDiario();
	}
	
	DateTimeFormat format = DateTimeFormat.getFormat("dd/MMM");
	int linha = 0;
	int coluna = 1;
	Collection<Aluno> alunos = new ArrayList<Aluno>();
	Collection<Aula> aulas = new ArrayList<Aula>();
	//Collection<Avaliacao> avaliacoes = new ArrayList<Avaliacao>();
	private void montaDiario(){
		
		String[] nomes = new String[]{"Cecilia Meireles",
					"Getulio Vargas", "Villa Lobos", "Machado de Asis", 
					"Monteiro Lobatto", "Tarsila Do Amaral", "Carlos Drummond de Andrade",
					"Mario de Andrade", "Fernando Meirelles", "Carlos Gomes"};
		
		int x = 0;
		while(x < 10){
			Aluno aluno = new Aluno();
			aluno.setNome(nomes[x]);
			alunos.add(aluno);
			x++;
		}
		
		Date[] datas = new Date[]{new Date("11/20/2012"), new Date("11/21/2012"),
				new Date("11/22/2012"),new Date("11/23/2012"),new Date("11/24/2012"),
				new Date("11/25/2012"),new Date("11/26/2012")};
		x = 0;
		while(x < 7){
			Aula aula = new Aula();
			aula.setData(datas[x]);
			aulas.add(aula);
			x++;
		}
		
		//montaHeader
		linha = 0;
		coluna = 2;

		for(Aula aula : aulas){
			addHeaderAula(aula);
			coluna++;
		}		
		
		//montaBody	
		linha = 1;
		for(Aluno aluno : alunos){
			coluna = 0;
			tableDiario.setWidget(linha, coluna, new HTMLPanel(aluno.getNome()));
			coluna++;
			CellFaltaAula qtdeFalta = new CellFaltaAula(0);
			tableDiario.setWidget(linha, coluna, qtdeFalta);
			coluna++;
			for(Aula aula : aulas){
				int faltas = 0;
				faltas = aula.getFaltantes().contains(aula) ? aula.getQtdeAulas() : 0;
				addColunaAula(aula.getQtdeAulas(), faltas);
				qtdeFalta.setText(""+faltas);
				coluna++;
			}
			linha++;
		}
		
		aulaTable.setData(aulas);
	}
	
	public void addHeaderAula(Aula aula){
		SimplePanel datePanel = new SimplePanel();
		datePanel.add(new HTML(DateTimeFormat.getFormat("dd/MMM").format(aula.getData())));
		tableDiario.setWidget(linha, coluna, datePanel);
	}
	
	public void addColunaAula(int qtdeAula, int qtdeFalta){
		String falta = "";
		int x = 0;
		while(x < qtdeFalta){
			falta += "F";
			x++;
		}
		CellFaltaAula cell = new CellFaltaAula(qtdeAula);
		cell.setText(falta);
		tableDiario.setWidget(linha, coluna, cell);
	}
	
	class CellFaltaAula extends TextBox{
		
		public CellFaltaAula(int creditoAula){
			this.setMaxLength(creditoAula);
			this.setWidth("40px");
			this.setHeight("10px");
		}
	
	}
	
	class AulaTable extends FlexTable{
		
		public AulaTable(){}
		
		public AulaTable(Collection<Aula> aulas){
			this.clear();
			setData(aulas);
		}
		
		int row = 0;
		public void setData(Collection<Aula> aulas){
			this.row = 0;
			if(aulas != null){
				for(Aula aula : aulas){
					addAula(aula, false);
				}
			}
			addAula(new Aula(), true);
		}
		
		public void addAula(Aula aula, boolean handler){
			final TextBox desc = new TextBox();
			desc.setWidth("415px");
			desc.setText(aula.getDescricao());
			this.setWidget(row, 2, desc);
			final DateBox data = new DateBox();
			data.setFormat(new DateBox.DefaultFormat(format));
			data.setWidth("70px");
			data.setValue(aula.getData());
			this.setWidget(row, 0, data);
			final TextBox creditos = new TextBox();
			creditos.setWidth("30px");
			this.setWidget(row, 1, creditos);
			creditos.setText(""+aula.getQtdeAulas());
			row++;
			if(handler) desc.addKeyDownHandler(new KeyDownHandler() {
				@Override
				public void onKeyDown(KeyDownEvent e) {
					if(e.getNativeKeyCode() == KeyCodes.KEY_ENTER){
						addAula(new Aula(),true);
						Aula aula = new Aula();
						aula.setData(data.getValue());
						linha=0;
						addHeaderAula(aula);
						linha++;
						for(Aluno aluno : alunos){
							addColunaAula(Integer.parseInt(creditos.getText()), 0);
							linha++;
						}
						coluna++;
					}
					
				}
			});
		}
		
		
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
	
	
	private void btnListTabOnClick() {
		if (lista.isVisible()) {
			listar();
		}
		btnSalvar.setVisible(cadastro.isVisible());
		btnCancelar.setVisible(cadastro.isVisible());

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
		cadastro.setVisible(false);
	}


}

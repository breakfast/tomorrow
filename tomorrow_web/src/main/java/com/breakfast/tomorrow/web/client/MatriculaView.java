package com.breakfast.tomorrow.web.client;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.breakfast.gwt.user.client.OptionPanel;
import com.breakfast.tomorrow.core.academic.vo.Aluno;
import com.breakfast.tomorrow.core.academic.vo.Curso;
import com.breakfast.tomorrow.core.academic.vo.Etapa;
import com.breakfast.tomorrow.core.academic.vo.Turma;
import com.breakfast.tomorrow.web.client.async.MatriculaService;
import com.breakfast.tomorrow.web.client.async.MatriculaServiceAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public class MatriculaView extends Composite {

	private static MatriculaViewUiBinder uiBinder = GWT
			.create(MatriculaViewUiBinder.class);

	interface MatriculaViewUiBinder extends UiBinder<Widget, MatriculaView> {
	}
	MatriculaServiceAsync service = GWT.create(MatriculaService.class);
	
	public Aluno alunoBean = new Aluno();
	
	Map<Integer, Curso> mapCurso = new HashMap<Integer, Curso>();
	Map<Integer, Turma> mapTurma = new HashMap<Integer, Turma>();
	Map<Integer, Etapa> mapEtapa = new HashMap<Integer, Etapa>();
	
	@UiField ListBox curso;
	@UiField ListBox turma;
	@UiField ListBox etapa;
	@UiField Label aluno;
	@UiField Button matricular;

	public MatriculaView() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	protected void onLoad() {
		super.onLoad();
		aluno.setText("[" + alunoBean.getId() + "]" + alunoBean.getNome());
		curso.clear();
		turma.clear();
		etapa.clear();
		service.getCursos(new AsyncCallback<Collection<Curso>>() {
			@Override
			public void onSuccess(Collection<Curso> cursos) {
				curso.addItem("");
				int key = 1;
				for(Curso c : cursos){
					mapCurso.put(key, c);
					curso.addItem(c.getNomeCurso());
					key++;
				}
			}
			
			@Override
			public void onFailure(Throwable t) {
				OptionPanel.showMessage("Erro ao Buscar Cursos", t);
			}
		});
	}
	
	@UiHandler("curso") void listCursoOnChange(ChangeEvent event){
		Curso c = mapCurso.get(curso.getSelectedIndex());
		turma.clear();
		service.getTurmasPorCurso(c, new AsyncCallback<Collection<Turma>>() {
			@Override
			public void onFailure(Throwable t) {
				OptionPanel.showMessage("Erro ao Buscar Turmas", t);
			}

			@Override
			public void onSuccess(Collection<Turma> turmas) {
				turma.addItem("");
				int key = 1;
				for(Turma t : turmas){
					mapTurma.put(key, t);
					turma.addItem(t.getNomeTurma());
					key++;
				}
			}
			
		});
	}
	
	@UiHandler("turma") void listTurmaOnChange(ChangeEvent event){
		Turma t = mapTurma.get(turma.getSelectedIndex());
		etapa.clear();
		service.getEtapasPorTurma(t, new AsyncCallback<Collection<Etapa>>() {
			@Override
			public void onFailure(Throwable t) {
				OptionPanel.showMessage("Erro ao Buscar Turmas", t);
			}

			@Override
			public void onSuccess(Collection<Etapa> etapas) {
				etapa.addItem("");
				int key = 1;
				for(Etapa e : etapas){
					mapEtapa.put(key, e);
					etapa.addItem(e.getNomeEtapa());
					key++;
				}
			}
			
		});
	}
	
	@UiHandler("matricular") void btnMatricularOnClick(ClickEvent e){
		OptionPanel.showConfirm("Deseja Matricular Aluno?", new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				/*
				service.matricularAluno(alunoBean, new AsyncCallback<Void>() {
					@Override
					public void onFailure(Throwable t) {
						OptionPanel.showMessage("Erro ao tentar Matricular Aluno!", t);
					}

					@Override
					public void onSuccess(Void arg0) {
						OptionPanel.showMessage("Aluno Matriculado com Sucesso!");
					}
				});
				*/
				service.gerarMatriculaPDF(alunoBean, new AsyncCallback<String>() {
					@Override
					public void onFailure(Throwable arg0) {}

					@Override
					public void onSuccess(String caminho) {
						Window.open("../" + caminho, "", "");
					}
				});
			}
		},null);
			
		
	} 
	
	

	

}

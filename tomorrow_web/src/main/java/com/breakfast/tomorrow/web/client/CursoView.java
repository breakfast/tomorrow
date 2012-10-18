package com.breakfast.tomorrow.web.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DragStartEvent;
import com.google.gwt.event.dom.client.DragStartHandler;
import com.google.gwt.event.dom.client.DropEvent;
import com.google.gwt.event.dom.client.DropHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


public class CursoView extends Composite {

	private static CursoViewUiBinder uiBinder = GWT.create(CursoViewUiBinder.class);
	interface CursoViewUiBinder extends UiBinder<Widget, CursoView> {}

	public CursoView() {
		initWidget(uiBinder.createAndBindUi(this));
		initConfig();
	}
	
	@UiField VerticalPanel configuracaoPanel;
	Hyperlink addEtapa = new Hyperlink("Adicionar Etapa","#");
	
	private void initConfig(){
		this.configuracaoPanel.setWidth("100%");
		this.configuracaoPanel.setSpacing(7);
		addEtapa.addHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent arg0){
				final VerticalPanel etapa = new EtapaUI();
				configuracaoPanel.add(etapa);
			}
		}, ClickEvent.getType());
		this.configuracaoPanel.add(addEtapa);
	}
		
		
	public void adcicionarEtapa(HorizontalPanel etapaUI){
		configuracaoPanel.add(etapaUI);
	}
	
	class EtapaUI extends VerticalPanel{
		final FlowPanel flow = new FlowPanel();
		public EtapaUI() {
			this.setWidth("100%");
			this.getElement().getStyle().setBackgroundColor("#F5F5F5");
			flow.setWidth("100%");
			Hyperlink addDisciplina = new Hyperlink("Adicionar Disciplina", "#");
			addDisciplina.addHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					DisciplinaUI disciplina = new DisciplinaUI();
					flow.add(disciplina);
				}
			}, ClickEvent.getType());
			this.add(addDisciplina);
			this.add(flow);
		}
		
		public void addDisciplina(DisciplinaUI ui){
			this.add(ui);
		}
	}
	

	class DisciplinaUI extends TextBox{
		public DisciplinaUI(){
			this.setWidth("150px");
			this.getElement().getStyle().setBackgroundColor("#f5f5c4");	
		}
	}
	
	
	

}

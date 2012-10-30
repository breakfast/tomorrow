package com.breakfast.tomorrow.web.client;

import com.breakfast.tomorrow.web.client.resources.ImageBundle;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ApplicationMenu extends Composite implements HasClickHandlers{

	private static ApplicationMenuUiBinder uiBinder = GWT.create(ApplicationMenuUiBinder.class);

	interface ApplicationMenuUiBinder extends UiBinder<Widget, ApplicationMenu> {
	}
	
	ImageBundle resources = GWT.create(ImageBundle.class);
	ApplicationConstants constants = GWT.create(ApplicationConstants.class);

	public ApplicationMenu() {
		initWidget(uiBinder.createAndBindUi(this));
		initHyperLinkMenus(constants, resources);
	}
	
	@UiField VerticalPanel menuPanel;
	
	@UiField Hyperlink cadastrarCurso;
	@UiField Hyperlink matricularAluno;
	@UiField Hyperlink aluno;
	@UiField Hyperlink professor;
	@UiField Hyperlink turma;
	@UiField Hyperlink curso;
	@UiField Hyperlink configuracoes;
	
	

	@SuppressWarnings("deprecation")
	@Override
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		for(int x = 0; x < menuPanel.getWidgetCount(); x++){
			Widget w = menuPanel.getWidget(x);
			if(w instanceof Hyperlink){
				Hyperlink h = (Hyperlink) w;
				h.addClickHandler(handler);
			}
		}
		return null;
	}
	
	/**
	 * Inicia os hyperlinks do menu da aplicação
	 * @param constants
	 * @param resources
	 */
	private void initHyperLinkMenus(ApplicationConstants constants, ImageBundle resources){
		
		cadastrarCurso.setHTML(getFormatedMenu(resources.cursos(), constants.dashboard()));
		matricularAluno.setHTML(getFormatedMenu(resources.matriculaAluno(), constants.diario()));
		aluno.setHTML(getFormatedMenu(resources.alunos(), constants.aluno()));
		professor.setHTML(getFormatedMenu(resources.professores(), constants.professor()));
		turma.setHTML(getFormatedMenu(resources.turmas(), constants.turma()));
		curso.setHTML(getFormatedMenu(resources.cursos(), constants.curso()));
		configuracoes.setHTML(getFormatedMenu(resources.configuracoes(), constants.configuracoes()));
		
	}
	
	private String getFormatedMenu(ImageResource resource, String text){
		Image img = new Image(resource);
		String htmlImage ="<img style=\"float : left; \" src=\"" + img.getUrl() + "\" >";
		String htmlText = "<p style=\" margin-top:2%; margin-left: 40px;\">" + text + "</p>";
		return htmlImage + htmlText;
	}
	

}

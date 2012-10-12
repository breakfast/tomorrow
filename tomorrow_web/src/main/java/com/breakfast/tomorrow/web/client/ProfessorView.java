package com.breakfast.tomorrow.web.client;

import com.breakfast.tomorrow.web.client.vo.ProfessorVO;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class ProfessorView extends Composite implements Editor<ProfessorVO> {
	
	

	private static ProfessorViewUiBinder uiBinder = GWT.create(ProfessorViewUiBinder.class);
	interface ProfessorViewUiBinder extends UiBinder<Widget, ProfessorView>{}
	interface Driver extends SimpleBeanEditorDriver<ProfessorVO, ProfessorView>{}
	

	

	public ProfessorView() {
		initWidget(uiBinder.createAndBindUi(this));
	
	}

	@UiField @Path("idPessoa") TextBox id;
	@UiField @Path("nome") TextBox nome;
	@UiField @Path("endereco") TextBox endereco;
	@UiField @Path("telefone") TextBox telefone;
	@UiField @Path("complemento") TextBox complemento;
	@UiField @Path("distrito") TextBox bairro;
	@UiField @Path("cidade") TextBox cidade;
	@UiField @Path("codigoPostal") TextBox cep;
	@UiField @Path("email") TextBox email;

	
}

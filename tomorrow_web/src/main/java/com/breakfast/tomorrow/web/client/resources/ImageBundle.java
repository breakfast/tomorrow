package com.breakfast.tomorrow.web.client.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface ImageBundle extends ClientBundle {

	@Source("logo-dev.png")
	ImageResource logo();
	
	@Source("matricularAluno32.png")
	ImageResource matriculaAluno();
	
	@Source("configuracoes32.png")
	ImageResource configuracoes();
	
	@Source("alunos32.png")
	ImageResource alunos();
	
	@Source("professores32.png")
	ImageResource professores();
	
	@Source("turmas32.png")
	ImageResource turmas();
	
	@Source("cursos32.png")
	ImageResource cursos();
}

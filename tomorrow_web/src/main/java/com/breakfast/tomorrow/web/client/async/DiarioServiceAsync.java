package com.breakfast.tomorrow.web.client.async;

import java.util.Collection;

import com.breakfast.tomorrow.core.academic.vo.Diario;
import com.breakfast.tomorrow.core.academic.vo.Disciplina;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DiarioServiceAsync {

	void getDiarios(AsyncCallback<Collection<Diario>> callback);

	void persistir(Diario diario, AsyncCallback<Diario> callback);

	void getDisciplinas(AsyncCallback<Collection<Disciplina>> callback);

}

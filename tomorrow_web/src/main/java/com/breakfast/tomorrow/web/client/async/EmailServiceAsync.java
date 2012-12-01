package com.breakfast.tomorrow.web.client.async;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface EmailServiceAsync {

	void enviarEmail(String menssagem, String email,AsyncCallback<Void> callback);

}

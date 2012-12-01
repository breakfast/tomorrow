package com.breakfast.tomorrow.web.client.async;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


@RemoteServiceRelativePath("emailServ")
public interface EmailService extends RemoteService {

	 void enviarEmail(String menssagem , String email);
	 
}

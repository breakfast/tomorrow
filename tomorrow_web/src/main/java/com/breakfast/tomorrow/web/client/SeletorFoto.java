package com.breakfast.tomorrow.web.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SeletorFoto extends Composite{

	private static FileUpload fileUpload = new FileUpload();
	private static PopupPanel popup = new PopupPanel();
	private static Label label = new Label("Altera Foto");
	
	public static void init(){
		VerticalPanel panel = new VerticalPanel();
		panel.add(label);
		panel.add(fileUpload);
		popup.setGlassEnabled(true);
		popup.setAutoHideEnabled(true);
		popup.center();
		popup.add(panel);
	}
	
	public static String getPathFoto(){
		init();
		popup.show();
		return fileUpload.getFilename();
	}
	
	

}

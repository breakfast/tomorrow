package com.breakfast.gwt.user.client;

import com.breakfast.gwt.user.client.resources.ResourceBundle;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class TextBoxDropPanel extends Composite{

	private static TextBoxDropPanelUiBinder uiBinder = GWT
			.create(TextBoxDropPanelUiBinder.class);

	interface TextBoxDropPanelUiBinder extends
			UiBinder<Widget, TextBoxDropPanel> {
	}
	
	ResourceBundle resource = GWT.create(ResourceBundle.class);

	public TextBoxDropPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		initdownButtonPanelClickHandler();
	}
	
	@UiField TextBox textBox;
	@UiField Button downButtonPanel;
	
	private void initdownButtonPanelClickHandler(){
		downButtonPanel.setHTML("<image src=' " + new Image(resource.downButton()).getUrl() + "'/ >");
		downButtonPanel.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				//TODO Painel de filtro e pesquisa
				Window.alert("Heloo");
			}
		});
	}
}

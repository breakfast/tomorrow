package com.breakfast.gwt.user.client;

import com.breakfast.gwt.user.client.resources.ResourceBundle;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
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
	}
	
	@UiField TextBox textBox;
	@UiField SimplePanel downButtonPanel;

}

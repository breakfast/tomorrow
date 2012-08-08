package com.breakfast.tomorrow.web.client;

import com.breakfast.gwt.user.client.TextBoxDropPanel;
import com.breakfast.tomorrow.web.client.resources.ImageBundle;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class ApplicationHeader extends Composite{

	private static ApplicationHeaderUiBinder uiBinder = GWT
			.create(ApplicationHeaderUiBinder.class);

	interface ApplicationHeaderUiBinder extends
			UiBinder<Widget, ApplicationHeader> {
	}

	ImageBundle res = GWT.create(ImageBundle.class);

	public ApplicationHeader() {
		initWidget(uiBinder.createAndBindUi(this));
		
		HorizontalPanel flow = new HorizontalPanel();
		flow.setSpacing(7);
		
		Image logo = new Image(res.logo());
		logo.setStyleName("image-logo");
		flow.add(logo);
		
		TextBoxDropPanel searchAndFilterTexBox = new TextBoxDropPanel();
		searchAndFilterTexBox.addStyleName("search-filter-textbox");
		flow.add(searchAndFilterTexBox);
		
		Button searchAndFilterButton = new Button();
		searchAndFilterButton.setStyleName("gwt-Button-blue");
		searchAndFilterButton.setText("Pesquisar");
		searchAndFilterButton.addStyleName("search-filter-textbox");
		flow.add(searchAndFilterButton);

		panel.add(flow);
		
	}
	
	@UiField HTMLPanel panel;

}

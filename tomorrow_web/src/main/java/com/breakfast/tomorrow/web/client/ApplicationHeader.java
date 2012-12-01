package com.breakfast.tomorrow.web.client;

import com.breakfast.tomorrow.web.client.resources.ImageBundle;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.Widget;

public class ApplicationHeader extends Composite{

	private static ApplicationHeaderUiBinder uiBinder = GWT
			.create(ApplicationHeaderUiBinder.class);

	interface ApplicationHeaderUiBinder extends
			UiBinder<Widget, ApplicationHeader> {
	}

	private ImageBundle res = GWT.create(ImageBundle.class);
	private MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();
	private SuggestBox searchAndFilterTexBox = new SuggestBox(oracle);

	public ApplicationHeader() {
		initWidget(uiBinder.createAndBindUi(this));
		
		HorizontalPanel flow = new HorizontalPanel();
		flow.setSpacing(7);
		
		Image logo = new Image(res.logo());
		logo.setStyleName("image-logo");
		flow.add(logo);
		
		
		searchAndFilterTexBox.addStyleName("search-filter-textbox");
		searchAndFilterTexBox.setWidth("400px");
		flow.add(searchAndFilterTexBox);
		
		Button searchAndFilterButton = new Button();
		searchAndFilterButton.setStyleName("gwt-Button-blue");
		searchAndFilterButton.setText("Pesquisar");
		searchAndFilterButton.addStyleName("search-filter-textbox");
		flow.add(searchAndFilterButton);
		
		Image userFoto = new Image(res.fotoLogin());
		flow.add(userFoto);

		panel.add(flow);
		
	}
	
	@UiField HTMLPanel panel;
	
	public SuggestBox getSearchText(){
		return this.searchAndFilterTexBox;
	}
	
	public MultiWordSuggestOracle getOracle(){
		return this.oracle;
	}

}

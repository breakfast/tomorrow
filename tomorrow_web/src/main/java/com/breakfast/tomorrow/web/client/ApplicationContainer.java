package com.breakfast.tomorrow.web.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class ApplicationContainer extends Composite {

	private static ApplicationContainerUiBinder uiBinder = GWT
			.create(ApplicationContainerUiBinder.class);

	interface ApplicationContainerUiBinder extends
			UiBinder<Widget, ApplicationContainer> {
	}

	public ApplicationContainer() {
		initWidget(uiBinder.createAndBindUi(this));
	}



	

}

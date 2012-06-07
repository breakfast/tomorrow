package com.breakfast.gwt.user.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;


/**
 * 
 * @author Kleber Ilario
 *
 */
public class DropPanelClickHandler implements ClickHandler {

	private PopupPanel popup = new PopupPanel();
	private Widget relativeTo = null;
	
	public DropPanelClickHandler(Panel dropPanel){
		this.popup.setWidget(dropPanel);
	}
	
	public DropPanelClickHandler(Panel dropPanel, Widget relativeTo){
		this.popup.setWidget(dropPanel);
		this.relativeTo = relativeTo;
	}
	
	public DropPanelClickHandler(Panel dropPanel, PopupPanel popup){
		this.popup = popup;
		this.popup.setWidget(dropPanel);
	}
	
	public DropPanelClickHandler(Panel dropPanel, Widget relativeTo, PopupPanel popup){
		this.popup = popup;
		this.popup.setWidget(dropPanel);
		this.relativeTo = relativeTo;
	}
	
	public PopupPanel getPopup() {
		return popup;
	}

	public void setPopup(PopupPanel popup) {
		this.popup = popup;
	}

	public Widget getRelativeTo() {
		return relativeTo;
	}

	public void setRelativeTo(Widget relativeTo) {
		this.relativeTo = relativeTo;
	}

	@Override
	public void onClick(ClickEvent event) {
		Widget source = (Widget) event.getSource();
		popup.setAutoHideEnabled(true);
		if(relativeTo == null){
			relativeTo = source;
		}
		popup.showRelativeTo(relativeTo);
	}
	
}

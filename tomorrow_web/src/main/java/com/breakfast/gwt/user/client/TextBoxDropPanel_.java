package com.breakfast.gwt.user.client;

import com.breakfast.gwt.user.client.resources.ResourceBundle;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * UI Component for Text Area presents Panel at click 
 * @author Kleber Ilario <mailto: kleberilario@gmail.com>
 *
 */
public class TextBoxDropPanel_ extends HorizontalPanel{

	
	//private Image dropButton = new Image(resource.downButton());
	private TextBox textBox = new TextBox();
	private AbsolutePanel layoutPanel = new AbsolutePanel();
	private CloseablePopupPanel popup = new CloseablePopupPanel();
	
	private static final int MARGIN_UI = 2;

	
	private Panel dropPanel;
	
	/**
	 * contructor with widget drop panel and width size in pixels, if w is null, throw a exception.
	 * @param panel
	 */
	public TextBoxDropPanel_(Panel panel, int width){
		super();
		if(panel == null){
			throw new IllegalArgumentException("reference of the widget is null in TextBoxDropPanel");
		}
		this.paint(panel,width);
	}
	
	/**
	 * Paint UI Componente adding Widget with drop box Panel
	 * @see DropPanelClickHandler
	 * @param (Widget)w
	 */
	private void paint(Panel panel, int width){
		
		//add textbox and setStyle
		layoutPanel.add(textBox);
		
		//dropButton.setSize("20px", "20px");
		
		//add DROPPanelClick handler, set width style and add dropbutton on layout panel. 
		//dropButton.addClickHandler(new DropPanelClickHandler(panel,textBox,popup));
		//layoutPanel.add(dropButton, width -(DROP_BUTTON_WIDTH) + (MARGIN_UI*2),7);
		this.add(layoutPanel);

		this.setWidth(width +"px");		
		this.setStyle();

	}
	
	private void setStyle(){
		textBox.addStyleName("ui-textboxdroppanel-textbox");
		//dropButton.addStyleName("ui-textboxdroppanel-dropbutton");
		Widget.setStyleName(layoutPanel.getElement(), "ui-textdroppanel");
		
	}
	
	/**
	 * widget used for drop panel in text area
	 * @return (Widget) drop panel;
	 */
	public Panel getDropPanel() {
		return dropPanel;
	}

	/**
	 * this method add widget of drop panel.
	 * @param (Widget) dropPanel
	 */
	public void setDropPanel(Panel dropPanel) {
		this.dropPanel = dropPanel;
	}
	
	@Override
	public void setWidth(String width){
		super.setWidth(width);
		textBox.setWidth(width);	
	}
	

}

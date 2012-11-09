package com.breakfast.gwt.user.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * Container dos componentes para a exibição de Mensagens para Alertas e Confirmações para o usuário
 * @author kleberilario.gmail.com
 *
 */
public class OptionPanel extends Composite {

	private static AlertUiBinder uiBinder = GWT.create(AlertUiBinder.class);

	interface AlertUiBinder extends UiBinder<Widget, OptionPanel> {
	}

	public OptionPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@UiField Button btnOK;
	@UiField Label text;
	@UiField Button btnCancel;
	@UiField TextBox prompt;
	@UiField DisclosurePanel panelE;
	@UiField TextArea exception;
	
	static PopupPanel popup;
	static OptionPanel widget;
	
	
	
	/**
	 * <p>Exibi um popup para usuário notficando a mensagem passada para o Usuário.</p>
	 * @param message
	 */
	public static void showMessage(String message, Throwable e){
		popup = new PopupPanel();
		widget = new OptionPanel();
		widget.text.setText(message);
		widget.text.getElement().setAttribute("color", "#D14836");
		widget.btnOK.addClickHandler(showMessageAndConfirmBtnOnClickOk());
		widget.btnCancel.setVisible(false);
		if(e!=null){
			widget.panelE.setVisible(true);
			widget.exception.setVisible(true);
			widget.exception.setText(e.getCause()!=null?e.getCause().toString():""+e);
		}
		popup.setGlassEnabled(true);
		popup.setWidget(widget);
		popup.center();
		popup.setModal(true);
		popup.show();
	}
	
	/**
	 * <p>Exibi um popup para usuário notficando a mensagem em vermelho passada para o Usuário..</p>
	 * @param message
	 * @param throwable
	 */
	public static void showMessage(String message){
		showMessage(message,null);
	}
	
	/**
	 * <p>Exibi uma mensagem para confirmação do usuário</p> 
	 * <p>Como argumento, devem ser passados dois ClickHandler`s, um para a OK da confirmação, e outro CANCEL para não confirmação
	 * <code>
	 * OptionPanel.showConfirm("Sua Mensagem",
			new ClickHandler() {
				@Override
				public void onClick(ClickEvent arg0) {
					Window.alert("OK");
				}
			},
			new ClickHandler() {
				@Override
				public void onClick(ClickEvent arg0) {
					Window.alert("Cancel");
				}
			}
		);
	 * 
	 * </code>  
	 * @param message - Mensagem da confirmação
	 * @param okClickHandler - handler que será executado ao clicar OK
	 * @param cancelClickHandler - handler que será executado ao clicar Cancel 
	 */
	public static void showConfirm(String message, ClickHandler okClickHandler, ClickHandler cancelClickHandler){
		showConfirm(message, okClickHandler, cancelClickHandler, false);
	}
	
	private static ClickHandler showMessageAndConfirmBtnOnClickOk() {
		return new ClickHandler() {
			@Override
			public void onClick(ClickEvent arg0) {
				popup.hide();
			}
		};
		
	}
	
	public static void showConfirm(String message, ClickHandler okClickHandler, ClickHandler cancelClickHandler, boolean prompt){
		popup = new PopupPanel();
		widget = new OptionPanel();
		widget.prompt.setVisible(prompt);
		widget.text.setText(message);
		widget.btnOK.addClickHandler(okClickHandler);
		if(cancelClickHandler!=null)
			widget.btnCancel.addClickHandler(cancelClickHandler);
		widget.btnOK.addClickHandler(showMessageAndConfirmBtnOnClickOk());
		widget.btnCancel.addClickHandler(showMessageAndConfirmBtnOnClickOk());
		popup.setGlassEnabled(true);
		popup.setWidget(widget);
		popup.center();
		popup.setModal(true);
		popup.setAutoHideOnHistoryEventsEnabled(true);
		popup.show();
	}
	
	public static void showPrompt(String message, String result, ClickHandler okClickHandler, ClickHandler cancelClickHandler){
		showConfirm(message, okClickHandler, cancelClickHandler, true);
		result = widget.prompt.getValue();
		Window.alert(widget.prompt.getValue());
		Window.alert(result);
	}
	

}

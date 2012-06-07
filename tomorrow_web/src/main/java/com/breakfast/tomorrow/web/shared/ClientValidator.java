package com.breakfast.tomorrow.web.shared;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.user.client.ui.TextBoxBase;

/**
 * Classe para validação de campos no lado do servidor.
 * @author Kleber Ilario.
 *
 */
public class ClientValidator {
	
	
	/**
	 * Valida o textbox de de acordo com as strings validations
	 * ex: validate(txtNome,style-error,"notnull","length>5")
	 * @param textBox
	 * @param validations
	 */
	public static boolean validate(TextBoxBase textBox, String style, String...validations){
		
		for(String stringValidate : validations){
			
			if(stringValidate.equals(NOT_NULL)){
				if(textBox.getText().trim().equals("")){
					textBox.addStyleName(style);
					return false;
				}
			}
			
			if(stringValidate.contains(LENGTH)){
				int len = stringValidate.lastIndexOf(">") + 1;
				String sub = stringValidate.substring(len).trim();
				if(len > 0){
					if(textBox.getText().length() > Integer.parseInt(sub)){
						textBox.addStyleName(style);
						return false;
					}
				}
				len = stringValidate.lastIndexOf("<") + 1;
				sub = stringValidate.substring(len).trim();
				if(len > 0){
					if(textBox.getText().length() < Integer.parseInt(sub)){
						textBox.addStyleName(style);
						return false;
					}
				}
				len = stringValidate.lastIndexOf(">=") + 1;
				sub = stringValidate.substring(len).trim();
				if(len > 0){
					if(textBox.getText().length() >= Integer.parseInt(sub)){
						textBox.addStyleName(style);
						return false;
					}
				}
				len = stringValidate.lastIndexOf("<=") + 1;
				sub = stringValidate.substring(len).trim();
				if(len > 0){
					if(textBox.getText().length() <= Integer.parseInt(sub)){
						textBox.addStyleName(style);
						return false;
					}
				}
				
			}
		}
		textBox.removeStyleName(style);
		return true;
		
	}
	
	public void register(final TextBoxBase textBox, final String style,final  String...validations){
		textBox.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent arg0) {
				if(ClientValidator.validate(textBox, style, validations)){
					
				}	
				
			}
		});
	}
	
	private static final String NOT_NULL = "notnull";
	private static final String LENGTH = "length";
	
}

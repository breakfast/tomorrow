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
	
	private boolean validate = false;
	
	public boolean isValid(){
		return validate;
	}
	
	
	/**
	 * Valida o textbox de de acordo com as strings validations
	 * ex: validate(txtNome,style-error,"notnull","length>5")
	 * @param textBox
	 * @param validations
	 */
	public static boolean validate(TextBoxBase textBox, String message, String style, String...validations){
		register(textBox, message, style, validations);
		for(String stringValidate : validations){
			
			if(stringValidate.equals(NOT_NULL)){
				if(textBox.getText().trim().equals("")){
					textBox.addStyleName(style);
					textBox.setTitle(message);
					return false;
				}
			}
			
			if(stringValidate.contains(LENGTH)){
				int len = stringValidate.lastIndexOf(">") + 1;
				String sub = stringValidate.substring(len).trim();
				if(len > 0){
					if(textBox.getText().length() > Integer.parseInt(sub)){
						textBox.addStyleName(style);
						textBox.setTitle(message);
						return false;
					}
				}
				len = stringValidate.lastIndexOf("<") + 1;
				sub = stringValidate.substring(len).trim();
				if(len > 0){
					if(textBox.getText().length() < Integer.parseInt(sub)){
						textBox.addStyleName(style);
						textBox.setTitle(message);
						return false;
					}
				}
				len = stringValidate.lastIndexOf(">=") + 1;
				sub = stringValidate.substring(len).trim();
				if(len > 0){
					if(textBox.getText().length() >= Integer.parseInt(sub)){
						textBox.addStyleName(style);
						textBox.setTitle(message);
						return false;
					}
				}
				len = stringValidate.lastIndexOf("<=") + 1;
				sub = stringValidate.substring(len).trim();
				if(len > 0){
					if(textBox.getText().length() <= Integer.parseInt(sub)){
						textBox.addStyleName(style);
						textBox.setTitle(message);
						return false;
					}
				}
				
			}
		}
		//textBox.removeStyleName(style);
		return true;
		
	}
	
	public static void register(final TextBoxBase textBox, final String message, final String style,final  String...validations){
		textBox.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent arg0) {
				textBox.removeStyleName(style);
				//validate = ClientValidator.validate(textBox, message, style, validations);
			}
		});
	}
	
	private static final String NOT_NULL = "notnull";
	private static final String LENGTH = "length";
	
}

package com.breakfast.base;

import java.io.DataInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class Tool {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		generateNeoObjectGettersAndSetters(Pessoa.class);
	}
	
	public static String captalize(String text){
		return text.substring(0,1).toUpperCase() + text.substring(1, text.length());
	}
	
	
	public static void generateNeoObjectGettersAndSetters(Class<?> clazz){
		System.out.println("Gererate NeoObject for " + clazz.getName());
		StringBuilder text = new StringBuilder();
		for(Field staticField : clazz.getFields()){
			if (Modifier.isStatic(staticField.getModifiers())){
				try{
					Object field = staticField.get(null);
					
					System.out.print("Insert Type for Neo Field " + staticField.getName() + " ([enter] for String / [0] for ignore :)");
					String fieldType = (String) new DataInputStream(System.in).readUTF();
					if(fieldType.equals("0")) continue;
					else if (fieldType.equals("")){
						fieldType = "String";
					}
					text.append(getNeoDeclaredMethod(staticField.getName(), field.toString(), fieldType));
					
				}
				catch (Exception e) {
					text.append("\n" + staticField.getName() + " : " + e.getMessage());
				}
			}
		}
		System.out.print("\n\n\n GENERATE SOURCES\n\n\n\n");
		System.out.println(text.toString());
	}
	
	
	public static String getNeoDeclaredMethod(String staticField, String field, String fieldType){
		return "public "+ fieldType + " get" + captalize(field) + "() {\n" +
				"	this." + field + " = node != null ? (" + fieldType + ") node.getProperty(" + staticField + ") : this." + field + ";\n" +
				"	return this." + field + ";\n}\n\n" +
				"public void set" + captalize(field) + "(" + fieldType + " " + field + ") {\n" +
				"	this." + field + " = " + field + ";\n}\n\n"; 
		
	}

}

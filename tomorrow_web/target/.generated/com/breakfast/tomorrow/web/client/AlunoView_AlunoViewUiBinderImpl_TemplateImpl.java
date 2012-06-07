package com.breakfast.tomorrow.web.client;

public class AlunoView_AlunoViewUiBinderImpl_TemplateImpl implements com.breakfast.tomorrow.web.client.AlunoView_AlunoViewUiBinderImpl.Template {
  
  public com.google.gwt.safehtml.shared.SafeHtml html2() {
    StringBuilder sb = new java.lang.StringBuilder();
    sb.append("Lista Alunos");
return new com.google.gwt.safehtml.shared.OnlyToBeUsedInGeneratedCodeStringBlessedAsSafeHtml(sb.toString());
}

public com.google.gwt.safehtml.shared.SafeHtml html1(java.lang.String arg0) {
StringBuilder sb = new java.lang.StringBuilder();
sb.append("<table style='width:100%'> <tr> <td align='left'> <span id='");
sb.append(com.google.gwt.safehtml.shared.SafeHtmlUtils.htmlEscape(arg0));
sb.append("'></span> </td> </tr> </table>");
return new com.google.gwt.safehtml.shared.OnlyToBeUsedInGeneratedCodeStringBlessedAsSafeHtml(sb.toString());
}

public com.google.gwt.safehtml.shared.SafeHtml html3(java.lang.String arg0,java.lang.String arg1,java.lang.String arg2) {
StringBuilder sb = new java.lang.StringBuilder();
sb.append("<span id='");
sb.append(com.google.gwt.safehtml.shared.SafeHtmlUtils.htmlEscape(arg0));
sb.append("'></span> <span id='");
sb.append(com.google.gwt.safehtml.shared.SafeHtmlUtils.htmlEscape(arg1));
sb.append("'></span> <span id='");
sb.append(com.google.gwt.safehtml.shared.SafeHtmlUtils.htmlEscape(arg2));
sb.append("'></span>");
return new com.google.gwt.safehtml.shared.OnlyToBeUsedInGeneratedCodeStringBlessedAsSafeHtml(sb.toString());
}
}

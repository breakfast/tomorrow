package com.breakfast.tomorrow.web.client;

public class AlunoView_nome_Context extends com.google.gwt.editor.client.impl.AbstractEditorContext<java.lang.String> {
  private final com.breakfast.tomorrow.web.client.vo.AlunoVO parent;
  public AlunoView_nome_Context(com.breakfast.tomorrow.web.client.vo.AlunoVO parent, com.google.gwt.editor.client.Editor<java.lang.String> editor, String path) {
    super(editor,path);
    this.parent = parent;
  }
  @Override public boolean canSetInModel() {
    return parent != null && true && true;
  }
  @Override public java.lang.String checkAssignment(Object value) {
    return (java.lang.String) value;
  }
  @Override public Class getEditedType() { return java.lang.String.class; }
  @Override public java.lang.String getFromModel() {
    return (parent != null && true) ? parent.getNome() : null;
  }
  @Override public void setInModel(java.lang.String data) {
    parent.setNome(data);
  }
}

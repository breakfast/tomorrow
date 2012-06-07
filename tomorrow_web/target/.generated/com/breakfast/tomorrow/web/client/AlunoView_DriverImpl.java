package com.breakfast.tomorrow.web.client;

public class AlunoView_DriverImpl extends com.google.gwt.editor.client.impl.AbstractSimpleBeanEditorDriver<com.breakfast.tomorrow.web.client.vo.AlunoVO, com.breakfast.tomorrow.web.client.AlunoView> implements com.breakfast.tomorrow.web.client.AlunoView.Driver {
  @Override public void accept(com.google.gwt.editor.client.EditorVisitor visitor) {
    com.google.gwt.editor.client.impl.RootEditorContext ctx = new com.google.gwt.editor.client.impl.RootEditorContext(getDelegate(), com.breakfast.tomorrow.web.client.vo.AlunoVO.class, getObject());
    ctx.traverse(visitor, getDelegate());
  }
  @Override protected com.google.gwt.editor.client.impl.SimpleBeanEditorDelegate createDelegate() {
    return new com.breakfast.tomorrow.web.client.AlunoView_SimpleBeanEditorDelegate();
  }
}

package com.breakfast.tomorrow.web.client;

public class AlunoView_SimpleBeanEditorDelegate extends com.google.gwt.editor.client.impl.SimpleBeanEditorDelegate {
  private com.breakfast.tomorrow.web.client.AlunoView editor;
  @Override protected com.breakfast.tomorrow.web.client.AlunoView getEditor() {return editor;}
  protected void setEditor(com.google.gwt.editor.client.Editor editor) {this.editor=(com.breakfast.tomorrow.web.client.AlunoView)editor;}
  private com.breakfast.tomorrow.web.client.vo.AlunoVO object;
  @Override public com.breakfast.tomorrow.web.client.vo.AlunoVO getObject() {return object;}
  @Override protected void setObject(Object object) {this.object=(com.breakfast.tomorrow.web.client.vo.AlunoVO)object;}
  com.google.gwt.editor.client.impl.SimpleBeanEditorDelegate idPessoaDelegate;
  com.google.gwt.editor.client.impl.SimpleBeanEditorDelegate nomeDelegate;
  com.google.gwt.editor.client.impl.SimpleBeanEditorDelegate enderecoDelegate;
  com.google.gwt.editor.client.impl.SimpleBeanEditorDelegate telefoneDelegate;
  com.google.gwt.editor.client.impl.SimpleBeanEditorDelegate numeroEnderecoDelegate;
  com.google.gwt.editor.client.impl.SimpleBeanEditorDelegate complementoDelegate;
  com.google.gwt.editor.client.impl.SimpleBeanEditorDelegate distritoDelegate;
  com.google.gwt.editor.client.impl.SimpleBeanEditorDelegate cidadeDelegate;
  com.google.gwt.editor.client.impl.SimpleBeanEditorDelegate codigoPostalDelegate;
  @Override protected void initializeSubDelegates() {
    if (editor.id.asEditor() != null) {
      idPessoaDelegate = new com.google.gwt.editor.ui.client.adapters.ValueBoxEditor_java_lang_String_SimpleBeanEditorDelegate();
      addSubDelegate(idPessoaDelegate, appendPath("idPessoa"), editor.id.asEditor());
    }
    if (editor.nome.asEditor() != null) {
      nomeDelegate = new com.google.gwt.editor.ui.client.adapters.ValueBoxEditor_java_lang_String_SimpleBeanEditorDelegate();
      addSubDelegate(nomeDelegate, appendPath("nome"), editor.nome.asEditor());
    }
    if (editor.endereco.asEditor() != null) {
      enderecoDelegate = new com.google.gwt.editor.ui.client.adapters.ValueBoxEditor_java_lang_String_SimpleBeanEditorDelegate();
      addSubDelegate(enderecoDelegate, appendPath("endereco"), editor.endereco.asEditor());
    }
    if (editor.telefone.asEditor() != null) {
      telefoneDelegate = new com.google.gwt.editor.ui.client.adapters.ValueBoxEditor_java_lang_String_SimpleBeanEditorDelegate();
      addSubDelegate(telefoneDelegate, appendPath("telefone"), editor.telefone.asEditor());
    }
    if (editor.numero.asEditor() != null) {
      numeroEnderecoDelegate = new com.google.gwt.editor.ui.client.adapters.ValueBoxEditor_java_lang_String_SimpleBeanEditorDelegate();
      addSubDelegate(numeroEnderecoDelegate, appendPath("numeroEndereco"), editor.numero.asEditor());
    }
    if (editor.complemento.asEditor() != null) {
      complementoDelegate = new com.google.gwt.editor.ui.client.adapters.ValueBoxEditor_java_lang_String_SimpleBeanEditorDelegate();
      addSubDelegate(complementoDelegate, appendPath("complemento"), editor.complemento.asEditor());
    }
    if (editor.bairro.asEditor() != null) {
      distritoDelegate = new com.google.gwt.editor.ui.client.adapters.ValueBoxEditor_java_lang_String_SimpleBeanEditorDelegate();
      addSubDelegate(distritoDelegate, appendPath("distrito"), editor.bairro.asEditor());
    }
    if (editor.cidade.asEditor() != null) {
      cidadeDelegate = new com.google.gwt.editor.ui.client.adapters.ValueBoxEditor_java_lang_String_SimpleBeanEditorDelegate();
      addSubDelegate(cidadeDelegate, appendPath("cidade"), editor.cidade.asEditor());
    }
    if (editor.cep.asEditor() != null) {
      codigoPostalDelegate = new com.google.gwt.editor.ui.client.adapters.ValueBoxEditor_java_lang_String_SimpleBeanEditorDelegate();
      addSubDelegate(codigoPostalDelegate, appendPath("codigoPostal"), editor.cep.asEditor());
    }
  }
  @Override public void accept(com.google.gwt.editor.client.EditorVisitor visitor) {
    if (idPessoaDelegate != null) 
    {
      com.breakfast.tomorrow.web.client.AlunoView_idPessoa_Context ctx = new com.breakfast.tomorrow.web.client.AlunoView_idPessoa_Context(getObject(), editor.id.asEditor(), appendPath("idPessoa"));
      ctx.setEditorDelegate(idPessoaDelegate);
      ctx.traverse(visitor, idPessoaDelegate);
    }
    if (nomeDelegate != null) 
    {
      com.breakfast.tomorrow.web.client.AlunoView_nome_Context ctx = new com.breakfast.tomorrow.web.client.AlunoView_nome_Context(getObject(), editor.nome.asEditor(), appendPath("nome"));
      ctx.setEditorDelegate(nomeDelegate);
      ctx.traverse(visitor, nomeDelegate);
    }
    if (enderecoDelegate != null) 
    {
      com.breakfast.tomorrow.web.client.AlunoView_endereco_Context ctx = new com.breakfast.tomorrow.web.client.AlunoView_endereco_Context(getObject(), editor.endereco.asEditor(), appendPath("endereco"));
      ctx.setEditorDelegate(enderecoDelegate);
      ctx.traverse(visitor, enderecoDelegate);
    }
    if (telefoneDelegate != null) 
    {
      com.breakfast.tomorrow.web.client.AlunoView_telefone_Context ctx = new com.breakfast.tomorrow.web.client.AlunoView_telefone_Context(getObject(), editor.telefone.asEditor(), appendPath("telefone"));
      ctx.setEditorDelegate(telefoneDelegate);
      ctx.traverse(visitor, telefoneDelegate);
    }
    if (numeroEnderecoDelegate != null) 
    {
      com.breakfast.tomorrow.web.client.AlunoView_numeroEndereco_Context ctx = new com.breakfast.tomorrow.web.client.AlunoView_numeroEndereco_Context(getObject(), editor.numero.asEditor(), appendPath("numeroEndereco"));
      ctx.setEditorDelegate(numeroEnderecoDelegate);
      ctx.traverse(visitor, numeroEnderecoDelegate);
    }
    if (complementoDelegate != null) 
    {
      com.breakfast.tomorrow.web.client.AlunoView_complemento_Context ctx = new com.breakfast.tomorrow.web.client.AlunoView_complemento_Context(getObject(), editor.complemento.asEditor(), appendPath("complemento"));
      ctx.setEditorDelegate(complementoDelegate);
      ctx.traverse(visitor, complementoDelegate);
    }
    if (distritoDelegate != null) 
    {
      com.breakfast.tomorrow.web.client.AlunoView_distrito_Context ctx = new com.breakfast.tomorrow.web.client.AlunoView_distrito_Context(getObject(), editor.bairro.asEditor(), appendPath("distrito"));
      ctx.setEditorDelegate(distritoDelegate);
      ctx.traverse(visitor, distritoDelegate);
    }
    if (cidadeDelegate != null) 
    {
      com.breakfast.tomorrow.web.client.AlunoView_cidade_Context ctx = new com.breakfast.tomorrow.web.client.AlunoView_cidade_Context(getObject(), editor.cidade.asEditor(), appendPath("cidade"));
      ctx.setEditorDelegate(cidadeDelegate);
      ctx.traverse(visitor, cidadeDelegate);
    }
    if (codigoPostalDelegate != null) 
    {
      com.breakfast.tomorrow.web.client.AlunoView_codigoPostal_Context ctx = new com.breakfast.tomorrow.web.client.AlunoView_codigoPostal_Context(getObject(), editor.cep.asEditor(), appendPath("codigoPostal"));
      ctx.setEditorDelegate(codigoPostalDelegate);
      ctx.traverse(visitor, codigoPostalDelegate);
    }
  }
}

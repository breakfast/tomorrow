package com.breakfast.tomorrow.web.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiBinderUtil;
import com.google.gwt.user.client.ui.Widget;

public class AlunoView_AlunoViewUiBinderImpl implements UiBinder<com.google.gwt.user.client.ui.Widget, com.breakfast.tomorrow.web.client.AlunoView>, com.breakfast.tomorrow.web.client.AlunoView.AlunoViewUiBinder {

  interface Template extends SafeHtmlTemplates {
    @Template("<table style='width:100%'> <tr> <td align='left'> <span id='{0}'></span> </td> </tr> </table>")
    SafeHtml html1(String arg0);
     
    @Template("Lista Alunos")
    SafeHtml html2();
     
    @Template("<span id='{0}'></span> <span id='{1}'></span> <span id='{2}'></span>")
    SafeHtml html3(String arg0, String arg1, String arg2);
     
  }

  Template template = GWT.create(Template.class);

  public com.google.gwt.user.client.ui.Widget createAndBindUi(final com.breakfast.tomorrow.web.client.AlunoView owner) {

    com.breakfast.tomorrow.web.client.AlunoView_AlunoViewUiBinderImpl_GenBundle clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay = (com.breakfast.tomorrow.web.client.AlunoView_AlunoViewUiBinderImpl_GenBundle) GWT.create(com.breakfast.tomorrow.web.client.AlunoView_AlunoViewUiBinderImpl_GenBundle.class);
    com.breakfast.tomorrow.web.client.ApplicationConstants cc = (com.breakfast.tomorrow.web.client.ApplicationConstants) GWT.create(com.breakfast.tomorrow.web.client.ApplicationConstants.class);
    com.breakfast.tomorrow.web.client.AlunoView_AlunoViewUiBinderImpl_GenCss_style style = clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay.style();
    java.lang.String domId0 = com.google.gwt.dom.client.Document.get().createUniqueId();
    com.google.gwt.user.client.ui.Label f_Label2 = (com.google.gwt.user.client.ui.Label) GWT.create(com.google.gwt.user.client.ui.Label.class);
    java.lang.String domId1 = com.google.gwt.dom.client.Document.get().createUniqueId();
    com.google.gwt.user.client.ui.Button btnNovo = (com.google.gwt.user.client.ui.Button) GWT.create(com.google.gwt.user.client.ui.Button.class);
    com.google.gwt.user.client.ui.Button btnSalvar = (com.google.gwt.user.client.ui.Button) GWT.create(com.google.gwt.user.client.ui.Button.class);
    com.google.gwt.user.client.ui.Button btnCancelar = (com.google.gwt.user.client.ui.Button) GWT.create(com.google.gwt.user.client.ui.Button.class);
    com.google.gwt.user.client.ui.Button btnExcluir = (com.google.gwt.user.client.ui.Button) GWT.create(com.google.gwt.user.client.ui.Button.class);
    com.google.gwt.user.client.ui.HorizontalPanel f_HorizontalPanel3 = (com.google.gwt.user.client.ui.HorizontalPanel) GWT.create(com.google.gwt.user.client.ui.HorizontalPanel.class);
    java.lang.String domId2 = com.google.gwt.dom.client.Document.get().createUniqueId();
    com.google.gwt.user.client.ui.Label f_Label6 = (com.google.gwt.user.client.ui.Label) GWT.create(com.google.gwt.user.client.ui.Label.class);
    com.google.gwt.user.client.ui.TextBox id = (com.google.gwt.user.client.ui.TextBox) GWT.create(com.google.gwt.user.client.ui.TextBox.class);
    com.google.gwt.user.client.ui.Label f_Label7 = (com.google.gwt.user.client.ui.Label) GWT.create(com.google.gwt.user.client.ui.Label.class);
    com.google.gwt.user.client.ui.TextBox nome = (com.google.gwt.user.client.ui.TextBox) GWT.create(com.google.gwt.user.client.ui.TextBox.class);
    com.google.gwt.user.client.ui.Label f_Label8 = (com.google.gwt.user.client.ui.Label) GWT.create(com.google.gwt.user.client.ui.Label.class);
    com.google.gwt.user.client.ui.TextBox telefone = (com.google.gwt.user.client.ui.TextBox) GWT.create(com.google.gwt.user.client.ui.TextBox.class);
    com.google.gwt.user.client.ui.Label f_Label9 = (com.google.gwt.user.client.ui.Label) GWT.create(com.google.gwt.user.client.ui.Label.class);
    com.google.gwt.user.client.ui.TextBox endereco = (com.google.gwt.user.client.ui.TextBox) GWT.create(com.google.gwt.user.client.ui.TextBox.class);
    com.google.gwt.user.client.ui.Label f_Label10 = (com.google.gwt.user.client.ui.Label) GWT.create(com.google.gwt.user.client.ui.Label.class);
    com.google.gwt.user.client.ui.TextBox numero = (com.google.gwt.user.client.ui.TextBox) GWT.create(com.google.gwt.user.client.ui.TextBox.class);
    com.google.gwt.user.client.ui.Label f_Label11 = (com.google.gwt.user.client.ui.Label) GWT.create(com.google.gwt.user.client.ui.Label.class);
    com.google.gwt.user.client.ui.TextBox complemento = (com.google.gwt.user.client.ui.TextBox) GWT.create(com.google.gwt.user.client.ui.TextBox.class);
    com.google.gwt.user.client.ui.Label f_Label12 = (com.google.gwt.user.client.ui.Label) GWT.create(com.google.gwt.user.client.ui.Label.class);
    com.google.gwt.user.client.ui.TextBox bairro = (com.google.gwt.user.client.ui.TextBox) GWT.create(com.google.gwt.user.client.ui.TextBox.class);
    com.google.gwt.user.client.ui.Label f_Label13 = (com.google.gwt.user.client.ui.Label) GWT.create(com.google.gwt.user.client.ui.Label.class);
    com.google.gwt.user.client.ui.TextBox cidade = (com.google.gwt.user.client.ui.TextBox) GWT.create(com.google.gwt.user.client.ui.TextBox.class);
    com.google.gwt.user.client.ui.Label f_Label14 = (com.google.gwt.user.client.ui.Label) GWT.create(com.google.gwt.user.client.ui.Label.class);
    com.google.gwt.user.client.ui.TextBox cep = (com.google.gwt.user.client.ui.TextBox) GWT.create(com.google.gwt.user.client.ui.TextBox.class);
    com.google.gwt.user.client.ui.VerticalPanel f_VerticalPanel5 = (com.google.gwt.user.client.ui.VerticalPanel) GWT.create(com.google.gwt.user.client.ui.VerticalPanel.class);
    com.google.gwt.user.client.ui.VerticalPanel f_VerticalPanel4 = (com.google.gwt.user.client.ui.VerticalPanel) GWT.create(com.google.gwt.user.client.ui.VerticalPanel.class);
    com.google.gwt.user.client.ui.Label f_Label15 = (com.google.gwt.user.client.ui.Label) GWT.create(com.google.gwt.user.client.ui.Label.class);
    com.google.gwt.user.cellview.client.DataGrid dataGrid = owner.dataGrid;
    java.lang.String domId3 = com.google.gwt.dom.client.Document.get().createUniqueId();
    com.google.gwt.user.cellview.client.SimplePager pager = owner.pager;
    com.google.gwt.user.client.ui.HTMLPanel f_HTMLPanel17 = new com.google.gwt.user.client.ui.HTMLPanel(template.html1(domId3).asString());
    com.google.gwt.user.client.ui.DockLayoutPanel f_DockLayoutPanel16 = new com.google.gwt.user.client.ui.DockLayoutPanel(com.google.gwt.dom.client.Style.Unit.EM);
    com.google.gwt.user.client.ui.TabLayoutPanel tab = new com.google.gwt.user.client.ui.TabLayoutPanel(30, com.google.gwt.dom.client.Style.Unit.PX);
    com.google.gwt.user.client.ui.HTMLPanel f_HTMLPanel1 = new com.google.gwt.user.client.ui.HTMLPanel(template.html3(domId0, domId1, domId2).asString());

    f_Label2.setText("Aluno");
    btnNovo.setText("" + cc.novo() + "");
    f_HorizontalPanel3.add(btnNovo);
    btnSalvar.setText("" + cc.salvar() + "");
    f_HorizontalPanel3.add(btnSalvar);
    btnCancelar.setText("" + cc.cancelar() + "");
    f_HorizontalPanel3.add(btnCancelar);
    btnExcluir.setText("" + cc.excluir() + "");
    f_HorizontalPanel3.add(btnExcluir);
    f_Label6.setText("" + cc.id() + "");
    f_VerticalPanel5.add(f_Label6);
    id.setWidth("234px");
    f_VerticalPanel5.add(id);
    f_Label7.setText("" + cc.nome() + "");
    f_VerticalPanel5.add(f_Label7);
    nome.setWidth("234px");
    f_VerticalPanel5.add(nome);
    f_Label8.setText("" + cc.telefone() + "");
    f_VerticalPanel5.add(f_Label8);
    telefone.setWidth("137px");
    f_VerticalPanel5.add(telefone);
    f_Label9.setText("Endereço");
    f_Label9.setTitle("Endereço");
    f_VerticalPanel5.add(f_Label9);
    endereco.setWidth("441px");
    f_VerticalPanel5.add(endereco);
    f_Label10.setText("Numero");
    f_Label10.setTitle("Numero");
    f_VerticalPanel5.add(f_Label10);
    numero.setWidth("54px");
    f_VerticalPanel5.add(numero);
    f_Label11.setText("Complemento");
    f_Label11.setTitle("Complemento");
    f_VerticalPanel5.add(f_Label11);
    complemento.setWidth("154px");
    f_VerticalPanel5.add(complemento);
    f_Label12.setText("Bairro");
    f_Label12.setTitle("Bairro");
    f_VerticalPanel5.add(f_Label12);
    bairro.setWidth("381px");
    f_VerticalPanel5.add(bairro);
    f_Label13.setText("Cidade");
    f_Label13.setTitle("Cidade");
    f_VerticalPanel5.add(f_Label13);
    cidade.setWidth("227px");
    f_VerticalPanel5.add(cidade);
    f_Label14.setText("CEP");
    f_Label14.setTitle("CEP");
    f_VerticalPanel5.add(f_Label14);
    cep.setWidth("115px");
    f_VerticalPanel5.add(cep);
    f_VerticalPanel4.add(f_VerticalPanel5);
    f_Label15.setText("Cadastro");
    tab.add(f_VerticalPanel4, f_Label15);
    f_DockLayoutPanel16.addSouth(f_HTMLPanel17, 3);
    f_DockLayoutPanel16.add(dataGrid);
    tab.add(f_DockLayoutPanel16, template.html2().asString(), true);
    tab.setHeight("600px");
    f_HTMLPanel1.setStyleName("" + style.content() + "");

    UiBinderUtil.TempAttachment attachRecord0 = UiBinderUtil.attachToDom(f_HTMLPanel1.getElement());
    com.google.gwt.user.client.Element domId0Element = com.google.gwt.dom.client.Document.get().getElementById(domId0).cast();
    com.google.gwt.user.client.Element domId1Element = com.google.gwt.dom.client.Document.get().getElementById(domId1).cast();
    UiBinderUtil.TempAttachment attachRecord1 = UiBinderUtil.attachToDom(f_HTMLPanel17.getElement());
    com.google.gwt.user.client.Element domId3Element = com.google.gwt.dom.client.Document.get().getElementById(domId3).cast();
    attachRecord1.detach();
    f_HTMLPanel17.addAndReplaceElement(pager, domId3Element);
    com.google.gwt.user.client.Element domId2Element = com.google.gwt.dom.client.Document.get().getElementById(domId2).cast();
    attachRecord0.detach();
    f_HTMLPanel1.addAndReplaceElement(f_Label2, domId0Element);
    f_HTMLPanel1.addAndReplaceElement(f_HorizontalPanel3, domId1Element);
    f_HTMLPanel1.addAndReplaceElement(tab, domId2Element);


    final com.google.gwt.event.dom.client.ClickHandler handlerMethodWithNameVeryUnlikelyToCollideWithUserFieldNames1 = new com.google.gwt.event.dom.client.ClickHandler() {
      public void onClick(com.google.gwt.event.dom.client.ClickEvent event) {
        owner.btnNovoOnClick(event);
      }
    };
    btnNovo.addClickHandler(handlerMethodWithNameVeryUnlikelyToCollideWithUserFieldNames1);

    final com.google.gwt.event.dom.client.ClickHandler handlerMethodWithNameVeryUnlikelyToCollideWithUserFieldNames2 = new com.google.gwt.event.dom.client.ClickHandler() {
      public void onClick(com.google.gwt.event.dom.client.ClickEvent event) {
        owner.btnSalvarOnClick(event);
      }
    };
    btnSalvar.addClickHandler(handlerMethodWithNameVeryUnlikelyToCollideWithUserFieldNames2);

    final com.google.gwt.event.dom.client.ClickHandler handlerMethodWithNameVeryUnlikelyToCollideWithUserFieldNames3 = new com.google.gwt.event.dom.client.ClickHandler() {
      public void onClick(com.google.gwt.event.dom.client.ClickEvent event) {
        owner.btnCancelarOnClick(event);
      }
    };
    btnCancelar.addClickHandler(handlerMethodWithNameVeryUnlikelyToCollideWithUserFieldNames3);

    final com.google.gwt.event.logical.shared.SelectionHandler<java.lang.Integer> handlerMethodWithNameVeryUnlikelyToCollideWithUserFieldNames4 = new com.google.gwt.event.logical.shared.SelectionHandler<java.lang.Integer>() {
      public void onSelection(com.google.gwt.event.logical.shared.SelectionEvent<java.lang.Integer> event) {
        owner.list(event);
      }
    };
    tab.addSelectionHandler(handlerMethodWithNameVeryUnlikelyToCollideWithUserFieldNames4);

    final com.google.gwt.event.dom.client.ClickHandler handlerMethodWithNameVeryUnlikelyToCollideWithUserFieldNames5 = new com.google.gwt.event.dom.client.ClickHandler() {
      public void onClick(com.google.gwt.event.dom.client.ClickEvent event) {
        owner.btnExcluirOnClick(event);
      }
    };
    btnExcluir.addClickHandler(handlerMethodWithNameVeryUnlikelyToCollideWithUserFieldNames5);

    owner.bairro = bairro;
    owner.btnCancelar = btnCancelar;
    owner.btnExcluir = btnExcluir;
    owner.btnNovo = btnNovo;
    owner.btnSalvar = btnSalvar;
    owner.cep = cep;
    owner.cidade = cidade;
    owner.complemento = complemento;
    owner.endereco = endereco;
    owner.id = id;
    owner.nome = nome;
    owner.numero = numero;
    owner.tab = tab;
    owner.telefone = telefone;
    clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay.style().ensureInjected();

    return f_HTMLPanel1;
  }
}

package com.breakfast.tomorrow.web.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiBinderUtil;
import com.google.gwt.user.client.ui.Widget;

public class ApplicationMenu_ApplicationMenuUiBinderImpl implements UiBinder<com.google.gwt.user.client.ui.Widget, com.breakfast.tomorrow.web.client.ApplicationMenu>, com.breakfast.tomorrow.web.client.ApplicationMenu.ApplicationMenuUiBinder {

  interface Template extends SafeHtmlTemplates {
    @Template("<span id='{0}'></span>")
    SafeHtml html1(String arg0);
     
  }

  Template template = GWT.create(Template.class);

  public com.google.gwt.user.client.ui.Widget createAndBindUi(final com.breakfast.tomorrow.web.client.ApplicationMenu owner) {

    com.breakfast.tomorrow.web.client.ApplicationMenu_ApplicationMenuUiBinderImpl_GenBundle clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay = (com.breakfast.tomorrow.web.client.ApplicationMenu_ApplicationMenuUiBinderImpl_GenBundle) GWT.create(com.breakfast.tomorrow.web.client.ApplicationMenu_ApplicationMenuUiBinderImpl_GenBundle.class);
    com.breakfast.tomorrow.web.client.ApplicationConstants cc = (com.breakfast.tomorrow.web.client.ApplicationConstants) GWT.create(com.breakfast.tomorrow.web.client.ApplicationConstants.class);
    com.breakfast.tomorrow.web.client.ApplicationMenu_ApplicationMenuUiBinderImpl_GenCss_style style = clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay.style();
    java.lang.String domId0 = com.google.gwt.dom.client.Document.get().createUniqueId();
    com.google.gwt.user.client.ui.Hyperlink f_Hyperlink2 = (com.google.gwt.user.client.ui.Hyperlink) GWT.create(com.google.gwt.user.client.ui.Hyperlink.class);
    com.google.gwt.user.client.ui.Hyperlink f_Hyperlink3 = (com.google.gwt.user.client.ui.Hyperlink) GWT.create(com.google.gwt.user.client.ui.Hyperlink.class);
    com.google.gwt.user.client.ui.Label f_Label6 = (com.google.gwt.user.client.ui.Label) GWT.create(com.google.gwt.user.client.ui.Label.class);
    com.google.gwt.user.client.ui.Label f_Label7 = (com.google.gwt.user.client.ui.Label) GWT.create(com.google.gwt.user.client.ui.Label.class);
    com.google.gwt.user.client.ui.Label f_Label8 = (com.google.gwt.user.client.ui.Label) GWT.create(com.google.gwt.user.client.ui.Label.class);
    com.google.gwt.user.client.ui.Label f_Label9 = (com.google.gwt.user.client.ui.Label) GWT.create(com.google.gwt.user.client.ui.Label.class);
    com.google.gwt.user.client.ui.VerticalPanel f_VerticalPanel5 = (com.google.gwt.user.client.ui.VerticalPanel) GWT.create(com.google.gwt.user.client.ui.VerticalPanel.class);
    com.google.gwt.user.client.ui.Hyperlink f_Hyperlink10 = (com.google.gwt.user.client.ui.Hyperlink) GWT.create(com.google.gwt.user.client.ui.Hyperlink.class);
    com.google.gwt.user.client.ui.StackLayoutPanel f_StackLayoutPanel4 = new com.google.gwt.user.client.ui.StackLayoutPanel(com.google.gwt.dom.client.Style.Unit.PX);
    com.google.gwt.user.client.ui.VerticalPanel menuPanel = (com.google.gwt.user.client.ui.VerticalPanel) GWT.create(com.google.gwt.user.client.ui.VerticalPanel.class);
    com.google.gwt.user.client.ui.HTMLPanel f_HTMLPanel1 = new com.google.gwt.user.client.ui.HTMLPanel(template.html1(domId0).asString());

    f_Hyperlink2.setStylePrimaryName("" + style.itemMenuCss() + "");
    f_Hyperlink2.setTargetHistoryToken("matricular");
    f_Hyperlink2.setHTML("" + cc.cadastrarCurso() + "");
    menuPanel.add(f_Hyperlink2);
    f_Hyperlink3.setStylePrimaryName("" + style.itemMenuCss() + "");
    f_Hyperlink3.setTargetHistoryToken("outro");
    f_Hyperlink3.setHTML("" + cc.matricularAluno() + "");
    menuPanel.add(f_Hyperlink3);
    f_Label6.setText("" + cc.aluno() + "");
    f_VerticalPanel5.add(f_Label6);
    f_Label7.setText("" + cc.professor() + "");
    f_VerticalPanel5.add(f_Label7);
    f_Label8.setText("" + cc.turma() + "");
    f_VerticalPanel5.add(f_Label8);
    f_Label9.setText("" + cc.curso() + "");
    f_VerticalPanel5.add(f_Label9);
    f_Hyperlink10.setStylePrimaryName("" + style.itemMenuCss() + "");
    f_Hyperlink10.setHTML("" + cc.consultar() + "");
    f_StackLayoutPanel4.add(f_VerticalPanel5, f_Hyperlink10, 30);
    f_StackLayoutPanel4.setStylePrimaryName("" + style.stackGroupMenuCss() + "");
    f_StackLayoutPanel4.setHeight("400px");
    f_StackLayoutPanel4.setWidth("250px");
    menuPanel.add(f_StackLayoutPanel4);
    f_HTMLPanel1.setStylePrimaryName("" + style.applicationMenuCss() + "");

    UiBinderUtil.TempAttachment attachRecord0 = UiBinderUtil.attachToDom(f_HTMLPanel1.getElement());
    com.google.gwt.user.client.Element domId0Element = com.google.gwt.dom.client.Document.get().getElementById(domId0).cast();
    attachRecord0.detach();
    f_HTMLPanel1.addAndReplaceElement(menuPanel, domId0Element);


    owner.menuPanel = menuPanel;
    clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay.style().ensureInjected();

    return f_HTMLPanel1;
  }
}

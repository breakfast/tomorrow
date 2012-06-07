package com.breakfast.tomorrow.web.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiBinderUtil;
import com.google.gwt.user.client.ui.Widget;

public class ApplicationHeader_ApplicationHeaderUiBinderImpl implements UiBinder<com.google.gwt.user.client.ui.Widget, com.breakfast.tomorrow.web.client.ApplicationHeader>, com.breakfast.tomorrow.web.client.ApplicationHeader.ApplicationHeaderUiBinder {

  interface Template extends SafeHtmlTemplates {
    @Template("<span id='{0}'></span>")
    SafeHtml html1(String arg0);
     
  }

  Template template = GWT.create(Template.class);

  public com.google.gwt.user.client.ui.Widget createAndBindUi(final com.breakfast.tomorrow.web.client.ApplicationHeader owner) {

    com.breakfast.tomorrow.web.client.ApplicationHeader_ApplicationHeaderUiBinderImpl_GenBundle clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay = (com.breakfast.tomorrow.web.client.ApplicationHeader_ApplicationHeaderUiBinderImpl_GenBundle) GWT.create(com.breakfast.tomorrow.web.client.ApplicationHeader_ApplicationHeaderUiBinderImpl_GenBundle.class);
    com.breakfast.tomorrow.web.client.ApplicationConstants cc = (com.breakfast.tomorrow.web.client.ApplicationConstants) GWT.create(com.breakfast.tomorrow.web.client.ApplicationConstants.class);
    com.breakfast.tomorrow.web.client.ApplicationHeader_ApplicationHeaderUiBinderImpl_GenCss_style style = clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay.style();
    java.lang.String domId0 = com.google.gwt.dom.client.Document.get().createUniqueId();
    com.google.gwt.user.client.ui.Button f_Button3 = (com.google.gwt.user.client.ui.Button) GWT.create(com.google.gwt.user.client.ui.Button.class);
    com.google.gwt.user.client.ui.Button f_Button4 = (com.google.gwt.user.client.ui.Button) GWT.create(com.google.gwt.user.client.ui.Button.class);
    com.google.gwt.user.client.ui.DockLayoutPanel f_DockLayoutPanel2 = new com.google.gwt.user.client.ui.DockLayoutPanel(com.google.gwt.dom.client.Style.Unit.PX);
    com.google.gwt.user.client.ui.HTMLPanel f_HTMLPanel1 = new com.google.gwt.user.client.ui.HTMLPanel(template.html1(domId0).asString());

    f_Button3.setText("Button");
    f_DockLayoutPanel2.addWest(f_Button3, 250);
    f_Button4.setText("Button");
    f_DockLayoutPanel2.addEast(f_Button4, 250);
    f_HTMLPanel1.setStyleName("" + style.headerApplicationCss() + "");

    UiBinderUtil.TempAttachment attachRecord0 = UiBinderUtil.attachToDom(f_HTMLPanel1.getElement());
    com.google.gwt.user.client.Element domId0Element = com.google.gwt.dom.client.Document.get().getElementById(domId0).cast();
    attachRecord0.detach();
    f_HTMLPanel1.addAndReplaceElement(f_DockLayoutPanel2, domId0Element);


    clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay.style().ensureInjected();

    return f_HTMLPanel1;
  }
}

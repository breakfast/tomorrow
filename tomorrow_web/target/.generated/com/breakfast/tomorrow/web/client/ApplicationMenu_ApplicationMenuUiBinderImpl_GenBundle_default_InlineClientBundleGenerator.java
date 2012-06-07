package com.breakfast.tomorrow.web.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ResourcePrototype;

public class ApplicationMenu_ApplicationMenuUiBinderImpl_GenBundle_default_InlineClientBundleGenerator implements com.breakfast.tomorrow.web.client.ApplicationMenu_ApplicationMenuUiBinderImpl_GenBundle {
  private static ApplicationMenu_ApplicationMenuUiBinderImpl_GenBundle_default_InlineClientBundleGenerator _instance0 = new ApplicationMenu_ApplicationMenuUiBinderImpl_GenBundle_default_InlineClientBundleGenerator();
  private void styleInitializer() {
    style = new com.breakfast.tomorrow.web.client.ApplicationMenu_ApplicationMenuUiBinderImpl_GenCss_style() {
      private boolean injected;
      public boolean ensureInjected() {
        if (!injected) {
          injected = true;
          com.google.gwt.dom.client.StyleInjector.inject(getText());
          return true;
        }
        return false;
      }
      public String getName() {
        return "style";
      }
      public String getText() {
        return com.google.gwt.i18n.client.LocaleInfo.getCurrentLocale().isRTL() ? ((".GMOKSFJDFI{width:" + ("250px")  + ";height:" + ("400px")  + ";padding-right:" + ("10px")  + ";padding-top:" + ("10px")  + ";}.GMOKSFJDHI{width:" + ("250px")  + ";height:" + ("400px")  + ";}.GMOKSFJDGI{background:" + ("#404040")  + ";width:" + ("100%")  + ";cursor:" + ("pointer")  + ";height:" + ("30px")  + ";}.GMOKSFJDGI a{color:") + (("white")  + ";}.GMOKSFJDGI:HOVER{background:" + ("#101010")  + ";}")) : ((".GMOKSFJDFI{width:" + ("250px")  + ";height:" + ("400px")  + ";padding-left:" + ("10px")  + ";padding-top:" + ("10px")  + ";}.GMOKSFJDHI{width:" + ("250px")  + ";height:" + ("400px")  + ";}.GMOKSFJDGI{background:" + ("#404040")  + ";width:" + ("100%")  + ";cursor:" + ("pointer")  + ";height:" + ("30px")  + ";}.GMOKSFJDGI a{color:") + (("white")  + ";}.GMOKSFJDGI:HOVER{background:" + ("#101010")  + ";}"));
      }
      public java.lang.String applicationMenuCss(){
        return "GMOKSFJDFI";
      }
      public java.lang.String itemMenuCss(){
        return "GMOKSFJDGI";
      }
      public java.lang.String stackGroupMenuCss(){
        return "GMOKSFJDHI";
      }
    }
    ;
  }
  private static class styleInitializer {
    static {
      _instance0.styleInitializer();
    }
    static com.breakfast.tomorrow.web.client.ApplicationMenu_ApplicationMenuUiBinderImpl_GenCss_style get() {
      return style;
    }
  }
  public com.breakfast.tomorrow.web.client.ApplicationMenu_ApplicationMenuUiBinderImpl_GenCss_style style() {
    return styleInitializer.get();
  }
  private static java.util.HashMap<java.lang.String, com.google.gwt.resources.client.ResourcePrototype> resourceMap;
  private static com.breakfast.tomorrow.web.client.ApplicationMenu_ApplicationMenuUiBinderImpl_GenCss_style style;
  
  public ResourcePrototype[] getResources() {
    return new ResourcePrototype[] {
      style(), 
    };
  }
  public ResourcePrototype getResource(String name) {
    if (GWT.isScript()) {
      return getResourceNative(name);
    } else {
      if (resourceMap == null) {
        resourceMap = new java.util.HashMap<java.lang.String, com.google.gwt.resources.client.ResourcePrototype>();
        resourceMap.put("style", style());
      }
      return resourceMap.get(name);
    }
  }
  private native ResourcePrototype getResourceNative(String name) /*-{
    switch (name) {
      case 'style': return this.@com.breakfast.tomorrow.web.client.ApplicationMenu_ApplicationMenuUiBinderImpl_GenBundle::style()();
    }
    return null;
  }-*/;
}

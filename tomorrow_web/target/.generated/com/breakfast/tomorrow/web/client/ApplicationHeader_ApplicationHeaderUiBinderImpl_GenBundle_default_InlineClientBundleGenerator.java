package com.breakfast.tomorrow.web.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ResourcePrototype;

public class ApplicationHeader_ApplicationHeaderUiBinderImpl_GenBundle_default_InlineClientBundleGenerator implements com.breakfast.tomorrow.web.client.ApplicationHeader_ApplicationHeaderUiBinderImpl_GenBundle {
  private static ApplicationHeader_ApplicationHeaderUiBinderImpl_GenBundle_default_InlineClientBundleGenerator _instance0 = new ApplicationHeader_ApplicationHeaderUiBinderImpl_GenBundle_default_InlineClientBundleGenerator();
  private void styleInitializer() {
    style = new com.breakfast.tomorrow.web.client.ApplicationHeader_ApplicationHeaderUiBinderImpl_GenCss_style() {
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
        return (".GMOKSFJDA{width:" + ("100%")  + ";height:" + ("70px")  + ";background:" + ("rgba(241,241,241,0.8)")  + ";border-bottom:" + ("1px"+ " " +"solid"+ " " +"#e5e9ec")  + ";border-top:" + ("6px"+ " " +"solid"+ " " +"#3583f2")  + ";}");
      }
      public java.lang.String headerApplicationCss(){
        return "GMOKSFJDA";
      }
    }
    ;
  }
  private static class styleInitializer {
    static {
      _instance0.styleInitializer();
    }
    static com.breakfast.tomorrow.web.client.ApplicationHeader_ApplicationHeaderUiBinderImpl_GenCss_style get() {
      return style;
    }
  }
  public com.breakfast.tomorrow.web.client.ApplicationHeader_ApplicationHeaderUiBinderImpl_GenCss_style style() {
    return styleInitializer.get();
  }
  private static java.util.HashMap<java.lang.String, com.google.gwt.resources.client.ResourcePrototype> resourceMap;
  private static com.breakfast.tomorrow.web.client.ApplicationHeader_ApplicationHeaderUiBinderImpl_GenCss_style style;
  
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
      case 'style': return this.@com.breakfast.tomorrow.web.client.ApplicationHeader_ApplicationHeaderUiBinderImpl_GenBundle::style()();
    }
    return null;
  }-*/;
}

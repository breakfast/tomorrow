package com.breakfast.tomorrow.web.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ResourcePrototype;

public class AlunoView_AlunoViewUiBinderImpl_GenBundle_default_InlineClientBundleGenerator implements com.breakfast.tomorrow.web.client.AlunoView_AlunoViewUiBinderImpl_GenBundle {
  private static AlunoView_AlunoViewUiBinderImpl_GenBundle_default_InlineClientBundleGenerator _instance0 = new AlunoView_AlunoViewUiBinderImpl_GenBundle_default_InlineClientBundleGenerator();
  private void styleInitializer() {
    style = new com.breakfast.tomorrow.web.client.AlunoView_AlunoViewUiBinderImpl_GenCss_style() {
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
        return (".GMOKSFJDII input{border:" + ("1px"+ " " +"solid"+ " " +"#d9d9d9")  + ";font-size:" + ("14px")  + ";padding:" + ("3px"+ " " +"3px"+ " " +"3px"+ " " +"3px")  + ";}.GMOKSFJDJI .GMOKSFJDII div{font-weight:" + ("bold")  + ";color:" + ("gray")  + ";}.GMOKSFJDII input:FOCUS{border:" + ("1px"+ " " +"solid"+ " " +"#4d90fe")  + ";outline:" + ("none")  + ";}");
      }
      public java.lang.String content(){
        return "GMOKSFJDII";
      }
      public java.lang.String gwtLabel(){
        return "GMOKSFJDJI";
      }
    }
    ;
  }
  private static class styleInitializer {
    static {
      _instance0.styleInitializer();
    }
    static com.breakfast.tomorrow.web.client.AlunoView_AlunoViewUiBinderImpl_GenCss_style get() {
      return style;
    }
  }
  public com.breakfast.tomorrow.web.client.AlunoView_AlunoViewUiBinderImpl_GenCss_style style() {
    return styleInitializer.get();
  }
  private static java.util.HashMap<java.lang.String, com.google.gwt.resources.client.ResourcePrototype> resourceMap;
  private static com.breakfast.tomorrow.web.client.AlunoView_AlunoViewUiBinderImpl_GenCss_style style;
  
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
      case 'style': return this.@com.breakfast.tomorrow.web.client.AlunoView_AlunoViewUiBinderImpl_GenBundle::style()();
    }
    return null;
  }-*/;
}

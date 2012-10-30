package com.breakfast.tomorrow.web.client;

import java.util.HashMap;
import java.util.Map;

import com.breakfast.gwt.user.client.TextBoxDropPanel;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;



/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class WebApp implements EntryPoint {
  
	Map<String,Widget> mapWidget = new HashMap<String, Widget>();
	
	ApplicationHeader appHeader = new ApplicationHeader();
	ApplicationMenu appMenu = new ApplicationMenu();
	DockLayoutPanel container = new DockLayoutPanel(Unit.PX);
	SimplePanel content = new SimplePanel();
	
	
	public void onModuleLoad() {
		
		container.addNorth(appHeader, 80);
		container.addWest(appMenu,250);
		container.add(new ScrollPanel(content));
		
		
		setupMapWidget();
		addMenuClickHandler();
		
		// Setup a history handler to reselect the associate menu item
	    final ValueChangeHandler<String> historyHandler = new ValueChangeHandler<String>() {
	    	public void onValueChange(ValueChangeEvent<String> event) {
	    		Widget w = mapWidget.get(event.getValue());
	    		if(w!=null){
	    			setContent(w);
	    		}
	    	}
	    };
	    History.addValueChangeHandler(historyHandler);
	    
	    if(History.getToken().length() > 0){
	    	History.fireCurrentHistoryState();
	    }
	    else{
	    	setContent(mapWidget.get("index"));
	    	
	    }
	    
		
		RootLayoutPanel.get().add(container);
	}
	
	private void  addMenuClickHandler(){
		this.appMenu.addClickHandler( new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Object o = event.getSource();
				if(o instanceof Hyperlink){
					Hyperlink h = (Hyperlink) o;
					History.newItem(h.getTargetHistoryToken());
				}
			}
		});
	}
	
	private void setContent(Widget w){
		if(w!=null){
			this.content.setWidget(w);
		}
	}
	
	private void setupMapWidget(){
		this.mapWidget.put("alunos",new AlunoView());
		this.mapWidget.put("outro",new TextBoxDropPanel());
		this.mapWidget.put("index",new HTMLPanel("<h1>Index Page Test<h1>"));
		this.mapWidget.put("cursos",new CursoView());
		this.mapWidget.put("professores",new ProfessorView());
		this.mapWidget.put("turmas",new TurmaView());
	}
	
	
}

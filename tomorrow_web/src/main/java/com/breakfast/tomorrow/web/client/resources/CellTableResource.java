package com.breakfast.tomorrow.web.client.resources;

import com.google.gwt.user.cellview.client.CellTable;

public interface CellTableResource extends CellTable.Resources{
	
	interface TableStyle extends CellTable.Style{}
	
	@Source({CellTable.Style.DEFAULT_CSS,"celltable.css"})
	TableStyle cellTableStyle();
	
	
	
}

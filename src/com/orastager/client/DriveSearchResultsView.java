/*
 * Copyright 2011 Nanda Emani
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.orastager.client;

import java.util.List;

import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;

public class DriveSearchResultsView {
	    CellTableResource resource = GWT.create(CellTableResource.class);
	    SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
	    SimplePager pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
		CellTable<DriveResult> table = new CellTable<DriveResult>(8, resource);
	    ListDataProvider<DriveResult> dataProvider = new ListDataProvider<DriveResult>();

	    List<DriveResult> list = dataProvider.getList();
	    VerticalPanel vp = new VerticalPanel();

	    interface CellTableResource extends CellTable.Resources 
	    { 
	       public interface CellTableStyle extends CellTable.Style {}; 

	       @Source({"../shared/CustomCellTable.css"}) 
	       CellTableStyle cellTableStyle(); 
	    };
	    
	    Column<DriveResult, String> resultColumn = new Column<DriveResult, String>(new ClickableTextCell()) {
	        @Override
	        public String getValue(DriveResult driveResult) {
	        	return driveResult.title;
	        }
        };

	    DriveSearchResultsView(){
	        // Add the columns.
		      table.addColumn(resultColumn, "File Name (Click to open)");
		      
		      dataProvider.addDataDisplay(table);
			  pager.setDisplay(table);
			  
			  resultColumn.setFieldUpdater(new FieldUpdater<DriveResult, String>() {
				
				@Override
				public void update(int index, DriveResult object, String value) {
					// TODO Auto-generated method stub
					  JSFunctions.openURL(object.url, "_blank");
				}
			  });

			  
	   		//table.setColumnWidth(whatColumn, "300px");
	  		
	    	vp.add(new InlineHTML("<hr/>"));
	    	vp.add(new InlineHTML("<b>Results from Google Drive (Max restricted to 20)</b>"));
	    	vp.add(new InlineHTML("<hr/>"));
		    vp.add(table);
	  	    vp.add(pager);
	  	    vp.getElement().getStyle().setBackgroundColor("white");
	  	}
}

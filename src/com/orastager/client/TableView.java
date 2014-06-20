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

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.ImageResourceCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;

public class TableView {
	
    CellTableResource resource = GWT.create(CellTableResource.class);
    SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
    SimplePager pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
	CellTable<Node> table = new CellTable<Node>(10, resource);
	Image imgClosePanel = new Image(StaticVar.menuResources.close());
	HorizontalPanel horPanel = new HorizontalPanel();
  	
    ListDataProvider<Node> dataProvider = new ListDataProvider<Node>();

    List<Node> list = dataProvider.getList();

    VerticalPanel vp = new VerticalPanel();

    interface CellTableResource extends CellTable.Resources 
    { 
       public interface CellTableStyle extends CellTable.Style {}; 

       @Source({"../shared/CustomCellTable.css"}) 
       CellTableStyle cellTableStyle(); 
    };
    
    Column<Node, String> keywordColumn = new Column<Node, String>(new EditTextCell()) {
        @Override
        public String getValue(Node node) {
        	return StaticVar.locate.get(node.ti.getParentItem()).text;
        }
      };
      
    Column<Node, String> childtextColumn = new Column<Node, String>(new ClickableTextCell()) {
        @Override
        public String getValue(Node node) {
			if (node.text.length()>40)
			      return node.text.substring(0,40)+"....";
			else
				return node.text;
        }
      };
      
      Column<Node, ImageResource> imageColumn =
          new Column<Node, ImageResource>(new ImageResourceCell()) {
       @Override
       public ImageResource getValue(Node node) {
    	   return DmaUtil.getChildTextTypeImg(node.attr1, node.attr2, false);
    	 }
     };  

      DateCell dateCell = new DateCell(StaticVar.dateFormatter);
      Column<Node, Date> dateColumn = new Column<Node, Date>(dateCell) {
        @Override
        public Date getValue(Node node) {
            return node.creation_date;
        }
      };

      TableView(){  
          // Add the columns.
	      table.addColumn(keywordColumn, "KeyWord");
	      table.addColumn(childtextColumn, "ChildText");
	      table.addColumn(dateColumn, "Created");
	      table.addColumn(imageColumn, "Type");
	      dataProvider.addDataDisplay(table);
  		  pager.setDisplay(table);
  		  imageColumn.setSortable(true);
  		  dateColumn.setSortable(true);

  		  keywordColumn.setFieldUpdater(new FieldUpdater<Node, String>() {
  	          @Override
  	          public void update(int index, Node node, String value) {
  	        	  StaticVar.locate.get(node.ti.getParentItem()).text = value;
  	        	  StaticVar.locate.get(node.ti.getParentItem()).nodeTextBox.getOriginalWidget().setText(value);
  	        	  DriveUtil.setFileModified();
  	          }
  	        });
  		  
  		  
  		childtextColumn.setFieldUpdater(new FieldUpdater<Node, String>() {
	          @Override
	          public void update(int index, Node node, String value) {
	        	  DmaUtil.setCurrNode(node);
   	        	  Commands.maximizeCommand.execute();
	          }
	        });
  		  
  		  
  		table.setColumnWidth(keywordColumn, "300px");
  		table.setColumnWidth(childtextColumn, "300px");
  		table.setColumnWidth(dateColumn, "200px");
  		table.setColumnWidth(imageColumn, "30px");

  	    horPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
  	    horPanel.add(imgClosePanel);
  	    vp.add(horPanel);
  		vp.add(table);
  	    vp.add(pager);
  	    
  	    imgClosePanel.setTitle("Close..");
  	    imgClosePanel.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				StaticVar.scrAbsResultsPanel.setVisible(true);
				StaticVar.hp.setVisible(false);
			}
		});

  	    ListHandler<Node> columnSortHandler = new ListHandler<Node>(list);
  	    
  	  columnSortHandler.setComparator(imageColumn,
  	         new Comparator<Node>() {
  	           public int compare(Node o1, Node o2) {
   	               return o1.attr1.concat(o1.attr2).compareTo(o2.attr1.concat(o2.attr2));
  	             }
  	         });
  	     
  	     columnSortHandler.setComparator(dateColumn,
  	          new Comparator<Node>() {
  	            public int compare(Node o1, Node o2) {
  	            	return o1.creation_date.compareTo(o2.creation_date);
  	            }
  	          });
  	     
  	     table.addColumnSortHandler(columnSortHandler);
  	  }
      
      public void loadData(){
    	list.clear();
		for (Map.Entry<CustomTreeItem, Node> entry : StaticVar.locate.entrySet()) {
		  if (entry.getValue().type.equalsIgnoreCase("ChildText"))	
			list.add(entry.getValue());
		}
      }
}

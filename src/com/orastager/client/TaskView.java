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
import java.util.Set;

import com.google.gwt.cell.client.DatePickerCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.ImageResourceCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONArray;
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
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.xml.client.DOMException;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.XMLParser;

public class TaskView {

    CellTableResource resource = GWT.create(CellTableResource.class);
    SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
    SimplePager pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
	CellTable<Task> table = new CellTable<Task>(10, resource);
	Image imgAddTask = new Image(StaticVar.menuResources.NewKeyWord());
	Image imgFinishTask = new Image(StaticVar.menuResources.remove());
	Image imgClosePanel = new Image(StaticVar.menuResources.close());
	HorizontalPanel horPanel = new HorizontalPanel();
	Set<Task> selected;
  	    
	final MultiSelectionModel<Task> selectionModel = new MultiSelectionModel<Task>();
    
  	interface Resources extends ClientBundle {
  	  @Source("redFlag.gif")
  	  ImageResource missedTask();
  	  @Source("greenFlag.gif")
	  ImageResource notMissedTask();
  	}
  	
  	Resources resources = GWT.create(Resources.class);
	
    ListDataProvider<Task> dataProvider = new ListDataProvider<Task>();

    List<Task> list = dataProvider.getList();
    ListHandler<Task> columnSortHandler = new ListHandler<Task>(list);

    VerticalPanel vp = new VerticalPanel();

    interface CellTableResource extends CellTable.Resources 
    { 
       public interface CellTableStyle extends CellTable.Style {}; 

       @Source({"../shared/CustomCellTable.css"}) 
       CellTableStyle cellTableStyle(); 
    };

    Column<Task, String> whatColumn = new Column<Task, String>(new EditTextCell()) {
        @Override
        public String getValue(Task task) {
        	return task.what;
        }
      };

	  DatePickerCell dateCell = new DatePickerCell(StaticVar.dateFormatter);
	  Column<Task, Date> whenColumn = new Column<Task, Date>(dateCell) {
	    @Override
	    public Date getValue(Task task) {
	      try{	
	        return task.when;
	      }catch (Exception e){
	    	  return (new Date());
	      }
	    }
	  };
	  
	  ImageResourceCell imgCell = new ImageResourceCell();
      Column<Task, ImageResource> imgTaskStatusColumn =
          new Column<Task, ImageResource>(imgCell) {
       @Override
       public ImageResource getValue(Task task) {
    	   if (task.when.compareTo(new Date()) > 0)
		     return resources.notMissedTask();
    	   else
    		 return resources.missedTask();
 		}
      };
      
  	  Comparator<Task> comparator = new Comparator<Task>() {
			public int compare(Task o1, Task o2) {
				return o1.when.compareTo(o2.when);
			}
	  };

	  TaskView(){
        // Add the columns.
	      table.addColumn(whatColumn, "Task");
	      table.addColumn(whenColumn, "To be done by");
	      table.addColumn(imgTaskStatusColumn, "Status");
	      
	      dataProvider.addDataDisplay(table);
		  pager.setDisplay(table);
		  
		  whenColumn.setSortable(true);
		  
  		  whatColumn.setFieldUpdater(new FieldUpdater<Task, String>() {
  	          @Override
  	          public void update(int index, Task task, String value) {
  	        	  task.what = value;
  	        	  DriveUtil.setFileModified();
  	          }
  	        });
  		  
  		  whenColumn.setFieldUpdater(new FieldUpdater<Task, Date>() {
  	          @Override
  	          public void update(int index, Task task, Date value) {
  	        	  task.when = value;
  	        	  table.redraw();
  	        	  DriveUtil.setFileModified();
  	          }
  	        });
  		  
   		table.setColumnWidth(whatColumn, "300px");
  		table.setColumnWidth(whenColumn, "200px");
  		table.setColumnWidth(imgTaskStatusColumn, "30px");
  		
  	    table.setSelectionModel(selectionModel);
  	    
  	    selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
  	        public void onSelectionChange(SelectionChangeEvent event) {
  	          selected = selectionModel.getSelectedSet();
  	        }
  	    });

  	    horPanel.add(imgAddTask);
  		horPanel.add(imgFinishTask);
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
  	  
  	  columnSortHandler.setComparator(whenColumn, comparator);

  	  table.addColumnSortHandler(columnSortHandler);
  	  
	  	imgAddTask.setTitle("Add new task");
		imgAddTask.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				list.add(0, new Task());
				table.redraw();
				selectionModel.clear();
				selectionModel.setSelected(list.get(0), true);
				table.setFocus(true);
				DriveUtil.setFileModified();
			}
		});
		
		imgFinishTask.setTitle("Mark finish");
		imgFinishTask.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				for(Task task:selected){
					list.remove(task);
					table.redraw();
					table.setFocus(true);
					DriveUtil.setFileModified();
				}
			}
		});
  	}
    
	public void loadTasks(String str, String tagName){
		try {
			Document dom = XMLParser.parse(str);
			String what;
			Date when;
			
			for(int i = dom.getElementsByTagName(tagName).getLength()-1; i>=0; i--){

				what = dom.getElementsByTagName(tagName).item(i).getAttributes().item(0).toString().replace("Ora_sq","\'").replace("Ora_dq","\"").replace("Ora_nl","\n").replace("Ora_amb","&").replace("Ora_la","<").replace("Ora_ra",">");
				try{
				 when = StaticVar.dateFormatter.parse(dom.getElementsByTagName(tagName).item(i).getAttributes().item(1).toString());
				}catch(Exception e){
					when = new Date();
				}
				
				Task newTask = new Task(what, when);
				list.add(newTask);

				if (newTask.when.compareTo(new Date()) < 0){
					DmaUtil.showNotification("Missed your task!", newTask.what);
    		    }
		    }
			
			table.redraw();

		} catch (DOMException e) {
		    DMA.showMsg("Did not find XML for tasks!");
		} catch (Exception e) {
			DMA.showMsg(e.getMessage());
		}
	}

	/*public String printTasks(){
		String s = "<Tasks>";
		for (int i=0; i<list.size(); i++){
			s += "<Task what=\""+ list.get(i).what.replace("\'","Ora_sq").replace("\"", "Ora_dq").replace("\n", "Ora_nl").replace("&","Ora_amb").replace("<", "Ora_la").replace(">", "Ora_ra") + "\"";
			s += " when=\""+ StaticVar.dateFormatter.format(list.get(i).when) + "\"";
			s += "></Task>";
		}
		s += "</Tasks>";
		return s;
	}*/
	
	public JSONArray buildTasksJson(){
		JSONArray arrayTasks= new JSONArray();
		for (int i = 0; i < list.size(); i++) {
		   arrayTasks.set(i, list.get(i).toJson());
		}
		return arrayTasks;
	}
}
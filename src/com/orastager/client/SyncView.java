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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.SelectionCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;

public class SyncView {

    CellTableResource resource = GWT.create(CellTableResource.class);
    SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
    SimplePager pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
    CellTable<NodePair> table = new CellTable<NodePair>(8, resource); //(8, resource);
	Image imgAddNode = new Image(StaticVar.menuResources.NewKeyWord());
	Image imgRemoveNode = new Image(StaticVar.menuResources.remove());
	Image imgMoveNode = new Image(StaticVar.menuResources.moveTo());
	Image imgClosePanel = new Image(StaticVar.menuResources.close());
	HorizontalPanel horPanel = new HorizontalPanel();
	Set<NodePair> selected = new HashSet<NodePair>();
	List<String> typeList = new ArrayList<String>();
	
	final MultiSelectionModel<NodePair> selectionModel = new MultiSelectionModel<NodePair>();
    
  	interface Resources extends ClientBundle {
  	  @Source("deleteNode.gif")
  	  ImageResource deleteNode();
  	}
  	
  	Resources resources = GWT.create(Resources.class);
	
    ListDataProvider<NodePair> dataProvider = new ListDataProvider<NodePair>();

    List<NodePair> list = dataProvider.getList();

    VerticalPanel vp = new VerticalPanel();

    interface CellTableResource extends CellTable.Resources 
    { 
       public interface CellTableStyle extends CellTable.Style {}; 

       @Source({"../shared/CustomCellTable.css"}) 
       CellTableStyle cellTableStyle(); 
    };

    EditTextCell editTextCell = new EditTextCell();
    Column<NodePair, String> keywordColumn = new Column<NodePair, String>(editTextCell) {
    	        @Override
    	        public String getValue(NodePair nodePair) {
    	        	return nodePair.nodeKeyWord.text;
    	        }
    };
      
    Column<NodePair, String> childtextColumn = new Column<NodePair, String>(new TextAreaInputCell()) {
        @Override
        public String getValue(NodePair nodePair) {
			return nodePair.nodeChildText.text;
        }
      };
      
      
    SyncView(){
    
    	for (String str: StaticVar.childTextTypeList)
    		typeList.add(str);
		
	      Column<NodePair, String> typeColumn = new Column<NodePair, String>(new SelectionCell(typeList)) {
	          @Override
	          public String getValue(NodePair nodePair) {
	        	  if (DmaUtil.isCodeNode(nodePair.nodeChildText))
	  			    return nodePair.nodeChildText.attr2;
	        	  else
	        		return nodePair.nodeChildText.attr1;  
	          }
	        };  

        // Add the columns.
	      table.addColumn(keywordColumn, "Keyword");
	      table.addColumn(childtextColumn, "Child Text");
	      table.addColumn(typeColumn, "Type");
	      
	      dataProvider.addDataDisplay(table);
		  pager.setDisplay(table);
		  
		  keywordColumn.setFieldUpdater(new FieldUpdater<NodePair, String>() {
  	          @Override
  	          public void update(int index, NodePair nodePair, String value) {
  	        	nodePair.nodeKeyWord.text = value;
  	        	nodePair.nodeKeyWord.nodeTextBox.getOriginalWidget().setText(value);
  	          }
  	        });
  		  
		  childtextColumn.setFieldUpdater(new FieldUpdater<NodePair, String>() {
  	          @Override
  	          public void update(int index, NodePair nodePair, String value) {
  	        	nodePair.nodeChildText.text = value;
  	        	nodePair.nodeChildText.nodeTextArea.setText(value);
  	          }
  	        });
		  
		  typeColumn.setFieldUpdater(new FieldUpdater<NodePair, String>() {
			@Override
			public void update(int index, NodePair nodePair, String value) {
				// TODO Auto-generated method stub
				if (value.equalsIgnoreCase(StaticVar.ChildTextTypes.type_notes) ||
					value.equalsIgnoreCase(StaticVar.ChildTextTypes.type_text) ||
					value.equalsIgnoreCase(StaticVar.ChildTextTypes.type_url) ||
					value.equalsIgnoreCase(StaticVar.ChildTextTypes.type_pdf) ||
					value.equalsIgnoreCase(StaticVar.ChildTextTypes.type_word) ||
					value.equalsIgnoreCase(StaticVar.ChildTextTypes.type_excel) ||
					value.equalsIgnoreCase(StaticVar.ChildTextTypes.type_music) ||
					value.equalsIgnoreCase(StaticVar.ChildTextTypes.type_video) ||
					value.equalsIgnoreCase(StaticVar.ChildTextTypes.type_video_pl) ||
					value.equalsIgnoreCase(StaticVar.ChildTextTypes.type_img)){
				 nodePair.nodeChildText.attr1 = value;
				 nodePair.nodeChildText.attr2 = "";
				}
				else{
				 nodePair.nodeChildText.attr1 = StaticVar.ChildTextTypes.type_text;
				 nodePair.nodeChildText.attr2 = value;
				}
			}
		  });
  		  
   		table.setColumnWidth(keywordColumn, "300px");
  		table.setColumnWidth(childtextColumn, "300px");
  		table.setColumnWidth(typeColumn, "100px");
  		
  	    table.setSelectionModel(selectionModel);
  	    
  	    selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
  	        public void onSelectionChange(SelectionChangeEvent event) {
  	          selected = selectionModel.getSelectedSet();
  	        }
  	    });  	    

  	    horPanel.add(imgAddNode);
  	    horPanel.add(imgRemoveNode);
  	    horPanel.add(imgMoveNode);
  	    horPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
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
  	  
	  	imgAddNode.setTitle("Add new node");
		imgAddNode.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
	    		NodePair nodePair = new NodePair("Key Word", "Child Text");
				list.add(nodePair);
				table.redraw();
				if (list.size() > 7)
					 pager.lastPage();
				selectionModel.clear();
				selectionModel.setSelected(list.get(list.size()-1), true);
				table.setFocus(true);
			}
		});
		
		imgRemoveNode.setTitle("Remove Node(s)");
		imgRemoveNode.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				for (NodePair nodePair: selected){
					selectionModel.setSelected(nodePair, false);
					list.remove(nodePair);
				}
			}
		});
		
		imgMoveNode.setTitle("Move selected");
		imgMoveNode.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
			  if (selected.size() > 0){
				DmaUtil.buildMoveNodeTree(StaticVar.root_ti, StaticVar.root_moveNode_ti);
				Commands.showMoveTreeCommand.execute();
				StaticVar.selectedNodePairList.clear();
				for (NodePair nodePair: selected)
					StaticVar.selectedNodePairList.add(nodePair);
			  }else DMA.showMsg("Select before moving..");
			}
		});
		
  	}
    
	public void loadSyncData(String syncData){
		boolean nodePresent;
		try{
			  if (syncData.equalsIgnoreCase("Deleted sync data"))
				  DMA.showMsg("Removed sync data");
			  else if (syncData.equalsIgnoreCase("") == false){
				  JSONValue value = JSONParser.parseStrict(syncData);
				  JSONArray arrayNodes = value.isArray();
				  for (int i=0; i<arrayNodes.size();i++){
					  JSONObject jsonObj = arrayNodes.get(i).isObject();
					    nodePresent = false;
						for (NodePair nPair:list){
						  if (nPair.nodeKeyWord.text.equalsIgnoreCase(jsonObj.get("kw").isString().stringValue()) &&
								  nPair.nodeChildText.text.equalsIgnoreCase(jsonObj.get("ct").isString().stringValue()))
								 nodePresent = true; 
						}

						if (nodePresent == false){
							NodePair nodePair = new NodePair(jsonObj.get("kw").isString().stringValue(), jsonObj.get("ct").isString().stringValue());
							String type = jsonObj.get("tp").isString().stringValue();
							if (type.equalsIgnoreCase(StaticVar.ChildTextTypes.type_notes) ||
								type.equalsIgnoreCase(StaticVar.ChildTextTypes.type_text) || 
									type.equalsIgnoreCase(StaticVar.ChildTextTypes.type_url) ||
									type.equalsIgnoreCase(StaticVar.ChildTextTypes.type_pdf) ||
									type.equalsIgnoreCase(StaticVar.ChildTextTypes.type_word) ||
									type.equalsIgnoreCase(StaticVar.ChildTextTypes.type_excel) ||
									type.equalsIgnoreCase(StaticVar.ChildTextTypes.type_music) ||
									type.equalsIgnoreCase(StaticVar.ChildTextTypes.type_video) ||
									type.equalsIgnoreCase(StaticVar.ChildTextTypes.type_video_pl) ||
									type.equalsIgnoreCase(StaticVar.ChildTextTypes.type_img)){
								 nodePair.nodeChildText.attr1 = type;
								 nodePair.nodeChildText.attr2 = "";
								}
								else{
								 nodePair.nodeChildText.attr1 = StaticVar.ChildTextTypes.type_text;
								 nodePair.nodeChildText.attr2 = type;
								}
							StaticVar.syncView.list.add(nodePair);
						}
				  }
				 table.redraw();
				 Commands.showSyncViewCommand.execute();
			  }else DMA.showMsg("No data to sync!");
			}
			catch(Exception e){			
			}
	}
}

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

import java.util.Arrays;
import java.util.HashSet;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;


public class SearchUtil {
	public static int depth;
	public static int height;
	public static String SPLIT_COMMA = " ";
	
	public static HashSet<String> removeDuplicates(String str) {
	    return new HashSet<String>(Arrays.asList(str.split(SPLIT_COMMA)));
	}
	
	public static void searchString(String searchStr, Boolean maximizeFlag){
		
	  if (StaticVar.moveNodeTreePopPanel.isShowing()){
		  StaticVar.searchListMoveTree.clear();
		  searchMoveNodeTree(StaticVar.root_ti, searchStr);
		  if (StaticVar.searchListMoveTree.size() > 0){
			    StaticVar.moveNodeTree.setSelectedItem(StaticVar.searchListMoveTree.get(0));
			    StaticVar.moveNodeTree.setFocus(true);
			    Timers.setScrollPositionMoveNode(StaticVar.searchListMoveTree.get(0));
		  }
		  return;
	  }

	  if (searchStr.equalsIgnoreCase("dma-speech")){
		  //searchStr = suggestBox.getText();
		  StaticVar.suggestBox.showSuggestionList();
		  return;
	  }
	  
		if(StaticVar.tabPanel.getSelectedIndex()>0){  
			StaticVar.tabPanel.selectTab(0);
		}

		//collapseAll(root_ti);

		StaticVar.searchList.clear();
    	StaticVar.searchAbsResultsPanel.clear();
    	//StaticVar.hp.setVisible(false);
    	StaticVar.scrAbsResultsPanel.setVisible(true);

    	if (StaticVar.searchchkBox.getValue() && StaticVar.resource_id.equalsIgnoreCase("new_file") == false){
        	JSFunctions.searchDrive(searchStr, "fullText contains '"+ searchStr + "' and trashed = false");
        	StaticVar.driveResultsView.list.clear();
        	StaticVar.driveResultsView.table.redraw();
        	StaticVar.driveResultsView.vp.setVisible(true);
        	StaticVar.searchResultsPanelHdr.setVisible(true);
        }else{
        	StaticVar.driveResultsView.vp.setVisible(false);
        	StaticVar.searchResultsPanelHdr.setVisible(false);
        }
      
        searchTree(StaticVar.root_ti, removeDuplicates(searchStr));

        if (StaticVar.searchList.size() > 0){
	    	Timers.setScrollPosition2();
	    }

	    if (StaticVar.searchList.size() == 1)
	    	if (StaticVar.searchList.get(0).getChildCount() == 1 )
	    		if (StaticVar.locate.get(StaticVar.searchList.get(0).getChild(0)).type.equalsIgnoreCase("ChildText") == true)
	    	    	if (maximizeFlag == true){
	    	    		Timers.maximize(StaticVar.locate.get(StaticVar.searchList.get(0).getChild(0)));
	    	    		if(Timers.tabIndex>0)
	    	    			Timers.delayedSelectTab();
	    	    	}

	    //if (searchList.size() > 0)
	    	//dmaTree.setFocus(true);
	    	
	    if (StaticVar.searchList.size() == 0){
	    	if (Window.confirm("Not found! Do you want to search in Google?")){
	    		JSFunctions.openURL("https://www.google.com/#sclient=psy-ab&hl=en&q="+StaticVar.suggestBox.getText(),"_blank");
	    	}
	    }
	    
	    if (StaticVar.searchList.size() > 1){
	    	StaticVar.tabPanel.selectTab(0);
	    }
	    
	    if (StaticVar.searchList.size() > 0){
	    	height = 0;
		    for (int i=0; i<StaticVar.searchList.size(); i++){
		    	depth = 0;
		    	addSearchListItem(StaticVar.searchList.get(i), removeDuplicates(searchStr), true);
		    	height = height + depth*20+30;
		    }
		    if (height > (StaticVar.tabPanelWidth-60))
		    	StaticVar.searchAbsResultsPanel.setPixelSize(StaticVar.tabPanelWidth-25, height+30);
		    else
		    	StaticVar.searchAbsResultsPanel.setPixelSize(StaticVar.tabPanelWidth-25, StaticVar.tabPanelWidth-60);
	    }
	    	    
	    StaticVar.suggestBox.getTextBox().setFocus(true);
	    StaticVar.suggestBox.getTextBox().selectAll();
	}
	
	public static void addDriveSearchResult(String title, String descr, String url){
		DriveResult driveResult = new DriveResult(title, descr, url);
		StaticVar.driveResultsView.list.add(driveResult);
	}
	
	public static void redrawDriveSearchTable(){
		StaticVar.driveResultsView.table.redraw();
	}
	
	/*private static void searchStringTableView(String searchStr) {
		StaticVar.tableView.loadData();
		// TODO Auto-generated method stub
		for (int i = 0; i < StaticVar.tableView.list.size(); i++) {
			if (StaticVar.locate.get(StaticVar.tableView.list.get(i).ti.getParentItem()).text.toLowerCase().contains(searchStr.toLowerCase()) == false){
				StaticVar.tableView.list.remove(i);
				i -= 1;
			}
		}
		StaticVar.tableView.table.redraw();
	}*/

	private static void addSearchListItem(final CustomTreeItem ti, HashSet<String> searchStrSet, boolean highlight) {
    	if (StaticVar.locate.get(ti).type.equalsIgnoreCase("Root") == false)
    		addSearchListItem((CustomTreeItem) ti.getParentItem(), searchStrSet, false);
    	
    	String html_str = StaticVar.locate.get(ti).text.replace("<","lt;").replace(">","rt;").replace("\n", " ");
    	
    	if (StaticVar.locate.get(ti).isKeyWord() && highlight){
    		html_str = "";
    		for (String str: StaticVar.locate.get(ti).text.split(SPLIT_COMMA)){
    			found = false;
        		for (String searchStr: searchStrSet){
        			if (str.equalsIgnoreCase(searchStr)){
        			  html_str = html_str + "<span style=\"background-color:#e5eecc;\">"+str+"</span>" + SPLIT_COMMA;
        			  found = true;
        			}
        		}
        		if (found == false)
        		  html_str = html_str + str + SPLIT_COMMA;
        	}
    	}
    	
    	if (StaticVar.locate.get(ti).isKeyWord() == false){
    		if (StaticVar.locate.get(ti).text.length() >= 70)
	    			html_str = html_str.substring(0,70);
    	}
    	
    	HorizontalPanel hp = new HorizontalPanel();
    	HTML html = new HTML(html_str);
    	hp.add(html);
		// TODO Auto-generated method stub
    	/*try{
    	  for (String str: searchStrSet){
  	    	if (StaticVar.locate.get(ti).text.length() < 70)
	    		html_str = html_str.replace(str, "<span style=\"background-color:#e5eecc;\">"+str+"</span>");
	    	else{
	    		if (StaticVar.locate.get(ti).text.lastIndexOf(str) > 70)
	    			html_str = html_str.substring(StaticVar.locate.get(ti).text.lastIndexOf(str)-70, StaticVar.locate.get(ti).text.lastIndexOf(str)+str.length()).replace(str, "<span style=\"background-color:#e5eecc;\">"+str+"</span>");
	    		else
	    			html_str = html_str.substring(0, 70).replace(str, "<span style=\"background-color:#e5eecc;\">"+str+"</span>");
	    	}
    	  }
    	
    	html = new HTML(html_str);
    	}catch (Exception e){html = new HTML(html_str);}*/

    	html.getElement().getStyle().setColor("black");
    	html.getElement().getStyle().setCursor(Cursor.POINTER);
    	
    	html.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
		    	if (StaticVar.locate.get(ti).isKeyWord()){
		    		Timers.setScrollPosition(ti.getElement().getOffsetTop());
		    		Timers.delayedNodeFocus(StaticVar.locate.get(ti), false);
		    	}
			}
    	});
    	
		html.setTitle("Click to focus");
    	
    	if (highlight){
    		html.getElement().getStyle().setFontWeight(Style.FontWeight.BOLD);
	    	
    		if (ti.getChildCount() > 0 && StaticVar.locate.get(ti.getChild(0)).isKeyWord() == false){
	    		html.getElement().getStyle().setColor("blue");
	    		html.setTitle("Click to focus. Double click to maximize.");
	    		CustomTag tag = new CustomTag(DmaUtil.getChildTextType(StaticVar.locate.get(ti.getChild(0)).attr1,StaticVar.locate.get(ti.getChild(0)).attr2), false);
	    		tag.getElement().getStyle().setMarginLeft(15, Unit.PX);
	    		tag.getElement().getStyle().setCursor(Cursor.DEFAULT);
	    		hp.add(tag);
    		}

	    	if(StaticVar.locate.get(ti).isKeyWord() == false){
	    		html.getElement().getStyle().setColor("green");
	    		html.setTitle("Click to focus. Double click to maximize.");
	    		CustomTag tag = new CustomTag(DmaUtil.getChildTextType(StaticVar.locate.get(ti).attr1,StaticVar.locate.get(ti).attr2), false);
	    		tag.getElement().getStyle().setMarginLeft(15, Unit.PX);
	    		tag.getElement().getStyle().setCursor(Cursor.DEFAULT);
	    		hp.add(tag);
	    	}

	    	html.addDoubleClickHandler(new DoubleClickHandler() {
				@Override
				public void onDoubleClick(DoubleClickEvent event) {
					// TODO Auto-generated method stub
			    	if (ti.getChildCount() > 0 && StaticVar.locate.get(ti.getChild(0)).isKeyWord() == false){
			    		DmaUtil.setCurrNode(StaticVar.locate.get(ti.getChild(0))); 
			    		Commands.maximizeCommand.execute();
			    	}

			    	if(StaticVar.locate.get(ti).isKeyWord() == false){
			    		DmaUtil.setCurrNode(StaticVar.locate.get(ti)); 
			    		Commands.maximizeCommand.execute();
			    	}
				}
			});
    	}
    	depth = depth + 1;
    	if (StaticVar.locate.get(ti).type.equalsIgnoreCase("Root") == false){
    		StaticVar.searchAbsResultsPanel.add(new Image(StaticVar.menuResources.inherit()), depth*10+10, height+depth*20+5);
    		StaticVar.searchAbsResultsPanel.add(hp, depth*10+25, height+depth*20+5);
    	}else
    		StaticVar.searchAbsResultsPanel.add(hp, depth*10+20, height+depth*20+5);
	}
	
	public static boolean found;

	//searchFunction
	public static void searchTree(CustomTreeItem ti, HashSet<String> searchStrSet) {
		
	  if (StaticVar.searchchkBox.getValue()){
	   found = true;
	   for (String str: searchStrSet){
		if (StaticVar.locate.get(ti).text.toLowerCase().contains(str.toLowerCase()) == false){
			found = false;
			break;
	    }
	   }

	   if (found){
			StaticVar.locate.get(ti).nodeTextBox.addStyleName("SearchMatchNode");
			StaticVar.locate.get(ti).nodeTextArea.addStyleName("SearchMatchNode");
			StaticVar.searchList.add(ti);
			setState(ti,true);
	   }else{
			StaticVar.locate.get(ti).nodeTextBox.removeStyleName("SearchMatchNode");
	    	StaticVar.locate.get(ti).nodeTextArea.removeStyleName("SearchMatchNode");
	   }
	  }
	  else {

	   found = true;
	   for (String str: searchStrSet){
		if (StaticVar.locate.get(ti).nodeTextBox.getOriginalWidget().getText().toLowerCase().contains(str.toLowerCase()) == false){
			found = false;
			break;
	    }
	   }

	   if (found){
			StaticVar.locate.get(ti).nodeTextBox.addStyleName("SearchMatchNode");
			StaticVar.searchList.add(ti);
			setState(ti,true);
			StaticVar.locate.get(ti).nodeTextBox.getOriginalWidget().show(DmaUtil.getNodeTextBoxSize(StaticVar.locate.get(ti).text));
	   }else
			StaticVar.locate.get(ti).nodeTextBox.removeStyleName("SearchMatchNode");
	   }
		
		for(int i=0; i<ti.getChildCount(); i++){
			searchTree((CustomTreeItem) ti.getChild(i), searchStrSet);
		}
	}

	public static void setState(CustomTreeItem ti, boolean t_f){
		  if (ti.equals(StaticVar.root_ti) == false)
			  setState((CustomTreeItem) ti.getParentItem(), t_f);
			ti.setState(t_f);
	}

	private static void searchMoveNodeTree(CustomTreeItem ti,
			String search_str) {
		// TODO Auto-generated method stub
		if (StaticVar.locate.get(ti).nodeTextBox.getOriginalWidget().getText().toLowerCase().contains(search_str.toLowerCase())){
			((Label) StaticVar.locateMoveNodeMap.get(ti).getWidget()).getElement().getStyle().setColor("#00CCFF");
			StaticVar.searchListMoveTree.add(StaticVar.locateMoveNodeMap.get(ti));
			setMoveNodeState(ti, true);
	    }else
	    	((Label) StaticVar.locateMoveNodeMap.get(ti).getWidget()).getElement().getStyle().setColor("white");

		for(int i=0; i<ti.getChildCount(); i++){
			if (StaticVar.locate.get(ti.getChild(i)).isKeyWord())
			  searchMoveNodeTree((CustomTreeItem) ti.getChild(i), search_str);
		}
	}

	public static void setMoveNodeState(CustomTreeItem ti, boolean t_f){
		  if (ti.equals(StaticVar.root_ti) == false)
			  setMoveNodeState((CustomTreeItem) ti.getParentItem(), t_f);
			StaticVar.locateMoveNodeMap.get(ti).setState(t_f);
	}
}

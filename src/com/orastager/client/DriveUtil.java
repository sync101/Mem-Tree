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

import java.util.logging.Level;
import java.util.logging.LogRecord;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;

public class DriveUtil {
	
	public static void openDriveFile(String fileId){
		Window.Location.replace(StaticVar.APP_BASE_URL+"/index.html?fileId="+fileId);
	}
	
	public static void setFileModified(){
		//Window.setTitle("*"+StaticVar.resource_title);
		//StaticVar.resource_modified = true;
	}
	
	public static void setFileSaving(){
		//StaticVar.resource_modified = false;
		StaticVar.savingFlag = true;
	}
	
	public static void openPickerDriveFile(String fileId){
		
		String value = Window.Location.getParameter("fileId");
	     if (value != null){
	    	 if (value.length() > 0){
	    		 if (fileId.equalsIgnoreCase(value))
	    			 DMA.showMsg("Same file is open currently");
	    		 else
	    		   JSFunctions.openURL(StaticVar.APP_BASE_URL+"/index.html?fileId="+fileId, "_blank");
	    		 return;
	    	 }
	     }
	     if (StaticVar.root_node.ti.getChildCount() > 0)
	    	 JSFunctions.openURL(StaticVar.APP_BASE_URL+"/index.html?fileId="+fileId, "_blank");
	     else
	         Window.Location.replace(StaticVar.APP_BASE_URL+"/index.html?fileId="+fileId);
	}
	
	public static void createNewFile(){
    	JSFunctions.openURL(StaticVar.APP_BASE_URL+"/start", "_blank");
	}

	public static void loadDriveFile(final String fileId){
		  DMA.showMsgPopup("Loading file..");
		  //DMA.showMsg("Mem-tree requests Google drive auth if expired. Make sure browser didn't block the popup for mem-tree.");
		  StaticVar.resource_id = fileId;
		  
			  try{
				JSFunctions.loadRealTimeForFile("get", StaticVar.resource_id);
			  }catch (Exception e){
				DMA.showMsg(e.getMessage());
			  }
	 }
	
	public static void loadDriveFileContent(String content, String title, String mimeType){
	  DMA.showMsgPopup("Loading file..");
	  if (content != null){
		if (title.endsWith(".dma")){			
		     DMA.loadText(content.substring(2, content.length()-2));						
      	     StaticVar.root_ti.setState(true);
      	     //Commands.showTaskViewCommand.execute();
		}else{
			RootPanel.get("RightPane").getElement().removeFromParent();
			RootPanel.get("LeftPane").getElement().removeFromParent();
			RootPanel.get("TopPane").getElement().removeFromParent();
			RootPanel.get("ResizePane").getElement().removeFromParent();

			StaticVar.tabPanel.setVisible(false);
			StaticVar.driveIcon.setVisible(false);
			StaticVar.suggestBox.setVisible(false);
			StaticVar.searchchkBox.setVisible(false);
			
			StaticVar.rootPanel.add(StaticVar.horCollaboratorsPanel, 10, 7);

		    StaticVar.codeTextArea.setText(content.substring(2, content.length()-2));
		    StaticVar.codeTextArea.getElement().setId("codeTab1");
			
			Grid grid = new Grid(1, 1);
		    grid.setWidget(0, 0, StaticVar.codeTextArea);
			StaticVar.rootPanel.add(grid, 5, 45);
			
			
			StaticVar.rootPanel.add(StaticVar.menuBar, Window.getClientWidth()/2, 10);
			Label mode = new Label("Change Mode");
			mode.getElement().getStyle().setColor("white");
			final ListBox codeType = new ListBox();
			StaticVar.rootPanel.add(mode, Window.getClientWidth() - 250, 22);
			StaticVar.rootPanel.add(codeType, Window.getClientWidth() - 150, 20);
			
			codeType.addItem("", "no_mode");
			for (int i=0;i<StaticVar.childTextCodeTypeList.size();i++) {
				codeType.addItem(StaticVar.childTextCodeTypeList.get(i), StaticVar.childTextCodeTypeMimeList.get(i));
			}
		    
		    codeType.addChangeHandler(new ChangeHandler() {
				@Override
				public void onChange(ChangeEvent event) {
					// TODO Auto-generated method stub
					if (codeType.getValue(codeType.getSelectedIndex()).equalsIgnoreCase("no_mode") == false)
						DmaUtil.setEditorMode(1, codeType.getItemText(codeType.getSelectedIndex()), codeType.getValue(codeType.getSelectedIndex()));
				}
			});
			
			int width, height;
			width = Window.getClientWidth()-30;
			height = Window.getClientHeight()-70;
			
			//"text/x-csrc","text/x-c++src","text/css","xml","text/x-java","text/javascript","text/x-mysql","application/x-httpd-php"
            //,"shell","text/x-plsql","xml","text/x-rsrc"
			if (mimeType.equalsIgnoreCase("text/x-c"))
				DmaUtil.createCodeEditor(1, StaticVar.childTextCodeTypeList.get(0), StaticVar.childTextCodeTypeMimeList.get(0), width, height);
			else if (mimeType.equalsIgnoreCase("text/x-cpp"))
				DmaUtil.createCodeEditor(1, StaticVar.childTextCodeTypeList.get(1), StaticVar.childTextCodeTypeMimeList.get(1), width, height);
			else if (mimeType.equalsIgnoreCase("text/css"))
				DmaUtil.createCodeEditor(1, StaticVar.childTextCodeTypeList.get(2), StaticVar.childTextCodeTypeMimeList.get(2), width, height);
			else if (mimeType.equalsIgnoreCase("text/html"))
				DmaUtil.createCodeEditor(1, StaticVar.childTextCodeTypeList.get(3), StaticVar.childTextCodeTypeMimeList.get(3), width, height);
			else if (mimeType.equalsIgnoreCase("text/x-java"))
				DmaUtil.createCodeEditor(1, StaticVar.childTextCodeTypeList.get(4), StaticVar.childTextCodeTypeMimeList.get(4), width, height);
			else if (mimeType.equalsIgnoreCase("application/x-javascript"))
				DmaUtil.createCodeEditor(1, StaticVar.childTextCodeTypeList.get(5), StaticVar.childTextCodeTypeMimeList.get(5), width, height);
			else if (mimeType.equalsIgnoreCase("application/x-mysql"))
				DmaUtil.createCodeEditor(1, StaticVar.childTextCodeTypeList.get(6), StaticVar.childTextCodeTypeMimeList.get(6), width, height);
			else if (mimeType.equalsIgnoreCase("text/x-php"))
				DmaUtil.createCodeEditor(1, StaticVar.childTextCodeTypeList.get(7), StaticVar.childTextCodeTypeMimeList.get(7), width, height);
			else if (mimeType.equalsIgnoreCase("text/x-sql"))
				DmaUtil.createCodeEditor(1, StaticVar.childTextCodeTypeList.get(9), StaticVar.childTextCodeTypeMimeList.get(9), width, height);
			else if (mimeType.equalsIgnoreCase("text/xml"))
				DmaUtil.createCodeEditor(1, StaticVar.childTextCodeTypeList.get(10), StaticVar.childTextCodeTypeMimeList.get(10), width, height);
			else
				DmaUtil.createCodeEditor(1, StaticVar.childTextCodeTypeList.get(0), StaticVar.childTextCodeTypeMimeList.get(0), width, height);
			
			StaticVar.dma_file = false;
		}
		setWindowTitle(title);
	  }else {
		  DMA.showMsg("error loading file");
	  }
			//expandAll(StaticVar.root_ti);
      	DMA.hideMsgPopUp();
	}
	
   public static void setWindowTitle(String title){
		DMA.showMsg("Loaded file: "+title);
	    Window.setTitle(title);
	    StaticVar.resource_title = title;

	  /*  if (StaticVar.resource_id.equalsIgnoreCase("0BwVzf8LNyklCQWUwMHFqVU1RbXM")){
	    	Timers.showProcessing(800);
	    	StaticVar.suggestBox.setText("java");
			SearchUtil.searchString(StaticVar.suggestBox.getText(), true);
			StaticVar.suggestBox.showSuggestionList();
	    }	    	
	    if (StaticVar.resource_id.equalsIgnoreCase("0BwVzf8LNyklCUGZYQXAyblJydmM")){
	    	Timers.showProcessing(800);
	    	StaticVar.suggestBox.setText("step by step to create a file");
			SearchUtil.searchString(StaticVar.suggestBox.getText(), true);
	    }*/	    	
	    
	    DmaUtil.setOrigText();
   }
	
	public static void downloadDMA() {
	  if (StaticVar.resource_id.equalsIgnoreCase("new_file") == false){	
		Window.open(StaticVar.APP_BASE_URL+"/svc?file_id=Download:"+StaticVar.resource_id, "", "");
	  } else DMA.showMsg("No file is open currently!");
	}
	
	public static void clearCollaborators(){
		StaticVar.horCollaboratorsPanel.clear();
		StaticVar.collaboratorsList.clear();
	}
	
	public static void addCollaborator(String photoUrl, String userId, String displayName, String color, String sessionId){
		CustomImage collaboratorImg = new CustomImage(photoUrl, 25, 25);
		collaboratorImg.setTitle(displayName);
		collaboratorImg.getElement().getStyle().setPadding(2, Style.Unit.PX);
		collaboratorImg.userId = userId;
		collaboratorImg.color = color;
		collaboratorImg.sessionId = sessionId;
		for (int i=0;i<StaticVar.collaboratorsList.size();i++){
			if (((userId+color+sessionId).equalsIgnoreCase(StaticVar.collaboratorsList.get(i).userId+StaticVar.collaboratorsList.get(i).color+StaticVar.collaboratorsList.get(i).sessionId))){
				return; // duplicate
			}
		}
		StaticVar.horCollaboratorsPanel.add(collaboratorImg);
		collaboratorImg.show(25);
		StaticVar.collaboratorsList.add(collaboratorImg);
	}
	
	public static void removeCollaborator(String imgUrl, String userId, String displayName, String color, String sessionId){
		for (int i=0;i<StaticVar.collaboratorsList.size();i++){
			if (((userId+color+sessionId).equalsIgnoreCase(StaticVar.collaboratorsList.get(i).userId+StaticVar.collaboratorsList.get(i).color+StaticVar.collaboratorsList.get(i).sessionId))){
				StaticVar.collaboratorsList.remove(i);
				i = i - 1;
				for (int j=0;j<StaticVar.horCollaboratorsPanel.getWidgetCount();j++)
					if (userId.equalsIgnoreCase(((CustomImage) StaticVar.horCollaboratorsPanel.getWidget(j)).userId)
							&&color.equalsIgnoreCase(((CustomImage) StaticVar.horCollaboratorsPanel.getWidget(j)).color)
							&&sessionId.equalsIgnoreCase(((CustomImage) StaticVar.horCollaboratorsPanel.getWidget(j)).sessionId)){
						((CustomImage) StaticVar.horCollaboratorsPanel.getWidget(j)).hide(25);
						StaticVar.horCollaboratorsPanel.remove(j);
					}
			}
		}
	}

	/*public static void readUrlTitle(final Node node, String url){
		if (url.contains("www") || url.contains("http")){
			RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET,"/readtitle?url="+url);
			try {
			    requestBuilder.sendRequest(null, new RequestCallback() {
					 public void onError(Request request, Throwable exception) {
						    DMA.showMsg("Error getting title!");
			  }

			@Override
			public void onResponseReceived(Request request, Response response) {
				node.text = response.getText();
				node.nodeTextBox.setText(response.getText());
			}
			});
			  } catch (RequestException ex) {
				  DMA.showMsg("Exception getting title!");
			  }
		}
	 }*/
}
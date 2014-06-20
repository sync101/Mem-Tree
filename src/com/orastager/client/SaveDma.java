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

import java.util.Map;
import java.util.Map.Entry;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.TextArea;

public class SaveDma {
	
	static String title;
	
	public static void saveDMA() {
	  if (StaticVar.readonly_file){
		 DMA.showMsg("Read-only file.."); 
	  }else{
		  if (StaticVar.savingFlag)
		  {
			DMA.showMsg("Save in progress");  
		  }else{
			Window.setTitle(StaticVar.resource_title);
			DriveUtil.setFileSaving();

			if (StaticVar.dma_file)
			  SaveDma.copyCodesFromTabs();
			else
			  DmaUtil.copyEditor(Integer.parseInt(StaticVar.codeTextArea.getElement().getId().substring(7)));
			DmaUtil.setOrigText();
			
			if (StaticVar.resource_id.equalsIgnoreCase("new_file") == true){
				createNewDriveFile();
			}else{
				 saveDriveFile();
			}
		  }
	  }
	}	
	
	public static void copyCodesFromTabs(){
	    for (Entry<RichTextArea, CustomTreeItem> entry : StaticVar.locateTextTab.entrySet()) {
	    	if (StaticVar.locate.get(entry.getValue()) != null){
			    if (entry.getKey().getHTML().length()>40)
			    	StaticVar.locate.get(entry.getValue()).nodeTextArea.setText(entry.getKey().getHTML().substring(0, 40)+"....");
			    else
			    	StaticVar.locate.get(entry.getValue()).nodeTextArea.setText(entry.getKey().getHTML());
			    StaticVar.locate.get(entry.getValue()).text = entry.getKey().getHTML();
	    	}
		}
	    
	    for (Entry<TextArea, CustomTreeItem> entry : StaticVar.locateUrlTab.entrySet()) {
	    	if (StaticVar.locate.get(entry.getValue()) != null){
			    if (entry.getKey().getText().length()>40)
			    	StaticVar.locate.get(entry.getValue()).nodeTextArea.setText(entry.getKey().getText().substring(0, 40)+"....");
			    else
			    	StaticVar.locate.get(entry.getValue()).nodeTextArea.setText(entry.getKey().getText());
			    StaticVar.locate.get(entry.getValue()).text = entry.getKey().getText();
	    	}
		}
		
		for (Map.Entry<TextArea, CustomTreeItem> entry : StaticVar.locateCodeTab.entrySet()) {
	    	if (StaticVar.locate.get(entry.getValue()) != null){
				DmaUtil.copyEditor(Integer.parseInt(entry.getKey().getElement().getId().substring(7)));
				if (entry.getKey().getText().length()>40)
			      StaticVar.locate.get(entry.getValue()).nodeTextArea.setText(entry.getKey().getText().substring(0,40)+"....");
				else
					StaticVar.locate.get(entry.getValue()).nodeTextArea.setText(entry.getKey().getText());
			    StaticVar.locate.get(entry.getValue()).text = entry.getKey().getText();
	    	}
		}
	}
	
	public static void saveDriveFile(){
	  try{
		JSFunctions.loadRealTimeForFile("update", StaticVar.resource_id);
	  }catch (Exception e){
		DMA.showMsg(e.getMessage());
	  }
	}
	
	public static void saveFileAfterAuth(){
		if (StaticVar.dma_file)
		  JSFunctions.updateFile(JSONUtil.buildJSON());
		else{
		  JSFunctions.updateFile(StaticVar.codeTextArea.getText());
		}
	}

	public static void saveComplete(){
		StaticVar.savingFlag = false;
	}

	public static void createNewDriveFile(){
		DMA.showMsgPopup("Creating new file..");
		//DMA.showMsg("Mem-tree requests Google drive auth if expired. Make sure browser didn't block the popup.");
		  try{
				JSFunctions.loadRealTimeForFile("insert", StaticVar.resource_id);
			  }catch (Exception e){
				  DMA.showMsg(e.getMessage());
			  }
	}
	
	public static void insertFileAfterAuth(){
		title = Window.prompt("Enter file name", "New File");
		if (title == null){
			DMA.hideMsgPopUp();
			saveComplete();
			return;
		}
		else
			title = title.concat(".dma");
		JSFunctions.insertFile(title, JSONUtil.buildJSON());
	}
	
	public static void insertComplete(final String fileId){
		DMA.hideMsgPopUp();
		DMA.showMsg("New file created..");
		Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand () {
	        public void execute () { 
           		DriveUtil.openDriveFile(fileId);           			
	        }
	    });
	}
}
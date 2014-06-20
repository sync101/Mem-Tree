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

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.TreeItem;

public class Timers {

	public static Node removeNode;
    public static Timer removeNodeTimer = new Timer() {
        public void run() {
        	if (StaticVar.locateMoveNodeMap.get(removeNode.ti) != null){
        	 StaticVar.locateMoveNodeMap.get(removeNode.ti).remove();
			 StaticVar.locateMoveNodeMap.remove(removeNode.ti);
        	}
			StaticVar.locate.remove(removeNode.ti);
			if (removeNode.ti.getParentItem().getChildCount() == 1)
				removeNode.ti.getParentItem().setState(false);
			removeNode.ti.remove();
			removeNodeTimer.cancel();
        }
    };
    
	  public static Timer resizeTimer = new Timer() {  
	  	    @Override
	  	    public void run() {
	  	      ResizeWindow.resizeDMA();
	  	    }
  	  };

	static int nodeBottom;
	
    // Create a new timer that updates the countdown every second.
    public static Timer timerScrPosition = new Timer() {
      public void run() {   		
   		StaticVar.treeScroller.setVertScrollPosition(StaticVar.dmaTree.getOffsetHeight(), StaticVar.treeScroller.getOffsetHeight(), nodeBottom);
   		timerScrPosition.cancel();
      }
    };

    public static void setScrollPosition(int x){
		nodeBottom = x;
		timerScrPosition.schedule(240);
	}
	
    public static Timer timerShowMsgPopupWithDelay = new Timer() {
        public void run() {   		
    		StaticVar.msgPopPanel.center();		
    		StaticVar.msgPopPanel.show();           			
        	timerShowMsgPopupWithDelay.cancel();
        }
      };

	public static Timer timerScrPositionMoveNode = new Timer() {
	      public void run() {   		
	   		StaticVar.moveNodeTreeScrPanel.setVertScrollPosition(StaticVar.moveNodeTree.getOffsetHeight(), StaticVar.moveNodeTreeScrPanel.getOffsetHeight(), nodeBottom);
	   		StaticVar.suggestBoxFocused = false;
	   		timerScrPositionMoveNode.cancel();
	      }
	};
	
	public static void setScrollPositionMoveNode(TreeItem ti){
		nodeBottom = ti.getElement().getOffsetTop();
		timerScrPositionMoveNode.schedule(240);
	}
	
    public static Timer timerScrPosition2 = new Timer() {
        public void run() {   		
     		setScrollPosition(StaticVar.searchList.get(0).getElement().getOffsetTop());
     		timerScrPosition2.cancel();
        }
      };

    public static void setScrollPosition2(){
    	timerScrPosition2.schedule(240);
	}
	
	static Node nodemaximize;
	public static Timer timerMaximize = new Timer() {
		public void run() {
			DmaUtil.setCurrNode(nodemaximize);
			Commands.maximizeCommand.execute();
			timerMaximize.cancel();
		}
	};
	
	public static void maximize(Node node){
		nodemaximize = node;
		timerMaximize.schedule(1600);
	}
	
	static Node nodePreview;
	public static Timer timerShowPreview = new Timer(){
		public void run() {
			StaticVar.previewHtml.setHTML(nodePreview.text.replace("<script", "lt; script").replace("createElement", "").replace("appendChild", "").replace("\n", "<br>"));
			StaticVar.previewPopup.showRelativeTo(nodePreview);
			timerShowPreview.cancel();
		}
	};

	public static void showPreview(Node node){
		nodePreview = node;
		timerShowPreview.schedule(1600);
	}
	
	public static void noPreview(){
		timerShowPreview.cancel();
	}
	
	public static Timer timerHidePreview = new Timer(){
		public void run() {
			timerShowPreview.cancel();
			timerHidePreview.cancel();
			StaticVar.previewPopup.hide();
		}
	};

	public static void hidePreview(){
		timerHidePreview.schedule(300);
	}
	

	public static Timer timerOpenEditor = new Timer() {
		public void run() {
			timerOpenEditor.cancel();
			DmaUtil.setCurrNode(nodemaximize);
	  		Commands.maximizeCommand.execute();
		}
	};

	public static void openEditor(Node node){
		nodemaximize = node;
		timerMaximize.schedule(1200);
	}
	
	static int codeTabId;
	static String editorMode;
	static String editorType;
	
	public static Timer timerCodeEditor = new Timer() {
		public void run() {
			DmaUtil.createCodeEditor(codeTabId, editorType, editorMode, StaticVar.codeEditorWidth, StaticVar.codeEditorHeight);
			timerCodeEditor.cancel();
		}
	};

	public static void createCodeEditor(int elementId, String editor_type, String editor_mode){
		codeTabId = elementId;
		editorType = editor_type;
		editorMode = editor_mode;
		timerCodeEditor.schedule(600);
	}
	
	public static int tabIndex = 0;
	public static Timer timerSelectTab = new Timer() {
		public void run() {
			StaticVar.tabPanel.selectTab(tabIndex);
			timerSelectTab.cancel();
		}
	};
	
	public static void delayedSelectTab(){
		if (DmaUtil.isTextNode(nodemaximize)==false)
			timerSelectTab.schedule(1400);
	}
	
  	//Timer for processing
    public static Timer timerProcessing = new Timer() {
        public void run() {
        	StaticVar.img_processing.setVisible(false);
        	timerProcessing.cancel();
        }
      };
      
    public static void showProcessing(int milli_seconds){
    	StaticVar.img_processing.setVisible(true);
    	timerProcessing.schedule(milli_seconds);
    }
    
    public static Node focusNode;
    public static Boolean showEditPopup;
	public static Timer timerFocusNode = new Timer() {
		public void run() {
			focusNode.setFocus();
			DmaUtil.setCurrNode(focusNode);
			if (showEditPopup && focusNode.nodeTextBox.getOriginalWidget().getText().equalsIgnoreCase("Key Word"))
				focusNode.nodeTextBox.getOriginalWidget().showPopUp();
			timerFocusNode.cancel();
		}
	};

	public static void delayedNodeFocus(Node node, Boolean showPopup) {		
		// TODO Auto-generated method stub
		focusNode = node;
		showEditPopup = showPopup;
		timerFocusNode.schedule(600);
	}
	
	public static TextBoxBase nodeEditTextBox;
	public static Timer timerFocusTextBox = new Timer() {
		public void run() {
			DmaUtil.setDelayedFocus(nodeEditTextBox);
    		if (nodeEditTextBox.getText().equalsIgnoreCase("Key Word"))
    			nodeEditTextBox.selectAll();
			timerFocusTextBox.cancel();
		}
	};

	public static void delayedTextBoxFocus(TextBoxBase tb) {		
		// TODO Auto-generated method stub
		nodeEditTextBox = tb;
		timerFocusTextBox.schedule(300);
	}
}

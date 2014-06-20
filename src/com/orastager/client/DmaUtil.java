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

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TreeItem;

public class DmaUtil {
	
	//Close Tab
	static void tabClose(Node node) {
		// TODO Auto-generated method stub
		if (StaticVar.textTabsArr.indexOf(node.nodeTextArea) >= 0)
			StaticVar.tabPanel.remove(StaticVar.textTabsArr.indexOf(node.nodeTextArea)+StaticVar.tabCount);
		//StaticVar.tabPanel.selectTab(0);
		StaticVar.textTabsArr.remove(node.nodeTextArea);
	}

	//Remove Node
	static void removeNode(Node node){
		Node focusNode; 
	    if (node.ti.getParentItem().getChildIndex(node.ti) == node.ti.getParentItem().getChildCount()-1){ //last node
	    	focusNode = StaticVar.locate.get(node.ti.getParentItem());
    		if (focusNode.isKeyWord())
         	  DmaUtil.setDelayedFocus(focusNode.nodeTextBox.getOriginalWidget());
      		else
      		  DmaUtil.setDelayedFocus(focusNode.nodeTextArea);
	    }else{
	    	focusNode = StaticVar.locate.get(node.ti.getParentItem().getChild(node.ti.getParentItem().getChildIndex(node.ti)+1));
	    	if (focusNode.type.equalsIgnoreCase("KeyWord"))
	          DmaUtil.setDelayedFocus(focusNode.nodeTextBox.getOriginalWidget());
	      	else
	      	  DmaUtil.setDelayedFocus(focusNode.nodeTextArea);
	    }

	        if (node.type.equalsIgnoreCase("KeyWord")){ 
				node.nodeTextBox.getOriginalWidget().hide(StaticVar.nodeTextBoxWidth);
			}	 
			else if (node.type.equalsIgnoreCase("ChildText")){
				node.nodeTextArea.hide(StaticVar.nodeTextAreaWidth);
				tabClose(node);
			}
	        DriveUtil.setFileModified();
		    Timers.removeNode = node;
		    Timers.removeNodeTimer.schedule(600);
	}


	static void assignCutNode(Node node){
    	StaticVar.cutNode = node;
    	StaticVar.cutNodeFlag = true;
		DMA.showMsg("Cut: " + StaticVar.cutNode.text);
    }

    static void pasteNode(Node node){
    	if (StaticVar.cutNode.type.equalsIgnoreCase("EmptyNode")){
    		DMA.showMsg("Cut a node first!");
    	}else {
    	   if (node.ti.equals(StaticVar.cutNode.ti) == false && DmaUtil.isChildNode(node.ti, true) == false){
    		   if (StaticVar.locateMoveNodeMap.get(node.ti) != null && StaticVar.locateMoveNodeMap.get(StaticVar.cutNode.ti) != null)
       		    StaticVar.locateMoveNodeMap.get(node.ti).addItem(StaticVar.locateMoveNodeMap.get(StaticVar.cutNode.ti));
       		    node.ti.addItem(StaticVar.cutNode.ti);
       		    node.ti.setState(true);
           		DMA.showMsg("Paste: " + StaticVar.cutNode.text);
           		DriveUtil.setFileModified();
           		StaticVar.cutNode = StaticVar.emptyCutNode;
    	   }else{
          		DMA.showMsg("Can't paste on the same node or child nodes!");
    	   }
    	}
    }
    
	static void showCommandsPopup(String type, int x, int y) {
		// TODO Auto-generated method stub
		Menus.popupPanel.clear();
		Menus.popupPanel.setWidget(Menus.vertMenuPanel);
		if (type.equalsIgnoreCase("KeyWord") && (y + 90) > Window.getClientHeight())
			Menus.popupPanel.setPopupPosition(x+10, Window.getClientHeight() - 90);
		else
			Menus.popupPanel.setPopupPosition(x+10, y-10);
		Menus.popupPanel.show();
	}
	
	static void showTabCloseCommandsPopup (int x, int y){
		Menus.popupPanel.clear();
		Menus.popupPanel.setWidget(Menus.tabCloseMenu);
		Menus.popupPanel.setPopupPosition(x+5, y-5);
		Menus.popupPanel.show();
	}
	
	static void setCurrNode(Node node){
		if (StaticVar.currNode != null){
			if (StaticVar.currNode.isKeyWord())
				StaticVar.currNode.nodeTextBox.removeStyleName("gwt-DmaTextBoxCurrent");
			else
				StaticVar.currNode.nodeTextArea.removeStyleName("gwt-DmaTextAreaCurrent");
		}
		StaticVar.currNode = node;
		if (StaticVar.currNode.isKeyWord())
			StaticVar.currNode.nodeTextBox.addStyleName("gwt-DmaTextBoxCurrent");
		else
			StaticVar.currNode.nodeTextArea.addStyleName("gwt-DmaTextAreaCurrent");
	}
	
	static void setDelayedFocus(final FocusWidget widget){
		Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand () {
	        public void execute () { 
           		widget.setFocus(true);           			
	        }
	    });
	}
	
	static void setDelayedFocus(final KeyWordLabel label){
		Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand () {
	        public void execute () { 
           		label.setFocus(true);           			
	        }
	    });
	}
	
	static void moveNode(Node parent, Node child){
		   DmaUtil.buildMoveNodeTree(StaticVar.root_ti, StaticVar.root_moveNode_ti);
	 	   if (parent.ti.equals(child.ti) == false && DmaUtil.isChildNode(parent.ti, false) == false){
			   parent.ti.addItem(child.ti);
			   parent.ti.setState(true);
			   StaticVar.moveNodeTreePopPanel.hide();
	    	   DMA.showMsg("Moved: " + child.text);
		       DriveUtil.setFileModified();
			   StaticVar.locateMoveNodeMap.get(parent.ti).addItem(StaticVar.locateMoveNodeMap.get(child.ti));
		   }else{
			   DMA.showMsg("Can't move to same node or child nodes!");
		   }
		}

	static void appendNode(TreeItem ti){
		if (StaticVar.locateMoveNodeMapRev.get(ti) != null){
	        if (StaticVar.selectedNodePairList.size() == 0){
	          moveNode(StaticVar.locate.get(StaticVar.locateMoveNodeMapRev.get(ti)), StaticVar.currNode);
	          SearchUtil.setState((CustomTreeItem) StaticVar.locateMoveNodeMapRev.get(ti), true);
	          Timers.setScrollPosition(((CustomTreeItem) StaticVar.locateMoveNodeMapRev.get(ti)).getElement().getOffsetTop());
		    }
		    else{
		    	try{
					for (NodePair nodePair: StaticVar.selectedNodePairList){
						DmaUtil.removeChildTextIcon(nodePair.nodeChildText);
						DmaUtil.addChildTextIcon(nodePair.nodeChildText);

						StaticVar.syncView.selectionModel.setSelected(nodePair, false);
						StaticVar.syncView.list.remove(nodePair);
						StaticVar.addNodePairPanel.hide();

						// only child text present
						if (nodePair.nodeKeyWord.text.equalsIgnoreCase("")){
							StaticVar.locateMoveNodeMapRev.get(ti).addItem(nodePair.nodeChildText.ti);
							SearchUtil.setState((CustomTreeItem) StaticVar.locateMoveNodeMapRev.get(ti), true);
				    		nodePair.nodeChildText.nodeTextArea.show(StaticVar.nodeTextAreaWidth);
				    		nodePair.nodeChildText.nodeTextArea.addStyleName("SearchMatchNode");
				    		Timers.setScrollPosition(nodePair.nodeChildText.ti.getElement().getOffsetTop());
						}else{
							StaticVar.oracle.add(nodePair.nodeKeyWord);
							StaticVar.locateMoveNodeMapRev.get(ti).addItem(nodePair.nodeKeyWord.ti);
							SearchUtil.setState((CustomTreeItem) StaticVar.locateMoveNodeMapRev.get(ti), true);
				    		nodePair.nodeKeyWord.ti.setState(true);
				    		nodePair.nodeKeyWord.nodeTextBox.getOriginalWidget().show(DmaUtil.getNodeTextBoxSize(nodePair.nodeKeyWord.text));
				    		nodePair.nodeKeyWord.nodeTextBox.addStyleName("SearchMatchNode");
				    		nodePair.nodeChildText.nodeTextArea.show(StaticVar.nodeTextAreaWidth);
				    		nodePair.nodeChildText.nodeTextArea.addStyleName("SearchMatchNode");
				    		Timers.setScrollPosition(nodePair.nodeKeyWord.ti.getElement().getOffsetTop());
						}
					}
					StaticVar.moveNodeTreePopPanel.hide();
					DriveUtil.setFileModified();
		    	}catch (Exception e){
		    		DMA.showMsg(e.getMessage());
		    	}
		    }
		}
	}

	static void buildMoveNodeTree(final CustomTreeItem root_ti, TreeItem root_moveNode_ti){
		for(int i=0; i<root_ti.getChildCount(); i++){
			if (StaticVar.locate.get(root_ti.getChild(i)).type.equalsIgnoreCase("KeyWord")){
			   if(StaticVar.locateMoveNodeMap.get(root_ti.getChild(i)) == null){
					final InlineLabel label = new InlineLabel(StaticVar.locate.get(root_ti.getChild(i)).text);
					final TreeItem ti = new TreeItem(label);
					root_moveNode_ti.addItem(ti);
					StaticVar.locateMoveNodeMap.put((CustomTreeItem) root_ti.getChild(i), ti);
					StaticVar.locateMoveNodeMapRev.put(ti, (CustomTreeItem) root_ti.getChild(i));
					label.addDoubleClickHandler(new DoubleClickHandler() {
						@Override
						public void onDoubleClick(DoubleClickEvent event) {
		                	DmaUtil.appendNode(StaticVar.moveNodeTree.getSelectedItem());
		                	StaticVar.moveNodeTreePopPanel.hide();
						}
					});
					label.addClickHandler(new ClickHandler() {
						@Override
						public void onClick(ClickEvent event) {
							// TODO Auto-generated method stub
							Timers.setScrollPositionMoveNode(ti);
						}
					});
					label.getElement().getStyle().setColor("white");
					buildMoveNodeTree((CustomTreeItem) root_ti.getChild(i), ti);
			   }
			   else{
				    ((Label) StaticVar.locateMoveNodeMap.get(root_ti.getChild(i)).getWidget()).setText(StaticVar.locate.get(root_ti.getChild(i)).text);
					buildMoveNodeTree((CustomTreeItem) root_ti.getChild(i), StaticVar.locateMoveNodeMap.get(root_ti.getChild(i)));
			   }
			}
		}
	}

    static void addChildTextIcon(final Node node){
		//CustomImage img_custom = new CustomImage(DmaUtil.getChildTextTypeImg(node.attr1, node.attr2, false));
    	CustomTag tag = new CustomTag(DmaUtil.getChildTextType(node.attr1,node.attr2), false);
		node.horPanel.add(tag);
		tag.setTitle("Click: Open");

		tag.label.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				DmaUtil.setCurrNode(node);
				Commands.maximizeCommand.execute();
			}
    	});
    }
    
    static void removeChildTextIcon(Node node){
    	node.horPanel.remove(1);
    }

    static native void printText(String html) /*-{
    	$wnd.printContent(html);
    }-*/;

    static boolean isChildNode(CustomTreeItem ti_paste, boolean cut){
    	if (StaticVar.locate.get(ti_paste).type.equalsIgnoreCase("Root") == true)
    		return false;
    	
    	if (StaticVar.locate.get(ti_paste.getParentItem()).type.equalsIgnoreCase("Root") == true)
    		return false;
    	
    	if (cut){
         	 if (ti_paste.getParentItem().equals(StaticVar.cutNode.ti) == true)
            		return true;
    	}
  	    else{
	      	 if (ti_paste.getParentItem().equals(StaticVar.currNode.ti) == true)
		       		return true;
	  	}
    	
    	return isChildNode((CustomTreeItem) ti_paste.getParentItem(), cut);
    }

    static SafeHtml makeImage(ImageResource resource){
    	return SafeHtmlUtils.fromTrustedString(AbstractImagePrototype.create(resource).getHTML());
    }

	public native static void  showNotification(String subject, String body) /*-{
		if (!window.webkitNotifications) {
	      return;
	    }
	
	    if (window.webkitNotifications.checkPermission() == 0) {
	        // note the show()
	        var popup = window.webkitNotifications.createNotification('', subject, body);
	        popup.show();
	        setTimeout(function(){popup.cancel();}, 3000);
	    }
    }-*/;	

	static void createCodeEditor(int elementId, String type, String mode, int width, int height) {
		appendEditorJS(elementId, type, mode, width, height, true);
	};
	
	static native void editorFind(String query, int elementId) /*-{
		$wnd.editorFind(query, elementId);
	}-*/;
	
	static native void editorFindNext(int elementId) /*-{
	    $wnd.editorFindNext(elementId);
	}-*/;
	
	static native void appendEditorJS(int elementId, String type, String mode, int width, int height, boolean create) /*-{
	  if (create)
		$wnd.createEditor(elementId, mode, width, height);
	  else
		$wnd.changeEditorMode(elementId, mode);
		//var script = $doc.createElement("script");
		//script.src = '/static/mode/'+type+'/'+type+'.js';
		//script.onload = function() {
		  //if (create)
			//$wnd.createEditor(elementId, mode, width, height);
		  //else
			//$wnd.changeEditorMode(elementId, mode);
		//};
		//$doc.getElementsByTagName("head")[0].appendChild(script);
	}-*/;

	static void setEditorMode(int elementId, String type, String mode) {
		appendEditorJS(elementId, type, mode, 0, 0, false);
    };
	
	static native void editorFindPrev(int elementId) /*-{
	    $wnd.editorFindPrev(elementId);
	}-*/;
	
	static native void setCodeEditorSize(int elementId, int width, int height) /*-{
	   $wnd.setEditorSize(elementId, width, height);
	}-*/;
	
	static native void copyEditor(int elementId) /*-{
		$wnd.copyEditor(elementId);
	}-*/;

	//is Number
	static boolean isNum(String s) {
		try {
			Double.parseDouble(s);
		}
		catch (NumberFormatException nfe) {
			return false;
		}

		return true;
	}	

	static void collapseAll(CustomTreeItem ti) {
		for(int i=0; i<ti.getChildCount(); i++){
			collapseAll((CustomTreeItem) ti.getChild(i));
		}
		ti.setState(false);
	}

	static void expandAll(CustomTreeItem ti) {
		for(int i=0; i<ti.getChildCount(); i++){
			expandAll((CustomTreeItem) ti.getChild(i));
		}
		ti.setState(true);
	}

    static void loadUrls(){
    	StaticVar.previewFrames.clear();
		for (Map.Entry<CustomTreeItem, Node> entry : StaticVar.locate.entrySet()) {
		  if (entry.getValue().attr1.equalsIgnoreCase(StaticVar.ChildTextTypes.type_text)==false && DmaUtil.isValidUrl(entry.getValue().text))	
			  StaticVar.previewFrames.add(entry.getValue());
		}
    }
    
    
    public static boolean requestFromMobile(){
    	String mobile = Window.Location.getParameter("m");
    	//String platform = Navigator.getUserAgent().toLowerCase();
    	if (mobile != null && mobile.length() > 0){
    		return true;
    	}
    	//else if(platform.contains("mobile"))
    	else if (JSFunctions.isMobile())
    		return true;
		else
			return false;
	}
	
	private static RegExp urlValidator;
    static boolean isValidUrl(String url) {
        if (urlValidator == null) {
            //urlValidator = RegExp.compile("^((ftp|http|https)://[\\w@.\\-\\_]+(:\\d{1,5})?(/[\\w#!:.?+=&%@!\\_\\-/]+)*){1}$");
        	urlValidator = RegExp.compile("(((ht|f)tp(s?))://){1}\\S+");
        	//urlValidator = RegExp.compile("@^(https?|ftp)://[^\\s/$.?#].[^\\s]*$@iS");
        	//urlValidator = RegExp.compile("(https?|ftp)://(-\\.)?([^\\s/?\\.#-]+\\.?)+(/[^\\s]*)?$@iS");
        }
        return (urlValidator.exec(url) != null);
    }
    
    
    
    public static ImageResource getChildTextTypeImg(String attr1, String attr2, Boolean big_small){
      if (big_small) {
    	  // big
    	  if (attr1.equalsIgnoreCase(StaticVar.ChildTextTypes.type_text)){
			  for (int i=0;i<StaticVar.childTextCodeTypeList.size();i++) 
	            if (attr2.equalsIgnoreCase(StaticVar.childTextCodeTypeList.get(i)))
	            	return StaticVar.childTextCodeTypeImagesBig.get(i);
		}			
		else {
			for (int i=0;i<StaticVar.childTextTextTypeList.size();i++)
				if (attr1.equalsIgnoreCase(StaticVar.childTextTextTypeList.get(i)))
					return StaticVar.childTextTextTypeImagesBig.get(i);
		}
		return StaticVar.childTextTextTypeImagesBig.get(1);
      }
      else{
 		if (attr1.equalsIgnoreCase(StaticVar.ChildTextTypes.type_text)){
			  for (int i=0;i<StaticVar.childTextCodeTypeList.size();i++) 
	            if (attr2.equalsIgnoreCase(StaticVar.childTextCodeTypeList.get(i)))
	            	return StaticVar.childTextCodeTypeImages.get(i);
		}			
		else {
			for (int i=0;i<StaticVar.childTextTextTypeList.size();i++)
				if (attr1.equalsIgnoreCase(StaticVar.childTextTextTypeList.get(i)))
					return StaticVar.childTextTextTypeImages.get(i);
		}
		return StaticVar.childTextTextTypeImages.get(1);
      }
    }
    
    public static String getChildTextType(String attr1, String attr2){
  	    if (attr1.equalsIgnoreCase(StaticVar.ChildTextTypes.type_text)){
		  for (int i=0;i<StaticVar.childTextCodeTypeList.size();i++) 
            if (attr2.equalsIgnoreCase(StaticVar.childTextCodeTypeList.get(i)))
            	return StaticVar.childTextCodeTypeList.get(i);
  		}			
  		else {
  			for (int i=0;i<StaticVar.childTextTextTypeList.size();i++)
  				if (attr1.equalsIgnoreCase(StaticVar.childTextTextTypeList.get(i)))
  					return StaticVar.childTextTextTypeList.get(i);
  		}
  		return StaticVar.childTextTextTypeList.get(1);
    }

	static boolean isCodeNode(Node node) {
		// TODO Auto-generated method stub
		if(node.attr1.equalsIgnoreCase(StaticVar.ChildTextTypes.type_text))
		  for (int i=0;i<StaticVar.childTextCodeTypeList.size();i++) 
            if (node.attr2.equalsIgnoreCase(StaticVar.childTextCodeTypeList.get(i)))
            	return true;
		return false;
	}
	
	static boolean isTextNode(Node node){
		if (node.attr1.equalsIgnoreCase(StaticVar.ChildTextTypes.type_notes) ||
				 node.attr1.equalsIgnoreCase(StaticVar.ChildTextTypes.type_text))
		 return true;
		return false;
	}
	
	static boolean isNotesNode(Node node){
		if (node.attr1.equalsIgnoreCase(StaticVar.ChildTextTypes.type_notes))
		 return true;
		return false;
	}

	static void setCodeNodeImg(Node node, String attr2) {
		// TODO Auto-generated method stub
		node.attr2 = attr2;
		node.horPanel.remove(1);
		addChildTextIcon(node);
	}

	static void setNodeTextBoxSize(KeyWordLabel label) {
		// TODO Auto-generated method stub		
		if (label.getText().length()>=StaticVar.nodeTextBoxMaxWidth)
			label.setWidth(StaticVar.nodeTextBoxMaxWidth*6+"px");
		else if (label.getText().length()>25 && label.getText().length()<StaticVar.nodeTextBoxMaxWidth)
			label.setWidth(label.getText().length()*6+"px");
		else
			label.setWidth(StaticVar.nodeTextBoxWidth+"px");
	}
	
	static int getNodeTextBoxSize(String text) {
		// TODO Auto-generated method stub
		if (text.length()>=StaticVar.nodeTextBoxMaxWidth)
			return StaticVar.nodeTextBoxMaxWidth*6;
		else if (text.length()>25 && text.length()<StaticVar.nodeTextBoxMaxWidth)
			return text.length()*6;
		else
			return StaticVar.nodeTextBoxWidth;
	}

	static void setOrigText() {
		// TODO Auto-generated method stub
		if (StaticVar.dma_file)
		  StaticVar.origText = JSONUtil.buildJSON();
		else
		  StaticVar.origText = StaticVar.codeTextArea.getText();
	}

	public static void showRightPane() {
		// TODO Auto-generated method stub
		if (RootPanel.get("RightPane").isVisible() == false){
			RootPanel.get("RightPane").setVisible(true);
			RootPanel.get("ResizePane").setVisible(true);
		}
	}
	
	public static void moveSelectionDown(TreeItem sel, boolean dig) {
	    if (sel == StaticVar.root_ti) {
	      return;
	    }

	    // Find a parent that is visible
	    TreeItem topClosedParent = getTopClosedParent(sel);
	    if (topClosedParent != null) {
	      moveSelectionDown(topClosedParent, false);
	      return;
	    }

	    TreeItem parent = sel.getParentItem();
	    if (parent == null) {
	      parent = StaticVar.root_ti;
	    }
	    int idx = parent.getChildIndex(sel);

	    if (!dig || !sel.getState()) {
	      if (idx < parent.getChildCount() - 1) {
	    	  StaticVar.locate.get(parent.getChild(idx + 1)).setFocus();
	      } else {
	        moveSelectionDown(parent, false);
	      }
	    } else if (sel.getChildCount() > 0) {
	    	StaticVar.locate.get(sel.getChild(0)).setFocus();
	    }
	  }
	
	public static void moveSelectionUp(TreeItem sel) {
	    // Find a parent that is visible
	    TreeItem topClosedParent = getTopClosedParent(sel);
	    if (topClosedParent != null) {
	      StaticVar.locate.get(topClosedParent).setFocus();
	      return;
	    }

	    TreeItem parent = sel.getParentItem();
	    if (parent == null) {
	      parent = StaticVar.root_ti;
	    }
	    int idx = parent.getChildIndex(sel);

	    if (idx > 0) {
	      TreeItem sibling = parent.getChild(idx - 1);
	      StaticVar.locate.get(findDeepestOpenChild(sibling)).setFocus();
	    } else {
	    	StaticVar.locate.get(parent).setFocus();
	    }
	  }
	
	public static TreeItem findDeepestOpenChild(TreeItem item) {
	    if (!item.getState() || item.getChildCount() == 0) {
	        return item;
	    }
	    return findDeepestOpenChild(item.getChild(item.getChildCount() - 1));
	}

	
	public static TreeItem getTopClosedParent(TreeItem item) {
	    TreeItem topClosedParent = null;
	    TreeItem parent = item.getParentItem();
	    while (parent != null) {
	      if (!parent.getState()) {
	        topClosedParent = parent;
	      }
	      parent = parent.getParentItem();
	    }
	    return topClosedParent;
	 }
    
	public static void maybeCollapseTreeItem(TreeItem ti) {
	    TreeItem topClosedParent = getTopClosedParent(ti);
	    if (topClosedParent != null) {
	      // Select the first visible parent if curSelection is hidden
	    	StaticVar.locate.get(topClosedParent).setFocus();
	    } else if (ti.getState()) {
	      ti.setState(false);
	    } else {
	      TreeItem parent = ti.getParentItem();
	      if (parent != null) {
	    	  StaticVar.locate.get(parent).setFocus();
	      }
	    }
	  }

	  public static void maybeExpandTreeItem(TreeItem ti) {
	    TreeItem topClosedParent = getTopClosedParent(ti);
	    if (topClosedParent != null) {
	      // Select the first visible parent if curSelection is hidden
	    	StaticVar.locate.get(topClosedParent).setFocus();
	    } else if (!ti.getState()) {
	      ti.setState(true);
	    } else if (ti.getChildCount() > 0) {
	    	StaticVar.locate.get(ti.getChild(0)).setFocus();
	    }
	  }
	  
	  public static void setNodePairType(String str){
	    for (int i=0;i<StaticVar.childTextTypeList.size();i++) 
	         if (str.equalsIgnoreCase(StaticVar.childTextTypeList.get(i))){
	          	StaticVar.tpNodePair.setSelectedIndex(i); 
	           	break;
	        }
	  }
}

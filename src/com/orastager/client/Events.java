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

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Event;

public class Events {

	static int x, y;
	
	public static void addRootKeyWordEvents(final Node node){
        // Mouse out (textBox)
    	node.nodeTextBox.getOriginalWidget().addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				node.nodeTextBox.removeStyleName("SearchMatchNode");
			}
		});
        
		// Single Click (textBox)
		node.nodeTextBox.getOriginalWidget().addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				DmaUtil.setCurrNode(node);
				DMA.showMsg("Right click for menu!");
			}
		});
		
		node.nodeTextBox.getOriginalWidget().addValueChangeHandler(new ValueChangeHandler<String>() {
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				// TODO Auto-generated method stub
	        	  DriveUtil.setFileModified();
			}
		});
		
        // Key Down
		node.nodeTextBox.getOriginalWidget().addKeyDownHandler(new KeyDownHandler(){
			@Override
			public void onKeyDown(KeyDownEvent event) {
				// TODO Auto-generated method stub
				DmaUtil.setCurrNode(node);
				if (event.isControlKeyDown() && event.getNativeKeyCode() == 75){
					event.preventDefault();
					event.stopPropagation();
					Commands.plusCommand.execute();
				}
				else if (event.isControlKeyDown() && event.getNativeKeyCode() == 86){
					event.preventDefault();
					event.stopPropagation();
					Commands.pasteNodeCommand.execute();
				}
				else if (event.isControlKeyDown() && event.getNativeKeyCode() == KeyCodes.KEY_ENTER){
					Menus.showMenuKeyWord(true);
					x = node.nodeTextBox.getAbsoluteLeft()+150;
					y = node.nodeTextBox.getAbsoluteTop();
					DmaUtil.showCommandsPopup("Root", x, y);
				}
				else if (event.getNativeKeyCode() == KeyCodes.KEY_RIGHT){
			        DmaUtil.maybeExpandTreeItem(node.ti);
				}
				else if (event.getNativeKeyCode() == KeyCodes.KEY_LEFT){
					DmaUtil.maybeCollapseTreeItem(node.ti);
				}
				else if (event.getNativeKeyCode() == KeyCodes.KEY_DOWN){
					//DomEvent.fireNativeEvent(Document.get().createKeyDownEvent(false, false, false, false, KeyCodes.KEY_TAB), StaticVar.locate.get(node.ti).nodeTextBox);
					if (node.ti == StaticVar.root_ti) {
					      if (StaticVar.root_ti.getState() && StaticVar.root_ti.getChildCount() > 0)
					    	  StaticVar.locate.get(StaticVar.root_ti.getChild(0)).setFocus();
					}else
					DmaUtil.moveSelectionDown(node.ti, true);
				}
				else if (event.getNativeKeyCode() == KeyCodes.KEY_UP){
					DmaUtil.moveSelectionUp(node.ti);
				}
			}
        });

		node.nodeTextBox.getOriginalWidget().addMouseDownHandler(new MouseDownHandler(){
			public void onMouseDown(MouseDownEvent event) {
				DmaUtil.setCurrNode(node);
				if (event.getNativeButton() == Event.BUTTON_RIGHT){
					StaticVar.auto_open = true;
					Menus.showMenuKeyWord(true);
					x = event.getClientX();
					y = event.getClientY();
					DmaUtil.showCommandsPopup("Root", x, y);
				}
			}
        });

		node.nodeTextBox.getOriginalWidget().addFocusHandler(new FocusHandler() {
			@Override
			public void onFocus(FocusEvent event) {
				// TODO Auto-generated method stub
				DmaUtil.setCurrNode(node);
				Timers.setScrollPosition(node.ti.getElement().getOffsetTop());
			}
		});
	}
	
    public static void addChildTextEvents(final Node node){
    	
    	node.nodeTextArea.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				Timers.showPreview(node);
			}
		});
    	
		// Mouse out (textArea)
    	node.nodeTextArea.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				Timers.hidePreview();
				node.nodeTextArea.removeStyleName("SearchMatchNode");
			}
		});
    	
    	// Key Down
    	node.nodeTextArea.addKeyDownHandler(new KeyDownHandler(){
			@Override
			public void onKeyDown(KeyDownEvent event) {
				// TODO Auto-generated method stub
				Timers.noPreview();
				DmaUtil.setCurrNode(node);
				if (event.getNativeKeyCode() == KeyCodes.KEY_DELETE || (event.isControlKeyDown() && event.getNativeKeyCode() == 68)){
					event.preventDefault();
					event.stopPropagation();
					Commands.removeCommand.execute();
				}
				else if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER){
					event.preventDefault();
					event.stopPropagation();
					Commands.maximizeCommand.execute();
				}
				else if (event.isControlKeyDown() && event.isUpArrow()){
					event.preventDefault();
					event.stopPropagation();
					Commands.moveupCommand.execute();
				}
				else if (event.isControlKeyDown() && event.isDownArrow()){
					event.preventDefault();
					event.stopPropagation();
					Commands.movedownCommand.execute();
				}
				else if (event.getNativeKeyCode() == KeyCodes.KEY_RIGHT){
			        DmaUtil.maybeExpandTreeItem(node.ti);
				}
				else if (event.getNativeKeyCode() == KeyCodes.KEY_LEFT){
					DmaUtil.maybeCollapseTreeItem(node.ti);
				}
				else if (event.getNativeKeyCode() == KeyCodes.KEY_DOWN){
					if (node.ti == StaticVar.root_ti) {
					      if (StaticVar.root_ti.getState() && StaticVar.root_ti.getChildCount() > 0)
					    	  StaticVar.locate.get(StaticVar.root_ti.getChild(0)).setFocus();
					}else
					DmaUtil.moveSelectionDown(node.ti, true);
				}
				else if (event.getNativeKeyCode() == KeyCodes.KEY_UP){
					DmaUtil.moveSelectionUp(node.ti);
				}
				
			}
    	});

		// Double Click (nodeTextArea)
    	node.nodeTextArea.addDoubleClickHandler(new DoubleClickHandler(){
			public void onDoubleClick(DoubleClickEvent event) {
				Timers.noPreview();
				DmaUtil.setCurrNode(node);
				Commands.maximizeCommand.execute();
			}
		});
    	
    	node.nodeTextArea.addMouseDownHandler(new MouseDownHandler(){
			public void onMouseDown(MouseDownEvent event) {
				Timers.noPreview();
				DmaUtil.setCurrNode(node);
				if (event.getNativeButton() == Event.BUTTON_RIGHT){
					Menus.showMenuChildText();
					DmaUtil.showCommandsPopup("ChildText", event.getClientX(), event.getClientY());
				}
			}
        });

    	node.nodeTextArea.addFocusHandler(new FocusHandler() {
			@Override
			public void onFocus(FocusEvent event) {
				// TODO Auto-generated method stub
				Timers.noPreview();
				DmaUtil.setCurrNode(node);
				Timers.setScrollPosition(node.ti.getElement().getOffsetTop());
			}
		});
    }

    public static void addKeyWordEvents(final Node node){
		// Single Click (textBox)
    	node.nodeTextBox.getOriginalWidget().addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				node.nodeTextBox.removeStyleName("SearchMatchNode");
				DmaUtil.setCurrNode(node);
				//if (node.text.equalsIgnoreCase("Key Word"))
					//node.nodeTextBox.getOriginalWidget().showPopUp();
					//node.nodeTextBox.getOriginalWidget().selectAll();
				Timers.setScrollPosition(node.ti.getElement().getOffsetTop());
			}
		});		

		// Double Click (textBox)
    	node.nodeTextBox.getOriginalWidget().addDoubleClickHandler(new DoubleClickHandler(){
			public void onDoubleClick(DoubleClickEvent event) {
				node.nodeTextBox.removeStyleName("SearchMatchNode");
				DmaUtil.setCurrNode(node);
				node.nodeTextBox.getOriginalWidget().showPopUp();
				
		    	/*if (node.ti.getChildCount()> 0 && node.ti.getState() == false){
		    	  node.ti.setState(true);
				  Timers.setScrollPosition(node.ti.getElement().getOffsetTop());
		    	}
				else if (node.ti.getChildCount()>0 && node.ti.getState())
					node.ti.setState(false);

		    	if (node.ti.getState() && node.ti.getChildCount() == 1 && StaticVar.locate.get(node.ti.getChild(0)).type.equalsIgnoreCase("ChildText") == true){
		    		Timers.maximize(StaticVar.locate.get(node.ti.getChild(0)));
    	    	}*/
			}

		});		
        
        // Key Down
    	node.nodeTextBox.getOriginalWidget().addKeyDownHandler(new KeyDownHandler(){
			@Override
			public void onKeyDown(KeyDownEvent event) {
				// TODO Auto-generated method stub
				DmaUtil.setCurrNode(node);
				if (event.isControlKeyDown() && event.getNativeKeyCode() == 75){
					event.preventDefault();
					event.stopPropagation();
					Commands.plusCommand.execute();
				}
				else if (event.isControlKeyDown() && event.getNativeKeyCode() == 77){
					event.preventDefault();
					event.stopPropagation();
					Commands.moveNodeCommand.execute();
				}
				else if (event.isControlKeyDown() && event.isUpArrow()){
					event.preventDefault();
					event.stopPropagation();
					Commands.moveupCommand.execute();
				}
				else if (event.isControlKeyDown() && event.isDownArrow()){
					event.preventDefault();
					event.stopPropagation();
					Commands.movedownCommand.execute();
				}
				else if (event.isControlKeyDown() && event.getNativeKeyCode() == 88){
					event.preventDefault();
					event.stopPropagation();
					Commands.cutNodeCommand.execute();
				}
				else if (event.isControlKeyDown() && event.getNativeKeyCode() == 86){
			    	if (StaticVar.cutNode.type.equalsIgnoreCase("EmptyNode")){
			    		DMA.showMsg("Cut a node first!");
			    	}else {
						event.preventDefault();
						event.stopPropagation();
						Commands.pasteNodeCommand.execute();
			    	}
			    }
				if (event.getNativeKeyCode() == KeyCodes.KEY_DELETE || (event.isControlKeyDown() && event.getNativeKeyCode() == 68)){
					event.preventDefault();
					event.stopPropagation();
					Commands.removeCommand.execute();
				}
				else if (event.isControlKeyDown() && event.getNativeKeyCode() == 13){
					StaticVar.auto_open = true;
					Menus.showMenuKeyWord(false);
					x = node.nodeTextBox.getAbsoluteLeft()+150;
					y = node.nodeTextBox.getAbsoluteTop();
					DmaUtil.showCommandsPopup("KeyWord", x, y);
				}
				else if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER){
					node.nodeTextBox.getOriginalWidget().showPopUp();
				}
				else if (event.getNativeKeyCode() == KeyCodes.KEY_RIGHT){
			        DmaUtil.maybeExpandTreeItem(node.ti);
				}
				else if (event.getNativeKeyCode() == KeyCodes.KEY_LEFT){
					DmaUtil.maybeCollapseTreeItem(node.ti);
				}
				else if (event.getNativeKeyCode() == KeyCodes.KEY_DOWN){
					if (node.ti == StaticVar.root_ti) {
					      if (StaticVar.root_ti.getState() && StaticVar.root_ti.getChildCount() > 0)
					    	  StaticVar.locate.get(StaticVar.root_ti.getChild(0)).setFocus();
					}else
					DmaUtil.moveSelectionDown(node.ti, true);
				}
				else if (event.getNativeKeyCode() == KeyCodes.KEY_UP){
					DmaUtil.moveSelectionUp(node.ti);
				}
			}
        });
    	
        // Mouse out (textBox)
    	node.nodeTextBox.getOriginalWidget().addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				node.nodeTextBox.removeStyleName("SearchMatchNode");
			}
		});
        
    	node.nodeTextBox.getOriginalWidget().addMouseDownHandler(new MouseDownHandler(){
			public void onMouseDown(MouseDownEvent event) {
				DmaUtil.setCurrNode(node);
				if (event.getNativeButton() == Event.BUTTON_RIGHT){
					StaticVar.auto_open = true;
					Menus.showMenuKeyWord(false);
					x = event.getClientX();
					y = event.getClientY();
					DmaUtil.showCommandsPopup("KeyWord", x, y);
					StaticVar.allowScroll = false;
				}
			}
        });
    	
    	node.nodeTextBox.getOriginalWidget().addFocusHandler(new FocusHandler() {
			@Override
			public void onFocus(FocusEvent event) {
				// TODO Auto-generated method stub
				DmaUtil.setCurrNode(node);
				
				//node.dragPanel.getElement().getStyle().setPaddingRight(0, Style.Unit.PX);
				if (StaticVar.allowScroll)
					Timers.setScrollPosition(node.ti.getElement().getOffsetTop());
				StaticVar.allowScroll = true;
			}
		});
    }
}

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

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.OptionElement;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.dom.client.Style.Unit;
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
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.ClosingEvent;
import com.google.gwt.user.client.Window.ClosingHandler;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class DMA implements EntryPoint{

	public static void showMsg(String msg){
		HumanizedMessagePopup.showMessageAndFade(msg, HumanizedMessagePopup.MESSAGE_TYPE.INFO);
	}
	
	public static void showMsgPopupWithDelay(String html){
		StaticVar.msgPopPanel.hide();
		StaticVar.msgHtml.setHTML(html);
		StaticVar.msgPopPanel.clear();
		StaticVar.msgPopPanel.setWidget(StaticVar.msgHtml);
		Timers.timerShowMsgPopupWithDelay.schedule(2000);
	}
	
	public static void showMsgPopup(String html){
		StaticVar.msgPopPanel.hide();
		StaticVar.msgHtml.setHTML(html);
		StaticVar.msgPopPanel.clear();
		StaticVar.msgPopPanel.setWidget(StaticVar.msgHtml);
		StaticVar.msgPopPanel.center();		
		StaticVar.msgPopPanel.show();           			
	}
	
	public static void hideMsgPopUp(){
		StaticVar.msgPopPanel.hide();
	}
	
    public void onModuleLoad() {
    	
    	DOM.setInnerHTML(RootPanel.get("Loading-Message").getElement(), "");
    	
	    JSFunctions.registerJSFunctions();
	    
	    StaticVar.msgPopPanel.setWidget(StaticVar.msgHtml);
	    StaticVar.msgHtml.getElement().getStyle().setPadding(10, Unit.PX);
	    StaticVar.msgHtml.getElement().getStyle().setBackgroundColor("white");
	    
	    
	    // Move Node Tree
		StaticVar.moveNodeTree.addItem(StaticVar.root_moveNode_ti);
		StaticVar.moveNodeTree.setAnimationEnabled(true);
		StaticVar.moveNodeTreeScrPanel.setPixelSize(StaticVar.moveNodeTreeScrWidth, StaticVar.moveNodeTreeScrHeight);
		StaticVar.moveNodeTreeScrPanel.setStyleName("moveNodeTreeScrPanel");
		
		StaticVar.root_moveNode_label.addDoubleClickHandler(new DoubleClickHandler() {
			@Override
			public void onDoubleClick(DoubleClickEvent event) {
				// TODO Auto-generated method stub
            	DmaUtil.appendNode(StaticVar.moveNodeTree.getSelectedItem());
            	StaticVar.moveNodeTreePopPanel.hide();
			}
		});

		StaticVar.root_moveNode_label.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				Timers.setScrollPositionMoveNode(StaticVar.root_moveNode_ti);
			}
		});

		Image cancelImg = new Image(StaticVar.menuResources.close());
		
		cancelImg.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				StaticVar.moveNodeTreePopPanel.hide();
			}
		});
		
		StaticVar.moveNodeTreePanel.add(cancelImg);
		StaticVar.moveNodeTreePanel.add(StaticVar.moveNodeTreeScrPanel);
		
		StaticVar.previewHtml.setPixelSize(700, 400);
		StaticVar.previewHtml.setStyleName("gwt-dma-edit-RichTextArea");
		StaticVar.previewHtml.getElement().getStyle().setOverflow(Overflow.AUTO);
		StaticVar.previewPopup.add(StaticVar.previewHtml);
		StaticVar.previewHtml.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				Timers.hidePreview();
			}
		});
		StaticVar.previewHtml.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				Timers.timerHidePreview.cancel();
			}
		});
		
		//suggestBox.setText("..");
		StaticVar.suggestBox.getTextBox().getElement().setId("speech-input-textbox");
		//StaticVar.suggestBox.getTextBox().getElement().setAttribute("type", "search");
		StaticVar.suggestBox.getTextBox().getElement().setAttribute("placeholder", "Search..");
		//suggestBox.getTextBox().getElement().setAttribute("spellcheck","true");
		//StaticVar.suggestBox.getTextBox().getElement().setAttribute("x-webkit-speech", "");
		//StaticVar.suggestBox.getTextBox().getElement().setAttribute("x-webkit-grammar","builtin:search");
		//StaticVar.suggestBox.getTextBox().getElement().setAttribute("lang","en");
		StaticVar.suggestBox.setTitle("Keyword search/suggestion box (Ctrl+F)");
		StaticVar.suggestBox.setLimit(15);
		StaticVar.suggestBox.getTextBox().addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				StaticVar.suggestBox.getTextBox().selectAll();
				JSFunctions.requestPermission();
			}
		});
		
		//checkBox for search
		StaticVar.searchchkBox.setTitle("Search content and drive along with keywords");

		//searchImg.setVisible(false);
		// Key Down (suggesthBox)
		StaticVar.suggestBox.getTextBox().addKeyDownHandler(new KeyDownHandler(){
			public void onKeyDown(KeyDownEvent event) {
				
			  if (StaticVar.suggestBox.getText().equalsIgnoreCase("") == false && event.getNativeKeyCode() == KeyCodes.KEY_ESCAPE){
				  StaticVar.suggestBox.hideSuggestionList();
			  }
			  
			  if (StaticVar.suggestBox.getText().equalsIgnoreCase("") == false && event.getNativeKeyCode() == KeyCodes.KEY_ENTER){
			    Timers.showProcessing(800);
				SearchUtil.searchString(StaticVar.suggestBox.getText(), true);
			  }
			  
			}
		});
		
		/*StaticVar.suggestBox.getTextBox().addKeyUpHandler(new KeyUpHandler(){
			@Override
			public void onKeyUp(KeyUpEvent event) {
				// TODO Auto-generated method stub
			  	  timerSmartSuggestion.cancel();
					if (event.isControlKeyDown() == false && event.getNativeKeyCode() >= 65 && event.getNativeKeyCode() <= 90){
						if (StaticVar.suggestBox.getText().contains(" "))
						  timerSmartSuggestion.schedule(600);
						else
					  	  timerSmartSuggestion.schedule(1200);
					}
			}
		});*/
			
		StaticVar.suggestBox.getTextBox().addFocusHandler(new FocusHandler() {
			
			@Override
			public void onFocus(FocusEvent event) {
				// TODO Auto-generated method stub
				if (StaticVar.moveNodeTreePopPanel.isShowing())
					StaticVar.suggestBoxFocused = true;
			}
		});
		
		//Select Suggestion
		StaticVar.suggestBox.addSelectionHandler(new SelectionHandler<SuggestOracle.Suggestion>(){
			@Override
			public void onSelection(SelectionEvent<Suggestion> event) {
					Timers.showProcessing(800);
					SearchUtil.searchString(StaticVar.suggestBox.getText(), false);
			}
		});
		
	    //json append Button
	    //StaticVar.jsontextArea.setVisible(false);
	    StaticVar.jsontextArea.setSize("30em", "30em");
	    StaticVar.jsonFlowPanel.add(StaticVar.jsontextArea);
	    StaticVar.jsonFlowPanel.add(StaticVar.replacejsonButton);
	    StaticVar.jsonPopupPanel.add(StaticVar.jsonFlowPanel);
	    StaticVar.jsonPopupPanel.getElement().getStyle().setBackgroundColor("#c6d5e4");
	    StaticVar.jsonPopupPanel.getElement().getStyle().setPadding(5, Unit.PX);
		
		// Root Tree Item 
	    StaticVar.root_node.ti = StaticVar.root_ti;		
		StaticVar.locate.put(StaticVar.root_ti, StaticVar.root_node);
		DmaUtil.setCurrNode(StaticVar.root_node);
		
		StaticVar.locateMoveNodeMap.put(StaticVar.root_ti, StaticVar.root_moveNode_ti);
		StaticVar.locateMoveNodeMapRev.put(StaticVar.root_moveNode_ti, StaticVar.root_ti);
		StaticVar.root_moveNode_label.getElement().getStyle().setColor("white");

		// GWT Tree
		StaticVar.dmaTree.addItem(StaticVar.root_ti);
		StaticVar.dmaTree.setAnimationEnabled(true);
		
		// xml replace button
		StaticVar.replacejsonButton.setTitle("Append json..");
		//StaticVar.replacejsonButton.setVisible(false);
		StaticVar.replacejsonButton.addClickHandler(new ClickHandler() {
	       public void onClick(ClickEvent event) {
	    	    StaticVar.jsonPopupPanel.hide();
	    	    DriveUtil.setFileModified();
	        	Timers.showProcessing(800);
	        	if (StaticVar.currNode != null)
		        	 JSONUtil.parseNodesJson(StaticVar.jsontextArea.getText(), StaticVar.currNode);
	        	else
		        	 JSONUtil.parseNodesJson(StaticVar.jsontextArea.getText(), StaticVar.root_node);
	        	//JSONUtil.parseTasksJson(StaticVar.jsontextArea.getText());
	        	StaticVar.root_ti.setState(true);
	        	//Commands.showTableViewCommand.execute();
	        	//Commands.showTaskViewCommand.execute();
	        	//Commands.chromeSyncCommand.execute();
	        	//Commands.featuresCommand.execute();
	        	//Commands.showSyncViewCommand.execute();
    		}
		});
		
		//collapseButton
		StaticVar.collapseButton.addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
	        	Timers.showProcessing(800);
	        	DmaUtil.collapseAll(StaticVar.root_ti);
	        }
	      });
		StaticVar.collapseButton.setTitle("Collapse All");
	
		//expandButton
		StaticVar.expandButton.addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
	        	Timers.showProcessing(800);
	        	DmaUtil.expandAll(StaticVar.root_ti);
	        }
	      });
		StaticVar.expandButton.setTitle("Expand All");
	    
	    
		//$(StaticVar.Templates.INSTANCE.outerHelper().asString()).appendTo($("body"));

	    StaticVar.searchAbsResultsPanel.getElement().getStyle().setBackgroundColor("white");
	    StaticVar.absSearchPanel.add(StaticVar.scrAbsResultsPanel);
    	StaticVar.searchResultsPanelHdr.add(new InlineHTML("<hr/>"));
    	StaticVar.searchResultsPanelHdr.add(new InlineHTML("<b>Results from Mem-Tree</b>"));
    	StaticVar.searchResultsPanelHdr.add(new InlineHTML("<hr/>"));
    	StaticVar.searchResultsPanel.add(StaticVar.driveResultsView.vp);
		StaticVar.searchResultsPanel.add(StaticVar.searchResultsPanelHdr);
		StaticVar.searchResultsPanelHdr.getElement().getStyle().setBackgroundColor("white");
		StaticVar.searchResultsPanel.add(StaticVar.searchAbsResultsPanel);
	    StaticVar.absSearchPanel.add(StaticVar.hp);
	  	StaticVar.hp.getElement().getStyle().setBackgroundColor("white");
	  	StaticVar.hp.getElement().setAttribute("border-radious", "6px");
	    StaticVar.hp.setVisible(false);
		StaticVar.scrAbsResultsPanel.setVisible(false);
	    
	    //StaticVar.absSearchPanel.add(StaticVar.sideVertPanel, 3, 50);
	    SideMenu.addItems();
	    SideMenu.sideMenuImage.setTitle("Add");
	    if (DmaUtil.requestFromMobile()){
		    SideMenu.sideMenuImage.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					// TODO Auto-generated method stub
					//SideMenu.sideMenuPanel.setPopupPosition(5, 125);
					SideMenu.sideMenuPanel.showRelativeTo(SideMenu.sideMenuImage);
				}
			});
	    }
	    SideMenu.sideMenuImage.addMouseOverHandler(new MouseOverHandler() {
			@Override
			public void onMouseOver(MouseOverEvent event) {
				// TODO Auto-generated method stub
				//SideMenu.sideMenuPanel.setPopupPosition(5, 125);
				SideMenu.sideMenuPanel.showRelativeTo(SideMenu.sideMenuImage);
			}
		});
	    
	    // Node pair logic
	    StaticVar.addNodePairImg.setTitle("Drag/click to add quickly");
	    StaticVar.addNodePairPanel.add(StaticVar.addNodePairVertPanel);
	    StaticVar.addNodePairPanel.getElement().getStyle().setBackgroundColor("#c6d5e4");
	    StaticVar.addNodePairPanel.getElement().getStyle().setPadding(5, Unit.PX);
	    StaticVar.kwTextBox.setPixelSize(400, 20);
	    StaticVar.kwTextBox.setStyleName("");
	    StaticVar.ctTextArea.setStyleName("");
	    StaticVar.kwTextBox.setTitle("Key Word");
	    StaticVar.kwTextBox.getElement().setAttribute("placeholder", "Key Word (optional)");
	    StaticVar.ctTextArea.setTitle("Child Text (url/bookmark or note)");
	    StaticVar.ctTextArea.getElement().setAttribute("placeholder", "Child Text (url/bookmark or note)");
	    StaticVar.tpNodePair.setTitle("Type");
	    StaticVar.ctTextArea.setPixelSize(400, 200);
	    StaticVar.addNodePairVertPanel.add(StaticVar.kwTextBox);
	    StaticVar.addNodePairVertPanel.add(new InlineHTML("<br>"));
	    StaticVar.addNodePairVertPanel.add(StaticVar.ctTextArea);

	    StaticVar.addNodePairVertPanel.add(StaticVar.tpNodePair);
	    for (String str: StaticVar.childTextTypeList){
	    	//StaticVar.tpNodePair.addItem(str);
	    	Element listElement = StaticVar.tpNodePair.getElement();
	    	OptionElement optionElement = Document.get().createOptionElement();
	    	//optionElement.setInnerHTML("&nbsp;&nbsp;&nbsp;"+str);
	    	optionElement.setInnerHTML(str);
	    	optionElement.setValue(str);
	    	listElement.appendChild(optionElement);
	    }

	    StaticVar.addNodePairVertPanel.add(StaticVar.addNodePairButton);
	    
	    StaticVar.addNodePairButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if (StaticVar.ctTextArea.getText().equalsIgnoreCase("")){
					showMsg("Child text is mandatory");
				}
				else{
					NodePair nodePair = new NodePair(StaticVar.kwTextBox.getText(), StaticVar.ctTextArea.getText());
					String type = StaticVar.tpNodePair.getValue(StaticVar.tpNodePair.getSelectedIndex());
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
					StaticVar.selectedNodePairList.clear();
					StaticVar.selectedNodePairList.add(nodePair);
					DmaUtil.buildMoveNodeTree(StaticVar.root_ti, StaticVar.root_moveNode_ti);
					if (StaticVar.currNode != null && (StaticVar.currNode.type.equalsIgnoreCase("KeyWord") || StaticVar.currNode.type.equalsIgnoreCase("Root"))){
						DmaUtil.appendNode(StaticVar.locateMoveNodeMap.get(StaticVar.currNode.ti));
					}else{
					  Commands.showMoveTreeCommand.execute();
					}
				}
			}
		});

	    StaticVar.tabPanel.addSelectionHandler(new SelectionHandler<Integer>(){
			@Override
			public void onSelection(SelectionEvent<Integer> event) {
				//if (event.getSelectedItem().intValue() == 0){
				//StaticVar.suggestBox.getTextBox().setFocus(true);
				//StaticVar.suggestBox.getTextBox().selectAll();
				//}
			}
	    });
	    
	    StaticVar.tabPanel.setAnimationDuration(500);
	    StaticVar.tabPanel.setAnimationVertical(true);
	    StaticVar.tabPanel.add(StaticVar.absSearchPanel,"SearchResults");
	    
	    StaticVar.tabPanel.selectTab(0);
	    
	    StaticVar.img_processing.setVisible(false);

	  	StaticVar.top_img.setTitle("Navigate to top");
	  	StaticVar.top_img.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				Timers.setScrollPosition(StaticVar.root_ti.getElement().getOffsetTop());
			}
	  	});
	  	StaticVar.top_img.setVisible(true);

	  	StaticVar.bottom_img.setTitle("Navigate to bottom");
	  	StaticVar.bottom_img.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				Timers.setScrollPosition(StaticVar.dmaTree.getOffsetHeight());
			}
	  	});
	  	StaticVar.bottom_img.setVisible(true);
	  	
	  	StaticVar.rootPanel.addDomHandler(new KeyDownHandler() {
				@Override
				public void onKeyDown(KeyDownEvent event) {
					
					// TODO Auto-generated method stub
					if (StaticVar.dma_file == false){
						if (event.isControlKeyDown() && event.getNativeKeyCode() == 83){
							event.preventDefault();
							SaveDma.saveDMA();
						}
					}else{
						if (event.getNativeKeyCode() != KeyCodes.KEY_ENTER)
							Timers.tabIndex = StaticVar.tabPanel.getSelectedIndex();

						if (event.isControlKeyDown() && event.getNativeKeyCode() == 70){
							event.preventDefault();
							StaticVar.suggestBox.getTextBox().setFocus(true);
							StaticVar.suggestBox.getTextBox().selectAll();
						}
						//else if (event.isControlKeyDown() && event.isAltKeyDown() && event.getNativeKeyCode() == 79){
							//event.preventDefault();
							//JSFunctions.showPicker();
						//}
						else if (event.isControlKeyDown() && event.getNativeKeyCode() == 83){
							event.preventDefault();
							SaveDma.saveDMA();
						}
						else if (event.isControlKeyDown() && event.isShiftKeyDown() && event.isRightArrow()){
							if(StaticVar.tabPanel.getSelectedIndex()>=0){
							  if(StaticVar.tabPanel.getSelectedIndex()+1<StaticVar.tabPanel.getWidgetCount()){	
								event.preventDefault();
								StaticVar.tabPanel.selectTab(StaticVar.tabPanel.getSelectedIndex()+1);
							  }
							  else if(StaticVar.tabPanel.getSelectedIndex()+1>=StaticVar.tabPanel.getWidgetCount()){
								event.preventDefault();
								StaticVar.tabPanel.selectTab(0);
							  }
							}
						}
						else if (event.isControlKeyDown() && event.isShiftKeyDown() && event.isLeftArrow()){
							if(StaticVar.tabPanel.getSelectedIndex()>=0){
							  if(StaticVar.tabPanel.getSelectedIndex()>0){	
								event.preventDefault();
								StaticVar.tabPanel.selectTab(StaticVar.tabPanel.getSelectedIndex()-1);
							  }
							  else if(StaticVar.tabPanel.getSelectedIndex()==0){
								event.preventDefault();
								StaticVar.tabPanel.selectTab(StaticVar.tabPanel.getWidgetCount()-1);
							  }
							}
						}
					}
				}
			   }, KeyDownEvent.getType());
	  	
	  	TopMenu.createMenu();
	    
	    loadRootPanel();

	    // Menu Creation
	    Menus.setMenuTitles();

	    JSFunctions.appendJS();
	    
	    loadTreeFromFileId();
	    DmaUtil.setOrigText();
	    //DriveUtil.loadDriveFileContent("/*test*/","test.sql","text/x-sql");
	    
	}// End onModuleLoad
    
    public static void loadRootPanel(){
		StaticVar.rootPanel.clear();
		
	    if (DmaUtil.requestFromMobile()){
		    RootPanel.get("LeftPane").add(StaticVar.menuBar);
			RootPanel.get("TopPane").add(StaticVar.searchchkBox, 10, 11);
			RootPanel.get("TopPane").add(StaticVar.suggestBox, 30, 8);
			RootPanel.get("TopPane").add(StaticVar.img_processing, 355, 8);
	    }else {
		    RootPanel.get("TopPane").add(StaticVar.menuBar);
			RootPanel.get("TopPane").add(StaticVar.searchchkBox, 40, 11);
			RootPanel.get("TopPane").add(StaticVar.suggestBox, 60, 8);
			RootPanel.get("TopPane").add(StaticVar.img_processing, 385, 8);
	    }
	    
	    RootPanel.get("LeftPane").add(StaticVar.collapseButton, 5, 0);
	    RootPanel.get("LeftPane").add(StaticVar.expandButton, 25, 0);
	    RootPanel.get("LeftPane").add(StaticVar.horCollaboratorsPanel, 50, 5);
	    RootPanel.get("LeftPane").add(SideMenu.sideMenuImage, 5, 50);
	    RootPanel.get("LeftPane").add(StaticVar.top_img);
	    RootPanel.get("LeftPane").add(StaticVar.bottom_img);
	    //RootPanel.get("LeftPane").add(StaticVar.hideImg);
	    RootPanel.get("LeftPane").add(StaticVar.treeScroller, 50, 34);
	    
	    if (DmaUtil.requestFromMobile()){
	    	RootPanel.get("ResizePane").setVisible(false);
	    }
	    else{
		    RootPanel.get("ResizePane").getElement().setDraggable(Element.DRAGGABLE_TRUE);
		    RootPanel.get("ResizePane").addDomHandler(new MouseDownHandler() {
				@Override
				public void onMouseDown(MouseDownEvent event) {
					// TODO Auto-generated method stub
					StaticVar.moveResizePane = true;
					DOM.setCapture(RootPanel.get("ResizePane").getElement());
				}
			}, MouseDownEvent.getType());
		    
		    RootPanel.get("ResizePane").addDomHandler(new MouseMoveHandler() {
				
				@Override
				public void onMouseMove(MouseMoveEvent event) {
					// TODO Auto-generated method stub
					if (StaticVar.moveResizePane && event.getClientX() > 0 && event.getClientX() < Window.getClientWidth() * .7){
						RootPanel.get("ResizePane").getElement().getStyle().setLeft(event.getClientX(), Unit.PX);
						RootPanel.get("LeftPane").getElement().getStyle().setWidth(event.getClientX(), Unit.PX);
						RootPanel.get("RightPane").getElement().getStyle().setWidth(Window.getClientWidth()-RootPanel.get("LeftPane").getElement().getOffsetWidth(), Unit.PX);
						RootPanel.get("RightPane").getElement().getStyle().setLeft(RootPanel.get("LeftPane").getElement().getOffsetWidth(), Unit.PX);
						ResizeWindow.resizeDMA();
					}
				}
			}, MouseMoveEvent.getType());
		    
	        RootPanel.get("ResizePane").addDomHandler(new MouseUpHandler() {
				
				@Override
				public void onMouseUp(MouseUpEvent event) {
					// TODO Auto-generated method stub
					DOM.releaseCapture(RootPanel.get("ResizePane").getElement());
					StaticVar.moveResizePane = false;
				}
			}, MouseUpEvent.getType());
	    }

	    RootPanel.get("RightPane").add(StaticVar.tabPanel, 3, 0);
	    //RootPanel.get("RightPane").add(StaticVar.showImg);

	    ResizeWindow.resizeDMA();

	    Window.addResizeHandler(new ResizeHandler() {
    	  @Override
    	  public void onResize(ResizeEvent event) {
    		  if (StaticVar.dma_file){
    	    	  Timers.resizeTimer.cancel();
    	    	  Timers.resizeTimer.schedule(150);
    		  }else
    		  {
    			  DmaUtil.setCodeEditorSize(1, Window.getClientWidth()-30, Window.getClientHeight()-70);
    		  }
    	  }
         });
	    
	    Window.addWindowClosingHandler(new ClosingHandler() {
			@Override
			public void onWindowClosing(ClosingEvent event) {
				// TODO Auto-generated method stub
				if (StaticVar.dma_file){
				  SaveDma.copyCodesFromTabs();
				  if (StaticVar.origText.equalsIgnoreCase(JSONUtil.buildJSON()) == false)
					event.setMessage("Unsaved changes");
				}
				else {
				  DmaUtil.copyEditor(Integer.parseInt(StaticVar.codeTextArea.getElement().getId().substring(7)));
				  if(StaticVar.origText.equalsIgnoreCase(StaticVar.codeTextArea.getText()) == false)
					event.setMessage("Unsaved changes");
				}
				//if (StaticVar.resource_modified)
					//event.setMessage("Unsaved changes.");
			}
		});
	    
    }
	
	public static String getAuthUrl(){
		String value = Window.Location.getParameter("fileId");
		if (value != null){
			return StaticVar.APP_BASE_URL+"/auth?state=%7B%22ids%22:%5B%22"+value+"%22%5D,%22action%22:%22open%22%7D";
		}else
			return StaticVar.APP_BASE_URL+"/auth";
	}
	
	public static void loadTreeFromFileId() {
		
		String fileId = Window.Location.getParameter("fileId");
		String edit = Window.Location.getParameter("edit");
	     if (fileId != null){
	    	 //Demo file
	    	 if (fileId.equalsIgnoreCase("0BwVzf8LNyklCQWUwMHFqVU1RbXM") && edit == null){
	    		 DMA.showMsgPopup("Loading file..");
	    		 StaticVar.resource_id = "0BwVzf8LNyklCQWUwMHFqVU1RbXM";
	    		 JSFunctions.downloadFileWithUrl(StaticVar.APP_BASE_URL+"/demo/Java.dma", "Demo.dma");
	    		 StaticVar.readonly_file = true;
	    		 return;
	    	 }
	    	 
	    	 if (fileId.equalsIgnoreCase("0BwVzf8LNyklCUGZYQXAyblJydmM") && edit == null){
	    		 DMA.showMsgPopup("Loading file..");
	    		 StaticVar.resource_id = "0BwVzf8LNyklCUGZYQXAyblJydmM";
	    		 JSFunctions.downloadFileWithUrl(StaticVar.APP_BASE_URL+"/demo/Mem-Tree.dma", "Mem-Tree.dma");
	    		 StaticVar.readonly_file = true;
	    		 return;
	    	 }

	    	 if (fileId.equalsIgnoreCase("new_dma_file")){
		    		 //Commands.featuresCommand.execute();
		    		 try{
		 				//JSFunctions.loadRealTimeForFile("newuser", StaticVar.resource_id);
		    			 return;
		 			  }catch (Exception e){
		 				DMA.showMsg(e.getMessage());
		 			  }
	    		 return;
	    	 }
	    	 
	    	 if (fileId.length() > 0){
		  	       DriveUtil.loadDriveFile(fileId);
			       return;
	    	 }
	    	 
    		 try{
	 				//JSFunctions.loadRealTimeForFile("newuser", StaticVar.resource_id);
    			  return;
	 			  }catch (Exception e){
	 				DMA.showMsg(e.getMessage());
	 		  }
	     } 
	}
	

	public static void loadText(String text) {	  
		// TODO Auto-generated method stub
	 if (StaticVar.root_ti.getChildCount() > 0)
 	   StaticVar.root_ti.removeItems();
	 StaticVar.oracle.clear();
	 StaticVar.locate.clear();
	 StaticVar.locate.put(StaticVar.root_ti, StaticVar.root_node);
	 StaticVar.textTabsArr.clear();
	 for (int i=StaticVar.tabCount;i<StaticVar.tabPanel.getWidgetCount();i++)
		 StaticVar.tabPanel.remove(StaticVar.tabCount);
	 StaticVar.searchAbsResultsPanel.clear();

	 StaticVar.taskView.list.clear();
	 
	 JSONUtil.loadJson(text);

	 StaticVar.root_ti.setState(true);
    }

	public static void addSyncData(String syncData){
		StaticVar.syncView.loadSyncData(syncData);
	}
	
	
}
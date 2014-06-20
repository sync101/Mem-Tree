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

import java.util.Date;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;

public class Commands {

	//Menu code
    public static Command plusCommand = new Command() {
    	public void execute() {
    		final Node child_node = new Node("Key Word","Key Word", StaticVar.dateFormatter.format(new Date()), "", "", "", "", "", "", "", "", true);
    		CustomTreeItem ti_child = new CustomTreeItem(child_node);
    		StaticVar.currNode.ti.addItem(ti_child);
    		//ti.insertItem(0, ti_child);
    		child_node.ti = ti_child;
    		StaticVar.locate.put(ti_child, child_node);
    		StaticVar.currNode.ti.setState(true);
    		Menus.popupPanel.hide();
        	Timers.setScrollPosition(ti_child.getElement().getOffsetTop());
    		DriveUtil.setFileModified();
    		child_node.nodeTextBox.getOriginalWidget().show(DmaUtil.getNodeTextBoxSize(child_node.text));
        	//DmaUtil.setDelayedFocus(child_node.nodeTextBox);
    		Timers.delayedNodeFocus(child_node, true);
        }
    };
    
    public static Command addPlainText = new Command(){
    	public void execute() {
    		AddNodes.addNodeChild(StaticVar.currNode, "Child Text", "Child Text", StaticVar.dateFormatter.format(new Date()), StaticVar.ChildTextTypes.type_text, "");
        }
    };
    
    public static Command addNotes = new Command(){
    	public void execute() {
    		AddNodes.addNodeChild(StaticVar.currNode, "Child Text", "Notes", StaticVar.dateFormatter.format(new Date()), StaticVar.ChildTextTypes.type_notes, "");
        }
    }; 
    
    public static Command addURL = new Command(){
    	public void execute(){
    		DmaUtil.setNodePairType(StaticVar.ChildTextTypes.type_url);
    		addNodePair.execute();
    		/*String url = Window.prompt("Enter url..", "");
    		if (url != null && DmaUtil.isValidUrl(url))
    		 AddNodes.addNodeChild(StaticVar.currNode, "Child Text", url, StaticVar.dateFormatter.format(new Date()), StaticVar.ChildTextTypes.type_url, "");
    		else if (url != null)
    		 DMA.showMsg("Not a valid Url. Format: http(s)://google.com/*");*/
    	}
    };


    public static Command addPDF = new Command(){
    	public void execute(){
    		DmaUtil.setNodePairType(StaticVar.ChildTextTypes.type_pdf);
    		addNodePair.execute();
    		/*String url = Window.prompt("Enter url of the pdf..", "");
    		if (url != null && DmaUtil.isValidUrl(url))
    		 AddNodes.addNodeChild(StaticVar.currNode, "Child Text", url, StaticVar.dateFormatter.format(new Date()), StaticVar.ChildTextTypes.type_pdf, "");
    		else if (url != null)
    		 DMA.showMsg("Not a valid Url. Format: http(s)://google.com/*");*/
    	}
    };
    
    public static Command addWord = new Command(){
    	public void execute(){
    		DmaUtil.setNodePairType(StaticVar.ChildTextTypes.type_word);
    		addNodePair.execute();
    		/*String url = Window.prompt("Enter url of the word document..", "");
    		if (url != null && DmaUtil.isValidUrl(url))
    		 AddNodes.addNodeChild(StaticVar.currNode, "Child Text", url, StaticVar.dateFormatter.format(new Date()), StaticVar.ChildTextTypes.type_word, "");
    		else if (url != null)
    		 DMA.showMsg("Not a valid Url. Format: http(s)://google.com/*");*/
    	}
    };    

    public static Command addExcel = new Command(){
    	public void execute(){
    		DmaUtil.setNodePairType(StaticVar.ChildTextTypes.type_excel);
    		addNodePair.execute();
    		/*String url = Window.prompt("Enter url of the excel document..", "");
    		if (url != null && DmaUtil.isValidUrl(url))
    		 AddNodes.addNodeChild(StaticVar.currNode, "Child Text", url, StaticVar.dateFormatter.format(new Date()), StaticVar.ChildTextTypes.type_excel, "");
    		else if (url != null)
    		 DMA.showMsg("Not a valid Url. Format: http(s)://google.com/*");*/
    	}
    };    

    public static Command addMusic = new Command(){
    	public void execute(){
    		DmaUtil.setNodePairType(StaticVar.ChildTextTypes.type_music);
    		addNodePair.execute();
    		/*String url = Window.prompt("Enter url of the album/song..", "");
    		if (url != null && DmaUtil.isValidUrl(url))
    		 AddNodes.addNodeChild(StaticVar.currNode, "Child Text", url, StaticVar.dateFormatter.format(new Date()), StaticVar.ChildTextTypes.type_music, "");
    		else if (url != null)
    		 DMA.showMsg("Not a valid Url. Format: http(s)://google.com/*");*/
    	}
    };    
    
    public static Command addYTVideo = new Command(){
    	public void execute(){
    		DmaUtil.setNodePairType(StaticVar.ChildTextTypes.type_video);
    		addNodePair.execute();
    		/*String url = Window.prompt("Enter url of the youtube video..", "");
    		if (url != null && DmaUtil.isValidUrl(url))
    		 AddNodes.addNodeChild(StaticVar.currNode, "Child Text", url, StaticVar.dateFormatter.format(new Date()), StaticVar.ChildTextTypes.type_video, "");
    		else if (url != null)
    		 DMA.showMsg("Not a valid Url. Format: http(s)://google.com/*");*/
    	}
    };

    public static Command addPlayList = new Command(){
    	public void execute(){
    		DmaUtil.setNodePairType(StaticVar.ChildTextTypes.type_video_pl);
    		addNodePair.execute();
    		/*String url = Window.prompt("Enter url of the playlist..", "");
    		if (url != null && DmaUtil.isValidUrl(url))
    		 AddNodes.addNodeChild(StaticVar.currNode, "Child Text", url, StaticVar.dateFormatter.format(new Date()), StaticVar.ChildTextTypes.type_video_pl, "");
    		else if (url != null)
    		 DMA.showMsg("Not a valid Url. Format: http(s)://google.com/*");*/
    	}
    };
    
    public static Command addImage = new Command(){
		public void execute() {
			DmaUtil.setNodePairType(StaticVar.ChildTextTypes.type_img);
    		addNodePair.execute();
    		/*String url = Window.prompt("Enter url of the image..", "");
    		if (url != null && DmaUtil.isValidUrl(url))
    		 AddNodes.addNodeChild(StaticVar.currNode, "Child Text", url, StaticVar.dateFormatter.format(new Date()), StaticVar.ChildTextTypes.type_img, "");
    		else if (url != null)
    		 DMA.showMsg("Not a valid Url. Format: http(s)://google.com/*");*/
		}
    };

	public static Command addC = new Command(){
    	public void execute() {
    		AddNodes.addNodeChild(StaticVar.currNode, "Child Text", "C", StaticVar.dateFormatter.format(new Date()), StaticVar.ChildTextTypes.type_text, StaticVar.ChildTextTypes.type_c);
        }
    };

	public static Command addCPP = new Command(){
    	public void execute() {
    		AddNodes.addNodeChild(StaticVar.currNode, "Child Text", "CPP", StaticVar.dateFormatter.format(new Date()), StaticVar.ChildTextTypes.type_text, StaticVar.ChildTextTypes.type_cpp);
        }
    };

	public static Command addCSS = new Command(){
    	public void execute() {
    		AddNodes.addNodeChild(StaticVar.currNode, "Child Text", "CSS", StaticVar.dateFormatter.format(new Date()), StaticVar.ChildTextTypes.type_text, StaticVar.ChildTextTypes.type_css);
        }
    };

	public static Command addHTML = new Command(){
    	public void execute() {
    		AddNodes.addNodeChild(StaticVar.currNode, "Child Text", "HTML", StaticVar.dateFormatter.format(new Date()), StaticVar.ChildTextTypes.type_text, StaticVar.ChildTextTypes.type_html);
        }
    };

	public static Command addJAVA = new Command(){
    	public void execute() {
    		AddNodes.addNodeChild(StaticVar.currNode, "Child Text", "JAVA", StaticVar.dateFormatter.format(new Date()), StaticVar.ChildTextTypes.type_text, StaticVar.ChildTextTypes.type_java);
        }
    };

	public static Command addJAVASCRIPT = new Command(){
    	public void execute() {
    		AddNodes.addNodeChild(StaticVar.currNode, "Child Text", "Java Script", StaticVar.dateFormatter.format(new Date()), StaticVar.ChildTextTypes.type_text, StaticVar.ChildTextTypes.type_javascript);
        }
    };

	public static Command addMYSQL = new Command(){
    	public void execute() {
    		AddNodes.addNodeChild(StaticVar.currNode, "Child Text", "MySQL", StaticVar.dateFormatter.format(new Date()), StaticVar.ChildTextTypes.type_text, StaticVar.ChildTextTypes.type_mysql);
        }
    };

	public static Command addPHP = new Command(){
    	public void execute() {
    		AddNodes.addNodeChild(StaticVar.currNode, "Child Text", "PHP", StaticVar.dateFormatter.format(new Date()), StaticVar.ChildTextTypes.type_text, StaticVar.ChildTextTypes.type_php);
        }
    };
    
    public static Command addPython = new Command(){
    	public void execute() {
    		AddNodes.addNodeChild(StaticVar.currNode, "Child Text", "PYTHON", StaticVar.dateFormatter.format(new Date()), StaticVar.ChildTextTypes.type_text, StaticVar.ChildTextTypes.type_python);
        }
    };

	public static Command addSHELLSCRIPT = new Command(){
    	public void execute() {
    		AddNodes.addNodeChild(StaticVar.currNode, "Child Text", "Shell Script", StaticVar.dateFormatter.format(new Date()), StaticVar.ChildTextTypes.type_text, StaticVar.ChildTextTypes.type_shellScript);
        }
    };

    public static Command addSQL = new Command(){
    	public void execute() {
    		AddNodes.addNodeChild(StaticVar.currNode, "Child Text", "SQL", StaticVar.dateFormatter.format(new Date()), StaticVar.ChildTextTypes.type_text, StaticVar.ChildTextTypes.type_sql);
        }
    };

	public static Command addXML = new Command(){
    	public void execute() {
    		AddNodes.addNodeChild(StaticVar.currNode, "Child Text", "XML", StaticVar.dateFormatter.format(new Date()), StaticVar.ChildTextTypes.type_text, StaticVar.ChildTextTypes.type_xml);
        }
    };
    
    public static Command addR = new Command(){
    	public void execute() {
    		AddNodes.addNodeChild(StaticVar.currNode, "Child Text", "R language", StaticVar.dateFormatter.format(new Date()), StaticVar.ChildTextTypes.type_text, StaticVar.ChildTextTypes.type_r);
        }
    };
    
    public static Command addNodePair = new Command(){
    	public void execute() {
    		Menus.popupPanel.hide();
    		StaticVar.addNodePairPanel.center();
    		StaticVar.addNodePairPanel.show();
    		DmaUtil.setDelayedFocus(StaticVar.kwTextBox);
        }
    };
    
    public static Command jsonCommand = new Command() {
    	public void execute() {
    		Timers.showProcessing(800);
    		Menus.popupPanel.hide();
    		StaticVar.jsontextArea.setText(JSONUtil.buildTreeJson(StaticVar.currNode).toString());
    		appendJsonCommand.execute();
        }
    };
    
    public static Command appendJsonCommand = new Command() {
    	public void execute() {
    		Timers.showProcessing(800);
    		StaticVar.jsonPopupPanel.center();
    		StaticVar.jsonPopupPanel.show();
    		Timers.delayedTextBoxFocus(StaticVar.jsontextArea);
    	}
    };

    public static Command shareCommand = new Command(){
    	public void execute() {
    	   Menus.popupPanel.hide();
    	   if (StaticVar.resource_id.equalsIgnoreCase("new_file")==false)	
    		   JSFunctions.shareFile(StaticVar.resource_id);
    	   else DMA.showMsg("No file to share..");
    	}
    };
    
    public static Command exportHtmlCommand = new Command(){
    	public void execute() {
    	    exportHtml(StaticVar.locate.get((CustomTreeItem) StaticVar.currNode.ti.getParentItem()).text+".html", StaticVar.currNode.text.replace("\n", "<br>"));
    		Menus.popupPanel.hide();
    	}
    };
    
    public static native void exportHtml(String title, String html)/*-{
	  var blob = new Blob([html], {type: 'text/html'});
	  //var url = window.URL.createObjectURL(blob);
	  //window.open(window.URL.createObjectURL(blob),'_blank');
	  $wnd.saveAs(blob, title);
    }-*/;

    public static Command closeAllTabs = new Command() {
    	public void execute() {
    		SaveDma.copyCodesFromTabs();
    		StaticVar.locateCodeTab.clear();
    		StaticVar.locateTextTab.clear();
    		StaticVar.locateUrlTab.clear();
    		StaticVar.textTabsArr.clear();
    		while(StaticVar.tabPanel.getWidgetCount() > StaticVar.tabCount){
    			StaticVar.tabPanel.remove(StaticVar.tabCount);
    		}
    		Menus.popupPanel.hide();
    	}
    };
    
    public static Command maximizeCommand = new Command() {
    	public void execute() {
    		Menus.popupPanel.hide();
    		Timers.showProcessing(800);
    		
    		if (DmaUtil.isTextNode(StaticVar.currNode) && DmaUtil.requestFromMobile())
    			Window.scrollTo(0, RootPanel.get("RightPane").getAbsoluteTop());
    		
    		  if (DmaUtil.isTextNode(StaticVar.currNode)==false){
				  //try{
					  //StaticVar.currNode.attr4 = ""+(Integer.parseInt(StaticVar.currNode.attr4)+1);
				  //}catch (Exception e){
					  //StaticVar.currNode.attr4 = "1";					  
				  //}
				  
				  //StaticVar.locate.get((CustomTreeItem) StaticVar.currNode.ti.getParentItem()).setKeyWordFrequency();

				  JSFunctions.openURL(StaticVar.currNode.text, StaticVar.currNode.text);
			  }
			  else if (DmaUtil.isCodeNode(StaticVar.currNode)){
				  if (StaticVar.textTabsArr.contains(StaticVar.currNode.nodeTextArea) == true){
					  StaticVar.tabPanel.selectTab(StaticVar.textTabsArr.indexOf(StaticVar.currNode.nodeTextArea)+StaticVar.tabCount);
				  }
				  else {
					  StaticVar.textTabsArr.add(StaticVar.currNode.nodeTextArea);
					  final TextArea tabTextArea = new TextArea();
					  final FormPanel tabTextFormPanel = new FormPanel();
					  tabTextFormPanel.add(tabTextArea);
					  StaticVar.locateCodeTab.put(tabTextArea, StaticVar.currNode.ti);

					  tabTextArea.setPixelSize(StaticVar.codeEditorWidth, StaticVar.codeEditorHeight);
					  tabTextArea.setText(StaticVar.currNode.text);
					  StaticVar.codeTabId += 1;
					  tabTextArea.getElement().setId("codeTab"+StaticVar.codeTabId);
					  
					  final Command cmdTabClose = new Command(){
						  public void execute() {
							  SaveDma.copyCodesFromTabs();
							  DmaUtil.tabClose(StaticVar.locate.get((CustomTreeItem) StaticVar.locateCodeTab.get(tabTextArea)));
							  StaticVar.locateCodeTab.remove(tabTextArea);
						  }
					  };
					  
						final ListBox codeType = new ListBox();
						//StaticVar.rootPanel.add(mode, 5, 22);
						//StaticVar.rootPanel.add(tpCode, 95, 20);
						
						codeType.setTitle("Change mode");
						for (int i=0;i<StaticVar.childTextCodeTypeList.size();i++) {
							codeType.addItem(StaticVar.childTextCodeTypeList.get(i), StaticVar.childTextCodeTypeMimeList.get(i));
						}
						
					    for (int i=0;i<StaticVar.childTextCodeTypeList.size();i++) 
				         if (StaticVar.currNode.attr2.equalsIgnoreCase(StaticVar.childTextCodeTypeList.get(i))){
				          	codeType.setSelectedIndex(i); 
				           	break;
				         }

					    codeType.addChangeHandler(new ChangeHandler() {
							@Override
							public void onChange(ChangeEvent event) {
								// TODO Auto-generated method stub
								DmaUtil.setCodeNodeImg(StaticVar.locate.get((CustomTreeItem) StaticVar.locateCodeTab.get(tabTextArea)), codeType.getItemText(codeType.getSelectedIndex()));
								DmaUtil.setEditorMode(Integer.parseInt(tabTextArea.getElement().getId().substring(7)), codeType.getItemText(codeType.getSelectedIndex()), codeType.getValue(codeType.getSelectedIndex()));
								DriveUtil.setFileModified();
							}
						});
					  
					    
					  HorizontalPanel fp = new HorizontalPanel();
					  fp.add(codeType);
					  //fp.add(new HTML("<span style=\"color: #888\">(Use /re/ syntax for regexp search) Ctrl-G Find next, Shift-Ctrl-G Find previous, Shift-Ctrl-F Replace, Shift-Ctrl-R Replace all</span>"));
					  Grid grid = new Grid(2, 1);
					  grid.setWidget(0, 0, fp);
					  grid.setWidget(1, 0, tabTextFormPanel);
					  
					  tabTextArea.setStyleName("gwt-dma-edit-RichTextArea");
					  
					  //Add to tab
					  TabHorPanel tabHorPanel = new TabHorPanel();
					  InlineLabel label = new InlineLabel(StaticVar.locate.get(StaticVar.currNode.ti.getParentItem()).text.length()>10?StaticVar.locate.get(StaticVar.currNode.ti.getParentItem()).text.substring(0, 10)+"..":StaticVar.locate.get(StaticVar.currNode.ti.getParentItem()).text);
					  tabHorPanel.add(label);
					  label.getElement().getStyle().setMarginRight(5, Unit.PX);
					  Image imgClose = new Image(StaticVar.menuResources.closeTab());
					  imgClose.setStyleName("gwt-dmaImage");
					  imgClose.setTitle("close tab");
					  tabHorPanel.add(imgClose);
					  imgClose.addClickHandler(new ClickHandler() {
						@Override
						public void onClick(ClickEvent event) {
							// TODO Auto-generated method stub
							cmdTabClose.execute();
						}
					  });
					  tabHorPanel.setTitle(StaticVar.locate.get(StaticVar.currNode.ti.getParentItem()).text);
					  
					  tabHorPanel.addDomHandler(new MouseDownHandler() {
						
						@Override
						public void onMouseDown(MouseDownEvent event) {
							// TODO Auto-generated method stub
							if (event.getNativeButton() == Event.BUTTON_RIGHT)
								  DmaUtil.showTabCloseCommandsPopup(event.getClientX(), event.getClientY());
						}
					  }, MouseDownEvent.getType());
					  
					  StaticVar.tabPanel.add(grid, tabHorPanel);
					  StaticVar.tabPanel.selectTab(StaticVar.textTabsArr.indexOf(StaticVar.currNode.nodeTextArea)+StaticVar.tabCount);
					  
					  //try{
						  //StaticVar.currNode.attr4 = ""+(Integer.parseInt(StaticVar.currNode.attr4)+1);
					  //}catch (Exception e){
						  //StaticVar.currNode.attr4 = "1";					  
					  //}
					  
					  //StaticVar.locate.get((CustomTreeItem) StaticVar.currNode.ti.getParentItem()).setKeyWordFrequency();

					  Timers.createCodeEditor(StaticVar.codeTabId, codeType.getItemText(codeType.getSelectedIndex()), codeType.getValue(codeType.getSelectedIndex()));
				  }
			  }
			  else if (DmaUtil.isNotesNode(StaticVar.currNode)) {
				  editUrlCommand.execute();
			  }
			  else {
				  if (StaticVar.textTabsArr.contains(StaticVar.currNode.nodeTextArea) == true){
					  StaticVar.tabPanel.selectTab(StaticVar.textTabsArr.indexOf(StaticVar.currNode.nodeTextArea)+StaticVar.tabCount);
				  }
				  else {
					  StaticVar.textTabsArr.add(StaticVar.currNode.nodeTextArea);
					  final RichTextArea tabTextArea = new RichTextArea();
					  StaticVar.locateTextTab.put(tabTextArea, StaticVar.currNode.ti);
					  
					  tabTextArea.setPixelSize(StaticVar.textEditorWidth, StaticVar.textEditorHeight);
					  tabTextArea.setHTML(StaticVar.currNode.text.replace("<script", "lt; script").replace("createElement", "").replace("appendChild", "").replace("\n", "<br>"));
					  //tabTextArea.setHTML(SimpleHtmlSanitizer.sanitizeHtml(StaticVar.currNode.text).asString().replace("<script", "lt; script").replace("src=", "src").replace("src =", "src").replace("document.createElement", ""));
					  final RichTextToolbar toolbar = new RichTextToolbar(tabTextArea);
					  
					  HorizontalPanel horFontPanel = new HorizontalPanel();

					  final Command cmdTabClose = new Command(){
						  public void execute() {
							  SaveDma.copyCodesFromTabs();
							  DmaUtil.tabClose(StaticVar.locate.get(StaticVar.locateTextTab.get(tabTextArea)));
							  StaticVar.locateTextTab.remove(tabTextArea);
							}
						  };

					  horFontPanel.add(toolbar);

					  Grid grid = new Grid(2, 1);
					  grid.setWidget(0, 0, horFontPanel);
					  grid.setWidget(1, 0, tabTextArea);
					  
					  tabTextArea.setStyleName("gwt-dma-edit-RichTextArea");

					  tabTextArea.addKeyDownHandler(new KeyDownHandler() {
							public void onKeyDown(KeyDownEvent event) {
								//if (tabTextArea.getHTML().equalsIgnoreCase(tabTextArea.prevHtml) == false){
									//tabTextArea.prevHtml = tabTextArea.getHTML();
									//DriveUtil.setFileModified();
								//}
								
								if (event.isControlKeyDown() && event.getNativeKeyCode() == 80){
									event.preventDefault();
									DmaUtil.printText(tabTextArea.getHTML());
								}
								else if (event.isControlKeyDown() && event.getNativeKeyCode() == 70){
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
					  });

					  //Add to tab
					  TabHorPanel tabHorPanel = new TabHorPanel();
					  InlineLabel label = new InlineLabel(StaticVar.locate.get(StaticVar.currNode.ti.getParentItem()).text.length()>10?StaticVar.locate.get(StaticVar.currNode.ti.getParentItem()).text.substring(0, 10)+"..":StaticVar.locate.get(StaticVar.currNode.ti.getParentItem()).text);
					  tabHorPanel.add(label);
					  label.getElement().getStyle().setMarginRight(5, Unit.PX);
					  Image imgClose = new Image(StaticVar.menuResources.closeTab());
					  imgClose.setStyleName("gwt-dmaImage");
					  imgClose.setTitle("close tab");
					  tabHorPanel.add(imgClose);
					  imgClose.addClickHandler(new ClickHandler() {
						@Override
						public void onClick(ClickEvent event) {
							// TODO Auto-generated method stub
							cmdTabClose.execute();
						}
					  });
					  tabHorPanel.setTitle(StaticVar.locate.get(StaticVar.currNode.ti.getParentItem()).text);
					  
					  tabHorPanel.addDomHandler(new MouseDownHandler() {
						
						@Override
						public void onMouseDown(MouseDownEvent event) {
							// TODO Auto-generated method stub
							if (event.getNativeButton() == Event.BUTTON_RIGHT)
								  DmaUtil.showTabCloseCommandsPopup(event.getClientX(), event.getClientY());
						}
					  }, MouseDownEvent.getType());
					  
					  StaticVar.tabPanel.add(grid, tabHorPanel);

					  //try{
						  //StaticVar.currNode.attr4 = ""+(Integer.parseInt(StaticVar.currNode.attr4)+1);
					  //}catch (Exception e){
						  //StaticVar.currNode.attr4 = "1";					  
					  //}
					  
					  //StaticVar.locate.get((CustomTreeItem) StaticVar.currNode.ti.getParentItem()).setKeyWordFrequency();

					  StaticVar.tabPanel.selectTab(StaticVar.textTabsArr.indexOf(StaticVar.currNode.nodeTextArea)+StaticVar.tabCount);
					  //tabTextArea.setFocus(true);
				  }
			  }
        }
    };
    
    public static Command editUrlCommand = new Command(){
		@Override
		public void execute() {
    		Menus.popupPanel.hide();
    		Timers.showProcessing(800);
    		
			// TODO Auto-generated method stub
			  if (StaticVar.textTabsArr.contains(StaticVar.currNode.nodeTextArea) == true){
				  StaticVar.tabPanel.selectTab(StaticVar.textTabsArr.indexOf(StaticVar.currNode.nodeTextArea)+StaticVar.tabCount);
			  }
			  else {
				  StaticVar.textTabsArr.add(StaticVar.currNode.nodeTextArea);
				  final TextArea tabTextArea = new TextArea();
				  StaticVar.locateUrlTab.put(tabTextArea, StaticVar.currNode.ti);
				  
				  tabTextArea.setPixelSize(StaticVar.notesEditorWidth, StaticVar.notesEditorHeight);
				  tabTextArea.setText(StaticVar.currNode.text);
				  
				  final Command cmdTabClose = new Command(){
					  public void execute() {
						  SaveDma.copyCodesFromTabs();
						  DmaUtil.tabClose(StaticVar.locate.get(StaticVar.locateUrlTab.get(tabTextArea)));
						  StaticVar.locateUrlTab.remove(tabTextArea);
						}
					  };

				  Grid grid = new Grid(1, 1);
				  grid.setWidget(0, 0, tabTextArea);
				  
				  tabTextArea.setStyleName("gwt-dma-edit-TextArea");
				  tabTextArea.addKeyDownHandler(new KeyDownHandler() {
						public void onKeyDown(KeyDownEvent event) {
							if (event.isControlKeyDown() && event.getNativeKeyCode() == 80){
								event.preventDefault();
								DmaUtil.printText(tabTextArea.getText());
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
				  });
				  
				  tabTextArea.addValueChangeHandler(new ValueChangeHandler<String>() {
					
					@Override
					public void onValueChange(ValueChangeEvent<String> event) {
						// TODO Auto-generated method stub
						DriveUtil.setFileModified();
					}
				  });

				  //Add to tab
				  TabHorPanel tabHorPanel = new TabHorPanel();
				  InlineLabel label = new InlineLabel(StaticVar.locate.get(StaticVar.currNode.ti.getParentItem()).text.length()>10?StaticVar.locate.get(StaticVar.currNode.ti.getParentItem()).text.substring(0, 10)+"..":StaticVar.locate.get(StaticVar.currNode.ti.getParentItem()).text);
				  tabHorPanel.add(label);
				  label.getElement().getStyle().setMarginRight(5, Unit.PX);
				  Image imgClose = new Image(StaticVar.menuResources.closeTab());
				  imgClose.setStyleName("gwt-dmaImage");
				  imgClose.setTitle("close tab");
				  tabHorPanel.add(imgClose);
				  imgClose.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						// TODO Auto-generated method stub
						cmdTabClose.execute();
					}
				  });
				  tabHorPanel.setTitle(StaticVar.locate.get(StaticVar.currNode.ti.getParentItem()).text);
				  
				  tabHorPanel.addDomHandler(new MouseDownHandler() {
					
					@Override
					public void onMouseDown(MouseDownEvent event) {
						// TODO Auto-generated method stub
						if (event.getNativeButton() == Event.BUTTON_RIGHT)
							  DmaUtil.showTabCloseCommandsPopup(event.getClientX(), event.getClientY());
					}
				  }, MouseDownEvent.getType());
				  
				  StaticVar.tabPanel.add(grid, tabHorPanel);
				  StaticVar.tabPanel.selectTab(StaticVar.textTabsArr.indexOf(StaticVar.currNode.nodeTextArea)+StaticVar.tabCount);
				  tabTextArea.setFocus(true);
				  //tabTextArea.setCursorPos(tabTextArea.getText().length());
			  }
		}
    };

    public static Command moveNodeCommand = new Command() {
    	public void execute() {
    		Menus.popupPanel.hide();
    		StaticVar.currNode.nodeTextBox.addStyleName("SearchMatchNode");
			DmaUtil.buildMoveNodeTree(StaticVar.root_ti, StaticVar.root_moveNode_ti);
			if (StaticVar.selectedNodePairList.size() > 0)
				StaticVar.selectedNodePairList.clear();
    		showMoveTreeCommand.execute();    		
    	}
    };
    
    public static Command showMoveTreeCommand = new Command() {
    	public void execute() {
    		StaticVar.moveNodeTreePopPanel.setTitle("Hit enter to choose the parent node, use search if needed");
			StaticVar.moveNodeTreePopPanel.setWidget(StaticVar.moveNodeTreePanel);
			StaticVar.moveNodeTreePopPanel.center();
			StaticVar.moveNodeTreePopPanel.show();
			StaticVar.root_moveNode_ti.setState(true);
		    if (StaticVar.searchListMoveTree.size() > 0)
				  Timers.setScrollPositionMoveNode(StaticVar.searchListMoveTree.get(0));
			//DmaUtil.setDelayedFocus(StaticVar.suggestBox.getTextBox());
		    if (StaticVar.moveNodeTree.getSelectedItem() == null){
		    	StaticVar.moveNodeTree.setSelectedItem(StaticVar.root_moveNode_ti);
		    }
		    StaticVar.moveNodeTree.setFocus(true);
    	}
    };
    
    public static Command moveupCommand = new Command() {
    	public void execute() {
    		Timers.showProcessing(800);
    		if (StaticVar.currNode.ti.getParentItem().getChildIndex(StaticVar.currNode.ti) > 0){
    			StaticVar.currNode.ti.getParentItem().insertItem(StaticVar.currNode.ti.getParentItem().getChildIndex(StaticVar.currNode.ti)-1, StaticVar.currNode.ti);
    			DriveUtil.setFileModified();
    		}		
    		Menus.popupPanel.hide();
    		if (StaticVar.currNode.type.equalsIgnoreCase("KeyWord"))
       		  DmaUtil.setDelayedFocus(StaticVar.currNode.nodeTextBox.getOriginalWidget());
    		else
    		  DmaUtil.setDelayedFocus(StaticVar.currNode.nodeTextArea);
        }
    };
    
    public static Command movedownCommand = new Command() {
    	public void execute() {
    		Timers.showProcessing(800);
    		if (StaticVar.currNode.ti.getParentItem().getChildIndex(StaticVar.currNode.ti) < StaticVar.currNode.ti.getParentItem().getChildCount()-1){
    			StaticVar.currNode.ti.getParentItem().insertItem(StaticVar.currNode.ti.getParentItem().getChildIndex(StaticVar.currNode.ti)+1, StaticVar.currNode.ti);
    			DriveUtil.setFileModified();
    		}		
    		Menus.popupPanel.hide();
    		if (StaticVar.currNode.type.equalsIgnoreCase("KeyWord"))
         	  DmaUtil.setDelayedFocus(StaticVar.currNode.nodeTextBox.getOriginalWidget());
      		else
      		  DmaUtil.setDelayedFocus(StaticVar.currNode.nodeTextArea);
        }
    };

    public static Command removeCommand = new Command() {
    	public void execute() {
    		Menus.popupPanel.hide();
    		DmaUtil.removeNode(StaticVar.currNode);
        }
    };

    public static Command cutNodeCommand = new Command() {
    	public void execute() {
    		Timers.showProcessing(800);
    		DmaUtil.assignCutNode(StaticVar.currNode);
    		Menus.popupPanel.hide();
        }
    };
    
    public static Command pasteNodeCommand = new Command() {
    	public void execute() {
    		Timers.showProcessing(800);
    		DmaUtil.pasteNode(StaticVar.currNode);
    		Menus.popupPanel.hide();
        }
    };
    
    public static Command previewCommand = new Command(){
    	public void execute(){
        	Timers.showProcessing(800);
    		Menus.popupPanel.hide();
    		if (DmaUtil.isTextNode(StaticVar.currNode) == false && DmaUtil.isValidUrl(StaticVar.currNode.text)){
            	if (StaticVar.previewFrames.size() == 0){
               	 DmaUtil.loadUrls();
           	     StaticVar.dialogPreviewBox.setSlides(StaticVar.previewFrames);
           	     StaticVar.dialogPreviewBox.setTitle("Preview <--   -->");
               	}
               	StaticVar.dialogPreviewBox.openSlide(StaticVar.currNode);
               	StaticVar.dialogPreviewBox.center();
               	StaticVar.dialogPreviewBox.show();
    		}else DMA.showMsg("Not an url");
    	}
    };


    //Features
    public static Command featuresCommand = new Command() {
    	public void execute() {
    		/*Timers.showProcessing(800);
        	if(StaticVar.featureImgs.size() == 0){
        		StaticVar.featureImgs.add(new String("http://www.youtube.com/embed/1N5mEqdg6BQ?iv_load_policy=1&hd=1"));
        		StaticVar.featureImgs.add(new String("http://www.youtube.com/embed/6XgpMsC1AcY?iv_load_policy=1&hd=1")); //?rel=0&wmode=opaque
	    	    //StaticVar.featureImgs.add(new String(StaticVar.APP_BASE_URL+"/demo/slides/1.jpg"));
	    	    StaticVar.featureImgs.add(new String(StaticVar.APP_BASE_URL+"/demo/slides/2.jpg"));
	    	    //StaticVar.featureImgs.add(new String(StaticVar.APP_BASE_URL+"/demo/slides/3.jpg"));
	    	    //StaticVar.featureImgs.add(new String(StaticVar.APP_BASE_URL+"/demo/slides/4.jpg"));
	    	    //StaticVar.featureImgs.add(new String(StaticVar.APP_BASE_URL+"/demo/slides/5.jpg"));
	    	    //StaticVar.featureImgs.add(new String(StaticVar.APP_BASE_URL+"/demo/slides/6.jpg"));
	    	    //StaticVar.featureImgs.add(new String(StaticVar.APP_BASE_URL+"/demo/slides/7.jpg"));
	    	    //StaticVar.featureImgs.add(new String(StaticVar.APP_BASE_URL+"/demo/slides/8.jpg"));
	    	    //StaticVar.featureImgs.add(new String(StaticVar.APP_BASE_URL+"/demo/slides/9.jpg"));
	    	    StaticVar.featureImgs.add(new String(StaticVar.APP_BASE_URL+"/demo/slides/11.jpg"));
	    	    //StaticVar.featureImgs.add(new String(StaticVar.APP_BASE_URL+"/demo/slides/12.jpg"));
	    	    StaticVar.featureImgs.add(new String(StaticVar.APP_BASE_URL+"/demo/slides/13.jpg"));
	    	    StaticVar.featureImgs.add(new String(StaticVar.APP_BASE_URL+"/demo/slides/14.jpg"));
	    	    StaticVar.featureImgs.add(new String(StaticVar.APP_BASE_URL+"/demo/slides/15.jpg"));
	    	    StaticVar.featureImgs.add(new String(StaticVar.APP_BASE_URL+"/demo/slides/16.jpg"));
	    	    StaticVar.dialogFeatureBox.setSlides(StaticVar.featureImgs);
	    	    StaticVar.dialogFeatureBox.setTitle("Esc to close");
        	}
        	StaticVar.dialogFeatureBox.setCurrentImage(0);
        	StaticVar.dialogFeatureBox.openImg(StaticVar.featureImgs.get(0));
        	StaticVar.dialogFeatureBox.center();
        	StaticVar.dialogFeatureBox.show();*/
        	JSFunctions.openURL(StaticVar.APP_BASE_URL+"/index.html?fileId=0BwVzf8LNyklCUGZYQXAyblJydmM", "Mem Tree tutorial");
    	}
    };
    
    //saveCommand
    public static Command saveCommand = new Command() {
    	public void execute() {
        	SaveDma.saveDMA();
    	}
    };
    
    public static Command saveAsCommand = new Command() {
    	public void execute() {
    		  if (StaticVar.readonly_file){
    				 DMA.showMsg("Read-only file.."); 
    			  }else{
    				  if (StaticVar.savingFlag)
    				  {
    					DMA.showMsg("Save in progress");  
    				  }else{
    					if (StaticVar.dma_file){
      					  SaveDma.copyCodesFromTabs();
      		    		  SaveDma.createNewDriveFile();
    					}
    					else
    					  DMA.showMsg("Not supported yet for non mem-tree file");    					
    				  }
    			  }
    	}
    };
    
    //downloadCommand
    public static Command downloadCommand = new Command() {
		public void execute() {
			// TODO Auto-generated method stub
			DriveUtil.downloadDMA();
		}
    };
	
    //Chrome sync
    public static Command chromeSyncCommand = new Command() {
    	public void execute() {
			Timers.showProcessing(800);
			DMA.showMsg("Syncing..");
			StaticVar.tabPanel.selectTab(0);
			JSFunctions.getSyncData();
    	}
    };
	
    //Chrome sync remove
    public static Command chromeSyncRemoveCommand = new Command() {
    	public void execute() {
			Timers.showProcessing(800);
			DMA.showMsg("Removing sync data..");
			JSFunctions.deleteSyncData();
    	}
    };
    
    public static Command importBookmarksCommand = new Command() {
    	public void execute() {
			Timers.showProcessing(800);
			DMA.showMsg("Pulling bookmarks..");
			StaticVar.tabPanel.selectTab(0);
			JSFunctions.getBookMarks();
    	}
    };
	
    //Chrome extension
    public static Command chromeExtCommand = new Command() {
    	public void execute() {
        	Timers.showProcessing(800);
        	JSFunctions.openURL("https://chrome.google.com/extensions/detail/ogfmiclbmmfeckiikcfpdpfakggjgdcd","_blank");
    	}
    };
    
    public static Command webStoreCommand = new Command() {
    	public void execute() {
        	Timers.showProcessing(800);
        	JSFunctions.openURL("https://chrome.google.com/webstore/detail/mem-tree/hmamefdgjljgbmaihadoocnkbhfdkbdm/reviews","_blank");
    	}
    };
    
	
    //Sign Out
    public static Command signOutCommand = new Command() {
    	public void execute() {
        	Timers.showProcessing(800);
        	SignOut.signOut();
    	}
    };
	
    public static Command driveCommand = new Command() {
    	public void execute(){
    		JSFunctions.showPicker();
    	}
    };

	public static Command showTableViewCommand = new Command(){
		@Override
		public void execute() {
			StaticVar.hp.clear();
			StaticVar.hp.add(StaticVar.tableView.vp);
			StaticVar.tabPanel.selectTab(0);
			// TODO Auto-generated method stub
			StaticVar.tableView.loadData();
			StaticVar.scrAbsResultsPanel.setVisible(false);
			StaticVar.hp.setVisible(true);
		}
	};

	public static Command showTaskViewCommand = new Command(){
		@Override
		public void execute() {
			StaticVar.hp.clear();
			StaticVar.hp.add(StaticVar.taskView.vp);
			StaticVar.tabPanel.selectTab(0);
			// TODO Auto-generated method stub
			StaticVar.scrAbsResultsPanel.setVisible(false);
			StaticVar.hp.setVisible(true);           			
		}
	};

	public static Command showSyncViewCommand = new Command(){
		@Override
		public void execute() {
			StaticVar.hp.clear();
			StaticVar.hp.add(StaticVar.syncView.vp);
			StaticVar.tabPanel.selectTab(0);
			// TODO Auto-generated method stub
			StaticVar.scrAbsResultsPanel.setVisible(false);
			StaticVar.hp.setVisible(true);           			
		}
	};

	public static Command markRedCommand = new Command(){
		@Override
		public void execute() {
    		Menus.popupPanel.hide();
			StaticVar.currNode.attr3 = "red";
			StaticVar.currNode.horPanel.clear();
			StaticVar.currNode.horPanel.add(new Image(StaticVar.menuResources.redFlag()));
			StaticVar.currNode.horPanel.add(StaticVar.currNode.dragPanel);
			DriveUtil.setFileModified();
		}		
	};

	public static Command markGreenCommand = new Command(){
		@Override
		public void execute() {
    		Menus.popupPanel.hide();
			StaticVar.currNode.attr3 = "green";
			StaticVar.currNode.horPanel.clear();
			StaticVar.currNode.horPanel.add(new Image(StaticVar.menuResources.greenFlag()));
			StaticVar.currNode.horPanel.add(StaticVar.currNode.dragPanel);
			DriveUtil.setFileModified();
		}		
	};

	public static Command clearFlagCommand = new Command(){
		@Override
		public void execute() {
    		Menus.popupPanel.hide();
			StaticVar.currNode.attr3 = "";
			StaticVar.currNode.horPanel.clear();
			StaticVar.currNode.horPanel.add(StaticVar.currNode.dragPanel);
			DriveUtil.setFileModified();
		}		
	};

	public static Command newFileCommand = new Command(){
		@Override
		public void execute() {
			// TODO Auto-generated method stub
			DriveUtil.createNewFile();
		}
	};

	public static Command blogCommand = new Command(){
		@Override
		public void execute() {
			// TODO Auto-generated method stub
			JSFunctions.openURL("http://datamagicagent.blogspot.com/2013/05/pose-your-questions-here.html", "DMA Blog");
		}
	};
	
	public static Command bugCommand = new Command(){
		@Override
		public void execute() {
			// TODO Auto-generated method stub
			//JSFunctions.openURL("http://datamagicagent.blogspot.com/2013/10/problem-opening-files-with-mem-tree.html", "DMA Blog");
			//JSFunctions.openURL("http://datamagicagent.blogspot.com/2013/11/open-issues-some-are-requests-from-users.html", "DMA Blog");
			JSFunctions.openURL("http://datamagicagent.blogspot.com", "DMA Blog");
		}
	};
	
	
	public static Command privacyCommand = new Command(){
		@Override
		public void execute() {
			// TODO Auto-generated method stub
			JSFunctions.openURL(StaticVar.APP_BASE_URL+"/privacy.html", "Privacy policy");
		}
	};
	
	public static Command creditsCommand = new Command(){
		@Override
		public void execute() {
			// TODO Auto-generated method stub
			JSFunctions.openURL(StaticVar.APP_BASE_URL+"/credits.html", "Credits");
		}
	};
	
	public static Command feedbackCommand = new Command(){
		@Override
		public void execute() {
			// TODO Auto-generated method stub
			JSFunctions.openURL("https://docs.google.com/forms/d/1EDoDCyoAZ81coYBmUYOw5VvEFqEjlxJckQNN0PiBDTA", "DMA Blog");
		}
	};

	public static Command sampleCommand = new Command(){
		@Override
		public void execute() {
			// TODO Auto-generated method stub
			JSFunctions.openURL(StaticVar.APP_BASE_URL+"/index.html?fileId=0BwVzf8LNyklCQWUwMHFqVU1RbXM", "Sample Mem Tree file");
		}
	};
}

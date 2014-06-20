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

import java.util.Map.Entry;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;

public class ResizeWindow {
    static void resizeDMA() {
		// TODO Auto-generated method stub

	    StaticVar.previewFrameWidth = Window.getClientWidth()-100;
	    StaticVar.previewFrameHeight = Window.getClientHeight()-10;
	    
	    if (DmaUtil.requestFromMobile()){
	    	RootPanel.get("LeftPane").setWidth("100%");
	    	RootPanel.get("RightPane").setWidth("100%");
	    	RootPanel.get("RightPane").getElement().getStyle().setTop(Window.getClientHeight(), Unit.PX);
	    	RootPanel.get("RightPane").getElement().getStyle().setLeft(0, Unit.PX);
	    }

	    StaticVar.treeWidth = RootPanel.get("LeftPane").getOffsetWidth()-70;
    	StaticVar.treeHeight = RootPanel.get("LeftPane").getOffsetHeight()*90/100;
    	StaticVar.treeScroller.setPixelSize(StaticVar.treeWidth, StaticVar.treeHeight);

    	StaticVar.tabPanelWidth = RootPanel.get("RightPane").getOffsetWidth()-10;
    	StaticVar.tabPanelHeight = RootPanel.get("RightPane").getOffsetHeight();
    	StaticVar.codeEditorWidth = StaticVar.tabPanelWidth-10;
    	StaticVar.codeEditorHeight = StaticVar.tabPanelHeight-60;
    	StaticVar.textEditorWidth = StaticVar.tabPanelWidth-15;
    	StaticVar.textEditorHeight = StaticVar.tabPanelHeight-100;
    	StaticVar.notesEditorWidth = StaticVar.textEditorWidth;
    	StaticVar.notesEditorHeight = StaticVar.tabPanelHeight-40;
    	
	    StaticVar.absSearchPanel.setPixelSize(StaticVar.tabPanelWidth, StaticVar.tabPanelHeight);
		StaticVar.tabPanel.setPixelSize(StaticVar.tabPanelWidth, StaticVar.tabPanelHeight);

	    for (Entry<RichTextArea, CustomTreeItem> entry : StaticVar.locateTextTab.entrySet()) {
		    entry.getKey().setPixelSize(StaticVar.textEditorWidth, StaticVar.textEditorHeight);
		}
	    
	    for (Entry<TextArea, CustomTreeItem> entry : StaticVar.locateUrlTab.entrySet()) {
		    entry.getKey().setPixelSize(StaticVar.notesEditorWidth, StaticVar.notesEditorHeight);
		}
		
	    for (Entry<TextArea, CustomTreeItem> entry : StaticVar.locateCodeTab.entrySet()) {
	    	DmaUtil.setCodeEditorSize(Integer.parseInt(entry.getKey().getElement().getId().substring(7)), StaticVar.codeEditorWidth, StaticVar.codeEditorHeight);
		}

		StaticVar.scrAbsResultsPanel.setPixelSize(StaticVar.tabPanelWidth, StaticVar.tabPanelHeight);
		StaticVar.searchAbsResultsPanel.setPixelSize(StaticVar.tabPanelWidth, StaticVar.tabPanelHeight);
		StaticVar.searchResultsPanelHdr.setWidth(StaticVar.tabPanelWidth+"px");
		StaticVar.driveResultsView.vp.setWidth(StaticVar.tabPanelWidth+"px");
		StaticVar.hp.setPixelSize(StaticVar.tabPanelWidth-40, StaticVar.tabPanelHeight-40);
		StaticVar.absSearchPanel.setWidgetPosition(StaticVar.scrAbsResultsPanel, 0, 0);
		StaticVar.absSearchPanel.setWidgetPosition(StaticVar.hp, 0, 0);
	    //RootPanel.get("TopPane").setWidgetPosition(StaticVar.menuBar, RootPanel.get("TopPane").getOffsetWidth()*3/10, 6);
	    RootPanel.get("LeftPane").setWidgetPosition(StaticVar.top_img, RootPanel.get("LeftPane").getOffsetWidth()-18, 40);
	    RootPanel.get("LeftPane").setWidgetPosition(StaticVar.bottom_img, RootPanel.get("LeftPane").getOffsetWidth()-18, 60);
	    //RootPanel.get("LeftPane").setWidgetPosition(StaticVar.hideImg, RootPanel.get("LeftPane").getOffsetWidth()-22, Window.getClientHeight()/2);
	    //RootPanel.get("RightPane").setWidgetPosition(StaticVar.showImg, 0, Window.getClientHeight()/2);
    }
}

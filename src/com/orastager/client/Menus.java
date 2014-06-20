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

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.PopupPanel;

public class Menus {
	
	public static CustomPopupPanel popupPanel = new CustomPopupPanel(true, false, true);
	public static MenuBar vertMenuPanel = new MenuBar(true);
	public static MenuBar subChildTextMenu = new MenuBar(true);
	public static MenuBar subTextMenu = new MenuBar(true);
	public static MenuBar subLinksMenu = new MenuBar(true);
	public static MenuBar subCodeMenu = new MenuBar(true);
	public static MenuBar subMoveMenu = new MenuBar(true);
	public static MenuBar subMarkMenu = new MenuBar(true);
	
	public static MenuBar tabCloseMenu = new MenuBar();
	public static MenuItem closeTabsMI = new MenuItem("Close all tabs", Commands.closeAllTabs);
	public static InlineLabel closeTabsExCurrMI = new InlineLabel("Close all but current");

	private static MenuItem keyWordMI = new MenuItem("KeyWord", Commands.plusCommand);
	private static MenuItem notesMI = new MenuItem("Notes", Commands.addNotes);
	private static MenuItem textMI = new MenuItem("Rich Text", Commands.addPlainText);

	private static MenuItem sqlMI = new MenuItem("sql", Commands.addSQL);
	private static MenuItem javaMI = new MenuItem("java", Commands.addJAVA);
	private static MenuItem cMI = new MenuItem("c", Commands.addC);
	private static MenuItem cppMI = new MenuItem("cpp", Commands.addCPP);
	private static MenuItem cssMI = new MenuItem("css", Commands.addCSS);
	private static MenuItem htmlMI = new MenuItem("html", Commands.addHTML);
	private static MenuItem javaScriptMI = new MenuItem("javascript", Commands.addJAVASCRIPT);
	private static MenuItem mysqlMI = new MenuItem("mysql", Commands.addMYSQL);
    private static MenuItem phpMI = new MenuItem("php", Commands.addPHP);
    private static MenuItem pythonMI = new MenuItem("python", Commands.addPython);
    private static MenuItem shellscriptMI = new MenuItem("shell", Commands.addSHELLSCRIPT);
    private static MenuItem xmlCodeMI = new MenuItem("xml", Commands.addXML);
    private static MenuItem rCodeMI = new MenuItem("r", Commands.addR);

    private static MenuItem urlMI = new MenuItem("url", Commands.addURL);
    private static MenuItem pdfMI = new MenuItem("pdf", Commands.addPDF);
    private static MenuItem wordMI = new MenuItem("word", Commands.addWord);
    private static MenuItem excelMI = new MenuItem("excel", Commands.addExcel);
    private static MenuItem musicMI = new MenuItem("music", Commands.addMusic);
    private static MenuItem videoMI = new MenuItem("video", Commands.addYTVideo);
    private static MenuItem playListMI = new MenuItem("playList", Commands.addPlayList);
    private static MenuItem imageMI = new MenuItem("image", Commands.addImage);

    private static MenuItem moveToMI = new MenuItem("Move To", Commands.moveNodeCommand);
    private static MenuItem moveUPMI = new MenuItem("Move Up", Commands.moveupCommand);
    private static MenuItem moveDOWNMI = new MenuItem("Move Down", Commands.movedownCommand);
    private static MenuItem cutMI = new MenuItem("Cut Node", Commands.cutNodeCommand);
    private static MenuItem pasteMI = new MenuItem("Paste Node", Commands.pasteNodeCommand);
    private static MenuItem markRedMI = new MenuItem("Red", Commands.markRedCommand);
    private static MenuItem markGreenMI = new MenuItem("Green", Commands.markGreenCommand);
    private static MenuItem clearFlagsMI = new MenuItem("Clear Flags", Commands.clearFlagCommand);
    private static MenuItem xmlMI = new MenuItem("Json", Commands.jsonCommand);
    private static MenuItem removeMI = new MenuItem("Delete", Commands.removeCommand);


    private static MenuItem shareMI = new MenuItem("share", Commands.shareCommand);

	private static MenuItem maximizeMI = new MenuItem("Maximize", Commands.maximizeCommand);
	private static MenuItem previewMI = new MenuItem("preview", Commands.previewCommand);
	private static MenuItem exportHtmlMI = new MenuItem("html", Commands.exportHtmlCommand);
	private static MenuItem editUrlMI = new MenuItem("edit", Commands.editUrlCommand);
	
	public static void setMenuTitles(){
		vertMenuPanel.setAutoOpen(true);
		vertMenuPanel.getElement().getStyle().setPadding(5, Unit.PX);
		
		keyWordMI.setTitle("Add new key word (Ctrl+K)");
		notesMI.setTitle("Notes (plain)");
        textMI.setTitle("Notes (rich text)");        

	    sqlMI.setTitle("sql");
        javaMI.setTitle("java");
        cMI.setTitle("c");
        cppMI.setTitle("cpp");
        cssMI.setTitle("css");
        htmlMI.setTitle("html");
        javaScriptMI.setTitle("java script");
        mysqlMI.setTitle("MySQL");
        phpMI.setTitle("php");
        pythonMI.setTitle("python");
        shellscriptMI.setTitle("Shell Script");
        xmlCodeMI.setTitle("xml");
        rCodeMI.setTitle("R language");

        urlMI.setTitle("Url/bookmark");
        pdfMI.setTitle("Pdf url/bookmark");
        wordMI.setTitle("Word doc url/bookmark");
        excelMI.setTitle("Excel file url/bookmark");
        musicMI.setTitle("Music url/bookmark");
        videoMI.setTitle("Video url/bookmark");
        playListMI.setTitle("Videos playlist url/bookmark");
        imageMI.setTitle("Image url");

	    moveToMI.setTitle("Move to (Ctrl+M)");
	    moveUPMI.setTitle("Move up (Ctrl+Up)");
	    moveDOWNMI.setTitle("Move down (Ctrl+Down)");
	    cutMI.setTitle("Cut to relocate (Ctrl+X)");
        pasteMI.setTitle("Paste after cut (Ctrl+V)");
	    removeMI.setTitle("Delete the node (Ctrl+D)");
	    markRedMI.setTitle("Mark Red");
	    markGreenMI.setTitle("Mark Green");
	    clearFlagsMI.setTitle("Clear Flag");
        xmlMI.setTitle("Generate json of this node and child nodes");
        shareMI.setTitle("Share this DMA file");

		maximizeMI.setTitle("Maximize");
		previewMI.setTitle("Preview/slideshow");
		exportHtmlMI.setTitle("Export to html");
		editUrlMI.setTitle("edit");
	   	
	   	tabCloseMenu.addItem(closeTabsMI);
	   	//tabCloseMenu.addItem(closeTabsExCurrMI);
	   	closeTabsMI.setStyleName("gwt-dma-MenuItem");
	   	//closeTabsExCurrMI.getElement().getStyle().setCursor(Cursor.POINTER);
	   	closeTabsMI.setTitle("Close all Tabs");
	   	//closeTabsExCurrMI.setTitle("Close all tabs but current");
	   	
	   	popupPanel.addCloseHandler(new CloseHandler<PopupPanel>() {			
			@Override
			public void onClose(CloseEvent<PopupPanel> event) {
				// TODO Auto-generated method stub
				//Timers.delayedNodeFocus(StaticVar.currNode);
			}
		});
	}

	public static void showMenuKeyWord(Boolean root){
		vertMenuPanel.clearItems();
		vertMenuPanel.addItem(keyWordMI);
		vertMenuPanel.addItem("ChildText", subChildTextMenu);

	     if (root == false){
			subMoveMenu.addItem(moveToMI);
			subMoveMenu.addItem(moveUPMI);
			subMoveMenu.addItem(moveDOWNMI);
			vertMenuPanel.addItem("Move", subMoveMenu);
		    //subMenu.addItem(cutMI);
	     }
	    //subMenu.addItem(pasteMI);
	    
	    if (root == false){
	    	subMarkMenu.addItem(markRedMI);
	    	subMarkMenu.addItem(markGreenMI);
	    	subMarkMenu.addItem(clearFlagsMI);
	    	vertMenuPanel.addItem("Mark", subMarkMenu);
	    }
	    
	    vertMenuPanel.addItem(xmlMI);
	    
	    if (root)
	    	vertMenuPanel.addItem(shareMI);
	    else
	    	vertMenuPanel.addItem(removeMI);

	    subTextMenu.clearItems();
	    subTextMenu.addItem(notesMI);
	    subTextMenu.addItem(textMI);
	    subChildTextMenu.clearItems();
		subChildTextMenu.addItem("Text",subTextMenu);

		subLinksMenu.clearItems();
	    subLinksMenu.addItem(urlMI);
	    subLinksMenu.addItem(pdfMI);
	    subLinksMenu.addItem(wordMI);
	    subLinksMenu.addItem(excelMI);
	    subLinksMenu.addItem(musicMI);
	    subLinksMenu.addItem(videoMI);
	    subLinksMenu.addItem(imageMI);
	    subLinksMenu.addItem(playListMI);
		subChildTextMenu.addItem("Links",subLinksMenu);

		subCodeMenu.clearItems();
	    subCodeMenu.addItem(cMI);
	    subCodeMenu.addItem(cppMI);
	    subCodeMenu.addItem(cssMI);
	    subCodeMenu.addItem(javaMI);
	    subCodeMenu.addItem(javaScriptMI);
	    subCodeMenu.addItem(htmlMI);
	    subCodeMenu.addItem(mysqlMI);
	    subCodeMenu.addItem(phpMI);
	    subCodeMenu.addItem(pythonMI);
	    subCodeMenu.addItem(rCodeMI);
	    subCodeMenu.addItem(shellscriptMI);
	    subCodeMenu.addItem(sqlMI);
	    subCodeMenu.addItem(xmlCodeMI);
		subChildTextMenu.addItem("Code",subCodeMenu);
		
		vertMenuPanel.focus();
		vertMenuPanel.selectItem(keyWordMI);		
	}

	public static void showMenuChildText(){
		vertMenuPanel.clearItems();
		vertMenuPanel.addItem(maximizeMI);
		vertMenuPanel.addItem(previewMI);
		vertMenuPanel.addItem(exportHtmlMI);
	    if (DmaUtil.isTextNode(StaticVar.currNode) == false){
	    	vertMenuPanel.addItem(editUrlMI);
	    }
	    vertMenuPanel.addItem(moveUPMI);
	    vertMenuPanel.addItem(moveDOWNMI);
	    vertMenuPanel.addItem(removeMI);

	    vertMenuPanel.focus();
		vertMenuPanel.selectItem(maximizeMI);		
	}
}

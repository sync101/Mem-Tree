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

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;


public class SideMenu {
	public static SideMenuCommand plusMI = new SideMenuCommand("Key Word", Commands.plusCommand, true);
	public static SideMenuCommand nodePairMI = new SideMenuCommand("Pair", Commands.addNodePair, true);

	public static SideMenuCommand notesMI = new SideMenuCommand("Notes", Commands.addNotes, true);
	public static SideMenuCommand textMI = new SideMenuCommand("Text", Commands.addPlainText, true);
    //public static DraggableImage xmlMI = new DraggableImage(xml(), Commands.jsonCommand);
	public static SideMenuCommand bulkAddMI = new SideMenuCommand("Bulk Add", Commands.showSyncViewCommand, false);

	public static SideMenuCommand sqlMI = new SideMenuCommand("Sql", Commands.addSQL, true);
	public static SideMenuCommand javaMI = new SideMenuCommand("Java", Commands.addJAVA, true);
	public static SideMenuCommand cMI = new SideMenuCommand("C", Commands.addC, true);
	public static SideMenuCommand cppMI = new SideMenuCommand("C++", Commands.addCPP, true);
	public static SideMenuCommand cssMI = new SideMenuCommand("CSS", Commands.addCSS, true);
	public static SideMenuCommand htmlMI = new SideMenuCommand("HTML", Commands.addHTML, true);
	public static SideMenuCommand javaScriptMI = new SideMenuCommand("JavaScript", Commands.addJAVASCRIPT, true);
	public static SideMenuCommand mysqlMI = new SideMenuCommand("MySql", Commands.addMYSQL, true);
    public static SideMenuCommand phpMI = new SideMenuCommand("PHP", Commands.addPHP, true);
    public static SideMenuCommand pythonMI = new SideMenuCommand("Python", Commands.addPython, true);
    public static SideMenuCommand shellscriptMI = new SideMenuCommand("Shell", Commands.addSHELLSCRIPT, true);
    public static SideMenuCommand xmlCodeMI = new SideMenuCommand("XML", Commands.addXML, true);
    public static SideMenuCommand rCodeMI = new SideMenuCommand("R", Commands.addR, true);

    public static SideMenuCommand urlMI = new SideMenuCommand("URL", Commands.addURL, true);
    public static SideMenuCommand pdfMI = new SideMenuCommand("PDF", Commands.addPDF, true);
    public static SideMenuCommand wordMI = new SideMenuCommand("Word", Commands.addWord, true);
    public static SideMenuCommand excelMI = new SideMenuCommand("Excel", Commands.addExcel, true);
    public static SideMenuCommand musicMI = new SideMenuCommand("Music", Commands.addMusic, true);
    public static SideMenuCommand videoMI = new SideMenuCommand("Video", Commands.addYTVideo, true);
    public static SideMenuCommand playListMI = new SideMenuCommand("PlayList", Commands.addPlayList, true);
    public static SideMenuCommand imageMI = new SideMenuCommand("Image", Commands.addImage, true);
    
	static CustomPopupPanel sideMenuPanel = new CustomPopupPanel(true, false, false);
	static Grid sideMenuGrid = new Grid(13, 3);
	static CustomImage sideMenuImage = new CustomImage(StaticVar.menuResources.addNodePair());
    
	public static void hidePopup(){
		sideMenuPanel.hide();
	}
	
    public static void addItems(){
    	//StaticVar.sideVertPanel.setWidth("50px");
    	//StaticVar.sideVertPanel.getElement().setAttribute("style","border-radius:4px;background:#6699cc;padding-right:5px;padding-left:5px;");
    	sideMenuPanel.clear();
    	sideMenuPanel.add(sideMenuGrid);
    	sideMenuGrid.setStyleName("SideMenu");
    	
    	//sideMenuGrid.setWidget(1, 0, StaticVar.addNodePairImg, 5, 35);
    	sideMenuGrid.setWidget(0, 0, plusMI);
    	//sideMenuGrid.setWidget(1, 0, xmlMI, 28, 75);
    	sideMenuGrid.setWidget(0, 1, nodePairMI);
    	sideMenuGrid.setWidget(0, 2, bulkAddMI);


    	sideMenuGrid.setWidget(2, 0, notesMI);
    	sideMenuGrid.setWidget(2, 1, textMI);

    	sideMenuGrid.setWidget(4, 0, urlMI);
    	sideMenuGrid.setWidget(4, 1, pdfMI);
    	sideMenuGrid.setWidget(4, 2, wordMI);
    	sideMenuGrid.setWidget(5, 0, excelMI);
    	sideMenuGrid.setWidget(5, 1, musicMI);
    	sideMenuGrid.setWidget(5, 2, videoMI);
    	sideMenuGrid.setWidget(6, 0, playListMI);
    	sideMenuGrid.setWidget(6, 1, imageMI);

    	sideMenuGrid.setWidget(8, 0, cMI);
    	sideMenuGrid.setWidget(8, 1, cppMI);
    	sideMenuGrid.setWidget(8, 2, cssMI);
    	sideMenuGrid.setWidget(9, 0, htmlMI);
    	sideMenuGrid.setWidget(9, 1, javaMI);
    	sideMenuGrid.setWidget(9, 2, javaScriptMI);
    	sideMenuGrid.setWidget(10, 0, mysqlMI);
    	sideMenuGrid.setWidget(10, 1, phpMI);
    	sideMenuGrid.setWidget(10, 2, pythonMI);
    	sideMenuGrid.setWidget(11, 0, rCodeMI);
    	sideMenuGrid.setWidget(11, 1, shellscriptMI);
    	sideMenuGrid.setWidget(11, 2, sqlMI);
    	sideMenuGrid.setWidget(12, 0, xmlCodeMI);
    	
		plusMI.setTitle("Add new key word (Ctrl+K)");
		nodePairMI.setTitle("Add key word/child text together");
        //xmlMI.setTitle("Generate xml of this sub tree");
		bulkAddMI.setTitle("Bulk add");

        notesMI.setTitle("Plain notes");
        textMI.setTitle("Rich text");
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
    }

}

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

import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.MenuItem;

public class TopMenu {
	
	public static void createMenu(){

		MenuItem fileMenu = new MenuItem("File", StaticVar.menuBarFile);
		MenuItem optionsMenu = new MenuItem("Options", StaticVar.menuBarOptions);
		MenuItem helpMenu = new MenuItem("Help", StaticVar.menuBarHelp);
	    StaticVar.menuBar.getElement().getStyle().setFloat(Float.RIGHT);
	    StaticVar.menuBar.getElement().getStyle().setPaddingRight(100, Unit.PX);
	    StaticVar.menuBar.setAutoOpen(true);
		StaticVar.menuBar.setAnimationEnabled(true);
		StaticVar.menuBar.addItem(fileMenu);
		StaticVar.menuBar.addItem(optionsMenu);
		StaticVar.menuBar.addItem(helpMenu);
		StaticVar.menuBarFile.setAnimationEnabled(true);
		StaticVar.menuBarOptions.setAnimationEnabled(true);
		StaticVar.menuBarHelp.setAnimationEnabled(true);

	    MenuItem newFileMI = new MenuItem("New", Commands.newFileCommand);
	    newFileMI.setTitle("New file");
	    StaticVar.menuBarFile.addItem(newFileMI);

	    MenuItem driveMI = new MenuItem("Open", Commands.driveCommand);
	    driveMI.setTitle("Google drive file picker");
	    StaticVar.menuBarFile.addItem(driveMI);

	    MenuItem shareMI = new MenuItem("Share", Commands.shareCommand);
	    shareMI.setTitle("Share the file");
	    StaticVar.menuBarFile.addItem(shareMI);

	    MenuItem downloadMI = new MenuItem(DmaUtil.makeImage(StaticVar.menuResources.download()), Commands.downloadCommand);
	    downloadMI.setTitle("Download last saved version of current open file");
	    //StaticVar.menuBarFile.addItem(downloadMI);

	    MenuItem saveMI = new MenuItem("Save", Commands.saveCommand);
	    saveMI.setTitle("Save (Ctrl+S)");
	    StaticVar.menuBarFile.addItem(saveMI);
	    
	    MenuItem saveAsMI = new MenuItem("Save as", Commands.saveAsCommand);
	    saveMI.setTitle("Save as");
	    StaticVar.menuBarFile.addItem(saveAsMI);
	    
	    MenuItem addNewKWMI = new MenuItem(DmaUtil.makeImage(StaticVar.menuResources.newFrame()), Commands.showSyncViewCommand);
	    addNewKWMI.setTitle("Add new key word quickly..");
	    //StaticVar.menuBarFile.addItem(addNewKWMI);
	    
	    MenuItem appendJsonMI = new MenuItem(DmaUtil.makeImage(StaticVar.menuResources.saveXml()), Commands.appendJsonCommand);
	    appendJsonMI.setTitle("Append json from other mem-tree files");
	    //StaticVar.menuBarFile.addItem(appendJsonMI);

	    MenuItem chromeSyncMI = new MenuItem("Sync from cache", Commands.chromeSyncCommand);
	    chromeSyncMI.setTitle("Pull data from chrome extension (mem-tree cache)");
	    StaticVar.menuBarOptions.addItem(chromeSyncMI);
	    
	    MenuItem chromeSyncRemoveMI = new MenuItem("Clear cache", Commands.chromeSyncRemoveCommand);
	    chromeSyncRemoveMI.setTitle("Clear browser extension cache");
	    StaticVar.menuBarOptions.addItem(chromeSyncRemoveMI);

	    MenuItem bookmarksSyncMI = new MenuItem("Import bookmarks", Commands.importBookmarksCommand);
	    bookmarksSyncMI.setTitle("Install chrome extension (mem-tree cache). Reload this page when using first time.");
	    StaticVar.menuBarOptions.addItem(bookmarksSyncMI);
	    
	    MenuItem taskViewMI = new MenuItem("Tasks", Commands.showTaskViewCommand);
	    taskViewMI.setTitle("Tasks");
	    StaticVar.menuBarOptions.addItem(taskViewMI);
	    
	    MenuItem tableViewMI = new MenuItem("Tabular view", Commands.showTableViewCommand);
	    tableViewMI.setTitle("Show tabular view");
	    StaticVar.menuBarOptions.addItem(tableViewMI);
	    
	    MenuItem chromeExtMI = new MenuItem("Extension", Commands.chromeExtCommand);
	    chromeExtMI.setTitle("Mem-tree cache (chrome extension)");
	    StaticVar.menuBarOptions.addItem(chromeExtMI);
	    
	    MenuItem featuresMI = new MenuItem("Tutorial", Commands.featuresCommand);
	    featuresMI.setTitle("Mem-tree tutorial/documentation");
	    StaticVar.menuBarHelp.addItem(featuresMI);

		MenuItem sampleMI = new MenuItem("Demo file", Commands.sampleCommand);
		sampleMI.setTitle("An example file. Explore and do some searches to understand.");
		StaticVar.menuBarHelp.addItem(sampleMI);

	    MenuItem feedbackMI = new MenuItem("Report bug", Commands.feedbackCommand);
	    feedbackMI.setTitle("Feedback/Report bug/Request feature");
	    StaticVar.menuBarHelp.addItem(feedbackMI);

	    MenuItem bugMI = new MenuItem("Open issues", Commands.bugCommand);
	    bugMI.setTitle("Open issues of mem-tree including problem opening/saving file");
	    StaticVar.menuBarHelp.addItem(bugMI);

	    MenuItem ratingMI = new MenuItem("Reviews", Commands.webStoreCommand);
	    ratingMI.setTitle("Rate mem-tree");
	    StaticVar.menuBarHelp.addItem(ratingMI);

	    MenuItem mailMI = new MenuItem("Blog", Commands.blogCommand);
	    mailMI.setTitle("Mem-tree shortcuts & other");
	    StaticVar.menuBarHelp.addItem(mailMI);

	    MenuItem creditsMI = new MenuItem("Credits", Commands.creditsCommand);
	    creditsMI.setTitle("Credits");
	    StaticVar.menuBarHelp.addItem(creditsMI);

	    MenuItem privacyMI = new MenuItem("Privacy", Commands.privacyCommand);
	    privacyMI.setTitle("Privacy policy");
	    StaticVar.menuBarHelp.addItem(privacyMI);

	    StaticVar.menuBarHelp.addSeparator();
		MenuItem signOutMI = new MenuItem("Sign out", Commands.signOutCommand);
	    signOutMI.setTitle("Sign Out (from Google accounts)");
	    StaticVar.menuBarHelp.addItem(signOutMI);
	}
}

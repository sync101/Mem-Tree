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

import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Label;

public class SideMenuCommand extends Label{
	Command command;
	
	SideMenuCommand(String text, final Command command, final boolean currNodeCheck){
		this.command = command;
		setStyleName("SideMenuCommand");
		setText(text);
		getElement().getStyle().setCursor(Cursor.POINTER);
		addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				//TODO Auto-generated method stub
				//right click on nodes
				if (currNodeCheck == false){
					SideMenu.hidePopup();
					StaticVar.auto_open = true;
					command.execute();
				}
				else if (StaticVar.currNode != null && StaticVar.currNode.isKeyWord()){
					SideMenu.hidePopup();
					StaticVar.auto_open = true;
					command.execute();
				}else
					DMA.showMsg("Select a parent KeyWord");
			}
		});
	}
}

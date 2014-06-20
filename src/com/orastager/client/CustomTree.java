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

import java.io.Serializable;

import com.google.gwt.event.dom.client.DragLeaveEvent;
import com.google.gwt.event.dom.client.DragLeaveHandler;
import com.google.gwt.event.dom.client.DragOverEvent;
import com.google.gwt.event.dom.client.DragOverHandler;
import com.google.gwt.event.dom.client.DropEvent;
import com.google.gwt.event.dom.client.DropHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.TreeItem;

public class CustomTree extends com.google.gwt.user.client.ui.Tree implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8935454885195907626L;

	CustomTree(Resources resources){
		super(resources);
		addDomHandler(new DragOverHandler()
		{
		    @Override
		    public void onDragOver(DragOverEvent event)
		    {
		    }
		}, DragOverEvent.getType());
		
		addDomHandler(new DragLeaveHandler()
		{
		    @Override
		    public void onDragLeave(DragLeaveEvent event)
		    {
		    }
		}, DragLeaveEvent.getType());
		
		addDomHandler(new DropHandler()
		{
		    @Override
		    public void onDrop(DropEvent event)
		    {
		    }
		}, DropEvent.getType());
	}

	CustomTree(){
		super();
	}
	
	public boolean isKeyboardNavigationEnabled(TreeItem currentItem){
		return false;
	}
	
	public void onBrowserEvent(Event event) {
		    int eventType = DOM.eventGetType(event);
		 // We must call super for all handlers.
		    switch (eventType) {
		      case Event.ONCLICK: {
		  	    super.onBrowserEvent(event);
		        break;
		      }

		      case Event.ONMOUSEDOWN: 
		  	    super.onBrowserEvent(event);
		        break;
		      }
	}
}

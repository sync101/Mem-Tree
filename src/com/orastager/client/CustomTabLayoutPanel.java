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
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.dom.client.DragLeaveEvent;
import com.google.gwt.event.dom.client.DragLeaveHandler;
import com.google.gwt.event.dom.client.DragOverEvent;
import com.google.gwt.event.dom.client.DragOverHandler;
import com.google.gwt.event.dom.client.DropEvent;
import com.google.gwt.event.dom.client.DropHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;

class CustomTabLayoutPanel extends com.google.gwt.user.client.ui.TabLayoutPanel{
	CustomTabLayoutPanel(){
		super(2, Unit.EM);
		sinkEvents(Event.ONMOUSEDOWN | Event.ONDBLCLICK | Event.ONCONTEXTMENU | Event.ONKEYDOWN | Event.ONCLICK);

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
	
	public void onBrowserEvent(Event event) {
		  switch (DOM.eventGetType(event)) {
		    case Event.ONCONTEXTMENU:
		      super.onBrowserEvent(event);
		      break;
		    default:
		      super.onBrowserEvent(event);
		      break; // Do nothing
		  }//end switch
		}

	public HandlerRegistration addDoubleClickHandler(DoubleClickHandler handler) {
		return addHandler(handler, DoubleClickEvent.getType());
	} 

	public HandlerRegistration addKeyDownHandler(KeyDownHandler handler) {
		return addHandler(handler, KeyDownEvent.getType());
	} 

}

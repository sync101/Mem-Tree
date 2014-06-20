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

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.FlowPanel;

public class TabHorPanel extends FlowPanel{

	TabHorPanel(){
		super();
		sinkEvents(Event.ONMOUSEDOWN | Event.ONDBLCLICK | Event.ONCONTEXTMENU | Event.ONKEYDOWN | Event.ONCLICK);
	}
	
	public void onBrowserEvent(Event event) {
		  switch (DOM.eventGetType(event)) {
		    case Event.ONCONTEXTMENU:
			  event.stopPropagation();
			  event.preventDefault();
		      break;
		    default:
		      super.onBrowserEvent(event);
		      break; // Do nothing
		  }//end switch
	}	
}

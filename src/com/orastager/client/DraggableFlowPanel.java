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

import gwtquery.plugins.draggable.client.DraggableOptions.RevertOption;
import gwtquery.plugins.draggable.client.events.DragStartEvent;
import gwtquery.plugins.draggable.client.events.DragStartEvent.DragStartEventHandler;
import gwtquery.plugins.draggable.client.gwt.DraggableWidget;

import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.FlowPanel;

public class DraggableFlowPanel extends DraggableWidget<FlowPanel>{
	FlowPanel fp;
	public DraggableFlowPanel(){
		fp = new FlowPanel();
		initWidget(fp);
		
		fp.getElement().getStyle().setCursor(Cursor.POINTER);
		//fp.setPixelSize(220, 18);
		//fp.getElement().getStyle().setPaddingLeft(7, Unit.PX);
		setDraggingCursor(Cursor.MOVE);
	    setDraggingOpacity((float)0.8);
	    setRevert(RevertOption.ON_INVALID_DROP);
	    useCloneAsHelper();

	    if (DmaUtil.requestFromMobile())
	    	setDisabledDrag(true);
	    
	    addDragStartHandler(new DragStartEventHandler() {

	        public void onDragStart(DragStartEvent event) {
	          //DraggableWidget<FlowPanel> dragged = (DraggableWidget<FlowPanel>) event.getDraggableWidget();
	          //Element helper = event.getHelper();
	          //helper.setInnerHTML(StaticVar.locateNode.get(dragged).text);
	        	KeyWordLabel.drag = true;
	        }
	    });
	}	
}

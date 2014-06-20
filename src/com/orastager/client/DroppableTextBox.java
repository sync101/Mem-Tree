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

import gwtquery.plugins.draggable.client.gwt.DraggableWidget;
import gwtquery.plugins.droppable.client.DroppableOptions.AcceptFunction;
import gwtquery.plugins.droppable.client.DroppableOptions.DroppableTolerance;
import gwtquery.plugins.droppable.client.events.DragAndDropContext;
import gwtquery.plugins.droppable.client.events.DropEvent;
import gwtquery.plugins.droppable.client.events.DropEvent.DropEventHandler;
import gwtquery.plugins.droppable.client.events.OutDroppableEvent;
import gwtquery.plugins.droppable.client.events.OutDroppableEvent.OutDroppableEventHandler;
import gwtquery.plugins.droppable.client.events.OverDroppableEvent;
import gwtquery.plugins.droppable.client.events.OverDroppableEvent.OverDroppableEventHandler;
import gwtquery.plugins.droppable.client.gwt.DroppableWidget;

import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.FlowPanel;

public class DroppableTextBox extends DroppableWidget<KeyWordLabel>{

	static Node dropping;
	static boolean expandTimerSet = false;
	static Timer expandTimer = new Timer() {  
  	    @Override
  	    public void run() {
  	      if (dropping != null){
  	    	  if (dropping.ti.getChildCount() > 0) {
  	    		  dropping.ti.setState(true);
  	    	  }
  	      }
  	    }
	};
	
	DroppableTextBox(){
		initWidget(new KeyWordLabel());
		setTolerance(DroppableTolerance.POINTER);
		//setDroppableHoverClass(Resource.INSTANCE.css().droppableHover());		
		//setDraggableHoverClass(Resource.INSTANCE.css().droppableHover());
		//setActiveClass(Resource.INSTANCE.css().droppableHover());
		
		setAccept(new AcceptFunction() {
		      
		    public boolean acceptDrop(DragAndDropContext context) {
		      DraggableWidget<?> draggabelWidget = context.getDraggableWidget();
		        //accept only draggable Label
		      return draggabelWidget.getOriginalWidget() instanceof CustomImage || draggabelWidget.getOriginalWidget() instanceof FlowPanel;
            }
		    
		});
		
		addOverDroppableHandler(new OverDroppableEventHandler() {
			@Override
			public void onOverDroppable(OverDroppableEvent event) {
				// TODO Auto-generated method stub
				DroppableWidget<KeyWordLabel> tb = (DroppableWidget<KeyWordLabel>)event.getDroppableWidget();
				/*dropping = StaticVar.locateTextBoxNode.get(tb.getOriginalWidget());
				if (expandTimerSet == false && dropping.ti.getChildCount() > 0){
		              expandTimer.schedule(1200);
		              expandTimerSet = true;
	            }*/
				tb.getOriginalWidget().getElement().getStyle().setBorderColor("#6699cc");
				tb.getOriginalWidget().getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
				tb.getOriginalWidget().getElement().getStyle().setBorderWidth(1, Unit.PX);
			}
		});
		
		addOutDroppableHandler(new OutDroppableEventHandler() {
			@Override
			public void onOutDroppable(OutDroppableEvent event) {
				// TODO Auto-generated method stub
				DroppableWidget<KeyWordLabel> tb = (DroppableWidget<KeyWordLabel>)event.getDroppableWidget();
				tb.getOriginalWidget().getElement().getStyle().setBorderStyle(BorderStyle.NONE);
				//expandTimerSet = false;
				//expandTimer.cancel();
				//dropping = null;
			}
		});
		
		addDropHandler(new DropEventHandler() {
		      
		      public void onDrop(DropEvent event) {
		        DroppableWidget<KeyWordLabel> tb = (DroppableWidget<KeyWordLabel>)event.getDroppableWidget();
		        DraggableWidget<?> dragged = event.getDraggableWidget();
		        
		        //expandTimerSet = false;
				//expandTimer.cancel();
				//dropping = null;
		        
		        //DmaUtil.setCurrNode(StaticVar.locateTextBoxNode.get(tb.getOriginalWidget()));
		        if (dragged.getOriginalWidget() instanceof CustomImage){
		        	StaticVar.auto_open = true;
		        	DmaUtil.setCurrNode(StaticVar.locateTextBoxNode.get(tb.getOriginalWidget()));
			        ((CustomImage)dragged.getOriginalWidget()).command.execute();
		        }
		        else if (dragged.getOriginalWidget() instanceof FlowPanel) // && StaticVar.locateNode.get(dragged).equals(StaticVar.root_node) == false
		        	DmaUtil.moveNode(StaticVar.locateTextBoxNode.get(tb.getOriginalWidget()), StaticVar.locateNode.get(dragged));
		        
		        tb.getOriginalWidget().getElement().getStyle().setBorderStyle(BorderStyle.NONE);
		        
		        //tb.getElement().getStyle().setBackgroundColor("#C1DEFD");
		       }
		});
	}
	
	
		
}

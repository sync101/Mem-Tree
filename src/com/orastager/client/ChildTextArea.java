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

import com.google.gwt.animation.client.Animation;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;

class ChildTextArea extends com.google.gwt.user.client.ui.TextArea{
	private ChildTextArea resizedWidget;
	
	private boolean isVisible = false;

	private ResizeWidthAnimation animation = new ResizeWidthAnimation();

    private Timer hideWidgetTimer = new Timer() {
        public void run() {
        	resizedWidget.setVisible(false);
        	hideWidgetTimer.cancel();
        }
      };

	ChildTextArea(){
		super();
		resizedWidget = this;
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

    private class ResizeWidthAnimation  extends Animation {
        // initial size of widget
        private int startWidth = 0;
        // desired size of widget. Widget will have this size after animation will stop to run
        private int desiredWidth = 0;
        // Right or Left Movement
        
       public void setWidth(int startWidth, int desiredWidth){
            this.startWidth = startWidth;
            this.desiredWidth = desiredWidth;
        }
       
        @Override
        protected void onUpdate(double progress) {
        	//Window.alert(""+progress);
            double width = startWidth - (startWidth - desiredWidth) * progress;
            resizedWidget.setWidth( width + Unit.PX.getType());
        }
    }
    
    public void show(int width){
    	isVisible = true;
    	resizedWidget.setVisible(true);
    	animation.setWidth(0, width);
    	animation.run(600);
    }

    public void hide(int width){
    	isVisible = false;
    	animation.setWidth(width, 0);
    	animation.run(600);
    	hideWidgetTimer.schedule(600);
    }

    public boolean isVisible(){
    	return isVisible;
    }
}

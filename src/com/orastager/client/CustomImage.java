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
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DragEndEvent;
import com.google.gwt.event.dom.client.DragEndHandler;
import com.google.gwt.event.dom.client.DragStartEvent;
import com.google.gwt.event.dom.client.DragStartHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Image;

class CustomImage extends Image{

	private CustomImage resizedWidget;
	
	private boolean isVisible = false;

	private ResizeWidthAnimation animation = new ResizeWidthAnimation();
	
	public String userId, color, sessionId;

    private Timer hideWidgetTimer = new Timer() {
        public void run() {
        	resizedWidget.setVisible(false);
        	hideWidgetTimer.cancel();
        }
    };
	
	CustomImage (ImageResource resource){
		super(resource);
		resizedWidget = this;
		//initDnD();
	}
	
	CustomImage (String url){
		super(url);
		resizedWidget = this;
	}
	
	CustomImage (String url, int width, int height){
		super(url);
		resizedWidget = this;
		this.setPixelSize(width, height);
	}
	
	Command command;

	CustomImage(ImageResource resource, final Command command, boolean clickAllowed){
		super(resource);
		resizedWidget = this;
		this.command = command;
		addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				 //TODO Auto-generated method stub
				 //right click on nodes
				DMA.showMsg("Drag and drop into the node where the action needs to be performed.");
			}
		});
	}

	CustomImage(ImageResource resource, final Command command){
		super(resource);
		resizedWidget = this;
		this.command = command;
		addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				//TODO Auto-generated method stub
				//right click on nodes
				StaticVar.auto_open = true;
				command.execute();
			}
		});
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
    
    public void setPosition(int left, int top) {
        // Account for the difference between absolute position and the
        // body's positioning context.
        left -= Document.get().getBodyOffsetLeft();
        top -= Document.get().getBodyOffsetTop();

        // Set the popup's position manually, allowing setPopupPosition() to be
        // called before show() is called (so a popup can be positioned without it
        // 'jumping' on the screen).
        Element elem = getElement();
        elem.getStyle().setPropertyPx("left", left);
        elem.getStyle().setPropertyPx("top", top);
    }
}
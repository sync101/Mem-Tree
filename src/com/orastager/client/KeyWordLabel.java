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
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.impl.FocusImpl;

public class KeyWordLabel extends Label {
	static KeyWordLabel currLabel;
	static Boolean drag = false;
	static CustomImage editIcon = new CustomImage(StaticVar.menuResources.pencil());
	//static HTML arrowHtml = new HTML("<div class=\"popup-arrow-border\"></div><div class=\"popup-arrow\"></div>");
	static TextBox textBox = new TextBox();
	
	static {
		//RootPanel.get("FullPane").add(editIcon);
		//editIcon.setVisible(false);
		//editIcon.getElement().getStyle().setPosition(Position.ABSOLUTE);
		//editIcon.getElement().getStyle().setZIndex(9);
		editIcon.setTitle("edit");
		editIcon.addMouseDownHandler(new MouseDownHandler() {
			@Override
			public void onMouseDown(MouseDownEvent event) {
				// TODO Auto-generated method stub
			  if (event.getNativeButton() == Event.BUTTON_LEFT){	
				event.stopPropagation();
				event.preventDefault();
				currLabel.hideEditIcon();
				currLabel.showPopUp();
			  }
			}
		});
		
		textBox.getElement().setId("nodeEditTextBox");
		textBox.setTitle("Enter to submit. Escape to hide");
		textBox.addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				// TODO Auto-generated method stub
				textBox.setWidth(DmaUtil.getNodeTextBoxSize(textBox.getText())+"px");
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER){
					event.preventDefault();
					event.stopPropagation();
					currLabel.setText(textBox.getText());
					textBox.setVisible(false);
					Timers.delayedNodeFocus(StaticVar.currNode, false);
					//currLabel.setFocus(true);
				}
				if (event.getNativeKeyCode() == KeyCodes.KEY_ESCAPE){
					event.preventDefault();
					event.stopPropagation();
					textBox.setVisible(false);
					Timers.delayedNodeFocus(StaticVar.currNode, false);
					//currLabel.setFocus(true);
				}
			}
		});
		
		textBox.addBlurHandler(new BlurHandler() {			
			@Override
			public void onBlur(BlurEvent event) {
				// TODO Auto-generated method stub
				textBox.setVisible(false);
			}
		});
	}
	
	private KeyWordLabel resizedWidget;
	
	private boolean isVisible = false;
	private boolean isReadonly = false;

	private ResizeWidthAnimation animation = new ResizeWidthAnimation();

    private Timer hideWidgetTimer = new Timer() {
        public void run() {
        	resizedWidget.setVisible(false);
        	hideWidgetTimer.cancel();
        }
      };

	KeyWordLabel(String str){
		super();
		setText(str);
		init();
	}
	
	KeyWordLabel(){
		super();
		init();
	}
	
	void init(){
		resizedWidget = this;
		sinkEvents(Event.ONMOUSEDOWN | Event.ONDBLCLICK | Event.ONCONTEXTMENU | Event.ONKEYDOWN | Event.ONKEYUP | Event.ONCLICK | Event.ONFOCUS | Event.ONBLUR);
		getElement().setTabIndex(0);
	}
	
	@Override
	public void setText(String text){
		super.setText(text);
		StaticVar.locateTextBoxNode.get(this).text = text;
		DmaUtil.setNodeTextBoxSize(this);
	}
	
	public void setFocus(boolean focus){
		if (focus)
			FocusImpl.getFocusImplForWidget().focus(getElement());
		else
			FocusImpl.getFocusImplForWidget().blur(getElement());
	}
	
	public void setCurrent(){
		currLabel = this;
	}
	
	public void removeCurrent(){
		currLabel = null;
	}
	
	public void onBrowserEvent(Event event) {
		  switch (DOM.eventGetType(event)) {
		    case Event.ONCONTEXTMENU:
			  event.preventDefault();
			  event.stopPropagation();
		      setCurrent();
		      setFocus(true);
		      break;
		    case Event.ONCLICK:
		      event.preventDefault();
		      event.stopPropagation();
			  setCurrent();
			  setFocus(true);
			  if (drag == false && isReadonly == false)
				  showEditIcon();
			  break;
		    case Event.ONDBLCLICK:
		      event.preventDefault();
		      event.stopPropagation();
			  setCurrent();
			  setFocus(true);
		      //showPopUp();
		      break;
		    case Event.ONMOUSEDOWN:
		      drag = false;
			  break;
		    case Event.ONKEYUP:
		     if(event.getKeyCode() == KeyCodes.KEY_ENTER && isReadonly == false)	
				  showPopUp();
			  break;
		    case Event.ONFOCUS:
			  setCurrent();
		      break;
		    case Event.ONBLUR:
			  hideEditIcon();
		      //removeCurrent(); //keep it commented to avoid focus issue
		      break;
		  }//end switch
	      super.onBrowserEvent(event);
	}
	
	private void showEditIcon(){
		//editIcon.setPosition(currLabel.getAbsoluteLeft()+currLabel.getElement().getOffsetWidth()+2, currLabel.getAbsoluteTop());
		StaticVar.locateTextBoxNode.get(this).horPanel.add(editIcon);
		editIcon.show(16);
	}
	
	private void hideEditIcon(){
		editIcon.setVisible(false);
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
    
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<?> handler) {
    	// Initialization code
        return addHandler(handler, ValueChangeEvent.getType());
    }
    
    public HandlerRegistration addMouseDownHandler(MouseDownHandler handler){
    	// Initialization code
        return addHandler(handler, MouseDownEvent.getType());
    }
    
    public HandlerRegistration addClickHandler(ClickHandler handler){
    	// Initialization code
        return addHandler(handler, ClickEvent.getType());
    }

    public HandlerRegistration addKeyDownHandler(KeyDownHandler handler){
    	// Initialization code
        return addHandler(handler, KeyDownEvent.getType());
    }

    public HandlerRegistration addKeyUpHandler(KeyUpHandler handler){
    	// Initialization code
        return addHandler(handler, KeyUpEvent.getType());
    }
    
    public HandlerRegistration addFocusHandler(FocusHandler handler){
    	// Initialization code
        return addHandler(handler, FocusEvent.getType());
    }

    public HandlerRegistration addBlurHandler(BlurHandler handler){
    	// Initialization code
        return addHandler(handler, BlurEvent.getType());
    }

	public void showPopUp() {
		// TODO Auto-generated method stub
		setCurrent();
		textBox.setText(currLabel.getText());
		textBox.setVisible(true);
		StaticVar.locateTextBoxNode.get(this).horPanel.add(textBox);
		textBox.setWidth(DmaUtil.getNodeTextBoxSize(textBox.getText())+"px");
		Timers.delayedTextBoxFocus(textBox);
	}
	
	public void setReadOnly(Boolean readOnly){
		isReadonly = readOnly;
	}
}

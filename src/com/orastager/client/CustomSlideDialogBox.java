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

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class CustomSlideDialogBox extends PopupPanel {
	
	private ArrayList<String> slideList = new ArrayList<String>();
	private HorizontalPanel horPanel = new HorizontalPanel();
	private Image imgLeft = new Image(StaticVar.menuResources.left());
	private Image imgRight = new Image(StaticVar.menuResources.right());
	private Image imgClose = new Image(StaticVar.menuResources.close());
	private Frame frame = new Frame("");
	private int currentImage;
	private int width, height;
	
	void setCurrentImage(int x){
		currentImage = x;
	}

	public CustomSlideDialogBox(int x, int y) {
		super(false);
		setAnimationEnabled(true);
		setGlassEnabled(true);

        //setPixelSize(x, y);
        width = x;
        height = y;
        
        //Image imgClosePanel = new Image(StaticVar.menuResources.close());
        horPanel.add(imgLeft);
        imgLeft.setTitle("Next.. (Esc to close)");
        horPanel.add(frame);
        imgRight.setTitle("Prev.. (Esc to close)");
        horPanel.add(imgRight);
        imgClose.setTitle("Close..");
        horPanel.add(imgClose);
        setWidget(horPanel);
		//setStyleName("gwt-SlideDialogBox");
	    currentImage = 0;
	    frame.getElement().getStyle().setBackgroundColor("white");
	    
	    imgLeft.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				navLeft();
			}
		});
	    
	    imgRight.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				navRight();
			}
		});
	    
	    imgClose.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				hide();
			}
		});
	}
	
	public void setSlides(ArrayList<String> listInput){
		slideList = listInput;
	    currentImage = 0;
	}
	
	public void openImg(String slide){
		 frame.setUrl(slide);
		 frame.setPixelSize(width - 20, height);
  		 horPanel.remove(1);
		 horPanel.insert(frame, 1);
	}
	
	public void navRight(){
    	if (slideList.size() > 1){

    		currentImage++;
    		if (currentImage == slideList.size())
    			currentImage = 0;

    			openImg(slideList.get(currentImage));
   		    //scrPanel.setPixelSize(slideList.get( (currentImage+1) % slideList.size() ).getWidth()<Window.getClientWidth()-200?slideList.get( (currentImage+1) % slideList.size() ).getWidth():Window.getClientWidth()-200, 
   		    		//slideList.get( (currentImage+1) % slideList.size() ).getHeight()<Window.getClientHeight()-100?slideList.get( (currentImage+1) % slideList.size() ).getHeight():Window.getClientHeight()-100);
    	}
	}
	
	public void navLeft(){
    	if (slideList.size() > 1){
    		currentImage--;

    		if (currentImage == -1)
    			currentImage = slideList.size()-1;

        		openImg(slideList.get(currentImage));
   		    //scrPanel.setPixelSize(imgList.get( (currentImage-1) % imgList.size() ).getWidth()<Window.getClientWidth()-200?imgList.get( (currentImage-1) % imgList.size() ).getWidth():Window.getClientWidth()-200, 
   		    		//imgList.get( (currentImage-1) % imgList.size() ).getHeight()<Window.getClientHeight()-100?imgList.get( (currentImage-1) % imgList.size() ).getHeight():Window.getClientHeight()-100);
    	}		
	}
	
	@Override
    protected void onPreviewNativeEvent(NativePreviewEvent event) {
        super.onPreviewNativeEvent(event);
        switch (event.getTypeInt()) {
            case Event.ONKEYDOWN:
                if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ESCAPE) {
                    hide();
                }
                else if(event.getNativeEvent().getKeyCode() == KeyCodes.KEY_RIGHT) {
                	navRight();
                }
                else if(event.getNativeEvent().getKeyCode() == KeyCodes.KEY_LEFT) {
                	navLeft();
                }
                /*else if(event.getNativeEvent().getKeyCode() == KeyCodes.KEY_UP) {
                	scrPanel.setVerticalScrollPosition(scrPanel.getVerticalScrollPosition()-50);
                }
                else if(event.getNativeEvent().getKeyCode() == KeyCodes.KEY_DOWN) {
                	scrPanel.setVerticalScrollPosition(scrPanel.getVerticalScrollPosition()+50);
                }*/
                break;
        }
    }
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		if (slideList.size() > 0)
		 super.show();
		else
		 DMA.showMsg("No slides to show");
	}
}

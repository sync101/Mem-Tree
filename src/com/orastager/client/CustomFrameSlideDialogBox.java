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
import java.util.HashMap;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;

public class CustomFrameSlideDialogBox extends PopupPanel{
	private ArrayList<Node> slideList = new ArrayList<Node>();
	private HashMap<Node, Frame> map = new HashMap<Node, Frame>();
	private HorizontalPanel horPanel = new HorizontalPanel();
	private Image imgLeft = new Image(StaticVar.menuResources.left());
	private Image imgRight = new Image(StaticVar.menuResources.right());
	private int currentImage;
	private int width, height;
	
	void setCurrentImage(int x){
		currentImage = x;
	}

	public CustomFrameSlideDialogBox(int x, int y) {
		super(false);
		setAnimationEnabled(true);
		setGlassEnabled(true);

        //setPixelSize(x, y);
        width = x;
        height = y;
        
        horPanel.add(imgLeft);
        horPanel.add(new Frame(""));
        horPanel.add(imgRight);
        setWidget(horPanel);
		//setStyleName("gwt-SlideDialogBox");
	    currentImage = 0;

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
	}
	
	public void setSlides(ArrayList<Node> listInput){
		slideList = listInput;
		map.clear();
	    currentImage = 0;
	}
	
	public void openSlide(Node slide){
		if (map.get(slide) == null){
		 slideList.remove(slide);
		 slideList.add(slide);
		 Frame newFrame;
		 if (slide.text.contains("youtube"))
			 newFrame = new Frame(JSFunctions.getVideoId(slide.text));
		 else
			 newFrame = new Frame(slide.text);
		 newFrame.setTitle(StaticVar.locate.get(slide.ti.getParentItem()).text);
		 newFrame.setPixelSize(width - 20, height);
		 newFrame.getElement().getStyle().setBackgroundColor("white");
		 map.put(slide, newFrame);
  		 horPanel.remove(1);
		 horPanel.insert(map.get(slide), 1);
		}else{
			horPanel.remove(1);
			map.get(slide).setPixelSize(width - 20, height);
			horPanel.insert(map.get(slide), 1);
		}
		/*int i = 0;
		for(String str:slideList){
			if (slide.equals(str)){
				currentImage = i;
				break;
			}
			else i++;
		}*/
	}
	
	public void navRight(){
    	if (slideList.size() > 1){

    		currentImage++;
    		if (currentImage == slideList.size())
    			currentImage = 0;

    		    openSlide(slideList.get(currentImage));
   		    //scrPanel.setPixelSize(slideList.get( (currentImage+1) % slideList.size() ).getWidth()<Window.getClientWidth()-200?slideList.get( (currentImage+1) % slideList.size() ).getWidth():Window.getClientWidth()-200, 
   		    		//slideList.get( (currentImage+1) % slideList.size() ).getHeight()<Window.getClientHeight()-100?slideList.get( (currentImage+1) % slideList.size() ).getHeight():Window.getClientHeight()-100);
    	}
	}
	
	public void navLeft(){
    	if (slideList.size() > 1){
    		currentImage--;

    		if (currentImage == -1)
    			currentImage = slideList.size()-1;

        		openSlide(slideList.get(currentImage));
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

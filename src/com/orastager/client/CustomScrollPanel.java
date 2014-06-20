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
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

class CustomScrollPanel extends ScrollPanel{

	CustomScrollPanel(Widget w){
		super(w);
		setStyleName("gwt-ScrollBar");
	}
	
	private ResizeWidthAnimation animation = new ResizeWidthAnimation();
	private int moveTo;
	private int initialPos;
	private double moveBy;
	
    private class ResizeWidthAnimation  extends Animation {
        @Override
        protected void onUpdate(double progress) {
        	//Window.alert(""+progress);
        	if (initialPos < moveTo){
        		moveBy = (moveTo - initialPos) * progress;
        		setVerticalScrollPosition(initialPos + (int) moveBy);
        	}
        	else if (initialPos > moveTo){
        		moveBy = (initialPos - moveTo) * progress;
        		setVerticalScrollPosition(initialPos - (int) moveBy);
        	}        	
        }
    }

    void setVertScrollPosition(int treeHeight, int scrPanelHeight, int widgetPosition){
		//setVerticalScrollPosition((int) (((widgetPosition)*scrPanelHeight)/treeHeight));
    	initialPos = getVerticalScrollPosition();
    	//DMA.showMsg(""+widgetPosition+", "+treeHeight+", "+((widgetPosition)-((int) ((517*517)/treeHeight))));
    	if (((widgetPosition)-((int) ((scrPanelHeight*scrPanelHeight)/treeHeight))) > 0)
    	 moveTo = (widgetPosition)-((int) ((scrPanelHeight*scrPanelHeight)/treeHeight));
    	else moveTo = 0;
    	animation.run(1200);
		//setVerticalScrollPosition((widgetPosition)-((int) ((517*517)/treeHeight)));
		//Window.alert(DMA.dmaTree.getOffsetHeight()+", "+DMA.treeScroller.getOffsetHeight()+", "+(widgetPosition)+", "+DMA.treeScroller.getVerticalScrollPosition());
	}
}

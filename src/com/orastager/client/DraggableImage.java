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
import gwtquery.plugins.draggable.client.gwt.DraggableWidget;

import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Command;

public class DraggableImage extends DraggableWidget<CustomImage>{
	
	public DraggableImage(ImageResource resource, final Command command, boolean clickAllowed) {
	    //as DraggableWidget is a composite call initWidget() method to setup your widget
		CustomImage img;
		if(clickAllowed)
			img = new CustomImage(resource, command);
		else
			img = new CustomImage(resource, command, false);
	    initWidget(img);
	    
	    //configure the drag behavior (see next paragraphs)
	    img.getElement().getStyle().setCursor(Cursor.MOVE);
	    setDraggingCursor(Cursor.MOVE);
	    setDraggingOpacity((float)0.8);
	    setRevert(RevertOption.ON_INVALID_DROP);
	    useCloneAsHelper();
	}
}

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

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.ui.PopupPanel;

public class CustomNodeMovePopupPanel extends PopupPanel{
	CustomNodeMovePopupPanel(){
		super(false);
		setGlassEnabled(true);
		setAnimationEnabled(true);
	}
	
	/*CustomPopupPanel(boolean autoHide){
		super(autoHide);
		setGlassEnabled(true);
		setAnimationEnabled(true);
	}*/
	
	@Override
    protected void onPreviewNativeEvent(NativePreviewEvent event) {
        super.onPreviewNativeEvent(event);
        switch (event.getTypeInt()) {
            case Event.ONKEYDOWN:
                if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ESCAPE) {
                    hide();
                }else if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER){
                  if (StaticVar.suggestBoxFocused){
                	  StaticVar.suggestBoxFocused = false;
                  }
                  else{
                	DmaUtil.appendNode(StaticVar.moveNodeTree.getSelectedItem());
                	hide();
                  }
                }
                break;
        }
    }
}

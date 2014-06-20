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

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

public class CustomTag extends HorizontalPanel{
  
  Label label = new Label();
  
  private CustomTag(){
  }
  
  public CustomTag(String str, boolean closable){
	  label.setText(str);
	  this.setStyleName("hashTag");
	  Image imgClose = new Image(StaticVar.menuResources.closeTab());
	  imgClose.setStyleName("gwt-dmaImage");
	  imgClose.setTitle("close tab");
	  this.add(label);
	  if (closable){
		  label.getElement().getStyle().setMarginRight(5, Unit.PX);
		  this.add(imgClose);
	  }
	  imgClose.addClickHandler(new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			CustomTag.this.getElement().removeFromParent();
		}
	  });
  }
}

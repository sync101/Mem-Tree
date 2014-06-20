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

import java.util.Date;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;

//Begin Class Node
public class Node extends Composite{

    public DroppableTextBox nodeTextBox = new DroppableTextBox();
	public ChildTextArea nodeTextArea = new ChildTextArea();
	
	public CustomTreeItem ti;
	
	//Key values
	public String type;
	public String text;
	
	public Date creation_date;

	// HorizontalPanel widget
	public HorizontalPanel horPanel = new HorizontalPanel();
	public DraggableFlowPanel dragPanel = new DraggableFlowPanel();

	//Other columns
	public String attr1; //Type of child text
	public String attr2; //Type of code editor
	public String attr3; //Mark red/green 
	public String attr4; //No of times opened so far
	public String attr5;
	public String attr6;
	public String attr7;
	public String attr8;
    
	//Menu code
	public Node(String nodeType) {
		type = nodeType;
	}
	
    public Node(String node_type, String node_str, String node_cr_date, String node_attr1, String node_attr2, String node_attr3, String node_attr4, String node_attr5, String node_attr6, String node_attr7, String node_attr8, Boolean addToOracle) {

	    //subMenu.setAutoOpen(true);

		text = node_str.replace("Ora_sq","\'").replace("Ora_dq","\"").replace("Ora_nl","\n").replace("Ora_amb","&").replace("Ora_la","<").replace("Ora_ra",">");
		
		attr1 = node_attr1;
		attr2 = node_attr2;
		attr3 = node_attr3;
		attr4 = node_attr4;
		attr5 = node_attr5;
		attr6 = node_attr6;
		attr7 = node_attr7;
		attr8 = node_attr8;
		
		// HorizontalPanel widget
		initWidget(horPanel);
		
    	try{
			creation_date = StaticVar.dateFormatter.parse(node_cr_date);
		}catch(Exception e){
			creation_date = new Date();
		}
		//last_update_date = new Date();
		//nodeTextBox.setTitle("Creation Date: "+ creation_date+"\n------------\n"+text);
		//nodeTextArea.setTitle("Creation Date: "+ creation_date+"\n------------\n"+text);
		
	 if (node_type.equalsIgnoreCase("Child Text")) {
		type = "ChildText";
		nodeTextArea.setStyleName("gwt-DmaTextArea");
	    if (text.length()>40)
			nodeTextArea.setText(text.substring(0, 40)+"....");
	    else
	    	nodeTextArea.setText(text);
		nodeTextArea.setTitle("Right click: Menu, Double Click: Open, Click: Select, Mouse Over: Preview");
		nodeTextArea.setReadOnly(true);
		
		horPanel.getElement().getStyle().setPaddingLeft(30, Unit.PX);
		horPanel.add(nodeTextArea);
		DmaUtil.addChildTextIcon(this);

		Events.addChildTextEvents(this);
	  }
	  else if (node_type.equalsIgnoreCase("Key Word")) {
			type = "KeyWord";
			StaticVar.locateTextBoxNode.put(nodeTextBox.getOriginalWidget(), this);
			nodeTextBox.getOriginalWidget().setText(text);
			nodeTextBox.setStyleName("gwt-DmaTextBox");
			nodeTextBox.setTitle("Right click: Menu, Double Click: Expand/Collapse, Click: Select/Edit, Enter: Edit");
			
			if (text.equalsIgnoreCase("Key Word") == false)
				nodeTextBox.setTitle(text);

	        if (addToOracle)
			  StaticVar.oracle.add(this);
	        
			// HorizontalPanel widget			
			dragPanel.getOriginalWidget().add(nodeTextBox);
			StaticVar.locateNode.put(dragPanel, this);

			if (attr3.equalsIgnoreCase("red")){
				horPanel.add(new Image(StaticVar.menuResources.redFlag()));
			}else if (attr3.equalsIgnoreCase("green")){
				horPanel.add(new Image(StaticVar.menuResources.greenFlag()));
			}

			horPanel.add(dragPanel);
			Events.addKeyWordEvents(this);
		}
		else if (node_type.equalsIgnoreCase("Root")) {
			type = "Root";
			StaticVar.locateTextBoxNode.put(nodeTextBox.getOriginalWidget(), this);
			nodeTextBox.getOriginalWidget().setText(text);
			nodeTextBox.getOriginalWidget().setReadOnly(true);
			nodeTextBox.setStyleName("gwt-DmaTextBox");
			nodeTextBox.setTitle("Right click: Menu, Click: Select");
			
	        // HorizontalPanel widget
			StaticVar.locateNode.put(dragPanel, this);
			horPanel.add(nodeTextBox);
			
			Events.addRootKeyWordEvents(this);
		}
	}// End Node()
	

    public String toString() {
	   return "<Node type= \""+type+"\" text= \""+text.replace("\'","Ora_sq").replace("\"", "Ora_dq").replace("\n", "Ora_nl").replace("&","Ora_amb").replace("<", "Ora_la").replace(">", "Ora_ra")+"\" creation_date= \""+StaticVar.dateFormatter.format(creation_date)+"\" attr1= \""+attr1+"\" attr2= \""+attr2+"\" attr3= \""+attr3+"\" attr4= \""+attr4+"\" attr5= \""+attr5+"\" attr6= \""+attr6+"\" attr7= \""+attr7+"\" attr8= \""+attr8+"\">";
    }
    
    public JSONObject toJson() {
    	JSONObject jsonObj = new JSONObject();
    	
    	jsonObj.put("tp", new JSONString(type));
    	jsonObj.put("tx", new JSONString(text));
    	jsonObj.put("cr", new JSONString(StaticVar.dateFormatter.format(creation_date)));
    	jsonObj.put("a1", new JSONString(attr1));
    	jsonObj.put("a2", new JSONString(attr2));
    	jsonObj.put("a3", new JSONString(attr3));
      	jsonObj.put("a4", new JSONString(attr4));
    	jsonObj.put("a5", new JSONString(attr5));
    	jsonObj.put("a6", new JSONString(attr6));
    	jsonObj.put("a7", new JSONString(attr7));
    	jsonObj.put("a8", new JSONString(attr8));
    	
    	if (type.equalsIgnoreCase("KeyWord"))
    		StaticVar.oracle.add(this);

    	return jsonObj;
    	/*return "{type:"+type+
    	       ",text:"+text+
    	       ",creation_date:"+StaticVar.dateFormatter.format(creation_date)+
    	       ",attr1:"+attr1+
    	       ",attr2:"+attr2+
    	       ",attr3:"+attr3+
    	       ",attr4:"+attr4+
    	       ",attr5:"+attr5+
    	       ",attr6:"+attr6+
    	       ",attr7:"+attr7+
    	       ",attr8:"+attr8+"}";*/	
    }
    
    public void setKeyWordFrequency(){
    	int frequency = 0;
    	int childFrequency = 0;
    	// get max frequency of child nodes
    	for (int i=0; i<ti.getChildCount(); i++){
    	  if(StaticVar.locate.get((CustomTreeItem) ti.getChild(i)).type.equalsIgnoreCase("ChildText")){
    		  try{
    			  childFrequency = Integer.parseInt(StaticVar.locate.get((CustomTreeItem) ti.getChild(i)).attr4);
			  }catch (Exception e){
				  childFrequency = 0;					  
			  }
			  if (frequency < childFrequency){
				  frequency = childFrequency;
			  }
    	  }
    	}
    	if (frequency > 0)
    	 attr4 = ""+frequency;
    }
    
    public void setFocus(){
    	if (isKeyWord())
    		nodeTextBox.getOriginalWidget().setFocus(true);
    	else
    		nodeTextArea.setFocus(true);
    }
    
    public boolean isKeyWord(){
    	if (type.equalsIgnoreCase("Root") || type.equalsIgnoreCase("KeyWord"))
    		return true;
    	else
    		return false;
    }
}// End Class Node
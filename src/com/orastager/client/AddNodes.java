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


public class AddNodes {
	// Add new Node, called from xml loader
	public static Node addNodeXML(Node node, String node_type, String node_text, String node_cr_date, String node_attr1, String node_attr2, String node_attr3){
	  Node child_node = null;
	  
	  if (node_attr1.equalsIgnoreCase(""))
	   node_attr1 = StaticVar.ChildTextTypes.type_text;
	  
	  if (node_type.equalsIgnoreCase("KeyWord"))	
       child_node = new Node("Key Word", node_text, node_cr_date, "", "", node_attr3, "", "", "", "", "", true);
	  else
	   child_node = new Node("Child Text", node_text, node_cr_date, node_attr1, node_attr2, "", "", "", "", "", "", true); 
	   
	    final CustomTreeItem ti_child = new CustomTreeItem(child_node);
    	//child_node.text = node_text;
	    node.ti.insertItem(0, ti_child);
    	//ti.addItem(ti_child);
    	child_node.ti = ti_child;
    	StaticVar.locate.put(ti_child, child_node);
    	//ti.setState(true);
	    return child_node;
	}

	// Add new Node, called from json loader
	public static Node addNodeJson(Node node, String node_type, String node_text, String node_cr_date, String node_attr1, String node_attr2, String node_attr3, String node_attr4){
	  Node child_node = null;
	  
	  if (node_attr1.equalsIgnoreCase(""))
	   node_attr1 = StaticVar.ChildTextTypes.type_text;
	  
	  if (node_type.equalsIgnoreCase("KeyWord"))	
       child_node = new Node("Key Word", node_text, node_cr_date, "", "", node_attr3, node_attr4, "", "", "", "", true);
	  else
	   child_node = new Node("Child Text", node_text, node_cr_date, node_attr1, node_attr2, "", node_attr4, "", "", "", "", true); 
	   
	    final CustomTreeItem ti_child = new CustomTreeItem(child_node);
    	//child_node.text = node_text;
	    node.ti.insertItem(node.ti.getChildCount(), ti_child);
    	//ti.addItem(ti_child);
    	child_node.ti = ti_child;
    	StaticVar.locate.put(ti_child, child_node);
    	//ti.setState(true);
	    return child_node;
	}
	
	// Add new Node, called from right click on nodes
	public static void addNodeChild(Node node, String node_type, String node_text, String node_cr_date, String node_attr1, String node_attr2){
	  Node child_node = null;
	  
	  if (node_attr1.equalsIgnoreCase(""))
	   node_attr1 = StaticVar.ChildTextTypes.type_text;
	  
	  if (node_type.equalsIgnoreCase("KeyWord"))	
       child_node = new Node("Key Word", node_text, node_cr_date, "", "", "", "", "", "", "", "", true);
	  else
	   child_node = new Node("Child Text", node_text, node_cr_date, node_attr1, node_attr2, "", "", "", "", "", "", true); 
	   
	    final CustomTreeItem ti_child = new CustomTreeItem(child_node);
	    node.ti.addItem(ti_child);
    	child_node.ti = ti_child;
    	StaticVar.locate.put(ti_child, child_node);

    	node.ti.setState(true);
    	Menus.popupPanel.hide();
    	Timers.setScrollPosition(ti_child.getElement().getOffsetTop());
    	DriveUtil.setFileModified();
    	child_node.nodeTextArea.show(StaticVar.nodeTextAreaWidth);
    	
    	if (StaticVar.auto_open && 
    			(DmaUtil.isTextNode(child_node) || DmaUtil.isCodeNode(child_node))){
    		StaticVar.auto_open = false;
    		Timers.openEditor(child_node);
    	}
    	//if (child_node.type.equalsIgnoreCase("Child Text") && DmaUtil.isTextNode(child_node) == false && node.text.equalsIgnoreCase("Key Word") == true)
    		//DriveUtil.readUrlTitle(node, child_node.text);
	}
}

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


public class XMLUtil {

	/*public static String printTree(CustomTreeItem ti){
		String s = "";
		if (StaticVar.locate.get(ti).type.equalsIgnoreCase("Root") == false){
			 s += StaticVar.locate.get(ti).toString();
			 if (StaticVar.locate.get(ti).type.equalsIgnoreCase("KeyWord"))
				  StaticVar.oracle.add(StaticVar.locate.get(ti).text);
		}
		for(int i=0; i<ti.getChildCount(); i++){
			if (StaticVar.locate.get(ti).type.equalsIgnoreCase("Root") == false)
				s += "\n";
			s += printTree((CustomTreeItem) ti.getChild(i));
		}
		if (StaticVar.locate.get(ti).type.equalsIgnoreCase("Root") == false)
	    	s += "\n" + "</Node>";
    	return s;
	}
	
	public static String printXML(){
		StaticVar.oracle.clear();
		return "<begin>"+printTree(StaticVar.root_ti)+StaticVar.taskView.printTasks()+"</begin>";
	}/*
	
		/*var node = new Object();
		var nodeList = new Array();
		node.type = n.@com.orastager.client.Node::type;
		node.text = n.@com.orastager.client.Node::type;
		//node.creation_date = n.@com.orastager.client.Node::creation_date;
		node.attr1 = n.@com.orastager.client.Node::attr1;
		node.attr2 = n.@com.orastager.client.Node::attr2;
		node.attr3 = n.@com.orastager.client.Node::attr3;
		node.attr4 = n.@com.orastager.client.Node::attr4;
		node.attr5 = n.@com.orastager.client.Node::attr5;
		node.attr6 = n.@com.orastager.client.Node::attr6;
		node.attr7 = n.@com.orastager.client.Node::attr7;
		node.attr8 = n.@com.orastager.client.Node::attr8;
		nodeList.push(node);
		node.list = nodeList;
		$wnd.alert(JSON.stringify(node));*/

}

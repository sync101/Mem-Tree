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

import java.util.Collections;
import java.util.Date;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;

public class JSONUtil {

	public static JSONArray buildTreeJson(Node node) {
		JSONArray arrayNodes = new JSONArray();
		arrayNodes.set(0, StaticVar.locate.get(node.ti).toJson());
		for (int i = 0; i < node.ti.getChildCount(); i++) {
		 arrayNodes.set(i+1, buildTreeJson(StaticVar.locate.get(node.ti.getChild(i))));
		}
		return arrayNodes;
	}

	public static String buildJSON(){
	 try
	 {
	  StaticVar.oracle.clear();
	  JSONArray jsonNodesArray = JSONUtil.buildTreeJson(StaticVar.root_node);

	  JSONArray jsonTasksArray = StaticVar.taskView.buildTasksJson();
	 
 	  JSONObject jsonObj = new JSONObject();
 	  jsonObj.put("Revision", new JSONString("1"));
	  jsonObj.put("Nodes", jsonNodesArray);
	  jsonObj.put("Tasks", jsonTasksArray);
	 
	  return jsonObj.toString();
	 }catch (Exception e){
		 DMA.showMsg("Error building json!");
		 return "";
	  }
	}

	
	public static void parseNodesJson(String json, Node node){
	  try
	  {
	    JSONValue value = JSONParser.parseStrict(json);
		JSONArray arrayNodes = value.isArray();
		Node parent_node = null;
		for (int i=0; i<arrayNodes.size();i++){
			if (i==0){
			  JSONObject jsonObj = arrayNodes.get(i).isObject();
			  if (jsonObj.get("tp").isString().stringValue().equalsIgnoreCase("root") == false){
				   parent_node = AddNodes.addNodeJson(node 
													 ,jsonObj.get("tp").isString().stringValue()
													 ,jsonObj.get("tx").isString().stringValue()
													 ,jsonObj.get("cr").isString().stringValue()
													 ,jsonObj.get("a1").isString().stringValue()
													 ,jsonObj.get("a2").isString().stringValue()
													 ,jsonObj.get("a3").isString().stringValue()
													 ,jsonObj.get("a4").isString().stringValue());
			  }
			  else
				parent_node = node;  
			}
			else{
				parseNodesJson(arrayNodes.get(i).toString(), parent_node);			  
			}
		}
	  }catch (Exception e){
			DMA.showMsg("Error loading nodes!");
	  }
	}
	
	public static void parseTasksJson(String json){
		try
		{
			JSONValue value = JSONParser.parseStrict(json);
			JSONArray arrayTasks = value.isArray();
			for (int i=0; i<arrayTasks.size();i++){
			  JSONObject jsonObj = arrayTasks.get(i).isObject();
			  String what = jsonObj.get("wt").isString().stringValue();
			  Date when;
			  try{
				when = StaticVar.dateFormatter.parse(jsonObj.get("wn").isString().stringValue());
			  }catch(Exception e){
				when = new Date();
			  } 
			  Task newTask = new Task(what, when);
			  StaticVar.taskView.list.add(newTask);
			  
			  if (newTask.when.compareTo(new Date()) < 0){
	 			DmaUtil.showNotification("Missed your task!", newTask.what);
	 		  }
			}
			
			Collections.sort(StaticVar.taskView.list, StaticVar.taskView.comparator);
			StaticVar.taskView.table.redraw();
		}catch (Exception e){
			DMA.showMsg("Error loading tasks!");
		}
	}

	public static void loadJson(String json) {
		try
		{
			// TODO Auto-generated method stub
			JSONValue value = JSONParser.parseStrict(json);
			JSONObject jsonObject = value.isObject();
			if (jsonObject.get("Revision") != null)
				StaticVar.revision = jsonObject.get("Revision").toString();
			parseNodesJson(jsonObject.get("Nodes").toString(), StaticVar.root_node);
			parseTasksJson(jsonObject.get("Tasks").toString());
		}catch (Exception e){
			DMA.showMsg("Error loading DMA file!");
		}
	}
}

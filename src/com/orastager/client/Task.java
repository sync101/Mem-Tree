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

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;

class Task{
	public String what; 
	public Date when;
	
	Task(){
		this.when = new Date();
		this.what = "New Task";
	}
	
	Task(String what, Date when){
		this.what = what;
		this.when = when;
	}

	public JSONValue toJson() {
		// TODO Auto-generated method stub
    	JSONObject jsonObj = new JSONObject();
    	jsonObj.put("wt", new JSONString(what));
    	jsonObj.put("wn", new JSONString(StaticVar.dateFormatter.format(when)));
    	return jsonObj;
	}
}

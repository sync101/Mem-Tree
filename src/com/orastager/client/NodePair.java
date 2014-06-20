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

public class NodePair {
	
	Node nodeKeyWord;
	Node nodeChildText;
	
	NodePair(String parent_text, String child_text){
		nodeKeyWord = new Node("Key Word", parent_text, StaticVar.dateFormatter.format(new Date()), "", "", "", "", "", "", "", "", false);
		nodeKeyWord.ti = new CustomTreeItem(nodeKeyWord);
		StaticVar.locate.put(nodeKeyWord.ti, nodeKeyWord);
		
		nodeChildText = new Node("Child Text", child_text, StaticVar.dateFormatter.format(new Date()), StaticVar.ChildTextTypes.type_text, "", "", "", "", "", "", "", false);
		nodeChildText.ti = new CustomTreeItem(nodeChildText);
		StaticVar.locate.put(nodeChildText.ti, nodeChildText);
		
		if (DmaUtil.isValidUrl(nodeChildText.text))
			nodeChildText.attr1 = StaticVar.ChildTextTypes.type_url;
		
		nodeKeyWord.ti.addItem(nodeChildText.ti);
	}
}

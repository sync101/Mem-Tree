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

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;

public class JSFunctions {

    public static native void setSelectionRange(Element elem, int pos, int length) /*-{
	    try {
	      elem.setSelectionRange(pos, pos + length);
	    } catch (e) {
	      // Firefox throws exception if TextBox is not visible, even if attached
	    }
    }-*/;
  
	public static native void registerJSFunctions() /*-{
	    $wnd.loadDriveFile = function (fileId) {
	    	@com.orastager.client.DriveUtil::openPickerDriveFile(Ljava/lang/String;)(fileId);
	    };
	    
	    $wnd.setFileModified = function () {
	    	@com.orastager.client.DriveUtil::setFileModified()();
	    };
	
	    $wnd.searchString = function (str) {
	    	@com.orastager.client.SearchUtil::searchString(Ljava/lang/String;Ljava/lang/Boolean;)(str,true);
	    };
	
	    $wnd.addSyncDataDMA = function (str) {
	    	@com.orastager.client.DMA::addSyncData(Ljava/lang/String;)(str);
	    };
	    
	    $wnd.showMsg = function (str) {
		  @com.orastager.client.DMA::showMsg(Ljava/lang/String;)(str);
	    };
	    
	    $wnd.showMsgPopup = function (str) {
	    	@com.orastager.client.DMA::showMsgPopupWithDelay(Ljava/lang/String;)(str);
	    };
	    
	    $wnd.hideMsgPopup = function () {
	    	@com.orastager.client.DMA::hideMsgPopUp()();
	    };
	
	    $wnd.saveDMA = function () {
		  @com.orastager.client.SaveDma::saveDMA()();
	    };
	    
	    $wnd.clearCollaborators = function(){
	      @com.orastager.client.DriveUtil::clearCollaborators()();
	    }
	    
	    $wnd.addCollaborator = function(photoUrl, userId, displayName, color, sessionId) {
	      @com.orastager.client.DriveUtil::addCollaborator(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)(photoUrl,userId, displayName, color, sessionId);
	    };
	    
	    $wnd.removeCollaborator = function(photoUrl, userId, displayName, color, sessionId){
	      @com.orastager.client.DriveUtil::removeCollaborator(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)(photoUrl, userId, displayName, color, sessionId);
	    };
	    
	    $wnd.loadDriveFileContent = function(content, title, mimeType){
	    	@com.orastager.client.DriveUtil::loadDriveFileContent(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)(content, title, mimeType);
	    }
	    
	    $wnd.saveComplete = function(){
	    	@com.orastager.client.SaveDma::saveComplete()();
	    }
	    
	    $wnd.insertComplete = function(fileId){
	    	@com.orastager.client.SaveDma::insertComplete(Ljava/lang/String;)(fileId);
	    }
	    
	    $wnd.insertFileAfterAuth = function(){
	    	@com.orastager.client.SaveDma::insertFileAfterAuth()();
	    }
	    
	    $wnd.saveFileAfterAuth = function(){
	    	@com.orastager.client.SaveDma::saveFileAfterAuth()();
	    }

	    $wnd.getClientId = function(){
	       return '1234567';
	    }
	    
	    $wnd.getAuthUrl = function(){
	    	return @com.orastager.client.DMA::getAuthUrl()();
	    }

	  $wnd.afterSearch = function(str, query, result) {
		  for (i=0; i<result.length; i++) {
		  	if(result[i]){
	          var title = result[i].title;
	          var fileId = result[i].id;
	          var descr = result[i].description;
	          //var fechaUpd = result[i].modifiedDate;
	          //var userUpd = result[i].lastModifyingUserName;
	          var selfUrl = result[i].alternateLink;
	          //var downloadUrl = resp.items[i].downloadUrl;
	
	          //alert(title + fileId + descr + selfUrl);
	          @com.orastager.client.SearchUtil::addDriveSearchResult(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)(title, descr, selfUrl);
		  	}
	      }
	      
	      @com.orastager.client.SearchUtil::redrawDriveSearchTable()();
	  }
	}-*/;

	public static native void installChromeExt() /*-{
		$wnd.installChromeExtension();
	}-*/;
	
	public static native void showPicker() /*-{
		$wnd.createPicker();
	}-*/;
	
	public static native void searchDrive(String str, String query) /*-{
		$wnd.searchDrive(str, query, $wnd.afterSearch);
	}-*/;
	
	public static native void shareFile(String fileId) /*-{
		$wnd.shareFile(fileId);
	}-*/;
	
	public static native void downloadFileWithUrl(String downloadUrl, String title) /*-{
		$wnd.downloadFileWithUrl(downloadUrl, title);
	}-*/;
	
	public static native boolean isMobile() /*-{
		return $wnd.isMobile();
	}-*/;
	
	public static native void appendJS() /*-{
		var script = document.createElement("script");
		script.src = '/static/dma.js';
		$doc.getElementsByTagName("head")[0].appendChild(script);
		$doc.getElementById('speech-input-textbox').style.zIndex = 10;
	}-*/;
	
	public native static void requestPermission() /*-{
		if (!window.webkitNotifications) {
	    return;
	  }
	
		if (window.webkitNotifications.checkPermission() > 0)
	   window.webkitNotifications.requestPermission();
	}-*/;

	public native static void getSyncData() /*-{
		$wnd.getSyncDataDMA();
	}-*/;
	
	public native static void getBookMarks() /*-{
		$wnd.getBookMarks();
	}-*/;
	
	public native static void deleteSyncData() /*-{
		$wnd.deleteSyncDataDMA();
	}-*/;
	
	public native static String getVideoId(String url) /*-{
		return $wnd.getVideoId(url)
	}-*/;
	
	public static void openURL(String url, String name){
	  if (url.toLowerCase().contains("key_dma")){
		  String keyword = Window.prompt("Enter keyword to replace", "");
		  if (keyword != null){
			  Window.open(url.replace("key_dma", keyword), name, "");
			  return;
		  }else
			  return;
	  }
	  Window.open(url, name, "");
	};

	public static native void loadRealTimeForFile(String type, String fileId)/*-{
	  $wnd.loadRealTimeForFile(type, fileId);
	}-*/;
	
	public static native void updateFile(String fileData)/*-{
	  $wnd.updateFile(fileData);
	}-*/;

	public static native void insertFile(String title, String fileData)/*-{
	  $wnd.insertFile(title, fileData);
	}-*/;
}
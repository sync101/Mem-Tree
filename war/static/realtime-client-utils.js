/**
 * Copyright 2013 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

 "use strict";

/**
 * @fileoverview Common utility functionality for Google Drive Realtime API,
 * including authorization and file loading. This functionality should serve
 * mostly as a well-documented example, though is usable in its own right.
 */


/**
 * @namespace Realtime client utilities namespace.
 */
var rtclient = rtclient || {}


/**
 * OAuth 2.0 scope for installing Drive Apps.
 * @const
 */
rtclient.INSTALL_SCOPE = 'https://www.googleapis.com/auth/drive.install'

//rtclient.DRIVE_SCOPE = 'https://www.googleapis.com/auth/drive.readonly'


/**
 * OAuth 2.0 scope for opening and creating files.
 * @const
 */
rtclient.FILE_SCOPE = 'https://www.googleapis.com/auth/drive.file'


/**
 * OAuth 2.0 scope for accessing the user's ID.
 * @const
 */
rtclient.OPENID_SCOPE = 'openid'
	

/**
 * Fetches an option from options or a default value, logging an error if
 * neither is available.
 * @param options {Object} containing options.
 * @param key {string} option key.
 * @param defaultValue {Object} default option value (optional).
 */
rtclient.getOption = function(options, key, defaultValue) {
  var value = options[key] == undefined ? defaultValue : options[key];
  if (value == undefined) {
    console.error(key + ' should be present in the options.');
  }
  //console.log(value);
  return value;
}


/**
 * Creates a new Authorizer from the options.
 * @constructor
 * @param options {Object} for authorizer. Two keys are required as mandatory, these are:
 *
 *    1. "clientId", the Client ID from the APIs Console
 */
rtclient.Authorizer = function(type, fileId, options) {
  this.clientId = getClientId();
  // Get the user ID if it's available in the state query parameter.
  this.userId = "";
  this.popupHtml = "<img style=\x22border: black 1px solid;\x22 src=\x22http://mem-tree.appspot.com/Icons/popup_blocker.jpg\x22><br><br><b>Mem-Tree needs Oauth2 token from Google Drive (via popup). <br>Browser might have blocked the popup. </b><br><br>"
	  +"\x3Cbutton class=\x22gwt-Button\x22 title=\x22Click should invoke the popup. If not, please check if browser blocked the popup as in the above image.\x22 onclick=\x22window.loadRealTimeForFile(\x27"+type+"\x27,\x27"+fileId+"\x27);\x22\x3EAuthorize\x3C/button\x3E"
	  +" \x3Cbutton class=\x22gwt-Button\x22 title=\x22Authorize without popup. Will loose unsaved changes. Make sure to choose the right account which owns the file if logged in multiple accounts. \x22 onclick=\x22window.location.replace(window.getAuthUrl());\x22\x3ENo luck!\x3C/button\x3E"
	  +" \x3Cbutton class=\x22gwt-Button\x22 onclick=\x22window.hideMsgPopup();\x22\x3EIgnore\x3C/button\x3E";
  this.type = type;
}


/**
 * Start the authorization process.
 * @param onAuthComplete {Function} to call once authorization has completed.
 */
rtclient.Authorizer.prototype.start = function(onAuthComplete) {
  var _this = this;
  gapi.load('auth:client,drive-realtime,drive-share', function() {
    _this.authorize(onAuthComplete);
  });
}


/**
 * Reauthorize the client with no callback (used for authorization failure).
 * @param onAuthComplete {Function} to call once authorization has completed.
 */
rtclient.Authorizer.prototype.authorize = function(onAuthComplete) {
  var clientId = this.clientId;
  var userId = this.userId;
  var type = this.type;
  var _this = this;
  var hideMsg = false;

  var handleAuthResult = function(authResult) {
    if (authResult && !authResult.error) {
      //_this.fetchUserId(onAuthComplete);
      onAuthComplete(authResult.access_token);
      if (hideMsg)
        hideMsgPopup();
    } else {
      showMsgPopup(_this.popupHtml);
      hideMsg = true;
      authorizeWithPopup();
    }
  };

  var authorizeWithPopup = function() {
     gapi.auth.authorize({
      client_id: clientId,
      scope: [
        rtclient.INSTALL_SCOPE,
        //rtclient.DRIVE_SCOPE,
        rtclient.FILE_SCOPE,
        rtclient.OPENID_SCOPE
      ],
      user_id: userId,
      immediate: false
    }, handleAuthResult);
    //console.log(clientId);
  };

  // Try with no popups first.
  gapi.auth.authorize({
    client_id: clientId,
    scope: [
      rtclient.INSTALL_SCOPE,
      //rtclient.DRIVE_SCOPE,
      rtclient.FILE_SCOPE,
      rtclient.OPENID_SCOPE
    ],
    user_id: userId,
    immediate: true
  }, handleAuthResult);
}


/**
 * Fetch the user ID using the UserInfo API and save it locally.
 * @param callback {Function} the callback to call after user ID has been
 *     fetched.
 */
rtclient.Authorizer.prototype.fetchUserId = function(callback) {
  var _this = this;
  gapi.client.load('oauth2', 'v2', function() {
    gapi.client.oauth2.userinfo.get().execute(function(resp) {
      if (resp.id) {
        _this.userId = resp.id;
      }
      if (callback) {
        callback();
      }
    });
  });
};

/**
 * Handles authorizing, parsing query parameters, loading and creating Realtime
 * documents.
 * @constructor
 * @param options {Object} options for loader. Four keys are required as mandatory, these are:
 *
 *    1. "clientId", the Client ID from the APIs Console
 *    2. "initializeModel", the callback to call when the file is loaded.
 *    3. "onFileLoaded", the callback to call when the model is first created.
 *
 * and one key is optional:
 *
 *    1. "defaultTitle", the title of newly created Realtime files.
 */

rtclient.RealtimeLoader = function(type, fileId, options) {
  // Initialize configuration variables.
  this.onFileLoaded = rtclient.getOption(options, 'onFileLoaded');
  this.initializeModel = rtclient.getOption(options, 'initializeModel');
  this.authorizer = new rtclient.Authorizer(type, fileId, options);
  this.fileId = fileId;
}


/**
 * Starts the loader by authorizing.
 * @param callback {Function} afterAuth callback called after authorization.
 */
rtclient.RealtimeLoader.prototype.start = function(afterAuth) {
  // Bind to local context to make them suitable for callbacks.
  var _this = this;
  this.authorizer.start(function() {
    if (afterAuth) {
      afterAuth();
    }
    if (_this.fileId != 'no-realtime')
      _this.load();
  });
}

/**
 * Loads or creates a Realtime file depending on the fileId and state query
 * parameters.
 */
rtclient.RealtimeLoader.prototype.load = function() {
	  // Creating the error callback.
	  var authorizer = this.authorizer;
	  var handleErrors = function(e) {
	    if(e.type == gapi.drive.realtime.ErrorType.TOKEN_REFRESH_REQUIRED) {
	      authorizer.authorize();
	    }
	  };

	  // We have a file ID in the query parameters, so we will use it to load a file.
	  if (this.fileId) {
	    gapi.drive.realtime.load(this.fileId, this.onFileLoaded, this.initializeModel, handleErrors);
	    return;
	  }
}
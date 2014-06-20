    // Use the Google Loader script to load the google.picker script.
    //google.setOnLoadCallback(createPicker);
    google.load('picker', '1');
    
    var authToken;

    // Create and render a Picker object for searching images.
    function createPicker() {
       if (authToken)
    	 createFilePicker(authToken);
       else
    	gapi.load('auth:client,picker', function() {
    		gapi.auth.authorize(
            {
              'client_id': getClientId(),
              'scope': 'https://www.googleapis.com/auth/drive.file', //'https://www.googleapis.com/auth/drive.readonly',
              'immediate': true
            },
            handleAuthResult);
   	    });    	
    }
    
    function handleAuthResult(authResult) {
        if (authResult && !authResult.error) {
          var oauthToken = authResult.access_token;
          createFilePicker(oauthToken);
        }
      }
    
    function createFilePicker(oauthToken){
		var view = new google.picker.View(google.picker.ViewId.DOCS).setQuery('*.dma');
		view.setMimeTypes('text/plain');
		var clientId = getClientId();
		var picker = new google.picker.PickerBuilder().
		                 addView(view).
		                 enableFeature(google.picker.Feature.NAV_HIDDEN).
		                 setOAuthToken(oauthToken).
		                 setTitle('Choose file').
		                 setAppId(clientId).
		                 setCallback(pickerCallback).
		                 build();
		picker.setVisible(true);
    }

    // A simple callback implementation.
    function pickerCallback(data) {
      var url = 'nothing';
      if (data[google.picker.Response.ACTION] == google.picker.Action.PICKED) {
        var doc = data[google.picker.Response.DOCUMENTS][0];
        url = doc[google.picker.Document.URL];
        loadDriveFile(doc.id);
      }else document.getElementById('speech-input-textbox').focus();
      //var message = 'You picked: ' + url;
    }
    
    function installChromeExtension(){
		if (chrome.app.isInstalled)
			  showMsg('Already installed');
	    else chrome.webstore.install();
    }
    
    function getVideoId(url){
    	var regExp = /^.*(youtu.be\/|v\/|u\/\w\/|embed\/|watch\?v=|\&v=)([^#\&\?]*).*/;
    	var match = url.match(regExp);
    	if (match&&match[2].length==11){
    	    return 'http://youtube.com/embed/'+match[2];
    	}else{
    	    return url;
    	}
    }
    
    function printContent(html){
	    var frame = document.getElementById('__printingFrame');

	    if (!frame) {
	     alert("Error: Can't find printing frame.");
	    return;
	    }

	    frame = frame.contentWindow;
	    var doc = frame.document;

	    var link = doc.createElement("link");
        link.setAttribute("rel", "stylesheet");
        link.setAttribute("type", "text/css");
        link.setAttribute("href", "/static/lib/codemirror.css");

        doc.open();
        doc.write('<!DOCTYPE html><html><head><meta charset="utf-8"/><meta http-quiv="Content-Type" content="text/html; charset=utf-8"/><title>Print Frame</title><link rel="stylesheet" type="text/css" href="/static/theme/eclipseDMA.css"/></head><body>' + html + '</body></html>');
	    //doc.write(html);
	    doc.close();

	    //doc.body.appendChild(link);

	    frame.focus();
	    frame.print();
    }
    
    var editor = new Array();
    var hlLine = new Array();

    function setEditorSize(elementId, width, height){
    	editor[elementId].setSize(width, height);
    }
    
    function copyEditor(elementId){
    	editor[elementId].save();
    }
    
    function changeEditorMode(elementId, mode){
    	editor[elementId].setOption("mode", mode);
    	//CodeMirror.autoLoadMode(editor[elementId], editor_type);
    }

    function createEditor(elementId, mode, width, height)
    {
        editor[elementId] = CodeMirror.fromTextArea(document.getElementById('codeTab'+elementId), {
            lineNumbers: true,
            styleActiveLine: true,
            matchBrackets: true,
            highlightSelectionMatches: {showToken: /\w/},
            indentUnit: 4
           });
        
        changeEditorMode(elementId, mode);

    	editor[elementId].setSize(width, height);
        editor[elementId].focus();
        editor[elementId].setOption("theme", "eclipse");
    }

	function addCollaboratorEvent(evt) {
	  var user = evt.collaborator;
	  addCollaborator(user.photoUrl, user.userId, user.displayName, user.color, user.sessionId);
	  showMsg(user.displayName+' opened this file');
	  //console.log('User ID:'    + user.userId);
	  //console.log('Session ID:' + user.sessionId);
	  //console.log('Name:'       + user.displayName);
	  //console.log('Color:'      + user.color);
	}

  function removeCollaboratorEvent(evt) {
  	  var user = evt.collaborator;
  	  removeCollaborator(user.photoUrl, user.userId, user.displayName, user.color, user.sessionId);
  	  showMsg(user.displayName+' closed this file');
  	  //console.log('User ID:'    + user.userId);
  	  //console.log('Session ID:' + user.sessionId);
  	  //console.log('Name:'       + user.displayName);
  	  //console.log('Color:'      + user.color);
  }

  function getAllCollaborators(doc) {
  	  clearCollaborators();
  	  var collaborators = doc.getCollaborators();
  	  var collaboratorCount = collaborators.length;
  	  //console.log(collaboratorCount + ' collaborators:');
  	  for (var i = 0; i < collaboratorCount; i++) {
  	    var user = collaborators[i];
  	    //alert(user.displayName);
  	    addCollaborator(user.photoUrl, user.userId, user.displayName, user.color, user.sessionId);
  	    //console.log('Name: ' + user.displayName);
  	    //console.log('Is me:' + user.isMe);
        }
  }

  function initializeModel(model) {
  }
  
  var share;

  var onAuthComplete = function(AuthToken) {
	  authToken = AuthToken;
	  if (action_type == 'insert')
		insertFileAfterAuth(); 
	  else if (action_type == 'update')
		  saveFileAfterAuth();
	  else if (action_type == 'newuser'){
		  document.getElementById('Loading-Message').innerHTML = "";
	  }
	  else {
	    getFileMetadata(downloadFile);
		  if (share)
			  return;
		  else{
			  var clientId = getClientId();
			  share = new gapi.drive.share.ShareClient(clientId);
		  }
	  }
  }
  
  function shareFile(fileId){
	  if (share){
		share.setItemIds([fileId]);
		share.showSettingsDialog();
	  }
  }  

  function onFileLoaded(doc) {
  	getAllCollaborators(doc);
    doc.addEventListener(gapi.drive.realtime.EventType.COLLABORATOR_JOINED, addCollaboratorEvent);
  	doc.addEventListener(gapi.drive.realtime.EventType.COLLABORATOR_LEFT, removeCollaboratorEvent);
  }

  var realtimeOptions = {
  	      initializeModel: initializeModel,
  	      onFileLoaded: onFileLoaded
  }
  
  var currFileId;
  var action_type;

  function loadRealTimeForFile(type, fileId) {
  	//gapi.drive.realtime.load(fileId, onFileLoaded, initializeModel);
	currFileId = fileId;
	action_type = type;
	
	if (type == 'get'){
  	 var realtimeLoader = new rtclient.RealtimeLoader(type, fileId, realtimeOptions);
      realtimeLoader.start(onAuthComplete);
	}else {
  	 var realtimeLoader = new rtclient.RealtimeLoader(type, 'no-realtime', realtimeOptions);
      realtimeLoader.start(onAuthComplete);
	}
    
  }
  
  function searchDrive(str, query, afterSearch) {
	  var retrievePageOfFiles = function(request, result) {
	    request.execute(function(resp) {
	      result = result.concat(resp.items);
	      /*var nextPageToken = resp.nextPageToken;
	      if (nextPageToken) {
	        request = gapi.client.drive.files.list({
	          'q' : query,
	          'pageToken' : nextPageToken,
	          'maxResults': 20
	        });
	        retrievePageOfFiles(request, result);
	      } else {
	    	 afterSearch(result);
	      }*/
	      afterSearch(str, query, result);
	    });
	  }
		  gapi.client.load('drive', 'v2', function() {
			  var request = gapi.client.drive.files.list({
		          'q': query,
		          'maxResults': 20
		      });
			  retrievePageOfFiles(request, []);
		  });
  }
  
  /**
   * Fetches the metadata for a Realtime file.
   * @param fileId {string} the file to load metadata for.
   * @param callback {Function} the callback to be called on completion, with signature:
   *
   *    function onGetFileMetadata(file) {}
   *
   * where the file parameter is a Google Drive API file resource instance.
   */
  getFileMetadata = function(callback) {
    if (currFileId){	  
	    gapi.client.load('drive', 'v2', function() {
	      gapi.client.drive.files.get({
	        'fileId' : currFileId
	      }).execute(callback);
	    });
    }
  }
  
  var currFile;

  /**
   * Download a file's content.
   *
   * @param {File} file Drive File instance.
   * @param {Function} callback Function to call when the request is complete.
   */
  function downloadFile(file) {
    if (file.downloadUrl) {
      currFile = file;
      var accessToken = gapi.auth.getToken().access_token;
      var xhr = new XMLHttpRequest();
      xhr.open('GET', file.downloadUrl);
      xhr.setRequestHeader('Authorization', 'Bearer ' + accessToken);
      xhr.onload = function() {
    	  loadDriveFileContent('/*'+xhr.responseText+'*/', file.title, file.mimeType);
      };
      xhr.onerror = function(e) {
		  if (e.error){
		   hideMsgPopup();
    	   showMsg(e.error);
		  }
		  else
		   loadDriveFileContent(null);
      };
      xhr.send();
    } else {
      if (file.error.message){
       hideMsgPopup();
	   showMsg(file.error.message);
      }else
       loadDriveFileContent(null);
    }
  }
  
  function downloadFileWithUrl(downloadUrl, title) {
        var xhr = new XMLHttpRequest();
        xhr.open('GET', downloadUrl);
        xhr.onload = function() {
      	  loadDriveFileContent('/*'+xhr.responseText+'*/', title, 'text/plain');
        };
        xhr.onerror = function(e) {
  		  if (e.error){
  		   hideMsgPopup();
      	   showMsg(e.error);
  		  }
  		  else
  		   loadDriveFileContent(null);
        };
        xhr.send();
  }
  
  /**
   * Update an existing file's metadata and content.
   *
   * @param {String} fileData File object to read data from.
   * @param {Function} callback Callback function to call when the request is complete.
   */
  function updateFile(fileData, callback) {
    var boundary = '-------314159265358979323846';
    var delimiter = "\r\n--" + boundary + "\r\n";
    var close_delim = "\r\n--" + boundary + "--";

   if(currFile.mimeType){
      // Updating the metadata is optional and you can instead use the value from drive.files.get.
      var multipartRequestBody =
    	  delimiter +  'Content-Type: application/json\r\n\r\n' +
          JSON.stringify(currFile) +
          delimiter + 'Content-Type: ' + currFile.mimeType + '\r\n' + '\r\n' +
          fileData +
          close_delim;
      
      var accessToken = gapi.auth.getToken().access_token;
      var request = gapi.client.request({
          'path': '/upload/drive/v2/files/' + currFileId,
          'method': 'PUT',
          'params': {'uploadType': 'multipart'},
          'headers': {
            'Content-Type': 'multipart/mixed; boundary="' + boundary + '"',
            'Authorization': 'Bearer ' + accessToken
          },
          'body': multipartRequestBody});

      if (!callback) {
        callback = function(file) {
          //console.log(file)
           if (!file.error) {	
        	showMsg('Saved');
        	saveComplete();
           }
           else
        	showMsg(file.error.message);
        };
      }
      request.execute(callback);
   }
  }
  
  /**
   * Insert new file.
   *
   * @param {String} title.
   * @param {String} fileData.
   * @param {Function} callback Function to call when the request is complete.
   */
  function insertFile(title, fileData, callback) {
    var boundary = '-------314159265358979323846';
    var delimiter = "\r\n--" + boundary + "\r\n";
    var close_delim = "\r\n--" + boundary + "--";

      var contentType = "text/plain";
      var metadata = {
        'title': title,
        'mimeType': contentType
      };

      var multipartRequestBody =
          delimiter +
          'Content-Type: application/json\r\n\r\n' +
          JSON.stringify(metadata) +
          delimiter + 'Content-Type: ' + contentType + '\r\n' + '\r\n' +
          fileData +
          close_delim;

      var accessToken = gapi.auth.getToken().access_token;
      var request = gapi.client.request({
          'path': '/upload/drive/v2/files',
          'method': 'POST',
          'params': {'uploadType': 'multipart'},
          'headers': {
            'Content-Type': 'multipart/mixed; boundary="' + boundary + '"',
            'Authorization': 'Bearer ' + accessToken
          },
          'body': multipartRequestBody});

      if (!callback) {
        callback = function(file) {
          //console.log(file)
           if (!file.error) {	
           	showMsg('Saved');
           	insertComplete(file.id);
           }
           else
        	showMsg(file.error.message);
        };
      }
      
      request.execute(callback);
  }
  
  function isMobile() {
	var check = false;
	(function(a){if(/(android|bb\d+|meego).+mobile|avantgo|bada\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|iris|kindle|lge |maemo|midp|mmp|mobile.+firefox|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\.(browser|link)|vodafone|wap|windows (ce|phone)|xda|xiino/i.test(a))check = true;})(navigator.userAgent||navigator.vendor||window.opera);
	return check; 
  }
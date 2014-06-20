document.getElementById("speech-input-textbox").addEventListener("webkitspeechchange", function(event) { searchString('dma-speech'); }, false);

window.addEventListener("message", function(event) {
	// We only accept messages from ourselves
	if (event.origin != "http://mem-tree.appspot.com" )
	  return;
	
	if (event.source != window)
	  return;
	
	if (event.data.type && (event.data.type == "FROM_PAGE")) {
	  //alert(event.data.text);
	  addSyncDataDMA(event.data.text);
	}
}, false);

function getSyncDataDMA(){
 window.postMessage({ type: "TO_PAGE", text: "getSyncData" }, "*");
}

function deleteSyncDataDMA(){
 window.postMessage({ type: "TO_PAGE", text: "deleteSyncData" }, "*");
}

function getBookMarks(){
 window.postMessage({ type: "TO_PAGE", text: "getBookMarks" }, "*");	
}
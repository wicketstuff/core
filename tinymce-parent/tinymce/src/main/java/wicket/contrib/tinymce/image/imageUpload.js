var __lastPositionBookMark;

var isIE=(navigator.appName.indexOf("Microsoft")!=-1)?1:0;

var putImage = function(image) {
   if (isIE) {
        tinyMCE.activeEditor.selection.moveToBookmark(__lastPositionBookMark);
   }
   tinyMCE.execCommand('mceInsertContent', false, image);
};

var saveBookmark = function() {
    if (isIE) {
        __lastPositionBookMark = tinyMCE.activeEditor.selection.getBookmark();
    }
};

var isImgSelected = function() {
	var ed = tinymce.activeEditor;
	var currentNode = ed.selection;
	var nodeName = '';
	
	if(currentNode)
		nodeName = currentNode.getNode().nodeName;
	
	return nodeName === 'IMG';
};

var openImageForm = function() {
	var ed = tinymce.activeEditor;
	var url = ed.baseURI.source + 'themes/advanced';
	
	ed.windowManager.open({
		file : url + '/image.htm',
		width : 480 + parseInt(ed.getLang('advimage.delta_width', 0)),
		height : 385 + parseInt(ed.getLang('advimage.delta_height', 0)),
		inline : 1
	}, {
		plugin_url : url
	});
};

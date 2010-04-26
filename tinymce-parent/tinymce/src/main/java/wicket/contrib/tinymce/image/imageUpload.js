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
        __lastPositionBookMark = activeEditor.selection.getBookmark();
    }
};
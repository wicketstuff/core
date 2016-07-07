/**
 * plugin.js
 *
 * Copyright, Moxiecode Systems AB
 * Released under LGPL License.
 *
 * License: http://www.tinymce.com/license
 * Contributing: http://www.tinymce.com/contributing
 */

/*global tinymce:true */

tinymce.PluginManager.add('imageupload', function(editor) {
	
	editor.addButton('imageupload', {
		icon: 'image',
		text: 'Image upload',
		tooltip: 'Upload an image',
		onclick: function() {
			showImageUploadDialog();
        }
	});

});

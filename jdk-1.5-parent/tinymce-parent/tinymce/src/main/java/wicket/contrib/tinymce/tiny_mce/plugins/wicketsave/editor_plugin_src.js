/**
 * @author Pointbreak, Amsterdam
 * @copyright Copyright © 2008, Pointbreak, Amsterdam, All rights reserved.
 */

(function() {
	tinymce.create('tinymce.plugins.WicketSave', {
		init : function(ed, url) {
			var t = this;

			t.editor = ed;

			// Register commands
			ed.addCommand('mceWicketSave', t._save, t);
			ed.addCommand('mceWicketCancel', t._cancel, t);

			// Register buttons
			ed.addButton('save', {title : 'save.save_desc', cmd : 'mceWicketSave'});
			ed.addButton('cancel', {title : 'save.cancel_desc', cmd : 'mceWicketCancel'});

			ed.onNodeChange.add(t._nodeChange, t);
			ed.addShortcut('ctrl+s', ed.getLang('save.save_desc'), 'mceWicketSave');
		},

		getInfo : function() {
			return {
				longname : 'WicketSave',
				author : 'Pointbreak, Amsterdam',
				authorurl : 'http://pointbreak.biz',
				infourl : 'http://pointbreak.biz',
				version : tinymce.majorVersion + "." + tinymce.minorVersion
			};
		},

		// Private methods

		_nodeChange : function(ed, cm, n) {
			var ed = this.editor;

			if (ed.getParam('save_enablewhendirty')) {
				cm.setDisabled('save', !ed.isDirty());
			}
		},

		// Private methods

		_save : function() {
			var ed = this.editor;
			//ed.save() -> don't, it will trigger dom changes which may trigger
			//  image resources being loaded, which may make wicket ignore ajax requests
			//  (ajax requests are ignored when normal requests for the same page are being
			//  made; so a request for a non-shared image may make an ajax request get ignored)
			ed.execCallback('save_onwicketsavecallback', ed);
		},

		_cancel : function() {
			var ed = this.editor, h = tinymce.trim(ed.startContent);
			ed.setContent(h);
			ed.undoManager.clear();
			ed.nodeChanged();
			tinyMCE.execCommand('mceRemoveControl', true, ed.id);
			ed.execCallback('save_onwicketcancelcallback', ed);
		}
	});

	// Register plugin
	tinymce.PluginManager.add('wicketsave', tinymce.plugins.WicketSave);
})();
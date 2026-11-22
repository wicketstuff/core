/*
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
;(function () {
	'use strict';

	if (Wicket.HugeRte) {
		return;
	}

	// Ids of editors to focus as soon as they are initialized.
	const pendingFocus = new Set();

	// Copies the content to the textarea without marking the editor as saved, so isDirty() keeps
	// meaning "changed since load" for the autosave and save plugins and for application code.
	function sync(editor) {
		editor.save({ set_dirty: false });
	}

	function removeEditorsWithin(node) {
		hugerte.get().forEach(function (editor) {
			const element = editor.getElement();
			if (element && (element === node || node.contains(element))) {
				editor.remove();
			}
		});
	}

	// The textarea is hidden while the editor is active, so it never fires change or blur itself.
	// Forwarding them lets behaviors such as AjaxFormComponentUpdatingBehavior work unchanged.
	function forwardEvents(editor, element) {
		editor.on('change undo redo', function () {
			sync(editor);
			element.dispatchEvent(new Event('change', { bubbles: true }));
		});
		editor.on('blur', function () {
			sync(editor);
			element.dispatchEvent(new Event('blur'));
		});
	}

	// A hidden textarea with the required attribute makes the browser refuse a normal form submit
	// without showing why. Wicket validates required components on the server, so the attribute
	// is removed while the editor is active. It is restored on detach, which HugeRTE fires after
	// putting back the attributes it saved when the editor was created.
	function suspendRequired(editor, element) {
		if (!element.hasAttribute('required')) {
			return;
		}
		element.removeAttribute('required');
		editor.on('detach', function () {
			element.setAttribute('required', 'required');
		});
	}

	// Installs the integration, then calls the setup callback of the configuration, if any.
	function wrapSetup(id, element, setup) {
		return function (editor) {
			suspendRequired(editor, element);
			forwardEvents(editor, element);
			editor.on('init', function () {
				// The element may have been replaced by Ajax while the editor was loading. HugeRTE
				// continues its own initialization after this event, so remove the editor later.
				if (!element.isConnected) {
					setTimeout(function () {
						if (!editor.removed) {
							editor.remove();
						}
					}, 0);
					return;
				}
				if (pendingFocus.delete(id)) {
					editor.focus();
				}
			});
			if (setup) {
				setup.call(this, editor);
			}
		};
	}

	Wicket.HugeRte = {
		/**
		 * Creates the editor for the element with the given id, replacing an editor that a
		 * previous render left behind.
		 */
		init: function (id, config) {
			const element = document.getElementById(id);
			if (!element) {
				return;
			}
			const previous = hugerte.get(id);
			if (previous) {
				previous.remove();
			}
			const options = Object.assign({}, config);
			options.target = element;
			options.setup = wrapSetup(id, element, config.setup);
			// On phones and tablets HugeRTE applies the mobile options over the others, so they
			// need the same setup, and must not make a disabled component editable.
			if (options.mobile) {
				options.mobile = Object.assign({}, options.mobile);
				options.mobile.setup = wrapSetup(id, element, options.mobile.setup || config.setup);
				if (options.readonly === true) {
					options.mobile.readonly = true;
				}
			}
			hugerte.init(options);
		},

		/**
		 * Focuses the editor with the given id, or does so once it has been initialized.
		 */
		focus: function (id) {
			// Stop Wicket from moving the focus back to the element that triggered the Ajax call.
			Wicket.Focus.setFocusOnId(null);
			const editor = hugerte.get(id);
			if (editor && editor.initialized) {
				editor.focus();
			} else {
				pendingFocus.add(id);
			}
		},

		/**
		 * Marks the editor with the given id as saved: it is no longer dirty, and a draft kept by
		 * the autosave plugin is discarded.
		 */
		markSaved: function (id) {
			const editor = hugerte.get(id);
			if (!editor) {
				return;
			}
			editor.setDirty(false);
			if (editor.plugins.autosave) {
				editor.plugins.autosave.removeDraft(false);
			}
		}
	};

	// Wicket serializes form fields after this event, so the textareas must be up to date.
	Wicket.Event.subscribe(Wicket.Event.Topic.AJAX_CALL_BEFORE, function () {
		hugerte.get().forEach(sync);
	});

	// The editor UI lives next to its textarea, so removing the textarea alone leaves it behind.
	Wicket.Event.subscribe(Wicket.Event.Topic.DOM_NODE_REMOVING, function (jqEvent, node) {
		removeEditorsWithin(node);
	});
})();

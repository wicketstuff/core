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

	// Editors to focus as soon as they are initialized.
	const pendingFocus = new WeakSet();

	// The content each editor had when the last Wicket Ajax call that sent it went out.
	const submitted = new WeakMap();

	// The number of Ajax calls that sent each editor and haven't completed yet.
	const inFlight = new WeakMap();

	// The editors each Ajax call sent, keyed by the call's attributes.
	const sentBy = new WeakMap();

	// Marks a snapshot that can't be attributed to one call, because calls that sent the editor
	// overlapped.
	const AMBIGUOUS = {};

	// Whether the element is in what the Ajax call serializes, following Wicket.Ajax.Call.doAjax:
	// the form (attrs.f), otherwise the component (attrs.c), recursively when attrs.sr is set.
	function targets(attrs, element) {
		if (attrs.f) {
			let form = document.getElementById(attrs.f);
			if (!form) {
				return false;
			}
			if (form.tagName.toLowerCase() === 'form') {
				return element.form === form;
			}
			// a nested Wicket form renders as a div; Wicket serializes the enclosing form
			while (form.parentNode && form.tagName.toLowerCase() !== 'form' &&
				form.tagName.toLowerCase() !== 'body') {
				form = form.parentNode;
			}
			return form.contains(element);
		}
		if (attrs.c) {
			const component = document.getElementById(attrs.c);
			return !!component &&
				(component === element || (!!attrs.sr && component.contains(element)));
		}
		return false;
	}

	// Whether the request data, as jQuery is about to send it, contains the parameter: FormData
	// for a multipart call, the body for a POST, the URL's query for a GET.
	function sends(settings, name) {
		if (!name) {
			return false;
		}
		const data = settings.data;
		if (data instanceof FormData) {
			return data.has(name);
		}
		if (typeof data === 'string') {
			return new URLSearchParams(data).has(name);
		}
		const url = (settings.url || '').split('#')[0];
		const query = url.indexOf('?');
		return query >= 0 && new URLSearchParams(url.slice(query + 1)).has(name);
	}

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
	function wrapSetup(element, setup) {
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
				if (pendingFocus.delete(editor)) {
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
			options.setup = wrapSetup(element, config.setup);
			// On phones and tablets HugeRTE applies the mobile options over the others, so they
			// need the same setup, and must not make a disabled component editable.
			if (options.mobile) {
				options.mobile = Object.assign({}, options.mobile);
				options.mobile.setup = wrapSetup(element, options.mobile.setup || config.setup);
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
			const editor = hugerte.get(id);
			if (!editor) {
				return;
			}
			// Stop Wicket from moving the focus back to the element that triggered the Ajax call.
			Wicket.Focus.setFocusOnId(null);
			if (editor.initialized) {
				editor.focus();
			} else {
				pendingFocus.add(editor);
			}
		},

		/**
		 * Marks the editor with the given id as saved: it is no longer dirty, and a draft kept by
		 * the autosave plugin is discarded. Runs in the response to the Ajax call that submitted
		 * the content. The editor stays unsaved when it changed since that call was sent, or
		 * when no submitted content is known for it.
		 */
		markSaved: function (id) {
			const editor = hugerte.get(id);
			// When calls that sent this editor overlapped, it is unknown which content is being
			// acknowledged, so the editor stays unsaved until a call sends it on its own.
			const snapshot = submitted.get(editor);
			if (!editor || !editor.initialized || typeof snapshot !== 'string' ||
				(inFlight.get(editor) || 0) > 1 || snapshot !== editor.getContent()) {
				return;
			}
			editor.setDirty(false);
			if (editor.plugins.autosave) {
				editor.plugins.autosave.removeDraft();
			}
			// Lets the save and restore buttons update their state.
			editor.nodeChanged();
		}
	};

	// Wicket serializes form fields after this event, so the textareas must be up to date.
	Wicket.Event.subscribe(Wicket.Event.Topic.AJAX_CALL_BEFORE, function () {
		hugerte.get().forEach(function (editor) {
			if (editor.initialized) {
				sync(editor);
			}
		});
	});

	// Only calls that actually send an editor take a snapshot of it, so a timer, a call stopped
	// by a precondition or a call that serializes something else can't make markSaved accept
	// content the server never received. Runs after preconditions and serialization.
	Wicket.Event.subscribe(Wicket.Event.Topic.AJAX_CALL_BEFORE_SEND,
		function (jqEvent, attrs, jqXHR, settings) {
			const sent = hugerte.get().filter(function (editor) {
				const element = editor.getElement();
				return editor.initialized && targets(attrs, element) &&
					sends(settings, element.name);
			});
			sent.forEach(function (editor) {
				const underway = inFlight.get(editor) || 0;
				submitted.set(editor, underway > 0 ? AMBIGUOUS : editor.getContent());
				inFlight.set(editor, underway + 1);
			});
			if (sent.length > 0) {
				sentBy.set(attrs, sent);
			}
		});

	Wicket.Event.subscribe(Wicket.Event.Topic.AJAX_CALL_COMPLETE, function (jqEvent, attrs) {
		(sentBy.get(attrs) || []).forEach(function (editor) {
			inFlight.set(editor, Math.max(0, (inFlight.get(editor) || 0) - 1));
		});
		sentBy.delete(attrs);
	});

	// The editor UI lives next to its textarea, so removing the textarea alone leaves it behind.
	Wicket.Event.subscribe(Wicket.Event.Topic.DOM_NODE_REMOVING, function (jqEvent, node) {
		removeEditorsWithin(node);
	});
})();

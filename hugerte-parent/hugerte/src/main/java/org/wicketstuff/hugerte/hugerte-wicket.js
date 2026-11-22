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

	function removeEditorsWithin(node) {
		hugerte.get().forEach(function (editor) {
			const element = editor.getElement();
			if (element && (element === node || node.contains(element))) {
				editor.remove();
			}
		});
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
			config.target = element;
			hugerte.init(config);
		}
	};

	// Wicket serializes form fields after this event, so the textareas must be up to date.
	Wicket.Event.subscribe(Wicket.Event.Topic.AJAX_CALL_BEFORE, function () {
		hugerte.triggerSave();
	});

	// The editor UI lives next to its textarea, so removing the textarea alone leaves it behind.
	Wicket.Event.subscribe(Wicket.Event.Topic.DOM_NODE_REMOVING, function (jqEvent, node) {
		removeEditorsWithin(node);
	});
})();

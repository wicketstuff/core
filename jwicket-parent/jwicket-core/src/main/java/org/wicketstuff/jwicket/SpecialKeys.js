/**
 * JQuery SpecialKeys for jWicket
 * @version 1.0
 * 
 */

(function($) {
	
	var pressedKeys = {};
	var keyname = {
		'ctrl': 17,
		'alt': 18,
		'shift': 16
	};

	jQuery.jWicketSpecialKeysIsPressed = function(key) {
		key = $.trim(key).toLowerCase();
		
		if (keyname[key] != undefined)
			return pressedKeys[keyname[key]];
		else
			return pressedKeys[key];
	};

	jQuery.jWicketSpecialKeysGetPressed = function() {
		var pressed = '';
		var first = true;

		if (pressedKeys[keyname['ctrl']]) {
			pressed = 'ctrl';
			first = false;
		}

		if (pressedKeys[keyname['alt']]) {
			if (first) {
				pressed += 'alt';
				first = false;
			}
			else
				pressed += ',alt';
		}

		if (pressedKeys[keyname['shift']]) {
			if (first) {
				pressed += 'shift';
				first = false;
			}
			else
				pressed += ',shift';
		}

		return pressed;
	};


	$(document).bind('keydown', function(e) {
		pressedKeys[e.keyCode] = true;
	});

	$(document).bind('keyup', function(e) {
		delete pressedKeys[e.keyCode];
	});

})(jQuery);
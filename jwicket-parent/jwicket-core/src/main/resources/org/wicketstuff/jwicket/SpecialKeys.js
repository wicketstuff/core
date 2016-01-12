/**
 * JQuery SpecialKeys for jWicket
 * @version 1.1
 * 
 */

(function($) {
	
	var pressedKeys = {};
	var keyname = {
		'shift': 16,
		'ctrl': 17,
		'alt': 18,
		'pageup' : 33,
		'pagedown' : 34,
		'end' : 35,
		'pos1' : 36,
		'crsr-left' : 37,
		'crsr-up' : 38,
		'crsr-right' : 39,
		'crsr-down' :40,
		'insert' : 45,
		'delete' : 46,
		'esc': 27
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

		for (key in keyname) {
			if (pressedKeys[keyname[key]]) {
				if (first) {
					pressed += key;
					first = false;
				}
				else
					pressed += ','+key;
			}
		}
//console.log('jWicketSpecialKeysGetPressed = ' + pressed);

		return pressed;
	};


	$(document).bind('keydown', function(e) {
		pressedKeys[e.keyCode] = true;
//console.log('jWicketSpecialKeys: keyDown = ' + e.keyCode);
//console.log('keyPressed: ' + jQuery.jWicketSpecialKeysGetPressed());
	});

	$(document).bind('keyup', function(e) {
		pressedKeys[e.keyCode] = false;
		delete pressedKeys[e.keyCode];
//console.log('jWicketSpecialKeys: keyUp = ' + e.keyCode);
//console.log('keyPressed: ' + jQuery.jWicketSpecialKeysGetPressed());
	});

})(jQuery);





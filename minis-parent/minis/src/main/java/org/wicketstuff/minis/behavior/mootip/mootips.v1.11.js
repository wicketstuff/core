/*
Script: MooTips.js
	Tooltips, BubbleTips, whatever they are, they will appear on mouseover

License:
	MIT-style license.

Credits:
	The idea behind Tips.js is based on Bubble Tooltips (<http://web-graphics.com/mtarchive/001717.php>) by Alessandro Fulcitiniti <http://web-graphics.com>
	MooTips.js is based on Tips.js.
		Modified by razvan@e-magine.ro (<http://www.e-magine.ro/>): 
			allow AJAX and DOM usage
		Modified by Vladimir Prieto (<http://vladimirprieto.blogspot.com>): 
			prevent "fixed" tips from hiding onmouseenter of the tip
		Modified by uhleeka@gmail.com:
			allow EVAL usage
		Modified by Nino Martinez nino.martinez@jayway.dk to allow callbacks to allow better wicket integration

Class: MooTips
	Display a tip on any element with a title and/or href.

Note:
	Tips requires an XHTML doctype.

Arguments:
	elements - a collection of elements to apply the tooltips to on mouseover.
	options - an object. See options Below.

Options:
	showOnClick - set to true to display the tooltip onclick; defaults to false;
	showOnMouseEnter - set to false to prevent the tooltip from displaying onmouseenter; defaults to true;
	
	maxTitleChars - the maximum number of characters to display in the title of the tip. defaults to 30.

	onShow - optionally you can alter the default onShow behaviour with this option (like displaying a fade in effect);
	onHide - optionally you can alter the default onHide behaviour with this option (like displaying a fade out effect);

	showDelay - the delay the onShow method is called. (defaults to 100 ms)
	hideDelay - the delay the onHide method is called. (defaults to 100 ms)

	className - the prefix for your tooltip classNames. defaults to 'tool'.

		the whole tooltip will have as classname: tool-tip

		the title will have as classname: tool-title

		the text will have as classname: tool-text

	offsets - the distance of your tooltip from the mouse. an Object with x/y properties.
	fixed - if set to true, the toolTip will not follow the mouse.
	
	loadingText - text to display as a title while loading an AJAX tooltip.
	
	errTitle, errText - text to display when there's a problem with the AJAX request.

	evalAlways - set to true when using the AJAX or EVAL methods to handle the request on every mouseover; set to false to cache the value of the first attempt; defaults to false;

Example:
	(start code)
	<div>
	<img src="images/moo.png" title="Title::The content of the tooltip is stored in the &quot;title&quot; attribute" class="toolTipImg"/>
	<h4>Title</h4>
	
	<img src="images/moo.png" title="EVAL:'Eval Title\:\:' + Date() + '<br /><br />[Note the escaped delimiters]'" class="toolTipImgEVAL1"/>
	<h4>EVAL (click me)</h4>
	
	<img src="images/moo.png" title="AJAX:lipsum.htm" class="toolTipImgAJAX"/>
	<h4>AJAX</h4>
	
	<img src="images/moo.png" title="DOM:HiddenElementID" class="toolTipImgDOM"/>
		<div style="display:none;" id="HiddenElementID">DOM Title::DOM Tooltip body<br /><br /><img src="images/moo.png" width="124" /></div>
	<h4>DOM</h4>
	
	<img src="images/moo.png" title="EVAL:myEvalFunction()" class="toolTipImgEVAL2"/>
	<h4>EVAL</h4>
	
	<img src="images/moo.png" title="AJAX:invalidurl.htm" class="toolTipImgAJAX_Err"/>
	<h4>AJAX (error)</h4>
	</div>
	<script>
		window.onload = function() {
			var myTips1 = new MooTips($$('.toolTipImg'), {
				maxTitleChars: 50		// long caption
			});
			var myTips2 = new MooTips($$('.toolTipImgDOM'), {
				showDelay: 500			// Delay for 500 milliseconds
			});
			var myTips3 = new MooTips($$('.toolTipImgAJAX'), {
				maxTitleChars: 100,		// very long caption
				fixed: true,			// fixed in place; note tip mouseover does not hide tip
				offsets: {'x':100,'y':100} // offset by 100,100
			});
			var myTips4 = new MooTips($$('.toolTipImgEVAL1'), {
				evalAlways: true,		// always run the eval statement
				showOnClick: true,		// click image to show tooltip
				showOnMouseEnter: false	// do not show on mouse enter
			});
			var myTips5 = new MooTips($$('.toolTipImgEVAL2'), {});
		}
		function myEvalFunction() {
			return 'Eval Function Title::Text to be displayed in the body of the tooltip';
		}
	</script>
	(end)

Note:
	The title of the element will always be used as the tooltip body. If you put :: on your title, the text before :: will become the tooltip title.
	If you put DOM:someElementID in your title, $('someElementID').innerHTML will be used as the tooltip contents (same syntax as above).
	If you put AJAX:http://www.example.com/path/to/ajax_file.php in your title, the response text will be used as the tooltip contents (same syntax as above). Either absolute or relative paths are ok.
	If you put EVAL:somethingToEval in your title, the eval(somethingToEval) response text will be used as the tooltip contents (same syntax as above).
	
*/

var MooTips = new Class({

	options: { // modded for X3
		onShow: function(tip){
			tip.setStyle('visibility', 'visible');
		},
		onHide: function(tip){
			tip.setStyle('visibility', 'hidden');
		},
		showOnClick: false,
		showOnMouseEnter: true,
		maxTitleChars: 30,
		showDelay: 100,
		hideDelay: 100,
		className: 'tool',
		offsets: {'x': 16, 'y': 16},
		fixed: false,
		loadingText: 'Loading...',
		errTitle: 'Error...',
		errText: 'There was a problem retrieving the contents of this tooltip.',
		evalAlways: false
	},

	initialize: function(elements, options){
		this.setOptions(options);
		this.toolTip = new Element('div', {
			'class': this.options.className + '-tip',
			'styles': {
				'position': 'absolute',
				'top': '0',
				'left': '0',
				'visibility': 'hidden'
			},
			'events': {
					'mouseenter': function(event){
						//setting state property, needed on end function
						this.setProperty('state','mouseenter');
					},
					'mouseleave': function(event){
						//setting state property, needed on end function
						this.setProperty('state','mouseleave');
						this.pather.end(event);
					}
			}
		}).inject(document.body);
		
		//didn't find other way to get owner of toolTip inside toolTip
		this.toolTip.pather = this;
			
		this.wrapper = new Element('div').inject(this.toolTip);
		$$(elements).each(this.buildEvents, this);
		$$(elements).each(this.build, this);
		if (this.options.initialize) this.options.initialize.call(this);
	},
	
	buildEvents: function(el) {
		//code with errors but works
		//that's why showOnClick option is false by default
		if (this.options.showOnClick) {
			el.addEvent('click', function(event){
				this.start(el);
				if (!this.options.fixed) this.locate(event);
				else this.position(el);
			}.bindWithEvent(this));
		}
		
		if (this.options.showOnMouseEnter) {
			el.addEvent('mouseenter', function(event){
				this.start(el);
				if (!this.options.fixed) this.locate(event);
				else this.position(el);
			}.bind(this));
		}
		
		if (!this.options.fixed) el.addEvent('mousemove', this.locate.bindWithEvent(this));
		var end = this.end.bind(this);
		el.addEvent('mouseleave', end);
		el.addEvent('trash', end);
	},

	build: function(el){ // modded for X3
		el.$tmp.myTitle = (el.href && el.getTag() == 'a') ? el.href.replace('http://', '') : (el.rel || false);
		if (el.title){
			
			if (el.title.test('^DOM:', 'i')) { // check if we need to extract contents from a DOM element
				el.title = $(el.title.split(':')[1].trim()).innerHTML;				
			} else if (el.title.test('^AJAX:', 'i')) { // check for an URL to retrieve content from
				el.title = this.options.loadingText + '::' + el.title;
			} else if (el.title.test('^EVAL:', 'i')) { // check for a statement to eval
				el.title = this.options.loadingText + '::' + el.title;
			}else if (el.title.test('^CALLBACK:', 'i')) { // check for a statement to eval
				el.title = this.options.loadingText + '::' + el.title;
			}
								
			var dual = el.title.split('::');
			if (dual.length > 1) {
				el.$tmp.myTitle = dual[0].trim();
				el.$tmp.myText = dual[1].trim();
			} else {
				el.$tmp.myTitle = false;
				el.$tmp.myText = el.title;
			}					
			el.removeAttribute('title');
		} else {
			el.$tmp.myText = false;
		}
		if (el.$tmp.myTitle && el.$tmp.myTitle.length > this.options.maxTitleChars) el.$tmp.myTitle = el.$tmp.myTitle.substr(0, this.options.maxTitleChars - 1) + "&hellip;";
	},

	start: function(el){ // modded for X3
		this.wrapper.empty();
			
		// check if we have an AJAX request - if so, show a loading animation and launch the request		
		if (el.$tmp.myText && el.$tmp.myText.test('^AJAX:', 'i')) {
			//if (this.ajax) this.ajax.cancel();
			if(this.options.evalAlways) {
				// save original text
				el.$tmp.myEvalAlwaysText = el.$tmp.myText;
			}
			this.ajax = new Ajax (el.$tmp.myText.replace(/AJAX:/i,''), {
				onComplete: function (responseText, responseXML) {
					el.title = responseText;
					this.build(el);
					this.start(el);
					}.bind(this),
				onFailure: function () {
					el.title = this.options.errTitle + '::' + this.options.errText;
					this.build(el);
					this.start(el);
					}.bind(this),
				method: 'get'
				}).request();				
			el.$tmp.myText = '<div class="' + this.options.className + '-loading">&nbsp;</div>';			
		} else if (el.$tmp.myText && el.$tmp.myText.test('^EVAL:', 'i')) {
			var tmp;
			
			if(this.options.evalAlways) {
				// save original text to reevaluate on the fly (AJAX or EVAL only)
				el.$tmp.myEvalAlwaysText = el.$tmp.myText;
			}
			try {
				eval('tmp = ' + el.$tmp.myText.replace(/EVAL:/i, '') + ';');
				var dual = tmp.split('::');
				if (dual.length > 1) {
					el.$tmp.myTitle = dual[0].trim();
					el.$tmp.myText = dual[1].trim();
				} else {
					el.$tmp.myTitle = false;
					el.$tmp.myText = tmp;
				}
			} catch(err) {
				el.$tmp.myTitle = this.options.errTitle;
				el.$tmp.myText = this.options.errText + '<br />--------<br />' + err.description;
			}
		}
		else if (el.$tmp.myText && el.$tmp.myText.test('^CALLBACK:', 'i')) {
			var tmp;
			
			if(this.options.evalAlways) {
				// save original text to reevaluate on the fly (AJAX or EVAL only)
				el.$tmp.myEvalAlwaysText = el.$tmp.myText;
			}
			try {
				
				var callback = function(tip){
					el.title = tip;
					this.build(el);
					this.start(el);

				}.bind(this);
				
				var preparefunction=el.$tmp.myText.replace(/CALLBACK:/i, '');
				eval('tmp = ' + preparefunction.replace(')','')  + 'callback);');
			} catch(err) {
				el.$tmp.myTitle = this.options.errTitle;
				el.$tmp.myText = this.options.errText + '<br />--------<br />' + err.description;
			}
		}
	
		if (el.$tmp.myTitle){
			this.title = new Element('span').inject(
				new Element('div', {'class': this.options.className + '-title'}).inject(this.wrapper)
			).setHTML(el.$tmp.myTitle);
		}
		if (el.$tmp.myText){
			this.text = new Element('span').inject(
				new Element('div', {'class': this.options.className + '-text'}).inject(this.wrapper)
			).setHTML(el.$tmp.myText);
			
			if((this.options.evalAlways) && (el.$tmp.myEvalAlwaysText)) {
				// reset text so that it will evaluate again
				el.$tmp.myText = el.$tmp.myEvalAlwaysText;
			}
		}
		$clear(this.timer);
		
		// setting initial state of tip
		this.toolTip.setProperty('state','mouseleave');
		
		this.timer = this.show.delay(this.options.showDelay, this);
	},

	end: function(event){
		$clear(this.timer);
		this.timer = this.hide.delay(this.options.hideDelay, this);
	},

	position: function(element){
		var pos = element.getPosition();
		this.toolTip.setStyles({
			'left': pos.x + this.options.offsets.x,
			'top': pos.y + this.options.offsets.y
		});
	},

	locate: function(event){
		var win = {'x': window.getWidth(), 'y': window.getHeight()};
		var scroll = {'x': window.getScrollLeft(), 'y': window.getScrollTop()};
		var tip = {'x': this.toolTip.offsetWidth, 'y': this.toolTip.offsetHeight};
		var prop = {'x': 'left', 'y': 'top'};
		for (var z in prop){
			var pos = event.page[z] + this.options.offsets[z];
			if ((pos + tip[z] - scroll[z]) > win[z]) pos = event.page[z] - this.options.offsets[z] - tip[z];
			this.toolTip.setStyle(prop[z], pos);
		};
	},

	show: function(){
		if (this.options.timeout) this.timer = this.hide.delay(this.options.timeout, this);
		this.fireEvent('onShow', [this.toolTip]);
	},

	hide: function(){
		// if "fixed", tooltip is only hidden when mouse leaves the tooltip (itself)
		if ((this.toolTip.getProperty('state') == 'mouseleave') || (!this.options.fixed))
			this.fireEvent('onHide', [this.toolTip]);
	}
});

MooTips.implement(new Events, new Options);
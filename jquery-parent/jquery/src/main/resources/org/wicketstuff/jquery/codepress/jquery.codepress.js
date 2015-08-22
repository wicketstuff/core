//##############################
// jQuery Codepress Plugin v1.1
// By Diego A. - diego@fyneworks.com
// 25-Jul-2007 - v1.0 - it begins...
// 12-Jan-2008 - v1.1 - plugin now follows the jquery philosophy of $(selector).plugin();
// 12-Jan-2008 - the new structure allows multiple configurations on one page
/*
 USAGE:
		$.codepress.start({ path:'/path/to/codepress/editor/' }); // initialize Codepress editor
		$.codepress.update(); // update value in textareas of each Codepress editor instance
*/

/*# AVOID COLLISIONS #*/
if(jQuery) (function($){
/*# AVOID COLLISIONS #*/

$.extend($, {
	codepress:{
		config: { }, // default configuration
		path: '/codepress/', // default path to Codepress directory
  list: [], // holds a list of instances
  loaded: false, // flag indicating whether Codepress script is loaded
		intercepted: null, // variable to store intercepted method(s)
		
		// utility method to read contents of Codepress editor
		content: function(i){
			try{
				return i.codepress.editor.getCode();
			}catch(e){ return ''; };
		}, // codepress.content function
		
		// utility method to update textarea contents before ajax submission
		update: function(){
			// Update contents of all instances
			var e = $.codepress.list;
			for(var i=0;i<e.length;i++){
				var ta = e[i].textarea;
				var T = $(e[i]);
				//if(T.size()>0){
				var ht = $.codepress.content(e[i]);
				T.text(ht).val(ht).attr('disabled',false);
				if(ht!=ta.val())
					alert('Critical error in Codepress plugin:'+'\n'+'Unable to update form data');
				//}
			};
		}, // codepress.update
		
		// utility method to create instances of Codepress editor (if any)
		create: function(o/* options */){
			o = $.extend($.codepress.config || {}, o || {});
			// Plugin options
			$.extend(o,{
				selector: (o.selector || 'textarea.code, textarea.codepress'),
			 path: (o.path || o.BasePath || $.codepress.path)
			});
			// Find fck.editor-instance 'wannabes'
			var e = $(o.e);
			if(!e.length>0) e = $(o.selector);
			if(!e.length>0) return;
			// Load script and create instances
			if($.codepress.loaded){
				$.codepress.editor(e,o);
			}
			else{
				$.getScript(
					o.path+'codepress.js',
					function(){
						$.codepress.loaded = true;
						$.codepress.editor(e,o);
					}
				);
			};
			// Return matched elements...
			return e;
		},
		
		// utility method to integrate this plugin with others...
		intercept: function(){
			if($.codepress.intercepted) return;
			// This method intercepts other known methods which
			// require up-to-date code from FCKEditor
			$.codepress.intercepted = {
			 ajaxSubmit: $.fn.ajaxSubmit || function(){}
			};
			$.fn.ajaxSubmit = function(){
				$.codepress.update(); // update html
				return $.codepress.intercepted.ajaxSubmit.apply( this, arguments );
			};
			// Also attach to conventional form submission
			$('form').submit(function(){
    $.codepress.update(); // update html
   });
		},
		
		// utility method to create an instance of Codepress editor
		editor: function(e /* elements */, o /* options */){
			o = $.extend($.codepress.config || {}, o || {});
			// Default configuration
			$.extend(o,{
			 width: (o.width || o.Width || '100%'),
			 //height: (o.height || o.Height|| '500px'),
			 height: (o.height || o.Height|| '100%'),
			 path: (o.path || $.codepress.path)
			});
			// Make sure we have a jQuery object
			e = $(e);
			if(e.size()>0){
				// Local array to store instances
				var a = ($.codepress.list || []);
				// Go through objects and initialize codepress.editor
				e.each(
					function(i,t){
						var T = $(t);// t = element, T = jQuery
						t.id = (t.id || 'codepress'+($.codepress.list.length+1));
						if(t.id/* has id */ && !t.codepress/* not already installed */){
							var n = a.length;
							
							//BUG: Sometimes, codepress creates an editor with height/width 0.
							// The next line will manually set the dimensions of the editor
							T.show().height(o.height).width(o.width);
							
							// OPTIONS: linenumbers-off, autocomplete-off and readonly-on
							var c0 = String(t.className), c2 = '';
							c2 += ' codepress';
							c2 += (' linenumbers-' + (!c0.match(/linenumbers/g)?'on':'off'));
							c2 += (' readonly-' + (c0.match(/readonly/g)?'on':'off'));
							c2 += ' '+(c0.match(/html|css|php|java|javascript|perl|ruby|sql|text/gi) || ['html'])[0];
							t.className = c2.replace(/(^\s+)|(\s+$)/gi,'');
							
							/*
							I use the class property to store non-html data and other javascript settings.
							The current codepress implementation does not allow me to have any data in the 
							class property other than the class names used by codepress.
							
							On line 40 of codepress.js:
							self.language = language ? language : self.options.replace(/ ?codepress ?| ?readonly-on ?| ?autocomplete-off ?| ?linenumbers-off ?/g,'');
							
							I'd like to suggest this flexible version, which allows other classes to be used:
							self.language = language ? language : (self.options.match(/html|css|php|java|javascript|perl|ruby|sql|text/gi) || ['generic'])[0];
							
							The modification looks like this:
											// Original:
											//self.language = language ? language : self.options.replace(/ ?codepress ?| ?readonly-on ?| ?autocomplete-off ?| ?linenumbers-off ?/g,'');
											// Allow other class names: (by diego.alto@gmail.com)
											self.language = language ? language : (self.options.match(/html|css|php|java|javascript|perl|ruby|sql|text/gi) || ['generic'])[0];
							*/
							// Initialize Codepress
							//CodePress.engine = CodePress.getEngine();
							CodePress.path = o.path;
							
							// Create Codepress instance
							t.codepress = new CodePress(t);
							
							// 1. Insert Editor
							// 2. Re-enable textarea (we still want to submit the data)
							//     - why does codepress disable the textarea?
							T.attr('disabled',false).parent().prepend(t.codepress);
							
							// Store element reference to be used later...
							a[n] = t;
							a[n].textarea = T;
						};
					}
				);
				// Store instances in global array
				$.codepress.list = a;
			};
			// return jQuery array of elements
		 return e;
		}, // codepress.editor function
		
		// start-up method
		start: function(o/* options */){
			// Attach itself to known plugins...
			$.codepress.intercept();
			// Create Codepress editors
			return $.codepress.create(o);
		} // codepress.start
		
 } // codepress object
	//##############################
	
});
// extend $
//##############################


$.extend($.fn, {
 codepress: function(o){
		$.codepress.start($.extend(o || {}, {e: this}));
	}
});
// extend $.fn
//##############################


/*# AVOID COLLISIONS #*/
})(jQuery);
/*# AVOID COLLISIONS #*/

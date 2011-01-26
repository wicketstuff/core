function ${connectFXTogglerFunction}(trigger, id, showEvent, hideEvent, startShown, showAnim, hideAnim) {
	var triggerNode = dojo.byId(trigger);
	var node = dojo.byId(id);

	if (!triggerNode || !node) {
		return;
	}
	
	var t = {
		node: id,
		showAnim: showAnim,
		hideAnim: hideAnim,
		shown: startShown,
		show: function() {
			this.shown = true;
			this.hideAnim.stop(true);
			dojo.removeClass(triggerNode, "${wiperHiddenClass}");
			t.showAnim.play();
			dojo.addClass(triggerNode, "${wiperShownClass}");
		},
		hide: function() {
			this.shown = false;
			this.showAnim.stop(true);
			dojo.removeClass(triggerNode, "${wiperShownClass}");
			t.hideAnim.play();
			dojo.addClass(triggerNode, "${wiperHiddenClass}");
		},
		toggle: function(){
			if (this.shown == true) {
				this.hide();
			} else {
				this.show();
			}
		}
	};
	dojo.subscribe("/"+id+"/toggle/show",t,t.show);
	dojo.subscribe("/"+id+"/toggle/hide",t,t.hide);
	dojo.subscribe("/"+id+"/toggle/toggle",t,t.toggle);

	if (showEvent == hideEvent) {
		dojo.connect(triggerNode, showEvent, t, t.toggle);
	} else {
		dojo.connect(triggerNode, showEvent, t, t.show);
		dojo.connect(triggerNode, hideEvent, t, t.hide);
	}
}
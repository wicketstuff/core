
if (typeof(Wicket) == "undefined") 
    Wicket = {};
if (typeof(Wicket.yui) == "undefined") 
    Wicket.yui = {};

/**
 * 
 * @param {Object} trigger_id
 * @param {Object} anim
 * @param {Object} element_id
 * @param {Object} element_value
 * @param {Object} animate_on_value
 */
Wicket.yui.AnimValueGroup = function(trigger_id, anim, element_id, element_value, animate_on_value){
    this.trigger_id = trigger_id;
    this.anim = anim;
    this.element_id = element_id;
    this.element_value = element_value;
    this.animate_on_value = animate_on_value;
    return this;
};

/**
 * constructor for an AnimGroup. Every trigger_id and trigger_event is a unique key.
 * it contains a map of array of animations keyed on it's animation_id.
 *
 * @param {Object} trigger_id
 * @param {Object} trigger_event
 */
Wicket.yui.AnimGroup = function(trigger_id, trigger_event){
    this.trigger_id = trigger_id;
    this.trigger_event = trigger_event;
    this.map = {};
    Wicket.yui.Animator.addAnimGroup(this);
    return this;
}

/**
 *
 * @param {Object} animation_id
 * @param {Object} anim_value_group
 */
Wicket.yui.AnimGroup.prototype.addAnim = function(animation_id, anim_value_group){
    if (!this.map[animation_id]) {
        this.map[animation_id] = [];
    }
    this.map[animation_id].push(anim_value_group);
}

/**
 * Animation is a Singleton. It keeps a list of AnimGroup objects.
 * Each AnimGroup has a unique trigger_id and trigger_event.
 * Each AnimGroup also has a map, key on the animation_id of the animation, containing an array
 * of anims (animation)
 *
 * upon an event(trigger_event) on an id(trigger_id), the AnimGroup object is passed into the
 * the callback function.
 * This function will call the animate() on the first anim in each array in the map. (mulitple triggers)
 * upon completion of each animation, the anim object is cycled to the end of the array. (chaining)
 *
 */
Wicket.yui.Animator = (function(){

    /**
     * the list of AnimGroup (tirgger_id/ event) that is created
     */
    var animGroupArray = [];
    
    return {
    
        /**
         *
         * @param {Object} animGroup
         */
        addAnimGroup: function(animGroup){
            animGroupArray[animGroupArray.length] = animGroup;
        },
        
        /**
         * returns the AnimGroup of this key
         *
         * @param {Object} trigger_id
         * @param {Object} trigger_event
         */
        getAnimGroup: function(trigger_id, trigger_event){
            for (i = 0, len = animGroupArray.length; i < len; i++) {
                var ag = animGroupArray[i];
                if ((ag) &&
                (ag.trigger_id == trigger_id) &&
                (ag.trigger_event == trigger_event)) {
                    return ag;
                }
            }
            return undefined;
        },
        
        /**
         * the callback function upon an event that was triggered.
         * this animator will take the animGroup and animation
         * all the first anim of each animation_id arrays
         *
         * @param {Object} e - the event
         * @param {Object} animGroup
         */
        callback: function(e, trigger_id){
			
            var animGroup = Wicket.yui.Animator.getAnimGroup(trigger_id, e.type);
			
			var storeValue = {};
			
            for (a_anim_id in animGroup.map) {
            
			    var animList = animGroup.map[a_anim_id];
			
			    if (animList) {
                    // splice first element an animate it
                    var avg = animList[0];
					var elem = document.getElementById(avg.element_id);
					
					if ((elem) &&
					   !(storeValue[avg.element_id])) {
						storeValue[avg.element_id] = elem.value;
					}
					
					if ((avg.animate_on_value) && 
					    (avg.element_value != storeValue[avg.element_id]))
				    {
						// noop 
					}		
					else 
					{
						avg.anim.animate();
						// update value
						if ((avg.element_id != null) &&
						    (!avg.animate_on_value) &&
							(elem)) {
								elem.value = avg.element_value;
						}
						// add this element back to the list
						avg = animList.splice(0, 1)[0];
                        animList.push(avg);
					}
                }
            }
        },
        
        /**
         * Adds an animation (anim) into this Animator. A Listener is created for each new AnimGroup
         *
         * @param {Object} trigger_ids : the list of trigger_ids that will activate this animatione
         * @param {Object} trigger_event : the trigger event
         * @param {Object} animation_id : the id of the animation - need this as key of the map
         * @param {Object} anim_value_object : the actual animation object that will be called "animate()" together with the value to update
         */
        add: function(anim_value_groups, trigger_event, animation_id){
        
            for (var i = 0, len = anim_value_groups.length; i < len; i++) {
				
                var avg = anim_value_groups[i];
                var trigger_id = avg.trigger_id;
                var animGroup = this.getAnimGroup(trigger_id, trigger_event);
                
                if (animGroup) {
                    animGroup.addAnim(animation_id, avg);
                }
                else { // create a new group, event 
                    animGroup = new Wicket.yui.AnimGroup(trigger_id, trigger_event);
                    animGroup.addAnim(animation_id, avg);
                    YAHOO.util.Event.addListener(trigger_id, trigger_event, this.callback, trigger_id, true);
                }
            }
        }
    };
})();


/* YAHOO COMPONENTS */


feedback = function() {   
   var attributes = {
      height: { to: 30 }
   };
   var attributesout = {
      height: { to: 0 }
   };
      
   //var animin = new YAHOO.util.Anim('feedbackpanel', attributes, 0.5, YAHOO.util.Easing.backOut);
   var animout = new YAHOO.util.Anim('feedbackpanel', attributesout, 0.5, YAHOO.util.Easing.backOut);
   //YAHOO.util.Event.on('footer', 'click', animin.animate, animin, true);
   YAHOO.util.Event.on('feedbackpanel', 'click', animout.animate, animout, true);
};

YAHOO.util.Event.onAvailable('footer', feedback);
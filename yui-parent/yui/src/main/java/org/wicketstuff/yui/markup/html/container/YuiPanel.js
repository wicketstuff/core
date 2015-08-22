if(typeof(WicketStuff)=='undefined') WicketStuff={}
if(typeof(WicketStuff.Yui)=='undefined') WicketStuff.Yui={}
if(typeof(WicketStuff.Yui.overlaymanager)=='undefined') WicketStuff.Yui.overlaymanager=new YAHOO.widget.OverlayManager()

WicketStuff.Yui.registerPanel = function(yuiPanelId, yuiPanelVar) {
 if(!WicketStuff.Yui.overlaymanager.find(yuiPanelId)) WicketStuff.Yui.overlaymanager.register(yuiPanelVar)
}
WicketStuff.Yui.deregisterPanel = function(yuiPanelVar) {
 WicketStuff.Yui.overlaymanager.remove(yuiPanelVar)
}

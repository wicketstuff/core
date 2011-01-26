function ${connectDojoDropContainerFunction} (target, url){
	var t = {
		target:dojo.byId(target),
		url:url,
		onDrop: function(source,nodes,iscopy) {
			if(nodes[0].parentNode != this.target) return;
			if (nodes.length > 1) console.warning("multi node drops not implemented");
			var dragId = nodes[0].id;
			
			// find position in drop container
			var position = -1;
			for (var i = 0; i < this.target.childNodes.length; i++) {
				if (this.target.childNodes[i] == nodes[0]) {
					position = i;
					break;
				}
			}
			
			var queryString = "&dragSource=" + dragId + "&position=" + position;
			console.debug("Query string: " + queryString);
			wicketAjaxGet(this.url + queryString, function() {}, function() {});
		}
	};
	dojo.subscribe("/dnd/drop", t, t.onDrop);
}
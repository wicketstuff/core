
/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/*
 * Wicket Openlayers Map
 *
 * @author Nino Martinez
 */

// Wicket Namespace
var Wicket;
if (!Wicket) {
	Wicket = {};
} else {
	if (typeof Wicket != "object") {
		throw new Error("Wicket already exists and is not an object");
	}
}
Wicket.omaps = {};
function WicketOMap(id, options) {
	// Default seems to be 0
	OpenLayers.IMAGE_RELOAD_ATTEMPTS = 5;
	Wicket.omaps[id] = this;
	if (options !== null) {
		this.map = new OpenLayers.Map(id, options);
	} else {
		this.map = new OpenLayers.Map(id);
	}
	this.controls = {};
	this.overlays = {};
	this.layers = {};
	this.div = id;
	this.openOverlays = new OpenLayers.Layer.Markers("markers" + id);
	this.map.addLayer(this.openOverlays);
	this.layers[1] = this.openOverlays;
	this.popup = null;
	this.popupId = "content";
	this.onEvent = function (callBack, params) {
		params["center"] = this.map.getCenter();
		params["bounds"] = this.map.getExtent();
		params["zoom"] = this.map.getZoomForExtent(this.map.getExtent(), false);
		for (var key in params) {
			callBack = callBack + "&" + key + "=" + params[key];
		}
		wicketAjaxGet(callBack, function () {
		}, function () {
		});
	};
	this.addLayer = function (layer, id) {
		var self = this;
		self.map.addLayer(layer);
		self.layers[id] = layer;
	};
	this.addFeature = function (feature, layer) {
		var self = this;
		layer.addFeatures([feature]);
	}
	this.setCenter = function (center, zoom) {
		var self = this;
		self.map.setCenter(center, zoom);		
	}
	this.zoomToMaxExtent = function () {
		var self = this;
		self.map.zoomToMaxExtent();
	};
	this.addListener = function (event, callBack) {
		var self = this;
		if (event == "click" || event == "dblclick") {
			Events.register(this.map, event, function (marker, gLatLng) {
				self.onEvent(callBack, {"marker":(marker === null ? "" : marker.overlayId), "latLng":gLatLng});
			});
		} else {
			Events.register(this.map, event, function () {
				self.onEvent(callBack, {});
			});
		}
	};
	this.addMoveEndListener = function (callBack) {
		var self = this;
		self.map.events.register("moveend", self.map, function (e) {
			self.onEvent(callBack, {});
		});
	};
	this.addClickListener = function (callBack) {
		var self = this;
		self.map.events.register("click", self.map, function (e) {
			var lonlat = self.map.getLonLatFromViewPortPx(e.xy);
			self.onEvent(callBack, {"lon":lonlat.lon, "lat":lonlat.lat});
		});
	};
	this.popupInfo = function (callBack, marker, wicketOMap, evt) {
		 //pass allong event!
		var event = "nullEvent";
		if (evt != null) {
			event = evt.type;
		}
		callBack = callBack + "&event=" + event;
		var wcall = wicketAjaxGet(callBack, function () {
		}, null, null);
	};
	this.getMarker = function (markerId) {
		var self = this;
		return self.overlays[markerId];
	};
	this.addMarkerListener = function (event, callBack, marker) {
		var self = this;
		marker.events.register(event, marker, function (evt) {
			self.popupInfo(callBack, marker, self, evt);
			OpenLayers.Event.stop(evt);
		});
	};
	this.addGOverlayListener = function (event, overlayID, callBack) {
		var self = this;
		if (event == "dragend") {
			var overlay = this.overlays[overlayID];
			Events.register(overlay, event, function () {
				self.onEvent(callBack, {"marker":overlayID, "latLng":overlay.getLatLng()});
			});
		}
	};
	this.setPopupId = function (popid) {
		var self = this;
		self.popupId = popid;
	};
	this.panDirection = function (dx, dy) {
		this.map.pan(dx, dy);
	};
	this.zoomOut = function () {
		var self = this;
		var zoomLevel = self.map.getZoom();
		zoomLevel = zoomLevel + 1;
		if (self.map.isValidZoomLevel(zoomLevel)) {
			self.map.zoomTo(zoomLevel);
		}
	};
	this.zoomIn = function () {
		var self = this;
		var zoomLevel = self.map.getZoom();
		zoomLevel = zoomLevel - 1;
		if (self.map.isValidZoomLevel(zoomLevel)) {
			self.map.zoomTo(zoomLevel);
		}
	};
	this.addControl = function (controlId, control) {
		this.controls[controlId] = control;
		this.map.addControl(control);
	};
	this.removeControl = function (controlId) {
		if (this.controls[controlId] !== null) {
			this.map.removeControl(this.controls[controlId]);
			this.controls[controlId] = null;
		}
	};
	//marker?
	this.addOverlay = function (overlayId, overlay) {
		this.overlays[overlayId] = overlay;
		overlay.overlayId = overlayId;
		this.openOverlays.addMarker(overlay);
	};
	this.removeOverlay = function (overlayId) {
		if (this.overlays[overlayId] !== null) {
			this.openOverlays.removeMarker(this.overlays[overlayId]);
			this.overlays[overlayId] = null;
		}
	};
	this.clearOverlays = function () {
		// preserve marker visibility..
		var visible = this.layers[1].getVisibility();
		this.overlays = {};
		this.map.removeLayer(this.openOverlays);
		this.openOverlays.destroy();
		this.openOverlays = new OpenLayers.Layer.Markers("markers" + this.div);
		this.map.addLayer(this.openOverlays);
		this.layers[1] = this.openOverlays;
		this.layers[1].setVisibility(visible);
	};
	this.toggleLayer = function (layerId) {
		var layer = this.layers[layerId];
		var visible = layer.getVisibility();
		layer.setVisibility(!visible);
	};
	
	/*used for draw*/
    this.serialize = function (feature) {
        var in_options = {
                'internalProjection': this.map.baseLayer.projection,
                'externalProjection': new OpenLayers.Projection("wkt")
            };   
        var format =new OpenLayers.Format.WKT(in_options);
        var str = format.write(feature, false);
        return str;
    }
	this.addDrawFeature = function (callBack){
	    this.drawLayer = new OpenLayers.Layer.Vector("Draw Layer");
	    this.map.addLayers(this.drawLayer);
		this.drawToolbar = new OpenLayers.Control.EditingToolbar(this.drawLayer);
		this.map.addControl(this.drawToolbar);
		var self = this;
	    for(var i = 0; i<this.drawToolbar.controls.length; i++) {
    	    var c = this.drawToolbar.controls[i];
        	c.events.on({
				"featureadded": function(e) {
	                var in_options = {
	                        'internalProjection': this.map.baseLayer.projection,
	                        'externalProjection': new OpenLayers.Projection("wkt")
	                    };   
	                var format =new OpenLayers.Format.WKT(in_options);
	                var str = format.write(e.feature, false);

					var callModded = callBack + "&wkt=" + str;
					var wcall = wicketAjaxGet(callModded, function () {}, null, null);
        	    }
    	    });
	    }
	}
	this.removeDrawFeature = function (){
		
		if(this.drawToolbar !=null){
			this.map.removeControl(this.drawToolbar);
			this.drawToolbar=null;
		}
		if(this.drawLayer !=null){
			this.drawLayer.destroy();
			this.drawLayer=null;
		}
		
	}

	
	
	
}


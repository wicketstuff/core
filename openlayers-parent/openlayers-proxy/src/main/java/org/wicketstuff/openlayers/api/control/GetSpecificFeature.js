/*
 * 
 * ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */


/*
 * A custom OpenLayers.Control class to get specific columns through a WFS GetFeature request.
 */
OpenLayers.Control.GetSpecificFeature = OpenLayers.Class(OpenLayers.Control, {                
    defaultHandlerOptions: {
    'single': true,
    'double': false,
    'pixelTolerance': 0,
    'stopSingle': false,
    'stopDouble': false
},

	/*
	 * The WFS url
	 */
	wfsUrl: null,
	
	/*
	 * The featureType
	 */
	featureTypeName: null,
	
	/*
	 * The srs number
	 */
	srsNumber: null, 
	
	/*
	 * Typically the WMS layer. It provides the Bounding Box.
	 */
	layer: null,
	
	/*
	 * The name of the property that will be extracted.
	 * 
	 */
	propertyName: null,
	
	/*
	 * The prefix for the featuretype
	 */
	featureTypePrefix: null, 
	
	/*
	 * the url for the feature type.
	 */
	featureTypeUrl: null,
	
	/*
	 * the url for GET request when a feature has been selected.
	 */
	featureSelectedCallback: null,
	
	

/* openlayers control related initialization */
initialize: function(options) {
    this.handlerOptions = OpenLayers.Util.extend(
        {}, this.defaultHandlerOptions
    );
    
    OpenLayers.Control.prototype.initialize.apply(this, [options]);

    this.handler = new OpenLayers.Handler.Click(
        this, {
            'click': this.onClick,
        }, this.handlerOptions
    );
}, 

// encode the bbox as a ogc spatial filter and return the
// generated xml.
getXMLFilter: function (bbox, x, y) {

	var pointFilter = new OpenLayers.Filter.Spatial({type: OpenLayers.Filter.Spatial.INTERSECTS,
		value: new OpenLayers.Geometry.Point (x, y)
	});
		
	var bboxFilter = new OpenLayers.Filter.Spatial({
        type: OpenLayers.Filter.Spatial.BBOX,
		value: bbox
    }); 
		
		
	var filters = new OpenLayers.Filter.Logical({
	    type: OpenLayers.Filter.Logical.AND,
	    filters:[ pointFilter, bboxFilter]
	              
	});
	
		var filter_1_1 = new OpenLayers.Format.Filter({version: "1.1.0"});
    var xml = new OpenLayers.Format.XML();

	return xml.write(filter_1_1.write(filters));

	
},

onClick: function(evt) {

	// bind the handler so that this == GetSpecificFeature.this inside the
	// handler
	var handler = OpenLayers.Function.bind((function(request) {
            // parse the GML document and extract the zone
			// that contained the point.
		
		var format = new WicketOpenLayersGetSpecificFeature();
		
		format.setupLayer(this.featureTypePrefix, this.featureTypeUrl, this.featureTypeName, this.propertyName);

		if (request.status == 200) {
			
			var data = format.readChildNodes(request.responseXML);		
		
			var propertyValue = data.collection.members[this.propertyName];
		
			// 	issue the callback only if there is a valid property selected.
			if (propertyValue) {
				// consider implementing the onSuccess, and onFailure scripts.
				
				var call = new Wicket.Ajax.Call(this.featureSelectedCallback + propertyValue, function() {}, function() {}, null, null);
				
     			 call.call(); 
			}
			else
				alert('nothing selected.');
		
// alert('value = ' + zone);
		}


	 }), this);
	
	var lonlat = this.layer.getLonLatFromViewPortPx(evt.xy);

	
	var requestParams = {
		url: this.wfsUrl,
		params: {
		
			request: 'GetFeature',
			service: 'WFS',
			version: '1.1.0',
			maxFeatures: 1,
			typeName: this.featureTypeName,
			srs: 'urn:x-ogc:def:crs:EPSG:' + this.srsNumber,
			filter: this.getXMLFilter (this.layer.getExtent(), lonlat.lon, lonlat.lat),
			propertyName: this.propertyName
		},

		callback: handler
	};


 OpenLayers.Request.GET(requestParams);
 

}
});


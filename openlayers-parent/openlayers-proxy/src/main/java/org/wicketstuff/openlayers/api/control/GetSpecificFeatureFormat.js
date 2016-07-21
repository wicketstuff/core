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
 * Created to  parse the WFS specific GML returned from the OpenLayers.Control.GetSpecificFeature control.
 * 
 * Currently only supports a single property name.
 */
WicketOpenLayersGetSpecificFeature = OpenLayers.Class(OpenLayers.Format.XML, {
					defaultPrefix: "gml",
					namespaces: {
						"ogc": "http://www.opengis.net/ogc",
						"tiger": "http://www.census.gov",
						"wfs": "http://www.opengis.net/wfs",
						"topp": "http://www.openplans.org/topp",
						"xsi": "http://www.w3.org/2001/XMLSchema-instance",
						"sf": "http://www.openplans.org/spearfish",
						"ows": "http://www.opengis.net/ows",
						"gml": "http://www.opengis.net/gml",
						"xlink": "http://www.w3.org/1999/xlink"
					},

					 readers: {

						 "wfs": {
						 	"FeatureCollection": function (node, obj) {

								var collection = {}

								this.readChildNodes (node, collection);

								obj.collection = collection;
							},
						 },

						 "gml": { 
						 	"featureMembers": function (node, obj) {

								var members = [];

								this.readChildNodes (node, members);

								obj.members = members;
								
							}
						 },

					  },
 			  /*
			   * I can't seem to figure out how to insert these two in the readers constructor definition.  
			   */
			  setupLayer: function (prefixName, nameSpaceUrl, featureTypeName, propertyName) {
				 
				 // this is a hack to get the XML methods available in the
				 // nested methods where 'this' does not refer to the outer
				 // scope anymore.
				 var gmlFormat = this;

				  gmlFormat.namespaces[prefixName] = nameSpaceUrl;

					// copied from XML.initialize() and is required so that the
					// namespace can be resolved and locate the node parsing
					// methods approproately.
				   gmlFormat.namespaceAlias[gmlFormat.namespaces[prefixName]] = prefixName;
        


				  prefix = gmlFormat.readers[prefixName];
				 
				 if (!prefix) {
				 	prefix = {};
					gmlFormat.readers[prefixName] = prefix;
				}
				  prefix[featureTypeName] = function (node, feature) {
					
						var details = {};

						gmlFormat.readChildNodes (node, details);

						feature[propertyName] = details[propertyName];


					};
					
				
					prefix[propertyName] = function (node, feature) {
						feature[propertyName] = gmlFormat.getChildValue(node);
					};
					
				},

				CLASS_NAME: "OpenLayers.Format.GetSpecificFeature",
				});
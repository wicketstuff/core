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
package org.wicketstuff.openlayers.api.control;

/**
 * @author mocleiri
 * 
 * Options for the SelectionFeaturesControl
 * 
 * To start with only the boolean's are implemented and the javascript callback functionality.
 *
 */
public interface ISelectFeatureControlOptions {
	
	// from: http://dev.openlayers.org/releases/OpenLayers-2.11/doc/apidocs/files/OpenLayers/Control/SelectFeature-js.html
	//
//	multiple	{Boolean} Allow selection of multiple geometries.
//	clickout	{Boolean} Unselect features when clicking outside any feature.
//	toggle	{Boolean} Unselect a selected feature on click.
//	hover	{Boolean} Select on mouse over and deselect on mouse out.
//	highlightOnly	{Boolean} If true do not actually select features (that is place them in the layer’s selected features array), just highlight them.
//	box	{Boolean} Allow feature selection by drawing a box.

	
	boolean isMultipleSelectEnabled();
	
	String getMultipleSelectKey();
	
	boolean isClickoutEnabled();
	
	boolean isToggleByMouseEnabled();
	
	public String getSelectionToggleKey();
	
	boolean isHoverEnabled();
	
	boolean isHighlightOnlyEnabled();
	
	boolean isBoxSelectionEnabled();
	
//	onSelect	{Function} Optional function to be called when a feature is selected.
//	onUnselect	{Function} Optional function to be called when a feature is unselected.
	
	public String getOnSelectFeatureJavascript();
	
	public String getOnUnselectFeatureJavascript();
	
	// TODO: support feature handlers
	// OpenLayers.Handler.Feature
	// click, clickout, over, out, and dblclick.
	
}
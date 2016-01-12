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
package org.wicketstuff.openlayers.api.feature;

import java.io.Serializable;

import org.wicketstuff.openlayers.IOpenLayersMap;

/**
 * 
 * @author Marin Mandradjiev (marinsm@hotmail.com)
 * 
 */
public class FeatureStyle implements Serializable
{
	private static final long serialVersionUID = 9080128798875425807L;

	private final String extendDefaultStyleName;
	private final FeatureStyle extendStyle;

	private Boolean fill = null;
	private String fillColor = null;
	private Double fillOpacity = null;
	private Boolean stroke = null;
	private String strokeColor = null;
	private Double strokeOpacity = null;
	private Double strokeWidth = null;
	private String strokeLinecap = null;
	private String strokeDashstyle = null;
	private Boolean graphic = null;
	private Double pointRadius = null;
	private String pointerEvents = null;
	private String cursor = null;
	private String externalGraphic = null;
	private Double graphicWidth = null;
	private Double graphicHeight = null;
	private Double graphicOpacity = null;
	private Double graphicXOffset = null;
	private Double graphicYOffset = null;
	private Double graphicZIndex = null;
	private String graphicName = null;
	private String graphicTitle = null;
	private String backgroundGraphic = null;
	private Double backgroundGraphicZIndex = null;
	private Double backgroundXOffset = null;
	private Double backgroundYOffset = null;
	private Double backgroundHeight = null;
	private Double backgroundWidth = null;
	private String label = null;
	private String labelAlign = null;
	private String fontColor = null;
	private String fontFamily = null;
	private String fontSize = null;
	private String fontWeight = null;
	private String display = null;

	public FeatureStyle(String extendDefaultStyleName)
	{
		this.extendDefaultStyleName = extendDefaultStyleName;
		extendStyle = null;
	}

	public FeatureStyle(FeatureStyle extendStyle)
	{
		extendDefaultStyleName = null;
		this.extendStyle = extendStyle;
	}

	public String getId()
	{
		return String.valueOf(System.identityHashCode(this));
	}

	private String convert(String name, Object object)
	{
		if (object != null)
		{
			return "layer_style" + getId() + "." + name + " = " + object + ";\n";
		}
		return "";
	}

	private String convert(String name, String object)
	{
		if (object != null)
		{
			return "layer_style" + getId() + "." + name + " = '" + object + "';\n";
		}
		return "";
	}

	public String getJSAddStyle(IOpenLayersMap map)
	{
		StringBuffer result = new StringBuffer();
		result.append("var layer_style" + getId() + " = OpenLayers.Util.extend({}, ");
		result.append(extendDefaultStyleName != null ? "OpenLayers.Feature.Vector.style['" +
			extendDefaultStyleName + "']" : extendStyle.getJSGetStyleNoLineEnd(map));
		result.append(");\n");
		result.append(convert("fill", fill));
		result.append(convert("fillColor", fillColor));
		result.append(convert("fillOpacity", fillOpacity));
		result.append(convert("stroke", stroke));
		result.append(convert("strokeColor", strokeColor));
		result.append(convert("strokeOpacity", strokeOpacity));
		result.append(convert("strokeWidth", strokeWidth));
		result.append(convert("strokeLinecap", strokeLinecap));
		result.append(convert("strokeDashstyle", strokeDashstyle));
		result.append(convert("graphic", graphic));
		result.append(convert("pointRadius", pointRadius));
		result.append(convert("pointerEvents", pointerEvents));
		result.append(convert("cursor", cursor));
		result.append(convert("externalGraphic", externalGraphic));
		result.append(convert("graphicWidth", graphicWidth));
		result.append(convert("graphicHeight", graphicHeight));
		result.append(convert("graphicOpacity", graphicOpacity));
		result.append(convert("graphicXOffset", graphicXOffset));
		result.append(convert("graphicYOffset", graphicYOffset));
		result.append(convert("graphicZIndex", graphicZIndex));
		result.append(convert("graphicName", graphicName));
		result.append(convert("graphicTitle", graphicTitle));
		result.append(convert("backgroundGraphic", backgroundGraphic));
		result.append(convert("backgroundGraphicZIndex", backgroundGraphicZIndex));
		result.append(convert("backgroundXOffset", backgroundXOffset));
		result.append(convert("backgroundYOffset", backgroundYOffset));
		result.append(convert("backgroundHeight", backgroundHeight));
		result.append(convert("backgroundWidth", backgroundWidth));
		result.append(convert("label", label));
		result.append(convert("labelAlign", labelAlign));
		result.append(convert("fontColor", fontColor));
		result.append(convert("fontFamily", fontFamily));
		result.append(convert("fontSize", fontSize));
		result.append(convert("fontWeight", fontWeight));
		result.append(convert("display", display));
		result.append(map.getJSinvoke("addFeatureStyle(layer_style" + getId() + ", " + getId() +
			")"));
		return result.toString();
	}

	public String getJSGetStyle(IOpenLayersMap map)
	{
		return map.getJSinvoke("getFeatureStyle(" + getId() + ")");
	}

	public String getJSGetStyleNoLineEnd(IOpenLayersMap map)
	{
		return map.getJSinvokeNoLineEnd("getFeatureStyle(" + getId() + ")");
	}

	public String getJSRemoveStyle(IOpenLayersMap map)
	{
		return map.getJSinvoke("removeFeatureStyle(" + getId() + ")");
	}

	public void setFill(Boolean fill)
	{
		this.fill = fill;
	}

	public Boolean getFill()
	{
		return fill;
	}

	public void setFillColor(String fillColor)
	{
		this.fillColor = fillColor;
	}

	public String getFillColor()
	{
		return fillColor;
	}

	public void setFillOpacity(Double fillOpacity)
	{
		this.fillOpacity = fillOpacity;
	}

	public Double getFillOpacity()
	{
		return fillOpacity;
	}

	public void setStroke(Boolean stroke)
	{
		this.stroke = stroke;
	}

	public Boolean getStroke()
	{
		return stroke;
	}

	public void setStrokeColor(String strokeColor)
	{
		this.strokeColor = strokeColor;
	}

	public String getStrokeColor()
	{
		return strokeColor;
	}

	public void setStrokeOpacity(Double strokeOpacity)
	{
		this.strokeOpacity = strokeOpacity;
	}

	public Double getStrokeOpacity()
	{
		return strokeOpacity;
	}

	public void setStrokeWidth(Double strokeWidth)
	{
		this.strokeWidth = strokeWidth;
	}

	public Double getStrokeWidth()
	{
		return strokeWidth;
	}

	public void setStrokeLinecap(String strokeLinecap)
	{
		this.strokeLinecap = strokeLinecap;
	}

	public String getStrokeLinecap()
	{
		return strokeLinecap;
	}

	public void setStrokeDashstyle(String strokeDashstyle)
	{
		this.strokeDashstyle = strokeDashstyle;
	}

	public String getStrokeDashstyle()
	{
		return strokeDashstyle;
	}

	public void setGraphic(Boolean graphic)
	{
		this.graphic = graphic;
	}

	public Boolean getGraphic()
	{
		return graphic;
	}

	public void setPointRadius(Double pointRadius)
	{
		this.pointRadius = pointRadius;
	}

	public Double getPointRadius()
	{
		return pointRadius;
	}

	public void setPointerEvents(String pointerEvents)
	{
		this.pointerEvents = pointerEvents;
	}

	public String getPointerEvents()
	{
		return pointerEvents;
	}

	public void setCursor(String cursor)
	{
		this.cursor = cursor;
	}

	public String getCursor()
	{
		return cursor;
	}

	public void setExternalGraphic(String externalGraphic)
	{
		this.externalGraphic = externalGraphic;
	}

	public String getExternalGraphic()
	{
		return externalGraphic;
	}

	public void setGraphicWidth(Double graphicWidth)
	{
		this.graphicWidth = graphicWidth;
	}

	public Double getGraphicWidth()
	{
		return graphicWidth;
	}

	public void setGraphicHeight(Double graphicHeight)
	{
		this.graphicHeight = graphicHeight;
	}

	public Double getGraphicHeight()
	{
		return graphicHeight;
	}

	public void setGraphicOpacity(Double graphicOpacity)
	{
		this.graphicOpacity = graphicOpacity;
	}

	public Double getGraphicOpacity()
	{
		return graphicOpacity;
	}

	public void setGraphicXOffset(Double graphicXOffset)
	{
		this.graphicXOffset = graphicXOffset;
	}

	public Double getGraphicXOffset()
	{
		return graphicXOffset;
	}

	public void setGraphicYOffset(Double graphicYOffset)
	{
		this.graphicYOffset = graphicYOffset;
	}

	public Double getGraphicYOffset()
	{
		return graphicYOffset;
	}

	public void setGraphicZIndex(Double graphicZIndex)
	{
		this.graphicZIndex = graphicZIndex;
	}

	public Double getGraphicZIndex()
	{
		return graphicZIndex;
	}

	public void setGraphicName(String graphicName)
	{
		this.graphicName = graphicName;
	}

	public String getGraphicName()
	{
		return graphicName;
	}

	public void setGraphicTitle(String graphicTitle)
	{
		this.graphicTitle = graphicTitle;
	}

	public String getGraphicTitle()
	{
		return graphicTitle;
	}

	public void setBackgroundGraphic(String backgroundGraphic)
	{
		this.backgroundGraphic = backgroundGraphic;
	}

	public String getBackgroundGraphic()
	{
		return backgroundGraphic;
	}

	public void setBackgroundGraphicZIndex(Double backgroundGraphicZIndex)
	{
		this.backgroundGraphicZIndex = backgroundGraphicZIndex;
	}

	public Double getBackgroundGraphicZIndex()
	{
		return backgroundGraphicZIndex;
	}

	public void setBackgroundXOffset(Double backgroundXOffset)
	{
		this.backgroundXOffset = backgroundXOffset;
	}

	public Double getBackgroundXOffset()
	{
		return backgroundXOffset;
	}

	public void setBackgroundYOffset(Double backgroundYOffset)
	{
		this.backgroundYOffset = backgroundYOffset;
	}

	public Double getBackgroundYOffset()
	{
		return backgroundYOffset;
	}

	public void setBackgroundHeight(Double backgroundHeight)
	{
		this.backgroundHeight = backgroundHeight;
	}

	public Double getBackgroundHeight()
	{
		return backgroundHeight;
	}

	public void setBackgroundWidth(Double backgroundWidth)
	{
		this.backgroundWidth = backgroundWidth;
	}

	public Double getBackgroundWidth()
	{
		return backgroundWidth;
	}

	public void setLabel(String label)
	{
		this.label = label;
	}

	public String getLabel()
	{
		return label;
	}

	public void setLabelAlign(String labelAlign)
	{
		this.labelAlign = labelAlign;
	}

	public String getLabelAlign()
	{
		return labelAlign;
	}

	public void setFontColor(String fontColor)
	{
		this.fontColor = fontColor;
	}

	public String getFontColor()
	{
		return fontColor;
	}

	public void setFontFamily(String fontFamily)
	{
		this.fontFamily = fontFamily;
	}

	public String getFontFamily()
	{
		return fontFamily;
	}

	public void setFontSize(String fontSize)
	{
		this.fontSize = fontSize;
	}

	public String getFontSize()
	{
		return fontSize;
	}

	public void setFontWeight(String fontWeight)
	{
		this.fontWeight = fontWeight;
	}

	public String getFontWeight()
	{
		return fontWeight;
	}

	public void setDisplay(String display)
	{
		this.display = display;
	}

	public String getDisplay()
	{
		return display;
	}
}

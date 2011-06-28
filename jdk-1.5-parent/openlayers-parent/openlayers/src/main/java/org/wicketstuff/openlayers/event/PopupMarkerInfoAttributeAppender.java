package org.wicketstuff.openlayers.event;

import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.openlayers.OpenLayersMap;
import org.wicketstuff.openlayers.api.Marker;

/**
 * Add this if you want to be able to open a markers popup info window externally
 * 
 * @author Nino Martinez Wael (nino.martinez@jayway.dk)
 * 
 */
public class PopupMarkerInfoAttributeAppender extends AttributeAppender
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final IModel<String> appendModel;

	public PopupMarkerInfoAttributeAppender(String attribute, String separator, Marker marker,
		OpenLayersMap map)
	{
		super(attribute, new Model<String>(), separator);
		appendModel = (IModel<String>)getReplaceModel();
		String markerId = marker.getId();
		String callBackUrl = map.getCallbackListener().getCallBackForMarker(marker);

		appendModel.setObject(map.getJSinvoke("popupInfo('" + callBackUrl + "'," +
			map.getJSinvokeNoLineEnd("getMarker(" + markerId + ")") + "," + map.getJSInstance() +
			", null)"));

	}

}

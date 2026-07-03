package org.wicketstuff.simile.timeline.json;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import org.apache.wicket.ajax.json.JSONFunction;
import org.wicketstuff.simile.timeline.model.BandInfoParameters;
import org.wicketstuff.simile.timeline.model.BandInfoParameters.DateTime;

import com.github.openjson.JSONArray;
import com.github.openjson.JSONException;
import com.github.openjson.JSONObject;

public class JsonUtils
{
	public String convertBandInfos(Collection<BandInfoParameters> bandInfos)
	{
		JSONArray bandInfo = new JSONArray();
		for (BandInfoParameters params : bandInfos) {
			// from JSONObject objectAsMap(Object bean)
			Map<String, Object> props = new TreeMap<>();
			try {
				PropertyDescriptor[] properties = Introspector.getBeanInfo(params.getClass(), Object.class).getPropertyDescriptors();
				for (PropertyDescriptor prop : properties) {
					Object v = prop.getReadMethod().invoke(params);
					if (v instanceof DateTime dti) {
						v = new JSONFunction("Timeline.DateTime." + dti.name());
					} else if (v instanceof Date dt) {
						String dateString = new SimpleDateFormat("yyyy").format(dt);
						v = new JSONFunction("Timeline.DateTime.parseGregorianDateTime('" + dateString + "')");
					}
					props.put(prop.getDisplayName(), JSONObject.wrap(v));
				}
			} catch (IllegalAccessException|IntrospectionException|InvocationTargetException e) {
				throw new JSONException(e);
			}

			bandInfo.put(new JSONFunction("Timeline.createBandInfo(" + new JSONObject(props) + ")"));
		}

		return bandInfo.toString();
	}
}

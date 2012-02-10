package org.wicketstuff.simile.timeline.json;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonBeanProcessor;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.WebUtils;

import org.wicketstuff.simile.timeline.model.BandInfoParameters;
import org.wicketstuff.simile.timeline.model.BandInfoParameters.DateTime;
import org.wicketstuff.simile.timeline.model.JsonConstructor;
import org.wicketstuff.simile.timeline.model.RawString;

public class JsonUtils
{
	private JsonConfig jsonConfig;

	public String convertBandInfos(Collection<BandInfoParameters> bandInfos)
	{
		JSONArray bandInfo = new JSONArray();
		for (BandInfoParameters params : bandInfos)
		{
			bandInfo.add(
				new JsonConstructor<BandInfoParameters>("Timeline.createBandInfo", params),
				jsonConfig);
		}

		return WebUtils.toString(bandInfo);
	}

	public JsonUtils()
	{
		jsonConfig = new JsonConfig();

		jsonConfig.registerJsonValueProcessor(DateTime.class, new JsonValueProcessor()
		{
			public Object processArrayValue(Object arg0, JsonConfig arg1)
			{
				throw new UnsupportedOperationException();
			}

			public Object processObjectValue(String arg0, Object arg1, JsonConfig arg2)
			{
				return new JSONRawString("Timeline.DateTime." + ((DateTime)arg1).toString());
			}

		});
		jsonConfig.registerJsonBeanProcessor(JsonConstructor.class, new JsonBeanProcessor()
		{

			public JSONObject processBean(Object arg0, JsonConfig arg1)
			{
				return (JSONObject)arg1.findJsonValueProcessor(JsonConstructor.class)
					.processObjectValue(null, arg0, arg1);
			}

		});
		jsonConfig.registerJsonValueProcessor(JsonConstructor.class, new JsonValueProcessor()
		{
			public Object processArrayValue(Object arg0, JsonConfig arg1)
			{
				return processObjectValue(null, arg0, arg1);
			}

			public Object processObjectValue(String arg0, Object arg1, JsonConfig arg2)
			{
				JsonConstructor<?> constructor = (JsonConstructor<?>)arg1;
				return new JSONRawString(constructor.getName() + "(" +
					WebUtils.toString(JSONObject.fromObject(constructor.getObject(), arg2)) + ")");
			}

		});
		jsonConfig.registerJsonValueProcessor(RawString.class, new JsonValueProcessor()
		{
			public Object processArrayValue(Object arg0, JsonConfig arg1)
			{
				throw new UnsupportedOperationException();
			}

			public Object processObjectValue(String arg0, Object arg1, JsonConfig arg2)
			{
				return new JSONRawString(((RawString)arg1).toString());
			}

		});
		jsonConfig.registerJsonValueProcessor(RawString.class, new JsonValueProcessor()
		{
			public Object processArrayValue(Object arg0, JsonConfig arg1)
			{
				throw new UnsupportedOperationException();
			}

			public Object processObjectValue(String arg0, Object arg1, JsonConfig arg2)
			{
				return new JSONRawString(((RawString)arg1).toString());
			}

		});
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonValueProcessor()
		{
			public Object processArrayValue(Object arg0, JsonConfig arg1)
			{
				throw new UnsupportedOperationException();
			}

			public Object processObjectValue(String arg0, Object arg1, JsonConfig arg2)
			{
				Date date = (Date)arg1;
				String dateString = new SimpleDateFormat("yyyy").format(date);
				String retval = "Timeline.DateTime.parseGregorianDateTime(\"" + dateString + "\")";
				return new JSONRawString(retval);
			}

		});
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonValueProcessor()
		{
			public Object processArrayValue(Object arg0, JsonConfig arg1)
			{
				throw new UnsupportedOperationException();
			}

			public Object processObjectValue(String arg0, Object arg1, JsonConfig arg2)
			{
				Date date = (Date)arg1;
				String dateString = new SimpleDateFormat("yyyy").format(date);
				String retval = "Timeline.DateTime.parseGregorianDateTime(\"" + dateString + "\")";
				return new JSONRawString(retval);
			}

		});

	}
}

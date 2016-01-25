package org.wicketstuff.datatables.virtualscroll;

import org.apache.wicket.ajax.json.JSONArray;
import org.apache.wicket.ajax.json.JSONObject;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.CharSequenceResource;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;

import java.util.Iterator;
import java.util.Locale;

/**
 * An abstract resource reference that could be used as a base for custom implementations
 */
public abstract class AbstractVirtualScrollResourceReference<T> extends ResourceReference {

	/*
	 * The names of the request parameters sent by DataTables when "serverSide" option is enabled
	 */
	public static final String LENGTH_PARAMETER = "length";
	public static final String START_PARAMETER = "start";
	public static final String DRAW_PARAMETER = "draw";
	public static final String ORDER_0_COLUMN_PARAMETER = "order[0][column]";
	public static final String ORDER_0_DIRECTION_PARAMETER = "order[0][dir]";
	public static final String ASCENDING_DIRECTION = "asc";
	public static final String DESCENDING_DIRECTION = "desc";
	public static final String SEARCH_VALUE_PARAMETER = "search[value]";
	public static final String SEARCH_REGEX_PARAMETER = "search[regex]";

	/*
	 * The names of the fields in the JSON response
	 */
	public static final String RECORDS_TOTAL_RESPONSE_FIELD = "recordsTotal";
	public static final String RECORDS_FILTERED_RESPONSE_FIELD = "recordsFiltered";
	public static final String DRAW_RESPONSE_FIELD = "draw";
	public static final String DATA_RESPONSE_FIELD = "data";

	public AbstractVirtualScrollResourceReference(final Key key) {
		super(key);
	}

	public AbstractVirtualScrollResourceReference(final Class<?> scope, final String name, final Locale locale, final String style,
	                                              final String variation) {
		super(scope, name, locale, style, variation);
	}

	public AbstractVirtualScrollResourceReference(final Class<?> scope, final String name) {
		super(scope, name);
	}

	public AbstractVirtualScrollResourceReference(final String name) {
		super(name);
	}

	@Override
	public IResource getResource() {
		return new CharSequenceResource("application/json") {
			@Override
			protected CharSequence getData(final Attributes attributes) {
				PageParameters parameters = attributes.getParameters();
				WebResponse webResponse = (WebResponse) attributes.getResponse();
				webResponse.disableCaching();

				String response = generateResponse(parameters);
				return response;
			}
		};
	}

	/**
	 * Generates the JSON response for infinite scrolling/paging.
	 * See <a href="https://datatables.net/manual/server-side">DataTables server side docs.</a>
	 *
	 * @param parameters The request parameters
	 * @return
	 */
	protected String generateResponse(PageParameters parameters) {
		int length = parameters.get(LENGTH_PARAMETER).toInt(getDefaultLength());

		int start = parameters.get(START_PARAMETER).toInt(getDefaultStart());
		IDataProvider<T> dataProvider = getDataProvider(parameters);

		Iterator<? extends T> dataItor = dataProvider.iterator(start, length);

		JSONObject responseJson = new JSONObject();
		JSONArray data = new JSONArray();
		responseJson.put(DATA_RESPONSE_FIELD, data);

		while (dataItor.hasNext()) {
			T dataEntry = dataItor.next();

			JSONObject entryJson = new JSONObject();
			populateEntryJson(entryJson, dataEntry);
			data.put(entryJson);
		}

		responseJson.put(DRAW_RESPONSE_FIELD, parameters.get(DRAW_PARAMETER).toInt(1));
		populateDataJson(responseJson, dataProvider);
		return responseJson.toString(2);
	}

	protected void populateDataJson(final JSONObject response, final IDataProvider<T> dataProvider) {
		long size = dataProvider.size();
		response.put(RECORDS_TOTAL_RESPONSE_FIELD, size);
		response.put(RECORDS_FILTERED_RESPONSE_FIELD, size);
	}

	/**
	 * A callback method that should populate the JSON object for a single entry
	 * in the response
	 *
	 * @param entryJson The JSON object to populate
	 * @param dataEntry The data entry returned by the {@link IDataProvider data provider}
	 */
	protected abstract void populateEntryJson(final JSONObject entryJson, final T dataEntry);

	/**
	 * @return The provider of the data for the table
	 */
	protected abstract IDataProvider<T> getDataProvider(PageParameters parameters);

	/**
	 * @return A default value for the <em>start</em> request parameter
	 *      for the cases when DataTables sends non-numeric value, like Nan
	 */
	protected int getDefaultStart() {
		return 0;
	}

	/**
	 * Sometimes DataTables confuses itself and send NaN as a value for the
	 * <em>length</em> parameter.
	 *
	 * @return The default value of length when DataTables cannot calculate a good value for some reason
	 */
	protected int getDefaultLength() {
		return 100;
	}
}

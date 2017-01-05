package org.wicketstuff.datatables.demo.infiniteScroll;

import org.apache.wicket.ajax.json.JSONObject;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.datatables.demo.PeopleDataProvider;
import org.wicketstuff.datatables.demo.Person;
import org.wicketstuff.datatables.virtualscroll.AbstractVirtualScrollResourceReference;

/**
 * A resource reference that delivers data for the demo  of virtual scrolling
 */
public class VirtualScrollDemoResourceReference extends AbstractVirtualScrollResourceReference<Person> {

	public VirtualScrollDemoResourceReference() {
		super("inifiniteScroll");
	}

	@Override
	protected void populateDataJson(final JSONObject response, final IDataProvider<Person> dataProvider) {
		// use a really big number for a good demo of virtual scrolling
		int size = 500000;
		response.put(RECORDS_TOTAL_RESPONSE_FIELD, size);
		response.put(RECORDS_FILTERED_RESPONSE_FIELD, size);
	}

	@Override
	protected void populateEntryJson(final JSONObject entryJson, final Person person) {
		entryJson.put("DT_RowId", "PK_" + person.number);
		entryJson.put("DT_RowClass", "custom");
		entryJson.put("firstName", person.firstName);
		entryJson.put("lastName", person.lastName);
		entryJson.put("age", person.age);
	}

	@Override
	protected IDataProvider<Person> getDataProvider(PageParameters parameters) {
		return new PeopleDataProvider();
	}
}

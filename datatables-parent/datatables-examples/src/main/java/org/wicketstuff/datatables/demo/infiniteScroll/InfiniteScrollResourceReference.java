package org.wicketstuff.datatables.demo.infiniteScroll;

import org.apache.wicket.ajax.json.JSONArray;
import org.apache.wicket.ajax.json.JSONObject;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.CharSequenceResource;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;
import org.wicketstuff.datatables.demo.PeopleDataProvider;
import org.wicketstuff.datatables.demo.Person;

import java.util.Iterator;

/**
 *
 */
public class InfiniteScrollResourceReference extends ResourceReference {

	public InfiniteScrollResourceReference() {
		super("inifiniteScroll");
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

	private String generateResponse(PageParameters parameters) {
//		"length", start, ;
		int length = parameters.get("length").toInt(10);
		if (length == -1) {
			length = 10;
		}
		length += 120;
		int start = parameters.get("start").toInt(0);
		PeopleDataProvider dataProvider = new PeopleDataProvider();
		Iterator<? extends Person> peopleItor = dataProvider.iterator(start, length);

		JSONObject response = new JSONObject();
		JSONArray data = new JSONArray();
		response.put("data", data);

		while (peopleItor.hasNext()) {
			Person person = peopleItor.next();
			JSONArray personData = new JSONArray();

			// make sure this is a "truthy" value, otherwise DataTables will skip it
			personData.put("PK_" + person.number);

			personData.put(person.firstName);
			personData.put(person.lastName);
			personData.put(person.age);
			data.put(personData);
		}

		response.put("recordsTotal", 500000);
		response.put("recordsFiltered", 500000);
		response.put("draw", parameters.get("draw").toInt(1));
		return response.toString(2);
	}
}

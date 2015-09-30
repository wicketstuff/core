package org.wicketstuff.lambda.table;

import java.util.Arrays;

import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.Model;
import org.wicketstuff.lambda.OptionalFunction;
import org.wicketstuff.lambda.model.Person;

public class TablePage extends WebPage {

	public TablePage() {
		super();
		add(new DataTable<>("table", 
				Arrays.asList(
						new FunctionColumn<>(Model.of("Name"), new OptionalFunction<>(Person.NAME)),
						new FunctionColumn<>(Model.of("Boss Name"), new OptionalFunction<>(Person.BOSS_NAME))), 
				new ListDataProvider<>(Arrays.asList(
						new Person("X", new Person("XB")),
						new Person("Y", new Person("YB")),
						new Person("Z", new Person("ZB")))), 
				10));
	}
	
}

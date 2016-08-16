package org.wicketstuff.lambda.example;

import static org.wicketstuff.lambda.example.Person.GETTER_OP_ADDRESS_CITY;
import static org.wicketstuff.lambda.example.Person.GETTER_OP_ADDRESS_STREET;
import static org.wicketstuff.lambda.example.Person.GETTER_OP_NAME;
import static org.wicketstuff.lambda.example.Person.SETTER_ADDRESS_CITY;
import static org.wicketstuff.lambda.example.Person.SETTER_ADDRESS_STREET;
import static org.wicketstuff.lambda.example.Person.SETTER_NAME;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.lambda.OptionalFunction;
import org.wicketstuff.lambda.model.LambdaModel;
import org.wicketstuff.lambda.model.SupplierModel;
import org.wicketstuff.lambda.table.FunctionColumn;

/**
 * Simple web page that demonstrates a {@link SupplierModel}, 
 * {@link LambdaModel} and {@link FunctionColumn}.
 * The {@link Function}s and {@link Consumer}s that are used 
 * are defined in the {@link Person} and {@link Address} "domain" classes. 
 */
public class HomePage extends WebPage {

	public HomePage() {
		super();

		/*
		 * Backing store for the added people.
		 */
		List<Person> people = new ArrayList<>();

		/*
		 * Models.
		 */
		IModel<Person> newPersonModel = Model.of(new Person());
		IModel<String> nameModel = new LambdaModel<>(newPersonModel, 
				new OptionalFunction<>(GETTER_OP_NAME), SETTER_NAME);
		IModel<String> streetModel = new LambdaModel<>(newPersonModel, 
				new OptionalFunction<>(GETTER_OP_ADDRESS_STREET), SETTER_ADDRESS_STREET);
		IModel<String> cityModel = new LambdaModel<>(newPersonModel, 
				new OptionalFunction<>(GETTER_OP_ADDRESS_CITY), SETTER_ADDRESS_CITY);

		/*
		 * Label -- example of a {@link SupplierModel}.
		 */
		Component label = new Label("time", 
				new SupplierModel<>(() -> 
					String.format("Page last refreshed: %s", LocalDateTime.now().toString())))
				.setOutputMarkupId(true);
		queue(label);

		/*
		 * Form: name, street and city.
		 */
		Form<Person> form = new Form<>("form", newPersonModel);
		form.setOutputMarkupId(true);
		queue(form);
		form.queue(new TextField<>("name", nameModel));
		form.queue(new TextField<>("street", streetModel));
		form.queue(new TextField<>("city", cityModel));

		/*
		 * Table - examples of {@link FunctionColumn}s.
		 */
		Component table = new DefaultDataTable<>("table", Arrays.asList(
						new FunctionColumn<>(
								Model.of("Name"), 
								new OptionalFunction<>(GETTER_OP_NAME)),
						new FunctionColumn<>(
								Model.of("Street"), 
								new OptionalFunction<>(GETTER_OP_ADDRESS_STREET)),
						new FunctionColumn<>(
								Model.of("City"), 
								new OptionalFunction<>(GETTER_OP_ADDRESS_CITY))),
				new PersonDataProvider(people), 10);
		table.setOutputMarkupId(true);
		queue(table);

		/*
		 * Add button.
		 */
		AjaxButton addButton = new AjaxButton("add") {

			@Override
			protected void onSubmit(AjaxRequestTarget target) {
				people.add(newPersonModel.getObject());
				newPersonModel.setObject(new Person());
				/*
				 * We've changed the backing object of the newPersonModel,
				 * but there is no mechanism for the LD-based LambdaModels
				 * to know this so we have to explicitly detach them.
				 */
				nameModel.detach();
				streetModel.detach();
				cityModel.detach();
				target.add(form, table, label);
			}

		};
		form.queue(addButton);
		form.setDefaultButton(addButton);
	}

	private static class PersonDataProvider extends SortableDataProvider<Person, Void> {

		private List<Person> list;
		
		public PersonDataProvider(List<Person> list) {
			this.list = list;
		}
		
		@Override
		public Iterator<? extends Person> iterator(long first, long count) {
			return list.subList((int) first, (int) (first + count)).iterator();
		}

		@Override
		public long size() {
			return list.size();
		}

		@Override
		public IModel<Person> model(Person object) {
			return Model.of(object);
		}
		
	}
	
}

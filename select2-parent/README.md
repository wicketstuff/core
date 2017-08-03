wicketstuff-select2
==============

This project provides [Apache Wicket](http://wicket.apache.org) components that leverage [Select2](https://select2.github.io/) JavaScript library to build select boxes that provide Ajax choice filtering, custom rendering, etc.

Installation
------------

```xml
<dependency>
    <groupId>org.wicketstuff</groupId>
    <artifactId>wicketstuff-select2</artifactId>
	<version>...</version>
</dependency>
```

*Use version 7.2 if you use Wicket 7 to use the select tag as specified by [Select2 v4](https://select2.github.io/announcements-4.0.html#migrating-from-select2-35)


You may want to check for the latest released version using [Maven Search](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22org.wicketstuff%22%20AND%20a%3A%22wicketstuff-select2%22)

Configuration
-------------

wicketstuff-select2 provides the `Select2ApplicationSettings` class that can be used to configure application-global settings such as:

* Whether or not additional Select2 features should be supported any time a wicketstuff-select2 component is used
* Whether or not default CSS resource will be included any time a wicketstuff-select2 component is used
* And other similar options

These settings allow the application to customize the behavior of wicketstuff-select2 components without creating application-specific subclasses for all of them.

Example:
```java
public class MyApplication extends WebApplication {
    public void init() {
		super.init();
		ApplicationSettings.get().setCssReference(CUSTOM_CSS_REFERENCE);
    }
}
```
Integration
-----------

The main interface between your application and wicketstuff-select2 components is the `ChoiceProvider`:
```java
public abstract class ChoiceProvider<T> implements IDetachable {

	/**
	 * Get the value for displaying to an end user.
	 *
	 * @param object
	 *            the actual object
	 * @return the value meant for displaying to an end user
	 */
	public abstract String getDisplayValue(T object);

	/**
	 * This method is called to get the id value of an object (used as the value attribute of a
	 * choice element) The id can be extracted from the object like a primary key, or if the list is
	 * stable you could just return a toString of the index.
	 * <p>
	 * Note that the given index can be {@code -1} if the object in question is not contained in the
	 * available choices.
	 *
	 * @param object
	 *            The object for which the id should be generated
	 * @param index
	 *            The index of the object in the choices list.
	 * @return String
	 */
	public abstract String getIdValue(T object);

    /**
     * Queries application for choices that match the search {@code term} and adds them to the {@code response}
     */
    public abstract void query(String term, int page, Response<T> response);

    /**
     * Converts a list of choice ids back into application's choice objects. When the choice provider is attached to a
     */
    public abstract Collection<T> toChoices(Collection<String> ids);
}
```
Once you implement a custom subclass your application can communicate with the Select2 components. Then its simply a matter of adding any one of the provided components to your page and configuring various Select2 options through the component. For example a single-select component can be added and configured like this:

Java
```java
// add the single-select component
Select2Choice<Country> country = new Select2Choice<Country>(
	"country",	new PropertyModel(this, "country"), new CountriesProvider());
form.add(country);

// configure various Select2 options
country.getSettings().setMinimumInputLength(1);
```

Html
```html
   ...
   <select wicket:id="country"></select>
   ...
```

The two main Select2 components are the `Select2Choice` which provides single-selection and `Select2MultiChoice` which provides multi-selection.

See the `wicketstuff-select2-examples` submodule for  code examples.

License
-------
Copyright 2012 Igor Vaynberg

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this work except in compliance with the License. You may obtain a copy of the License at:

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.

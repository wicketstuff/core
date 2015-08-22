wicketstuff-select2
==============

This project provides [Apache Wicket](http://wicket.apache.org) components that leverage [Select2](http://ivaynberg.github.com/select2) JavaScript library to build select boxes that provide Ajax choice filtering, custom rendering, etc.

Installation
------------
```xml
<dependency>
    <groupId>org.wicketstuff</groupId>
    <artifactId>wicketstuff-select2</artifactId>
	<version>...</version>
</dependency>
```
You may want to check for the latest released version using [Maven Search](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22org.wicketstuff%22%20AND%20a%3A%22wicketstuff-select2%22)

Configuration
-------------

wicketstuff-select2 provides the `Select2ApplicationSettings` class that can be used to configure application-global settings such as:

* Whether or not internal version of JQuery library will be included any time a wicketstuff-select2 components is used
* Whether or not default CSS resource will be included any time a wicketstuff-select2 component is used
* And other similar options

These settings allow the application to customize the behavior of wicketstuff-select2 components without creating application-specific subclasses for all of them.

Example:
```java
public class MyApplication extends WebApplication {
    public void init() {
		super.init();
		ApplicationSettings.get().setIncludeJquery(false);
    }
}
```
Integration
-----------

The main interface between your application and wicketstuff-select2 components is the `ChoiceProvider`:
```java
public abstract class ChoiceProvider<T> implements IDetachable {
    /**
     * Queries application for choices that match the search {@code term} and adds them to the {@code response}
     */
    public abstract void query(String term, int page, Response<T> response);

    /**
     * Converts the specified choice to Json.
     */
    public abstract void toJson(T choice, JSONWriter writer) throws JSONException;

    /**
     * Converts a list of choice ids back into application's choice objects. When the choice provider is attached to a
     */
    public abstract Collection<T> toChoices(Collection<String> ids);
}
```
Once you implement this interface your application can communicate with the Select2 components. Then its simply a matter of adding any one of the provided components to your page and configuring various Select2 options through the component. For example a single-select component can be added and configured like this:
```java
// add the single-select component
Select2Choice<Country> country = new Select2Choice<Country>(
	"country",	new PropertyModel(this, "country"), new CountriesProvider());
form.add(country);

// configure various Select2 options
country.getSettings().setMinimumInputLength(1);
```
The two main Select2 components are the `Select2Choice` which provides single-selection and `Select2MultiChoice` which provides multi-selection.

See the `wicketstuff-select2-examples` submodule for  code examples.

License
-------
Copyright 2012 Igor Vaynberg

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this work except in compliance with the License. You may obtain a copy of the License at:

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.

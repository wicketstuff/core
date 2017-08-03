# About

This is a set of Wicket components which enable you to read and write URL fragment parameters. With this you can build bookmarkable AJAX features and still support the back button.

1. [Usage](UrlFragment#usage)
 1. [Reading URL fragment (and query) parameters](UrlFragment#reading-url-fragment-and-query-parameters)
 2. [Writing and Removing URL fragment parameters](UrlFragment#writing-and-removing-url-fragment-parameters)
 3. [Back button support](UrlFragment#back-button-support)
2. [Change log](UrlFragment#change-log)
3. [Future Features](UrlFragment#future-features)
4. [License](UrlFragment#license)

# Usage
Working with the URL fragment means reading parameters from it and writing parameters into it. This is done by the following components:

* reading: **AsyncUrlFragmentAwarePage**, **AsyncUrlFragmentAwarePanel**
* writing: **BookmarkableAjaxLink**, **BookmarkableAjaxButton**

## Reading URL fragment (and query) parameters

URL fragment parameters are expected to come in the following form: ../path/to/page#!param1=value1&param2=value2
The parameters will be sent to the server in an asynchronous request:

1. user requests page, page will be loaded initially (page contains JavaScript provided by **UrlParametersReceivingBehavior**)
2. the JavaScript module **UrlUtil** reads the URL query **and** fragment parameters and sends them to the **UrlParametersReceivingBehavior**
3. **UrlParametersReceivingBehavior** invokes its abstract method **onParameterArrival(IRequestParameters requestParameters, AjaxRequestTarget target)** which you have to implement

Reading the URL fragment currently only works for this pattern: **param1=value1&param2=value2**

**AsyncUrlFragmentAwarePage** and **AsyncUrlFragmentAwarePanel** can be used to read the query and fragment parameters. They abstract away the **UrlParametersReceivingBehavior** for you, so you only need to implement the abstract method **onParameterArrival**.

### **AsyncUrlFragmentAwarePage**
```java
public class MyHomePage extends AsyncUrlFragmentAwarePage {
  public MyHomePage(PageParameters parameters) {
    super(parameters);
    WebMarkupContainer emptyContentPanel = new WebMarkupContainer("content"); // initial content
    emptyContentPanel.setOutputMarkupId(true);
    add(emptyContentPanel);
  }
 
  @Override
  protected void onParameterArrival(IRequestParameters requestParameters, AjaxRequestTarget target) {
    ContentPanel content = new ContentPanel("content", requestParameters); // content based on URL fragment parameters
    content.setOutputMarkupId(true);
    MyHomePage.this.replace(content);
    target.add(content);
  }
}
```
At first the page is built with initial content which is independent from URL fragment parameters. Since the panel with the real content depends on URL fragment parameters, it will be initialized in **onParameterArrival**.
This example shows you that it is necessary to have initial content for components depending on URL fragment parameters. While the solution with an empty **WebMarkupContainer** may look strange from a Wicket perspective, it avoids sending uninitialized markup to the client.

### **AsyncUrlFragmentAwarePanel**
```java
public class AsyncContentPanel extends AsyncUrlFragmentAwarePanel {
      
  private final IModel<String> sortingModel;
  private final Label sortingLabel;
    
  public AsyncContentPanel(String id) {
    super(id);
    
    sortingModel = Model.of(""); // initial state
    
    sortingLabel = new Label("sorting", sortingModel);
    sortingLabel.setOutputMarkupId(true);
    add(sortingLabel); // render label with initial content
  }
    
  @Override
  protected void onParameterArrival(IRequestParameters requestParameters, AjaxRequestTarget target) {
    String sorting = requestParameters.getParameterValue("sorting").toString();
    sortingModel.setObject(sorting);
    target.add(sortingLabel); // render with content based on URL fragment parameter(s)
  }
}
```
This examples works almost the same as the previous. **onParameterArrival** is implemented to initialize components based on URL fragment parameters. The only difference is, that these components aren't wrapped in a container but instead will be rendered on the initial page load. This may look cleaner from a Wicket perspective but does lead to sending the **Label** to the client twice.

### Configuration of URL fragment manipulation
By overriding **AsyncUrlFragmentAwarePage.getOptions()** or **AsyncUrlFragmentAwarePanel.getOptions()** you can configure the following constants used to render the URL fragment:
* fragmentIdentifierSuffix (defaults to '!')
* keyValueDelimiter (defaults to '=')

## Writing and Removing URL fragment parameters

Parameters are written to the URL fragment in the following form: ../path/to/page#!param1=value1&param2=value2
Manipulating the URL fragment on AJAX events is done by prepending JavaScript to the corresponding **AjaxRequestTarget**.

### **BookmarkableAjaxLink**
```java
add(new BookmarkableAjaxLink<Void>("link") {
  @Override
  public void onBookmarkableClick(AjaxRequestTarget target) {
    // handle click event
    
    urlFragment().set("new window.location.hash");
    urlFragment().set("key", "value");
    urlFragment().putParameter("key", "value1");
    urlFragment().putParameter("key", "value2", "|");
    urlFragment().removeParameter("key");
  }
});
```
### **BookmarkableAjaxButton**
```java
add(new BookmarkableAjaxButton<Void>("submit") {
  @Override
  public void onBookmarkableSubmit(AjaxRequestTarget target) {
    // handle submit event
    
    urlFragment().set("new window.location.hash");
    urlFragment().set("key", "value");
    urlFragment().putParameter("key", "value1");
    urlFragment().putParameter("key", "value2", "|");
    urlFragment().removeParameter("key");
  }
      
  @Override
  public void onBookmarkableError(AjaxRequestTarget target) {
    // handle error event
    
    urlFragment().set("new window.location.hash");
    urlFragment().set("key", "value");
    urlFragment().putParameter("key", "value1");
    urlFragment().putParameter("key", "value2", "|");
    urlFragment().removeParameter("key");
  }
});
```
## Back button support
Since editing the URL fragment does not lead to a page reload, back button events containing only changes to the URL fragment will be handled via the jQuery hashchange event plugin. Such events will lead to an asynchronous call to **UrlParametersReceivingBehavior** just like after the initial page load. This also works when manually editing the URL fragment.

# Change log
15/05/2013:
* added a method for replacing the URL fragment with a key-value-pair (thanks to Maxim Solodovnik)
* changed fragment updating API:
 * urlFragment().set("fragment")
 * urlFragment().set("key", "value")
 * urlFragment().putParameter("key", "value")
 * urlFragment().putParameter("key", "value", "delimiter")
 * urlFragment().removeParameter("key")

14/05/2013:
* changed fragment updating API in order to better encapsulate the corresponding JavaScripts:
 * setFragmentParameter("key", value") -> urlFragment().setParameter("key", "value")
 * addFragmentParameter("key", value", "delim") -> urlFragment().addParameter("key", "value", "delim")
 * removeFragmentParameter("param") -> urlFragment().removeParameter("key")
* introduced JavaScript for changing location.hash completely (thanks to Maxim Solodovnik)
 * urlFragment().set("new location hash")

12/05/2013:
* UrlUtil can be configured via overriding #getOptions() (thanks to Maxim Solodovnik)

01/05/2013:
* fragment parameters can be concatenated with a custom delimiter (#!param1=value1|value2&param2=...)
* set/addFragmentParameter new recieves an Object as the value and uses its toString method

25/04/2013:
* URL fragment starts with the hashbang #!
* using jQuery hashchange event for cross-browser hashchange handling

# Future features
* more bookmarkable Ajax components (AjaxSubmitLink)
* Callback for requests with "_escaped_fragment_" query parameter (see https://developers.google.com/webmasters/ajax-crawling/docs/specification)
* Binding JavaScript callbacks to certain URL fragment parameters (e.g. Behavior callbacks)
* mirroring URL fragment on the server
* href of BookmarkableAjaxLink with url fragment (href="http://www.mysite.com/#!key1=value1")
* more and custom strategies for rendering state into the URL fragment (e.g. #!param1/value1/param2/value2|value3)

# License
Copyright (c) 2013 Martin Knopf
Licensed under the MIT license.

## Hashchange (cross-browser hash change event)
jQuery hashchange event - v1.3 - 7/21/2010
http://benalman.com/projects/jquery-hashchange-plugin/

Copyright (c) 2010 "Cowboy" Ben Alman
Dual licensed under the MIT and GPL licenses.
http://benalman.com/about/license/

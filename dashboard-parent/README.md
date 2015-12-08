Simple wicket dashboard
=====================
A dashboard is a panel with quick access to information or common tasks.

Features/Benefits
-------------------
With wicketstuff-dashboard you can easily add a dashboard with standard and custom widgets to your wicket application.  
For a dashboard you can specify the numbers of columns, drag and drop widgets, remove widgets, add new widgets, change widget settings, 
collapse widgets, or perform any custom action added by you to widget.  
Also you can save and load the dashboard from a repository (file, database).  
Support for internationalization.

Components
-------------------
- **Dashboard** is the object that contains the widgets. It has one or more columns.
- **Widget** is a fancy word for tools or content that you can add, arrange, and remove from the dashboard.
Widgets make it easy to customize the content of your dashboard.
Widget has an id, a title, some settings (optional), a location (the column and row in dashboard),
and more important it has a view.
- **WidgetView** is the component that displays the widget content (it extends the wicket Panel class). 
A view can be for example a chart or a table.
- **WidgetDescriptor** contains widget meta data: name, description, provider and the widget class name.
- **WidgetFactory** is the object that creates widgets using the widget descriptors.
- **WidgetRegistry** is the object that stores all active widget descriptors. You can register a new widget 
using a widget descriptor.
- **DashboardPersister** is responsible for dashboard load and save. XStreamDashboardPersister is a concrete implementation
that save/load a dashboard to/from a file.
- **DashboardPanel** is a wicket panel that displays a dashboard.
- **WidgetPanel** is a wicket panel that displays a widget. It contains a header panel, a settings panel (if the
widget has settings) and the widget view. It can be moved with drag and drop.
The header panel contains the widget title, an icon that display the collapsed state and some actions (refresh, delete, settings).
- **WidgetAction** defines an action that is usually assigned to a widget. By default on each widget are some default actions:
_REFRESH_, _DELETE_ and _SETTINGS_ (if the widget has settings).
- **WidgetActionsFactory** is the object that creates actions for the widgets. You can add easily any custom actions or remove defaults
actions (adding security is a scenario).

Artifacts
-------------------
- Wicket Dashboard Core `wicketstuff-dashboard-core` (jar)
- Wicket Dashboard (Standard) Widgets
    - ofchart `wicketstuff-dashboard-widgets-ofchart`        (jar)
    - jqplot `wicketstuff-dashboard-widgets-jqplot`          (jar)
    - justgage `wicketstuff-dashboard-widgets-justgage`      (jar)
    - wicked-charts `wicketstuff-dashboard-widgets-charts` (jar)
    - loremipsum `wicketstuff-dashboard-widgets-loremispum`  (jar)
- Wicket Dashboard Demo `wicketstuff-dashboard-examples` (war)

Using Maven
-------------------
All artifacts are uploaded to the Central Maven Repository.

In your pom.xml you must define the dependencies to wicket dashboard artifacts with:

```xml
<dependency>
    <groupId>org.wicketstuff</groupId>
    <artifactId>wicketstuff-dashboard-core</artifactId>
    <version>${wicketstuff.version}</version>
</dependency>

<!-- OPTIONAL -->
<dependency>
    <groupId>org.wicketstuff</groupId>
    <artifactId>wicketstuff-dashboard-widgets-ofchart</artifactId>
    <version>${wicketstuff.version}</version>
</dependency>

<!-- OPTIONAL -->
<dependency>
    <groupId>org.wicketstuff</groupId>
    <artifactId>wicketstuff-dashboard-widgets-jqplot</artifactId>
    <version>${wicketstuff.version}</version>
</dependency>

<!-- OPTIONAL -->
<dependency>
    <groupId>org.wicketstuff</groupId>
    <artifactId>wicketstuff-dashboard-widgets-loremipsum</artifactId>
    <version>${wicketstuff.version}</version>
</dependency>

<!-- OPTIONAL -->
<dependency>
    <!-- Wicked-Charts is the open source code project that integrates HighCharts in Wicket. -->
    <!-- HighCharts is a commercial product and a license might be required (http://www.HighCharts.com) -->
    <groupId>org.wicketstuff</groupId>
    <artifactId>wicketstuff-dashboard-widgets-charts</artifactId>
    <version>${wicketstuff.version}</version>
</dependency>

<!-- OPTIONAL -->
<dependency>
    <groupId>org.wicketstuff</groupId>
    <artifactId>wicketstuff-dashboard-widgets-loremipsum</artifactId>
    <version>${wicketstuff.version}</version>
</dependency>    
```

where ${wicketstuff.version} is the last wicketstuff version.

You may want to check for the latest released version using [Maven Search](http://search.maven.org/#search%7Cga%7C1%7Cwicketstuff-dashboard)

How to use
-------------------
It's very simple to add a dashboard panel in your wicket application.

In your application class make some initializations:

```java
public void init() {
    ...

    // >>> begin dashboard settings
    
    // register some widgets
    DashboardContext dashboardContext = getDashboardContext();
    dashboardContext.getWidgetRegistry()
        .registerWidget(new LoremIpsumWidgetDescriptor())
        .registerWidget(new ChartWidgetDescriptor())
        .registerWidget(new JqPlotWidgetDescriptor())
        .registerWidget(new JustGageWidgetDescriptor())
        .registerWidget(new HighChartsWidgetDescriptor());
    
    // add a custom action for all widgets
    dashboardContext.setWidgetActionsFactory(new DemoWidgetActionsFactory());

    // set some (data) factory
    ChartWidget.setChartDataFactory(new DemoChartDataFactory());
    JqPlotWidget.setChartFactory(new DemoChartFactory());
    JustGageWidget.setJustGageFactory(new DemoJustGageFactory());
    HighChartsWidget.setHighChartsFactory(new DemoHighChartsFactory());

    // init dashboard from context
    initDashboard();
    
    // <<< end dashboard settings
}

private DashboardContext getDashboardContext() {
    return getMetaData(DashboardContextInitializer.DASHBOARD_CONTEXT_KEY);
}

private void initDashboard() {
    dashboard = getDashboardContext().getDashboardPersister().load();
    if (dashboard == null) {
        dashboard = new DefaultDashboard("default", "Default");
    }
}
```	


In your web page add the dashboard panel:

```java
Dashboard dashboard = ...;
add(new DashboardPanel("dashboard", new Model<Dashboard>(dashboard)));
```

To configure wicketstuff-dashboard see **DashboardSettings** class. In this class you can specify for example if you want to ignore
the jquery internal version, or to specify another versions for wicketstuff-dashboard's resources.

```java
public void init() {
    ...
    
    DashboardSettings dashboardSettings = DashboardSettings.get();
    // I don't want to add the internal wicket-dashboard's jquery to page head when I used DashboardPanel
    dashboardSettings.setIncludeJQuery(false); 

    ...       
}
```
    
If you need an dashboard context object in your wicket panel than implements **DashboardContextAware** (see _AddWidgetPanel_ from demo).    

For more information please see the demo sources.

Internationalization
-------------------
Wicketstuff-dashboard has support for internationalization. 
Supported languages:
- English
- French
- Romanian
- German
 
If you want support for another languages please create and send a pull request (or an email) with the translation of [wicket-package.properties](https://github.com/decebals/wicket-dashboard/blob/master/core/src/main/java/ro/fortsoft/wicket/dashboard/wicket-package.properties).

Demo
-------------------
I have a tiny demo application. In this demo I have implemented four widgets types:
a chart widget using [Open Flash Chart 2](http://teethgrinder.co.uk/open-flash-chart-2), a chart widget using [jqPlot](http://www.jqplot.com), a chart using [HighCharts](http://www.HighCharts.com), a handy widget for generating and animating nice & clean gauges using [JustGage](http://justgage.com) and a LoremIpsum widget (display a Lorem Ipsum).

The demo application is in demo package.
To run the demo application use:  

 ```
mvn install
cd demo
mvn jetty:run
```

In the internet browser type http://localhost:8081/.

You can see a screenshot of the demo application on the project's [wiki page] (https://github.com/decebals/wicket-dashboard/wiki).

Mailing list
--------------
Much of the conversation between developers and users is managed through the project's [mailing list] (http://groups.google.com/group/wicket-dashboard).

Versioning
------------
Wicketstuff-dashboard will be maintained under the Semantic Versioning guidelines as much as possible.

Releases will be numbered with the follow format:

`<major>.<minor>.<patch>`

And constructed with the following guidelines:

* Breaking backward compatibility bumps the major
* New additions without breaking backward compatibility bumps the minor
* Bug fixes and misc changes bump the patch

For more information on SemVer, please visit http://semver.org/. 

License
--------------
Copyright 2012 Decebal Suiu
 
Licensed under the Apache License, Version 2.0 (the "License"); you may not use this work except in compliance with
the License. You may obtain a copy of the License in the LICENSE file, or at:
 
http://www.apache.org/licenses/LICENSE-2.0
 
Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
specific language governing permissions and limitations under the License.

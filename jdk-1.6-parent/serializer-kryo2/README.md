Kryo2 Wicket Serializer Project
===

**HINT**: Please do **NOT** use in production environment until anybody came around and say that is safe to do so.
But if you should NOT use this in production environments, then what else could you do?

## Usefull features so far

There are not as many usefull features so far, but one could be very usefull: Answer the question: What makes my pages such big?

### Maven Setup

There is a Version for Wicket 6 starting with 6.2.1,but API changed since 6.9.x.

	<dependency>
		<groupId>org.wicketstuff</groupId>
		<artifactId>wicketstuff-serializer-kryo2</artifactId>
		<version>6.9.1</version>
	</dependency>
	
### Type and Tree Serialization Reports

Change Application.init() like this:

	public class Application extends WebApplication
	{
    ...

		@Override
		public void init()
		{
			super.init();


			IReportOutput reportOutput=new LoggerReportOutput();

			// output of report of type sizes, sorted tree report (by size), aggregated tree 
			ISerializedObjectTreeProcessor typeAndSortedTreeAndCollapsedSortedTreeProcessors = TreeProcessors.listOf(
				new TypeSizeReport(reportOutput), new SortedTreeSizeReport(reportOutput), new SimilarNodeTreeTransformator(
					new SortedTreeSizeReport(reportOutput)));

			// strips class object writes from tree
			TreeTransformator treeProcessors = new TreeTransformator(
				typeAndSortedTreeAndCollapsedSortedTreeProcessors,
				TreeTransformator.strip(new TypeFilter(Class.class)));

			// serialization listener notified on every written object
			ISerializationListener serializationListener = SerializationListeners.listOf(
				new DefaultJavaSerializationValidator(), new AnalyzingSerializationListener(
					new NativeTypesAsLabel(new ComponentIdAsLabel()), treeProcessors));


			InspectingKryoSerializer serializer = new InspectingKryoSerializer(Bytes.megabytes(1L),
				serializationListener);

			getFrameworkSettings().setSerializer(serializer);
		}
	}

And with this SamplePage:

	public class SamplePage extends WebPage
	{
		public SamplePage()
		{
			add(new ListView<String>("list", Arrays.asList("A", "B", "C"))
			{
				@Override
				protected void populateItem(ListItem<String> item)
				{
					item.add(new Label("label",item.getModel()));
				}
			});
		}
	}

... you will get following Reports if the Page is serialized:

	DEBUG - KryoSerializer             - Buffer size: '1M'
	DEBUG - KryoSerializer             - Going to serialize: '[Page class = org.wicketstuff.pageserializer.kryo2.examples.SamplePage, id = 0, render count = 1]'
	DEBUG - LoggerReportOutput         - TypeSizeReport
	==================================================================
	|bytes|Type                                                      |
	------------------------------------------------------------------
	|  183|org.wicketstuff.pageserializer.kryo2.examples.SamplePage  |
	|   80|org.wicketstuff.pageserializer.kryo2.examples.SamplePage$1|
	|   70|org.apache.wicket.markup.html.list.ListItem               |
	|   63|org.apache.wicket.markup.html.basic.Label                 |
	|   63|java.lang.Integer                                         |
	|   34|java.lang.String                                          |
	|   12|org.apache.wicket.markup.html.list.ListItemModel          |
	|    3|org.apache.wicket.request.mapper.parameter.PageParameters |
	|    3|java.util.Arrays$ArrayList                                |
	|    2|org.apache.wicket.model.util.WildcardListModel            |
	|    2|[Ljava.lang.Object;                                       |
	|    1|java.lang.Boolean                                         |
	==================================================================

	DEBUG - LoggerReportOutput         - TreeSizeReport
	==============================================================================================
	|  #|    %| sum| local| child|Type                                                           |
	----------------------------------------------------------------------------------------------
	| #0| 100%| 516|   181|   335|org.wicketstuff.pageserializer.kryo2.examples.SamplePage(0)    |
	| #4|  62%| 323|    74|   249|  org.apache.wicket.markup.html.list.ListView(list)            |
	| #6|  41%| 212|     2|   210|    [Ljava.lang.Object;                                        |
	| #7|  25%| 134|    51|    83|      org.apache.wicket.markup.html.list.ListItem(0)           |
	|#10|  13%|  71|    53|    18|        org.apache.wicket.markup.html.basic.Label(label)       |
	|#16|   1%|   6|     6|     0|          java.lang.String("labe...")                          |
	|#12|   0%|   5|     3|     2|          org.apache.wicket.markup.html.list.ListItemModel     |
	|#13|   0%|   1|     1|     0|            java.lang.Integer(=0)                              |
	| #4|   0%|   1|     1|     0|            org.apache.wicket.markup.html.list.ListView(list)  |
	|#14|   0%|   5|     5|     0|          java.lang.Integer(=1074999450)                       |
	|#15|   0%|   1|     1|     0|          java.lang.Integer(=-1)                               |
	| #7|   0%|   1|     1|     0|          org.apache.wicket.markup.html.list.ListItem(0)       |
	|#17|   0%|   5|     5|     0|        java.lang.Integer(=1074999450)                         |
	|#19|   0%|   3|     3|     0|        java.lang.String("0")                                  |
	|#12|   0%|   1|     1|     0|        org.apache.wicket.markup.html.list.ListItemModel       |
	|#18|   0%|   1|     1|     0|        java.lang.Integer(=-1)                                 |
	|#20|   0%|   1|     1|     0|        java.lang.Integer(=0)                                  |
	| #4|   0%|   1|     1|     0|        org.apache.wicket.markup.html.list.ListView(list)      |
	|#21|   7%|  38|     8|    30|      org.apache.wicket.markup.html.list.ListItem(1)           |
	|#22|   3%|  18|     5|    13|        org.apache.wicket.markup.html.basic.Label(label)       |
	|#23|   0%|   5|     3|     2|          org.apache.wicket.markup.html.list.ListItemModel     |
	|#24|   0%|   1|     1|     0|            java.lang.Integer(=1)                              |
	| #4|   0%|   1|     1|     0|            org.apache.wicket.markup.html.list.ListView(list)  |
	|#25|   0%|   5|     5|     0|          java.lang.Integer(=1074999450)                       |
	|#26|   0%|   1|     1|     0|          java.lang.Integer(=-1)                               |
	|#16|   0%|   1|     1|     0|          java.lang.String("labe...")                          |
	|#21|   0%|   1|     1|     0|          org.apache.wicket.markup.html.list.ListItem(1)       |
	|#27|   0%|   5|     5|     0|        java.lang.Integer(=1074999450)                         |
	|#29|   0%|   3|     3|     0|        java.lang.String("1")                                  |
	|#23|   0%|   1|     1|     0|        org.apache.wicket.markup.html.list.ListItemModel       |
	|#28|   0%|   1|     1|     0|        java.lang.Integer(=-1)                                 |
	|#30|   0%|   1|     1|     0|        java.lang.Integer(=1)                                  |
	| #4|   0%|   1|     1|     0|        org.apache.wicket.markup.html.list.ListView(list)      |
	|#31|   7%|  38|     8|    30|      org.apache.wicket.markup.html.list.ListItem(2)           |
	|#32|   3%|  18|     5|    13|        org.apache.wicket.markup.html.basic.Label(label)       |
	|#33|   0%|   5|     3|     2|          org.apache.wicket.markup.html.list.ListItemModel     |
	|#34|   0%|   1|     1|     0|            java.lang.Integer(=2)                              |
	| #4|   0%|   1|     1|     0|            org.apache.wicket.markup.html.list.ListView(list)  |
	|#35|   0%|   5|     5|     0|          java.lang.Integer(=1074999450)                       |
	|#36|   0%|   1|     1|     0|          java.lang.Integer(=-1)                               |
	|#16|   0%|   1|     1|     0|          java.lang.String("labe...")                          |
	|#31|   0%|   1|     1|     0|          org.apache.wicket.markup.html.list.ListItem(2)       |
	|#37|   0%|   5|     5|     0|        java.lang.Integer(=1074999450)                         |
	|#39|   0%|   3|     3|     0|        java.lang.String("2")                                  |
	|#33|   0%|   1|     1|     0|        org.apache.wicket.markup.html.list.ListItemModel       |
	|#38|   0%|   1|     1|     0|        java.lang.Integer(=-1)                                 |
	|#40|   0%|   1|     1|     0|        java.lang.Integer(=2)                                  |
	| #4|   0%|   1|     1|     0|        org.apache.wicket.markup.html.list.ListView(list)      |
	|#42|   3%|  17|     2|    15|    org.apache.wicket.model.util.WildcardListModel             |
	|#44|   2%|  15|     3|    12|      java.util.Arrays$ArrayList                               |
	|#46|   0%|   4|     4|     0|        java.lang.String("A")                                  |
	|#47|   0%|   4|     4|     0|        java.lang.String("B")                                  |
	|#48|   0%|   4|     4|     0|        java.lang.String("C")                                  |
	|#50|   0%|   5|     5|     0|    java.lang.Integer(=1074999450)                             |
	|#52|   0%|   5|     5|     0|    java.lang.String("list")                                   |
	|#54|   0%|   5|     5|     0|    java.lang.Integer(=MAX)                                    |
	| #0|   0%|   2|     2|     0|    org.wicketstuff.pageserializer.kryo2.examples.SamplePage(0)|
	|#49|   0%|   1|     1|     0|    java.lang.Integer(=0)                                      |
	|#51|   0%|   1|     1|     0|    java.lang.Integer(=-1)                                     |
	|#53|   0%|   1|     1|     0|    java.lang.Boolean                                          |
	|#55|   0%|   5|     5|     0|  java.lang.Integer(=1074475162)                               |
	|#59|   0%|   3|     3|     0|  org.apache.wicket.request.mapper.parameter.PageParameters    |
	| #2|   0%|   1|     1|     0|  java.lang.Integer(=1)                                        |
	|#56|   0%|   1|     1|     0|  java.lang.Integer(=-1)                                       |
	|#57|   0%|   1|     1|     0|  java.lang.Integer(=0)                                        |
	|#60|   0%|   1|     1|     0|  java.lang.Integer(=1)                                        |
	==============================================================================================

	DEBUG - LoggerReportOutput         - TreeSizeReport
	===================================================================================================================
	|   #|    %| sum| local| child|Type                                                                               |
	-------------------------------------------------------------------------------------------------------------------
	|  #0| 100%| 516|   181|   335|org.wicketstuff.pageserializer.kryo2.examples.SamplePage(0)                        |
	|  #4|  62%| 323|    74|   249|  org.apache.wicket.markup.html.list.ListView(list)                                |
	|  #6|  41%| 212|     2|   210|    [Ljava.lang.Object;                                                            |
	|null|  40%| 210|    67|   143|      org.apache.wicket.markup.html.list.ListItem(0|1|2)                           |
	|null|  20%| 107|    63|    44|        org.apache.wicket.markup.html.basic.Label(label)                           |
	|null|   3%|  18|    18|     0|          java.lang.Integer(=1074999450|=-1)                                       |
	|null|   2%|  15|     9|     6|          org.apache.wicket.markup.html.list.ListItemModel                         |
	|null|   0%|   3|     3|     0|            java.lang.Integer(=0|=1|=2)                                            |
	|null|   0%|   3|     3|     0|            org.apache.wicket.markup.html.list.ListView(list)                      |
	|null|   1%|   8|     8|     0|          java.lang.String("labe...")                                              |
	|null|   0%|   3|     3|     0|          org.apache.wicket.markup.html.list.ListItem(0|1|2)                       |
	|null|   4%|  21|    21|     0|        java.lang.Integer(=1074999450|=-1|=0|=1074999450|=-1|=1|=1074999450|=-1|=2)|
	|null|   1%|   9|     9|     0|        java.lang.String("0"|"1"|"2")                                              |
	|null|   0%|   3|     3|     0|        org.apache.wicket.markup.html.list.ListItemModel                           |
	|null|   0%|   3|     3|     0|        org.apache.wicket.markup.html.list.ListView(list)                          |
	| #42|   3%|  17|     2|    15|    org.apache.wicket.model.util.WildcardListModel                                 |
	| #44|   2%|  15|     3|    12|      java.util.Arrays$ArrayList                                                   |
	|null|   2%|  12|    12|     0|        java.lang.String("A"|"B"|"C")                                              |
	|null|   2%|  12|    12|     0|    java.lang.Integer(=0|=1074999450|=-1|=MAX)                                     |
	| #52|   0%|   5|     5|     0|    java.lang.String("list")                                                       |
	|  #0|   0%|   2|     2|     0|    org.wicketstuff.pageserializer.kryo2.examples.SamplePage(0)                    |
	| #53|   0%|   1|     1|     0|    java.lang.Boolean                                                              |
	|null|   1%|   9|     9|     0|  java.lang.Integer(=1|=1074475162|=-1|=0)                                         |
	| #59|   0%|   3|     3|     0|  org.apache.wicket.request.mapper.parameter.PageParameters                        |
	===================================================================================================================

You can customize a lot if you want to, but maybe this is a good start for some investigation.

### Single file for each report instead of logging

Change Application.init() like this:

	public class Application extends WebApplication
	{
    ...

		@Override
		public void init()
		{
			super.init();

			File outputDirectory = tempDirectory("kryo2-reports");

			DirectoryBasedReportOutput output = new DirectoryBasedReportOutput(outputDirectory);
			
			// output of report of type sizes, sorted tree report (by size), aggregated tree 
			ISerializedObjectTreeProcessor typeAndSortedTreeAndCollapsedSortedTreeProcessors = TreeProcessors.listOf(
				new TypeSizeReport(output.with(Keys.withNameAndFileExtension("typesize", "txt"))),
				new SortedTreeSizeReport(output.with(Keys.withNameAndFileExtension("treesize", "txt"))),
				new SimilarNodeTreeTransformator(new SortedTreeSizeReport(output.with(Keys.withNameAndFileExtension("sorted-treesize", "txt")))),
				new RenderTreeProcessor(output.with(Keys.withNameAndFileExtension("d3js-chart", "html")),new D3DataFileRenderer()));

			// strips class object writes from tree
			TreeTransformator treeProcessors = new TreeTransformator(
				typeAndSortedTreeAndCollapsedSortedTreeProcessors,
				TreeTransformator.strip(new TypeFilter(Class.class)));

			// serialization listener notified on every written object
			ISerializationListener serializationListener = SerializationListeners.listOf(
				new DefaultJavaSerializationValidator(), new AnalyzingSerializationListener(
					new NativeTypesAsLabel(new ComponentIdAsLabel()), treeProcessors));


			InspectingKryoSerializer serializer = new InspectingKryoSerializer(Bytes.megabytes(1L),
				serializationListener);

			getFrameworkSettings().setSerializer(serializer);
		}
	}

... you will get the Reports in separate files in the configured directory:

	org-wicketstuff-pageserializer-kryo2-examples-SamplePage-d3js-chart-2013-07-26--055104-640-.html
	org-wicketstuff-pageserializer-kryo2-examples-SamplePage-sorted-treesize-2013-07-26--055104-633-.txt
	org-wicketstuff-pageserializer-kryo2-examples-SamplePage-treesize-2013-07-26--055104-618-.txt
	org-wicketstuff-pageserializer-kryo2-examples-SamplePage-typesize-2013-07-26--055104-585-.txt




-----------------------------------------------
**the documentation below is not wrong but does not reflect the state of this project**

Serializer Kryo
===============
is an implementation of `org.apache.wicket.serialize.ISerializer` for Wicket 6

Such serializer can be used to convert almost any kind of object to/from byte array. Almost any because Kryo may need your help for some complex graph of objects. Refer to Kryo documentation to understand more about that.

When configured with


	public class MyApplication extends WebApplication
	{
		@Override
		public void init()
		{
			super.init();

			getFrameworkSettings().setSerializer(new KryoSerializer());
		}
	}


it will be used to serialize any page for the IPageStore needs.

It is based on [Kryo2](http://code.google.com/p/kryo/) and [kryo2-serializers](https://github.com/magro/kryo-serializers/tree/kryo2). 

Notes
----
* serializer-kryo is not heavily tested so it may have need for more custom serializers for some of the Wicket classes. Let us know if you face a problem by creating an issue. Pull requests are more than welcome!

* serializer-kryo uses SUN/Oracle propriate APIs (sun.reflect.ReflectionFactory) and thus cannot be used on different JDKs.


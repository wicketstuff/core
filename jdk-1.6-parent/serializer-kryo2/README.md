Kryo2 Wicket Serializer Project
===

**HINT**: Please do **NOT** use in production environment until anybody came around and say that is safe to do so.
But if you should NOT use this in production environments, then what else could you do?

## Usefull features so far

There are not as many usefull features so far, but one could be very usefull: Answer the question: What makes my pages such big?

### Maven Setup

There is a Version for Wicket 6 starting with 6.2.1.

	<dependency>
		<groupId>org.wicketstuff</groupId>
		<artifactId>wicketstuff-serializer-kryo2</artifactId>
		<version>6.2.1</version>
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


			// output of report of type sizes, sorted tree report (by size), aggregated tree 
			ISerializedObjectTreeProcessor typeAndSortedTreeAndCollapsedSortedTreeProcessors = TreeProcessors.listOf(
				new TypeSizeReport(), new SortedTreeSizeReport(), new SimilarNodeTreeTransformator(
					new SortedTreeSizeReport()));

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
	DEBUG - TypeSizeReport             - 
	====================================================================
	|Type.........................................................bytes|
	--------------------------------------------------------------------
	|org.wicketstuff.pageserializer.kryo2.examples.SamplePage.......183|
	|org.wicketstuff.pageserializer.kryo2.examples.SamplePage$1......80|
	|org.apache.wicket.markup.html.list.ListItem.....................70|
	|java.lang.Integer...............................................63|
	|org.apache.wicket.markup.html.basic.Label.......................63|
	|java.lang.String................................................34|
	|org.apache.wicket.markup.html.list.ListItemModel................12|
	|java.util.Arrays$ArrayList.......................................3|
	|org.apache.wicket.request.mapper.parameter.PageParameters........3|
	|org.apache.wicket.model.util.WildcardListModel...................2|
	|[Ljava.lang.Object;..............................................2|
	|java.lang.Boolean................................................1|
	====================================================================

	DEBUG - TreeSizeReport             - 
	===============================================================================================
	|  #|Type.................................................................%|.sum|.local|.child|
	-----------------------------------------------------------------------------------------------
	| #0|org.wicketstuff.pageserializer.kryo2.examples.SamplePage(0).......100%|.516|...181|...335|
	| #4|  org.apache.wicket.markup.html.list.ListView(list)................62%|.323|....74|...249|
	| #6|    [Ljava.lang.Object;............................................41%|.212|.....2|...210|
	| #7|      org.apache.wicket.markup.html.list.ListItem(0)...............25%|.134|....51|....83|
	|#10|        org.apache.wicket.markup.html.basic.Label(label)...........13%|..71|....53|....18|
	|#16|          java.lang.String("labe...")...............................1%|...6|.....6|.....0|
	|#12|          org.apache.wicket.markup.html.list.ListItemModel..........0%|...5|.....3|.....2|
	|#13|            java.lang.Integer(=0)...................................0%|...1|.....1|.....0|
	| #4|            org.apache.wicket.markup.html.list.ListView(list).......0%|...1|.....1|.....0|
	|#14|          java.lang.Integer(=1074999450)............................0%|...5|.....5|.....0|
	|#15|          java.lang.Integer(=-1)....................................0%|...1|.....1|.....0|
	| #7|          org.apache.wicket.markup.html.list.ListItem(0)............0%|...1|.....1|.....0|
	|#17|        java.lang.Integer(=1074999450)..............................0%|...5|.....5|.....0|
	|#19|        java.lang.String("0").......................................0%|...3|.....3|.....0|
	|#12|        org.apache.wicket.markup.html.list.ListItemModel............0%|...1|.....1|.....0|
	|#18|        java.lang.Integer(=-1)......................................0%|...1|.....1|.....0|
	|#20|        java.lang.Integer(=0).......................................0%|...1|.....1|.....0|
	| #4|        org.apache.wicket.markup.html.list.ListView(list)...........0%|...1|.....1|.....0|
	|#21|      org.apache.wicket.markup.html.list.ListItem(1)................7%|..38|.....8|....30|
	|#22|        org.apache.wicket.markup.html.basic.Label(label)............3%|..18|.....5|....13|
	|#23|          org.apache.wicket.markup.html.list.ListItemModel..........0%|...5|.....3|.....2|
	|#24|            java.lang.Integer(=1)...................................0%|...1|.....1|.....0|
	| #4|            org.apache.wicket.markup.html.list.ListView(list).......0%|...1|.....1|.....0|
	|#25|          java.lang.Integer(=1074999450)............................0%|...5|.....5|.....0|
	|#26|          java.lang.Integer(=-1)....................................0%|...1|.....1|.....0|
	|#16|          java.lang.String("labe...")...............................0%|...1|.....1|.....0|
	|#21|          org.apache.wicket.markup.html.list.ListItem(1)............0%|...1|.....1|.....0|
	|#27|        java.lang.Integer(=1074999450)..............................0%|...5|.....5|.....0|
	|#29|        java.lang.String("1").......................................0%|...3|.....3|.....0|
	|#23|        org.apache.wicket.markup.html.list.ListItemModel............0%|...1|.....1|.....0|
	|#28|        java.lang.Integer(=-1)......................................0%|...1|.....1|.....0|
	|#30|        java.lang.Integer(=1).......................................0%|...1|.....1|.....0|
	| #4|        org.apache.wicket.markup.html.list.ListView(list)...........0%|...1|.....1|.....0|
	|#31|      org.apache.wicket.markup.html.list.ListItem(2)................7%|..38|.....8|....30|
	|#32|        org.apache.wicket.markup.html.basic.Label(label)............3%|..18|.....5|....13|
	|#33|          org.apache.wicket.markup.html.list.ListItemModel..........0%|...5|.....3|.....2|
	|#34|            java.lang.Integer(=2)...................................0%|...1|.....1|.....0|
	| #4|            org.apache.wicket.markup.html.list.ListView(list).......0%|...1|.....1|.....0|
	|#35|          java.lang.Integer(=1074999450)............................0%|...5|.....5|.....0|
	|#36|          java.lang.Integer(=-1)....................................0%|...1|.....1|.....0|
	|#16|          java.lang.String("labe...")...............................0%|...1|.....1|.....0|
	|#31|          org.apache.wicket.markup.html.list.ListItem(2)............0%|...1|.....1|.....0|
	|#37|        java.lang.Integer(=1074999450)..............................0%|...5|.....5|.....0|
	|#39|        java.lang.String("2").......................................0%|...3|.....3|.....0|
	|#33|        org.apache.wicket.markup.html.list.ListItemModel............0%|...1|.....1|.....0|
	|#38|        java.lang.Integer(=-1)......................................0%|...1|.....1|.....0|
	|#40|        java.lang.Integer(=2).......................................0%|...1|.....1|.....0|
	| #4|        org.apache.wicket.markup.html.list.ListView(list)...........0%|...1|.....1|.....0|
	|#42|    org.apache.wicket.model.util.WildcardListModel..................3%|..17|.....2|....15|
	|#44|      java.util.Arrays$ArrayList....................................2%|..15|.....3|....12|
	|#46|        java.lang.String("A").......................................0%|...4|.....4|.....0|
	|#47|        java.lang.String("B").......................................0%|...4|.....4|.....0|
	|#48|        java.lang.String("C").......................................0%|...4|.....4|.....0|
	|#50|    java.lang.Integer(=1074999450)..................................0%|...5|.....5|.....0|
	|#52|    java.lang.String("list")........................................0%|...5|.....5|.....0|
	|#54|    java.lang.Integer(=MAX).........................................0%|...5|.....5|.....0|
	| #0|    org.wicketstuff.pageserializer.kryo2.examples.SamplePage(0).....0%|...2|.....2|.....0|
	|#49|    java.lang.Integer(=0)...........................................0%|...1|.....1|.....0|
	|#51|    java.lang.Integer(=-1)..........................................0%|...1|.....1|.....0|
	|#53|    java.lang.Boolean...............................................0%|...1|.....1|.....0|
	|#55|  java.lang.Integer(=1074475162)....................................0%|...5|.....5|.....0|
	|#59|  org.apache.wicket.request.mapper.parameter.PageParameters.........0%|...3|.....3|.....0|
	| #2|  java.lang.Integer(=1).............................................0%|...1|.....1|.....0|
	|#56|  java.lang.Integer(=-1)............................................0%|...1|.....1|.....0|
	|#57|  java.lang.Integer(=0).............................................0%|...1|.....1|.....0|
	|#60|  java.lang.Integer(=1).............................................0%|...1|.....1|.....0|
	===============================================================================================

	DEBUG - TreeSizeReport             - 
	====================================================================================================================
	|   #|Type.....................................................................................%|.sum|.local|.child|
	--------------------------------------------------------------------------------------------------------------------
	|  #0|org.wicketstuff.pageserializer.kryo2.examples.SamplePage(0)...........................100%|.516|...181|...335|
	|  #4|  org.apache.wicket.markup.html.list.ListView(list)....................................62%|.323|....74|...249|
	|  #6|    [Ljava.lang.Object;................................................................41%|.212|.....2|...210|
	|null|      org.apache.wicket.markup.html.list.ListItem(0|1|2)...............................40%|.210|....67|...143|
	|null|        org.apache.wicket.markup.html.basic.Label(label)...............................20%|.107|....63|....44|
	|null|          java.lang.Integer(=1074999450|=-1)............................................3%|..18|....18|.....0|
	|null|          org.apache.wicket.markup.html.list.ListItemModel..............................2%|..15|.....9|.....6|
	|null|            java.lang.Integer(=0|=1|=2).................................................0%|...3|.....3|.....0|
	|null|            org.apache.wicket.markup.html.list.ListView(list)...........................0%|...3|.....3|.....0|
	|null|          java.lang.String("labe...")...................................................1%|...8|.....8|.....0|
	|null|          org.apache.wicket.markup.html.list.ListItem(0|1|2)............................0%|...3|.....3|.....0|
	|null|        java.lang.Integer(=1074999450|=-1|=0|=1074999450|=-1|=1|=1074999450|=-1|=2).....4%|..21|....21|.....0|
	|null|        java.lang.String("0"|"1"|"2")...................................................1%|...9|.....9|.....0|
	|null|        org.apache.wicket.markup.html.list.ListItemModel................................0%|...3|.....3|.....0|
	|null|        org.apache.wicket.markup.html.list.ListView(list)...............................0%|...3|.....3|.....0|
	| #42|    org.apache.wicket.model.util.WildcardListModel......................................3%|..17|.....2|....15|
	| #44|      java.util.Arrays$ArrayList........................................................2%|..15|.....3|....12|
	|null|        java.lang.String("A"|"B"|"C")...................................................2%|..12|....12|.....0|
	|null|    java.lang.Integer(=0|=1074999450|=-1|=MAX)..........................................2%|..12|....12|.....0|
	| #52|    java.lang.String("list")............................................................0%|...5|.....5|.....0|
	|  #0|    org.wicketstuff.pageserializer.kryo2.examples.SamplePage(0).........................0%|...2|.....2|.....0|
	| #53|    java.lang.Boolean...................................................................0%|...1|.....1|.....0|
	|null|  java.lang.Integer(=1|=1074475162|=-1|=0)..............................................1%|...9|.....9|.....0|
	| #59|  org.apache.wicket.request.mapper.parameter.PageParameters.............................0%|...3|.....3|.....0|
	====================================================================================================================

You can customize a lot if you want to, but maybe this is a good start for some investigation.




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


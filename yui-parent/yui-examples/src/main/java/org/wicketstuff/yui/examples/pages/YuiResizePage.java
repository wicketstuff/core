package org.wicketstuff.yui.examples.pages;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.wicketstuff.yui.behavior.resize.YuiResize;
import org.wicketstuff.yui.examples.WicketExamplePage;

/**
 * @author josh
 * 
 */
public class YuiResizePage extends WicketExamplePage
{

	@SuppressWarnings("serial")
	public YuiResizePage()
	{
		add(new WebMarkupContainer("simple").add(new YuiResize()));

		add(new WebMarkupContainer("proxy").add(new YuiResize()
		{
			@Override
			protected String getOpts()
			{
				return "{ proxy: true, status: true}";
			}
		}));

		add(new WebMarkupContainer("animated").add(new YuiResize()
		{
			@Override
			protected String getOpts()
			{
				return "{ proxy: true, status: true, animate: true, animateDuration: .75, "
						+ "animateEasing: YAHOO.util.Easing.backBoth}";
			}
		}));

		add(new WebMarkupContainer("eight").add(new YuiResize()
		{
			@Override
			protected String getOpts()
			{
				return "{ handles: 'all', knobHandles: true,  "
						+ "height: '199px', width: '300px'," + "proxy: true, status: true,"
						+ "draggable: true, animate: true, animateDuration: .75, "
						+ "animateEasing: YAHOO.util.Easing.backBoth}";

			}
		}));

		add(new WebMarkupContainer("ghost").add(new YuiResize()
		{
			@Override
			protected String getOpts()
			{
				return "{ handles: 'all', knobHandles: true,  "
						+ "height: '199px', width: '300px',"
						+ "ghost: true, proxy: true, status: true,"
						+ "draggable: true, animate: true, animateDuration: .75, "
						+ "animateEasing: YAHOO.util.Easing.backBoth}";
			}

			@Override
			protected String getStartResizeJs()
			{

				return "function() { " + " var Dom = YAHOO.util.Dom;" + getYuiResizeVar()
						+ ".getProxyEl().innerHTML = '<img src=\"' + " + getYuiResizeVar()
						+ ".get('element').src + '\" style=\"height: 100%; width: 100%;\">';"
						+ " Dom.setStyle(" + getYuiResizeVar()
						+ ".getProxyEl().firstChild, 'opacity', '.25');" + "}";
			}
		}));

	}
}

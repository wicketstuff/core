import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.basic.Label;

import com.googlecode.wicket.jquery.ui.IJQueryWidget;
import com.googlecode.wicket.jquery.ui.JQueryBehavior;
import com.googlecode.wicket.jquery.ui.JQueryEvent;
import com.googlecode.wicket.jquery.ui.ajax.JQueryAjaxBehavior;

public class MyJQueryLabel extends Label implements IJQueryWidget
{
	private static final long serialVersionUID = 1L;

	// Mainly used to cast to the exact type
	class MyEvent extends JQueryEvent
	{
		public MyEvent(AjaxRequestTarget target)
		{
			super(target);
		}
	}

	private JQueryAjaxBehavior onJQueryEventBehavior;

	public MyJQueryLabel(String id)
	{
		super(id);
		this.init();
	}

	private void init()
	{
		this.onJQueryEventBehavior = new JQueryAjaxBehavior(this) {

			private static final long serialVersionUID = 1L;

			@Override
			public String getCallbackFunction()
			{
				return "function(event, ui) { " + this.getCallbackScript() + " }";
			}

			@Override
			protected JQueryEvent newEvent(AjaxRequestTarget target)
			{
				return new MyEvent(target);
			}
		};
	}

	@Override
	public void onEvent(IEvent<?> event)
	{
		if (event.getPayload() instanceof MyEvent)
		{
//			JQueryEvent payload = (JQueryEvent) event.getPayload();
//			AjaxRequestTarget target = payload.getTarget();
			// do something with the target
		}
	}

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.add(this.onJQueryEventBehavior);
		this.add(JQueryWidget.newWidgetBehavior(this));
	}

	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new JQueryBehavior(selector, "jquerymethod") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onConfigure(Component component)
			{
				this.setOption("jqueryevent", onJQueryEventBehavior.getCallbackFunction());
			}
		};
	}
}

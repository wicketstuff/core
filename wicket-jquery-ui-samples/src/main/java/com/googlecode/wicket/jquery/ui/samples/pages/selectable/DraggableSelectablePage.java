package com.googlecode.wicket.jquery.ui.samples.pages.selectable;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;

import com.googlecode.wicket.jquery.ui.IJQueryWidget.JQueryWidget;
import com.googlecode.wicket.jquery.ui.JQueryBehavior;
import com.googlecode.wicket.jquery.ui.Options;
import com.googlecode.wicket.jquery.ui.interaction.Selectable;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;

public class DraggableSelectablePage extends AbstractSelectablePage
{
	private static final long serialVersionUID = 1L;
	
	public DraggableSelectablePage()
	{
		List<String> list = Arrays.asList("item #1", "item #2", "item #3", "item #4", "item #5", "item #6");
		
		// FeedbackPanel //
		final FeedbackPanel feedbackPanel = new JQueryFeedbackPanel("feedback");
		this.add(feedbackPanel.setOutputMarkupId(true));

		// Selectable //
		final Selectable<String> selectable = new Selectable<String>("selectable", list) {
			
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSelect(AjaxRequestTarget target, List<String> items)
			{
				this.info("items: " + items.toString());
				target.add(feedbackPanel);
			}
		};

		this.add(selectable);
		
		// ListView //
		selectable.add(new ListView<String>("items", list) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<String> item)
			{
				// Draggable //
				Panel panel = new EmptyPanel("drag");
				panel.add(AttributeModifier.append("class", "ui-icon ui-icon-arrow-4-diag"));
				panel.add(AttributeModifier.append("style", "display: inline-block"));
				item.add(panel);
				
				// Label //
				Label label = new Label("item", item.getModelObject());
				label.add(AttributeModifier.append("style", "position: relative; top: 2px; vertical-align: top;"));
				item.add(label);
			}
		});

		// Draggable behavior (sandbox) //
		String selector = JQueryWidget.getSelector(selectable);
		String helper = "function() { " +
			    "var container = $('<div/>').attr('id', 'draggingContainer');" +
			    "$('" + selector + "').find('.ui-selected').each(" +
	    		"  function() { " +
	    		// "    console.log($(this));" +
	    		"    container.append($(this).clone()); }" +
	    		"  );" +
			    "  return container; " +
			    "}";

		selectable.add(new JQueryBehavior(selector + " .ui-icon", "draggable", new Options("helper", helper)));
		//TODO: replace with a Draggable. Warning, the Droppable is listening only one Draggable.
		//FIXME: the icon disappears when the lasso is over
	}
}

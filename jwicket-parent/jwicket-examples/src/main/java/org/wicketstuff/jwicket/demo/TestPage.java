package org.wicketstuff.jwicket.demo;


import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;
import org.wicketstuff.jwicket.JQueryAjaxBehavior;
import org.wicketstuff.jwicket.JQuery;
import org.wicketstuff.jwicket.ui.dragdrop.DraggableBehavior;
import org.wicketstuff.jwicket.ui.dragdrop.DraggablesAcceptedByDroppable;



public class TestPage extends WebPage {

	private final DraggableElement draggable1;
	private final DraggableElement draggable2;

	private final DroppableElement droppable1;
	private final DroppableElement droppable2;

	private final DraggableAndDroppableElement draggableDroppable1;
	private final DraggableAndDroppableElement draggableDroppable2;

	private final ResizableElement resizable1;

	private final DraggableAndResizableElement draggableAndResizable1;


	private Label replaceMe;

	public TestPage() {
		super();


		addOrReplace(new Label("title", JQueryAjaxBehavior.getVersion()).setRenderBodyOnly(true));


		DraggablesAcceptedByDroppable defaultValues = new DraggablesAcceptedByDroppable("one", "two", "three");



		add(draggable1 = new DraggableElement("draggable1"));
		draggable1.setOutputMarkupId(true);

		add(draggableDroppable1 = new DraggableAndDroppableElement("draggableDroppable1"));

		add(droppable1 = new DroppableElement("droppable1", defaultValues));



		add(resizable1 = new ResizableElement("resizable1"));

		add(draggableAndResizable1 = new DraggableAndResizableElement("draggableAndResizable1"));




		add(draggable2 = new DraggableElement("draggable2"));

		add(draggableDroppable2 = new DraggableAndDroppableElement("draggableDroppable2"));

		add(droppable2 = new DroppableElement("droppable2", defaultValues));



		final Label dragSwitchLabel = new Label("dragSwitchLabel", "disable dragging");
		dragSwitchLabel.setOutputMarkupId(true);

		AjaxLink<Void> dragSwitch = new AjaxLink<Void>("dragSwitch") {
			private static final long serialVersionUID = 1L;
			private boolean on = true;

			@Override
			public void onClick(AjaxRequestTarget target) {
				if (on) {
					on = false;
					draggable1.disable(target);
					draggable2.disable(target);
					draggableDroppable1.disableDraggable(target);
					draggableDroppable2.disableDraggable(target);
					draggableAndResizable1.disableDraggable(target);
					dragSwitchLabel.setDefaultModelObject("enable dragging");
					target.addComponent(dragSwitchLabel);
				}
				else {
					on = true;
					draggable1.enable(target);
					draggable2.enable(target);
					draggableDroppable1.enableDraggable(target);
					draggableDroppable2.enableDraggable(target);
					draggableAndResizable1.enableDraggable(target);
					dragSwitchLabel.setDefaultModelObject("disable dragging");
					target.addComponent(dragSwitchLabel);
				}
			}
		};
		dragSwitch.add(dragSwitchLabel);
		add(dragSwitch);












		final Label dropSwitchLabel = new Label("dropSwitchLabel", "disable dropping");
		dropSwitchLabel.setOutputMarkupId(true);

		AjaxLink<Void> dropSwitch = new AjaxLink<Void>("dropSwitch") {
			private static final long serialVersionUID = 1L;
			private boolean on = true;

			@Override
			public void onClick(AjaxRequestTarget target) {
				if (on) {
					on = false;
					droppable1.disable(target);
					droppable2.disable(target);
					draggableDroppable1.disableDroppable(target);
					draggableDroppable2.disableDroppable(target);
					dropSwitchLabel.setDefaultModelObject("enable dropping");
					target.addComponent(dropSwitchLabel);
				}
				else {
					on = true;
					droppable1.enable(target);
					droppable2.enable(target);
					draggableDroppable1.enableDroppable(target);
					draggableDroppable2.enableDroppable(target);
					dropSwitchLabel.setDefaultModelObject("disable dropping");
					target.addComponent(dropSwitchLabel);
				}
			}
		};
		dropSwitch.add(dropSwitchLabel);
		add(dropSwitch);








		final Label resizeSwitchLabel = new Label("resizeSwitchLabel", "disable resizing");
		resizeSwitchLabel.setOutputMarkupId(true);

		AjaxLink<Void> resizeSwitch = new AjaxLink<Void>("resizeSwitch") {
			private static final long serialVersionUID = 1L;
			private boolean on = true;

			@Override
			public void onClick(AjaxRequestTarget target) {
				if (on) {
					on = false;
					resizable1.disable(target);
					draggableAndResizable1.disableResizable(target);
					resizeSwitchLabel.setDefaultModelObject("enable resizing");
					target.addComponent(resizeSwitchLabel);
				}
				else {
					on = true;
					resizable1.enable(target);
					draggableAndResizable1.enableResizable(target);
					resizeSwitchLabel.setDefaultModelObject("disable resizing");
					target.addComponent(resizeSwitchLabel);
				}
			}
		};
		resizeSwitch.add(resizeSwitchLabel);
		add(resizeSwitch);




		add(new Label("version", "jWicket: Wicket jQuery Integration - Demonstration  " + JQuery.getVersion()));






		replaceMe = new Label("replaceMe", "replace me");
		replaceMe.add(new DraggableBehavior());

		replaceMe.add(new AjaxEventBehavior("onclick") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onEvent(AjaxRequestTarget target) {
				System.out.println("klicked!");
			}

		});

		add(replaceMe);




		add(new AjaxLink<Void>("ajaxLink"){
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				DraggableElement newDraggableElement = new DraggableElement("draggable1", new Model<String>("Am I still draggable?"));
				newDraggableElement.setOutputMarkupId(true);
				TestPage.this.addOrReplace(newDraggableElement);
				target.addComponent(newDraggableElement);
			}
		});

		add(new Link<Void>("link"){
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(TestPage.class);
			}
		});

















		WebMarkupContainer ganzeSpalte = new WebMarkupContainer("ganzeSpalte");
		ganzeSpalte.add(new DraggableBehavior());


		ganzeSpalte.add(new DraggableAndDroppableElement("a"));
		ganzeSpalte.add(new DroppableElement("b"));
		ganzeSpalte.add(new DraggableAndDroppableElement("c"));




		add(ganzeSpalte);
	}

}

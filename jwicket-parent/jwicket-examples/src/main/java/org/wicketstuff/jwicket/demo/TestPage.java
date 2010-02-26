package org.wicketstuff.jwicket.demo;


import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.wicketstuff.jwicket.JQuery;
import org.wicketstuff.jwicket.ui.dragdrop.DraggableBehavior;
import org.wicketstuff.jwicket.ui.dragdrop.DraggablesAcceptedByDroppable;
import org.wicketstuff.jwicket.ui.effect.AbstractJqueryUiEffect;
import org.wicketstuff.jwicket.ui.effect.Blind;
import org.wicketstuff.jwicket.ui.effect.Bounce;
import org.wicketstuff.jwicket.ui.effect.Clip;
import org.wicketstuff.jwicket.ui.effect.Drop;
import org.wicketstuff.jwicket.ui.effect.EffectDirection;
import org.wicketstuff.jwicket.ui.effect.EffectHorVerDirection;
import org.wicketstuff.jwicket.ui.effect.EffectMode;
import org.wicketstuff.jwicket.ui.effect.Explode;
import org.wicketstuff.jwicket.ui.effect.Fold;
import org.wicketstuff.jwicket.ui.effect.Highlight;
import org.wicketstuff.jwicket.ui.effect.Puff;
import org.wicketstuff.jwicket.ui.effect.Pulsate;
import org.wicketstuff.jwicket.ui.effect.Scale;
import org.wicketstuff.jwicket.ui.effect.Shake;
import org.wicketstuff.jwicket.ui.effect.Slide;
import org.wicketstuff.jwicket.ui.effect.Transfer;
import org.wicketstuff.jwicket.ui.effect.Bounce.BounceMode;
import org.wicketstuff.jwicket.ui.effect.Scale.ScaleDirection;
import org.wicketstuff.jwicket.ui.effect.Scale.ScaleElement;


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


		addOrReplace(new Label("title", JQuery.getVersion()).setRenderBodyOnly(true));


		DraggablesAcceptedByDroppable defaultValues = new DraggablesAcceptedByDroppable("one", "two", "three");



		add(draggable1 = new DraggableElement("draggable1"));
		draggable1.setOutputMarkupId(true);

		add(draggableDroppable1 = new DraggableAndDroppableElement("draggableDroppable1"));

		add(droppable1 = new DroppableElement("droppable1", defaultValues));



		add(resizable1 = new ResizableElement("resizable1"));

		add(draggableAndResizable1 = new DraggableAndResizableElement("draggableAndResizable1"));
		draggableAndResizable1.setOutputMarkupId(true);




		add(draggable2 = new DraggableElement("draggable2"));
		draggable2.setOutputMarkupId(true);

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


		
		
		
		final Explode explode = new Explode();
		explode.setSpeed(1000);
		explode.setPieces(3);
		add(explode);
//		draggable2.add(effect);
		
		
		/*********************************************************************
		 * Prepare a bunch of effects. Need to add them here for header
		 * contribution.
		 */
		final List<AbstractJqueryUiEffect> postEffects = new ArrayList<AbstractJqueryUiEffect>();
		
		Blind blindOut = new Blind();
		add(blindOut);
		blindOut.setDirection(EffectHorVerDirection.VERTICAL);
		blindOut.setMode(EffectMode.HIDE);
		blindOut.setSpeed(1000);
		postEffects.add(blindOut);

		Blind blindIn = new Blind();
		add(blindIn);
		blindOut.setDirection(EffectHorVerDirection.HORIZONTAL);
		blindIn.setMode(EffectMode.SHOW);
		blindIn.setSpeed(1000);
		blindIn.setEffectClass("blindInClass");
		postEffects.add(blindIn);


		Bounce bounceHide = new Bounce();
		add(bounceHide);
		bounceHide.setDistance(100);
		bounceHide.setTimes(3);
		bounceHide.setMode(BounceMode.HIDE);
		bounceHide.setDirection(EffectDirection.RIGHT);
		bounceHide.setSpeed(1000);
		postEffects.add(bounceHide);
		
		Bounce bounceShow = new Bounce();
		add(bounceShow);
		bounceShow.setDistance(100);
		bounceShow.setTimes(3);
		bounceShow.setMode(BounceMode.SHOW);
		bounceShow.setDirection(EffectDirection.LEFT);
		bounceShow.setSpeed(1000);
		postEffects.add(bounceShow);


		Clip clip1 = new Clip();
		add(clip1);
		clip1.setSpeed(1000);
		clip1.setMode(EffectMode.HIDE);
		clip1.setDirection(EffectHorVerDirection.VERTICAL);
		postEffects.add(clip1);
		
		Clip clip2 = new Clip();
		add(clip2);
		clip2.setSpeed(1000);
		clip2.setMode(EffectMode.SHOW);
		clip2.setDirection(EffectHorVerDirection.HORIZONTAL);
		postEffects.add(clip2);


		Drop drop1 = new Drop();
		add(drop1);
		drop1.setSpeed(1000);
		drop1.setDirection(EffectDirection.RIGHT);
		drop1.setMode(EffectMode.HIDE);
		postEffects.add(drop1);

		Drop drop2 = new Drop();
		add(drop2);
		drop2.setSpeed(1000);
		drop2.setDirection(EffectDirection.UP);
		drop2.setMode(EffectMode.SHOW);
		postEffects.add(drop2);
		

		Fold fold1 = new Fold();
		add(fold1);
		fold1.setSpeed(1000);
		fold1.setMode(EffectMode.HIDE);
		fold1.setSize(400);
		postEffects.add(fold1);

		Fold fold2 = new Fold();
		add(fold2);
		fold2.setHorizFirst(false);
		fold2.setSpeed(1000);
		fold2.setMode(EffectMode.SHOW);
		fold2.setSize(300);
		postEffects.add(fold2);


		Highlight highlight1 = new Highlight();
		add(highlight1);
		highlight1.setSpeed(1000);
		highlight1.setMode(EffectMode.HIDE).setColor("#000000");
		postEffects.add(highlight1);

		Highlight highlight2 = new Highlight();
		add(highlight2);
		highlight2.setSpeed(1000);
		highlight2.setMode(EffectMode.SHOW).setColor("#00ffff");
		postEffects.add(highlight2);


		Pulsate pulsate1 = new Pulsate();
		add(pulsate1);
		pulsate1.setSpeed(10);
		pulsate1.setMode(EffectMode.HIDE).setTimes(4);
		postEffects.add(pulsate1);

		Pulsate pulsate2 = new Pulsate();
		add(pulsate2);
		pulsate2.setSpeed(10);
		pulsate2.setMode(EffectMode.SHOW).setTimes(4);
		postEffects.add(pulsate2);

		
		Scale scale1 = new Scale();
		add(scale1);
		scale1.setSpeed(1000);
		scale1.setDirection(ScaleDirection.BOTH);
		scale1.setElement(ScaleElement.BOTH);
		scale1.setFrom(10, 10);
		scale1.setPercen(200);
		postEffects.add(scale1);
		
		Scale scale2 = new Scale();
		add(scale2);
		scale2.setSpeed(1000);
		scale2.setDirection(ScaleDirection.BOTH);
		scale2.setElement(ScaleElement.BOTH);
		scale2.setPercen(50);
		postEffects.add(scale2);

		
		Shake shake1 = new Shake();
		add(shake1);
		shake1.setSpeed(50);
		shake1.setDirection(EffectDirection.DOWN);
		shake1.setDistance(15);
		shake1.setTimes(2);
		postEffects.add(shake1);


		Slide slide1 = new Slide();
		add(slide1);
		slide1.setSpeed(1000);
		slide1.setDirection(EffectDirection.LEFT).setDistance(200).setMode(EffectMode.HIDE);
		postEffects.add(slide1);

		Slide slide2 = new Slide();
		add(slide2);
		slide2.setSpeed(1000);
		slide2.setDirection(EffectDirection.RIGHT).setDistance(200).setMode(EffectMode.SHOW);
		slide2.setFadeInAfter(10);
		postEffects.add(slide1);
		
		
		Transfer transfer = new Transfer();
		add(transfer);
		transfer.setSpeed(2000);
		try {
			transfer.setTo(draggable2);
		} catch (Exception e) {
			throw new WicketRuntimeException(e);
		}
		transfer.setFadeInAfter(10);
		postEffects.add(transfer);


		System.out.println("---------- prepariere den ersten Effekt");



		List<AbstractJqueryUiEffect> postEffects2 = new ArrayList<AbstractJqueryUiEffect>();
		
		final Puff puff = new Puff();
		add(puff);
		puff.setMode(EffectMode.SHOW);
		puff.setPercen(5);
		puff.setSpeed(10000);
		
		postEffects2.add(puff);
		postEffects2.add(highlight1);
		postEffects2.add(highlight2);
		postEffects2.add(transfer);


		add(new AjaxLink<Void>("ajaxLink"){
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
/*
				DraggableElement newDraggableElement = new DraggableElement("draggable1", new Model<String>("Am I still draggable?"));
				newDraggableElement.setOutputMarkupId(true);
				TestPage.this.addOrReplace(newDraggableElement);
				target.addComponent(newDraggableElement);
*/				
				
				
//				effect.fire(target, replaceMe);

				
				
				explode.fire(target, postEffects, draggable1);
				System.out.println("---------- prepariere den ZWEITEN Effekt");
				//explode.fire(target, postEffects2, draggable2);
				puff.fire(target, draggable2);

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

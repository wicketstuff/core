package org.wicketstuff.jwicket.demo;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.wicket.Application;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.wicketstuff.jwicket.JQuery;
import org.wicketstuff.jwicket.SpecialKeys;
import org.wicketstuff.jwicket.tooltip.WTooltip;
import org.wicketstuff.jwicket.ui.datepicker.DatePicker;
import org.wicketstuff.jwicket.ui.dragdrop.DraggableBehavior;
import org.wicketstuff.jwicket.ui.dragdrop.DraggablesAcceptedByDroppable;
import org.wicketstuff.jwicket.ui.effect.AbstractJqueryUiEffect;
import org.wicketstuff.jwicket.ui.effect.Blind;
import org.wicketstuff.jwicket.ui.effect.Bounce;
import org.wicketstuff.jwicket.ui.effect.Bounce.BounceMode;
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
import org.wicketstuff.jwicket.ui.effect.Scale.ScaleDirection;
import org.wicketstuff.jwicket.ui.effect.Scale.ScaleElement;
import org.wicketstuff.jwicket.ui.effect.Shake;
import org.wicketstuff.jwicket.ui.effect.Slide;
import org.wicketstuff.jwicket.ui.effect.Transfer;
import org.wicketstuff.menu.IMenuLink;
import org.wicketstuff.menu.Menu;
import org.wicketstuff.menu.MenuBarPanel;


public class TestPage extends WebPage {
    private static final long serialVersionUID = -4903898352269701250L;

    private final DraggableElement draggable1;
    private final DraggableElement draggable2;

    private final DroppableElement droppable1;
    private final DroppableElement droppable2;

    private final DraggableAndDroppableElement draggableDroppable1;
    private final DraggableAndDroppableElement draggableDroppable2;

    private final ResizableElement resizable1;

    private final DraggableAndResizableElement draggableAndResizable1;

    private final Explode explode = new Explode();
    private final Puff puff = new Puff();
    private final List<AbstractJqueryUiEffect> postEffects;
    private final List<AbstractJqueryUiEffect> postEffects2;

    public TestPage() {
        super();

        Image wTooltipImage = new Image("wTooltipImage", new PackageResourceReference(TestPage.class, "QuestionMark.jpeg"));
        wTooltipImage.add(new WTooltip("this is de wToolTipText"));
        this.add(wTooltipImage);

        /* Demo for a simple menu bar ***************************************************/

        /* Menu 1 with some static links */
        List<IMenuLink> itemsForMenu1 = new ArrayList<IMenuLink>();

        // Link to Homepage
        itemsForMenu1.add(new IMenuLink() {
            private static final long serialVersionUID = 1L;

            @Override
            public Component getDisplayComponent(String id) {
                //return new Label(id, "Home");
                return new Image(id, new PackageResourceReference(TestPage.class, "P_orange_81x81.gif"))
                        .add(AttributeModifier.replace("alt", ""))
                        ;
            }

            @Override
            public AbstractLink getLink(String id) {
                return new BookmarkablePageLink<Void>(id, Application.get().getHomePage());
            }
        });

        // Link to Apache Wicket
        itemsForMenu1.add(new IMenuLink() {
            private static final long serialVersionUID = 1L;

            @Override
            public Component getDisplayComponent(String id) {
                return new Label(id, "Apache Wicket");
            }

            @Override
            public AbstractLink getLink(String id) {
                return new ExternalLink(id, "http://www.wicketframework.org");
            }
        });

        // Link to Wicketstuff
        itemsForMenu1.add(new IMenuLink() {
            private static final long serialVersionUID = 1L;

            @Override
            public Component getDisplayComponent(String id) {
                return new Label(id, "Wicketstuff");
            }

            @Override
            public AbstractLink getLink(String id) {
                return new ExternalLink(id, "http://www.wicketstuff.org");
            }
        });

        // Disabled Link to nowhere
        itemsForMenu1.add(new IMenuLink() {
            private static final long serialVersionUID = 1L;

            @Override
            public Component getDisplayComponent(String id) {
                return new Label(id, "Nowhere");
            }

            @Override
            public AbstractLink getLink(String id) {
                ExternalLink link = new ExternalLink(id, "http://www.nowhere.somewhere");
                link.setEnabled(false);
                return link;
            }
        });


        Menu menu1 = new Menu(new Model<>("Pages"), itemsForMenu1);
        /* End of menu 1 */


        /* Menu 2 with some AJAX links for drag/drop and resize control */
        final Menu menu2 = new Menu(new Model<>("Ajax Control"));

        // Enable/disable dragging
        menu2.addMenuItem(new IMenuLink() {
            private static final long serialVersionUID = 1L;
            private boolean isEnabled = true;
            private Model<String> labelModel = new Model<String>("disable drag");

            @Override
            public Component getDisplayComponent(String id) {
                return new Label(id, labelModel);
            }

            @Override
            public AbstractLink getLink(String id) {
                return new AjaxLink<Void>(id) {
                    private static final long serialVersionUID = 1L;

                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        if (isEnabled) {
                            isEnabled = false;
                            labelModel.setObject("enable drag");
                            draggable1.disable(target);
                            draggable2.disable(target);
                            draggableDroppable1.disableDraggable(target);
                            draggableDroppable2.disableDraggable(target);
                            draggableAndResizable1.disableDraggable(target);
                        } else {
                            isEnabled = true;
                            labelModel.setObject("disable drag");
                            draggable1.enable(target);
                            draggable2.enable(target);
                            draggableDroppable1.enableDraggable(target);
                            draggableDroppable2.enableDraggable(target);
                            draggableAndResizable1.enableDraggable(target);
                        }

                        // Redraw menu
                        menu2.redraw(target);
                    }
                };
            }
        });


        // Enable/disable dropping
        menu2.addMenuItem(new IMenuLink() {
            private static final long serialVersionUID = 1L;
            private boolean isEnabled = true;
            private Model<String> labelModel = new Model<String>("disable drop");

            @Override
            public Component getDisplayComponent(String id) {
                return new Label(id, labelModel);
            }

            @Override
            public AbstractLink getLink(String id) {
                return new AjaxLink<Void>(id) {
                    private static final long serialVersionUID = 1L;

                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        if (isEnabled) {
                            isEnabled = false;
                            labelModel.setObject("enable drop");
                            droppable1.disable(target);
                            droppable2.disable(target);
                            draggableDroppable1.disableDroppable(target);
                            draggableDroppable2.disableDroppable(target);
                        } else {
                            isEnabled = true;
                            labelModel.setObject("disable drop");
                            droppable1.enable(target);
                            droppable2.enable(target);
                            draggableDroppable1.enableDroppable(target);
                            draggableDroppable2.enableDroppable(target);
                        }

                        // Redraw menu
                        menu2.redraw(target);
                    }
                };
            }
        });


        // Enable/disable resize
        menu2.addMenuItem(new IMenuLink() {
            private static final long serialVersionUID = 1L;
            private boolean isEnabled = true;
            private Model<String> labelModel = new Model<String>("disable resize");

            @Override
            public Component getDisplayComponent(String id) {
                return new Label(id, labelModel);
            }

            @Override
            public AbstractLink getLink(String id) {
                return new AjaxLink<Void>(id) {
                    private static final long serialVersionUID = 1L;

                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        if (isEnabled) {
                            isEnabled = false;
                            labelModel.setObject("enable resize");
                            resizable1.disable(target);
                            draggableAndResizable1.disableResizable(target);
                        } else {
                            isEnabled = true;
                            labelModel.setObject("disable resize");
                            resizable1.enable(target);
                            draggableAndResizable1.enableResizable(target);
                        }

                        // Redraw menu
                        menu2.redraw(target);
                    }
                };
            }
        });


        // Enable/disable resize
        menu2.addMenuItem(new IMenuLink() {
            private static final long serialVersionUID = 1L;
            private Model<String> labelModel = new Model<String>("show animations");

            @Override
            public Component getDisplayComponent(String id) {
                return new Label(id, labelModel);
            }

            @Override
            public AbstractLink getLink(String id) {
                return new AjaxLink<Void>(id) {
                    private static final long serialVersionUID = 1L;

                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        // Redraw menu
                        menu2.redraw(target);

                        // Show the effects
                        explode.fire(target, postEffects, draggable1);
                        puff.fire(target, postEffects2, draggable2);
                    }
                };
            }
        });

        /* End of menu 2 */


        /* The Menus as a list */
        List<Menu> menus = new ArrayList<Menu>(2);
        menus.add(menu1);
        menus.add(menu2);


        /* And the menubar itself */
        add(new MenuBarPanel("simpleMenu", menus));


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


        add(new Label("version", "jWicket: Wicket jQuery Integration - Demonstration  " + JQuery.getVersion()));


        explode.setSpeed(1000);
        explode.setPieces(3);
        add(explode);
//		draggable2.add(effect);


        /*********************************************************************
         * Prepare a bunch of effects. Need to add them here for header
         * contribution.
         */
        postEffects = new ArrayList<AbstractJqueryUiEffect>();

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


        postEffects2 = new ArrayList<AbstractJqueryUiEffect>();

        add(puff);
        puff.setMode(EffectMode.SHOW);
        puff.setPercen(5);
        puff.setSpeed(10000);

        postEffects2.add(puff);
        postEffects2.add(highlight1);
        postEffects2.add(highlight2);
        postEffects2.add(transfer);


        WebMarkupContainer ganzeSpalte = new WebMarkupContainer("ganzeSpalte");
        ganzeSpalte.add(new DraggableBehavior());


        ganzeSpalte.add(new DraggableAndDroppableElement("a"));
        ganzeSpalte.add(new DroppableElement("b"));
        ganzeSpalte.add(new DraggableAndDroppableElement("c"));

        add(ganzeSpalte);

        final WebMarkupContainer datecontainer = new WebMarkupContainer("dateRedrawContainer");
        datecontainer.setOutputMarkupId(true);
        add(datecontainer);


        final TextField<Date> datePickerInput = new TextField<Date>("datePickerInput", new Model<Date>());
        DatePicker datePicker = new DatePicker(new PackageResourceReference(TestPage.class, "calendar20x22.gif")) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void onSelect(final AjaxRequestTarget target, final String pickedDate, final SpecialKeys specialKeys) {
                target.appendJavaScript(
                        "alert('Selected Date: " + pickedDate + ", Pressed keys: " +
                                specialKeys.toString() + "');"
                );
            }

            @Override
            protected void onChangeMonthYear(final AjaxRequestTarget target, final String year, final String month, final SpecialKeys specialKeys) {
                target.appendJavaScript(
                        "alert('Selected Year: " + year + ", selected month: " + month + ", Pressed keys: " +
                                specialKeys.toString() + "');"
                );
            }
        }
                .setWantOnSelectNotification(true)
                .setWantOnChangeMonthYearNotification(true)
                ;
        datePickerInput.add(datePicker);
        datecontainer.add(datePickerInput);


        add(new AjaxLink<Void>("ajaxLinkRedrawDate") {
            private static final long serialVersionUID = 1L;

            @Override
            public void onClick(AjaxRequestTarget target) {
                datePickerInput.setModelObject(new Date());
                target.add(datePickerInput);
            }
        });


        add(new AjaxLink<Void>("ajaxLinkRedrawDateContainer") {
            private static final long serialVersionUID = 1L;

            @Override
            public void onClick(AjaxRequestTarget target) {
                datePickerInput.setModelObject(new Date());
                target.add(datecontainer);
            }
        });


        //inline datePicker
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, -5);

        WebMarkupContainer inlineDatePickerContainer = new WebMarkupContainer("inlineDatePickerContainer");
        DatePicker inlineDatePicker = new DatePicker(){

            @Override
            public void setRestoreAfterRedraw(boolean value) {
                super.setRestoreAfterRedraw(true);
            }

            @Override
            protected void onSelect(final AjaxRequestTarget target, final String pickedDate, final SpecialKeys specialKeys) {
                target.appendJavaScript(
                        "alert('Selected Date: " + pickedDate + ", Pressed keys: " +
                                specialKeys.toString() + "');"
                );


            }
        }.setWantOnSelectNotification(true)
         .setMaxDate(new Date())
         .setChangeMonth(true)
         .setChangeYear(true)
         .setNumberOfMonths(3)
          .setShowButtonPanel(true)
          .setYearRange("-30:+0")
           .setMonthNamesShort(new String[]{ "Jan", "Feb", "Mar", "Apr", "Maj", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dec"})
          .setDefaultDate(c.getTime()) ;
        c.set(Calendar.DAY_OF_MONTH, 1);


        inlineDatePickerContainer.add(inlineDatePicker);
        add(inlineDatePickerContainer);

    }
}

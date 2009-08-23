package org.wicketstuff.jquery.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.wicketstuff.jquery.Options;
import org.wicketstuff.jquery.block.BlockOptions;
import org.wicketstuff.jquery.block.BlockingAjaxLink;
import org.wicketstuff.jquery.jgrowl.JGrowlFeedbackPanel;
import org.wicketstuff.jquery.sparkline.Sparkline;
import org.wicketstuff.jquery.sparkline.SparklineOptions;
import org.wicketstuff.jquery.sparkline.SparklineOptions.TYPE;

@SuppressWarnings("serial")
public class Page4Sparkline extends PageSupport {

	public Page4Sparkline() {
	  
	  RepeatingView rv = new RepeatingView( "spot" );
	  add( rv );
	  
	  
	  WebMarkupContainer spot = new WebMarkupContainer( rv.newChildId() );
	  Sparkline s = new Sparkline( "chart",  5,6,7,9,9,5,3,2,2,4,6,7 );
	  spot.add( s );
	  spot.add( new Label( "js", s.getSparklineJS().toString() ) );
	  rv.add( spot );
	  
	  // BAR
	  SparklineOptions options = new SparklineOptions( TYPE.bar );
	  spot = new WebMarkupContainer( rv.newChildId() );
    s = new Sparkline( "chart", options, 5,6,7,2,0,-4,-2,4 );
    spot.add( s );
    spot.add( new Label( "js", s.getSparklineJS().toString() ) );
    rv.add( spot );
    
    // TRISTATE
    options = new SparklineOptions( TYPE.tristate );
    spot = new WebMarkupContainer( rv.newChildId() );
    s = new Sparkline( "chart", options, -1,1,1,2,0,-1,-2,1,1 );
    spot.add( s );
    spot.add( new Label( "js", s.getSparklineJS().toString() ) );
    rv.add( spot );
    

    // DISCRETE
    options = new SparklineOptions( TYPE.discrete );
    spot = new WebMarkupContainer( rv.newChildId() );
    s = new Sparkline( "chart", options, 5,6,7,9,9,5,3,2,2,4,6,7 );
    spot.add( s );
    spot.add( new Label( "js", s.getSparklineJS().toString() ) );
    rv.add( spot );
    

    // PIE
    options = new SparklineOptions( TYPE.pie );
    spot = new WebMarkupContainer( rv.newChildId() );
    s = new Sparkline( "chart", options, 1,2,4 );
    spot.add( s );
    spot.add( new Label( "js", s.getSparklineJS().toString() ) );
    rv.add( spot );
	}

}

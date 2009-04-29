package org.wicketstuff.jquery.demo;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.wicketstuff.jquery.Options;
import org.wicketstuff.jquery.block.BlockOptions;
import org.wicketstuff.jquery.block.BlockingAjaxLink;
import org.wicketstuff.jquery.jgrowl.JGrowlFeedbackPanel;

@SuppressWarnings("serial")
public class Page4Block extends PageSupport {

	public Page4Block() {

	  WebMarkupContainer body = new WebMarkupContainer( "demo" );
	  add( body );
	  
	  BlockOptions options = new BlockOptions();
	  options.setMessage( "Hello!!!" );
	  

	  body.add( new BlockingAjaxLink<Void>( "ajaxlink", "hello!" ) {
      @Override
      public void doClick(AjaxRequestTarget target) {
        try {
          Thread.sleep( 3000 );
        } 
        catch (InterruptedException e) {
          e.printStackTrace();
        }
        info( "Clicked link: "+this.toString() );
        target.addChildren( getPage(), FeedbackPanel.class );
      }
    });
    
	  body.add( new BlockingAjaxLink<Void>( "block2", "hello!" ) {
      @Override
      public void doClick(AjaxRequestTarget target) {
        try {
          Thread.sleep( 3000 );
        } 
        catch (InterruptedException e) {
          e.printStackTrace();
        }
        info( "Clicked link: "+this.toString() );
        target.addChildren( getPage(), FeedbackPanel.class );
      }
      
      public CharSequence getBlockElementsSelector() {
        return "div.blockMe";
      }
    });
	}

}

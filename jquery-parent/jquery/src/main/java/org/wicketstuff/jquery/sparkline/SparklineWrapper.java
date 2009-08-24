package org.wicketstuff.jquery.sparkline;

import java.util.List;
import java.util.Map;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.MetaDataKey;
import org.apache.wicket.ajax.AbstractAjaxTimerBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.jquery.sparkline.Sparkline;
import org.wicketstuff.jquery.sparkline.SparklineOptions;


public class SparklineWrapper extends Panel
{
  public static final String SPARKID = "sparkline";
  
  /**
   * Construct.
   *
   * @param id
   */
  public SparklineWrapper(String id, final Sparkline line)
  {
    super(id);
    line.setWriteJSOnReady(false);

    this.add( line );
    this.add( new Label( "js", new AbstractReadOnlyModel<CharSequence>() {
      @Override
      public CharSequence getObject() {
        return line.getSparklineJS();
      }
    }).setEscapeModelStrings(false));
  }
}





package org.wicketstuff.jquery.sparkline;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;


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





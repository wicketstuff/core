/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.jquery.sparkline;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.resources.CompressedResourceReference;
import org.apache.wicket.markup.parser.XmlTag;
import org.apache.wicket.model.IModel;
import org.wicketstuff.jquery.JQueryBehavior;

public class Sparkline extends WebComponent implements IHeaderContributor
{
  public static final CompressedResourceReference SPARKLINE_JS 
    = new CompressedResourceReference(Sparkline.class, "jquery.sparkline-1.4.3.min.js");

  final SparklineOptions options;
  final IModel<Collection<Integer>> model;
  final CharSequence values;
  boolean writeJSOnReady = true; 
  
  public Sparkline(final String id, 
      IModel<Collection<Integer>> values, 
      SparklineOptions options )
	{
		super(id);
		this.setOutputMarkupId(true);
		this.model = values;
		this.values = null;
		this.options = options;
	}

  public Sparkline(final String id, 
      Collection<Integer> values, 
      SparklineOptions options )
  {
    super(id);
    this.setOutputMarkupId(true);
    this.model = null;
    this.values = values.toString();
    this.options = options;
  }

  public Sparkline(final String id, 
      SparklineOptions options,
      int ... values )
  {
    super(id);
    this.setOutputMarkupId(true);
    this.model = null;
    this.options = options;

    StringBuilder v = new StringBuilder();
    v.append( '[' );
    for( int i=0; i<values.length; i++ ) {
      if( i > 0 ) {
        v.append(',');
      }
      v.append( values[i] );
    }
    v.append( ']' );
    this.values = v;
  }

  public Sparkline(final String id, 
      int ... values )
  {
    this(id, null, values );
  }

  //---------------------------------------------------------------
  //---------------------------------------------------------------

  public boolean isWriteJSOnReady() {
    return writeJSOnReady;
  }

  public Sparkline setWriteJSOnReady(boolean writeJSOnReady) {
    this.writeJSOnReady = writeJSOnReady;
    return this;
  }

  public CharSequence getSparklineJS() {
    StringBuilder js = new StringBuilder();
    js.append( "$('#" ).append( this.getMarkupId() ).append( "' ).sparkline( " );
    if( model != null ) {
      Collection<Integer> vals = model.getObject();
      js.append( '[' );
      Iterator<Integer> iter = vals.iterator();
      while( iter.hasNext() ) {
        js.append( iter.next() );
        if( iter.hasNext() ) {
          js.append( ',' );
        }
      }
      js.append( ']' );
    }
    else {
      js.append( values );
    }
    if( options != null ) {
      js.append( ", " ).append( options.toString(false) );
    }
    js.append( " );" );
    return js;
  }
  
  public void renderHead(IHeaderResponse response) {
    response.renderJavascriptReference(JQueryBehavior.JQUERY_JS);
    response.renderJavascriptReference(SPARKLINE_JS);

    if( writeJSOnReady ) {
      StringBuilder builder = new StringBuilder();
      builder.append("$(document).ready(function(){\n");
      builder.append(getSparklineJS());
      builder.append("\n});");
      response.renderJavascript(builder, null );
    }
  }

  @Override
  protected void onComponentTagBody(final MarkupStream markupStream, final ComponentTag openTag)
  {
    replaceComponentTagBody(markupStream, openTag, "sparkline" );
  }

  @Override
  protected void onComponentTag(ComponentTag tag)
  {
    super.onComponentTag(tag);
    // always transform the tag to <span></span> so even labels defined as <span/> render
    tag.setType(XmlTag.OPEN);
  }
}

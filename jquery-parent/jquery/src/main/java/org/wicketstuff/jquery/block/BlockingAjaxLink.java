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
package org.wicketstuff.jquery.block;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.resources.CompressedResourceReference;
import org.wicketstuff.jquery.JQueryBehavior;

public abstract class BlockingAjaxLink<T> extends AjaxLink<T> implements IHeaderContributor
{
  public static final CompressedResourceReference BLOCK_JS = new CompressedResourceReference(BlockingAjaxLink.class, "jquery.blockUI.js");

  final BlockOptions options;
  
  public BlockingAjaxLink(final String id, BlockOptions options )
	{
		super(id, null);
		this.options = options;
	}

  public BlockingAjaxLink(final String id, String message )
  {
    this(id, new BlockOptions().setMessage( message ) );
  }

  public void renderHead(IHeaderResponse response) {
    response.renderJavascriptReference(JQueryBehavior.JQUERY_JS);
    response.renderJavascriptReference(BLOCK_JS);
  }
  
	/**
	 * Returns ajax call decorator that will be used to decorate the ajax call.
	 * 
	 * @return ajax call decorator
	 */
	protected IAjaxCallDecorator getAjaxCallDecorator()
	{
		return new IAjaxCallDecorator() {
      public CharSequence decorateScript(CharSequence script) {
        StringBuilder js = new StringBuilder();
        CharSequence sel = getBlockElementsSelector();
        if( sel != null ) {
          js.append( "$('" ).append( sel ).append( "').block( " );
        }
        else {
          js.append( "$.blockUI( " );
        }
        return js.append( options.toString() ).append( " ); " ).append( script );
      }
      
      public CharSequence decorateOnFailureScript(CharSequence script) {
        return script;
      }

      public CharSequence decorateOnSuccessScript(CharSequence script) {
        return script;
      }
		};
	}

	public CharSequence getBlockElementsSelector()
	{
	  return null;
	}
	
  final public void onClick(final AjaxRequestTarget target)
  {
    doClick( target );

    CharSequence sel = getBlockElementsSelector();
    if( sel != null ) {
      target.appendJavascript( "$('"+sel+"').unblock(); " );
    }
    else {
      target.appendJavascript( "$.unblockUI(); " );
    }
  }
  
  public abstract void doClick(final AjaxRequestTarget target);
}

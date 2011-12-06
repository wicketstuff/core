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
package org.wicketstuff.dojo11.markup.html.toaster;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.HeaderContributor;
import org.wicketstuff.dojo11.DojoIdConstants;
import org.wicketstuff.dojo11.IDojoWidget;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.time.Duration;

/**
 * A dojo toaster is a message that will appear in a corner of the screen such.
 * <p>
 * <b>Sample</b>
 * 	<pre>
 * package org.wicketstuff.dojo11.examples;
 * 
 * import org.apache.wicket.PageParameters;
 * import org.apache.wicket.ajax.AjaxRequestTarget;
 * import org.apache.wicket.ajax.markup.html.AjaxLink;
 * import org.wicketstuff.dojo11.markup.html.toaster.DojoToaster;
 * import org.apache.wicket.markup.html.WebPage;
 * import org.apache.wicket.model.Model;
 * import org.apache.wicket.util.time.Duration;
 * 
 * public class ToasterSample extends WebPage {
 * 	
 * 	public ToasterSample(PageParameters parameters){
 * 		final DojoToaster toaster1 = new DojoToaster("toaster1",new Model("Some messages here. Funy isn\'t it ;)"));
 * 		AjaxLink link1 = new AjaxLink( "link1"){
 * 			public void onClick(AjaxRequestTarget target) {
 * 				toaster1.publishMessage(target);
 * 				
 * 			}
 * 		};
 * 		
 * 		final DojoToaster toaster2 = new DojoToaster("toaster2",new Model("Some messages here. Funy isn\'t it ;)"));
 * 		AjaxLink link2 = new AjaxLink("link2"){
 * 			public void onClick(AjaxRequestTarget target) {
 * 				toaster2.publishMessage(target,DojoToaster.ERROR);
 * 				
 * 			}
 * 		};
 * 
 * 		
 * 		final DojoToaster toaster4 = new DojoToaster("toaster4",new Model("Some messages here. Funy isn\'t it ;)"));
 * 		toaster4.setPosition(DojoToaster.BOTTOM_LEFT_UP);
 * 		toaster4.setDuration(Duration.seconds(10));
 * 		AjaxLink link4 = new AjaxLink("link4"){
 * 			public void onClick(AjaxRequestTarget target) {
 * 				toaster4.publishMessage(target,DojoToaster.WARNING);
 * 				
 * 			}
 * 		};
 * 		
 * 		add(toaster1);
 * 		add(link1);
 * 		add(toaster2);
 * 		add(link2);
 * 		add(toaster4);
 * 		add(link4);
 * 	}
 * }
 * 
 *  </pre>
 * </p>
 * @author <a href="http://www.demay-fr.net/blog/index.php/en">Vincent Demay</a>
 *
 */
@SuppressWarnings("serial")
public class DojoToaster extends WebMarkupContainer implements IDojoWidget{

	/**
	 * type of message
	 */
	public enum ToasterMessageType {
		/** info message */
		INFO,
		/** warning message */
		WARNING,
		/** error message */
		ERROR,
		/** fatal error message */
		FATAL;
	}
	
	/**
	 * position of toaster on screen
	 */
	public enum ToasterPosition { 
		/** slide up at bottom right */
		BOTTOM_RIGHT_UP("br-up"),
		/** slide left at bottom right  */
		BOTTOM_RIGHT_LEFT("br-left"),
		/** slide up at bottom left */
		BOTTOM_LEFT_UP ( "bl-up"),
		/** slide right at bottom left */
		BOTTOM_LEFT_RIGHT ( "bl-right"),
		/** slide down at top right */
		TOP_RIGHT_DOWN ( "tr-down"),
		/** slide left at top right */
		TOP_RIGHT_LEFT ( "tr-left"),
		/** slide down at top left  */
		TOP_LEFT_DOWN ( "tl-down"),
		/** slide right at top left */
		TOP_LEFT_RIGHT ( "tl-right");
		
		private String _dojoType;
		private ToasterPosition(String dojoType) {
			_dojoType = dojoType;
		}
		
		private String getPositionString() {
			return _dojoType;
		}
	};
	
	private ToasterPosition position;
	private Duration duration;
	/**
	 * Construct a new DojoToaster. The message displayed in it is the model
	 * @param id the name of the widget
	 * @param model A String representing the message
	 */
	public DojoToaster(String id, IModel model){
		super(id, model);
		add(new DojoToasterHandler());
	}

	/**
	 * Construct a new DojoToaster. The message displayed in it is the model
	 * @param id the name of the widget
	 */
	public DojoToaster(String id){
		super(id);
		add(new DojoToasterHandler());
	}
	
	/**
	 * @see org.wicketstuff.dojo11.IDojoWidget#getDojoType()
	 */
	public String getDojoType()
	{
		return DojoIdConstants.DOJO_TYPE_TOASTER;
	}

	protected void onComponentTag(ComponentTag tag){
		super.onComponentTag(tag);
		if (position != null){
			tag.put("positionDirection", position.getPositionString());
		}
		tag.put("messageTopic", getMarkupId());
		tag.put("templateCssPath", urlFor(new ResourceReference(DojoToaster.class, "Toaster.css")));
		if (duration != null){
			tag.put("showDelay", duration.getMilliseconds()+"");
		}
		tag.put("separator", "<hr/>");
	}

	/**
	 * get the position where the toaster will be displayed
	 * @return the position where the toaster will be displayed
	 */
	public ToasterPosition getPosition(){
		return position;
	}

	/**
	 * set the position where the toaster will be displayed
	 * @param position the position where the toaster will be displayed
	 */
	public void setPosition(ToasterPosition position){
		this.position = position;
	}
	
	/**
	 * Show the massage
	 * @param target ajax target
	 */
	public void publishMessage(AjaxRequestTarget target){
		publishMessage(target, null);
	}

	/**
	 * get the duration
	 * @return duration see {@link Duration}
	 */
	public Duration getDuration(){
		return duration;
	}

	/**
	 * set the direction
	 * @param duration duration see {@link Duration}
	 */
	public void setDuration(Duration duration){
		this.duration = duration;
	}
	
	/**
	 * Show the massage
	 * @param target ajax target
	 * @param type
	 */
	public void publishMessage(AjaxRequestTarget target, final ToasterMessageType type){
		if (target == null) {
	        add(new HeaderContributor(new IHeaderContributor() {
				public void renderHead(IHeaderResponse response) {
					response.renderOnDomReadyJavascript(getPublishMessageJavaScript(type));
				}
	        }));
		} else {
			target.appendJavascript(getPublishMessageJavaScript(type));
		}
	}

	/**
	 * @param type
	 * @return javascript string to display message in toaster
	 */
	public String getPublishMessageJavaScript(ToasterMessageType type)
	{	
		StringBuilder buf = new StringBuilder();
	    buf.append("dijit.byId('").append(getMarkupId()).append("').setContent('").append(getDefaultModelObject()).append("'");
	    if (type != null) {
	    	buf.append(",'").append(type.toString().toLowerCase()).append("'");
	    }
	    buf.append(");");
	    buf.append("dijit.byId('").append(getMarkupId()).append("').show();");    
		return buf.toString();
	}

}

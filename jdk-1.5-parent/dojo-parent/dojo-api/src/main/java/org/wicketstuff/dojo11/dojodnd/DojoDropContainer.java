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
package org.wicketstuff.dojo11.dojodnd;

import java.util.HashSet;
import java.util.Set;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.dojo11.DojoIdConstants;
import org.wicketstuff.dojo11.IDojoWidget;

/**
 * Dojo drrop container
 * <p>
 * 	A drop container is a HTML container used to define a Drop area.
 *  This area is associated with a pattern. This pattern is used to know 
 *  if a {@link DojoDragContainer} can be drag and drop on it 
 * </p>
 * <p>
 * 	<b>Sample</b>
 *  <pre>
 * package org.wicketstuff.dojo11.examples;
 * 
 * import org.apache.wicket.PageParameters;
 * import org.wicketstuff.dojo11.dojodnd.DojoDragContainer;
 * import org.wicketstuff.dojo11.dojodnd.DojoDropContainer;
 * import org.apache.wicket.markup.html.WebPage;
 * import org.apache.wicket.markup.html.image.Image;
 * 
 * public class DnDShower extends WebPage {
 * 	
 * 	public DnDShower(PageParameters parameters){
 * 		DojoDropContainer dropContainer = new DojoDropContainer("dropContainer"){
 * 		
 * 			public void onDrop(DojoDragContainer container, int position) {
 * 				System.out.println("position = " + position);
 * 				System.out.println("DojoDragContainer" + container.getId());
 * 				
 * 			}
 * 		
 * 		};
 * 		add(dropContainer);
 * 		
 * 		DojoDragContainer dragContainer1 = new DojoDragContainer("dragContainer1");
 * 		DojoDragContainer dragContainer2 = new DojoDragContainer("dragContainer2");
 * 		DojoDragContainer dragContainer3 = new DojoDragContainer("dragContainer3");
 * 		add(dragContainer1);
 * 		add(dragContainer2);
 * 		add(dragContainer3);
 * 		
 * 		
 * 		DojoDragContainer dragContainer4 = new DojoDragContainer("dragContainer4");
 * 		DojoDragContainer dragContainer5 = new DojoDragContainer("dragContainer5");
 * 		dropContainer.add(dragContainer4);
 * 		dropContainer.add(dragContainer5);
 * 		
 * 		dragContainer1.add(new Image("pic1"));
 * 		dragContainer2.add(new Image("pic2"));
 * 		dragContainer3.add(new Image("pic3"));
 * 		dragContainer4.add(new Image("pic4"));
 * 		dragContainer5.add(new Image("pic5"));
 * 	}
 * }
 *  </pre>
 * </p>
 * @author <a href="http://www.demay-fr.net/blog/index.php/en">Vincent Demay</a>
 *
 */
public abstract class DojoDropContainer extends WebMarkupContainer implements IDojoWidget
{
	
	/**
	 * default accept id. if no accept is specified, any draggable 
	 * item may be dropped in any drop container
	 */
	public static final String DEFAULT_ACCEPT = "default";
	
	private Set<String> dropIds = new HashSet<String>();
	
	/**
	 * Construct.
	 * @param id
	 */
	public DojoDropContainer(String id)
	{
		this(id, new Model(DEFAULT_ACCEPT));
	}

	/**
	 * Create a DropContainer
	 * @param id Drop container id
	 * @param acceptModel 
	 */
	public DojoDropContainer(String id, IModel acceptModel)
	{
		this(id, new DojoDropContainerHandler(acceptModel));
	}

	/**
	 * Construct.
	 * @param id
	 * @param dojoDropContainerHandler
	 */
	public DojoDropContainer(String id, DojoDropContainerHandler dojoDropContainerHandler)
	{
		super(id);
		this.setOutputMarkupId(true);
		this.dropIds = new HashSet<String>();
		add(dojoDropContainerHandler);
	}
	
	/**
	 * Set the drop pattern. The drop container only accept dragContainer with
	 * the same pattern or all id *
	 * @param patterns drop pattern set
	 */
	public void setDropPattern(Set<String> patterns){
		this.dropIds = patterns;
	}
	
	/**
	 * Add a drop pattern to the drop pattern set. The drop container will accept
	 * only dragConbtainer with a dragPattern contains in the dropPattern set.
	 * If the dropPattern set is empty, all dragPattern will be accpeted
	 * by the dropContainer.
	 * @param pattern a pattern which will be accepted by the dropContainer
	 */
	public void addDropPattern(String pattern){
		this.dropIds.add(pattern);
	}
	
	/**
	 * Drop pattern to specified which Drag container can be dropped on
	 * this Container. If no pattern has been set return * (all)
	 * @return the Drop pattern set
	 */
	public Set<String> getDropPatterns(){
		if (dropIds.isEmpty()){
			Set<String> toReturn  = new HashSet<String>();
			toReturn.add("*");
			return toReturn;
		}
		return dropIds;
	}
	
	/**
	 * Returns the name of the javascript method that will be invoked when the
	 * processing of the ajax callback is complete. The method must have the
	 * following signature: <code>function(type, data, evt)</code> where the
	 * data argument will be the value of the resouce stream provided by
	 * <code>getResponseResourceStream</code> method.
	 * 
	 * For example if we want to echo the value returned by
	 * getResponseResourceStream stream we can implement it as follows: <code>
	 * <pre>
	 *       
	 *       getJsCallbackFunctionName() {return(&quot;handleit&quot;);}
	 *       
	 *       in javascript:
	 *       
	 *       function handleit(type, data, evt) { alert(data); } 
	 * </pre>
	 * </code>
	 * 
	 * @return name of the client-side javascript callback handler
	 */
	protected String getJSCallbackFunctionName()
	{
		return null;
	}
	
	/**
	 * @param target
	 */
	protected final void onAjaxModelUpdated(AjaxRequestTarget target)
	{
		String dragSource = getRequest().getParameter("dragSource");
		int position = Integer.parseInt(getRequest().getParameter("position"));
		LookupChildVisitor visitor = new LookupChildVisitor(dragSource);
		getPage().visitChildren(visitor);
		Component container = visitor.getComponent();
		if (container != null) {
			onDrop(target, container, position);
		}
	}

	
	/**
	 * @see org.wicketstuff.dojo11.IDojoWidget#getDojoType()
	 */
	public String getDojoType()
	{
		return DojoIdConstants.DOJO_TYPE_DND_TARGET;
	}

	/**
	 * This method is triggered when a {@link DojoDragContainer} is dropped
	 * on this container.
	 * @param component {@link DojoDragContainer} dropped
	 * @param position position where it is dropped
	 * @param target {@link AjaxRequestTarget}
	 */
	public abstract void onDrop(AjaxRequestTarget target, Component component, int position);
	
	/**
	 * This class lookup a component in the page component tree by its id
	 * 
	 * @author Vincent Demay
	 *
	 */
	protected class LookupChildVisitor implements IVisitor{

		private String markupId;
		private Component component = null;
		
		/**
		 * Create the visitor using the markupId 
		 * @param markupId markupId to lookup
		 */
		public LookupChildVisitor(String markupId)
		{
			this.markupId = markupId;
		}

		/* (non-Javadoc)
		 * @see wicket.Component.IVisitor#component(wicket.Component)
		 */
		public Object component(Component component)
		{
			if (/*!component.getId().startsWith(Component.AUTO_COMPONENT_PREFIX) &&*/ component.getMarkupId() != null && component.getMarkupId().equals(this.markupId)){
				this.component = component;
			}
			if (this.component == null){
				return IVisitor.CONTINUE_TRAVERSAL;
			}
			return IVisitor.STOP_TRAVERSAL;
		}
		
		/**
		 * Return component if it has been found or null otherwise
		 * @return component if it has been found or null otherwise
		 */
		public Component getComponent(){
			return this.component;
		}
		
	}
}

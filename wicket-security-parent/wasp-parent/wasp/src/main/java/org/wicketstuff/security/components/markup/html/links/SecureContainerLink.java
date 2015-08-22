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
package org.wicketstuff.security.components.markup.html.links;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.wicketstuff.security.actions.Enable;
import org.wicketstuff.security.actions.WaspAction;
import org.wicketstuff.security.checks.ISecurityCheck;
import org.wicketstuff.security.checks.LinkSecurityCheck;
import org.wicketstuff.security.components.ISecureComponent;
import org.wicketstuff.security.components.SecureComponentHelper;

/**
 * A secure link to handle panel replacements or any other type of {@link MarkupContainer} s. It is
 * also usable as a link to switch between 2 or more panels. Security is enforced on the replacing
 * class, not on the panel. This means that the panels do not need to be {@link ISecureComponent}s,
 * but are allowed to be if so desired. The link however will only show up if the user has the
 * {@link Enable} action for the class of the replacement panel. Please consult your implementation
 * on how to do that. This link is typically placed as a child on the parent of the panel it is
 * supposed to replace. Like so:<br/>
 * <code>
 * MarkupContainer parent=new WebMarkupContainer("parent);
 * parent.add(new FirstPanel("replaceMe",new Model("hello"));
 * parent.add(new SecureContainerLink("link",SecondPanel.class,parent,"replaceMe"){....});
 * </code>
 * 
 * @author marrink
 */
public abstract class SecureContainerLink<T> extends Link<T> implements ISecureComponent
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Class<? extends MarkupContainer> replacementClass;

	private MarkupContainer containerParent;

	private String containerId;

	/**
	 * Constructs a new replacement link.
	 * 
	 * @param id
	 *            id of the link
	 * @param replacementPanel
	 *            the class of the container replacing the component on the supplied parent
	 * @param parentOfReplaceablePanel
	 *            the parent component where the replacement needs to take place
	 * @param panelId
	 *            the id of the component to be replaced
	 */
	public SecureContainerLink(String id, Class<? extends MarkupContainer> replacementPanel,
		MarkupContainer parentOfReplaceablePanel, String panelId)
	{
		this(id, null, replacementPanel, parentOfReplaceablePanel, panelId);

	}

	/**
	 * Constructs a new replacement link.
	 * 
	 * @param id
	 *            id of the link
	 * @param object
	 *            model of the link
	 * @param replacementPanel
	 *            the class of the container replacing the component on the supplied parent
	 * @param parentOfReplaceablePanel
	 *            the parent component where the replacement needs to take place
	 * @param panelId
	 *            the id of the component to be replaced
	 */
	public SecureContainerLink(String id, IModel<T> object,
		Class<? extends MarkupContainer> replacementPanel,
		MarkupContainer parentOfReplaceablePanel, String panelId)
	{
		super(id, object);
		setReplacementClass(replacementPanel);
		if (parentOfReplaceablePanel == null)
			throw new WicketRuntimeException("Parent required for replacing components.");
		containerParent = parentOfReplaceablePanel;
		if (panelId == null)
			throw new WicketRuntimeException("Id required from component to be replaced.");
		containerId = panelId;
	}

	/**
	 * Performs the replacement, only if an actual replacement was constructed.
	 * 
	 * @see org.apache.wicket.markup.html.link.Link#onClick()
	 * @see #getReplacementFor(Component, String, Class)
	 * @throws WicketRuntimeException
	 *             if a problem occurs in replacing the container.
	 */
	@Override
	public final void onClick()
	{
		Component replaceMe = getComponentToBeReplaced();
		if (replaceMe == null)
			throw new WicketRuntimeException("unable to find child with id: " + containerId +
				" on parent: " + containerParent);
		Class<? extends MarkupContainer> myReplacementClass = getReplacementClass();
		MarkupContainer replacement = getReplacementFor(replaceMe, containerId, myReplacementClass);
		if (replacement == null)
			return; // do nothing
		if (!containerId.equals(replacement.getId()))
			throw new WicketRuntimeException("The replacement does not have the specified id: " +
				containerId + ", but id: " + replacement.getId());
		if (myReplacementClass.isAssignableFrom(replacement.getClass()))
			containerParent.replace(replacement);
		else
			throw new WicketRuntimeException("The replacement for " + containerId + " on " +
				containerParent + " is not assignable from " + myReplacementClass);

	}

	/**
	 * The component, usually a MarkupContainer or subclass, that will be replaced by the output
	 * from {@link #getReplacementFor(Component, String, Class)}.
	 * 
	 * @return the component or null if the id specified at the constructor is bogus.
	 */
	protected final Component getComponentToBeReplaced()
	{
		return containerParent.get(containerId);
	}

	/**
	 * Creates a replacement for a component. although the component to be replaced does not need to
	 * be a {@link MarkupContainer} it typically is. The replacement however does need to be a
	 * MarkupContainer, more specifically a (sub)class of replacementClass. Implementation may
	 * choose at this point to do the next replacement with a different class by using
	 * {@link #setReplacementClass(Class)} in order to create a switch like behavior. The intention
	 * of this method is thus to create the new panel, for example:<br/>
	 * <code>
	 * protected MarkupContainer getReplacementFor(Component current, String id,
	 * 		Class replacementClass)
	 * 	{
	 * 		//does a one time replace
	 * 		if(MyPanel.class.isAssignableFrom(replacementClass)
	 * 		{
	 * 			//if this link is not a child of current, you might want to hide it after the replace has taken place
	 * 			this.setVisible(false);
	 * 			return new MyPanel(id, current.getModel());
	 * 		}
	 * 	}
	 * </code> Or if this link should switch between panels and is situated somewhere higher in the
	 * component hierarchy as these panels it is supposed to switch you could do something like
	 * this: <br/>
	 * <code>
	 * protected MarkupContainer getReplacementFor(Component current, String id,
	 * 		Class replacementClass)
	 * 	{
	 * 		//continually switches between two panels
	 * 		
	 * 		//prepare the next replacement
	 * 		setReplacementClass(current.getClass();
	 * 		//do the switch
	 * 		if(MyPanel.class.isAssignableFrom(replacementClass)
	 * 		{
	 * 			return new MyPanel(id, current.getModel());
	 * 		}
	 * 		//other panel
	 * 		else 
	 * 		{
	 * 			return new MyOtherPanel(id, current.getModel());
	 * 		}
	 * 	}
	 * </code>
	 * 
	 * @param current
	 *            the component to be replaced
	 * @param id
	 *            the id of the new container
	 * @param replacementClass
	 *            the class of the replacement
	 * @return a new replacement or null if the original component is not to be replaced
	 * @see #setReplacementClass(Class)
	 */
	protected abstract MarkupContainer getReplacementFor(Component current, String id,
		Class<? extends MarkupContainer> replacementClass);

	/**
	 * Generates the securitycheck for this link. by default this is a {@link LinkSecurityCheck} but
	 * implementations may choose to override this. Note that the returned LinkSecurityCheck should
	 * not be placed in alternative rendering mode as this will completely change the intended
	 * behavior.
	 * 
	 * @return the securitycheck for this link or null if no security is to be enforced
	 */
	protected ISecurityCheck generateSecurityCheck()
	{
		return new LinkSecurityCheck(this, getReplacementClass());
	}

	/**
	 * 
	 * @see org.wicketstuff.security.components.ISecureComponent#getSecurityCheck()
	 */
	public ISecurityCheck getSecurityCheck()
	{
		return SecureComponentHelper.getSecurityCheck(this);
	}

	/**
	 * 
	 * @see org.wicketstuff.security.components.ISecureComponent#isActionAuthorized(java.lang.String)
	 */
	public boolean isActionAuthorized(String waspAction)
	{
		return SecureComponentHelper.isActionAuthorized(this, waspAction);
	}

	/**
	 * 
	 * @see org.wicketstuff.security.components.ISecureComponent#isActionAuthorized(WaspAction)
	 */
	public boolean isActionAuthorized(WaspAction action)
	{
		return SecureComponentHelper.isActionAuthorized(this, action);
	}

	/**
	 * 
	 * @see org.wicketstuff.security.components.ISecureComponent#isAuthenticated()
	 */
	public boolean isAuthenticated()
	{
		return SecureComponentHelper.isAuthenticated(this);
	}

	/**
	 * 
	 * @see org.wicketstuff.security.components.ISecureComponent#setSecurityCheck(org.wicketstuff.security.checks.ISecurityCheck)
	 */
	public void setSecurityCheck(ISecurityCheck check)
	{
		SecureComponentHelper.setSecurityCheck(this, check);
	}

	/**
	 * Gets replacementClass.
	 * 
	 * @return replacementClass
	 */
	protected final Class<? extends MarkupContainer> getReplacementClass()
	{
		return replacementClass;
	}

	/**
	 * Sets replacementClass. Note by changing the replacement class a new securitycheck is
	 * automatically created.
	 * 
	 * @param replacementClass
	 *            replacementClass
	 * @see #generateSecurityCheck()
	 * @throws WicketRuntimeException
	 *             if the class is null or not a {@link MarkupContainer}
	 */
	protected final void setReplacementClass(Class<? extends MarkupContainer> replacementClass)
	{
		if (replacementClass == null)
			throw new WicketRuntimeException("replacementClass cannot be null");
		this.replacementClass = replacementClass;
		setSecurityCheck(generateSecurityCheck());
	}
}

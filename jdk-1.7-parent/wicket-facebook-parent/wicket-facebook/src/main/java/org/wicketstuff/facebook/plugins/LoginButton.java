package org.wicketstuff.facebook.plugins;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.PropertyModel;
import org.wicketstuff.facebook.FacebookPermission;

/**
 * https://developers.facebook.com/docs/reference/plugins/login/
 * 
 * @author Till Freier
 * 
 */
public class LoginButton extends AbstractFacebookPlugin
{
	private class PermissionsModel extends AbstractReadOnlyModel<String>
	{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public String getObject()
		{
			final StringBuilder str = new StringBuilder();

			for (final FacebookPermission perm : permissions)
				str.append(perm.name().toLowerCase()).append(',');

			if (str.length() > 0)
				str.deleteCharAt(str.length() - 1);

			return str.toString();
		}

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int maxRows = 1;
	private List<FacebookPermission> permissions = Collections.emptyList();
	private boolean showFaces = false;

	/**
	 * 
	 * {@inheritDoc}
	 */
	public LoginButton(final String id)
	{
		super(id, "fb-login-button");

		add(new AttributeModifier("data-show-faces", new PropertyModel<Boolean>(this, "showFaces")));
		add(new AttributeModifier("data-max-rows", new PropertyModel<Boolean>(this, "maxRows")));
		add(new AttributeModifier("data-scope", new PermissionsModel()));
	}

	/**
	 * By default the Login button prompts users for their public information. If your application
	 * needs to access other parts of the user's profile that may be private, your application can
	 * request extended permissions
	 * 
	 * @param id
	 *            wicket-id
	 * @param permissions
	 */
	public LoginButton(final String id, final FacebookPermission... permissions)
	{
		this(id);
		this.permissions = Arrays.asList(permissions);
	}

	/**
	 * @see #setMaxRows(int)
	 * @return
	 */
	public int getMaxRows()
	{
		return maxRows;
	}

	/**
	 * @see FacebookPermission
	 * 
	 */
	public List<FacebookPermission> getPermissions()
	{
		return permissions;
	}

	public boolean isShowFaces()
	{
		return showFaces;
	}

	/**
	 * the maximum number of rows of profile pictures to display. Default value: 1.
	 * 
	 * @param maxRows
	 */
	public void setMaxRows(final int maxRows)
	{
		this.maxRows = maxRows;
	}

	/**
	 * @see FacebookPermission
	 * @param permissions
	 */
	public void setPermissions(final List<FacebookPermission> permissions)
	{
		this.permissions = permissions;
	}

	/**
	 * 
	 * @param showFaces
	 *            whether to show faces underneath the Login button.
	 */
	public void setShowFaces(final boolean showFaces)
	{
		this.showFaces = showFaces;
	}
}

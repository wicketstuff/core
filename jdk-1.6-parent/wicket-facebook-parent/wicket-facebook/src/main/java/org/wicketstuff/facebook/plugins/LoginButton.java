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

			for (FacebookPermission perm : permissions)
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

	public LoginButton(String id)
	{
		super(id, "fb-login-button");

		add(new AttributeModifier("data-show-faces", new PropertyModel<Boolean>(this, "showFaces")));
		add(new AttributeModifier("data-max-rows", new PropertyModel<Boolean>(this, "maxRows")));
		add(new AttributeModifier("data-perms", new PermissionsModel()));
	}


	public LoginButton(String id, FacebookPermission... permissions)
	{
		this(id);
		this.permissions = Arrays.asList(permissions);
	}


	public int getMaxRows()
	{
		return maxRows;
	}

	public List<FacebookPermission> getPermissions()
	{
		return permissions;
	}

	public boolean isShowFaces()
	{
		return showFaces;
	}

	public void setMaxRows(int maxRows)
	{
		this.maxRows = maxRows;
	}

	public void setPermissions(List<FacebookPermission> permissions)
	{
		this.permissions = permissions;
	}

	public void setShowFaces(boolean showFaces)
	{
		this.showFaces = showFaces;
	}
}

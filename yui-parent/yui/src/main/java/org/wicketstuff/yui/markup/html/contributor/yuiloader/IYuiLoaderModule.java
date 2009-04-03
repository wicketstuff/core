package org.wicketstuff.yui.markup.html.contributor.yuiloader;

import java.io.Serializable;

public interface IYuiLoaderModule extends Serializable
{

	public String onSuccessJS();

	public String getModuleJS();

	public String getName();

}

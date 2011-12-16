package org.wicketstuff.yui.markup.html.image;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.image.ContextImage;
import org.apache.wicket.model.IModel;

/**
 * and Image with either context relative or external "src"  
 * 
 * @author josh
 *
 */
public class URIImage extends ContextImage
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 * @param id
	 */
	public URIImage(String id, String contextRelativePath)
	{
		super(id,contextRelativePath);
	}

	/**
	 * Construct.
	 * 
	 * @param id
	 * @param model
	 */
	
	 public URIImage(String id, IModel model)
	 {
		super(id, model);
	 }

	 /**
	  * 
	  */
	 protected void onComponentTag(final ComponentTag tag) 
	 {
     	checkComponentTag(tag, "img");
     	super.onComponentTag(tag);
     	String url = getDefaultModelObjectAsString();
     	if (url.startsWith("http")) 
     	{
     		tag.put("src", url);
     	} 
     	else 
     	{
     		tag.put("src", getRequest().getRelativePathPrefixToContextRoot() + getDefaultModelObjectAsString());
     	}
	 }
}

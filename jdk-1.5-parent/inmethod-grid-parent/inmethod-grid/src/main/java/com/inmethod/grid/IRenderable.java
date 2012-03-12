package com.inmethod.grid;

import org.apache.wicket.model.IModel;
import org.apache.wicket.request.Response;

/**
 * Lightweight columns return an implementation of this interface to render cell output.
 * 
 * @param <T>
 *            row/item model object type
 * 
 * @see IGridColumn#isLightWeight(IModel)
 * @author Matej Knopp
 */
public interface IRenderable<T>
{

	/**
	 * Renders the output for given cell model. The implementation must take care of proper escaping
	 * (e.g. translating &lt; to &amp;lt;, etc.) where appropriate.
	 * 
	 * @param rowModel
	 *            model for given row
	 * @param response
	 */
	public void render(IModel<T> rowModel, Response response);

}

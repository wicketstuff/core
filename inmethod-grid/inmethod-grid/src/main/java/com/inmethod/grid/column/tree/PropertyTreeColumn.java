package com.inmethod.grid.column.tree;

import javax.swing.tree.TreeNode;

import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortState;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

/**
 * Simple tree column that displays a property of {@link TreeNode} instance specified by the
 * property expression.
 * 
 * @see AbstractTreeColumn
 * @author Matej Knopp
 */
public class PropertyTreeColumn extends AbstractTreeColumn {

	private static final long serialVersionUID = 1L;
	private final String propertyExpression;

	/**
	 * Constructor.
	 * 
	 * @param columnId
	 *            column identified (must be unique within the grid)
	 * @param headerModel
	 *            model for column header
	 * @param propertyExpression
	 *            property expression used to get the displayed value for row object
	 * @param sortProperty
	 *            optional string that will be returned by {@link ISortState} to indicate that the
	 *            column is being sorted
	 */
	public PropertyTreeColumn(String columnId, IModel headerModel, String propertyExpression, String sortProperty) {
		super(columnId, headerModel, sortProperty);
		this.propertyExpression = propertyExpression;
	}

	/**
	 * Constructor.
	 * 
	 * @param columnId
	 *            column identified (must be unique within the grid)
	 * @param headerModel
	 *            model for column header
	 * @param propertyExpression
	 *            property expression used to get the displayed value for row object
	 */
	public PropertyTreeColumn(String columnId, IModel headerModel, String propertyExpression) {
		this(columnId, headerModel, propertyExpression, null);
	}

	/**
	 * Constructor. The column id is omitted in this constructor, because the property expression is
	 * used as column id.
	 * 
	 * @param headerModel
	 *            model for column header
	 * @param propertyExpression
	 *            property expression used to get the displayed value for row object
	 * @param sortProperty
	 *            optional string that will be returned by {@link ISortState} to indicate that the
	 *            column is being sorted
	 */
	public PropertyTreeColumn(IModel headerModel, String propertyExpression, String sortProperty) {
		this(propertyExpression, headerModel, propertyExpression, sortProperty);
	}

	/**
	 * Constructor. The column id is omitted in this constructor, because the property expression is
	 * used as column id.
	 * 
	 * @param headerModel
	 *            model for column header
	 * @param propertyExpression
	 *            property expression used to get the displayed value for row object
	 */
	public PropertyTreeColumn(IModel headerModel, String propertyExpression) {
		this(propertyExpression, headerModel, propertyExpression);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Component newNodeComponent(String id, IModel model) {
		return new Label(id, new PropertyModel(model, getPropertyExpression()));
	}

	/**
	 * Returns the property expression.
	 * 
	 * @return property expression.
	 */
	protected String getPropertyExpression() {
		return propertyExpression;
	}

}

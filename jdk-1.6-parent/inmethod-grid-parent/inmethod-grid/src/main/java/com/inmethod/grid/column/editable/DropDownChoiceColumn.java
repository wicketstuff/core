package com.inmethod.grid.column.editable;


import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortState;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.util.WildcardListModel;

/**
 * Property column that uses a {@link DropDownChoicePanel} as cell component
 * when the item is selected.
 *
 * @author Tom Burton
 */
public class DropDownChoiceColumn<M, I, T, S> extends EditablePropertyColumn<M, I, T, S>
{
	private static final long serialVersionUID = 1L;

  /** following {@Link AbstractChoice}'s example
   *  and using {@link WildcardListModel} as default model*/
  private IModel<? extends List<? extends T>> choicesModel;
  private IChoiceRenderer<T> choiceRenderer = null;

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
	public DropDownChoiceColumn(String columnId, IModel<String> headerModel, String propertyExpression, S sortProperty)
  {
		super(columnId, headerModel, propertyExpression, sortProperty);
	}

	/**
	 * Constructor.
	 *
	 * @param columnId
	 *            column identified (must be unique within the grid)
	 * @param headerModel
	 *            model for column header
	 * @param propertyExpression
	 *            property expression used to get the displayed value for row
   *            object
	 */
	public DropDownChoiceColumn(String columnId, IModel<String> headerModel, String propertyExpression)
  {
		super(columnId, headerModel, propertyExpression);
	}

	/**
	 * Constructor. The column id is omitted in this constructor,
   * because the property expression is used as column id.
	 *
	 * @param headerModel
	 *            model for column header
	 * @param propertyExpression
	 *            property expression used to get the displayed value for row object
	 * @param sortProperty
	 *            optional string that will be returned by {@link ISortState}
   *            to indicate that the
	 *            column is being sorted
	 */
	public DropDownChoiceColumn(IModel<String> headerModel, String propertyExpression, S sortProperty)
  {
		super(headerModel, propertyExpression, sortProperty);
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
	public DropDownChoiceColumn(IModel<String> headerModel, String propertyExpression)
  {
		super(headerModel, propertyExpression);
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
	 * @param sortProperty
	 *            optional string that will be returned by {@link ISortState} to indicate that the
	 *            column is being sorted
   * @param choices
   *           The collection of choices in the drop down when the cell becomes editable
	 */
	public DropDownChoiceColumn(String columnId, IModel<String> headerModel, String propertyExpression, S sortProperty,
		IModel<? extends List<? extends T>> choices)
	{
		super(columnId, headerModel, propertyExpression, sortProperty);
		choicesModel = choices;
	}

	/**
	 * Constructor.
	 *
	 * @param columnId
	 *            column identified (must be unique within the grid)
	 * @param headerModel
	 *            model for column header
	 * @param propertyExpression
	 *            property expression used to get the displayed value for row
   *            object
   * @param choices
   *           The collection of choices in the drop down when the cell becomes editable
	 */
	public DropDownChoiceColumn(String columnId, IModel<String> headerModel,
                              String propertyExpression,
                              IModel<? extends List<? extends T>> choices)
  {
		super(columnId, headerModel, propertyExpression);
    choicesModel = choices;
	}

	/**
	 * Constructor. The column id is omitted in this constructor,
   * because the property expression is used as column id.
	 *
	 * @param headerModel
	 *            model for column header
	 * @param propertyExpression
	 *            property expression used to get the displayed value for row object
	 * @param sortProperty
	 *            optional string that will be returned by {@link ISortState}
   *            to indicate that the
	 *            column is being sorted
   * @param choices
   *           The collection of choices in the drop down when the cell becomes editable
	 */
	public DropDownChoiceColumn(IModel<String> headerModel, String propertyExpression, S sortProperty,
		IModel<? extends List<? extends T>> choices)
	{
		super(headerModel, propertyExpression, sortProperty);
		choicesModel = choices;
	}

	/**
	 * Constructor. The column id is omitted in this constructor, because the property expression is
	 * used as column id.
	 *
	 * @param headerModel
	 *            model for column header
	 * @param propertyExpression
	 *            property expression used to get the displayed value for row object
   * @param choices
   *           The collection of choices in the drop down when the cell becomes editable
	 */
	public DropDownChoiceColumn(IModel<String> headerModel, String propertyExpression,
                              IModel<? extends List<? extends T>> choices)
  {
		super(headerModel, propertyExpression);
    choicesModel = choices;
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
	 * @param sortProperty
	 *            optional string that will be returned by {@link ISortState} to indicate that the
	 *            column is being sorted
   * @param choices
   *           The collection of choices in the drop down when the cell becomes editable
	 * @param renderer
	 *            The rendering engine
	 */
	public DropDownChoiceColumn(String columnId, IModel<String> headerModel,
                              String propertyExpression, S sortProperty,
                              IModel<? extends List<? extends T>> choices,
                              IChoiceRenderer<T> renderer)
  {
		super(columnId, headerModel, propertyExpression, sortProperty);
    choicesModel = choices;
    choiceRenderer = renderer;
	}

	/**
	 * Constructor.
	 *
	 * @param columnId
	 *            column identified (must be unique within the grid)
	 * @param headerModel
	 *            model for column header
	 * @param propertyExpression
	 *            property expression used to get the displayed value for row
   *            object
   * @param choices
   *           The collection of choices in the drop down when the cell becomes editable
	 * @param renderer
	 *            The rendering engine
	 */
	public DropDownChoiceColumn(String columnId, IModel<String> headerModel,
                              String propertyExpression,
                              IModel<? extends List<? extends T>> choices,
                              IChoiceRenderer<T> renderer)
  {
		super(columnId, headerModel, propertyExpression);
    choicesModel = choices;
    choiceRenderer = renderer;
	}

	/**
	 * Constructor. The column id is omitted in this constructor,
   * because the property expression is used as column id.
	 *
	 * @param headerModel
	 *            model for column header
	 * @param propertyExpression
	 *            property expression used to get the displayed value for row object
	 * @param sortProperty
	 *            optional string that will be returned by {@link ISortState}
   *            to indicate that the
	 *            column is being sorted
   * @param choices
   *           The collection of choices in the drop down when the cell becomes editable
	 * @param renderer
	 *            The rendering engine
	 */
	public DropDownChoiceColumn(IModel<String> headerModel, String propertyExpression,
                              S sortProperty,
                              IModel<? extends List<? extends T>> choices,
                              IChoiceRenderer<T> renderer)
  {
		super(headerModel, propertyExpression, sortProperty);
    choicesModel = choices;
    choiceRenderer = renderer;
	}

	/**
	 * Constructor. The column id is omitted in this constructor, because the property expression is
	 * used as column id.
	 *
	 * @param headerModel
	 *            model for column header
	 * @param propertyExpression
	 *            property expression used to get the displayed value for row object
   * @param choices
   *           The collection of choices in the drop down when the cell becomes editable
	 * @param renderer
	 *            The rendering engine
	 */
	public DropDownChoiceColumn(IModel<String> headerModel, String propertyExpression,
                              IModel<? extends List<? extends T>> choices,
                              IChoiceRenderer<T> renderer)
  {
		super(headerModel, propertyExpression);
    choicesModel = choices;
    choiceRenderer = renderer;
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
     * @param sortProperty
     *            optional string that will be returned by {@link ISortState} to indicate that the
     *            column is being sorted
   * @param choices
   *           The collection of choices in the drop down when the cell becomes editable
     */
  public DropDownChoiceColumn(String columnId, IModel<String> headerModel,
                              String propertyExpression, S sortProperty,
                              List<T> choices)
  {
    super(columnId, headerModel, propertyExpression, sortProperty);
    choicesModel = new WildcardListModel(choices);
  }

  /**
   * Constructor.
   *
   * @param columnId
   *            column identified (must be unique within the grid)
   * @param headerModel
   *            model for column header
   * @param propertyExpression
   *            property expression used to get the displayed value for row
   *            object
   * @param choices
   *           The collection of choices in the drop down when the cell becomes editable
   */
  public DropDownChoiceColumn(String columnId, IModel<String> headerModel,
                              String propertyExpression, List<T> choices)
  {
    super(columnId, headerModel, propertyExpression);
    choicesModel = new WildcardListModel(choices);
  }

  /**
   * Constructor. The column id is omitted in this constructor,
   * because the property expression is used as column id.
   *
   * @param headerModel
   *            model for column header
   * @param propertyExpression
   *            property expression used to get the displayed value for row object
   * @param sortProperty
   *            optional string that will be returned by {@link ISortState}
   *            to indicate that the
   *            column is being sorted
   * @param choices
   *           The collection of choices in the drop down when the cell becomes editable
   */
  public DropDownChoiceColumn(IModel<String> headerModel,
                              String propertyExpression, S sortProperty,
                              List<T> choices)
  {
    super(headerModel, propertyExpression, sortProperty);
    choicesModel = new WildcardListModel(choices);
  }

  /**
   * Constructor. The column id is omitted in this constructor, because the property expression is
   * used as column id.
   *
   * @param headerModel
   *            model for column header
   * @param propertyExpression
   *            property expression used to get the displayed value for row object
   * @param choices
   *           The collection of choices in the drop down when the cell becomes editable
   */
  public DropDownChoiceColumn(IModel<String> headerModel, String propertyExpression,
                              List<T> choices)
  {
    super(headerModel, propertyExpression);
    choicesModel = new WildcardListModel(choices);
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
   * @param sortProperty
   *            optional string that will be returned by {@link ISortState} to indicate that the
   *            column is being sorted
   * @param choices
   *           The collection of choices in the drop down when the cell becomes editable
   * @param renderer
   *           The renderer to use for displaying the data
   */
  public DropDownChoiceColumn(String columnId, IModel<String> headerModel,
                              String propertyExpression, S sortProperty,
                              List<T> choices, IChoiceRenderer<T> renderer)
  {
    super(columnId, headerModel, propertyExpression, sortProperty);
    choicesModel = new WildcardListModel(choices);
    choiceRenderer = renderer;
  }

  /**
   * Constructor.
   *
   * @param columnId
   *            column identified (must be unique within the grid)
   * @param headerModel
   *            model for column header
   * @param propertyExpression
   *            property expression used to get the displayed value for row
   *            object
   * @param choices
   *           The collection of choices in the drop down when the cell becomes editable
	 * @param renderer
	 *            The rendering engine
   */
  public DropDownChoiceColumn(String columnId, IModel<String> headerModel,
                              String propertyExpression,
                              List<T> choices,
                              IChoiceRenderer<T> renderer)
  {
    super(columnId, headerModel, propertyExpression);
    choicesModel = new WildcardListModel(choices);
    choiceRenderer = renderer;
  }

  /**
   * Constructor. The column id is omitted in this constructor,
   * because the property expression is used as column id.
   *
   * @param headerModel
   *            model for column header
   * @param propertyExpression
   *            property expression used to get the displayed value for row object
   * @param sortProperty
   *            optional string that will be returned by {@link ISortState}
   *            to indicate that the
   *            column is being sorted
   * @param choices
   *           The collection of choices in the drop down when the cell becomes editable
   * @param renderer
   *           The renderer to use for displaying the data
   */
  public DropDownChoiceColumn(IModel<String> headerModel, String propertyExpression,
                              S sortProperty,
                              List<T> choices, IChoiceRenderer<T> renderer)
  {
    super(headerModel, propertyExpression, sortProperty);
    choicesModel = new WildcardListModel(choices);
    choiceRenderer = renderer;
  }

  /**
   * Constructor. The column id is omitted in this constructor,
   * because the property expression is used as column id.
   *
   * @param headerModel
   *            model for column header
   * @param propertyExpression
   *            property expression used to get the displayed value for row object
   * @param choices
   *           The collection of choices in the drop down when the cell becomes editable
	 * @param renderer
	 *            The rendering engine
   */
  public DropDownChoiceColumn(IModel<String> headerModel, String propertyExpression,
                              List<T> choices, IChoiceRenderer<T> renderer)
  {
    super(headerModel, propertyExpression);
    choicesModel = new WildcardListModel(choices);
    choiceRenderer = renderer;
  }

  /** {@inheritDoc} **/
  @Override
  protected EditableCellPanel<M, I, T, S> newCellPanel(String componentId, IModel<I> rowModel,
                                           IModel<T> cellModel)
  {
    return new DropDownChoicePanel(componentId, cellModel, rowModel, this,
                                   choicesModel, choiceRenderer);
  }

  /** {@inheritDoc} */
  @Override
  protected <C> CharSequence convertToString(C obj)
  {
    if (null != obj && null != choiceRenderer)
    {
      return getConverter((Class<C>)obj.getClass())
                .convertToString((C)choiceRenderer.getDisplayValue((T)obj),
                                 getLocale());
    }
    else if ( null != obj) { return super.convertToString(obj); }
    else { return ""; }
  }

}

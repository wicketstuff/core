package org.wicketstuff.lambda.table;

import java.util.function.Function;

import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.wicketstuff.lambda.SerializableFunction;
import org.wicketstuff.lambda.model.LambdaModel;

/**
 * {@link AbstractColumn} where the {@link IModel} of a cell is provided by
 * applying a {@code dataFunction} to the {@link IModel} of the row.
 *
 * @param <T>
 *            - the type of the row
 * @param <S>
 *            - the type of the sort property
 * @param <R>
 *            - type type of the cell
 */
public class FunctionColumn<T, S, R> extends AbstractColumn<T, S> {

	/*
	 * Function that generates the model of the cell from the model of the row.
	 */
	private SerializableFunction<T, R> dataFunction;

	/**
	 * @param displayModel
	 *            model used to generate header text
	 * @param dataFunction
	 *            function that generates the model of the cell from the model
	 *            of the row
	 */
	public FunctionColumn(IModel<String> displayModel, Function<T, R> dataFunction) {
		this(displayModel, dataFunction, null);
	}

	/**
	 * @param displayModel
	 *            model used to generate header text
	 * @param dataFunction
	 *            function that generates the model of the cell from the model
	 *            of the row
	 * @param sortProperty
	 *            sort property this column represents
	 */
	public FunctionColumn(IModel<String> displayModel, Function<T, R> dataFunction, S sortProperty) {
		super(displayModel, sortProperty);
		this.dataFunction = (SerializableFunction<T, R>) dataFunction;
	}

	@Override
	public void populateItem(Item<ICellPopulator<T>> cellItem, String componentId, IModel<T> rowModel) {
		cellItem.add(new Label(componentId, getDataModel(rowModel)));
	}

	/**
	 * Returns the model for the cell from the model of the row.
	 * 
	 * @param rowModel
	 *            {@link IModel} of the row
	 * @return the model of the cell
	 */
	protected IModel<R> getDataModel(IModel<T> rowModel) {
		return new LambdaModel<>(rowModel, dataFunction);
	}

}

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
package org.wicketstuff.lazymodel;

import static org.wicketstuff.lazymodel.LazyModel.model;

import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.export.IExportableColumn;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;

/**
 * A {@link DataTable} column for lazy evaluations.
 * 
 * <pre>
 * new LazyColumn&lt;A, Void, B&gt;(header, from(A.class).getB());
 * </pre>
 * 
 * @param <T>
 *            row object type
 * @param <S>
 *            sort property type
 * @param <R>
 *            cell object type
 * 
 * @author svenmeier
 */
public class LazyColumn<T, S, R> extends AbstractColumn<T, S> implements
		IExportableColumn<T, S> {

	private static final long serialVersionUID = 1L;
	
	private final LazyModel<R> cellModel;

	/**
	 * Creates a new non-sortable column.
	 * 
	 * @param displayModel
	 *            model for the header
	 * @param evaluationResult
	 *            result of an evaluation
	 * 
	 * @see LazyModel#from(Class)
	 */
	public LazyColumn(IModel<String> displayModel, R evaluationResult) {
		this(displayModel, evaluationResult, null);
	}

	/**
	 * Creates a new non-sortable column.
	 * 
	 * @param displayModel
	 *            model for the header
	 * @param cellModel
	 *            lazy model for the cells
	 */
	public LazyColumn(IModel<String> displayModel, LazyModel<R> cellModel) {
		this(displayModel, cellModel, null);
	}

	/**
	 * Creates a new column. If the {@code sortProperty} is not {@code null},
	 * then the column will be sortable.
	 * 
	 * @param displayModel
	 *            model for the header
	 * @param evaluationResult
	 *            result of an evaluation
	 * @param sortProperty
	 *            sort property to use when sorting by this column
	 * 
	 * @see LazyModel#from(Class)
	 */
	public LazyColumn(IModel<String> displayModel, R evaluationResult,
			S sortProperty) {
		this(displayModel, model(evaluationResult), sortProperty);
	}

	/**
	 * Creates a new column. If the {@code sortProperty} is not {@code null},
	 * then the column will be sortable.
	 * 
	 * @param displayModel
	 *            model for the header
	 * @param cellModel
	 *            lazy model for the cells 
	 * @param sortProperty
	 *            sort property to use when sorting by this column
	 */
	public LazyColumn(IModel<String> displayModel, LazyModel<R> cellModel,
			S sortProperty) {
		super(displayModel, sortProperty);
		this.cellModel = cellModel;
	}

	@Override
	public void detach() {
		super.detach();

		this.cellModel.detach();
	}

	/**
	 * Implementation of populateItem which adds a label to the cell whose model
	 * is the provided evaluation against rowModelObject.
	 * 
	 * @param item
	 *            the row item
	 * @param componentId
	 *            id for component
	 * @param rowModel
	 *            the model of the row
	 */
	@Override
	public void populateItem(final Item<ICellPopulator<T>> item,
			final String componentId, final IModel<T> rowModel) {
		item.add(new Label(componentId, getDataModel(rowModel)));
	}

	/**
	 * Factory method for generating a model that will generated the cell value.
	 * 
	 * @param rowModel
	 *            the model of the row
	 * @return cell model
	 */
	@Override
	public IModel<R> getDataModel(IModel<T> rowModel) {
		return cellModel.bind(rowModel);
	}
}

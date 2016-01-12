package org.wicketstuff.lambda.model;

import org.apache.wicket.model.LoadableDetachableModel;
import org.wicketstuff.lambda.SerializableSupplier;

/**
 * {@link LoadableDetachableModel} that gets its value from a {@link SerializableSupplier}.
 *
 * @param <T>
 *            - type of the model object
 */
public class SupplierModel<T> extends LoadableDetachableModel<T> {

	/*
	 * Supplier that supplies the value of the model.
	 */
	private SerializableSupplier<T> supplier;

	/**
	 * Constructor.
	 * @param supplier that supplies the value of the model
	 */
	public SupplierModel(SerializableSupplier<T> supplier) {
		this.supplier = supplier;
	}

	@Override
	protected T load() {
		return supplier.get();
	}

}

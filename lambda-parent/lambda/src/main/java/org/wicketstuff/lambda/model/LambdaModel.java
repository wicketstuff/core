package org.wicketstuff.lambda.model;

import java.util.Objects;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.wicketstuff.lambda.SerializableBiConsumer;
import org.wicketstuff.lambda.SerializableBiFunction;
import org.wicketstuff.lambda.SerializableFunction;

/**
 * {@link LoadableDetachableModel} that wraps another {@link IModel} and applies
 * a {@link SerializableFunction} to the wrapped model to get the value for the
 * {@link #load()} function.
 * <p/>
 * An optional {@link SerializableBiConsumer} can be provided that will be
 * called with the object of the wrapped model and the new object value when the
 * {@link #setObject()} method is called.
 * 
 * @param <T>
 *            - type of the wrapped {@link IModel}
 * @param <R>
 *            - type of the {@link LambdaModel}
 */
public class LambdaModel<T, R> extends LoadableDetachableModel<R> {
	
	private IModel<T> wrappedModel;
	private SerializableFunction<T, R> loadHandler;
	private SerializableBiConsumer<T, R> setObjectHandler;

	/**
	 * @param wrappedModel
	 *            {@link IModel} that wraps the value which will be applied to
	 *            the {@code loadHandler} {@link SerializableFunction}
	 * @param loadHandler
	 *            {@link SerializableFunction} that is invoked to provide the
	 *            value for the {@code #load()} method
	 */
	public LambdaModel(IModel<T> wrappedModel, SerializableFunction<T, R> loadHandler) {
		this(wrappedModel, loadHandler, (t, r) -> { throw new UnsupportedOperationException("No setter specified"); });
	}

	/**
	 * @param wrappedModel
	 *            {@link IModel} that wraps the value which will be applied to
	 *            the {@code loadHandler} {@link SerializableFunction}
	 * @param loadHandler
	 *            {@link SerializableFunction} that is invoked to provide the
	 *            value for the {@code #load()} method
	 * @param setObjectHandler
	 *            {@link SerializableBiFunction} that is invoked when the
	 *            {@code #setObject()} method is called
	 */
	public LambdaModel(IModel<T> wrappedModel, SerializableFunction<T, R> loaderFunction,
			SerializableBiConsumer<T, R> setObjectHandler) {
		super();
		Objects.requireNonNull(wrappedModel);
		Objects.requireNonNull(loaderFunction);
		Objects.requireNonNull(setObjectHandler);
		this.wrappedModel = wrappedModel;
		this.loadHandler = loaderFunction;
		this.setObjectHandler = setObjectHandler;
	}

	@Override
	protected R load() {
		return loadHandler.apply(wrappedModel.getObject());
	}

	@Override
	public void setObject(R r) {
		super.setObject(r);
		setObjectHandler.accept(wrappedModel.getObject(), r);
	}

	@Override
	protected void onDetach() {
		super.onDetach();
		wrappedModel.detach();
	}

}

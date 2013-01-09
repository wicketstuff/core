package org.wicketstuff.minis.model;

import java.io.Serializable;
import org.apache.wicket.model.LoadableDetachableModel;

/**
 * A {@link LoadableDetachableModel} that retains a reference to the loadable object. This is useful
 * for storing database primary keys. This model is capable of modeling {@code null}.
 * @author Jesse Long
 * @param <E> The type of the object that is managed by this model.
 * @param <K> The type of the reference that uniquely references the object. This should be small,
 * serializable type, because it is not detached from the model. Integers, Longs, Strings etc.
 */
public abstract class ReferenceLoadableDetachableModel<E, K extends Serializable>
	extends LoadableDetachableModel<E>
{
    /**
     * The reference to the object. This is used to retrieve the object from a database for instance.
     */
    private K reference;

    public ReferenceLoadableDetachableModel(E object)
    {
	setObject(object);
    }

    /**
     * Loads the model object using the provided reference. This method is not for {@code null} objects.
     * @param reference The reference to the model object.
     * @return The model object.
     * @see #load() 
     */
    protected abstract E load(K reference);

    /**
     * Returns a reference to the object being modeled. This reference is later passed to the {@link #load(java.io.Serializable)}
     * method to load the object after {@link #detach()} has been called.
     * @param object The object from which to obtain a reference.
     * @return a reference to the object being modeled.
     */
    protected abstract K reference(E object);

    /**
     * Loads and returns the modeled object. This method is once after each {@link #detach()} call.
     * <p>
     * If the reference is {@code null}, then {@code null} is returned. Otherwise, {@link #load(java.io.Serializable)} is called
     * passing it the reference previously returned by {@link #reference(java.lang.Object)}.
     * @return the modeled object, or {@code null} if the reference is {@code null}
     */
    @Override
    protected final E load()
    {
	if (reference == null){
	    return null;
	}else{
	    return load(reference);
	}
    }

    /**
     * Sets the object being managed. This method calls the {@link #reference(java.lang.Object) } method to get
     * a reference to the object so it can be loaded again with the {@link #load(java.io.Serializable) } after
     * {@link #detach() } is called on this object. If the object is {@code null}, then {@link #reference(java.lang.Object)}
     * is not called - a {@code null} reference is used instead.
     * @param object  The object to model.
     */
    @Override
    public final void setObject(E object)
    {
	super.setObject(object);
	if (object == null){
	    reference = null;
	}else{
	    reference = reference(object);
	}
    }
}

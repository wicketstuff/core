package org.wicketstuff.objectautocomplete;

import org.apache.wicket.model.IModel;
import org.apache.wicket.ajax.AjaxRequestTarget;

import java.io.Serializable;

/**
 * Listener interface for interested listeners which want to be notified on selection changes
 *
 * Parameter <I> is the id of the object updated
 * @author roland
 * @since Sep 29, 2008
 */
public interface ObjectAutoCompleteSelectionChangeListener<I /* id */> extends Serializable {
    /**
     * Method called on model change (whether it is inside or outside a form)
     *
     * @param pTarget target used during update
     * @param pModel updated model
     */
    void selectionChanged(AjaxRequestTarget pTarget, IModel<I> pModel);
}

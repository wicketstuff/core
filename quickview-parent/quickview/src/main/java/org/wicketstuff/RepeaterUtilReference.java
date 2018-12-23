package org.wicketstuff;

import org.apache.wicket.request.resource.PackageResourceReference;

/**
 * packageresourcereference to repeater.js
 *
 * @author Vineet Semwal
 */
public class RepeaterUtilReference extends PackageResourceReference {
    private static RepeaterUtilReference instance = new RepeaterUtilReference();

    public static RepeaterUtilReference get() {
        return instance;
    }

    public RepeaterUtilReference() {
        super(QuickView.class, "repeater.js");
    }
}

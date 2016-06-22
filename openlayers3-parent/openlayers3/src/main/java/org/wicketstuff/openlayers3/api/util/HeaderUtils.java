package org.wicketstuff.openlayers3.api.util;

import java.io.IOException;
import java.util.Map;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.util.template.PackageTextTemplate;

/**
 * Header-related utility methods.
 */
public final class HeaderUtils {

    /**
     * Renders the template, filled with the {@code params}, as an
     * {@link OnDomReadyHeaderItem}.
     *
     * @param response
     *            {@link IHeaderResponse} to render the script into
     * @param clazz
     *            Class the template file is relative to
     * @param fileName
     *            Name of the template file
     * @param params
     *            {@link Map} of the parameters to use to fill the template
     */
    public static void renderOnDomReady(IHeaderResponse response, Class<?> clazz, String fileName,
            Map<String, ?> params) {
        try (PackageTextTemplate template = new PackageTextTemplate(clazz, fileName);) {
            response.render(OnDomReadyHeaderItem.forScript(template.asString(params)));
        } catch (IOException e) {
            throw new WicketRuntimeException(e);
        }
    }

    private HeaderUtils() {

    }

}

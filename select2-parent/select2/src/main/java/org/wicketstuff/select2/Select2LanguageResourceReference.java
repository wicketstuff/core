package org.wicketstuff.select2;

import org.apache.wicket.request.resource.JavaScriptResourceReference;

/**
 * {@link JavaScriptResourceReference} for a select2 i18n file,
 * please see i18n resources folder for supported languages.
 *
 * @author Tom Götz (tom@decoded.de)
 */
class Select2LanguageResourceReference extends JavaScriptResourceReference {

    /**
     * @param language code to load
     */
    Select2LanguageResourceReference(String language) {
        super(Select2LanguageResourceReference.class, String.format("res/js/i18n/%s.js", language));
    }

}

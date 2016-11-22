package org.wicketstuff.select2;

import org.apache.wicket.request.resource.JavaScriptResourceReference;

/**
 * {@link JavaScriptResourceReference} for a select2 i18n file,
 * please see i18n resources folder for supported languages.
 *
 * @author Tom Götz (tom@decoded.de)
 */
class Select2LanguageResourceReference extends JavaScriptResourceReference {
	private static final long serialVersionUID = 1L;
	private static final String resourceName = "res/js/i18n/%s.js";
    private static final String defaultLanguage = "en";

    /**
     * @param language i18n file to load (e.g. "en", "de", "fr" ...)
     */
    Select2LanguageResourceReference(String language) {
        super(Select2LanguageResourceReference.class, getResourceName(language));
    }

    /**
     * Returns the resource name of the i18n file, uses a fallback to defaultLanguage
     * if requested resource file does not exist
     *
     * @param language i18n file to load
     * @return resource name
     */
    private static String getResourceName(String language) {
        try {
            String name = String.format(resourceName, language);
            if (Select2LanguageResourceReference.class.getResource(name) != null) {
                return name;
            }
        } catch (Exception ignore) {
            // noop
        }
        return String.format(resourceName, defaultLanguage);
    }
}

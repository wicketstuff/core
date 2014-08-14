package org.wicketstuff.openlayers3.api.util;

/**
 * Provides an enumeration of valid CORS (Cross Origin Resource Sharing) policy values.
 */
public enum CorsPolicy {

    ANONYMOUS("anonymous"), USE_CREDENTIALS("use-credentials");

    String value;

    CorsPolicy(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}

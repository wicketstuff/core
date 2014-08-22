package org.wicketstuff.openlayers3.api.util;

/**
 * Provides an object that will convert the items in the list to a delimited String.
 */
public class Joiner {

    /**
     * Returns a String containing the objects delimited by the provided separator.
     *
     * @param separator The separator used to delimit items
     * @param objects The objects in the delimited list
     * @return String of delimited objects
     */
    public static String join(String separator, Object... objects) {

        StringBuilder builder = new StringBuilder();

        for(Object object : objects) {

            if(builder.length() > 0) {
                builder.append(separator);
            }

            builder.append(object.toString());
        }

        return builder.toString();
    }
}

package org.wicketstuff.openlayers3.api.geometry;

/**
 * Provides an base class used for vector geometriee
 */
public class Geometry {

    /**
     * The coordinate layout for geometries, indicating whether a 3rd or 4th z ('Z') or measure ('M') coordinate is
     * available.
     */
    public enum Layout {
        XY("XY"), XYZ("XYZ"), XYZM("XYZM");

        String value;

        Layout(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }
}

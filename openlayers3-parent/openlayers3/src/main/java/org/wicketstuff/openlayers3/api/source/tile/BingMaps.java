package org.wicketstuff.openlayers3.api.source.tile;

/**
 * Provides an object that models a Bing Maps source of map data.
 */
public class BingMaps extends TileSource {

    /**
     * Default culture for new instances.
     */
    private final static Culture DEFAULT_CULTURE = Culture.ENGLISH_US;

    /**
     * The culture for this instance.
     */
    private Culture culture;

    /**
     * The Bing Maps API key for this instance.
     */
    private String key;

    /**
     * The imagery set for this instance.
     */
    private ImagerySet imagerySet;

    /**
     * Creates a new instance with the default culture.
     *
     * @param key
     *         Bing Maps API key
     * @param imagerySet
     *         Imagery set type to display
     */
    public BingMaps(String key, ImagerySet imagerySet) {
        this(DEFAULT_CULTURE, key, imagerySet);
    }

    /**
     * Creates a new instance.
     *
     * @param culture
     *         Culture, used for displaying labels, etc.
     * @param key
     *         The Bing Maps API key
     * @param imagerySet
     *         Imagery set type to display
     */
    public BingMaps(Culture culture, String key, ImagerySet imagerySet) {
        super();

        this.culture = culture;
        this.key = key;
        this.imagerySet = imagerySet;
    }

    /**
     * Returns the culture for this instance.
     *
     * @return Culture for this instance
     */
    public Culture getCulture() {
        return culture;
    }

    /**
     * Sets the culture for this instance.
     *
     * @param culture
     *         New value
     */
    public void setCulture(Culture culture) {
        this.culture = culture;
    }

    /**
     * Sets the culture for this instance.
     *
     * @param culture
     *         New value
     * @return This instance
     */
    public BingMaps culture(Culture culture) {
        setCulture(culture);
        return this;
    }

    /**
     * Returns the Bing Maps API key for this instance.
     *
     * @return Current value
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the Bing Maps API key for this instance.
     *
     * @param key
     *         New value
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Sets the Bing Maps API key for this instance.
     *
     * @param key
     *         New value
     * @return This instance
     */
    public BingMaps key(String key) {
        setKey(key);
        return this;
    }

    /**
     * Returns the imagery set for this instance.
     *
     * @return Current value
     */
    public ImagerySet getImagerySet() {
        return imagerySet;
    }

    /**
     * Sets the imagery set for this instance.
     *
     * @param imagerySet
     *         New value
     */
    public void setImagerySet(ImagerySet imagerySet) {
        this.imagerySet = imagerySet;
    }

    /**
     * Sets the imagery set for this instance.
     *
     * @param imagerySet
     *         New value
     * @return This instance
     */
    public BingMaps imagerySet(ImagerySet imagerySet) {
        setImagerySet(imagerySet);
        return this;
    }

    @Override
    public String getJsType() {
        return "ol.source.BingMaps";
    }

    @Override
    public String renderJs() {
        StringBuilder builder = new StringBuilder();
        builder.append("{");

        if (getCulture() != null) {
            builder.append("'culture': '" + getCulture() + "',");
        }

        if (getKey() != null) {
            builder.append("'key': '" + getKey() + "',");
        }

        if (getImagerySet() != null) {
            builder.append("'imagerySet': '" + getImagerySet() + "',");
        }

        builder.append("}");
        return builder.toString();
    }

    /**
     * Provides an enumeration of Bing Maps culture codes.
     */
    public enum Culture {

        ENGLISH_US("en-US"), ENGLISH_UK("en-GB");

        private String code;

        Culture(String code) {
            this.code = code;
        }

        @Override
        public String toString() {
            return code;
        }
    }

    /**
     * Provides an enumeration of the valid Bing Maps imagery types.
     */
    public enum ImagerySet {

        Aerial("Aerial"), AerialWithLabels("AerialWithLabels"), Road("Road"), OrdnanceSurvey("OrdnanceSurvey"),
        CollinsBart("CollinsBart");

        private String set;

        ImagerySet(String set) {
            this.set = set;
        }

        @Override
        public String toString() {
            return set;
        }
    }
}

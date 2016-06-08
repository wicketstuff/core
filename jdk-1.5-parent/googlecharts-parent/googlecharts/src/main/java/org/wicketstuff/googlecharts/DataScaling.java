package org.wicketstuff.googlecharts;

public class DataScaling implements IDataScaling {

    private final Float min;
    private final Float max;
    private final boolean autoScale;

    public DataScaling(boolean autoScale) {
        this(autoScale, null, null);
    }

    public DataScaling(Float min, Float max) {
        this(false, min, max);
    }

    private DataScaling(boolean autoScale, Float min, Float max) {
        this.autoScale = autoScale;
        this.min = !autoScale ? min : null;
        this.max = !autoScale ? max : null;
    }

    public boolean isAutoScale() {
        return autoScale;
    }

    public Float getMinimum() {
        return min;
    }

    public Float getMaximum() {
        return max;
    }
}

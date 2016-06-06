package org.wicketstuff.googlecharts;

public enum TextValueMarkerType {

    FLAG("f"),
    SIMPLE_TEXT("t"),
    ANNOTATION("A"),
    FORMATTING_STRING("N"),
    ;

    private final String rendering;

    private TextValueMarkerType(String rendering) {
        this.rendering = rendering;
    }

    public String getRendering() {
        return rendering;
    }
}

package org.wicketstuff.yui.markup.html.dialog;

import java.io.Serializable;

public class DialogSettings implements Serializable{
    
    public static enum UnderlayType{
        SHADOW("shadow"),
        NONE("none"),
        MATTE("matte");
        private String value; 
        UnderlayType(String value){
            this.value=value;
        }
        
        public String getValue(){
            return this.value;
        }
    }
    
    private Boolean close;
    private Boolean draggable;
    private Boolean visible;
    private Integer x;
    private Integer y;
    private String width;
    private String height;
    private UnderlayType underlay;
    private Boolean modal;
    private Boolean fixCenter;
    private Boolean constrainToViewport;
    
    
    
    public Boolean getClose() {
        return close;
    }
    public void setClose(Boolean close) {
        this.close = close;
    }
    public Boolean getDraggable() {
        return draggable;
    }
    public void setDraggable(Boolean draggable) {
        this.draggable = draggable;
    }
    public Boolean getModal() {
        return modal;
    }
    public void setModal(Boolean modal) {
        this.modal = modal;
    }
    public UnderlayType getUnderlay() {
        return underlay;
    }
    public void setUnderlay(UnderlayType underlay) {
        this.underlay = underlay;
    }
    public Boolean getVisible() {
        return visible;
    }
    public void setVisible(Boolean visible) {
        this.visible = visible;
    }
    public String getHeight() {
        return height;
    }
    public void setHeight(String height) {
        this.height = height;
    }
    public String getWidth() {
        return width;
    }
    public void setWidth(String width) {
        this.width = width;
    }
    public Integer getX() {
        return x;
    }
    public void setX(Integer x) {
        this.x = x;
    }
    public Integer getY() {
        return y;
    }
    public void setY(Integer y) {
        this.y = y;
    }
    public Boolean getConstrainToViewport() {
        return constrainToViewport;
    }
    public void setConstrainToViewport(Boolean constrainToViewport) {
        this.constrainToViewport = constrainToViewport;
    }
    public Boolean getFixCenter() {
        return fixCenter;
    }
    public void setFixCenter(Boolean fixCenter) {
        this.fixCenter = fixCenter;
    }
    
    public String generateSettings(){
        StringBuffer buffer = new StringBuffer("{");
        buffer.append(generateBoolean(getClose(), "close"));
        buffer.append(generateBoolean(getDraggable(), "draggable"));
        buffer.append(generateString(getHeight(), "height"));
        buffer.append(generateString(getWidth(), "width"));
        buffer.append(generateBoolean(getModal(), "modal"));
        buffer.append(generateBoolean(getVisible(), "visible"));
        buffer.append(generateInteger(getX(), "x"));
        buffer.append(generateInteger(getY(), "y"));
        buffer.append(generateBoolean(getConstrainToViewport(), "constraintoviewport"));
        buffer.append(generateBoolean(getFixCenter(), "fixedcenter"));
        if (getUnderlay() != null){
           buffer.append("underlay");
           buffer.append(": \"");
           buffer.append(getUnderlay().getValue());
           buffer.append("\",");
        }
        buffer.deleteCharAt(buffer.length()-1);
        buffer.append("}");
        return buffer.toString();
        
    }
    
    private String generateBoolean(Boolean b, String value){
        if (b != null){
            StringBuffer buffer = new StringBuffer(value);
            buffer.append(": ");
            buffer.append(b.booleanValue());
            buffer.append(",");
            return buffer.toString();
        }
        return "";
    }
    
    private String generateInteger(Integer i, String value){
        if (i != null){
            StringBuffer buffer = new StringBuffer(value);
            buffer.append(": ");
            buffer.append(i.intValue());
            buffer.append(",");
            return buffer.toString();
        }
        return "";
    }
    
    private String generateString(String s, String value){
        if (s != null){
            StringBuffer buffer = new StringBuffer(value);
            buffer.append(": \"");
            buffer.append(s);
            buffer.append("\",");
            return buffer.toString();
        }
        return "";
    }

}

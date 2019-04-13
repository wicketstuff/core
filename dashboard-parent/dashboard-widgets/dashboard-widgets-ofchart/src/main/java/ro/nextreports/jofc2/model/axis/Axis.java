/*
This file is part of jofc2.

jofc2 is free software: you can redistribute it and/or modify
it under the terms of the Lesser GNU General Public License as
published by the Free Software Foundation, either version 3 of
the License, or (at your option) any later version.

jofc2 is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

See <http://www.gnu.org/licenses/lgpl-3.0.txt>.
*/

package ro.nextreports.jofc2.model.axis;

import java.io.Serializable;

import ro.nextreports.jofc2.model.metadata.Alias;


public abstract class Axis implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 4823643361437691998L;
	private Integer stroke;
    private String colour;
    @Alias("grid-colour") private String grid_colour;
    private Double steps;
    private Integer offset;
    @Alias("3d") private Integer threed;
    private Double min;
    private Double max;

    
    public Integer getStroke() {
        return stroke;
    }
    public Axis setStroke(Integer stroke) {
        this.stroke = stroke;
        return this;
    }
    public String getColour() {
        return colour;
    }
    public Axis setColour(String colour) {
        this.colour = colour;
        return this;
    }
    public String getGridColour() {
        return grid_colour;
    }
    public Axis setGridColour(String grid_colour) {
        this.grid_colour = grid_colour;
        return this;
    }
    public Double getSteps() {
        return steps;
    }
    public Axis setSteps(Double steps) {
        this.steps = steps;
        return this;
    }
    public Axis setSteps(Integer steps) {
        this.steps = steps.doubleValue();
        return this;
    }
    public Integer getOffset() {
        return offset;
    }
    public Axis setOffset(Boolean offset) {
        if (offset == null) this.offset = null;
        else this.offset = offset ? 1 : 0;
        return this;
    }
    public Integer get3D() {
        return threed;
    }
    public Axis set3D(Integer threed) {
        this.threed = threed;
        return this;
    }
    public Double getMin() {
        return min;
    }
    public Axis setMin(Double min) {
        this.min = min;
        return this;
    }
    public Axis setMin(Integer min) {
        this.min = min.doubleValue();
        return this;
    }
    public Double getMax() {
        return max;
    }
    public Axis setMax(Double max) {
        this.max = max;
        return this;
    }
    public Axis setMax(Integer max) {
        this.max = max.doubleValue();
        return this;
    }
    public Axis setRange(Number min, Number max, Number steps) {
        setMin(min.doubleValue());
        setMax(max.doubleValue());
        setSteps(steps == null ? null : steps.doubleValue());
        return this;
    }
    
    public Axis setRange(Number min, Number max) {
        return setRange(min, max, getSteps());
    }

}

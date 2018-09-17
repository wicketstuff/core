/*
 *  Copyright 2011 Inaiat H. Moraes.
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */
package org.wicketstuff.jqplot.lib.elements;

/**
 * GridPadding
 * 
 * @author inaiat
 * 
 */
public class GridPadding implements Element {
    private static final long serialVersionUID = 5716187389339583925L;
    private Integer top;
    private Integer bottom;
    private Integer right;
    private Integer left;

    public GridPadding(Integer top, Integer bottom, Integer left, Integer right) {
	this.top = top;
	this.bottom = bottom;
	this.left = left;
	this.right = right;
    }

    public GridPadding() {
    }

    public Integer getTop() {
	return top;
    }

    public void setTop(Integer top) {
	this.top = top;
    }

    public Integer getBottom() {
	return bottom;
    }

    public void setBottom(Integer bottom) {
	this.bottom = bottom;
    }

    public Integer getRight() {
	return right;
    }

    public void setRight(Integer right) {
	this.right = right;
    }

    public Integer getLeft() {
	return left;
    }

    public void setLeft(Integer left) {
	this.left = left;
    }
}

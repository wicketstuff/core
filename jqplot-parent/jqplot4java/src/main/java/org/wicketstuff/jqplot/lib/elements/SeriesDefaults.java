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
 * SeriesDafaults
 * 
 * @author inaiat
 */
public class SeriesDefaults extends Serie {

	private static final long serialVersionUID = -7921968769382552293L;
	private PointLabels pointLabels;
	private Trendline trendline;

	/**
	 * 
	 * @return pointLables
	 */
	public PointLabels getPointLabels() {
		return pointLabels;
	}

	/**
	 * @param pointLabels Set point lables
	 * @return SeriesDafaults
	 */
	public SeriesDefaults setPointLabels(PointLabels pointLabels) {
		this.pointLabels = pointLabels;
		return this;
	}

	public Trendline getTrendline() {
		return trendline;
	}

	public SeriesDefaults setTrendline(Trendline trendline) {
		this.trendline = trendline;
		return this;
	}

	public Trendline trendlineInstance() {
		if (trendline == null) {
			trendline = new Trendline();
		}
		return trendline;
	}

	public PointLabels pointLabelsInstance() {
		if (pointLabels == null) {
			pointLabels = new PointLabels();
		}
		return pointLabels;
	}
}

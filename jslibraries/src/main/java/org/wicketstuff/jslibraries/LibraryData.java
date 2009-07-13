/**
 * Copyright (C) 2009 Uwe Schaefer <uwe@codesmell.de>
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.jslibraries;


public class LibraryData {

	static {

		// Local
		Library.register(Library.JQUERY, LocalProvider.DEFAULT, new int[][] {
				{ 1, 0, 4 }, { 1, 1, 4 }, { 1, 2 }, { 1, 2, 1 }, { 1, 2, 2 },
				{ 1, 2, 3 }, { 1, 2, 4 }, { 1, 2, 5 }, { 1, 2, 6 }, { 1, 3 },
				{ 1, 3, 1 }, { 1, 3, 2 } });
		Library.register(Library.JQUERY_UI, LocalProvider.DEFAULT,
				new int[][] { { 1, 5, 2 }, { 1, 5, 3 }, { 1, 7, 0 },
						{ 1, 7, 1 }, { 1, 7, 2 } });
		Library.register(Library.MOOTOOLS_CORE, LocalProvider.DEFAULT,
				new int[][] { { 1, 2, 1 }, });
		Library.register(Library.MOOTOOLS_MORE, LocalProvider.DEFAULT,
				new int[][] { { 1, 2 }, });
		Library.register(Library.PROTOTYPE, LocalProvider.DEFAULT, new int[][] {
				{ 1, 5, 0 }, { 1, 5, 1, 1 }, { 1, 5, 1, 2 }, { 1, 5, 1 },
				{ 1, 6, 0 }, { 1, 6, 0, 2 }, { 1, 6, 0, 3 }, });
		Library.register(Library.SCRIPTACULOUS, LocalProvider.DEFAULT,
				new int[][] { { 1, 8, 1 }, { 1, 8, 2 }, });
		Library.register(Library.YUI, LocalProvider.DEFAULT, new int[][] {
				{ 2, 6, 0 }, { 2, 7, 0 }, });
		Library.register(Library.DOJO, LocalProvider.DEFAULT, new int[][] {
				{ 1, 1, 1 }, { 1, 2, 0 }, { 1, 2, 3 }, { 1, 3, 0 },
				{ 1, 3, 1 }, });
		Library.register(Library.SWFOBJECT, LocalProvider.DEFAULT, new int[][] {
				{ 2, 1 }, { 2, 2 }, });
		Library.register(Library.EXT_CORE, LocalProvider.DEFAULT, new int[][] { { 3,
			0, 0 }, });

		// Google CDN
		Library.register(Library.JQUERY, CDN.GOOGLE,
				new int[][] { { 1, 2, 3 }, { 1, 2, 6 }, { 1, 3, 0 },
						{ 1, 3, 1 }, { 1, 3, 2 } });
		Library.register(Library.JQUERY_UI, CDN.GOOGLE, new int[][] {
				{ 1, 5, 2 }, { 1, 5, 3 }, { 1, 6 }, { 1, 7, 0 }, { 1, 7, 1 },
				{ 1, 7, 2 } });
		Library.register(Library.MOOTOOLS_CORE, CDN.GOOGLE, new int[][] {
				{ 1, 11 }, { 1, 2, 1 }, { 1, 2, 2 }, { 1, 2, 3 } });
		Library.register(Library.PROTOTYPE, CDN.GOOGLE, new int[][] {
				{ 1, 6, 0, 2 }, { 1, 6, 0, 3 }, });
		Library.register(Library.SCRIPTACULOUS, CDN.GOOGLE, new int[][] {
				{ 1, 8, 1 }, { 1, 8, 2 }, });
		Library.register(Library.YUI, CDN.GOOGLE, new int[][] {
				{ 2, 6, 0 }, { 2, 7, 0 }, });
		Library.register(Library.DOJO, CDN.GOOGLE, new int[][] {
				{ 1, 1, 1 }, { 1, 2, 0 }, { 1, 2, 3 }, { 1, 3, 0 },
				{ 1, 3, 1 }, });
		Library.register(Library.SWFOBJECT, CDN.GOOGLE, new int[][] {
				{ 2, 1 }, { 2, 2 }, });
		Library.register(Library.EXT_CORE, CDN.GOOGLE, new int[][] { { 3,
				0, 0 }, });
		// Yahoo CDN
		Library.register(Library.YUI, CDN.YAHOO, new int[][] {
				{ 2, 6, 0 }, { 2, 7, 0 }, });
		
	}
}

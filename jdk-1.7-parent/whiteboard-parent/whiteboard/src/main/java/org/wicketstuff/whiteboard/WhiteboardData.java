/*
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

package org.wicketstuff.whiteboard;

import org.wicketstuff.whiteboard.elements.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingDeque;

/**
 * This class holds all the data structures which are common to a whiteboard instance
 */
public class WhiteboardData {

	private Map<Integer, Element> elementMap;
	private Map<Integer, Element> loadedElementMap;

	private BlockingDeque<List<Element>> undoSnapshots;
	private BlockingDeque<List<Boolean>> undoSnapshotCreationList;

	private ArrayList<String> clipArts;
	private String clipArtFolder;

	private Map<String, ArrayList<String>> docMap;
	private String documentFolder;

	private Background background;

	private BlockingDeque<Background> undoSnapshots_Background;
	private BlockingDeque<Boolean> undoSnapshotCreationList_Background;

	private BlockingDeque<Boolean> isElementSnapshotList;

	private String loadedContent;

	public WhiteboardData(Map<Integer, Element> elementMap, Map<Integer, Element> loadedElementMap,
			BlockingDeque<List<Element>> undoSnapshots, BlockingDeque<List<Boolean>> undoSnapshotCreationList,
			BlockingDeque<Background> undoSnapshots_Background,
			BlockingDeque<Boolean> undoSnapshotCreationList_Background, BlockingDeque<Boolean> isElementSnapshotList,
			ArrayList<String> clipArts, String clipArtFolder, Map<String, ArrayList<String>> docMap,
			String documentFolder, Background background, String loadedContent) {
		this.elementMap = elementMap;
		this.loadedElementMap = loadedElementMap;
		this.undoSnapshots = undoSnapshots;
		this.undoSnapshotCreationList = undoSnapshotCreationList;
		this.clipArts = clipArts;
		this.clipArtFolder = clipArtFolder;
		this.docMap = docMap;
		this.documentFolder = documentFolder;
		this.background = background;
		this.loadedContent = loadedContent;
		this.undoSnapshots_Background = undoSnapshots_Background;
		this.undoSnapshotCreationList_Background = undoSnapshotCreationList_Background;
		this.isElementSnapshotList = isElementSnapshotList;
	}

	public Map<Integer, Element> getElementMap() {
		return elementMap;
	}

	public void setElementMap(Map<Integer, Element> elementMap) {
		this.elementMap = elementMap;
	}

	public Map<Integer, Element> getLoadedElementMap() {
		return loadedElementMap;
	}

	public void setLoadedElementMap(Map<Integer, Element> loadedElementMap) {
		this.loadedElementMap = loadedElementMap;
	}

	public BlockingDeque<List<Element>> getUndoSnapshots() {
		return undoSnapshots;
	}

	public void setUndoSnapshots(BlockingDeque<List<Element>> undoSnapshots) {
		this.undoSnapshots = undoSnapshots;
	}

	public BlockingDeque<List<Boolean>> getUndoSnapshotCreationList() {
		return undoSnapshotCreationList;
	}

	public void setUndoSnapshotCreationList(BlockingDeque<List<Boolean>> undoSnapshotCreationList) {
		this.undoSnapshotCreationList = undoSnapshotCreationList;
	}

	public ArrayList<String> getClipArts() {
		return clipArts;
	}

	public void setClipArts(ArrayList<String> clipArts) {
		this.clipArts = clipArts;
	}

	public String getClipArtFolder() {
		return clipArtFolder;
	}

	public void setClipArtFolder(String clipArtFolder) {
		this.clipArtFolder = clipArtFolder;
	}

	public Map<String, ArrayList<String>> getDocMap() {
		return docMap;
	}

	public void setDocMap(Map<String, ArrayList<String>> docMap) {
		this.docMap = docMap;
	}

	public String getDocumentFolder() {
		return documentFolder;
	}

	public void setDocumentFolder(String documentFolder) {
		this.documentFolder = documentFolder;
	}

	public Background getBackground() {
		return background;
	}

	public void setBackground(Background background) {
		this.background = background;
	}

	public String getLoadedContent() {
		return loadedContent;
	}

	public void setLoadedContent(String loadedContent) {
		this.loadedContent = loadedContent;
	}

	public BlockingDeque<Background> getUndoSnapshots_Background() {
		return undoSnapshots_Background;
	}

	public void setUndoSnapshots_Background(BlockingDeque<Background> undoSnapshots_Background) {
		this.undoSnapshots_Background = undoSnapshots_Background;
	}

	public BlockingDeque<Boolean> getUndoSnapshotCreationList_Background() {
		return undoSnapshotCreationList_Background;
	}

	public void setUndoSnapshotCreationList_Background(BlockingDeque<Boolean> undoSnapshotCreationList_Background) {
		this.undoSnapshotCreationList_Background = undoSnapshotCreationList_Background;
	}

	public BlockingDeque<Boolean> getIsElementSnapshotList() {
		return isElementSnapshotList;
	}

	public void setIsElementSnapshotList(BlockingDeque<Boolean> elementSnapshotList) {
		isElementSnapshotList = elementSnapshotList;
	}
}

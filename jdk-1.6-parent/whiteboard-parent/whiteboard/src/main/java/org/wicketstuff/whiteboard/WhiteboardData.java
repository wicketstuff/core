package org.wicketstuff.whiteboard;

import org.wicketstuff.whiteboard.elements.Element;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * This class holds all the data structures which are common to a whiteboard instance
 */
public class WhiteboardData{

	private  Map<Integer,Element> elementMap;
	private  Map<Integer,Element> loadedElementMap;

	private  BlockingDeque<List<Element>> undoSnapshots;
	private  BlockingDeque<List<Boolean>> undoSnapshotCreationList;

	private  ArrayList<String> clipArts;
	private  String clipArtFolder;

	private  Map<String,ArrayList<String>> docMap;
	private  String documentFolder;

	private  Background background;

	private  String loadedContent;

	public WhiteboardData(Map<Integer,Element> elementMap, Map<Integer,Element> loadedElementMap, BlockingDeque<List<Element>> undoSnapshots, BlockingDeque<List<Boolean>> undoSnapshotCreationList, ArrayList<String> clipArts, String clipArtFolder, Map<String,ArrayList<String>> docMap, String documentFolder, Background background, String loadedContent){
		this.elementMap=elementMap;
		this.loadedElementMap=loadedElementMap;
		this.undoSnapshots=undoSnapshots;
		this.undoSnapshotCreationList=undoSnapshotCreationList;
		this.clipArts=clipArts;
		this.clipArtFolder=clipArtFolder;
		this.docMap=docMap;
		this.documentFolder=documentFolder;
		this.background=background;
		this.loadedContent=loadedContent;
	}

	public Map<Integer,Element> getElementMap(){
		return elementMap;
	}

	public void setElementMap(Map<Integer,Element> elementMap){
		this.elementMap=elementMap;
	}

	public Map<Integer,Element> getLoadedElementMap(){
		return loadedElementMap;
	}

	public void setLoadedElementMap(Map<Integer,Element> loadedElementMap){
		this.loadedElementMap=loadedElementMap;
	}

	public BlockingDeque<List<Element>> getUndoSnapshots(){
		return undoSnapshots;
	}

	public void setUndoSnapshots(BlockingDeque<List<Element>> undoSnapshots){
		this.undoSnapshots=undoSnapshots;
	}

	public BlockingDeque<List<Boolean>> getUndoSnapshotCreationList(){
		return undoSnapshotCreationList;
	}

	public void setUndoSnapshotCreationList(BlockingDeque<List<Boolean>> undoSnapshotCreationList){
		this.undoSnapshotCreationList=undoSnapshotCreationList;
	}

	public ArrayList<String> getClipArts(){
		return clipArts;
	}

	public void setClipArts(ArrayList<String> clipArts){
		this.clipArts=clipArts;
	}

	public String getClipArtFolder(){
		return clipArtFolder;
	}

	public void setClipArtFolder(String clipArtFolder){
		this.clipArtFolder=clipArtFolder;
	}

	public Map<String,ArrayList<String>> getDocMap(){
		return docMap;
	}

	public void setDocMap(Map<String,ArrayList<String>> docMap){
		this.docMap=docMap;
	}

	public String getDocumentFolder(){
		return documentFolder;
	}

	public void setDocumentFolder(String documentFolder){
		this.documentFolder=documentFolder;
	}

	public Background getBackground(){
		return background;
	}

	public void setBackground(Background background){
		this.background=background;
	}

	public String getLoadedContent(){
		return loadedContent;
	}

	public void setLoadedContent(String loadedContent){
		this.loadedContent=loadedContent;
	}
}

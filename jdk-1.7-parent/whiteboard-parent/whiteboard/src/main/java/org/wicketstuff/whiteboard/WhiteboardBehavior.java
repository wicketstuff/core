/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.whiteboard;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.json.JSONArray;
import org.apache.wicket.ajax.json.JSONException;
import org.apache.wicket.ajax.json.JSONObject;
import org.apache.wicket.markup.head.*;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.ws.WebSocketSettings;
import org.apache.wicket.protocol.ws.api.IWebSocketConnection;
import org.apache.wicket.protocol.ws.api.registry.IWebSocketConnectionRegistry;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.whiteboard.elements.*;
import org.wicketstuff.whiteboard.elements.Element.Type;
import org.wicketstuff.whiteboard.resource.*;
import org.wicketstuff.whiteboard.settings.WhiteboardLibrarySettings;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * This class is the behaviour handler of the whiteboard. All the server-side functionality of whiteboard is handled
 * here
 * 
 * @author andunslg
 */
public class WhiteboardBehavior extends AbstractDefaultAjaxBehavior {
	private static final Map<String, WhiteboardData> whiteboardMap = new ConcurrentHashMap<String, WhiteboardData>();

	private static final Logger log = LoggerFactory.getLogger(WhiteboardBehavior.class);
	private static final long serialVersionUID = 1L;
	private String whiteboardMarkupId;
	private String whiteboardObjectId;

	private Map<Integer, Element> elementMap;
	private Map<Integer, Element> loadedElementMap;

	private BlockingDeque<List<Element>> undoSnapshots;
	private BlockingDeque<List<Boolean>> undoSnapshotCreationList;

	private List<Element> snapShot = null;
	private List<Boolean> snapShotCreation = null;

	private DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");

	private ArrayList<String> clipArts;
	private String clipArtFolder;

	private Map<String, ArrayList<String>> docMap;
	private String documentFolder;

	private Background background;

	private BlockingDeque<Background> undoSnapshots_Background;
	private BlockingDeque<Boolean> undoSnapshotCreationList_Background;

	private BlockingDeque<Boolean> isElementSnapshotList;

	private String loadedContent = "";

	/**
	 * Creating the behaviour using whiteboard's html element id
	 * 
	 * @param whiteboardMarkupId
	 */
	public WhiteboardBehavior(String whiteboardObjectId, String whiteboardMarkupId) {
		this(whiteboardObjectId, whiteboardMarkupId, null, null, null);
	}

	/**
	 * This is the constructor which used to create a whiteboard behaviour with more features
	 * 
	 * @param whiteboardMarkupId
	 *            html element id which holds the whiteboard
	 * @param whiteboardContent
	 *            If loading from a saved whiteboard file, content should be provided as a string. Otherwise null
	 * @param clipArtFolder
	 *            Path of the folder which holds clipArts which can be added to whiteboard. Relative to context root
	 * @param documentFolder
	 *            Path of the folder which holds docs images which can be added to whiteboard. Relative to context root
	 */
	public WhiteboardBehavior(String whiteboardObjectId, String whiteboardMarkupId, String whiteboardContent,
			String clipArtFolder, String documentFolder) {
		super();
		this.whiteboardObjectId = whiteboardObjectId;
		this.whiteboardMarkupId = whiteboardMarkupId;

		if (!whiteboardMap.containsKey(whiteboardObjectId)) {
			elementMap = new ConcurrentHashMap<Integer, Element>();
			loadedElementMap = new ConcurrentHashMap<Integer, Element>();

			undoSnapshots = new LinkedBlockingDeque<List<Element>>(20);
			undoSnapshotCreationList = new LinkedBlockingDeque<List<Boolean>>(20);

			undoSnapshots_Background = new LinkedBlockingDeque<Background>(20);
			undoSnapshotCreationList_Background = new LinkedBlockingDeque<Boolean>(20);

			isElementSnapshotList = new LinkedBlockingDeque<Boolean>(20);

			clipArts = new ArrayList<String>();
			docMap = new ConcurrentHashMap<String, ArrayList<String>>();

			loadedContent = whiteboardContent;

			WhiteboardData whiteboardData = new WhiteboardData(elementMap, loadedElementMap, undoSnapshots,
					undoSnapshotCreationList, undoSnapshots_Background, undoSnapshotCreationList_Background,
					isElementSnapshotList, clipArts, null, docMap, null, null, whiteboardContent);
			whiteboardMap.put(whiteboardObjectId, whiteboardData);
		} else {
			WhiteboardData whiteboardData = whiteboardMap.get(whiteboardObjectId);
			elementMap = whiteboardData.getElementMap();
			loadedElementMap = whiteboardData.getLoadedElementMap();

			undoSnapshots = whiteboardData.getUndoSnapshots();
			undoSnapshotCreationList = whiteboardData.getUndoSnapshotCreationList();

			clipArts = whiteboardData.getClipArts();
			docMap = whiteboardData.getDocMap();

			this.clipArtFolder = whiteboardData.getClipArtFolder();
			this.documentFolder = whiteboardData.getDocumentFolder();
			background = whiteboardData.getBackground();
			loadedContent = whiteboardData.getLoadedContent();

			undoSnapshots_Background = whiteboardData.getUndoSnapshots_Background();
			undoSnapshotCreationList_Background = whiteboardData.getUndoSnapshotCreationList_Background();

			isElementSnapshotList = whiteboardData.getIsElementSnapshotList();
		}

		if (!"".equals(loadedContent)) {
			whiteboardMap.get(whiteboardObjectId).setLoadedContent(loadedContent);
			if (whiteboardContent != null && !whiteboardContent.equals("")) {
				try {
					JSONObject savedContent = new JSONObject(whiteboardContent);

					JSONArray elementList = (JSONArray) savedContent.get("elements");
					snapShot = new ArrayList<Element>();
					snapShotCreation = new ArrayList<Boolean>();

					for (int i = 0; i < elementList.length(); i++) {
						JSONObject jElement = (JSONObject) elementList.get(i);

						Element element = getElementObject(jElement);

						if (element != null) {
							elementMap.put(element.getId(), element);
							loadedElementMap.put(element.getId(), element);
							snapShot.add(element);
							snapShotCreation.add(true);
						}
					}
					if (undoSnapshots.isEmpty()) {
						undoSnapshots.addLast(snapShot);
						undoSnapshotCreationList.addLast(snapShotCreation);
						isElementSnapshotList.addLast(true);
					}

					snapShot = null;
					snapShotCreation = null;

					if (savedContent.has("background")) {
						JSONObject backgroundJSON = (JSONObject) savedContent.get("background");
						background = new Background(backgroundJSON);
						whiteboardMap.get(whiteboardObjectId).setBackground(background);
						undoSnapshots_Background.addLast(new Background("Background", "", 0.0, 0.0, 0.0, 0.0));
						undoSnapshotCreationList_Background.addLast(true);
						isElementSnapshotList.addLast(false);
					}

				} catch (JSONException e) {
					log.error("Unexpected error while constructing WhiteboardBehavior", e);
				}
			}
		}

		// Setting the path to clipArt folder
		if (clipArtFolder != null && !clipArtFolder.equals("")) {

			this.clipArtFolder = clipArtFolder;
			whiteboardMap.get(whiteboardObjectId).setClipArtFolder(clipArtFolder);
			this.loadClipArts();

		}

		// Setting the path to documents folder
		if (documentFolder != null && !documentFolder.equals("")) {

			this.documentFolder = documentFolder;
			whiteboardMap.get(whiteboardObjectId).setDocumentFolder(documentFolder);
			this.loadDocuments();

		}
	}

	/**
	 * This method handles all the Ajax calls coming from whiteboard
	 * 
	 * @param target
	 */
	protected void respond(final AjaxRequestTarget target) {

		RequestCycle cycle = RequestCycle.get();
		WebRequest webRequest = (WebRequest) cycle.getRequest();

		// If geometric element is drawn of edited on whiteboard, message will be sent and this if clause handles that
		if (webRequest.getQueryParameters().getParameterNames().contains("editedElement")) {
			String editedElement = webRequest.getQueryParameters().getParameterValue("editedElement").toString();
			handleEditedElement(editedElement);
		}
		// If undo button is clicked on whiteboard, message will be sent and this if clause handles that
		else if (webRequest.getQueryParameters().getParameterNames().contains("undo")) {
			handleUndo();
		}
		// If eraseAll button is clicked on whiteboard, message will be sent and this if clause handles that
		else if (webRequest.getQueryParameters().getParameterNames().contains("eraseAll")) {
			handleEraseAll();
		}
		// If save button is clicked on whiteboard, message will be sent and this if clause handles that
		else if (webRequest.getQueryParameters().getParameterNames().contains("save")) {
			handleSave();
		}
		// If addClipArt button is clicked on whiteboard, message will be sent and this if clause handles that
		else if (webRequest.getQueryParameters().getParameterNames().contains("clipArt")) {
			handleClipArts();
		}
		// If addDocument button is clicked on whiteboard, message will be sent and this if clause handles that
		else if (webRequest.getQueryParameters().getParameterNames().contains("docList")) {
			handleDocs();
			;
		}
		// If document page navigation buttons are clicked on whiteboard, message will be sent and this if clause
		// handles that
		else if (webRequest.getQueryParameters().getParameterNames().contains("docComponents")) {
			String docBaseName = webRequest.getQueryParameters().getParameterValue("docBaseName").toString();
			handleDocComponents(docBaseName);
		}
		// If document is added to whiteboard, message will be sent and this if clause handles that
		else if (webRequest.getQueryParameters().getParameterNames().contains("background")) {
			String backgroundString = webRequest.getQueryParameters().getParameterValue("background").toString();
			handleBackground(backgroundString);
		}
	}

	/**
	 * Mapping JSON String to Objects and Adding to the Element List
	 * 
	 * @param editedElement
	 * @return
	 */
	private boolean handleEditedElement(String editedElement) {
		try {
			JSONObject jsonEditedElement = new JSONObject(editedElement);
			Element element = getElementObject(jsonEditedElement);

			boolean isLoaded = false;

			if (!elementMap.isEmpty() && loadedElementMap.get(element.getId()) != null && !loadedElementMap.isEmpty()) {
				isLoaded = isEqual(element.getJSON(), loadedElementMap.get(element.getId()).getJSON());
			}

			// If the edited element is not from file loaded content this clause executes
			if (!isLoaded) {

				// Adding the element creation/editing is add to the undo snapshots
				if (snapShot == null && snapShotCreation == null) {
					snapShot = new ArrayList<Element>();
					snapShotCreation = new ArrayList<Boolean>();
				}

				if (elementMap.containsKey(element.getId()) && !elementMap.isEmpty()) {
					snapShot.add(elementMap.get(element.getId()));
					snapShotCreation.add(false);

				} else {
					snapShot.add(element);
					snapShotCreation.add(true);
				}

				if (Type.PointFree != element.getType()) {
					if (undoSnapshots.size() == 20) {
						undoSnapshots.pollFirst();
						undoSnapshotCreationList.pollFirst();
					}

					if (Type.PencilCurve == element.getType()) {
						List<Element> lastElementSnapshot = undoSnapshots.peekLast();
						if (lastElementSnapshot != null) {
							Element lastSnapshotElement = lastElementSnapshot.get(lastElementSnapshot.size() - 1);
	
							if ((lastSnapshotElement instanceof PencilCurve) && (lastSnapshotElement.getId() == element.getId())) {
								List<Boolean> lastCreationSnapshot = undoSnapshotCreationList.getLast();
	
								for (int i = 0; i < snapShot.size(); i++) {
									lastElementSnapshot.add(snapShot.get(i));
									lastCreationSnapshot.add(snapShotCreation.get(i));
								}
							} else {
								undoSnapshots.addLast(snapShot);
								undoSnapshotCreationList.addLast(snapShotCreation);
								isElementSnapshotList.addLast(true);
							}
						}
					} else if (Type.ClipArt == element.getType()) {
						List<Element> snapShotTemp = undoSnapshots.pollLast();
						List<Boolean> snapShotCreationTemp = undoSnapshotCreationList.pollLast();

						for (int i = 0; i < snapShotTemp.size(); i++) {
							snapShot.add(snapShotTemp.get(i));
							snapShotCreation.add(snapShotCreationTemp.get(i));
						}
						undoSnapshots.addLast(snapShot);
						undoSnapshotCreationList.addLast(snapShotCreation);
						isElementSnapshotList.addLast(true);

					} else {

						undoSnapshots.addLast(snapShot);
						undoSnapshotCreationList.addLast(snapShotCreation);
						isElementSnapshotList.addLast(true);
					}

					snapShot = null;
					snapShotCreation = null;
				}

				// Synchronizing newly added/edited element between whiteboard clients
				if (element != null) {
					elementMap.put(element.getId(), element);

					IWebSocketConnectionRegistry reg = WebSocketSettings.Holder.get(Application.get()).getConnectionRegistry();
					for (IWebSocketConnection c : reg.getConnections(Application.get())) {
						try {
							JSONObject jsonObject = new JSONObject(editedElement);
							c.sendMessage(getAddElementMessage(jsonObject).toString());
						} catch (Exception e) {
							log.error("Unexpected error while sending message through the web socket", e);
						}
					}
				}
			}
			return true;
		} catch (JSONException e) {
			log.error("Unexpected error while editing element", e);
		}
		return false;
	}

	/**
	 * Synchronizing newly added/edited background between whiteboard clients
	 * 
	 * @param backgroundString
	 * @return
	 */
	private boolean handleBackground(String backgroundString) {
		try {
			JSONObject backgroundJSON = new JSONObject(backgroundString);
			Background backgroundObject = new Background(backgroundJSON);
			background = backgroundObject;

			Background previousBackground = new Background("Background", "", 0.0, 0.0, 0.0, 0.0);
			if (whiteboardMap.get(whiteboardObjectId).getBackground() != null) {
				previousBackground = whiteboardMap.get(whiteboardObjectId).getBackground();
			}
			whiteboardMap.get(whiteboardObjectId).setBackground(background);

			undoSnapshotCreationList_Background.addLast(previousBackground == null);
			undoSnapshots_Background.addLast(previousBackground);
			isElementSnapshotList.addLast(false);

			IWebSocketConnectionRegistry reg = WebSocketSettings.Holder.get(Application.get()).getConnectionRegistry();
			for (IWebSocketConnection c : reg.getConnections(Application.get())) {
				try {
					JSONObject jsonObject = new JSONObject(backgroundString);
					c.sendMessage(getAddBackgroundMessage(jsonObject).toString());
				} catch (Exception e) {
					log.error("Unexpected error while sending message through the web socket", e);
				}
			}

			return true;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Undo one step and synchronizing undo between whiteboard clients
	 * 
	 * @return
	 */
	private boolean handleUndo() {
		if (!isElementSnapshotList.isEmpty()) {

			if (isElementSnapshotList.pollLast()) {
				List<Boolean> undoCreationList = undoSnapshotCreationList.pollLast();
				List<Element> undoElement = undoSnapshots.pollLast();

				String deleteList = "";
				JSONArray changeList = new JSONArray();

				IWebSocketConnectionRegistry reg = WebSocketSettings.Holder.get(Application.get()).getConnectionRegistry();

				for (int i = 0; i < undoElement.size(); i++) {
					if (undoCreationList.get(i)) {
						elementMap.remove(undoElement.get(i).getId());
						if (loadedElementMap.containsKey(undoElement.get(i).getId())) {
							loadedContent = "";
							whiteboardMap.get(whiteboardObjectId).setLoadedContent("");
							loadedElementMap.remove(undoElement.get(i).getId());
						}
						if ("".equals(deleteList)) {
							deleteList = "" + undoElement.get(i).getId();
						} else {
							deleteList += "," + undoElement.get(i).getId();
						}
					} else {
						elementMap.put(undoElement.get(i).getId(), undoElement.get(i));
						try {
							changeList.put(undoElement.get(i).getJSON());
						} catch (JSONException e) {
							log.error("Unexpected error while getting JSON", e);
						}
					}
				}

				for (IWebSocketConnection c : reg.getConnections(Application.get())) {
					try {
						c.sendMessage(getUndoMessage(changeList, deleteList).toString());
					} catch (Exception e) {
						log.error("Unexpected error while sending message through the web socket", e);
					}
				}
			} else {
				Background previousBackground = undoSnapshots_Background.pollLast();
				background = previousBackground;
				whiteboardMap.get(whiteboardObjectId).setBackground(previousBackground);

				IWebSocketConnectionRegistry reg = WebSocketSettings.Holder.get(Application.get()).getConnectionRegistry();
				for (IWebSocketConnection c : reg.getConnections(Application.get())) {
					try {
						if (previousBackground != null) {
							c.sendMessage(getAddBackgroundMessage(previousBackground.getJSON()).toString());
						} else {
							c.sendMessage(getAddBackgroundMessage(new JSONObject()).toString());
						}
					} catch (Exception e) {
						log.error("Unexpected error while sending message through the web socket", e);
					}
				}

			}
			return true;
		}
		return false;
	}

	/**
	 * Synchronizing eraseAll request between whiteboard clients
	 * 
	 * @return
	 */
	private boolean handleEraseAll() {
		elementMap.clear();
		IWebSocketConnectionRegistry reg = WebSocketSettings.Holder.get(Application.get()).getConnectionRegistry();
		for (IWebSocketConnection c : reg.getConnections(Application.get())) {
			try {
				JSONArray jsonArray = new JSONArray();
				c.sendMessage(getEraseAllMessage(jsonArray).toString());
				return true;
			} catch (Exception e) {
				log.error("Unexpected error while sending message through the web socket", e);
			}
		}
		return false;
	}

	/**
	 * Save the whiteboard content to a file
	 * 
	 * @return
	 */
	private boolean handleSave() {
		ServletContext servletContext = WebApplication.get().getServletContext();
		String saveFolderPath = servletContext.getRealPath("") + "/Saved_Whiteboards";

		File saveFolder = new File(saveFolderPath);
		if (!saveFolder.exists()) {
			saveFolder.mkdir();
		}

		boolean result = false;
		JSONObject saveObject = new JSONObject();

		JSONArray elementArray = new JSONArray();
		for (int elementID : elementMap.keySet()) {
			try {
				elementArray.put(elementMap.get(elementID).getJSON());
			} catch (JSONException e) {
				log.error("Unexpected error while getting JSON", e);
			}
		}
		JSONObject backgroundJSON = null;
		if (background != null) {
			try {
				backgroundJSON = background.getJSON();
			} catch (JSONException e) {
				log.error("Unexpected error while getting JSON", e);
			}
		}

		try {
			saveObject.put("elements", elementArray);
			if (backgroundJSON != null) {
				saveObject.put("background", backgroundJSON);
			}
		} catch (JSONException e) {
			log.error("Unexpected error while getting JSON", e);
		}

		File whiteboardFile = new File(saveFolderPath + "/Whiteboard_" + dateFormat.format(new Date()) + ".json");

		FileWriter writer = null;
		try {
			whiteboardFile.createNewFile();
			log.debug("Going to dump WB to file: " + whiteboardFile.getAbsolutePath());
			writer = new FileWriter(whiteboardFile);
			writer.write(saveObject.toString());
			writer.flush();
			result = true;
		} catch (IOException e) {
			log.debug("Unexpected error during dumping WB to file ", e);
		} finally {
			if (writer != null) {
				try {
					writer.close();
					log.debug("Dumped WB to file: " + whiteboardFile.getAbsolutePath());
				} catch (IOException e) {
					log.debug("Unexpected error during closing WB file ", e);
					result = false;
				}
			}
		}
		return result;
	}

	/**
	 * Load the clipArts list from the clipArt folder and synchronizing the list between whiteboard clients
	 */
	private void handleClipArts() {
		loadClipArts();
		IWebSocketConnectionRegistry reg = WebSocketSettings.Holder.get(Application.get()).getConnectionRegistry();
		for (IWebSocketConnection c : reg.getConnections(Application.get())) {
			try {
				JSONArray jsonArray = new JSONArray();
				for (String clipArtURL : clipArts) {
					jsonArray.put(clipArtURL);
				}
				c.sendMessage(getClipArtListMessage(jsonArray).toString());
			} catch (Exception e) {
				log.error("Unexpected error while sending message through the web socket", e);
			}
		}
	}

	/**
	 * Load the documents list from the documents folder and synchronizing the list between whiteboard clients
	 */
	private void handleDocs() {
		loadDocuments();
		IWebSocketConnectionRegistry reg = WebSocketSettings.Holder.get(Application.get()).getConnectionRegistry();
		for (IWebSocketConnection c : reg.getConnections(Application.get())) {
			try {
				JSONArray jsonArray = new JSONArray();
				Set<String> keySet = docMap.keySet();
				for (String key : keySet) {
					jsonArray.put(docMap.get(key).get(0));
				}
				c.sendMessage(getDocumentListMessage(jsonArray).toString());
			} catch (Exception e) {
				log.error("Unexpected error while sending message through the web socket", e);
			}
		}
	}

	/**
	 * Load the components of a particular document from the documents folder and synchronizing the list between
	 * whiteboard clients
	 * 
	 * @param docBaseName
	 */
	private void handleDocComponents(String docBaseName) {
		loadDocuments();
		IWebSocketConnectionRegistry reg = WebSocketSettings.Holder.get(Application.get()).getConnectionRegistry();
		for (IWebSocketConnection c : reg.getConnections(Application.get())) {
			try {
				JSONArray jsonArray = new JSONArray();
				for (String url : docMap.get(docBaseName)) {
					jsonArray.put(url);
				}
				c.sendMessage(getDocumentComponentListMessage(jsonArray).toString());
			} catch (Exception e) {
				log.error("Unexpected error while sending message through the web socket", e);
			}
		}
	}

	private JSONObject getAddElementMessage(JSONObject element) throws JSONException {
		return new JSONObject().put("type", "addElement").put("json", element);
	}

	private JSONObject getAddBackgroundMessage(JSONObject element) throws JSONException {
		return new JSONObject().put("type", "addBackground").put("json", element);
	}

	private JSONObject getUndoMessage(JSONArray changeList, String deleteList) throws JSONException {
		return new JSONObject().put("type", "undoList").put("changeList", changeList).put("deleteList", deleteList);
	}

	/* commented for now
	private JSONObject getWhiteboardMessage(JSONArray array) throws JSONException {
		return new JSONObject().put("type", "parseWB").put("json", array);
	}
	*/

	private JSONObject getEraseAllMessage(JSONArray array) throws JSONException {
		return new JSONObject().put("type", "eraseElements").put("json", array);
	}

	private JSONObject getClipArtListMessage(JSONArray array) throws JSONException {
		return new JSONObject().put("type", "clipArtList").put("json", array);
	}

	private JSONObject getDocumentListMessage(JSONArray array) throws JSONException {
		return new JSONObject().put("type", "documentList").put("json", array);
	}

	private JSONObject getDocumentComponentListMessage(JSONArray array) throws JSONException {
		return new JSONObject().put("type", "documentComponentList").put("json", array);
	}

	public void renderHead(Component component, IHeaderResponse response) {
		super.renderHead(component, response);

		initReferences(response);

		try {
			// Synchronizing existing content between clients
			JSONArray elements = null;
			if (!elementMap.isEmpty()) {
				Map<Integer, Element> sortedElementList = new TreeMap<Integer, Element>(elementMap);
				elements = new JSONArray();
				for (Element e : sortedElementList.values()) {
						elements.put(e.getJSON());
				}
			}
	
			response.render(OnDomReadyHeaderItem.forScript(String.format("initWB('%s', '%s', %s, %s);", getCallbackUrl(), whiteboardMarkupId
					, elements, background == null ? null : background.getJSON())));
		} catch (JSONException e) {
			log.error("Unexpected error while getting JSON", e);
		}
	}

	/**
	 * Loading default resources which need to whiteboard
	 * 
	 * @param response
	 */
	private void initReferences(IHeaderResponse response) {
		WhiteboardLibrarySettings settings = getLibrarySettings();

		// Whiteboard.css
		if (settings != null && settings.getWhiteboardStyleSheetReference() != null) {
			response.render(new PriorityHeaderItem(CssHeaderItem.forReference(settings.getWhiteboardStyleSheetReference())));
		} else {
			response.render(new PriorityHeaderItem(CssHeaderItem.forReference(WhiteboardStyleSheetResourceReference.get())));
		}

		// Goog.css
		if (settings != null && settings.getGoogStyleSheetReference() != null) {
			response.render(new PriorityHeaderItem(CssHeaderItem.forReference(settings.getGoogStyleSheetReference())));
		} else {
			response.render(new PriorityHeaderItem(CssHeaderItem.forReference(GoogStyleSheetResourceReference.get())));
		}

		// translate.js
		if (settings != null && settings.getTranslateJavaScriptReference() != null) {
			response.render(new PriorityHeaderItem(JavaScriptHeaderItem.forReference(settings.getTranslateJavaScriptReference())));
		} else {
			response.render(new PriorityHeaderItem(JavaScriptHeaderItem.forReference(TranslateJavaScriptResourceReference.get())));
		}

		// whiteboard.js
		if (settings != null && settings.getWhiteboardJavaScriptReference() != null) {
			response.render(new PriorityHeaderItem(JavaScriptHeaderItem.forReference(settings.getWhiteboardJavaScriptReference())));
		} else {
			response.render(new PriorityHeaderItem(JavaScriptHeaderItem.forReference(WhiteboardJavaScriptResourceReference.get())));
		}

		// wb-helper.js
		if (settings != null && settings.getWhiteboardHelperJavaScriptReference() != null) {
			response.render(new PriorityHeaderItem(JavaScriptHeaderItem.forReference(settings.getWhiteboardHelperJavaScriptReference())));
		} else {
			response.render(new PriorityHeaderItem(JavaScriptHeaderItem.forReference(WhiteboardHelperJavaScriptResourceReference.get())));
		}
	}

	private static WhiteboardLibrarySettings getLibrarySettings() {
		if (Application.exists() && (Application.get().getJavaScriptLibrarySettings() instanceof WhiteboardLibrarySettings)) {
			return (WhiteboardLibrarySettings) Application.get().getJavaScriptLibrarySettings();
		}

		return null;
	}

	public Map<Integer, Element> getElementMap() {
		return elementMap;
	}

	/**
	 * Give a Element object for given JSON object which represent a element
	 * 
	 * @param obj
	 * @return
	 * @throws JSONException
	 */
	private Element getElementObject(JSONObject obj) throws JSONException {
		Element element = null;

		Type type = Type.valueOf(obj.getString("type"));
		switch (type) {
			case CircleGeneral:
				element = new CircleGeneral(obj);
				break;
			case Circle_3p:
				element = new Circle_3p(obj);
				break;
			case LineGeneral:
				element = new LineGeneral(obj);
				break;
			case Line_2p:
				element = new Line_2p(obj);
				break;
			case PencilArrow:
				element = new PencilArrow(obj);
				break;
			case PencilCircle:
				element = new PencilCircle(obj);
				break;
			case PencilCurve:
				element = new PencilCurve(obj);
				break;
			case PencilFreeLine:
				element = new PencilFreeLine(obj);
				break;
			case PencilPointAtRect:
				element = new PencilPointAtRect(obj);
				break;
			case PencilPointer:
				element = new PencilPointer(obj);
				break;
			case PencilRect:
				element = new PencilRect(obj);
				break;
			case PencilUnderline:
				element = new PencilUnderline(obj);
				break;
			case PointAtCircle:
				element = new PointAtCircle(obj);
				break;
			case PointAtLine:
				element = new PointAtLine(obj);
				break;
			case PointFree:
				element = new PointFree(obj);
				break;
			case Point_2c:
				element = new Point_2c(obj);
				break;
			case Point_2l:
				element = new Point_2l(obj);
				break;
			case Point_lc:
				element = new Point_lc(obj);
				break;
			case Segment:
				element = new Segment(obj);
				break;
			case Text:
				element = new Text(obj);
				break;
			case ClipArt:
				element = new ClipArt(obj);
				break;
			default:
				break;

		}

		return element;
	}

	/**
	 * Load clipArts from the clipArt folder and filling the clipArts list
	 */
	private void loadClipArts() {
		List<String> pictureExtensions = Arrays.asList(new String[] { "jpg", "bmp", "png", "jpeg", "gif" });
		ServletContext servletContext = WebApplication.get().getServletContext();

		HttpServletRequest httpReq = (HttpServletRequest) ((WebRequest) RequestCycle.get().getRequest())
				.getContainerRequest();
		Url relative = Url.parse(httpReq.getContextPath());
		String basURL = RequestCycle.get().getUrlRenderer().renderFullUrl(relative);

		String clipArtFolderPath = servletContext.getRealPath("") + "/" + clipArtFolder;
		File folder = new File(clipArtFolderPath);

		for (final File fileEntry : folder.listFiles()) {
			if (!fileEntry.isDirectory()) {
				String extension = "";
				int i = fileEntry.getAbsolutePath().lastIndexOf('.');
				if (i > 0) {
					extension = fileEntry.getAbsolutePath().substring(i + 1);
				}
				if (pictureExtensions.contains(extension)) {
					String clipArtURL = basURL + "/" + clipArtFolder + "/" + fileEntry.getName();
					if (!clipArts.contains(clipArtURL)) {
						clipArts.add(clipArtURL);
					}
				}
			}
		}
	}

	/**
	 * Load documents from the documents folder and filling the documents list
	 */
	private void loadDocuments() {
		String pictureExtension = "jpg";
		ServletContext servletContext = WebApplication.get().getServletContext();

		HttpServletRequest httpReq = (HttpServletRequest) ((WebRequest) RequestCycle.get().getRequest()).getContainerRequest();
		Url relative = Url.parse(httpReq.getContextPath());
		String basURL = RequestCycle.get().getUrlRenderer().renderFullUrl(relative);

		String clipArtFolderPath = servletContext.getRealPath("") + "/" + documentFolder;
		File folder = new File(clipArtFolderPath);

		for (final File fileEntry : folder.listFiles()) {
			if (!fileEntry.isDirectory()) {
				String extension = "";
				int i = fileEntry.getAbsolutePath().lastIndexOf('.');
				if (i > 0) {
					extension = fileEntry.getAbsolutePath().substring(i + 1);
				}
				if (pictureExtension.equals(extension)) {
					String docURL = basURL + "/" + documentFolder + "/" + fileEntry.getName();

					String documentName = fileEntry.getName().substring(0, fileEntry.getName().lastIndexOf('.'));

					if (-1 == documentName.lastIndexOf('_')) {
						ArrayList<String> docList = new ArrayList<String>();
						docList.add(docURL);
						docMap.put(documentName, docList);
					} else {
						String documentBaseName = documentName.substring(0, documentName.lastIndexOf('_'));
						if (docMap.containsKey(documentBaseName)) {
							docMap.get(documentBaseName).add(docURL);
						}
					}

				}
			}
		}
	}

	/**
	 * Check the equality of two elements on a whiteboard
	 * 
	 * @param element1
	 * @param element2
	 * @return
	 */
	private boolean isEqual(JSONObject element1, JSONObject element2) {
		for (String key : JSONObject.getNames(element1)) {
			Object value = null;
			try {
				value = element2.get(key);
				if (value == null) {
					return false;
				} else {
					if (value instanceof String) {
						if (!value.equals(element1.get(key))) {
							return false;
						}
					} else if (value instanceof Integer) {
						Integer x = (Integer) value;
						Integer y = (Integer) element1.get(key);
						if (x.intValue() != y.intValue()) {
							return false;
						}
					} else if (value instanceof Double) {
						Double x = (Double) value;
						Double y = (Double) element1.get(key);
						if (x.doubleValue() != y.doubleValue()) {
							return false;
						}
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
				return false;
			}

		}
		return true;
	}
}

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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.json.JSONArray;
import org.apache.wicket.ajax.json.JSONException;
import org.apache.wicket.ajax.json.JSONObject;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.head.PriorityHeaderItem;
import org.apache.wicket.protocol.ws.IWebSocketSettings;
import org.apache.wicket.protocol.ws.api.IWebSocketConnection;
import org.apache.wicket.protocol.ws.api.IWebSocketConnectionRegistry;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.whiteboard.elements.CircleGeneral;
import org.wicketstuff.whiteboard.elements.Circle_3p;
import org.wicketstuff.whiteboard.elements.Element;
import org.wicketstuff.whiteboard.elements.Element.Type;
import org.wicketstuff.whiteboard.elements.LineGeneral;
import org.wicketstuff.whiteboard.elements.Line_2p;
import org.wicketstuff.whiteboard.elements.PencilArrow;
import org.wicketstuff.whiteboard.elements.PencilCircle;
import org.wicketstuff.whiteboard.elements.PencilCurve;
import org.wicketstuff.whiteboard.elements.PencilFreeLine;
import org.wicketstuff.whiteboard.elements.PencilPointAtRect;
import org.wicketstuff.whiteboard.elements.PencilPointer;
import org.wicketstuff.whiteboard.elements.PencilRect;
import org.wicketstuff.whiteboard.elements.PencilUnderline;
import org.wicketstuff.whiteboard.elements.PointAtCircle;
import org.wicketstuff.whiteboard.elements.PointAtLine;
import org.wicketstuff.whiteboard.elements.PointFree;
import org.wicketstuff.whiteboard.elements.Point_2c;
import org.wicketstuff.whiteboard.elements.Point_2l;
import org.wicketstuff.whiteboard.elements.Point_lc;
import org.wicketstuff.whiteboard.elements.Segment;
import org.wicketstuff.whiteboard.elements.Text;
import org.wicketstuff.whiteboard.resource.GoogStyleSheetResourceReference;
import org.wicketstuff.whiteboard.resource.WhiteboardJavaScriptResourceReference;
import org.wicketstuff.whiteboard.resource.WhiteboardStyleSheetResourceReference;
import org.wicketstuff.whiteboard.settings.IWhiteboardLibrarySettings;

public class WhiteboardBehavior extends AbstractDefaultAjaxBehavior {
	private static final Logger log = LoggerFactory.getLogger(WhiteboardBehavior.class);
	private static final long serialVersionUID = 1L;
	private String whiteboardId;
	private static Map<Integer, Element> elementMap = new ConcurrentHashMap<Integer, Element>();

	private static BlockingDeque<List<Element>> undoSnapshots = new LinkedBlockingDeque<List<Element>>(20);
	private static BlockingDeque<List<Boolean>> undoSnapshotCreationList = new LinkedBlockingDeque<List<Boolean>>(20);

	private List<Element> snapShot = new ArrayList<Element>();
	private List<Boolean> snapShotCreation = new ArrayList<Boolean>();
	private static DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");

	public WhiteboardBehavior(String whiteboardId) {
		super();
		this.whiteboardId = whiteboardId;
	}

	public WhiteboardBehavior(String whiteboardId, String whiteboardContent) {
		super();
		this.whiteboardId = whiteboardId;
		if (whiteboardContent != null && !whiteboardContent.equals("")) {
			try {
				JSONArray elementList = new JSONArray(whiteboardContent);

				for (int i = 0; i < elementList.length(); i++) {
					JSONObject jElement = (JSONObject) elementList.get(i);

					Element element = getElementObject(jElement);

					if (element != null) {
						elementMap.put(element.getId(), element);
					}
				}
			} catch (JSONException e) {
				log.error("Unexpected error while constructing WhiteboardBehavior", e);
			}
		}
	}

	protected void respond(final AjaxRequestTarget target) {

		RequestCycle cycle = RequestCycle.get();
		WebRequest webRequest = (WebRequest) cycle.getRequest();

		if (webRequest.getQueryParameters().getParameterNames().contains("editedElement")) {
			String editedElement = webRequest.getQueryParameters().getParameterValue("editedElement").toString();

			try {
				// Mapping JSON String to Objects and Adding to the Element List
				JSONObject jsonEditedElement = new JSONObject(editedElement);
				Element element = getElementObject(jsonEditedElement);

				if (elementMap.containsKey(element.getId()) && !elementMap.isEmpty()) {
					snapShot.add(elementMap.get(element.getId()));
					snapShotCreation.add(false);
				} else {
					snapShot.add(element);
					snapShotCreation.add(true);
				}

				if (!"PointFree".equals(element.getType())) {
					if (undoSnapshots.size() == 20) {
						undoSnapshots.pollFirst();
						undoSnapshotCreationList.pollFirst();
					}

					if ("PencilCurve".equals(element.getType())) {
						List<Element> lastElementSnapshot = undoSnapshots.getLast();
						Element lastSnapshotElement = lastElementSnapshot.get(lastElementSnapshot.size() - 1);

						if ((lastSnapshotElement instanceof PencilCurve)
								&& (lastSnapshotElement.getId() == element.getId())) {
							List<Boolean> lastCreationSnapshot = undoSnapshotCreationList.getLast();

							for (int i = 0; i < snapShot.size(); i++) {
								lastElementSnapshot.add(snapShot.get(i));
								lastCreationSnapshot.add(snapShotCreation.get(i));
							}
						} else {
							undoSnapshots.addLast(snapShot);
							undoSnapshotCreationList.addLast(snapShotCreation);
						}

					} else {
						undoSnapshots.addLast(snapShot);
						undoSnapshotCreationList.addLast(snapShotCreation);
					}

					snapShot.clear();
					snapShotCreation.clear();
				}

				// Synchronizing newly added element between whiteboards
				if (element != null) {
					elementMap.put(element.getId(), element);

					IWebSocketConnectionRegistry reg = IWebSocketSettings.Holder.get(Application.get())
							.getConnectionRegistry();
					for (IWebSocketConnection c : reg.getConnections(Application.get())) {
						try {
							JSONObject jsonObject = new JSONObject(editedElement);
							c.sendMessage(getAddElementMessage(jsonObject).toString());
						} catch (Exception e) {
							log.error("Unexpected error while sending message through the web socket", e);
						}
					}
				}

			} catch (JSONException e) {
				log.error("Unexpected error while editing element", e);
			}
		} else if (webRequest.getQueryParameters().getParameterNames().contains("undo")) {
			if (!undoSnapshots.isEmpty()) {
				List<Boolean> undoCreationList = undoSnapshotCreationList.pollLast();
				List<Element> undoElement = undoSnapshots.pollLast();

				String deleteList = "";
				JSONArray changeList = new JSONArray();

				IWebSocketConnectionRegistry reg = IWebSocketSettings.Holder.get(Application.get())
						.getConnectionRegistry();

				for (int i = 0; i < undoElement.size(); i++) {
					if (undoCreationList.get(i)) {
						elementMap.remove(undoElement.get(i).getId());
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
			}

		} else if (webRequest.getQueryParameters().getParameterNames().contains("eraseAll")) {
			elementMap.clear();
			IWebSocketConnectionRegistry reg = IWebSocketSettings.Holder.get(Application.get()).getConnectionRegistry();
			for (IWebSocketConnection c : reg.getConnections(Application.get())) {
				try {
					JSONArray jsonArray = new JSONArray();
					c.sendMessage(getWhiteboardMessage(jsonArray).toString());
				} catch (Exception e) {
					log.error("Unexpected error while sending message through the web socket", e);
				}
			}
		} else if (webRequest.getQueryParameters().getParameterNames().contains("save")) {
			JSONArray elementArray = new JSONArray();
			for (int elementID : elementMap.keySet()) {
				try {
					elementArray.put(elementMap.get(elementID).getJSON());
				} catch (JSONException e) {
					log.error("Unexpected error while getting JSON", e);
				}
			}
			File whiteboardFile = new File("Whiteboard_" + dateFormat.format(new Date()) + ".json");

			FileWriter writer = null;
			try {
				whiteboardFile.createNewFile();
				log.debug("Going to dump WB to file: " + whiteboardFile.getAbsolutePath());
				writer = new FileWriter(whiteboardFile);
				writer.write(elementArray.toString());
				writer.flush();
			} catch (IOException e) {
				log.debug("Unexpected error during dumping WB to file ", e);
			} finally {
				if (writer != null) {
					try {
						writer.close();
					} catch (IOException e) {
						log.debug("Unexpected error during closing WB file ", e);
					}
				}
			}
		} else if (webRequest.getQueryParameters().getParameterNames().contains("clipArt")) {
			IWebSocketConnectionRegistry reg = IWebSocketSettings.Holder.get(Application.get()).getConnectionRegistry();
			for (IWebSocketConnection c : reg.getConnections(Application.get())) {
				try {
					JSONArray jsonArray = new JSONArray();
					jsonArray.put("http://icons.iconarchive.com/icons/femfoyou/angry-birds/64/angry-bird-icon.png");
					jsonArray
							.put("http://icons.iconarchive.com/icons/femfoyou/angry-birds/64/angry-bird-yellow-icon.png");
					c.sendMessage(getClipArtListMessage(jsonArray).toString());
				} catch (Exception e) {
					log.error("Unexpected error while sending message through the web socket", e);
				}
			}
		}
	}

	private JSONObject getAddElementMessage(JSONObject element) throws JSONException {
		return new JSONObject().put("type", "addElement").put("json", element);
	}

	private JSONObject getUndoMessage(JSONArray changeList, String deleteList) throws JSONException {
		return new JSONObject().put("type", "undoList").put("changeList", changeList).put("deleteList", deleteList);
	}

	private JSONObject getWhiteboardMessage(JSONArray array) throws JSONException {
		return new JSONObject().put("type", "parseWB").put("json", array);
	}

	private JSONObject getClipArtListMessage(JSONArray array) throws JSONException {
		return new JSONObject().put("type", "clipArtList").put("json", array);
	}

	public void renderHead(Component component, IHeaderResponse response) {
		super.renderHead(component, response);

		initReferences(response);
		String callbackUrl = getCallbackUrl().toString();
		String whiteboardInitializeScript = "" + "callbackUrl='" + callbackUrl + "';\n"
				+ "whiteboard = bay.whiteboard.Create();\n" + "elementCollection=whiteboard.getMainCollection();\n"
				+ "whiteboard.getMainCollection().onChange = function(element){\n"
				+ "changedElement=this.getJson(element);\n" + "Wicket.Ajax.get({u:'" + callbackUrl
				+ "',ep:{editedElement:changedElement}});\n};\n" + "whiteboard.render(document.getElementById('"
				+ whiteboardId + "'));\n" + "whiteboard.setBoundaries(0, 0, 0, 0);\n";

		// Clearing the whiteboard for first client
		// IWebSocketConnectionRegistry reg = IWebSocketSettings.Holder.get(Application.get()).getConnectionRegistry();
		// if(reg.getConnections(Application.get()).size()==0){
		// elementMap.clear();
		// }

		// Loading existing content for clients join after first one
		if (!elementMap.isEmpty()) {
			Map<Integer, Element> sortedElementList = new TreeMap<Integer, Element>(elementMap);
			JSONArray jsonArray = new JSONArray();
			for (Element e : sortedElementList.values()) {
				try {
					jsonArray.put(e.getJSON());
				} catch (JSONException e1) {
					log.error("Unexpected error while getting JSON", e);
				}
			}
			whiteboardInitializeScript += "elementCollection.parseJson('" + jsonArray.toString() + "');";
		}

		response.render(OnDomReadyHeaderItem.forScript(whiteboardInitializeScript));
	}

	private void initReferences(IHeaderResponse response) {
		IWhiteboardLibrarySettings settings = getLibrarySettings();

		// Whiteboard.css
		if (settings != null && settings.getWhiteboardStyleSheetReference() != null) {
			response.render(new PriorityHeaderItem(CssHeaderItem.forReference(settings
					.getWhiteboardStyleSheetReference())));
		} else {
			response.render(new PriorityHeaderItem(CssHeaderItem.forReference(WhiteboardStyleSheetResourceReference
					.get())));
		}

		// Goog.css
		if (settings != null && settings.getGoogStyleSheetReference() != null) {
			response.render(new PriorityHeaderItem(CssHeaderItem.forReference(settings.getGoogStyleSheetReference())));
		} else {
			response.render(new PriorityHeaderItem(CssHeaderItem.forReference(GoogStyleSheetResourceReference.get())));
		}

		// Whiteboard.js
		if (settings != null && settings.getWhiteboardJavaScriptReference() != null) {
			response.render(new PriorityHeaderItem(JavaScriptHeaderItem.forReference(settings
					.getWhiteboardJavaScriptReference())));
		} else {
			response.render(new PriorityHeaderItem(JavaScriptHeaderItem
					.forReference(WhiteboardJavaScriptResourceReference.get())));
		}

	}

	private static IWhiteboardLibrarySettings getLibrarySettings() {
		if (Application.exists()
				&& (Application.get().getJavaScriptLibrarySettings() instanceof IWhiteboardLibrarySettings)) {
			return (IWhiteboardLibrarySettings) Application.get().getJavaScriptLibrarySettings();
		}

		return null;
	}

	public Map<Integer, Element> getElementMap() {
		return elementMap;
	}

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
			default:
				break;

		}

		return element;
	}
}

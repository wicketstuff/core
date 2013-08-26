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

import org.wicketstuff.whiteboard.elements.*;
import org.wicketstuff.whiteboard.resource.GoogStyleSheetResourceReference;
import org.wicketstuff.whiteboard.resource.WhiteboardJavaScriptResourceReference;
import org.wicketstuff.whiteboard.resource.WhiteboardStyleSheetResourceReference;
import org.wicketstuff.whiteboard.settings.IWhiteboardLibrarySettings;
import com.sun.org.apache.xerces.internal.impl.dv.DatatypeException;
import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.json.JSONArray;
import org.apache.wicket.ajax.json.JSONException;
import org.apache.wicket.ajax.json.JSONObject;
import org.apache.wicket.markup.head.*;
import org.apache.wicket.protocol.ws.IWebSocketSettings;
import org.apache.wicket.protocol.ws.api.IWebSocketConnection;
import org.apache.wicket.protocol.ws.api.IWebSocketConnectionRegistry;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebRequest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

public class WhiteboardBehavior extends AbstractDefaultAjaxBehavior{

	private String whiteboardId;
	private static ConcurrentHashMap<Integer,Element> elementMap=new ConcurrentHashMap<Integer,Element>();

	private static LinkedBlockingDeque<ArrayList> undoSnapshots=new LinkedBlockingDeque<ArrayList>(20);
	private static LinkedBlockingDeque<ArrayList> undoSnapshotCreationList=new LinkedBlockingDeque<ArrayList>(20);

	private ArrayList<Element> snapShot=null;
	private ArrayList<Boolean> snapShotCreation=null;

	public WhiteboardBehavior(String whiteboardId){
		super();
		this.whiteboardId=whiteboardId;
	}

	public WhiteboardBehavior(String whiteboardId,String whiteboardContent){
		super();
		this.whiteboardId=whiteboardId;
		if(whiteboardContent!=null&&!whiteboardContent.equals("")) {
			try{
				JSONArray elementList=new JSONArray(whiteboardContent) ;

				for(int i=0;i<elementList.length();i++){
					JSONObject jElement=(JSONObject)elementList.get(i);

					Element element=getElementObject(jElement);

					if(element!=null){
						elementMap.put(element.getId(),element);
					}
				}
			}catch(JSONException e){
				e.printStackTrace();
			}
		}
	}

	protected void respond(final AjaxRequestTarget target){

		RequestCycle cycle = RequestCycle.get();
		WebRequest webRequest = (WebRequest) cycle.getRequest();

		if(webRequest.getQueryParameters().getParameterValue("editedElement").toString()!=null){
			String editedElement = webRequest.getQueryParameters().getParameterValue("editedElement").toString();

			try{

				if(snapShot==null&&snapShotCreation==null){
					snapShot=new ArrayList<Element>();
					snapShotCreation=new ArrayList<Boolean>();
				}

				//Mapping JSON String to Objects and Adding to the Element List
				JSONObject jsonEditedElement=new JSONObject(editedElement);
				Element element=getElementObject(jsonEditedElement);

				if(elementMap.containsKey(element.getId())&&!elementMap.isEmpty()){
					snapShot.add(elementMap.get(element.getId()));
					snapShotCreation.add(false);
				}
				else{
					snapShot.add(element);
					snapShotCreation.add(true);
				}

				if(!"PointFree".equals(element.getType())){
					if(undoSnapshots.size()==20){
						undoSnapshots.pollFirst();
						undoSnapshotCreationList.pollFirst();
					}

					if("PencilCurve".equals(element.getType())) {
						ArrayList<Element> lastElementSnapshot=undoSnapshots.getLast();
						Element lastSnapshotElement=lastElementSnapshot.get(lastElementSnapshot.size()-1);

						if((lastSnapshotElement instanceof PencilCurve )&& (lastSnapshotElement.getId()==element.getId())){
							ArrayList<Boolean> lastCreationSnapshot=undoSnapshotCreationList.getLast();

							for(int i=0;i<snapShot.size();i++){
								lastElementSnapshot.add(snapShot.get(i));
								lastCreationSnapshot.add(snapShotCreation.get(i));
							}
						}
						else{
							undoSnapshots.addLast(snapShot);
							undoSnapshotCreationList.addLast(snapShotCreation);
						}

					}  else{
						undoSnapshots.addLast(snapShot);
						undoSnapshotCreationList.addLast(snapShotCreation);
					}

					snapShot=null;
					snapShotCreation=null;
				}

				// Synchronizing newly added element between whiteboards
				if(element!=null){
					elementMap.put(element.getId(),element);

					IWebSocketConnectionRegistry reg = IWebSocketSettings.Holder.get(Application.get()).getConnectionRegistry();
					for (IWebSocketConnection c : reg.getConnections(Application.get())) {
						try {
							JSONObject jsonObject=new JSONObject(editedElement);
							c.sendMessage(getAddElementMessage(jsonObject).toString());
						} catch(Exception e) {
							e.printStackTrace();
						}
					}
				}

			}catch(JSONException e){
				e.printStackTrace();
			}
		}
		else if(webRequest.getQueryParameters().getParameterValue("undo").toString()!=null){
			if(!undoSnapshots.isEmpty()){
				ArrayList<Boolean> undoCreationList=undoSnapshotCreationList.pollLast();
				ArrayList<Element> undoElement=undoSnapshots.pollLast();

				String deleteList="";
				JSONArray changeList=new JSONArray();

				IWebSocketConnectionRegistry reg = IWebSocketSettings.Holder.get(Application.get()).getConnectionRegistry();

				for(int i=0;i<undoElement.size();i++){
					if(undoCreationList.get(i)){
						elementMap.remove(undoElement.get(i).getId());
						if("".equals(deleteList)){
							deleteList= ""+undoElement.get(i).getId();
						}
						else{
							deleteList+= ","+undoElement.get(i).getId();
						}
					}
					else{
						elementMap.put(undoElement.get(i).getId(),undoElement.get(i));
						changeList.put(undoElement.get(i).getJSON());
					}
				}

				for (IWebSocketConnection c : reg.getConnections(Application.get())) {
					try {
						c.sendMessage(getUndoMessage(changeList,deleteList).toString());
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			}


		}
		else if(webRequest.getQueryParameters().getParameterValue("eraseAll").toString()!=null){
			elementMap.clear();
			IWebSocketConnectionRegistry reg = IWebSocketSettings.Holder.get(Application.get()).getConnectionRegistry();
			for (IWebSocketConnection c : reg.getConnections(Application.get())) {
				try {
					JSONArray jsonArray=new JSONArray();
					c.sendMessage(getWhiteboardMessage(jsonArray).toString());
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		else if(webRequest.getQueryParameters().getParameterValue("save").toString()!=null){
			JSONArray elementArray= new JSONArray();
			for(int elementID:elementMap.keySet()){
				elementArray.put(elementMap.get(elementID).getJSON());
			}
			DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
			Date date = new Date();
			File whiteboardFile = new File("Whiteboard_"+dateFormat.format(date)+".json");

			FileWriter writer=null;
			try{
				whiteboardFile.createNewFile();
				System.out.println(whiteboardFile.getAbsolutePath());
				writer=new FileWriter(whiteboardFile);
				writer.write(elementArray.toString());
				writer.flush();
			}catch(IOException e){
				e.printStackTrace();
			}finally{
				if(writer != null){
					try{
						writer.close();
					}catch(IOException e){
						e.printStackTrace();
					}
				}
			}

		}

		else if(webRequest.getQueryParameters().getParameterValue("clipArt").toString()!=null){

			IWebSocketConnectionRegistry reg = IWebSocketSettings.Holder.get(Application.get()).getConnectionRegistry();
			for (IWebSocketConnection c : reg.getConnections(Application.get())) {
				try {
					JSONArray jsonArray=new JSONArray();
					jsonArray.put("http://icons.iconarchive.com/icons/femfoyou/angry-birds/64/angry-bird-icon.png");
					jsonArray.put("http://icons.iconarchive.com/icons/femfoyou/angry-birds/64/angry-bird-yellow-icon.png");
					c.sendMessage(getClipArtListMessage(jsonArray).toString());
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private JSONObject getAddElementMessage(JSONObject element) throws JSONException {
		return new JSONObject()
				.put("type", "addElement")
				.put("json", element);
	}

	private JSONObject getDeleteElementMessage(int element) throws JSONException {
		return new JSONObject()
				.put("type", "deleteElement")
				.put("elementID", element);
	}

	private JSONObject getUndoMessage(JSONArray changeList,String deleteList) throws JSONException {
		return new JSONObject()
				.put("type", "undoList")
				.put("changeList", changeList)
				.put("deleteList", deleteList);
	}

	private JSONObject getWhiteboardMessage(JSONArray array) throws JSONException {
		return new JSONObject()
				.put("type", "parseWB")
				.put("json", array);
	}

	private JSONObject getClipArtListMessage(JSONArray array) throws JSONException {
		return new JSONObject()
				.put("type", "clipArtList")
				.put("json", array);
	}

	public void renderHead(Component component, IHeaderResponse response) {
		super.renderHead(component,response);

		initReferences(response);
		String callbackUrl=getCallbackUrl().toString();
		String whiteboardInitializeScript="" +
				"callbackUrl='"+callbackUrl+"';\n" +
				"whiteboard = bay.whiteboard.Create();\n" +
				"elementCollection=whiteboard.getMainCollection();\n"+
				"whiteboard.getMainCollection().onChange = function(element){\n"+
				"changedElement=this.getJson(element);\n"+
				"Wicket.Ajax.get({u:'"+callbackUrl+"',ep:{editedElement:changedElement}});\n};\n"+
				"whiteboard.render(document.getElementById('"+whiteboardId+"'));\n"+
				"whiteboard.setBoundaries(0, 0, 0, 0);\n";

		//Clearing the whiteboard for first client
		IWebSocketConnectionRegistry reg = IWebSocketSettings.Holder.get(Application.get()).getConnectionRegistry();
//		if(reg.getConnections(Application.get()).size()==0){
//			elementMap.clear();
//		}

		//Loading existing content for clients join after first one
		if(!elementMap.isEmpty()){
			Map<Integer,Element> sortedElementList = new TreeMap<Integer,Element>(elementMap);
			JSONArray jsonArray=new JSONArray();
			for (Element e : sortedElementList.values()) {
				jsonArray.put(e.getJSON());
			}
			whiteboardInitializeScript+="elementCollection.parseJson('"+jsonArray.toString()+"');";
		}

		response.render(OnDomReadyHeaderItem.forScript(whiteboardInitializeScript));
	}


	private void initReferences(IHeaderResponse response){
		IWhiteboardLibrarySettings settings = getLibrarySettings();

//Whiteboard.css
		if (settings != null && settings.getWhiteboardStyleSheetReference() != null)
		{
			response.render(new PriorityHeaderItem(CssHeaderItem.forReference(settings.getWhiteboardStyleSheetReference())));
		}
		else
		{
			response.render(new PriorityHeaderItem(CssHeaderItem.forReference(WhiteboardStyleSheetResourceReference.get())));
		}

//Goog.css
		if (settings != null && settings.getGoogStyleSheetReference() != null)
		{
			response.render(new PriorityHeaderItem(CssHeaderItem.forReference(settings.getGoogStyleSheetReference())));
		}
		else
		{
			response.render(new PriorityHeaderItem(CssHeaderItem.forReference(GoogStyleSheetResourceReference.get())));
		}


//Whiteboard.js
		if (settings != null && settings.getWhiteboardJavaScriptReference() != null)
		{
			response.render(new PriorityHeaderItem(JavaScriptHeaderItem.forReference(settings.getWhiteboardJavaScriptReference())));
		}
		else
		{
			response.render(new PriorityHeaderItem(JavaScriptHeaderItem.forReference(WhiteboardJavaScriptResourceReference.get())));
		}

	}

	private static IWhiteboardLibrarySettings getLibrarySettings()
	{
		if (Application.exists() && (Application.get().getJavaScriptLibrarySettings() instanceof IWhiteboardLibrarySettings))
		{
			return (IWhiteboardLibrarySettings) Application.get().getJavaScriptLibrarySettings();
		}

		return null;
	}

	public ConcurrentHashMap<Integer,Element> getElementMap(){
		return elementMap;
	}

	public void setElementMap(ConcurrentHashMap<Integer,Element> elementMap){
		this.elementMap=elementMap;
	}

	private Element getElementObject(JSONObject jsonObject){
		Element element=null;

		try{
			String elementType=(String)jsonObject.get("type");
			if("PointFree".equals(elementType)){
				element=new PointFree(jsonObject);
			}else if("PencilCurve".equals(elementType)){
				element=new PencilCurve(jsonObject);
			}else if("PencilFreeLine".equals(elementType)){
				element=new PencilFreeLine(jsonObject);
			}else if("PencilRect".equals(elementType)){
				element=new PencilRect(jsonObject);
			}else if("PencilPointAtRect".equals(elementType)){
				element=new PencilPointAtRect(jsonObject);
			}else if("PencilCircle".equals(elementType)){
				element=new PencilCircle(jsonObject);
			}else if("Text".equals(elementType)){
				element=new Text(jsonObject);
			}else if("PointAtLine".equals(elementType)){
				element=new PointAtLine(jsonObject);
			}else if("PointAtCircle".equals(elementType)){
				element=new PointAtCircle(jsonObject);
			}else if("Point_2l".equals(elementType)){
				element=new Point_2l(jsonObject);
			}else if("Point_2c".equals(elementType)){
				element=new Point_2c(jsonObject);
			}else if("Point_lc".equals(elementType)){
				element=new Point_lc(jsonObject);
			}else if("LineGeneral".equals(elementType)){
				element=new LineGeneral(jsonObject);
			}else if("Line_2p".equals(elementType)){
				element=new Line_2p(jsonObject);
			}else if("Segment".equals(elementType)){
				element=new Segment(jsonObject);
			}else if("CircleGeneral".equals(elementType)){
				element=new CircleGeneral(jsonObject);
			}else if("Circle_3p".equals(elementType)){
				element=new Circle_3p(jsonObject);
			}else if("PencilArrow".equals(elementType)){
				element=new PencilArrow(jsonObject);
			}else if("PencilUnderline".equals(elementType)){
				element=new PencilUnderline(jsonObject);
			}else if("PencilPointer".equals(elementType)){
				element=new PencilPointer(jsonObject);
			}
		}catch(JSONException e){
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}

		return element;
	}
}

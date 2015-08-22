package org.wicketstuff.yui.markup.html.dragdrop;

import java.util.ArrayList;
import java.util.List;

public class DragableSlotList {
	private List<DragableSlot> dragableList;
	private String id;
	private String slotId;
	private String imgId;
	
	public DragableSlotList(String id, String slotId, String imgId){
		this.id= id;
		this.slotId= slotId;
		this.imgId= imgId;
		this.dragableList= new ArrayList<DragableSlot>();
	}
	
	public boolean add(DragableSlot dragableSlot){
		return dragableList.add(dragableSlot);
	}
	
	public boolean remove(DragableSlot dragableSlot){
		return dragableList.remove(dragableSlot);
	}
	
	public int getSize(){
			return dragableList.size();			
	}
	
	public DragableSlot getDragableSlot(int index){
		return (DragableSlot)dragableList.get(index);
	}

	/**
	 * @return the dragableList
	 */
	public List<DragableSlot> getDragableList() {
		return dragableList;
	}

	/**
	 * @param dragableList the dragableList to set
	 */
	public void setDragableList(List<DragableSlot> dragableList) {
		this.dragableList = dragableList;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the imgId
	 */
	public String getImgId() {
		return imgId;
	}

	/**
	 * @param imgId the imgId to set
	 */
	public void setImgId(String imgId) {
		this.imgId = imgId;
	}

	/**
	 * @return the slotId
	 */
	public String getSlotId() {
		return slotId;
	}

	/**
	 * @param slotId the slotId to set
	 */
	public void setSlotId(String slotId) {
		this.slotId = slotId;
	}
	
}

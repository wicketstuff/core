package org.wicketstuff.yui.markup.html.dragdrop;

import java.util.ArrayList;
import java.util.List;

public class TargetSlotList {
	private List<TargetSlot> targetList; 
	private String id;
	private String slotId;
	
	public TargetSlotList(String id, String slotId){
		this.id= id;
		this.slotId= slotId;
		this.targetList= new ArrayList<TargetSlot>();
	}
	
	public boolean add(TargetSlot targetSlot){
		return targetList.add(targetSlot);
	}
	
	public boolean remove(TargetSlot targetSlot){
		return targetList.remove(targetSlot);
	}
	
	public int getSize(){
			return targetList.size();			
	}
	
	public TargetSlot getTargetSlot(int index){
		return (TargetSlot)targetList.get(index);
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
	 * @return the targetList
	 */
	public List<TargetSlot> getTargetList() {
		return targetList;
	}

	/**
	 * @param targetList the targetList to set
	 */
	public void setTargetList(List<TargetSlot> targetList) {
		this.targetList = targetList;
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

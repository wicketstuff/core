package org.wicketstuff.yui.examples.pages;

import org.apache.wicket.markup.html.form.TextField;
import org.wicketstuff.yui.YuiImage;
import org.wicketstuff.yui.examples.WicketExamplePage;
import org.wicketstuff.yui.markup.html.dragdrop.DragDropGroup;
import org.wicketstuff.yui.markup.html.dragdrop.DragDropSettings;
import org.wicketstuff.yui.markup.html.dragdrop.DragableSlot;
import org.wicketstuff.yui.markup.html.dragdrop.DragableSlotList;
import org.wicketstuff.yui.markup.html.dragdrop.TargetSlot;
import org.wicketstuff.yui.markup.html.dragdrop.TargetSlotList;

/**
 * This class demostrates how you can use the org.wicketstuff.yui.markup.html.dragDrop package
 * to selection and sorting of images. 
 * <p>
 * 
 * @author cptan
 *
 */
public class DragDropPage extends WicketExamplePage {
	
	/**
	 * Defines a DragDropPage object
	 *
	 */
	public DragDropPage() {
	
		//Define the dragable slot with the group name, should i allow the user to define the group name?
		// the group can be define by creating DragableSlotList with default name Dragable 
		YuiImage dSlotA = new YuiImage("style/yellow.bmp", "dragA");
		YuiImage dSlotB = new YuiImage("style/yellow.bmp", "dragB");
		YuiImage dSlotC = new YuiImage("style/yellow.bmp", "dragC");
		
		//Define the image that is used to be swapped between the target slot and the dragable slot
		YuiImage imageA = new YuiImage("style/blue.bmp", "blue");
		YuiImage imageB = new YuiImage("style/green.bmp", "green");
		YuiImage imageC = new YuiImage("style/pink.bmp", "pink");
		
		//Define the position of the dragable slot
		DragableSlot dragableA = new DragableSlot(dSlotA, imageA, 350, 164);
		DragableSlot dragableB = new DragableSlot(dSlotB, imageB, 350, 238);
		DragableSlot dragableC = new DragableSlot(dSlotC, imageC, 350, 312);
		
		//Define the target slot with the group name, should i allow the user to define the group name?
		//the group can be define by creating TargetSlotList with default group name Target
		YuiImage tSlotA= new YuiImage("style/yellow.bmp", "targetA");
		YuiImage tSlotB= new YuiImage("style/yellow.bmp", "targetB");
		YuiImage tSlotC= new YuiImage("style/yellow.bmp", "targetC");
		
		//Define the position of the target slot
		TargetSlot targetA = new TargetSlot(tSlotA, 110, 164);
		TargetSlot targetB = new TargetSlot(tSlotB, 110, 238);
		TargetSlot targetC = new TargetSlot(tSlotC, 110, 312);

		//Store the dragable slots in a List
		//May need to create a DragableSlotList to allow the user to specify the wicket id
		DragableSlotList dList =  new DragableSlotList("dragableListView", "dragableSlot", "dragableImg");
		dList.add(dragableA);
		dList.add(dragableB);
		dList.add(dragableC);
		
		//Store the target slots in a List
		//May need to create a TargetSlotList to allow the user to specify the wicket id
		TargetSlotList tList= new TargetSlotList("targetListView", "targetSlot"); 
		tList.add(targetA);
		tList.add(targetB);
		tList.add(targetC);
		
		//For each DragDrop, there are dragable and target slots lists, define the lists as a settings
		//Set the style of the images in the lists
		DragDropSettings settings= DragDropSettings.getDefault(dList, tList);
		
		//Use to obtain the order of the elements in the dragable slots
		TextField dragableElement = new TextField("dragableElement");
		
		//Use to obtain the order of the elements in the target slots
		TextField targetElement = new TextField("targetElement");
		
		//Getting the  settings which consists of the dragable and target lists,
		//the textfields to retrieve the order of the elements in the dragable and target slots
		DragDropGroup slotGroup = new DragDropGroup("dragDrop", settings, dragableElement, targetElement);
		
		//Add the group
		add(slotGroup);
	}
}
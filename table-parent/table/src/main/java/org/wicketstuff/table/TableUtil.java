package org.wicketstuff.table;

import javax.swing.DefaultListSelectionModel;
import javax.swing.ListSelectionModel;

public class TableUtil {

    public static ListSelectionModel createSingleSelectionModel() {
	ListSelectionModel selectionModel = new DefaultListSelectionModel();
	selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	return selectionModel;
    }

}

package org.wicketstuff.table.sorter;

import java.io.Serializable;

import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class SerializableTableRowSorter extends TableRowSorter implements Serializable {

    public SerializableTableRowSorter(TableModel tableModel) {
	super(tableModel);
	System.out.println("criando SerializableTableRowSorter ");
    }
}

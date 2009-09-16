package org.wicketstuff.table;

import java.io.Serializable;
import java.util.Arrays;

import javax.swing.table.TableModel;

import org.apache.wicket.model.Model;

/**
 * Just a adapter class to return a list, with the size equals to the rows count
 * on table model .
 * 
 * @author Pedro Henrique Oliveira dos Santos
 * 
 */
public class ListModelAdapter extends Model {
    private TableModel tableModel;

    public ListModelAdapter(TableModel tableModel) {
	this.tableModel = tableModel;
    }

    @Override
    public Serializable getObject() {
	return (Serializable) Arrays.asList(new Object[tableModel.getRowCount()]);
    }
}
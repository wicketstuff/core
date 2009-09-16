package org.wicketstuff.table;

import java.io.Serializable;
import java.util.Arrays;

import javax.swing.table.TableModel;

import org.apache.wicket.model.Model;

/**
 * Just a adapter class to return a list, with the size equals to the columns
 * count on table model .
 * 
 * @author Pedro Henrique Oliveira dos Santos
 * 
 */
public class ColumnsModelAdapter extends Model {
    private TableModel tableModel;

    public ColumnsModelAdapter(TableModel tableModel) {
	this.tableModel = tableModel;
    }

    @Override
    public Serializable getObject() {
	return (Serializable) Arrays.asList(new Object[tableModel.getColumnCount()]);
    }
}
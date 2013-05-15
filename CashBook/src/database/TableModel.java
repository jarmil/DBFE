package database;

import database.IDatabase;
import database.IRecord;
import database.Settings;
import date.SimpleDate;
import gui.dialogs.AddDialog;
import gui.dialogs.ChangeDialog;
import gui.dialogs.DeleteDialog;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TableModel extends AbstractTableModel implements IModel {

	private static final long serialVersionUID = -3634399011027604785L;

	private IDatabase database;
	private List<IRecord> tableList;
	private int rowCount;

	public TableModel(IDatabase database) {
		if (database == null) {
			throw new NullPointerException("Nebyla p�ipojena datab�ze");
		} else {

			this.database = database;
			this.tableList = database.getDatabaseList();
			rowCount = this.tableList.size();

		}
	}

	public boolean isSelected() {
		return database.isSelected();
	}

	@Override
	public boolean isChanged() {
		// TODO Auto-generated method stub
		return database.isChanged();
	}

	@Override
	public int getRowCount() {
		return rowCount;
	}

	@Override
	public int getColumnCount() {
		return database.getColumnCount();
	}

	@Override
	public String getValueAt(int rowIndex, int columnIndex) {
		return tableList.get(rowIndex).getRecord()[columnIndex];
	}

	public IRecord getRow(int index) {
		return tableList.get(index);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case (0):
			return SimpleDate.class;
		case (1):
			return String.class;
		case (2):
			return Double.class;
		default:
			return String.class;

		}
	}

	@Override
	public String getColumnName(int column) {
		return ((String[])Settings.getSetting().get("tableHeaderTitles"))[column];
	}

	public void setValueAt(int rowIndex, int columnIndex, String value) {
		throw new UnsupportedOperationException("Tato metoda byla zakázána");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see main.IModel#addRow(database.IRecord)
	 */
	@Override
	public void addRow(IRecord r) {
		if (database.isSelected()) {
			tableList.add(r);
			fireTableRowsInserted(rowCount - 1 , rowCount - 1);
		}else{
		int i = database.addRecord(r);
		rowCount++;
		fireTableRowsInserted(i , rowCount - 1);
		}

	}

	@Override
	public void deleteRow(int index, IRecord record) {
		if (database.isSelected()) {
			tableList.remove(index);
			database.deleteRecord(record.getIndex());

		} else {
			database.deleteRecord(index);
		}
		rowCount--;
		fireTableRowsDeleted(index, index);
	}

	@Override
	public void changeRow(int index, IRecord record) {
		tableList.set(index, record);
		if (database.isSelected()) {
			database.changeRecord(record.getIndex(), record);
		} else {
			database.changeRecord(index, record);
		}
		fireTableRowsUpdated(index, index);
	}

	public void showAddDialog() {
		new AddDialog(this);

	}

	public void showDeleteDialog(IRecord record, int row) {
		new ChangeDialog(this, record, row);

	}

	public void showChangeDialog(IRecord record, int row) {
		new DeleteDialog(this, record, row);

	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub
		database.save();
		database = null;
		tableList = null;
	}

	@Override
	public List<IRecord> selectByParams(SimpleDate fromDate, SimpleDate toDate,
			String type, String selectedType, Double min, Double max,
			boolean state) {
		tableList = database.selectByParams(fromDate, toDate, type,
				selectedType, min, max, state);
		rowCount = tableList.size();
		fireTableDataChanged();
		return null;
	}

	@Override
	public void reset() {

		tableList = database.getDatabaseList();
		fireTableDataChanged();

	}

}

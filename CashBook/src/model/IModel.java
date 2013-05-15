package model;

import java.util.List;

import javax.swing.table.TableModel;

import database.IRecord;
import date.SimpleDate;

public interface IModel extends TableModel {

	public void addRow(IRecord record);

	public void deleteRow(int index, IRecord record);

	public void changeRow(int index, IRecord record);

	public IRecord getRow(int index);

	public boolean isChanged();

	public void exit();

	public List<IRecord> selectByParams(SimpleDate fromDate, SimpleDate toDate,
			String type, String selectedType, Double min, Double max,
			boolean state);

	public void reset();

}
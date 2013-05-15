package models;

import java.util.List;

import date.SimpleDate;


public interface IDatabase {

	public boolean isSelected();

	public void load();

	public void save();

	public IRecord getRecord(int index);

	public void addRecord(IRecord record);

	public void changeRecord(int index, IRecord newRecord);

	public int getRecordCount();

	public int getColumnCount();
	
	public List<IRecord> getDatabaseList();
	
	public void deleteRecord(int index);
	
	public boolean isChanged();
	
	public List<IRecord> selectByParams(SimpleDate fromDate, SimpleDate toDate,String type,String selectedType,Double min, Double max,boolean state);
	
	public List<IRecord> selectDatabaseByParams(SimpleDate fromDate, SimpleDate toDate,String type,String selectedType,Double min, Double max,boolean state);

}
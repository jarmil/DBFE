package models;

import date.SimpleDate;

import java.util.List;


public interface IModel {

	public void addRow(IRecord record);

	public void deleteRow(int index,IRecord record);

	public void changeRow(int index,IRecord record);
	
	public IRecord getRow(int index);
	
	public boolean isChanged();
	
	public void exit();
	
	public List<IRecord> selectByParams(SimpleDate fromDate, SimpleDate toDate,String type,String selectedType,Double min, Double max,boolean state);
	
	public void reset();

}
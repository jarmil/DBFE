package database;

import java.util.Comparator;

import date.SimpleDate;

 
public interface IRecord extends Comparator<IRecord>,Comparable<IRecord> {

	public int getIndex();

	public void setIndex(int index);

	public String[] getRecord();

	public SimpleDate getDate();

	public void setDate(SimpleDate date);

	public String getType();

	public void setType(String type);

	public double getMoney();

	public void setMoney(double money);

	public String getDescription();

	public void setDescription(String description);
	
	public boolean equals(IRecord record);

	public int compareTo(IRecord o); 
	
	public int compareToByType(IRecord o); 
	
	public int compareToByMoney(IRecord o);
}
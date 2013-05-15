package date;

import java.util.Comparator;
import java.util.Date;

public class SimpleDate implements Comparable<SimpleDate>, Comparator<SimpleDate> {
	private int year;
	private int month;
	private int day;

	public SimpleDate(int d, int m, int y) {
		// TODO Auto-generated constructor stub
		year = y;
		month = m;
		day = d;
	}
	 
	public static SimpleDate getDate(String date){
		if(date == null || date.equals(""))
			return null;
		return new SimpleDate(date);
	}
	
	@Deprecated
	public static SimpleDate getDate(Date date){
		if(date == null)
			return null;
		return new SimpleDate(date);
	}

	private SimpleDate(String date) {
		String[] temp = date.split("\\.");
		day = Integer.parseInt(temp[0]);
		month = Integer.parseInt(temp[1]);
		year = Integer.parseInt(temp[2]);

	}

	@Deprecated
	private SimpleDate(Date date) {
		day = date.getDate();
		month = date.getMonth() + 1;
		year = date.getYear() + 1900;

	}

	public static boolean checkDate(String s) {
		String[] temp = s.split("\\.");
		int tmp;
		if (temp.length < 3 || temp.length > 3)
			return false;

		if (temp[2].length() < 4 || temp[2].length() > 4)
			return false;

		try {
			tmp = Integer.parseInt(temp[0]);
			if (tmp < 1 || tmp > 31)
				return false;

			tmp = Integer.parseInt(temp[1]);
			if (tmp < 1 || tmp > 12)
				return false;
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public boolean equals(SimpleDate date) {
		if (year > date.year)
			return true;
		if ((year >= date.year) && (month > date.month)) {
			return true;
		}

		if ((year >= date.year) && (month >= date.month) && (day > date.day)) {
			return true;
		}

		if ((year == date.year) && (month == date.month) && (day == date.day)) {
			return true;
		}

		return false;
	}

	public int compare(SimpleDate dt1, SimpleDate dt2) {
		{

			if ((dt1.year == dt2.year) && (dt1.month == dt2.month)
					&& (dt1.day == dt2.day))
				return 0;
		}
		{
			if (dt1.year > dt2.year)
				return 1;
			if ((dt1.year >= dt2.year) && (dt1.month > dt2.month))
				return 1;
			if ((dt1.year >= dt2.year) && (dt1.month >= dt2.month)
					&& (dt1.day > dt2.day))
				return 1;
		}
		return -1;

	}
	
	@Override
	public int compareTo(SimpleDate dt) {
		if ((year == dt.year) && (month == dt.month)
				&& (day == dt.day))
			return 0;
	
	
		if (year > dt.year)
			return 1;
		if ((year >= dt.year) && (month > dt.month))
			return 1;
		if ((year >= dt.year) && (month >= dt.month)
				&& (day > dt.day))
			return 1;
	
	return -1;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return day + "." + month + "." + year;
	}



}

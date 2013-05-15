package database;


import date.SimpleDate;

public class Record implements IRecord {

	private SimpleDate date;
	private String type;
	private double money;
	private String description;
	private int index;
	private String[] record;

	/*
	 * (non-Javadoc)
	 * 
	 * @see database.IRecord#getIndex()
	 */
	@Override
	public int getIndex() {
		return index;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see database.IRecord#setIndex(int)
	 */
	@Override
	public void setIndex(int index) {
		this.index = index;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see database.IRecord#getRecord()
	 */
	@Override
	public String[] getRecord() {
		return record;
	}

	public Record() {
		// TODO Auto-generated constructor stub
		record = new String[4];
	}

	public Record(SimpleDate date, String type, double money, String description) {
		this();
		this.date = date;
		this.type = type;
		this.money = money;
		this.description = description;
		record[0] = date.toString();
		record[1] = type;
		record[2] = Double.toString(money);
		record[3] = description;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see database.IRecord#getDate()
	 */
	@Override
	public SimpleDate getDate() {
		return date;
	}
 
	/*
	 * (non-Javadoc)
	 * 
	 * @see database.IRecord#setDate(java.util.Date)
	 */
	@Override
	public void setDate(SimpleDate date) {
		this.date = date;
		record[0] = date.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see database.IRecord#getType()
	 */
	@Override
	public String getType() {
		return type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see database.IRecord#setType(java.lang.String)
	 */
	@Override
	public void setType(String type) {
		this.type = type;
		record[1] = type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see database.IRecord#getMoney()
	 */
	@Override
	public double getMoney() {
		return money;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see database.IRecord#setMoney(double)
	 */
	@Override
	public void setMoney(double money) {
		this.money = money;
		record[2] = Double.toString(money);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see database.IRecord#getDescription()
	 */
	@Override
	public String getDescription() {
		return description;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see database.IRecord#setDescription(java.lang.String)
	 */
	@Override
	public void setDescription(String description) {
		this.description = description;
		record[3] = description;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuilder s = new StringBuilder("[");
		s.append("\n datum :\t" + record[0]);
		s.append("\n typ :\t\t" + record[1]);
		s.append("\n ��stka :\t" + record[2]);
		s.append("\n popis :\t" + record[3]);
		s.append("\n]");
		return s.toString();
	}

	public boolean equals(IRecord record) {
		if (this.date.equals(record.getDate()) && this.type == record.getType()
				&& this.money == record.getMoney()
				&& this.description == record.getDescription())
			return true;
		return false;
	}

	@Override
	public int compareTo(IRecord o) {
		return date.compareTo(o.getDate());
		
	}
	@Override
	public int compareToByType(IRecord o) {
		type.compareTo(o.getType());
		return 0;
	}
	@Override
	public int compareToByMoney(IRecord o) {
		return Double.compare(money, o.getMoney());
		
	}

	@Override
	public int compare(IRecord o1, IRecord o2) {
		// TODO Auto-generated method stub
		return o1.compareTo(o2);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
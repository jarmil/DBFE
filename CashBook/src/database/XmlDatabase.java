package database;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import date.SimpleDate;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XmlDatabase implements IDatabase {

	private List<IRecord> database;
	private boolean selected;
	private boolean changed;
	private int size;
	XStream stream = new XStream(new StaxDriver());
	public static final IRecord defaultRecord = new Record(
			SimpleDate.getDate("00.00.0000"), "-----", 0.0, "----");

	@Override
	public boolean isSelected() {
		return selected;
	}

	public XmlDatabase() {}

	@Override
	@SuppressWarnings("unchecked")
	public void load() {
		try {
			database = (List<IRecord>) XMLWorkSet.getInstance().load(
					(String)Settings.getSetting().get("databaseNameAndPath"));

            database.remove(0);
			size = database.size();

		} catch (Exception e) {
			XMLWorkSet.getInstance().saveErrorTrace(e, "Problem v nacitani databaze");
			int opt = JOptionPane
					.showConfirmDialog(
							null,
							"Nepodařilo se načíst soubor s databází programu, nebo je poškozena, chcete vytvořit novou databázi?");
			if (opt == JOptionPane.YES_OPTION) {
				database = createDefaultDatabaseFile();

			} else {
				throw new RuntimeException("Je to v haji!");
			}
		}

	}

	@Override
	public void save() {
		database.add(0, defaultRecord);
		try {
			XMLWorkSet.getInstance().save((String)Settings.getSetting().get("databaseNameAndPath"), database);
		} catch (IOException e) {
			database.remove(0);
			XMLWorkSet.getInstance().saveErrorTrace(e,"Problem s ukladanim databaze");
		}

	}


	public List<IRecord> createDefaultDatabaseFile() {
		List<IRecord> defaultDatabase = new ArrayList<IRecord>();
		defaultDatabase.add(defaultRecord);
		try {
			XMLWorkSet.getInstance().save(
                    (String)Settings.getSetting().get("databaseNameAndPath"), defaultDatabase);
		} catch (IOException e) {
            XMLWorkSet.getInstance().saveErrorTrace(e,"Problem v ukladani default databaze v metode creatgefaultDatabaseFile");
		}
        defaultDatabase.remove(0);
		return defaultDatabase;
	}

	
	@Override
	public IRecord getRecord(int index) {
		return database.get(index);
	}

	
	@Override
	public int addRecord(IRecord record) {

		changed = true;
		return addToSort(record);
	}

	private int addToSort(IRecord record) {
		int min = 0;
		int max = database.size() - 1;
		int index = max / 2;
		SimpleDate date = record.getDate();

		if (size == 0) {

		} else {
			while (min != max) {

				switch (date.compareTo(database.get(index).getDate())) {
				case 0: {
					database.add(index, record);
					record.setIndex(index);
					return index;
				}
				case -1: {
					max = index;
					index = index / 2;
					break;
				}
				case 1: {
					min = index;
					index += (max - min);
					break;
				}
				}
			}
		}

		database.add(index, record);
		record.setIndex(database.size() - 1);
		return index;
	}


	@Override
	public int changeRecord(int index, IRecord newRecord) {

		database.remove(index);
		changed = true;
		return addToSort(newRecord);

	}

	public void deleteRecord(int index) {
		database.remove(index);
		changed = true;
	}

	
	@Override
	public int getRecordCount() {
		return database.size();
	}

	
	@Override
	public int getColumnCount() {
		return (int)Settings.getSetting().get("tableHeaderCount");
	}

	@Override
	public List<IRecord> getDatabaseList() {

		selected = false;
		return database;
	}

	@Override
	public boolean isChanged() {
		// TODO Auto-generated method stub
		return changed;
	}

	private List<IRecord> selectByDate(SimpleDate fromDate, SimpleDate toDate,
			List<IRecord> database) {
		// TODO Auto-generated method stub
		List<IRecord> selectedList = new ArrayList<>();
		if (fromDate == null && toDate == null)
			return database;
		if (fromDate == null) {
			for (IRecord record : database) {
				if (record.getDate().compareTo(toDate) <= 0)
					selectedList.add(record);
			}
		} else if (toDate == null) {
			for (IRecord record : database) {
				if (record.getDate().compareTo(fromDate) >= 0)
					selectedList.add(record);
			}
		} else {

			for (IRecord record : database) {
				if ((record.getDate().compareTo(toDate) <= 0)
						&& (record.getDate().compareTo(fromDate) >= 0))
					selectedList.add(record);
			}
		}
		return selectedList;
	}

	private List<IRecord> selectByType(String type, String selectedType,
			List<IRecord> database) {
		List<IRecord> selectedList = new ArrayList<>();
		if (type == null && selectedType == null)
			return database;

		if (type == null) {
			for (IRecord record : database) {
				System.out.println("1 " + record.getIndex());
				if (!record.getType().equals(selectedType)) {

					selectedList.add(record);
				}
			}
		} else if (selectedType == null) {
			for (IRecord record : database) {

				if (record.getType().equals(type)) {
					System.out.println(record.getType() + " "
							+ record.getIndex());
					selectedList.add(record);
				}
			}
		} else {
			for (IRecord record : database) {
				if (record.getType() == type
						&& record.getType() != selectedType) {
					System.out.print("3 " + record.getIndex());
					selectedList.add(record);
				}
			}
		}
		return selectedList;
	}

	private List<IRecord> selectByMoney(Double min, Double max,
			List<IRecord> database) {
		List<IRecord> selectedList = new ArrayList<>();
		if (min == null & max == null)
			return database;

		if (min == null) {
			for (IRecord record : database) {
				if (record.getMoney() <= max)
					selectedList.add(record);
			}

		} else if (max == null) {
			for (IRecord record : database) {
				if (record.getMoney() >= min)
					selectedList.add(record);
			}

		} else {
			for (IRecord record : database) {
				if (record.getMoney() >= min && record.getMoney() <= max)
					selectedList.add(record);
			}
		}
		return selectedList;
	}

	@Override
	public List<IRecord> selectByParams(SimpleDate fromDate, SimpleDate toDate,
			String type, String selectedType, Double min, Double max,
			boolean state) {

		selected = true;
		return selectByMoney(
				min,
				max,
				selectByType(type, selectedType,
						selectByDate(fromDate, toDate, database)));
	}

	public List<IRecord> selectDatabaseByParams(SimpleDate fromDate,
			SimpleDate toDate, String type, String selectedType, Double min,
			Double max, boolean state, List<IRecord> dtb) {

		selected = true;
		return selectByMoney(
				min,
				max,
				selectByType(type, selectedType,
						selectByDate(fromDate, toDate, dtb)));
	}

	@Override
	public List<IRecord> selectDatabaseByParams(SimpleDate fromDate,
			SimpleDate toDate, String type, String selectedType, Double min,
			Double max, boolean state) {
		// TODO Auto-generated method stub
		return null;
	}

}

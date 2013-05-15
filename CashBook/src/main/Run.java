package main;

import gui.MainGui;

import database.*;
public class Run {
	
	
	public static void main(String[] args){



		IDatabase database = new XmlDatabase();
		database.load();
		final IModel tModel = new TableModel(database);
		
		 java.awt.EventQueue.invokeLater(new Runnable() {
			  
			  @Override public void run() {
			  
			  MainGui m = new MainGui(tModel);
			  m.setVisible(true);
			  
			  
			  } });
		
	}

}
package gui.dialogs;

import database.IRecord;
import database.Record;
import database.Settings;
import date.SimpleDate;
import database.IModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangeDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8938527915607648229L;

	private JPanel panel;
	private JLabel titleLabel;
	private Label dateLabel;
	private JComboBox<String> typeComboBox;
	private Label typeLabel;
	private Label moneyLabel;
	private Label descriptionLabel;
	private JScrollPane scrollPane;
	private TextField descriptionFTField;
	private TextField moneyFTField;
	private TextField dateFTField;
	private JPanel buttonPane;
	private JButton okButton;
	private JButton cancelButton;
	private JButton calendarButton;

	private IModel model;
	private IRecord record;
	private int row;

	private final JPanel contentPanel = new JPanel();

	private void init() {

		setModal(true);
		setBounds(100, 100, 300, 300);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		setTitle("Nov\u00FD z\u00E1znam");

		panel = new JPanel();
		contentPanel.add(panel);
		panel.setLayout(null);

		titleLabel = new JLabel();
		titleLabel.setBounds(44, 11, 147, 17);
		panel.add(titleLabel);
		titleLabel.setText("Zadejte nov\u00FD z\u00E1znam");
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 14));

		dateLabel = new Label();
		dateLabel.setBounds(24, 50, 62, 24);
		panel.add(dateLabel);
		dateLabel.setText("Datum:");
		dateLabel.setFont(new Font("Dialog", Font.BOLD, 14));

		dateFTField = new TextField();
		dateFTField.setFont(new Font("Dialog", Font.BOLD, 15));
		dateFTField.setBounds(92, 50, 160, 29);
		dateFTField.setText(record.getDate().toString());
		panel.add(dateFTField);
		dateFTField.setEditable(true);

		calendarButton = new JButton();
		calendarButton.setBounds(255, 50, 29, 29);
		calendarButton.setIcon(new ImageIcon(AddDialog.class
				.getResource("/resources/JDateChooserColor16.gif")));
		panel.add(calendarButton);

		typeComboBox = new JComboBox<String>();
		for(String i : (String[])Settings.getSetting().get("typePosibilities"))
			typeComboBox.addItem(i);
		typeComboBox.setSelectedIndex(0);
		typeComboBox.setFont(new Font("Dialog", Font.BOLD, 15));
		typeComboBox.setBounds(92, 90, 160, 29);
		panel.add(typeComboBox);

		typeLabel = new Label();
		typeLabel.setBounds(44, 90, 42, 24);
		panel.add(typeLabel);
		typeLabel.setText("Typ:");
		typeLabel.setFont(new Font("Dialog", Font.BOLD, 14));

		moneyLabel = new Label();
		moneyLabel.setBounds(21, 135, 65, 24);
		panel.add(moneyLabel);
		moneyLabel.setText("\u010C\u00E1stka:");
		moneyLabel.setFont(new Font("Dialog", Font.BOLD, 14));

		moneyFTField = new TextField();
		moneyFTField.setFont(new Font("Dialog", Font.BOLD, 15));
		moneyFTField.setBounds(92, 130, 160, 29);
		moneyFTField.setText(String.valueOf(record.getMoney()).replace(".", ","));
		panel.add(moneyFTField);
		moneyFTField.setEditable(true);

		descriptionLabel = new Label();
		descriptionLabel.setBounds(29, 170, 57, 24);
		panel.add(descriptionLabel);
		descriptionLabel.setText("Popis:");
		descriptionLabel.setFont(new Font("Dialog", Font.BOLD, 14));

		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 2, 2);
		panel.add(scrollPane);

		descriptionFTField = new TextField();
		descriptionFTField.setFont(new Font("Dialog", Font.BOLD, 15));
		descriptionFTField.setBounds(92, 170, 160, 29);
		descriptionFTField.setText(record.getDescription());
		panel.add(descriptionFTField);

		buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		okButton = new JButton("P\u0159idat");
		okButton.setFont(new Font("Dialog", Font.PLAIN, 15));
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		cancelButton = new JButton("Zru\u0161it");
		cancelButton.setFont(new Font("Dialog", Font.PLAIN, 15));
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
		
		setLocationRelativeTo(null);

		// Buttons actionPerformed methods

		calendarButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dateFTField.setText(new DateDialog().getDate().toString());
			}
		});

		{
			okButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					eButtonOKActionPerformed(e);
				}
			});

			cancelButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(final ActionEvent arg0) {
					eButtonCancelActionPerformed(arg0);
				}
			});

		}

	}

	private void eButtonCancelActionPerformed(final ActionEvent arg0) {
		setVisible(false);
		dispose();
	}

	private void eButtonOKActionPerformed(final ActionEvent arg0) {

		
		IRecord record = new Record();

		if (dateFTField.getText().isEmpty()
				|| !SimpleDate.checkDate(dateFTField.getText())) {
			JOptionPane
					.showMessageDialog(
							this,
							"nebyl zad�n datum nebo je ve 3patn�m form�tu,\n spr�vn� form�t je: dd.mm.rrrr nap�. 1.1.2000 "
									+ dateFTField.getText());
			return;
		} else {
			record.setDate(SimpleDate.getDate(dateFTField.getText()));
		}

		if (moneyFTField.getText().isEmpty()) {
			JOptionPane.showMessageDialog(this, "nebyla zad�na ��dn� ��stka");
			return;
		} else {
			try {
				Double d = Double.parseDouble(moneyFTField.getText().trim().replace(
						',', '.'));
				record.setMoney(d.doubleValue());
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this,
						"mus�te zadat pouze ��slice te�ku nebo ��rku");
				return;
			}
			
		}
		if(typeComboBox.getSelectedItem() == null){
			JOptionPane.showMessageDialog(this,
					"mus�te vybrat ze seznamu typ operace!");
			return;
		}
			
		record.setType(typeComboBox.getSelectedItem().toString());
		record.setDescription(descriptionFTField.getText());
		record.setIndex(this.record.getIndex());

		model.changeRow(row,record);
		setVisible(false);
		dispose();

	}

	public ChangeDialog(IModel model, IRecord record, int row) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				ChangeDialog.class.getResource("/resources/Plus.png")));

		if (model == null || record == null)
			throw new NullPointerException();
		
		if (row < 0) {
			throw new NumberFormatException("hodnota mimo moznosti row :" + row );
		}
		this.row = row;
		this.model = model;
		this.record = record;
		init();
		setVisible(true);
	}
}

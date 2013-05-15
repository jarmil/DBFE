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

public class AddDialog extends JDialog {
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

	private final JPanel contentPanel = new JPanel();

	private void init() {

		setModal(true);
		setBounds(100, 100, 300, 300);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		setTitle("Nový záznam");

		panel = new JPanel();
		contentPanel.add(panel);
		panel.setLayout(null);

		titleLabel = new JLabel();
		titleLabel.setBounds(44, 11, 147, 17);
		panel.add(titleLabel);
		titleLabel.setText("Zadejte nový záznam");
		titleLabel.setFont(new Font("Dialog", Font.BOLD, 14));

		dateLabel = new Label();
		dateLabel.setBounds(24, 50, 62, 24);
		panel.add(dateLabel);
		dateLabel.setText("Datum:");
		dateLabel.setFont(new Font("Dialog", Font.BOLD, 14));

		dateFTField = new TextField();
		dateFTField.setFont(new Font("Dialog", Font.BOLD, 15));

		dateFTField.setBounds(92, 50, 160, 29);
		panel.add(dateFTField);
		dateFTField.setEditable(true);

		calendarButton = new JButton();
		calendarButton.setBounds(255, 50, 29, 29);
		calendarButton.setIcon(new ImageIcon(AddDialog.class
				.getResource("/resources/JDateChooserColor16.gif")));
		panel.add(calendarButton);

		typeComboBox = new JComboBox<String>();

		for (String i : (String[]) Settings.getSetting().get("typePosibilities"))
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
		moneyLabel.setText("Částka:");
		moneyLabel.setFont(new Font("Dialog", Font.BOLD, 14));

		moneyFTField = new TextField("0,0");
		moneyFTField.setFont(new Font("Dialog", Font.BOLD, 15));
		moneyFTField.setBounds(92, 130, 160, 29);
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
		panel.add(descriptionFTField);

		buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		okButton = new JButton("Přidat");
		okButton.setFont(new Font("Dialog", Font.PLAIN, 15));
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		cancelButton = new JButton("Zrušit");
		cancelButton.setFont(new Font("Dialog", Font.PLAIN, 15));
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);

		setLocationRelativeTo(null);

		// Buttons actionPerformed methods

		calendarButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				DateDialog dd = new DateDialog();
				dateFTField.setText(dd.getDate().toString());
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

		if (record == null)
			record = new Record();

		if (dateFTField.getText().isEmpty()
				|| !SimpleDate.checkDate(dateFTField.getText())) {
			JOptionPane
					.showMessageDialog(
							this,
							"nebyl zadán datum nebo je ve špatném formátu,\n správný formát je: dd.mm.rrrr např. 1.1.2000 "
									+ dateFTField.getText());
			return;
		} else {
			record.setDate(SimpleDate.getDate(dateFTField.getText()));
		}

		if (moneyFTField.getText().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Nebyla zadána žádná částka");
			return;
		} else {
			try {
				Double d = Double.parseDouble(moneyFTField.getText().trim()
						.replace(',', '.'));
				record.setMoney(d.doubleValue());
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this,
						"musíte zadat pouze číslice tečku nebo čárku");
				return;
			}
		}
		record.setType(typeComboBox.getSelectedItem().toString());
		record.setDescription(descriptionFTField.getText());
		model.addRow(record);

		setVisible(false);
		dispose();

	}

	public AddDialog(IModel model) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				AddDialog.class.getResource("/resources/Plus.png")));

		if (model == null)
			throw new NullPointerException();
		this.model = model;
		init();
		setVisible(true);
	}
}

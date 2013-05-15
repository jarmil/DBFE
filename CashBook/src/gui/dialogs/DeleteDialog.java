package gui.dialogs;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import database.IModel;

import javax.swing.LayoutStyle.ComponentPlacement;

import database.IRecord;
import java.awt.Color;

public class DeleteDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5335124170302849184L;
	private JPanel panel;
	private JPanel panel_1;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private Component horizontalStrut;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JLabel dateLabel;
	private JLabel typeLabel;
	private JLabel moneyLabel;
	private JLabel descriptionLabel;
	private IRecord record;
	private IModel model;
	private int row;
	private JLabel lblNewLabel_4;

	public DeleteDialog(IModel model, IRecord record, int row) {
		setResizable(false);
		setPreferredSize(new Dimension(300, 300));
		setSize(new Dimension(310, 300));
		if (model == null || record == null)
			throw new NullPointerException();

		if (row < 0)
			throw new NumberFormatException("hodnota mimo moznosti row :" + row);

		this.row = row;
		this.record = record;
		this.model = model;

		setTitle("Smaz\u00E1n\u00ED z\u00E1znamu");
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(getPanel_1(), BorderLayout.CENTER);
		getContentPane().add(getPanel(), BorderLayout.SOUTH);
		setLocationRelativeTo(null);
		setVisible(true);

	}

	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setSize(new Dimension(100, 0));
			panel.add(getBtnNewButton());
			panel.add(getHorizontalStrut());
			panel.add(getBtnNewButton_1());
		}
		return panel;
	}

	private JPanel getPanel_1() {
		if (panel_1 == null) {
			panel_1 = new JPanel();
			panel_1.setSize(new Dimension(220, 300));
			panel_1.setPreferredSize(new Dimension(10, 100));
			GroupLayout gl_panel_1 = new GroupLayout(panel_1);
			gl_panel_1
					.setHorizontalGroup(gl_panel_1
							.createParallelGroup(Alignment.LEADING)
							.addGroup(
									gl_panel_1
											.createSequentialGroup()
											.addContainerGap()
											.addGroup(
													gl_panel_1
															.createParallelGroup(
																	Alignment.LEADING)
															.addComponent(
																	getLblNewLabel_4())
															.addGroup(
																	gl_panel_1
																			.createSequentialGroup()
																			.addGroup(
																					gl_panel_1
																							.createParallelGroup(
																									Alignment.LEADING)
																							.addComponent(
																									getLblNewLabel_2())
																							.addComponent(
																									getLblNewLabel())
																							.addComponent(
																									getLblNewLabel_1())
																							.addComponent(
																									getLblNewLabel_3()))
																			.addGroup(
																					gl_panel_1
																							.createParallelGroup(
																									Alignment.LEADING)
																							.addGroup(
																									gl_panel_1
																											.createSequentialGroup()
																											.addGap(22)
																											.addGroup(
																													gl_panel_1
																															.createParallelGroup(
																																	Alignment.LEADING)
																															.addComponent(
																																	getTypeLabel())
																															.addComponent(
																																	getDateLabel())
																															.addComponent(
																																	getMoneyLabel())))
																							.addGroup(
																									gl_panel_1
																											.createSequentialGroup()
																											.addGap(18)
																											.addComponent(
																													getDescriptionLabel())))))
											.addContainerGap(30,
													Short.MAX_VALUE)));
			gl_panel_1
					.setVerticalGroup(gl_panel_1
							.createParallelGroup(Alignment.LEADING)
							.addGroup(
									gl_panel_1
											.createSequentialGroup()
											.addGap(31)
											.addComponent(getLblNewLabel_4())
											.addGap(39)
											.addGroup(
													gl_panel_1
															.createParallelGroup(
																	Alignment.BASELINE)
															.addComponent(
																	getLblNewLabel())
															.addComponent(
																	getDateLabel(),
																	GroupLayout.DEFAULT_SIZE,
																	GroupLayout.DEFAULT_SIZE,
																	Short.MAX_VALUE))
											.addGap(13)
											.addGroup(
													gl_panel_1
															.createParallelGroup(
																	Alignment.BASELINE)
															.addComponent(
																	getLblNewLabel_1())
															.addComponent(
																	getTypeLabel()))
											.addPreferredGap(
													ComponentPlacement.UNRELATED)
											.addGroup(
													gl_panel_1
															.createParallelGroup(
																	Alignment.BASELINE)
															.addComponent(
																	getLblNewLabel_2())
															.addComponent(
																	getMoneyLabel()))
											.addPreferredGap(
													ComponentPlacement.UNRELATED)
											.addGroup(
													gl_panel_1
															.createParallelGroup(
																	Alignment.BASELINE)
															.addComponent(
																	getLblNewLabel_3())
															.addComponent(
																	getDescriptionLabel()))
											.addGap(25)));
			panel_1.setLayout(gl_panel_1);
		}
		return panel_1;
	}

	private JButton getBtnNewButton() {
		if (btnNewButton == null) {
			btnNewButton = new JButton("Ano");
			btnNewButton.setFont(new Font("Dialog", Font.PLAIN, 15));

			btnNewButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					model.deleteRow(row, record);
					setVisible(false);

				}
			});
		}
		return btnNewButton;
	}

	private JButton getBtnNewButton_1() {
		if (btnNewButton_1 == null) {
			btnNewButton_1 = new JButton("Ne");
			btnNewButton_1.setFont(new Font("Dialog", Font.PLAIN, 15));

			btnNewButton_1.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					setVisible(false);
				}
			});
		}
		return btnNewButton_1;
	}

	private Component getHorizontalStrut() {
		if (horizontalStrut == null) {
			horizontalStrut = Box.createHorizontalStrut(20);
			horizontalStrut.setPreferredSize(new Dimension(50, 0));
			horizontalStrut.setMinimumSize(new Dimension(50, 0));
		}
		return horizontalStrut;
	}

	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("Datum :");
			lblNewLabel.setFont(new Font("Dialog", Font.PLAIN, 15));
		}
		return lblNewLabel;
	}

	private JLabel getLblNewLabel_1() {
		if (lblNewLabel_1 == null) {
			lblNewLabel_1 = new JLabel("Typ :");
			lblNewLabel_1.setFont(new Font("Dialog", Font.PLAIN, 15));
		}
		return lblNewLabel_1;
	}

	private JLabel getLblNewLabel_2() {
		if (lblNewLabel_2 == null) {
			lblNewLabel_2 = new JLabel("\u010C\u00E1stka :");
			lblNewLabel_2.setFont(new Font("Dialog", Font.PLAIN, 15));
		}
		return lblNewLabel_2;
	}

	private JLabel getLblNewLabel_3() {
		if (lblNewLabel_3 == null) {
			lblNewLabel_3 = new JLabel("Popisek :");
			lblNewLabel_3.setFont(new Font("Dialog", Font.PLAIN, 15));
		}
		return lblNewLabel_3;
	}

	private JLabel getDateLabel() {
		if (dateLabel == null) {

			dateLabel = new JLabel(record.getDate().toString());
			dateLabel.setForeground(Color.WHITE);
			dateLabel.setBackground(Color.RED);
			dateLabel.setFont(new Font("Dialog", Font.BOLD, 15));
			dateLabel.setOpaque(true);
		}
		return dateLabel;
	}

	private JLabel getTypeLabel() {
		if (typeLabel == null) {
			typeLabel = new JLabel(record.getType());
			typeLabel.setForeground(Color.WHITE);
			typeLabel.setBackground(Color.RED);
			typeLabel.setFont(new Font("Dialog", Font.BOLD, 15));
			typeLabel.setOpaque(true);
		}
		return typeLabel;
	}

	private JLabel getMoneyLabel() {
		if (moneyLabel == null) {
			moneyLabel = new JLabel(String.valueOf(record.getMoney()).replace(
					".", ","));
			moneyLabel.setForeground(Color.WHITE);
			moneyLabel.setBackground(Color.RED);
			moneyLabel.setFont(new Font("Dialog", Font.BOLD, 15));
			moneyLabel.setOpaque(true);
		}
		return moneyLabel;
	}

	private JLabel getDescriptionLabel() {
		if (descriptionLabel == null) {
			descriptionLabel = new JLabel(record.getDescription());
			descriptionLabel.setForeground(Color.WHITE);
			descriptionLabel.setBackground(Color.RED);
			descriptionLabel.setFont(new Font("Dialog", Font.BOLD, 15));
			descriptionLabel.setOpaque(true);
		}
		return descriptionLabel;
	}

	private JLabel getLblNewLabel_4() {
		if (lblNewLabel_4 == null) {
			lblNewLabel_4 = new JLabel("Opravdu chcete smazat tento z�znam?");
			lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 15));
		}
		return lblNewLabel_4;
	}
}

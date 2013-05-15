package gui.dialogs;

import com.toedter.calendar.JCalendar;
import date.SimpleDate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DateDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -833795521130196871L;
	private JPanel panel;
	private JButton btnNewButton;
	private JCalendar calendar;

	public DateDialog() {
		getContentPane().setBackground(Color.DARK_GRAY);
		setModal(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(461, 378);
		getContentPane().setLayout(null);
		getContentPane().add(getPanel());
		getContentPane().add(getBtnNewButton());
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setBounds(10, 0, 425, 289);
			panel.setLayout(new BorderLayout(0, 0));

			calendar = new JCalendar();
			panel.add(calendar);
		}
		return panel;
	}

	private JButton getBtnNewButton() {
		if (btnNewButton == null) {
			btnNewButton = new JButton("Vlož datum");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(final ActionEvent arg0) {
					do_btnNewButton_actionPerformed(arg0);
				}
			});
			btnNewButton.setBounds(175, 300, 89, 29);
		}
		return btnNewButton;
	}

	protected void do_btnNewButton_actionPerformed(final ActionEvent arg0) {
		setVisible(false);
		dispose();
	}

	@SuppressWarnings("deprecation")
	public SimpleDate getDate() {
		return SimpleDate.getDate(calendar.getDate());
	}

}

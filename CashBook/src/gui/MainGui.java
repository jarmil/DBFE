package gui;

import database.Settings;
import date.SimpleDate;
import gui.dialogs.AddDialog;
import gui.dialogs.ChangeDialog;
import gui.dialogs.DateDialog;
import gui.dialogs.DeleteDialog;
import database.IModel;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;

/**
 * 
 * @author kuba
 */
public class MainGui extends javax.swing.JFrame {

	private static final long serialVersionUID = -1463656139403107021L;

	private static Font tableFont = new Font("Dialog", Font.PLAIN, 14);
	private static Color nsBackground = SystemColor.control;
	private static Color sBackground = SystemColor.activeCaption;
	private static Color nsForeground = SystemColor.BLACK;
	private static Color sForeground = Color.WHITE;

	private javax.swing.JButton buttonAdd;
	private javax.swing.JButton buttonDelete;
	private javax.swing.JButton buttonChange;
	private javax.swing.JButton buttonSetting;
	private java.awt.TextField dateChoice1;
	private java.awt.TextField dateChoice2;
	private javax.swing.JComboBox<String> typeChoice1;
	private javax.swing.JComboBox<String> typeChoice2;
	private java.awt.TextField moneyChoice1;
	private java.awt.TextField moneyChoice2;
	private javax.swing.Box.Filler filler1;
	private javax.swing.JPanel panelMainBottom;
	private javax.swing.JPanel panelMainRight;
	private javax.swing.JPanel panelMainTable;
	private javax.swing.JPanel panelMainBottomDate;
	private javax.swing.JPanel panelMainBottomMoney;
	private javax.swing.JPanel panelMainBottomType;
	private javax.swing.JPanel jPanel9;
	private javax.swing.JScrollPane scrollPaneMain;
	private javax.swing.JTable tableMain;
	private java.awt.Label label1;
	private java.awt.Label label4;
	private java.awt.Label label5;
	private java.awt.Label label6;
	private java.awt.Label label7;
	private java.awt.Label label8;
	private IModel tm;
	private JButton calendarButton1;
	private JButton calendarButton2;
	private JPanel panel_1;
	private JCheckBox selectCheckBox;
	private JButton confirmButton;
	private JButton resetButton;
	private JPanel panelSummary;
	private JPanel panel_2;
	private JPanel panel_3;
	private JPanel panel_4;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private JButton btnNewButton_2;
	private JSeparator separator;

	private JTabbedPane tabbedPane;
	private JPanel panelSubMain;

	public MainGui(IModel tm) {
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(final WindowEvent arg0) {
				do_this_windowClosing(arg0);
			}
		});
		this.tm = tm;
		initComponents();

	}

	private void initComponents() {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException | ClassNotFoundException
				| InstantiationException | IllegalAccessException e) {

		}

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setPreferredSize(new java.awt.Dimension(1024, 768));

		// table////////////////////////////////////////////////////////////////////////////////////////////////

		panelMainTable = new javax.swing.JPanel();
		panelMainTable.setLayout(new java.awt.BorderLayout());

		// //////////////////////////////////////////////////////////////////////////////////////////////////////

		// right
		// buttons///////////////////////////////////////////////////////////////////////////////
		{

			panelMainRight = new javax.swing.JPanel();
			panelMainRight.setBackground(Color.DARK_GRAY);
			panelMainRight.setBorder(new MatteBorder(2, 2, 0, 2,
					(Color) new Color(255, 255, 255)));
			panelMainRight.setPreferredSize(new java.awt.Dimension(120, 427));

			filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0),
					new java.awt.Dimension(20, 0), new java.awt.Dimension(20,
							32767));

			buttonAdd = new javax.swing.JButton();
			buttonAdd.setFont(new Font("Dialog", Font.BOLD, 14));
			buttonAdd.setIcon(new ImageIcon(MainGui.class
					.getResource("/resources/document_add_32.png")));

			buttonAdd.addActionListener(new ActionListener() {
				public void actionPerformed(final ActionEvent e) {
					do_buttonAdd_actionPerformed(e);
				}
			});

			buttonDelete = new javax.swing.JButton();
			buttonDelete.setFont(new Font("Dialog", Font.BOLD, 14));
			buttonDelete.setIcon(new ImageIcon(MainGui.class
					.getResource("/resources/document_delete_32.png")));
			buttonDelete.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(final ActionEvent e) {
					do_buttonDelete_actionPerformed(e);
				}
			});

			buttonChange = new javax.swing.JButton();
			buttonChange.setFont(new Font("Dialog", Font.BOLD, 14));
			buttonChange.setIcon(new ImageIcon(MainGui.class
					.getResource("/resources/recycle_32.png")));
			buttonChange.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					do_buttonChange_actionPerformed(e);
				}
			});

			buttonSetting = new javax.swing.JButton();
			buttonSetting.setFont(new Font("Dialog", Font.BOLD, 14));

			buttonAdd.setBackground(new java.awt.Color(0, 255, 102));

			buttonAdd
					.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
			buttonAdd.setText("Přidat");
			buttonAdd.setVerticalAlignment(javax.swing.SwingConstants.TOP);
			buttonAdd
					.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

			buttonDelete.setBackground(new java.awt.Color(204, 51, 0));

			buttonDelete
					.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
			buttonDelete.setText("Odebrat");
			buttonDelete
					.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

			buttonChange.setBackground(new java.awt.Color(51, 51, 255));

			buttonChange
					.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
			buttonChange.setText("Změnit");
			buttonChange
					.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

			buttonSetting.setBackground(new java.awt.Color(0, 0, 0));
			buttonSetting.setIcon(new ImageIcon(MainGui.class
					.getResource("/resources/Computer.png"))); // NOI18N
			buttonSetting.setText("Úpravy");
			buttonSetting
					.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
			buttonSetting
					.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		}

		// //////////////////////////////////////////////////////////////////////////////////////////////////////

		// bottom buttons and text fields
		// ///////////////////////////////////////////////////////////////////////
		{

			tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			tabbedPane.setFont(new Font("Dialog", Font.BOLD, 14));
			tabbedPane.setBorder(new CompoundBorder(new LineBorder(new Color(
					255, 255, 255)), new LineBorder(new Color(64, 64, 64))));
			tabbedPane.setForeground(Color.BLACK);
			tabbedPane.setBackground(Color.BLACK);
			panelMainBottom = new javax.swing.JPanel();
			tabbedPane.addTab("   Výběr   ", panelMainBottom);

			panelMainBottomDate = new javax.swing.JPanel();
			label1 = new java.awt.Label();
			label1.setFont(new Font("Dialog", Font.PLAIN, 14));
			dateChoice1 = new java.awt.TextField();
			dateChoice1.setFont(new Font("Dialog", Font.PLAIN, 15));
			dateChoice2 = new java.awt.TextField();
			dateChoice2.setFont(new Font("Dialog", Font.PLAIN, 15));
			label4 = new java.awt.Label();
			label4.setFont(new Font("Dialog", Font.PLAIN, 14));
			panelMainBottomMoney = new javax.swing.JPanel();
			label5 = new java.awt.Label();
			label5.setFont(new Font("Dialog", Font.PLAIN, 14));
			moneyChoice1 = new java.awt.TextField();
			moneyChoice1.setFont(new Font("Dialog", Font.PLAIN, 15));
			moneyChoice2 = new java.awt.TextField();
			moneyChoice2.setFont(new Font("Dialog", Font.PLAIN, 15));
			label6 = new java.awt.Label();
			label6.setFont(new Font("Dialog", Font.PLAIN, 14));
			panelMainBottomType = new javax.swing.JPanel();
			label7 = new java.awt.Label();
			label7.setFont(new Font("Dialog", Font.PLAIN, 14));

			typeChoice1 = new javax.swing.JComboBox<String>();
			typeChoice1.addActionListener(new ActionListener() {
				public void actionPerformed(final ActionEvent arg0) {
					do_typeChoice1_actionPerformed(arg0);
				}
			});
			typeChoice1.setFont(new Font("Dialog", Font.PLAIN, 15));

			typeChoice2 = new javax.swing.JComboBox<String>();
			typeChoice2.addActionListener(new ActionListener() {
				public void actionPerformed(final ActionEvent e) {
					do_typeChoice2_actionPerformed(e);
				}
			});
			typeChoice2.setFont(new Font("Dialog", Font.PLAIN, 15));
			typeChoice1.addItem("");
			typeChoice2.addItem("");

			for (String s : (String[]) Settings.getSetting().get(
					"typePosibilities")) {
				typeChoice1.addItem(s);
				typeChoice2.addItem(s);
			}
			typeChoice1.setSelectedIndex(-1);
			typeChoice2.setSelectedIndex(-1);
			label8 = new java.awt.Label();
			label8.setFont(new Font("Dialog", Font.PLAIN, 14));
			jPanel9 = new javax.swing.JPanel();

			javax.swing.GroupLayout gl_panelMainRight = new javax.swing.GroupLayout(
					panelMainRight);
			gl_panelMainRight
					.setHorizontalGroup(gl_panelMainRight
							.createParallelGroup(Alignment.LEADING)
							.addGroup(
									gl_panelMainRight
											.createSequentialGroup()
											.addComponent(filler1,
													GroupLayout.PREFERRED_SIZE,
													6,
													GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(
													ComponentPlacement.UNRELATED)
											.addGroup(
													gl_panelMainRight
															.createParallelGroup(
																	Alignment.TRAILING,
																	false)
															.addComponent(
																	buttonChange,
																	Alignment.LEADING,
																	GroupLayout.DEFAULT_SIZE,
																	GroupLayout.DEFAULT_SIZE,
																	Short.MAX_VALUE)
															.addComponent(
																	buttonAdd,
																	Alignment.LEADING,
																	GroupLayout.DEFAULT_SIZE,
																	GroupLayout.DEFAULT_SIZE,
																	Short.MAX_VALUE)
															.addComponent(
																	buttonDelete,
																	Alignment.LEADING,
																	GroupLayout.DEFAULT_SIZE,
																	GroupLayout.DEFAULT_SIZE,
																	Short.MAX_VALUE)
															.addComponent(
																	buttonSetting,
																	Alignment.LEADING,
																	GroupLayout.DEFAULT_SIZE,
																	GroupLayout.DEFAULT_SIZE,
																	Short.MAX_VALUE))
											.addContainerGap(11,
													Short.MAX_VALUE)));
			gl_panelMainRight.setVerticalGroup(gl_panelMainRight
					.createParallelGroup(Alignment.LEADING)
					.addGroup(
							gl_panelMainRight
									.createSequentialGroup()
									.addContainerGap()
									.addComponent(buttonAdd,
											GroupLayout.PREFERRED_SIZE, 66,
											GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(buttonDelete,
											GroupLayout.PREFERRED_SIZE, 66,
											GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(buttonChange,
											GroupLayout.PREFERRED_SIZE, 70,
											GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(
											ComponentPlacement.RELATED, 221,
											Short.MAX_VALUE)
									.addComponent(buttonSetting)
									.addContainerGap())
					.addComponent(filler1, Alignment.TRAILING,
							GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE));
			panelMainRight.setLayout(gl_panelMainRight);

			panelMainTable.add(panelMainRight, java.awt.BorderLayout.LINE_END);

			panelMainBottom.setBackground(Color.DARK_GRAY);

			panelMainBottomDate
					.setBackground(new java.awt.Color(102, 102, 102));
			panelMainBottomDate
					.setBorder(javax.swing.BorderFactory
							.createTitledBorder(
									null,
									"Datum",
									javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
									javax.swing.border.TitledBorder.DEFAULT_POSITION,
									null, new java.awt.Color(240, 240, 240)));

			label1.setForeground(new java.awt.Color(240, 240, 240));
			label1.setText("Od:");

			label4.setBackground(new java.awt.Color(102, 102, 102));
			label4.setForeground(new java.awt.Color(240, 240, 240));
			label4.setText("Do:");

			javax.swing.GroupLayout gl_panelMainBottomDate = new javax.swing.GroupLayout(
					panelMainBottomDate);
			gl_panelMainBottomDate
					.setHorizontalGroup(gl_panelMainBottomDate
							.createParallelGroup(Alignment.LEADING)
							.addGroup(
									gl_panelMainBottomDate
											.createSequentialGroup()
											.addContainerGap()
											.addGroup(
													gl_panelMainBottomDate
															.createParallelGroup(
																	Alignment.LEADING,
																	false)
															.addGroup(
																	gl_panelMainBottomDate
																			.createSequentialGroup()
																			.addComponent(
																					label1,
																					GroupLayout.PREFERRED_SIZE,
																					GroupLayout.DEFAULT_SIZE,
																					GroupLayout.PREFERRED_SIZE)
																			.addPreferredGap(
																					ComponentPlacement.RELATED)
																			.addComponent(
																					dateChoice1,
																					GroupLayout.PREFERRED_SIZE,
																					106,
																					GroupLayout.PREFERRED_SIZE))
															.addGroup(
																	gl_panelMainBottomDate
																			.createSequentialGroup()
																			.addComponent(
																					label4,
																					GroupLayout.PREFERRED_SIZE,
																					GroupLayout.DEFAULT_SIZE,
																					GroupLayout.PREFERRED_SIZE)
																			.addPreferredGap(
																					ComponentPlacement.RELATED)
																			.addComponent(
																					dateChoice2,
																					GroupLayout.DEFAULT_SIZE,
																					GroupLayout.DEFAULT_SIZE,
																					Short.MAX_VALUE)))
											.addPreferredGap(
													ComponentPlacement.UNRELATED,
													15, Short.MAX_VALUE)
											.addGroup(
													gl_panelMainBottomDate
															.createParallelGroup(
																	Alignment.LEADING)
															.addComponent(
																	getCalendarButton2(),
																	GroupLayout.PREFERRED_SIZE,
																	31,
																	GroupLayout.PREFERRED_SIZE)
															.addComponent(
																	getCalendarButton1(),
																	GroupLayout.PREFERRED_SIZE,
																	31,
																	GroupLayout.PREFERRED_SIZE))
											.addContainerGap(16,
													Short.MAX_VALUE)));
			gl_panelMainBottomDate
					.setVerticalGroup(gl_panelMainBottomDate
							.createParallelGroup(Alignment.LEADING)
							.addGroup(
									gl_panelMainBottomDate
											.createSequentialGroup()
											.addContainerGap()
											.addGroup(
													gl_panelMainBottomDate
															.createParallelGroup(
																	Alignment.LEADING)
															.addComponent(
																	dateChoice1,
																	GroupLayout.DEFAULT_SIZE,
																	GroupLayout.DEFAULT_SIZE,
																	Short.MAX_VALUE)
															.addGroup(
																	gl_panelMainBottomDate
																			.createParallelGroup(
																					Alignment.LEADING,
																					false)
																			.addComponent(
																					label1,
																					GroupLayout.PREFERRED_SIZE,
																					GroupLayout.DEFAULT_SIZE,
																					GroupLayout.PREFERRED_SIZE)
																			.addComponent(
																					getCalendarButton1(),
																					GroupLayout.DEFAULT_SIZE,
																					GroupLayout.DEFAULT_SIZE,
																					Short.MAX_VALUE)))
											.addGap(11)
											.addGroup(
													gl_panelMainBottomDate
															.createParallelGroup(
																	Alignment.LEADING)
															.addComponent(
																	label4,
																	GroupLayout.PREFERRED_SIZE,
																	GroupLayout.DEFAULT_SIZE,
																	GroupLayout.PREFERRED_SIZE)
															.addComponent(
																	dateChoice2,
																	GroupLayout.DEFAULT_SIZE,
																	GroupLayout.DEFAULT_SIZE,
																	Short.MAX_VALUE)
															.addComponent(
																	getCalendarButton2(),
																	GroupLayout.DEFAULT_SIZE,
																	25,
																	Short.MAX_VALUE))
											.addGap(17)));
			panelMainBottomDate.setLayout(gl_panelMainBottomDate);

			panelMainBottomMoney
					.setBackground(new java.awt.Color(102, 102, 102));
			panelMainBottomMoney
					.setBorder(javax.swing.BorderFactory
							.createTitledBorder(
									null,
									"Částka",
									javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
									javax.swing.border.TitledBorder.DEFAULT_POSITION,
									null, new java.awt.Color(240, 240, 240)));

			label5.setForeground(new java.awt.Color(240, 240, 240));
			label5.setText("Od:");

			label6.setBackground(new java.awt.Color(102, 102, 102));
			label6.setForeground(new java.awt.Color(240, 240, 240));
			label6.setText("Do:");

			javax.swing.GroupLayout gl_panelMainBottomMoney = new javax.swing.GroupLayout(
					panelMainBottomMoney);
			gl_panelMainBottomMoney
					.setHorizontalGroup(gl_panelMainBottomMoney
							.createParallelGroup(Alignment.LEADING)
							.addGroup(
									gl_panelMainBottomMoney
											.createSequentialGroup()
											.addContainerGap()
											.addGroup(
													gl_panelMainBottomMoney
															.createParallelGroup(
																	Alignment.LEADING)
															.addGroup(
																	gl_panelMainBottomMoney
																			.createSequentialGroup()
																			.addComponent(
																					label5,
																					GroupLayout.PREFERRED_SIZE,
																					GroupLayout.DEFAULT_SIZE,
																					GroupLayout.PREFERRED_SIZE)
																			.addPreferredGap(
																					ComponentPlacement.RELATED)
																			.addComponent(
																					moneyChoice1,
																					GroupLayout.PREFERRED_SIZE,
																					102,
																					GroupLayout.PREFERRED_SIZE))
															.addGroup(
																	gl_panelMainBottomMoney
																			.createSequentialGroup()
																			.addComponent(
																					label6,
																					GroupLayout.PREFERRED_SIZE,
																					GroupLayout.DEFAULT_SIZE,
																					GroupLayout.PREFERRED_SIZE)
																			.addPreferredGap(
																					ComponentPlacement.RELATED)
																			.addComponent(
																					moneyChoice2,
																					GroupLayout.DEFAULT_SIZE,
																					GroupLayout.DEFAULT_SIZE,
																					Short.MAX_VALUE)))
											.addContainerGap(10,
													Short.MAX_VALUE)));
			gl_panelMainBottomMoney
					.setVerticalGroup(gl_panelMainBottomMoney
							.createParallelGroup(Alignment.LEADING)
							.addGroup(
									gl_panelMainBottomMoney
											.createSequentialGroup()
											.addContainerGap()
											.addGroup(
													gl_panelMainBottomMoney
															.createParallelGroup(
																	Alignment.LEADING)
															.addComponent(
																	label5,
																	GroupLayout.PREFERRED_SIZE,
																	GroupLayout.DEFAULT_SIZE,
																	GroupLayout.PREFERRED_SIZE)
															.addComponent(
																	moneyChoice1,
																	GroupLayout.PREFERRED_SIZE,
																	GroupLayout.DEFAULT_SIZE,
																	GroupLayout.PREFERRED_SIZE))
											.addGap(10)
											.addGroup(
													gl_panelMainBottomMoney
															.createParallelGroup(
																	Alignment.LEADING)
															.addComponent(
																	moneyChoice2,
																	GroupLayout.PREFERRED_SIZE,
																	GroupLayout.DEFAULT_SIZE,
																	GroupLayout.PREFERRED_SIZE)
															.addComponent(
																	label6,
																	GroupLayout.PREFERRED_SIZE,
																	GroupLayout.DEFAULT_SIZE,
																	GroupLayout.PREFERRED_SIZE))
											.addContainerGap(19,
													Short.MAX_VALUE)));
			panelMainBottomMoney.setLayout(gl_panelMainBottomMoney);

			panelMainBottomType
					.setBackground(new java.awt.Color(102, 102, 102));
			panelMainBottomType
					.setBorder(javax.swing.BorderFactory
							.createTitledBorder(
									null,
									"Typ",
									javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
									javax.swing.border.TitledBorder.DEFAULT_POSITION,
									null, new java.awt.Color(240, 240, 240)));

			label7.setForeground(new java.awt.Color(240, 240, 240));
			label7.setText("Typ:");

			label8.setBackground(new java.awt.Color(102, 102, 102));
			label8.setForeground(new java.awt.Color(240, 240, 240));
			label8.setText("Vynechat:");

			javax.swing.GroupLayout gl_panelMainBottomType = new javax.swing.GroupLayout(
					panelMainBottomType);
			gl_panelMainBottomType
					.setHorizontalGroup(gl_panelMainBottomType
							.createParallelGroup(Alignment.LEADING)
							.addGroup(
									gl_panelMainBottomType
											.createSequentialGroup()
											.addContainerGap()
											.addGroup(
													gl_panelMainBottomType
															.createParallelGroup(
																	Alignment.LEADING)
															.addComponent(
																	label8,
																	GroupLayout.PREFERRED_SIZE,
																	GroupLayout.DEFAULT_SIZE,
																	GroupLayout.PREFERRED_SIZE)
															.addComponent(
																	label7,
																	GroupLayout.PREFERRED_SIZE,
																	GroupLayout.DEFAULT_SIZE,
																	GroupLayout.PREFERRED_SIZE))
											.addPreferredGap(
													ComponentPlacement.RELATED)
											.addGroup(
													gl_panelMainBottomType
															.createParallelGroup(
																	Alignment.LEADING)
															.addComponent(
																	typeChoice1,
																	GroupLayout.DEFAULT_SIZE,
																	124,
																	Short.MAX_VALUE)
															.addComponent(
																	typeChoice2,
																	GroupLayout.DEFAULT_SIZE,
																	GroupLayout.DEFAULT_SIZE,
																	Short.MAX_VALUE))
											.addGap(10)));
			gl_panelMainBottomType
					.setVerticalGroup(gl_panelMainBottomType
							.createParallelGroup(Alignment.LEADING)
							.addGroup(
									gl_panelMainBottomType
											.createSequentialGroup()
											.addContainerGap()
											.addGroup(
													gl_panelMainBottomType
															.createParallelGroup(
																	Alignment.LEADING)
															.addComponent(
																	label7,
																	GroupLayout.PREFERRED_SIZE,
																	GroupLayout.DEFAULT_SIZE,
																	GroupLayout.PREFERRED_SIZE)
															.addComponent(
																	typeChoice1,
																	GroupLayout.PREFERRED_SIZE,
																	GroupLayout.DEFAULT_SIZE,
																	GroupLayout.PREFERRED_SIZE))
											.addGap(10)
											.addGroup(
													gl_panelMainBottomType
															.createParallelGroup(
																	Alignment.LEADING)
															.addComponent(
																	typeChoice2,
																	GroupLayout.PREFERRED_SIZE,
																	GroupLayout.DEFAULT_SIZE,
																	GroupLayout.PREFERRED_SIZE)
															.addComponent(
																	label8,
																	GroupLayout.PREFERRED_SIZE,
																	GroupLayout.DEFAULT_SIZE,
																	GroupLayout.PREFERRED_SIZE))
											.addContainerGap(17,
													Short.MAX_VALUE)));
			panelMainBottomType.setLayout(gl_panelMainBottomType);

			javax.swing.GroupLayout gl_panelMainBottom = new javax.swing.GroupLayout(
					panelMainBottom);
			gl_panelMainBottom
					.setHorizontalGroup(gl_panelMainBottom.createParallelGroup(
							Alignment.LEADING)
							.addGroup(
									gl_panelMainBottom
											.createSequentialGroup()
											.addGap(6)
											.addComponent(panelMainBottomDate,
													GroupLayout.PREFERRED_SIZE,
													225,
													GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(
													ComponentPlacement.RELATED)
											.addComponent(panelMainBottomType,
													GroupLayout.PREFERRED_SIZE,
													222,
													GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(
													ComponentPlacement.RELATED)
											.addComponent(panelMainBottomMoney,
													GroupLayout.PREFERRED_SIZE,
													GroupLayout.DEFAULT_SIZE,
													GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(
													ComponentPlacement.RELATED)
											.addComponent(getPanel_1(),
													GroupLayout.PREFERRED_SIZE,
													223,
													GroupLayout.PREFERRED_SIZE)
											.addContainerGap(12,
													Short.MAX_VALUE)));
			gl_panelMainBottom
					.setVerticalGroup(gl_panelMainBottom
							.createParallelGroup(Alignment.TRAILING)
							.addGroup(
									gl_panelMainBottom
											.createSequentialGroup()
											.addContainerGap()
											.addGroup(
													gl_panelMainBottom
															.createParallelGroup(
																	Alignment.BASELINE)
															.addComponent(
																	panelMainBottomDate,
																	GroupLayout.DEFAULT_SIZE,
																	GroupLayout.DEFAULT_SIZE,
																	Short.MAX_VALUE)
															.addComponent(
																	panelMainBottomType,
																	GroupLayout.DEFAULT_SIZE,
																	112,
																	Short.MAX_VALUE)
															.addComponent(
																	panelMainBottomMoney,
																	GroupLayout.DEFAULT_SIZE,
																	112,
																	Short.MAX_VALUE)
															.addComponent(
																	getPanel_1(),
																	GroupLayout.PREFERRED_SIZE,
																	112,
																	GroupLayout.PREFERRED_SIZE))
											.addContainerGap()));
			panelMainBottom.setLayout(gl_panelMainBottom);

		}

		// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

		// add components to tree ////////////////////////////////////////
		{

			jPanel9.setLayout(new BorderLayout(0, 0));
			jPanel9.add(getPanelSummary());

			getContentPane().add(panelMainTable, java.awt.BorderLayout.CENTER);

		}

		panelSubMain = new JPanel();
		panelMainTable.add(panelSubMain, BorderLayout.CENTER);
		panelSubMain.setLayout(new BorderLayout(0, 0));
		scrollPaneMain = new javax.swing.JScrollPane();
		panelSubMain.add(scrollPaneMain, BorderLayout.CENTER);
		panelSubMain.add(tabbedPane, BorderLayout.SOUTH);
		scrollPaneMain.setBorder(new MatteBorder(2, 2, 0, 0, (Color) new Color(
				255, 255, 255)));
		tableMain = new javax.swing.JTable(tm) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 684814535828898145L;

			@Override
			public Component prepareRenderer(TableCellRenderer arg0, int arg1,
					int arg2) {
				// TODO Auto-generated method stub
				Component c = super.prepareRenderer(arg0, arg1, arg2);

				if (!isRowSelected(arg1)) {
					c.setBackground(getBackground());
					int modelRow = convertRowIndexToModel(arg1);
					Double type = Double.parseDouble((String) getModel()
							.getValueAt(modelRow, 2));
					if (type < 0) {
						c.setBackground(Color.LIGHT_GRAY);

					}
				}

				return c;
			}
		};
		tableMain.setBackground(SystemColor.control);
		tableMain.setBorder(javax.swing.BorderFactory.createLineBorder(
				new java.awt.Color(255, 255, 255), 2));

		tableMain.setRowHeight(30);
		tableMain
				.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		tableMain.getTableHeader().setResizingAllowed(false);
		tableMain.getTableHeader().setReorderingAllowed(false);
		tableMain.getTableHeader().setDefaultRenderer(
				new MyHeaderDefaultCellRender());

		tableMain.setBorder(javax.swing.BorderFactory.createLineBorder(
				Color.DARK_GRAY, 1));

		tableMain.getColumnModel().getColumn(0)
				.setCellRenderer(new MyDateCellrender());

		tableMain.getColumnModel().getColumn(1)
				.setCellRenderer(new MyStringCellrender());

		tableMain.getColumnModel().getColumn(2)
				.setCellRenderer(new MyMoneyCellrender());

		tableMain.getColumnModel().getColumn(3)
				.setCellRenderer(new MyStringCellrender());

		// tableMain.getColumnModel().getColumn(3).setMinWidth(400);
		tableMain.getColumnModel().getColumn(0).setMaxWidth(250);
		tableMain.getColumnModel().getColumn(1).setMaxWidth(250);
		tableMain.getColumnModel().getColumn(2).setMaxWidth(250);
		tableMain.getColumnModel().getColumn(0).setMinWidth(150);
		tableMain.getColumnModel().getColumn(1).setMinWidth(150);
		tableMain.getColumnModel().getColumn(2).setMinWidth(150);

		scrollPaneMain.setViewportView(tableMain);

		// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

		pack();
	}

	public void do_buttonAdd_actionPerformed(final ActionEvent e) {
		new AddDialog(tm);
	}

	public void do_buttonDelete_actionPerformed(final ActionEvent e) {
		if (tableMain.getSelectedRow() == -1) {
			JOptionPane.showMessageDialog(this, "Nebyl vybrán žádný řádek!",
					"Upozornění", JOptionPane.WARNING_MESSAGE);
			return;
		}
		new DeleteDialog(tm, tm.getRow(tableMain.getSelectedRow()),
				tableMain.getSelectedRow());
	}

	public void do_buttonChange_actionPerformed(final ActionEvent e) {
		if (tableMain.getSelectedRow() == -1) {
			JOptionPane.showMessageDialog(this, "Nebyl vybrán žádný řádek!",
					"Upozornění", JOptionPane.WARNING_MESSAGE);
			return;
		}
		new ChangeDialog(tm, tm.getRow(tableMain.getSelectedRow()),
				tableMain.getSelectedRow());
	}

	protected void do_this_windowClosing(final WindowEvent arg0) {
		if (tm.isChanged()) {
			int i = JOptionPane.showConfirmDialog(this,
					"P?ejete si uložit změny", "Uložit změny",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (i == JOptionPane.OK_OPTION)
				tm.exit();
			setVisible(false);
			dispose();

		}
	}

	private JButton getCalendarButton1() {
		if (calendarButton1 == null) {
			calendarButton1 = new JButton();
			calendarButton1.setIcon(new ImageIcon(AddDialog.class
					.getResource("/resources/JDateChooserColor16.gif")));
			calendarButton1.addActionListener(new ActionListener() {
				public void actionPerformed(final ActionEvent arg0) {
					do_btnNewButton_actionPerformed(arg0);
				}
			});
		}
		return calendarButton1;
	}

	private JButton getCalendarButton2() {
		if (calendarButton2 == null) {
			calendarButton2 = new JButton();
			calendarButton2.setIcon(new ImageIcon(AddDialog.class
					.getResource("/resources/JDateChooserColor16.gif")));
			calendarButton2.addActionListener(new ActionListener() {
				public void actionPerformed(final ActionEvent e) {
					do_calendarButton2_actionPerformed(e);
				}
			});
		}
		return calendarButton2;
	}

	protected void do_btnNewButton_actionPerformed(final ActionEvent arg0) {

		dateChoice1.setText(new DateDialog().getDate().toString());
	}

	protected void do_calendarButton2_actionPerformed(final ActionEvent e) {
		dateChoice2.setText(new DateDialog().getDate().toString());
	}

	private JPanel getPanel_1() {
		if (panel_1 == null) {
			panel_1 = new JPanel();
			panel_1.setBorder(new TitledBorder(null, "Volby",
					TitledBorder.LEFT, TitledBorder.TOP, null, Color.WHITE));
			panel_1.setBackground(UIManager
					.getColor("Button.disabledForeground"));

			resetButton = new JButton("Resetovat");
			resetButton.addActionListener(new ActionListener() {
				public void actionPerformed(final ActionEvent arg0) {
					do_resetButton_actionPerformed(arg0);
				}
			});
			resetButton.setFont(new Font("Dialog", Font.BOLD, 13));
			GroupLayout gl_panel_1 = new GroupLayout(panel_1);
			gl_panel_1
					.setHorizontalGroup(gl_panel_1
							.createParallelGroup(Alignment.TRAILING)
							.addGroup(
									gl_panel_1
											.createSequentialGroup()
											.addContainerGap()
											.addGroup(
													gl_panel_1
															.createParallelGroup(
																	Alignment.LEADING)
															.addComponent(
																	getSelectCheckBox(),
																	Alignment.TRAILING)
															.addGroup(
																	Alignment.TRAILING,
																	gl_panel_1
																			.createSequentialGroup()
																			.addComponent(
																					getConfirmButton(),
																					GroupLayout.DEFAULT_SIZE,
																					86,
																					Short.MAX_VALUE)
																			.addGap(10)
																			.addComponent(
																					resetButton)))
											.addContainerGap()));
			gl_panel_1
					.setVerticalGroup(gl_panel_1
							.createParallelGroup(Alignment.LEADING)
							.addGroup(
									gl_panel_1
											.createSequentialGroup()
											.addComponent(getSelectCheckBox())
											.addGap(13)
											.addGroup(
													gl_panel_1
															.createParallelGroup(
																	Alignment.TRAILING)
															.addComponent(
																	resetButton,
																	GroupLayout.PREFERRED_SIZE,
																	38,
																	GroupLayout.PREFERRED_SIZE)
															.addComponent(
																	getConfirmButton(),
																	GroupLayout.PREFERRED_SIZE,
																	38,
																	GroupLayout.PREFERRED_SIZE))
											.addContainerGap()));
			panel_1.setLayout(gl_panel_1);
		}
		return panel_1;
	}

	private JCheckBox getSelectCheckBox() {
		if (selectCheckBox == null) {
			selectCheckBox = new JCheckBox("Pracovat nad změnami");
			selectCheckBox.setFont(new Font("Dialog", Font.BOLD, 14));
			selectCheckBox.setForeground(Color.WHITE);
			selectCheckBox.setBackground(UIManager
					.getColor("Button.disabledForeground"));
		}
		return selectCheckBox;
	}

	private JButton getConfirmButton() {
		if (confirmButton == null) {
			confirmButton = new JButton("Potvrdit");
			confirmButton.addActionListener(new ActionListener() {
				public void actionPerformed(final ActionEvent arg0) {
					do_confirmButton_actionPerformed(arg0);
				}
			});
			confirmButton.setFont(new Font("Dialog", Font.BOLD, 13));
		}
		return confirmButton;
	}

	protected void do_confirmButton_actionPerformed(final ActionEvent arg0) {

		Double num1, num2;

		try {
			num2 = Double.parseDouble(moneyChoice2.getText());
		} catch (NumberFormatException e) {
			num2 = null;
		}

		try {
			num1 = Double.parseDouble(moneyChoice1.getText());
		} catch (NumberFormatException e) {
			num1 = null;
		}

		tm.selectByParams(SimpleDate.getDate(dateChoice1.getText()),
				SimpleDate.getDate(dateChoice2.getText()),
				(String) typeChoice1.getSelectedItem(),
				(String) typeChoice2.getSelectedItem(), num1, num2,
				selectCheckBox.isSelected());

	}

	protected void do_resetButton_actionPerformed(final ActionEvent e) {
		tm.selectByParams(null, null, null, null, null, null, false);
	}

	private JPanel getPanelSummary() {
		if (panelSummary == null) {
			panelSummary = new JPanel();
			panelSummary.setBackground(Color.DARK_GRAY);
			panelSummary.setLayout(new BorderLayout(0, 0));

			panel_2 = new JPanel();
			panel_2.setBorder(new LineBorder(Color.DARK_GRAY, 5));
			panelSummary.add(panel_2);

			panel_3 = new JPanel();
			panel_3.setBorder(new LineBorder(Color.DARK_GRAY, 5));
			panelSummary.add(panel_3, BorderLayout.SOUTH);
			panel_3.setLayout(new GridLayout(3, 0, 0, 0));

			btnNewButton_2 = new JButton("New button");
			panel_3.add(btnNewButton_2);

			separator = new JSeparator();
			panel_3.add(separator);

			btnNewButton_1 = new JButton("New button");
			panel_3.add(btnNewButton_1);

			panel_4 = new JPanel();
			panel_4.setBorder(new LineBorder(Color.DARK_GRAY, 5));
			panelSummary.add(panel_4, BorderLayout.EAST);

			btnNewButton = new JButton("New button");
			panel_4.add(btnNewButton);
		}
		return panelSummary;
	}

	protected void do_typeChoice1_actionPerformed(final ActionEvent arg0) {
		if (typeChoice1.getSelectedIndex() == 0)
			typeChoice1.setSelectedIndex(-1);
	}

	protected void do_typeChoice2_actionPerformed(final ActionEvent e) {
		if (typeChoice2.getSelectedIndex() == 0)
			typeChoice2.setSelectedIndex(-1);
	}

	class MyMoneyCellrender extends DefaultTableCellRenderer {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1122257698594749884L;

		public MyMoneyCellrender() {
			super();
			setOpaque(true);
			setFont(tableFont);

		}

		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {

			String s = ((String) value);
			Double d = Double.parseDouble(s);

			if (isSelected) {
				setBackground(sBackground);
				setForeground(sForeground);
			} else {
				setBackground(nsBackground);
				setForeground(nsForeground);
			}

			if (d < 0) {
				this.setForeground(Color.RED);
				setText(" " + new DecimalFormat("#####0.00").format(d));
			} else {
				this.setForeground(Color.BLACK);
				setText("  " + new DecimalFormat("#####0.00").format(d));
			}

			return this;

		}

	}

	class MyDateCellrender extends DefaultTableCellRenderer {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1122257698594749884L;

		public MyDateCellrender() {
			super();
			setOpaque(true);
			setFont(tableFont);

		}

		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {

			if (isSelected) {
				setBackground(sBackground);
				setForeground(sForeground);
			} else {
				setBackground(nsBackground);
				setForeground(nsForeground);
			}

			setText(" " + (String) value);

			return this;

		}

	}

	class MyStringCellrender extends DefaultTableCellRenderer {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1122257698594749884L;

		public MyStringCellrender() {
			super();
			setOpaque(true);
			setFont(tableFont);
		}

		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {

			if (isSelected) {
				setBackground(sBackground);
				setForeground(sForeground);
			} else {
				setBackground(nsBackground);
				setForeground(nsForeground);
			}

			setText(" " + (String) value);

			return this;

		}

	}

	class MyHeaderDefaultCellRender extends DefaultTableCellRenderer {

		/**
		 * 
		 */
		private static final long serialVersionUID = -5404209565819819776L;

		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			// TODO Auto-generated method stub
			super.getTableCellRendererComponent(table, value, isSelected,
					hasFocus, row, column);

			setBackground(Color.DARK_GRAY);
			setForeground(Color.WHITE);
			setFont(new Font("Dialog", Font.BOLD, 14));
			return this;
		}
	}

}

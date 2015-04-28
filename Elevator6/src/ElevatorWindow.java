import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.ToolTipManager;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.plaf.ToolTipUI;
import javax.swing.plaf.multi.MultiButtonUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.Color;

import javax.swing.JButton;

import java.awt.SystemColor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.swing.ScrollPaneConstants;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.DropMode;
import javax.swing.JTextArea;
import javax.swing.JComboBox;

import org.elevator.warn.FormatException;
import org.elevator.warn.Warnning;

import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import javax.swing.JProgressBar;
import javax.swing.JFormattedTextField;
import javax.swing.JEditorPane;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JSpinner;
import java.awt.Component;


public class ElevatorWindow extends JFrame {
	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel model;
	private JButton ConfirmButton;
	private JButton AddButton;
	private JButton ClearButton;
	private JTextField textField;
	private JComboBox comboBox_Direction,comboBox_State,comboBox_Number;
	private RequestQueue RequestQ = new RequestQueue();
	private static JTextArea textArea1;
	private static JTextArea textArea2;
	private static JTextArea textArea3;
	private JComboBox comboBox_ID;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ElevatorWindow frame = new ElevatorWindow();
					frame.setVisible(true);
				} catch (Throwable e) {
					Warnning frame = new Warnning(e);
					frame.setVisible(true);
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	public ElevatorWindow() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		
		UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		
		Toolkit kit=Toolkit.getDefaultToolkit();
		Image icon  =kit.getImage("img/icon.png");
		
		ImageIcon background = new ImageIcon("img/background.png");
		//background for JFrame.
		this.setIconImage(icon);
		this.setResizable(false);
		this.setTitle("Elevator System");
		
		JLabel ImageLabel = new JLabel(background);
		ImageLabel.setBounds(0, 0, background.getIconWidth(), background.getIconHeight());
		

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setBounds(100, 100, 761, 527);
		this.getLayeredPane().add(ImageLabel, new Integer(Integer.MIN_VALUE));
		

		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.textHighlightText);
		contentPane.setBounds(0,0,background.getIconWidth(),background.getIconHeight());
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);
		contentPane.setOpaque(false);
		
		
		
		/********************************* Tabel *******************************************************/

		DefaultTableModel model = new DefaultTableModel(new String[]{"Requests"},0){
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        }; 
		contentPane.setLayout(null);
		table = new JTable(model);
		table.setFont(new Font("Consolas", Font.PLAIN, 14));
		table.setForeground(new Color(0, 128, 0));
		table.setSurrendersFocusOnKeystroke(true);
		table.setBackground(SystemColor.window);
		table.getColumnModel().setColumnMargin(0);
		
		//set scrollPane <- -> for JTable
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(389, 92, 250, 151);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.getViewport().setBackground(Color.WHITE);
		
		contentPane.add(scrollPane);
		
		/*******************MouseListener for table to display ToolTiptext************************/
		table.addMouseListener(new MouseListener() {
			public void mouseEntered(MouseEvent e) {
				Point mousepoint;
				mousepoint =e.getPoint();
				// TODO Auto-generated method stub
				if(table.rowAtPoint(mousepoint)!=-1 && table.getValueAt(table.rowAtPoint(mousepoint),0)!=null){
					table.setToolTipText(table.getValueAt(table.rowAtPoint(mousepoint),0).toString());
				}
			}
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
			}
		});
		
		/*********************************  Button    *******************************************/
		
		AddButton = new JButton("Add");
		AddButton.setBounds(89, 137, 110, 41);
		AddButton.setFont(new Font("Cambria Math", Font.PLAIN, 18));
		AddButton.setForeground(SystemColor.textText);
		AddButton.setBackground(SystemColor.window);
		AddButton.setFocusPainted(false);
		contentPane.add(AddButton);
		
		ConfirmButton = new JButton("Confirm");
		ConfirmButton.setBounds(143, 188, 153, 55);
		ConfirmButton.setForeground(Color.BLACK);
		ConfirmButton.setFont(new Font("Cambria Math", Font.BOLD, 18));
		ConfirmButton.setFocusPainted(false);
		ConfirmButton.setBackground(SystemColor.menu);
		contentPane.add(ConfirmButton);
		
		ClearButton = new JButton("Clear");
		ClearButton.setBounds(219, 137, 130, 41);
		ClearButton.setForeground(Color.BLACK);
		ClearButton.setFont(new Font("Cambria Math", Font.PLAIN, 18));
		ClearButton.setFocusPainted(false);
		ClearButton.setBackground(SystemColor.textHighlightText);
		contentPane.add(ClearButton);
		
		//Add function 
		AddButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(RequestQ.TimeCheck(textField.getText())){
						switch(comboBox_State.getSelectedIndex()){
						case 0://F_R
							Request one = new Request("F_R",comboBox_Number.getSelectedIndex()+1,(String)comboBox_Direction.getSelectedItem(),Double.parseDouble(textField.getText()));
							RequestQ.AddRequest(one);
							String[] a={"F_R Floor:"+(comboBox_Number.getSelectedIndex()+1)+" "+comboBox_Direction.getSelectedItem()+" t:"+textField.getText()};
							((DefaultTableModel)table.getModel()).addRow(a);
							break;
						case 1://E_R
							Request one2 = new Request("E_R",comboBox_Number.getSelectedIndex()+1,comboBox_ID.getSelectedIndex()+1,Double.parseDouble(textField.getText()));
							RequestQ.AddRequest(one2);
							String[] b={"E_R Floor:"+(comboBox_Number.getSelectedIndex()+1)+" EID:"+(comboBox_ID.getSelectedIndex()+1)+" t:"+textField.getText()};
							((DefaultTableModel)table.getModel()).addRow(b);
							break;
						case 2://leave
							Request one3 = new Request("leave",comboBox_ID.getSelectedIndex()+1,Double.parseDouble(textField.getText()));
							RequestQ.AddRequest(one3);
							String[] c={"leave  EID:"+(comboBox_ID.getSelectedIndex()+1)+" t:"+textField.getText()};
							((DefaultTableModel)table.getModel()).addRow(c);
							break;
						case 3://join
							Request one4 = new Request("join",comboBox_ID.getSelectedIndex()+1,Double.parseDouble(textField.getText()));
							RequestQ.AddRequest(one4);
							String[] d={"join   EID:"+(comboBox_ID.getSelectedIndex()+1)+" t:"+textField.getText()};
							((DefaultTableModel)table.getModel()).addRow(d);
							break;
						}
					}
					else{
						System.out.println("The time input is not valid!");
					}
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (FormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		ClearButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for(int i=0;i<table.getRowCount();){				
					((DefaultTableModel)table.getModel()).removeRow(i);
				}
				RequestQ.RemoveQueue();
				textArea1.setText("");
				textArea2.setText("");
				textArea3.setText("");
			}
		});
		
		
		ConfirmButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Elevator[] elevators = new Elevator[3];
					elevators[0] = new Elevator(1,textArea1);
					elevators[1] = new Elevator(2,textArea2);
					elevators[2] = new Elevator(3,textArea3);
					textArea1.setText("");
					textArea2.setText("");
					textArea3.setText("");
					Scheduler sche = new Scheduler(RequestQ,elevators);
					sche.Schedule();
				} catch (Exception e1) {
					Warnning a = new Warnning(e1);
					a.setVisible(true);
				}
			}
		});
		
		/*******************************     comboBox      *************************************/
		
		String[] State = {"F_R",
				"E_R","leave","join"};
		comboBox_State = new JComboBox();
		comboBox_State.setModel(new DefaultComboBoxModel(State));
		comboBox_State.setBounds(84, 93, 77, 34);
		comboBox_State.setFont(new Font("Consolas", Font.PLAIN, 14));
		comboBox_State.setBackground(SystemColor.window);
		comboBox_State.setFocusable(false);
		contentPane.add(comboBox_State);
		
		String[] Direction = {"UP"};
		comboBox_Direction = new JComboBox(Direction);
		comboBox_Direction.setBackground(SystemColor.window);
		comboBox_Direction.setBounds(229, 93, 63, 34);
		comboBox_Direction.setFont(new Font("Consolas", Font.PLAIN, 12));
		comboBox_Direction.setFocusable(false);
		contentPane.add(comboBox_Direction);
		
		String[] Number = {"1","2","3","4","5","6","7","8","9","10"};
		comboBox_Number = new JComboBox(Number);
		comboBox_Number.setBackground(SystemColor.window);
		comboBox_Number.setBounds(175, 93, 40, 34);
		comboBox_Number.setFont(new Font("Consolas", Font.PLAIN, 12));
		comboBox_Number.setFocusable(false);
		contentPane.add(comboBox_Number);
		
		String[] ID = {"1","2","3"};
		comboBox_ID = new JComboBox(ID);
		comboBox_ID.setFont(new Font("Consolas", Font.PLAIN, 12));
		comboBox_ID.setFocusable(false);
		comboBox_ID.setBackground(Color.WHITE);
		comboBox_ID.setBounds(229, 93, 63, 34);
		contentPane.add(comboBox_ID);
		
		comboBox_State.addActionListener(new ActionListener()
		{		
			@Override
			public void actionPerformed(ActionEvent e) {
				if(comboBox_State.getSelectedIndex()==0)/*F_R*/{
					comboBox_Number.setVisible(true);
					comboBox_Direction.setVisible(true);
					comboBox_ID.setVisible(false);
				}
				else if(comboBox_State.getSelectedIndex()==1)/*E_R*/{
					comboBox_Number.setVisible(true);
					comboBox_Direction.setVisible(false);
					comboBox_ID.setVisible(true);
				}
				else{/*leave,join*/
					comboBox_ID.setVisible(true);
					comboBox_Number.setVisible(false);
					comboBox_Direction.setVisible(false);
				}
				
			}
		});
		
		comboBox_Number.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(comboBox_Number.getSelectedIndex()==0){
				//if you request to 1, you just have UP 
					comboBox_Direction.removeAllItems();
					comboBox_Direction.addItem("UP");
					}
				else if(comboBox_Number.getSelectedIndex()==9){
					comboBox_Direction.removeAllItems();
					comboBox_Direction.addItem("DOWN");
				}
				else{
					comboBox_Direction.removeAllItems();
					comboBox_Direction.addItem("UP");
					comboBox_Direction.addItem("DOWN");
				}
			}
		});

		/************************************ Text Field for t *************************************/
		

		
		textField = new JTextField();
		textField.setBounds(300, 93, 57, 34);
		textField.setFont(new Font("Consolas", Font.PLAIN, 12));
		textField.setToolTipText("输入格式不要太调皮哦！");
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setForeground(Color.BLACK);
		textField.setColumns(10);
		textField.setBorder(new LineBorder(Color.LIGHT_GRAY));
		textField.setBackground(Color.WHITE);
		contentPane.add(textField);
		
		
		/*********************************** TextArea and Label ***********************************/

		textArea1 = new JTextArea();
		textArea1.setFont(new Font("Consolas", Font.PLAIN, 12));
		textArea1.setForeground(new Color(153, 102, 204));
		textArea1.setEditable(false);
		
		textArea2 = new JTextArea();
		textArea2.setForeground(new Color(204, 102, 255));
		textArea2.setFont(new Font("Consolas", Font.PLAIN, 12));
		textArea2.setEditable(false);
		
		textArea3 = new JTextArea();
		textArea3.setForeground(new Color(153, 102, 204));
		textArea3.setFont(new Font("Consolas", Font.PLAIN, 12));
		textArea3.setEditable(false);
		
		JScrollPane scrollPaneTextArea1 = new JScrollPane(textArea1);
		scrollPaneTextArea1.setBounds(31, 287, 210, 177);
		contentPane.add(scrollPaneTextArea1);
		
		JScrollPane scrollPaneTextArea2 = new JScrollPane(textArea2);
		scrollPaneTextArea2.setBounds(258, 287, 210, 177);
		contentPane.add(scrollPaneTextArea2);
		
		JScrollPane scrollPaneTextArea3 = new JScrollPane(textArea3);
		scrollPaneTextArea3.setBounds(485, 287, 210, 177);
		contentPane.add(scrollPaneTextArea3);
		
		JLabel lblNewLabel = new JLabel("Elevator 1");
		lblNewLabel.setForeground(new Color(255, 102, 51));
		lblNewLabel.setFont(new Font("Impact", Font.PLAIN, 25));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(73, 253, 110, 32);
		contentPane.add(lblNewLabel);
		
		JLabel lblElevator = new JLabel("Elevator 2");
		lblElevator.setForeground(new Color(255, 102, 153));
		lblElevator.setHorizontalAlignment(SwingConstants.CENTER);
		lblElevator.setFont(new Font("Impact", Font.PLAIN, 25));
		lblElevator.setBounds(300, 253, 110, 32);
		contentPane.add(lblElevator);
		
		JLabel lblElevator_1 = new JLabel("Elevator 3");
		lblElevator_1.setForeground(Color.PINK);
		lblElevator_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblElevator_1.setFont(new Font("Impact", Font.PLAIN, 25));
		lblElevator_1.setBounds(529, 253, 110, 32);
		contentPane.add(lblElevator_1);
		
	}
}
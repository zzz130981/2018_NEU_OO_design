package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import entity.Treat;
import dao.TreatDao;
import dao.TreatDaoImpl;
public class AdminTreatMangeView extends JInternalFrame {

	private static final long serialVersionUID = 1L;

    private JPanel panelMain; // �����

    private JPanel panelTop; // �������ϲ�
    private JPanel panelSearch; // �������
    private JLabel labKey; // �ؼ���
    private JTextField tfSearch; // ������
    private JButton btnSearch; // ���Ұ�ť
    private JButton btnRefresh;

    private JPanel panelSearchType; // ��ʲô����
    private ButtonGroup buttonGroup = new ButtonGroup();
    private JRadioButton[] btnSearchTypes = new JRadioButton[2]; // ����������������ԣ���𣬸���
    private JLabel labSearch;

    private JScrollPane panelTable; // ������
    private myTableModel tableModel;// ������ģ�Ͷ���
    private JTable table;// ���������

    private JPanel panelBottom; // �ײ���壬�����������ӡ�ɾ�����޸�
    private JPanel panelTextField;
    private JPanel panelButton;
    
    private JLabel labTreatID;
    private JLabel labPersonID;
    private JLabel labhLevel;
    private JLabel labhNumber;
    private JLabel labhName;
    private JLabel labDoor;
    private JLabel labName;
    
    private JTextField tfTreatID; // ��������
    private JTextField tfPersonID; // �������
    private JTextField tfhLevel; // ����
    private JTextField tfhNumber; // �����������
    private JTextField tfhName;
    private JTextField tfDoor;
    private JTextField tfName;
    
    private JButton btnPrescribeID;
    private JButton btnAdd; // ��Ӱ�ť
    private JButton btnEdit; // �޸İ�ť
    private JButton btnDelete; // ɾ����ť

    private TreatDao treatDao;

    public AdminTreatMangeView() {
    	treatDao = new TreatDaoImpl();
        init();
        getTableSelectedRow();
        addTableRow();
        editTableRow();
        deleteTableRow();
        getSearchTypeValue();
        displayMessage();
        find();
        TreatID();
        //refresh();
        System.out.println("songmanagerView");
    }

    /**
     * ��ʼ�������
     */
    private void init() {
        this.setTitle("������Ϣ����");
        this.setSize(new Dimension(750, 550));
        this.setIconifiable(true); // ������С��
        this.setClosable(true); // �ɱ��ر�
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE); // �˳�
        panelMain = new JPanel(new BorderLayout());

        panelTop = new JPanel(new GridLayout(2, 1, 0, 0));
        panelTop.setLocation(0, 20);
        panelSearch = new JPanel();
        panelSearch.setPreferredSize(new Dimension(750, 30));
        labKey = new JLabel("�ؼ���");
        tfSearch = new JTextField("�����봦��ID",20);
        tfSearch.setPreferredSize(new Dimension(100, 20));
        btnSearch = new JButton("����");
        btnSearch.setPreferredSize(new Dimension(60, 20));
        btnRefresh = new JButton("ˢ��");
        btnRefresh.setPreferredSize(new Dimension(60, 20));
        panelSearch.add(new JLabel());
        panelSearch.add(labKey);
        panelSearch.add(tfSearch);
        panelSearch.add(new JLabel());
        panelSearch.add(btnSearch);
        panelSearch.add(new JLabel());
        panelSearch.add(btnRefresh);
        panelTop.add(panelSearch);

        panelSearchType = new JPanel();
        panelSearchType.setPreferredSize(new Dimension(750, 30));
        labSearch = new JLabel("��ѯ");
        panelSearchType.add(labSearch);
        btnSearchTypes[0] = new JRadioButton("����ID", true);
        btnSearchTypes[1] = new JRadioButton("��ԱID");
        //btnSearchTypes[2] = new JRadioButton("ҩƷID");
        //btnSearchTypes[3] = new JRadioButton("����");

        // System.out.println("����"+btnSearchTypes[1].getText());
        for (JRadioButton jRadioButton : btnSearchTypes) {
            buttonGroup.add(jRadioButton);
            panelSearchType.add(jRadioButton);
        }
        panelTop.add(panelSearchType);
        panelMain.add(panelTop, BorderLayout.NORTH);

        String[] columnNames = { "�����¼���", "����ID", "ҽԺ�ȼ�", "ҽԺ���" ,"ҽԺ����" , "�����" , "��������"};
        // �����ݿ��в�ѯ��������
        ArrayList<Treat> treats = treatDao.findAll();
        //System.out.println(prescribes);
        String[][] tableValues = new String[treats.size()][7];
        for (int i = 0; i < treats.size(); i++) {
            tableValues[i][0] = treats.get(i).getTreatID();
            tableValues[i][1] = treats.get(i).getPersonID();
            tableValues[i][2] = treats.get(i).gethLevel();
            tableValues[i][3] = treats.get(i).gethNumber();
            tableValues[i][4] = treats.get(i).gethName();
            tableValues[i][5] = treats.get(i).getDoor();
            tableValues[i][6] = treats.get(i).getName();
            // System.out.println(tableValues[i][0]);
        }
        tableModel = new myTableModel(tableValues, columnNames);
        tableModel.isCellEditable(0, 0);
        table = new JTable(tableModel);
//        table.setRowSorter(new TableRowSorter<>(tableModel)); // ��������
        table.setRowHeight(20); // �����и�
        table.getTableHeader().setBackground(Color.GRAY); // ��ͷ��ɫ
        table.setLocation(20, 80);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // ֻ��ѡ�е���
//        table.setEnabled(false); // ���ñ��Ϊ���ɱ༭
        panelTable = new JScrollPane(table);
        panelTable.setBorder(BorderFactory.createTitledBorder("������Ϣ"));
        panelMain.add(panelTable, BorderLayout.CENTER);

        panelBottom = new JPanel(new GridLayout(2, 1, 0, 0));
        panelTextField = new JPanel();
        panelButton = new JPanel();
        
        labTreatID = new JLabel("����");
        tfTreatID = new JTextField(4);
        labPersonID = new JLabel("��ԱID");
        tfPersonID= new JTextField(5);
        labhLevel = new JLabel("ҽԺ�ȼ�");
        tfhLevel = new JTextField(3);
        labhNumber = new JLabel("ҽԺ���");
        tfhNumber = new JTextField(5);
        labhName = new JLabel("ҽԺ����");
        tfhName = new JTextField(7);
        labDoor = new JLabel("�����");
        tfDoor = new JTextField(2);
        labName = new JLabel("��������");
        tfName = new JTextField(3);
        
        panelTextField.add(labTreatID);
        panelTextField.add(tfTreatID);
        panelTextField.add(labPersonID);
        panelTextField.add(tfPersonID);
        panelTextField.add(labhLevel);
        panelTextField.add(tfhLevel);
        panelTextField.add(labhNumber);
        panelTextField.add(tfhNumber);
        panelTextField.add(labhName);
        panelTextField.add(tfhName);
        panelTextField.add(labDoor);
        panelTextField.add(tfDoor);
        panelTextField.add(labName);
        panelTextField.add(tfName);
        
        btnPrescribeID = new JButton("����ID");
        btnAdd = new JButton("����");
        btnEdit = new JButton("�޸�");
        btnDelete = new JButton("ɾ��");
        panelButton.add(btnPrescribeID);
        panelButton.add(btnAdd);
        panelButton.add(btnEdit);
        panelButton.add(btnDelete);
        panelBottom.add(panelTextField);
        panelBottom.add(panelButton);
        panelMain.add(panelBottom, BorderLayout.SOUTH);
        
        this.getContentPane().add(panelMain);
        this.setVisible(true);
        // System.out.println("1234");
    }
    /**
     * ���һ��
     */
    private void addTableRow() {
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // String[] rowValues = {"add",tfName.getText(),tfLanguage.getText(),
                // tfCategory.getText(),tfSinger.getText()};
            	String treatID = tfTreatID.getText().trim();
            	
            	String personID = tfPersonID.getText().trim();
                String hLevel =  tfhLevel.getText().trim();
                String hNumber = tfhNumber.getText().trim();
                String hName = tfhName.getText().trim();
                String door = tfDoor.getText().trim();
                String name = tfName.getText().trim();
                if (name.equals("") || name == null 
                        /*|| language.equals("") || language == null
                        || category.equals("") || category == null
                        || singer.equals("") || singer == null*/) {
                    JOptionPane.showMessageDialog(panelTable, "����Ϊ��", "��Ϣ��ʾ��", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                Treat treat = new Treat(treatID,personID,hLevel,hNumber,hName,door,name);
                //Person Person = new Person(PersonID,ID_type,ID_number,name,sex,ethnicity,birthday);
                String[] rowValues = new String[7];
                if (treatDao.addTreat(treat)) {
                    JOptionPane.showMessageDialog(panelMain, "��ӳɹ�", "��Ϣ��ʾ��", JOptionPane.INFORMATION_MESSAGE);
                    rowValues[0] = treat.getTreatID();
                    rowValues[1] = treat.getPersonID();
                    rowValues[2] = treat.gethLevel();
                    rowValues[3] = treat.gethNumber();
                    rowValues[4] = treat.gethName();
                    rowValues[5] = treat.getDoor();
                    rowValues[6] = treat.getName();
                    //rowValues[7] = Person.getId();
                    // ����������
                    tfTreatID.setText("");
                    tfPersonID.setText("");
                    tfhLevel.setText("");
                    tfhNumber.setText("");
                    tfhName.setText("");
                    tfDoor.setText("");
                    tfName.setText("");
                    
                    // ���������м�������
                    tableModel.addRow(rowValues);
                } else {
                    JOptionPane.showMessageDialog(panelTable, "���ʧ��", "��Ϣ��ʾ��", JOptionPane.INFORMATION_MESSAGE);
                }
                
            }
        });
    }
    /**
     * �޸���Ϣ
     */
    
    private void editTableRow() {
        btnEdit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow(); // ��ȡ��ѡ���е�����
                //Person Person = new Person();
                //Person Person = new Person();
                System.out.println("�޸ĵڼ��У�" + selectedRow);
                Treat treat = new Treat();
                if (selectedRow != -1) { // �ж��Ƿ���ڱ�ѡ����
                    System.out.println("��һ����"+tableModel.getValueAt(selectedRow, 0));
                    treat.setTreatID(tableModel.getValueAt(selectedRow, 0).toString());
                    String prescribeID = treat.getTreatID();
                    treat.setPersonID(tfPersonID.getText());
                    treat.sethLevel(tfhLevel.getText());
                    treat.sethNumber(tfhNumber.getText());
                    treat.sethName(tfhName.getText());
                    treat.setDoor(tfDoor.getText());
                    treat.setName(tfName.getText());
                    if (treatDao.updateTreat(treat)) {
                    	treat = treatDao.findTreatByTreatID(prescribeID);
                        JOptionPane.showMessageDialog(panelMain, "�޸ĳɹ�", "��Ϣ��ʾ��", JOptionPane.INFORMATION_MESSAGE);
                        tableModel.setValueAt(treat.getTreatID(), selectedRow, 0);
                        tableModel.setValueAt(treat.getPersonID(), selectedRow, 1);
                        tableModel.setValueAt(treat.gethLevel(), selectedRow, 2);
                        tableModel.setValueAt(treat.gethNumber(), selectedRow, 3);
                        tableModel.setValueAt(treat.gethName(), selectedRow, 4);
                        tableModel.setValueAt(treat.getDoor(), selectedRow, 5);
                        tableModel.setValueAt(treat.getName(), selectedRow, 6);
                    } else {
                        JOptionPane.showMessageDialog(panelTable, "�޸�ʧ��", "��Ϣ��ʾ��", JOptionPane.INFORMATION_MESSAGE);
                    }

                }
            }
        });
        
    }
    /**
     * ɾ��һ����Ϣ
     */
    private void deleteTableRow() {
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow(); // ��ȡ��ѡ���е�����
                if (selectedRow != -1) {
                    String id = tableModel.getValueAt(selectedRow, 0).toString();
                    System.out.println(id);
                    if (treatDao.deletTreat(id)) {
                        JOptionPane.showMessageDialog(panelMain, "ɾ���ɹ�", "��Ϣ��ʾ��", JOptionPane.INFORMATION_MESSAGE);
                        // ����������
                        tfTreatID.setText("");
                        tfPersonID.setText("");
                        tfhLevel.setText("");
                        tfhNumber.setText("");
                        tfhName.setText("");
                        tfDoor.setText("");
                        tfName.setText("");
                        tableModel.removeRow(selectedRow);
                        
                    } else {
                        JOptionPane.showMessageDialog(panelTable, "ɾ��ʧ��", "��Ϣ��ʾ��", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });
    }
    //��ѯʵ�������ɣ�����ID+name ; name ; ID_number ; ����DI
    private void find() {
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ArrayList<Treat> treats = null;
                String type = "����ID";
                for (int i = 0; i < btnSearchTypes.length; i++) {
                    if (btnSearchTypes[i].isSelected()) {
                        type = btnSearchTypes[i].getText();
                        System.out.println(type+"**********");
                    }
                }
                if ("����ID".equals(type)) {
                    String perscribeID = tfSearch.getText();
                    //System.out.println("name:" + PersonID);
                    treats = treatDao.findByTreatID(perscribeID);
                    System.out.println("��ѯ������" + treats );
                } else if ("��ԱID".equals(type)) {
                    String personID = tfSearch.getText();
                    //System.out.println(name);
                    treats = treatDao.findByPersonID(personID);
                    //System.out.println("��ѯ������"+Persons);
                } else {
                    JOptionPane.showMessageDialog(panelMain, "��ѡ���ѯ����", "��Ϣ��ʾ��", JOptionPane.INFORMATION_MESSAGE);
                }
                fillPersoninfoToTable(treats);
            }
        });
    }
    // ��ѯʱʹ�ã���ձ�����ݣ������������
    private void fillPersoninfoToTable(ArrayList<Treat> prescrbies) {
        if (prescrbies.isEmpty()) {
            JOptionPane.showMessageDialog(panelMain, "Sorry, δ��ѯ�����ݣ����������", "��Ϣ��ʾ��", JOptionPane.INFORMATION_MESSAGE);
        }
        tableModel.setRowCount(0); // ��������
        String[][] tableValues = new String[prescrbies.size()][7];
        // ����װ������
        for (int j = 0; j < prescrbies.size(); j++) {
            tableValues[j][0] = prescrbies.get(j).getTreatID();
            tableValues[j][1] = prescrbies.get(j).getPersonID();
            tableValues[j][2] = prescrbies.get(j).gethLevel();
            tableValues[j][3] = prescrbies.get(j).gethNumber();
            tableValues[j][4] = prescrbies.get(j).gethName();
            tableValues[j][5] = prescrbies.get(j).getDoor();
            tableValues[j][6] = prescrbies.get(j).getName();
            // System.out.println(tableValues[j][0]);
            tableModel.addRow(tableValues[j]);
        }
    }
    /**
     * ��ȡ��ѡ���е�ֵ������ֵ����Ϣ�����뵽tfsearch�У�������ʾ��Ϣ
     */
    private void getSearchTypeValue() {
        for (JRadioButton jRadioButton : btnSearchTypes) {
            jRadioButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jRadioButton.setSelected(true); // ����jRadioButton����Ϊѡ��״̬
                    String type = jRadioButton.getText();
                    tfSearch.setText("������" + type);
                    // System.out.println(type);
                }
            });
        }
    }
    //��ȡѡ�еı���һ��
    private void getTableSelectedRow() {
        table.addMouseListener(new MouseAdapter() {
            // �����˵���¼�
            public void mouseClicked(MouseEvent e) {
                // ��ñ�ѡ���е�����
                int selectedRow = table.getSelectedRow();
                System.out.println("���Ѿ�����" + selectedRow);
                // �ӱ��ģ���л��ָ����Ԫ���ֵ
                Object treatID = tableModel.getValueAt(selectedRow, 0);
                Object personID = tableModel.getValueAt(selectedRow, 1);
                
                Object hLevel = tableModel.getValueAt(selectedRow, 2);
                Object hNumber = tableModel.getValueAt(selectedRow, 3);
                
                Object hName = tableModel.getValueAt(selectedRow, 4);
                Object door = tableModel.getValueAt(selectedRow, 5);
                Object name = tableModel.getValueAt(selectedRow, 6);
                //PersonID,ID_type,ID_number,name,sex,ethnicity,birthday;
                tfTreatID.setText(treatID.toString());
                tfPersonID.setText(personID.toString());
                
                tfhLevel.setText(hLevel.toString());
                tfhNumber.setText(hNumber.toString());
                
                tfhName.setText(hName.toString());
                tfDoor.setText(door.toString());
                tfName.setText(name.toString());
                //tfLanguage.setText(language.toString());
                //tfCategory.setText(category.toString());
                //tfSinger.setText(singer.toString());
            }
        });
    }
    // ������������ʾ��Ϣ������������search�����ʱ������������
    private void displayMessage() {
        tfSearch.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                tfSearch.setText("");
            }
        });
    }
    private class myTableModel extends DefaultTableModel {
        
        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        public myTableModel(Object[][] data, Object[] columnNames) {
            super(data, columnNames);
            // TODO Auto-generated constructor stub
        }

        // �ñ�񲻿ɱ༭����д��DefaultTableModel�еķ���
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }
    public void TreatID() {
    	btnPrescribeID.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			int number = treatDao.findAll().size() + 1;
    			String ans = "R";
    			if(number / 10 != 0) {
    				ans += "00" + number;
    			} else {
    				ans += "000" + number;
    			}
    			tfTreatID.setText(ans);
    		}
    	});
    }
   

}

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

import entity.Person;
import service.PersonService;
import service.PersonServiceImpl;
public class AdminPersonManageView extends JInternalFrame {

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
    private JRadioButton[] btnSearchTypes = new JRadioButton[3]; // ����������������ԣ���𣬸���
    private JLabel labSearch;

    private JScrollPane panelTable; // ������
    private myTableModel tableModel;// ������ģ�Ͷ���
    private JTable table;// ���������

    private JPanel panelBottom; // �ײ���壬�����������ӡ�ɾ�����޸�
    private JPanel panelTextField;
    private JPanel panelButton;
    
    private JLabel labPersonID;
    private JLabel labID_type;
    private JLabel labID_number;
    private JLabel labName;
    private JLabel labSex;
    private JLabel labEthnicity;
    private JLabel labBirthday;
    
    private JTextField tfPersonID; // ��������
    private JTextField tfID_type; // �������
    private JTextField tfID_number; // ����
    private JTextField tfName; // �����������
    private JTextField tfSex;
    private JTextField tfEthnicity;
    private JTextField tfBirthday;
    
    private JButton btnPersonID;
    private JButton btnAdd; // ��Ӱ�ť
    private JButton btnEdit; // �޸İ�ť
    private JButton btnDelete; // ɾ����ť

    private PersonService PersonService;
    public AdminPersonManageView() {
        PersonService = new PersonServiceImpl();
        init();
        getTableSelectedRow();
        addTableRow();
        editTableRow();
        deleteTableRow();
        getSearchTypeValue();
        displayMessage();
        find();
        PersonID();
        //refresh();
        System.out.println("songmanagerView");
    }

    /**
     * ��ʼ�������
     */
    private void init() {
        this.setTitle("��Ա����");
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
        tfSearch = new JTextField("���������ID",20);
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
        btnSearchTypes[1] = new JRadioButton("����");
        btnSearchTypes[2] = new JRadioButton("���֤��");
        //btnSearchTypes[3] = new JRadioButton("����");

        // System.out.println("����"+btnSearchTypes[1].getText());
        for (JRadioButton jRadioButton : btnSearchTypes) {
            buttonGroup.add(jRadioButton);
            panelSearchType.add(jRadioButton);
        }
        panelTop.add(panelSearchType);
        panelMain.add(panelTop, BorderLayout.NORTH);

        String[] columnNames = { "����ID", "֤������", "֤������", "����", "�Ա�" ,"����" , "��������"};
        // �����ݿ��в�ѯ��������
        ArrayList<Person> Persons = PersonService.findAll();
        System.out.println(Persons);
        String[][] tableValues = new String[Persons.size()][7];
        for (int i = 0; i < Persons.size(); i++) {
            tableValues[i][0] = Persons.get(i).getPersonID();
            tableValues[i][1] = Persons.get(i).getID_type();
            tableValues[i][2] = Persons.get(i).getID_number();
            tableValues[i][3] = Persons.get(i).getName();
            tableValues[i][4] = Persons.get(i).getSex();
            tableValues[i][5] = Persons.get(i).getEthnicity();
            tableValues[i][6] = Persons.get(i).getBirthday();
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
        labName = new JLabel("����");
        tfName = new JTextField(5);
        labPersonID = new JLabel("����ID");
        tfPersonID = new JTextField(6);
        labID_type = new JLabel("֤������");
        tfID_type = new JTextField(5);
        labID_number = new JLabel("֤������");
        tfID_number = new JTextField(14);
        labSex = new JLabel("�Ա�");
        tfSex = new JTextField(2);
        labEthnicity = new JLabel("����");
        tfEthnicity = new JTextField(3);
        labBirthday = new JLabel("��������");
        tfBirthday = new JTextField(6);
        
        panelTextField.add(labName);
        panelTextField.add(tfName);
        panelTextField.add(labPersonID);
        panelTextField.add(tfPersonID);
        panelTextField.add(labID_type);
        panelTextField.add(tfID_type);
        panelTextField.add(labID_number);
        panelTextField.add(tfID_number);
        panelTextField.add(labSex);
        panelTextField.add(tfSex);
        panelTextField.add(labEthnicity);
        panelTextField.add(tfEthnicity);
        panelTextField.add(labBirthday);
        panelTextField.add(tfBirthday);
        
        btnPersonID = new JButton("����ID");
        btnAdd = new JButton("����");
        btnEdit = new JButton("�޸�");
        btnDelete = new JButton("ɾ��");
        panelButton.add(btnPersonID);
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
            	String PersonID = tfPersonID.getText().trim();
            	if(PersonService.findByPersonID(PersonID).size() != 0) {
            		JOptionPane.showMessageDialog(panelTable, "��ID�Ѵ��ڣ����������룡", "��Ϣ��ʾ��", JOptionPane.INFORMATION_MESSAGE);
            		return;
            	}
            	String ID_type = tfID_type.getText().trim();
            	String ID_number = tfID_number.getText().trim();
                String name =  tfName.getText().trim();
                String sex = tfSex.getText().trim();
                String ethnicity = tfEthnicity.getText().trim();
                String birthday = tfBirthday.getText().trim();
                if (name.equals("") || name == null 
                        /*|| language.equals("") || language == null
                        || category.equals("") || category == null
                        || singer.equals("") || singer == null*/) {
                    JOptionPane.showMessageDialog(panelTable, "����Ϊ��", "��Ϣ��ʾ��", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                Person Person = new Person(PersonID,ID_type,ID_number,name,sex,ethnicity,birthday);
                String[] rowValues = new String[7];
                if (PersonService.addPerson(Person)) {
                    JOptionPane.showMessageDialog(panelMain, "��ӳɹ�", "��Ϣ��ʾ��", JOptionPane.INFORMATION_MESSAGE);
                    rowValues[0] = Person.getPersonID();
                    rowValues[1] = Person.getID_type();
                    rowValues[2] = Person.getID_number();
                    rowValues[3] = Person.getName();
                    rowValues[4] = Person.getSex();
                    rowValues[5] = Person.getEthnicity();
                    rowValues[6] = Person.getBirthday();
                    //rowValues[7] = Person.getId();
                    // ����������
                    tfPersonID.setText("");
                    tfID_type.setText("");
                    tfID_number.setText("");
                    tfName.setText("");
                    tfSex.setText("");
                    tfEthnicity.setText("");
                    tfBirthday.setText("");
                    
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
                Person Person = new Person();
                if (selectedRow != -1) { // �ж��Ƿ���ڱ�ѡ����
                    System.out.println(tableModel.getValueAt(selectedRow, 0));
                    Person.setPersonID(tableModel.getValueAt(selectedRow, 0).toString());
                    String PersonID = Person.getPersonID();
                    Person.setID_type(tfID_type.getText());
                    Person.setID_number(tfID_number.getText());
                    Person.setName(tfName.getText());
                    Person.setSex(tfSex.getText());
                    Person.setEthnicity(tfEthnicity.getText());
                    Person.setBirthday(tfBirthday.getText());
                    if (PersonService.updatePerson(Person)) {
                        Person = PersonService.findPersonByPersonID(Person.getPersonID());
                        JOptionPane.showMessageDialog(panelMain, "�޸ĳɹ�", "��Ϣ��ʾ��", JOptionPane.INFORMATION_MESSAGE);
                        tableModel.setValueAt(Person.getPersonID(), selectedRow, 0);
                        tableModel.setValueAt(Person.getID_type(), selectedRow, 1);
                        tableModel.setValueAt(Person.getID_number(), selectedRow, 2);
                        tableModel.setValueAt(Person.getName(), selectedRow, 3);
                        tableModel.setValueAt(Person.getSex(), selectedRow, 4);
                        tableModel.setValueAt(Person.getEthnicity(), selectedRow, 5);
                        tableModel.setValueAt(Person.getBirthday(), selectedRow, 6);
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
                    if (PersonService.deletPerson(id)) {
                        JOptionPane.showMessageDialog(panelMain, "ɾ���ɹ�", "��Ϣ��ʾ��", JOptionPane.INFORMATION_MESSAGE);
                        // ����������
                        tfName.setText("");
                        tfPersonID.setText("");
                        tfSearch.setText("");
                        tfID_type.setText("");
                        tfID_number.setText("");
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
                ArrayList<Person> Persons = null;
                String type = "����";
                for (int i = 0; i < btnSearchTypes.length; i++) {
                    if (btnSearchTypes[i].isSelected()) {
                        type = btnSearchTypes[i].getText();
                        //System.out.println(type+"**********");
                    }
                }
                if ("����ID".equals(type)) {
                    String PersonID = tfSearch.getText();
                    //System.out.println("name:" + PersonID);
                    Persons = PersonService.findByPersonID(PersonID);
                    System.out.println("��ѯ������"+Persons);
                } else if ("����".equals(type)) {
                    String name = tfSearch.getText();
                    System.out.println(name);
                    Persons = PersonService.findByName(name);
                    System.out.println("��ѯ������"+Persons);
                } else if ("���֤��".equals(type)) {
                    String id_number= tfSearch.getText();
                    Persons = PersonService.findByID_number(id_number);
                    System.out.println("��ѯ������"+Persons);
                } else {
                    JOptionPane.showMessageDialog(panelMain, "��ѡ���ѯ����", "��Ϣ��ʾ��", JOptionPane.INFORMATION_MESSAGE);
                }
                fillPersoninfoToTable(Persons);
            }
        });
    }
    // ��ѯʱʹ�ã���ձ�����ݣ������������
    private void fillPersoninfoToTable(ArrayList<Person> Persons) {
        if (Persons.isEmpty()) {
            JOptionPane.showMessageDialog(panelMain, "Sorry, δ��ѯ�����ݣ����������", "��Ϣ��ʾ��", JOptionPane.INFORMATION_MESSAGE);
        }
        tableModel.setRowCount(0); // ��������
        String[][] tableValues = new String[Persons.size()][7];
        // ����װ������
        for (int j = 0; j < Persons.size(); j++) {
            tableValues[j][0] = Persons.get(j).getPersonID();
            tableValues[j][1] = Persons.get(j).getID_type();
            tableValues[j][2] = Persons.get(j).getID_number();
            tableValues[j][3] = Persons.get(j).getName();
            tableValues[j][4] = Persons.get(j).getSex();
            tableValues[j][5] = Persons.get(j).getEthnicity();
            tableValues[j][6] = Persons.get(j).getBirthday();
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
                Object PersonID = tableModel.getValueAt(selectedRow, 0);
                Object ID_type = tableModel.getValueAt(selectedRow, 1);
                Object ID_number = tableModel.getValueAt(selectedRow, 2);
                Object name = tableModel.getValueAt(selectedRow, 3);
                Object sex = tableModel.getValueAt(selectedRow, 4);
                Object ethnicity = tableModel.getValueAt(selectedRow, 5);
                Object birthday = tableModel.getValueAt(selectedRow, 6);
                tfPersonID.setText(PersonID.toString());
                tfID_type.setText(ID_type.toString());
                tfID_number.setText(ID_number.toString());
                tfName.setText(name.toString());
                tfSex.setText(sex.toString());
                tfEthnicity.setText(ethnicity.toString());
                tfBirthday.setText(birthday.toString());
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
    public void PersonID() {
    	btnPersonID.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			int number = PersonService.findAll().size() + 1;
    			String ans = "CN";
    			if(number / 10 != 0) {
    				ans += "000" + number;
    			} else {
    				ans += "0000" + number;
    			}
    			tfPersonID.setText(ans);
    		}
    	});
    }
   

}

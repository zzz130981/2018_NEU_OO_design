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

import entity.Prescribe;
import dao.PrescribeDao;
import dao.PrescribeDaoImpl;
public class AdminPrescribeManageView extends JInternalFrame {

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
    
    private JLabel labPrescribeID;
    private JLabel labPersonID;
    private JLabel labDrugID;
    private JLabel labName;
    private JLabel labPrice;
    private JLabel labNumber;
    private JLabel labTotal;
    
    private JTextField tfPrescribeID; // ��������
    private JTextField tfPersonID; // �������
    private JTextField tfDrugID; // ����
    private JTextField tfName; // �����������
    private JTextField tfNumber;
    private JTextField tfPrice;
    private JTextField tfTotal;
    
    private JButton btnPrescribeID;
    private JButton btnAdd; // ��Ӱ�ť
    private JButton btnEdit; // �޸İ�ť
    private JButton btnDelete; // ɾ����ť

    private PrescribeDao prescribeDao;

    public AdminPrescribeManageView() {
    	prescribeDao = new PrescribeDaoImpl();
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
        btnSearchTypes[1] = new JRadioButton("����ID");
        btnSearchTypes[2] = new JRadioButton("ҩƷID");
        //btnSearchTypes[3] = new JRadioButton("����");

        // System.out.println("����"+btnSearchTypes[1].getText());
        for (JRadioButton jRadioButton : btnSearchTypes) {
            buttonGroup.add(jRadioButton);
            panelSearchType.add(jRadioButton);
        }
        panelTop.add(panelSearchType);
        panelMain.add(panelTop, BorderLayout.NORTH);

        String[] columnNames = { "����ID", "����ID", "ҩƷID", "ҽ������", "����" ,"����" , "�ܼ�"};
        // �����ݿ��в�ѯ��������
        ArrayList<Prescribe> prescribes = prescribeDao.findAll();
        System.out.println(prescribes);
        String[][] tableValues = new String[prescribes.size()][7];
        for (int i = 0; i < prescribes.size(); i++) {
            tableValues[i][0] = prescribes.get(i).getPrescribeID();
            tableValues[i][1] = prescribes.get(i).getPersonID();
            tableValues[i][2] = prescribes.get(i).getDrugID();
            tableValues[i][3] = prescribes.get(i).getName();
            tableValues[i][4] = prescribes.get(i).getNumber();
            tableValues[i][5] = prescribes.get(i).getPrice();
            tableValues[i][6] = prescribes.get(i).getTotal();
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
        
        labPrescribeID = new JLabel("����ID");
        tfPrescribeID = new JTextField(6);
        labPersonID = new JLabel("��ԱID");
        tfPersonID= new JTextField(7);
        labDrugID = new JLabel("ҩƷID");
        tfDrugID = new JTextField(7);
        labName = new JLabel("ҽ������");
        tfName = new JTextField(5);
        labPrice = new JLabel("����");
        tfPrice = new JTextField(3);
        labNumber = new JLabel("����");
        tfNumber = new JTextField(6);
        labTotal = new JLabel("�ܼ�");
        tfTotal = new JTextField(5);
        
        panelTextField.add(labPrescribeID);
        panelTextField.add(tfPrescribeID);
        panelTextField.add(labPersonID);
        panelTextField.add(tfPersonID);
        panelTextField.add(labDrugID);
        panelTextField.add(tfDrugID);
        panelTextField.add(labName);
        panelTextField.add(tfName);
        panelTextField.add(labNumber);
        panelTextField.add(tfNumber);
        panelTextField.add(labPrice);
        panelTextField.add(tfPrice);
        panelTextField.add(labTotal);
        panelTextField.add(tfTotal);
        
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
            	String prescribeID = tfPrescribeID.getText().trim();
            	if(prescribeDao.findByPrescribeID(prescribeID).size() != 0) {
            		JOptionPane.showMessageDialog(panelTable, "��ID�Ѵ��ڣ����������룡", "��Ϣ��ʾ��", JOptionPane.INFORMATION_MESSAGE);
            		return;
            	}
            	
            	String personID = tfPersonID.getText().trim();
                String drugID =  tfDrugID.getText().trim();
                String name = tfName.getText().trim();
                String number = tfNumber.getText().trim();
                String price = tfPrice.getText().trim();
                String total = tfTotal.getText().trim();
                if (name.equals("") || name == null 
                        /*|| language.equals("") || language == null
                        || category.equals("") || category == null
                        || singer.equals("") || singer == null*/) {
                    JOptionPane.showMessageDialog(panelTable, "����Ϊ��", "��Ϣ��ʾ��", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                Prescribe prescribe = new Prescribe(prescribeID,personID,drugID,name,number,price,total);
                //Person Person = new Person(PersonID,ID_type,ID_number,name,sex,ethnicity,birthday);
                String[] rowValues = new String[7];
                if (prescribeDao.addPrescribe(prescribe)) {
                    JOptionPane.showMessageDialog(panelMain, "��ӳɹ�", "��Ϣ��ʾ��", JOptionPane.INFORMATION_MESSAGE);
                    rowValues[0] = prescribe.getPrescribeID();
                    rowValues[1] = prescribe.getPersonID();
                    rowValues[2] = prescribe.getDrugID();
                    rowValues[3] = prescribe.getName();
                    rowValues[4] = prescribe.getNumber();
                    rowValues[5] = prescribe.getPrice();
                    rowValues[6] = prescribe.getTotal();
                    //rowValues[7] = Person.getId();
                    // ����������
                    tfPrescribeID.setText("");
                    tfPersonID.setText("");
                    tfDrugID.setText("");
                    tfName.setText("");
                    tfNumber.setText("");
                    tfPrice.setText("");
                    tfTotal.setText("");
                    
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
                Prescribe prescribe = new Prescribe();
                if (selectedRow != -1) { // �ж��Ƿ���ڱ�ѡ����
                    System.out.println("��һ����"+tableModel.getValueAt(selectedRow, 0));
                    prescribe.setPrescribeID(tableModel.getValueAt(selectedRow, 0).toString());
                    String prescribeID = prescribe.getPrescribeID();
                    prescribe.setPersonID(tfPersonID.getText());
                    prescribe.setDrugID(tfDrugID.getText());
                    prescribe.setName(tfName.getText());
                    prescribe.setNumber(tfNumber.getText());
                    prescribe.setPrice(tfPrice.getText());
                    prescribe.setTotal(tfTotal.getText());
                    if (prescribeDao.updatePrescribe(prescribe)) {
                    	prescribe = prescribeDao.findPrescribeByPrescribeID(prescribeID);
                        JOptionPane.showMessageDialog(panelMain, "�޸ĳɹ�", "��Ϣ��ʾ��", JOptionPane.INFORMATION_MESSAGE);
                        tableModel.setValueAt(prescribe.getPrescribeID(), selectedRow, 0);
                        tableModel.setValueAt(prescribe.getPersonID(), selectedRow, 1);
                        tableModel.setValueAt(prescribe.getDrugID(), selectedRow, 2);
                        tableModel.setValueAt(prescribe.getName(), selectedRow, 3);
                        tableModel.setValueAt(prescribe.getNumber(), selectedRow, 4);
                        tableModel.setValueAt(prescribe.getPrice(), selectedRow, 5);
                        tableModel.setValueAt(prescribe.getTotal(), selectedRow, 6);
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
                    if (prescribeDao.deletPrescribe(id)) {
                        JOptionPane.showMessageDialog(panelMain, "ɾ���ɹ�", "��Ϣ��ʾ��", JOptionPane.INFORMATION_MESSAGE);
                        // ����������
                        tfPrescribeID.setText("");
                        tfPersonID.setText("");
                        tfDrugID.setText("");
                        tfName.setText("");
                        tfNumber.setText("");
                        tfTotal.setText("");
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
                ArrayList<Prescribe> prescribes = null;
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
                    prescribes = prescribeDao.findByPrescribeID(perscribeID);
                    System.out.println("��ѯ������" + prescribes );
                } else if ("����ID".equals(type)) {
                    String personID = tfSearch.getText();
                    //System.out.println(name);
                    prescribes = prescribeDao.findByPersonID(personID);
                    //System.out.println("��ѯ������"+Persons);
                } else if ("ҩƷID".equals(type)) {
                    String drugID = tfSearch.getText();
                    prescribes = prescribeDao.findByDrugID(drugID);
                    //System.out.println("��ѯ������"+Persons);
                } else {
                    JOptionPane.showMessageDialog(panelMain, "��ѡ���ѯ����", "��Ϣ��ʾ��", JOptionPane.INFORMATION_MESSAGE);
                }
                fillPersoninfoToTable(prescribes);
            }
        });
    }
    // ��ѯʱʹ�ã���ձ�����ݣ������������
    private void fillPersoninfoToTable(ArrayList<Prescribe> prescrbies) {
        if (prescrbies.isEmpty()) {
            JOptionPane.showMessageDialog(panelMain, "Sorry, δ��ѯ�����ݣ����������", "��Ϣ��ʾ��", JOptionPane.INFORMATION_MESSAGE);
        }
        tableModel.setRowCount(0); // ��������
        String[][] tableValues = new String[prescrbies.size()][7];
        // ����װ������
        for (int j = 0; j < prescrbies.size(); j++) {
            tableValues[j][0] = prescrbies.get(j).getPrescribeID();
            tableValues[j][1] = prescrbies.get(j).getPersonID();
            tableValues[j][2] = prescrbies.get(j).getDrugID();
            tableValues[j][3] = prescrbies.get(j).getName();
            tableValues[j][4] = prescrbies.get(j).getNumber();
            tableValues[j][5] = prescrbies.get(j).getPrice();
            tableValues[j][6] = prescrbies.get(j).getTotal();
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
                Object prescribeID = tableModel.getValueAt(selectedRow, 0);
                Object personID = tableModel.getValueAt(selectedRow, 1);
                
                Object drugID = tableModel.getValueAt(selectedRow, 2);
                Object name = tableModel.getValueAt(selectedRow, 3);
                
                Object number = tableModel.getValueAt(selectedRow, 4);
                Object price = tableModel.getValueAt(selectedRow, 5);
                Object total = tableModel.getValueAt(selectedRow, 6);
                //PersonID,ID_type,ID_number,name,sex,ethnicity,birthday;
                tfPrescribeID.setText(prescribeID.toString());
                tfPersonID.setText(personID.toString());
                
                tfDrugID.setText(drugID.toString());
                tfName.setText(name.toString());
                
                tfNumber.setText(number.toString());
                tfPrice.setText(price.toString());
                tfTotal.setText(total.toString());
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
    public void PersonID() {
    	btnPrescribeID.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			int number = prescribeDao.findAll().size() + 1;
    			String ans = "PR";
    			if(number / 10 != 0) {
    				ans += "000" + number;
    			} else {
    				ans += "0000" + number;
    			}
    			tfPrescribeID.setText(ans);
    		}
    	});
    }
   

}

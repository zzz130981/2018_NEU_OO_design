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

import entity.Drug;
import service.DrugService;
import service.DrugServiceImpl;
public class AdminDrugMangeView extends JInternalFrame {

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
    
    private JLabel labDrugID;
    private JLabel labDrugName;
    private JLabel labUnit;
    private JLabel labMaxPrice;
    private JLabel labChargeLevel;
    private JLabel labHospital;
    //private JLabel labHospital;
    
    private JTextField tfDrugID; // ��������
    private JTextField tfDrugName; // �������
    private JTextField tfUnit; // ����
    private JTextField tfMaxPrice; // �����������
    private JTextField tfChargeLevel;
    private JTextField tfHospital;
    //private JTextField tfHospital;
    
    private JButton btnDrugID;
    private JButton btnAdd; // ��Ӱ�ť
    private JButton btnEdit; // �޸İ�ť
    private JButton btnDelete; // ɾ����ť

    private DrugService DrugService;

    public AdminDrugMangeView() {
        DrugService = new DrugServiceImpl();
        init();
        getTableSelectedRow();
        addTableRow();
        editTableRow();
        deleteTableRow();
        getSearchTypeValue();
        displayMessage();
        find();
        DrugID();
        //refresh();
        System.out.println("songmanagerView");
    }

    /**
     * ��ʼ�������
     */
    private void init() {
        this.setTitle("ҩƷ����");
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
        tfSearch = new JTextField("������ҩƷID",20);
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
        btnSearchTypes[0] = new JRadioButton("ҩƷID", true);
        btnSearchTypes[1] = new JRadioButton("ҩƷ��");
        //btnSearchTypes[2] = new JRadioButton("��߼۸�");
        //btnSearchTypes[3] = new JRadioButton("����");

        // System.out.println("����"+btnSearchTypes[1].getText());
        for (JRadioButton jRadioButton : btnSearchTypes) {
            buttonGroup.add(jRadioButton);
            panelSearchType.add(jRadioButton);
        }
        panelTop.add(panelSearchType);
        panelMain.add(panelTop, BorderLayout.NORTH);

        String[] columnNames = { "ҩƷID", "ҩƷ����", "��߼۸�", "������λ", "�շѵȼ�" ,"ҽԺ"};
        // �����ݿ��в�ѯ��������
        ArrayList<Drug> Drugs = DrugService.findAll();
        System.out.println(Drugs);
        String[][] tableValues = new String[Drugs.size()][6];
        for (int i = 0; i < Drugs.size(); i++) {
            tableValues[i][0] = Drugs.get(i).getDrugID();
            tableValues[i][1] = Drugs.get(i).getDrugName();
            tableValues[i][2] = Drugs.get(i).getMaxPrice();
            tableValues[i][3] = Drugs.get(i).getUnit();
            tableValues[i][4] = Drugs.get(i).getChargeLevel();
            tableValues[i][5] = Drugs.get(i).getHospital();
            //tableValues[i][6] = Drugs.get(i).getHospital();
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
        labDrugID = new JLabel("ҩƷID");
        tfDrugID = new JTextField(6);
        labDrugName = new JLabel("ҩƷ��");
        tfDrugName = new JTextField(10);
        labUnit = new JLabel("������λ");
        tfUnit = new JTextField(5);
        labMaxPrice = new JLabel("��߼۸�");
        tfMaxPrice = new JTextField(4);
        labChargeLevel = new JLabel("�շѵȼ�");
        tfChargeLevel = new JTextField(4);
        labHospital = new JLabel("ҽԺ");
        tfHospital = new JTextField(4);
        
        panelTextField.add(labDrugID);
        panelTextField.add(tfDrugID);
        panelTextField.add(labDrugName);
        panelTextField.add(tfDrugName);
        panelTextField.add(labUnit);
        panelTextField.add(tfUnit);
        panelTextField.add(labMaxPrice);
        panelTextField.add(tfMaxPrice);
        panelTextField.add(labChargeLevel);
        panelTextField.add(tfChargeLevel);
        panelTextField.add(labHospital);
        panelTextField.add(tfHospital);
        
        btnDrugID = new JButton("����ID");
        btnAdd = new JButton("����");
        btnEdit = new JButton("�޸�");
        btnDelete = new JButton("ɾ��");
        panelButton.add(btnDrugID);
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
            	String DrugID = tfDrugID.getText().trim();
            	if(DrugService.findByDrugID(DrugID).size() != 0) {
            		JOptionPane.showMessageDialog(panelTable, "��ID�Ѵ��ڣ����������룡", "��Ϣ��ʾ��", JOptionPane.INFORMATION_MESSAGE);
            		return;
            	}
            	String drugID = tfDrugID.getText().trim();
                String name =  tfDrugName.getText().trim();
                String maxPrice = tfMaxPrice.getText().trim();
                String unit= tfUnit.getText().trim();
                String chargeLevel = tfChargeLevel.getText().trim();
                String birthday = tfHospital.getText().trim();
                if (name.equals("") || name == null 
                        /*|| language.equals("") || language == null
                        || category.equals("") || category == null
                        || singer.equals("") || singer == null*/) {
                    JOptionPane.showMessageDialog(panelTable, "����Ϊ��", "��Ϣ��ʾ��", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                Drug Drug = new Drug(drugID,name,maxPrice,unit,chargeLevel,birthday);
                String[] rowValues = new String[6];
                if (DrugService.addDrug(Drug)) {
                    JOptionPane.showMessageDialog(panelMain, "��ӳɹ�", "��Ϣ��ʾ��", JOptionPane.INFORMATION_MESSAGE);
                    rowValues[0] = Drug.getDrugID();
                    rowValues[1] = Drug.getDrugName();
                    rowValues[2] = Drug.getMaxPrice();
                    //rowValues[3] = Drug.getName();
                    rowValues[3] = Drug.getUnit();
                    rowValues[4] = Drug.getChargeLevel();
                    rowValues[5] = Drug.getHospital();
                    //rowValues[7] = Drug.getId();
                    // ����������
                    tfDrugID.setText("");
                    tfDrugName.setText("");
                    tfMaxPrice.setText("");
                    //tfName.setText("");
                    tfUnit.setText("");
                    tfChargeLevel.setText("");
                    tfHospital.setText("");
                    
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
                //Drug Drug = new Drug();
                Drug Drug = new Drug();
                if (selectedRow != -1) { // �ж��Ƿ���ڱ�ѡ����
                    System.out.println(tableModel.getValueAt(selectedRow, 0));
                    Drug.setDrugID(tableModel.getValueAt(selectedRow, 0).toString());
                    String DrugID = Drug.getDrugID();
                    Drug.setDrugName(tfDrugName.getText());
                    Drug.setMaxPrice(tfMaxPrice.getText());
                    //Drug.setName(tfName.getText());
                    Drug.setUnit(tfUnit.getText());
                    Drug.setChargeLevel(tfChargeLevel.getText());
                    Drug.setHospital(tfHospital.getText());
                    if (DrugService.updateDrug(Drug)) {
                        Drug = DrugService.findDrugByDrugID(Drug.getDrugID());
                        JOptionPane.showMessageDialog(panelMain, "�޸ĳɹ�", "��Ϣ��ʾ��", JOptionPane.INFORMATION_MESSAGE);
                        tableModel.setValueAt(Drug.getDrugID(), selectedRow, 0);
                        tableModel.setValueAt(Drug.getDrugName(), selectedRow, 1);
                        tableModel.setValueAt(Drug.getMaxPrice(), selectedRow, 2);
                        //tableModel.setValueAt(Drug.getName(), selectedRow, 3);
                        tableModel.setValueAt(Drug.getUnit(), selectedRow, 3);
                        tableModel.setValueAt(Drug.getChargeLevel(), selectedRow, 4);
                        tableModel.setValueAt(Drug.getHospital(), selectedRow, 5);
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
                    if (DrugService.deletDrug(id)) {
                        JOptionPane.showMessageDialog(panelMain, "ɾ���ɹ�", "��Ϣ��ʾ��", JOptionPane.INFORMATION_MESSAGE);
                        // ����������
                        tfDrugName.setText("");
                        tfDrugID.setText("");
                        tfMaxPrice.setText("");
                        tfUnit.setText("");
                        tfChargeLevel.setText("");
                        tfHospital.setText("");
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
                ArrayList<Drug> Drugs = null;
                String type = "����";
                for (int i = 0; i < btnSearchTypes.length; i++) {
                    if (btnSearchTypes[i].isSelected()) {
                        type = btnSearchTypes[i].getText();
                        //System.out.println(type+"**********");
                    }
                }
                if ("ҩƷID".equals(type)) {
                    String DrugID = tfSearch.getText();
                    //System.out.println("name:" + DrugID);
                    Drugs = DrugService.findByDrugID(DrugID);
                    System.out.println("��ѯ������"+Drugs);
                } else if ("ҩƷ��".equals(type)) {
                    String name = tfSearch.getText();
                    System.out.println(name);
                    Drugs = DrugService.findByName(name);
                    System.out.println("��ѯ������"+Drugs);
                } else {
                    JOptionPane.showMessageDialog(panelMain, "��ѡ���ѯ����", "��Ϣ��ʾ��", JOptionPane.INFORMATION_MESSAGE);
                }
                fillDruginfoToTable(Drugs);
            }
        });
    }
    // ��ѯʱʹ�ã���ձ�����ݣ������������
    private void fillDruginfoToTable(ArrayList<Drug> Drugs) {
        if (Drugs.isEmpty()) {
            JOptionPane.showMessageDialog(panelMain, "Sorry, δ��ѯ�����ݣ����������", "��Ϣ��ʾ��", JOptionPane.INFORMATION_MESSAGE);
        }
        tableModel.setRowCount(0); // ��������
        String[][] tableValues = new String[Drugs.size()][7];
        // ����װ������
        for (int j = 0; j < Drugs.size(); j++) {
            tableValues[j][0] = Drugs.get(j).getDrugID();
            tableValues[j][1] = Drugs.get(j).getDrugName();
            tableValues[j][2] = Drugs.get(j).getMaxPrice();
            //tableValues[j][3] = Drugs.get(j).getName();
            tableValues[j][3] = Drugs.get(j).getUnit();
            tableValues[j][4] = Drugs.get(j).getChargeLevel();
            tableValues[j][5] = Drugs.get(j).getHospital();
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
                Object drugID = tableModel.getValueAt(selectedRow, 0);
                Object drugName = tableModel.getValueAt(selectedRow, 1);
                Object maxPrice = tableModel.getValueAt(selectedRow, 2);
                //Object name = tableModel.getValueAt(selectedRow, 3);
                Object unit= tableModel.getValueAt(selectedRow, 3);
                Object chargeLevel = tableModel.getValueAt(selectedRow, 4);
                Object birthday = tableModel.getValueAt(selectedRow, 5);
                //DrugID,ID_type,ID_number,name,unit,chargeLevel,birthday;
                tfDrugID.setText(drugID.toString());
                tfDrugName.setText(drugName.toString());
                tfMaxPrice.setText(maxPrice.toString());
                //tfName.setText(name.toString());
                tfUnit.setText(unit.toString());
                tfChargeLevel.setText(chargeLevel.toString());
                tfHospital.setText(birthday.toString());
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
    public void DrugID() {
    	btnDrugID.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			int number = DrugService.findAll().size() + 1;
    			String ans = "GY";
    			if(number / 10 != 0) {
    				ans += "00" + number;
    			} else {
    				ans += "000" + number;
    			}
    			tfDrugID.setText(ans);
    		}
    	});
    }
   

}

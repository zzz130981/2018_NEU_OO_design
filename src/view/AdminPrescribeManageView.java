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

    private JPanel panelMain; // 主面板

    private JPanel panelTop; // 主面板的上部
    private JPanel panelSearch; // 搜索面板
    private JLabel labKey; // 关键字
    private JTextField tfSearch; // 搜索框
    private JButton btnSearch; // 查找按钮
    private JButton btnRefresh;

    private JPanel panelSearchType; // 按什么查找
    private ButtonGroup buttonGroup = new ButtonGroup();
    private JRadioButton[] btnSearchTypes = new JRadioButton[3]; // 条件，如歌名，语言，类别，歌手
    private JLabel labSearch;

    private JScrollPane panelTable; // 表格面板
    private myTableModel tableModel;// 定义表格模型对象
    private JTable table;// 定义表格对象

    private JPanel panelBottom; // 底部面板，包括输入框，添加、删除、修改
    private JPanel panelTextField;
    private JPanel panelButton;
    
    private JLabel labPrescribeID;
    private JLabel labPersonID;
    private JLabel labDrugID;
    private JLabel labName;
    private JLabel labPrice;
    private JLabel labNumber;
    private JLabel labTotal;
    
    private JTextField tfPrescribeID; // 输入语言
    private JTextField tfPersonID; // 输入类别
    private JTextField tfDrugID; // 歌手
    private JTextField tfName; // 输入歌曲名字
    private JTextField tfNumber;
    private JTextField tfPrice;
    private JTextField tfTotal;
    
    private JButton btnPrescribeID;
    private JButton btnAdd; // 添加按钮
    private JButton btnEdit; // 修改按钮
    private JButton btnDelete; // 删除按钮

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
     * 初始化各组件
     */
    private void init() {
        this.setTitle("处方信息管理");
        this.setSize(new Dimension(750, 550));
        this.setIconifiable(true); // 窗体最小化
        this.setClosable(true); // 可被关闭
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE); // 退出
        panelMain = new JPanel(new BorderLayout());

        panelTop = new JPanel(new GridLayout(2, 1, 0, 0));
        panelTop.setLocation(0, 20);
        panelSearch = new JPanel();
        panelSearch.setPreferredSize(new Dimension(750, 30));
        labKey = new JLabel("关键字");
        tfSearch = new JTextField("请输入处方ID",20);
        tfSearch.setPreferredSize(new Dimension(100, 20));
        btnSearch = new JButton("查找");
        btnSearch.setPreferredSize(new Dimension(60, 20));
        btnRefresh = new JButton("刷新");
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
        labSearch = new JLabel("查询");
        panelSearchType.add(labSearch);
        btnSearchTypes[0] = new JRadioButton("处方ID", true);
        btnSearchTypes[1] = new JRadioButton("个人ID");
        btnSearchTypes[2] = new JRadioButton("药品ID");
        //btnSearchTypes[3] = new JRadioButton("歌手");

        // System.out.println("名字"+btnSearchTypes[1].getText());
        for (JRadioButton jRadioButton : btnSearchTypes) {
            buttonGroup.add(jRadioButton);
            panelSearchType.add(jRadioButton);
        }
        panelTop.add(panelSearchType);
        panelMain.add(panelTop, BorderLayout.NORTH);

        String[] columnNames = { "处方ID", "个人ID", "药品ID", "医生姓名", "数量" ,"单价" , "总价"};
        // 从数据库中查询所有数据
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
//        table.setRowSorter(new TableRowSorter<>(tableModel)); // 不可排序
        table.setRowHeight(20); // 设置行高
        table.getTableHeader().setBackground(Color.GRAY); // 表头颜色
        table.setLocation(20, 80);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // 只能选中单行
//        table.setEnabled(false); // 设置表格为不可编辑
        panelTable = new JScrollPane(table);
        panelTable.setBorder(BorderFactory.createTitledBorder("就诊信息"));
        panelMain.add(panelTable, BorderLayout.CENTER);

        panelBottom = new JPanel(new GridLayout(2, 1, 0, 0));
        panelTextField = new JPanel();
        panelButton = new JPanel();
        
        labPrescribeID = new JLabel("处方ID");
        tfPrescribeID = new JTextField(6);
        labPersonID = new JLabel("人员ID");
        tfPersonID= new JTextField(7);
        labDrugID = new JLabel("药品ID");
        tfDrugID = new JTextField(7);
        labName = new JLabel("医生姓名");
        tfName = new JTextField(5);
        labPrice = new JLabel("单价");
        tfPrice = new JTextField(3);
        labNumber = new JLabel("数量");
        tfNumber = new JTextField(6);
        labTotal = new JLabel("总价");
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
        
        btnPrescribeID = new JButton("新增ID");
        btnAdd = new JButton("增加");
        btnEdit = new JButton("修改");
        btnDelete = new JButton("删除");
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
     * 添加一行
     */
    private void addTableRow() {
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // String[] rowValues = {"add",tfName.getText(),tfLanguage.getText(),
                // tfCategory.getText(),tfSinger.getText()};
            	String prescribeID = tfPrescribeID.getText().trim();
            	if(prescribeDao.findByPrescribeID(prescribeID).size() != 0) {
            		JOptionPane.showMessageDialog(panelTable, "该ID已存在，请重新输入！", "信息提示框", JOptionPane.INFORMATION_MESSAGE);
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
                    JOptionPane.showMessageDialog(panelTable, "不能为空", "信息提示框", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                Prescribe prescribe = new Prescribe(prescribeID,personID,drugID,name,number,price,total);
                //Person Person = new Person(PersonID,ID_type,ID_number,name,sex,ethnicity,birthday);
                String[] rowValues = new String[7];
                if (prescribeDao.addPrescribe(prescribe)) {
                    JOptionPane.showMessageDialog(panelMain, "添加成功", "信息提示框", JOptionPane.INFORMATION_MESSAGE);
                    rowValues[0] = prescribe.getPrescribeID();
                    rowValues[1] = prescribe.getPersonID();
                    rowValues[2] = prescribe.getDrugID();
                    rowValues[3] = prescribe.getName();
                    rowValues[4] = prescribe.getNumber();
                    rowValues[5] = prescribe.getPrice();
                    rowValues[6] = prescribe.getTotal();
                    //rowValues[7] = Person.getId();
                    // 将输入框清空
                    tfPrescribeID.setText("");
                    tfPersonID.setText("");
                    tfDrugID.setText("");
                    tfName.setText("");
                    tfNumber.setText("");
                    tfPrice.setText("");
                    tfTotal.setText("");
                    
                    // 将新增的行加入表格中
                    tableModel.addRow(rowValues);
                } else {
                    JOptionPane.showMessageDialog(panelTable, "添加失败", "信息提示框", JOptionPane.INFORMATION_MESSAGE);
                }
                
            }
        });
    }
    /**
     * 修改信息
     */
    
    private void editTableRow() {
        btnEdit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow(); // 获取被选中行的索引
                //Person Person = new Person();
                //Person Person = new Person();
                System.out.println("修改第几行？" + selectedRow);
                Prescribe prescribe = new Prescribe();
                if (selectedRow != -1) { // 判断是否存在被选中行
                    System.out.println("第一行是"+tableModel.getValueAt(selectedRow, 0));
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
                        JOptionPane.showMessageDialog(panelMain, "修改成功", "信息提示框", JOptionPane.INFORMATION_MESSAGE);
                        tableModel.setValueAt(prescribe.getPrescribeID(), selectedRow, 0);
                        tableModel.setValueAt(prescribe.getPersonID(), selectedRow, 1);
                        tableModel.setValueAt(prescribe.getDrugID(), selectedRow, 2);
                        tableModel.setValueAt(prescribe.getName(), selectedRow, 3);
                        tableModel.setValueAt(prescribe.getNumber(), selectedRow, 4);
                        tableModel.setValueAt(prescribe.getPrice(), selectedRow, 5);
                        tableModel.setValueAt(prescribe.getTotal(), selectedRow, 6);
                    } else {
                        JOptionPane.showMessageDialog(panelTable, "修改失败", "信息提示框", JOptionPane.INFORMATION_MESSAGE);
                    }

                }
            }
        });
        
    }
    /**
     * 删除一条信息
     */
    private void deleteTableRow() {
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow(); // 获取被选中行的索引
                if (selectedRow != -1) {
                    String id = tableModel.getValueAt(selectedRow, 0).toString();
                    System.out.println(id);
                    if (prescribeDao.deletPrescribe(id)) {
                        JOptionPane.showMessageDialog(panelMain, "删除成功", "信息提示框", JOptionPane.INFORMATION_MESSAGE);
                        // 将输入框清空
                        tfPrescribeID.setText("");
                        tfPersonID.setText("");
                        tfDrugID.setText("");
                        tfName.setText("");
                        tfNumber.setText("");
                        tfTotal.setText("");
                        tableModel.removeRow(selectedRow);
                        
                    } else {
                        JOptionPane.showMessageDialog(panelTable, "删除失败", "信息提示框", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });
    }
    //查询实现三个吧：个人ID+name ; name ; ID_number ; 个人DI
    private void find() {
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ArrayList<Prescribe> prescribes = null;
                String type = "处方ID";
                for (int i = 0; i < btnSearchTypes.length; i++) {
                    if (btnSearchTypes[i].isSelected()) {
                        type = btnSearchTypes[i].getText();
                        System.out.println(type+"**********");
                    }
                }
                if ("处方ID".equals(type)) {
                    String perscribeID = tfSearch.getText();
                    //System.out.println("name:" + PersonID);
                    prescribes = prescribeDao.findByPrescribeID(perscribeID);
                    System.out.println("查询出来的" + prescribes );
                } else if ("个人ID".equals(type)) {
                    String personID = tfSearch.getText();
                    //System.out.println(name);
                    prescribes = prescribeDao.findByPersonID(personID);
                    //System.out.println("查询出来的"+Persons);
                } else if ("药品ID".equals(type)) {
                    String drugID = tfSearch.getText();
                    prescribes = prescribeDao.findByDrugID(drugID);
                    //System.out.println("查询出来的"+Persons);
                } else {
                    JOptionPane.showMessageDialog(panelMain, "请选择查询类型", "信息提示框", JOptionPane.INFORMATION_MESSAGE);
                }
                fillPersoninfoToTable(prescribes);
            }
        });
    }
    // 查询时使用，清空表格数据，再重新添加行
    private void fillPersoninfoToTable(ArrayList<Prescribe> prescrbies) {
        if (prescrbies.isEmpty()) {
            JOptionPane.showMessageDialog(panelMain, "Sorry, 未查询到数据，请从新输入", "信息提示框", JOptionPane.INFORMATION_MESSAGE);
        }
        tableModel.setRowCount(0); // 将表格清空
        String[][] tableValues = new String[prescrbies.size()][7];
        // 重新装填数据
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
     * 获取勾选框中的值，将该值的信息给放入到tfsearch中，用作提示信息
     */
    private void getSearchTypeValue() {
        for (JRadioButton jRadioButton : btnSearchTypes) {
            jRadioButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jRadioButton.setSelected(true); // 将该jRadioButton设置为选中状态
                    String type = jRadioButton.getText();
                    tfSearch.setText("请输入" + type);
                    // System.out.println(type);
                }
            });
        }
    }
    //获取选中的表格的一行
    private void getTableSelectedRow() {
        table.addMouseListener(new MouseAdapter() {
            // 发生了点击事件
            public void mouseClicked(MouseEvent e) {
                // 获得被选中行的索引
                int selectedRow = table.getSelectedRow();
                System.out.println("我已经点中" + selectedRow);
                // 从表格模型中获得指定单元格的值
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
    // 处理输入框的提示消息处理，当光标进入search输入框时，将输入框清空
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

        // 让表格不可编辑，重写了DefaultTableModel中的方法
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

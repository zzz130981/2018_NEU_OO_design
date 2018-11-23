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

    private JPanel panelMain; // 主面板

    private JPanel panelTop; // 主面板的上部
    private JPanel panelSearch; // 搜索面板
    private JLabel labKey; // 关键字
    private JTextField tfSearch; // 搜索框
    private JButton btnSearch; // 查找按钮
    private JButton btnRefresh;

    private JPanel panelSearchType; // 按什么查找
    private ButtonGroup buttonGroup = new ButtonGroup();
    private JRadioButton[] btnSearchTypes = new JRadioButton[2]; // 条件，如歌名，语言，类别，歌手
    private JLabel labSearch;

    private JScrollPane panelTable; // 表格面板
    private myTableModel tableModel;// 定义表格模型对象
    private JTable table;// 定义表格对象

    private JPanel panelBottom; // 底部面板，包括输入框，添加、删除、修改
    private JPanel panelTextField;
    private JPanel panelButton;
    
    private JLabel labTreatID;
    private JLabel labPersonID;
    private JLabel labhLevel;
    private JLabel labhNumber;
    private JLabel labhName;
    private JLabel labDoor;
    private JLabel labName;
    
    private JTextField tfTreatID; // 输入语言
    private JTextField tfPersonID; // 输入类别
    private JTextField tfhLevel; // 歌手
    private JTextField tfhNumber; // 输入歌曲名字
    private JTextField tfhName;
    private JTextField tfDoor;
    private JTextField tfName;
    
    private JButton btnPrescribeID;
    private JButton btnAdd; // 添加按钮
    private JButton btnEdit; // 修改按钮
    private JButton btnDelete; // 删除按钮

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
        btnSearchTypes[0] = new JRadioButton("就诊ID", true);
        btnSearchTypes[1] = new JRadioButton("人员ID");
        //btnSearchTypes[2] = new JRadioButton("药品ID");
        //btnSearchTypes[3] = new JRadioButton("歌手");

        // System.out.println("名字"+btnSearchTypes[1].getText());
        for (JRadioButton jRadioButton : btnSearchTypes) {
            buttonGroup.add(jRadioButton);
            panelSearchType.add(jRadioButton);
        }
        panelTop.add(panelSearchType);
        panelMain.add(panelTop, BorderLayout.NORTH);

        String[] columnNames = { "就诊记录编号", "个人ID", "医院等级", "医院编号" ,"医院名称" , "门诊号" , "疾病名称"};
        // 从数据库中查询所有数据
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
        
        labTreatID = new JLabel("就诊");
        tfTreatID = new JTextField(4);
        labPersonID = new JLabel("人员ID");
        tfPersonID= new JTextField(5);
        labhLevel = new JLabel("医院等级");
        tfhLevel = new JTextField(3);
        labhNumber = new JLabel("医院编号");
        tfhNumber = new JTextField(5);
        labhName = new JLabel("医院名称");
        tfhName = new JTextField(7);
        labDoor = new JLabel("门诊号");
        tfDoor = new JTextField(2);
        labName = new JLabel("疾病名称");
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
                    JOptionPane.showMessageDialog(panelTable, "不能为空", "信息提示框", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                Treat treat = new Treat(treatID,personID,hLevel,hNumber,hName,door,name);
                //Person Person = new Person(PersonID,ID_type,ID_number,name,sex,ethnicity,birthday);
                String[] rowValues = new String[7];
                if (treatDao.addTreat(treat)) {
                    JOptionPane.showMessageDialog(panelMain, "添加成功", "信息提示框", JOptionPane.INFORMATION_MESSAGE);
                    rowValues[0] = treat.getTreatID();
                    rowValues[1] = treat.getPersonID();
                    rowValues[2] = treat.gethLevel();
                    rowValues[3] = treat.gethNumber();
                    rowValues[4] = treat.gethName();
                    rowValues[5] = treat.getDoor();
                    rowValues[6] = treat.getName();
                    //rowValues[7] = Person.getId();
                    // 将输入框清空
                    tfTreatID.setText("");
                    tfPersonID.setText("");
                    tfhLevel.setText("");
                    tfhNumber.setText("");
                    tfhName.setText("");
                    tfDoor.setText("");
                    tfName.setText("");
                    
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
                Treat treat = new Treat();
                if (selectedRow != -1) { // 判断是否存在被选中行
                    System.out.println("第一行是"+tableModel.getValueAt(selectedRow, 0));
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
                        JOptionPane.showMessageDialog(panelMain, "修改成功", "信息提示框", JOptionPane.INFORMATION_MESSAGE);
                        tableModel.setValueAt(treat.getTreatID(), selectedRow, 0);
                        tableModel.setValueAt(treat.getPersonID(), selectedRow, 1);
                        tableModel.setValueAt(treat.gethLevel(), selectedRow, 2);
                        tableModel.setValueAt(treat.gethNumber(), selectedRow, 3);
                        tableModel.setValueAt(treat.gethName(), selectedRow, 4);
                        tableModel.setValueAt(treat.getDoor(), selectedRow, 5);
                        tableModel.setValueAt(treat.getName(), selectedRow, 6);
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
                    if (treatDao.deletTreat(id)) {
                        JOptionPane.showMessageDialog(panelMain, "删除成功", "信息提示框", JOptionPane.INFORMATION_MESSAGE);
                        // 将输入框清空
                        tfTreatID.setText("");
                        tfPersonID.setText("");
                        tfhLevel.setText("");
                        tfhNumber.setText("");
                        tfhName.setText("");
                        tfDoor.setText("");
                        tfName.setText("");
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
                ArrayList<Treat> treats = null;
                String type = "处方ID";
                for (int i = 0; i < btnSearchTypes.length; i++) {
                    if (btnSearchTypes[i].isSelected()) {
                        type = btnSearchTypes[i].getText();
                        System.out.println(type+"**********");
                    }
                }
                if ("就诊ID".equals(type)) {
                    String perscribeID = tfSearch.getText();
                    //System.out.println("name:" + PersonID);
                    treats = treatDao.findByTreatID(perscribeID);
                    System.out.println("查询出来的" + treats );
                } else if ("人员ID".equals(type)) {
                    String personID = tfSearch.getText();
                    //System.out.println(name);
                    treats = treatDao.findByPersonID(personID);
                    //System.out.println("查询出来的"+Persons);
                } else {
                    JOptionPane.showMessageDialog(panelMain, "请选择查询类型", "信息提示框", JOptionPane.INFORMATION_MESSAGE);
                }
                fillPersoninfoToTable(treats);
            }
        });
    }
    // 查询时使用，清空表格数据，再重新添加行
    private void fillPersoninfoToTable(ArrayList<Treat> prescrbies) {
        if (prescrbies.isEmpty()) {
            JOptionPane.showMessageDialog(panelMain, "Sorry, 未查询到数据，请从新输入", "信息提示框", JOptionPane.INFORMATION_MESSAGE);
        }
        tableModel.setRowCount(0); // 将表格清空
        String[][] tableValues = new String[prescrbies.size()][7];
        // 重新装填数据
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

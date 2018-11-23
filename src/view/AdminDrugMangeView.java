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
    
    private JLabel labDrugID;
    private JLabel labDrugName;
    private JLabel labUnit;
    private JLabel labMaxPrice;
    private JLabel labChargeLevel;
    private JLabel labHospital;
    //private JLabel labHospital;
    
    private JTextField tfDrugID; // 输入语言
    private JTextField tfDrugName; // 输入类别
    private JTextField tfUnit; // 歌手
    private JTextField tfMaxPrice; // 输入歌曲名字
    private JTextField tfChargeLevel;
    private JTextField tfHospital;
    //private JTextField tfHospital;
    
    private JButton btnDrugID;
    private JButton btnAdd; // 添加按钮
    private JButton btnEdit; // 修改按钮
    private JButton btnDelete; // 删除按钮

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
     * 初始化各组件
     */
    private void init() {
        this.setTitle("药品管理");
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
        tfSearch = new JTextField("请输入药品ID",20);
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
        btnSearchTypes[0] = new JRadioButton("药品ID", true);
        btnSearchTypes[1] = new JRadioButton("药品名");
        //btnSearchTypes[2] = new JRadioButton("最高价格");
        //btnSearchTypes[3] = new JRadioButton("歌手");

        // System.out.println("名字"+btnSearchTypes[1].getText());
        for (JRadioButton jRadioButton : btnSearchTypes) {
            buttonGroup.add(jRadioButton);
            panelSearchType.add(jRadioButton);
        }
        panelTop.add(panelSearchType);
        panelMain.add(panelTop, BorderLayout.NORTH);

        String[] columnNames = { "药品ID", "药品名称", "最高价格", "计量单位", "收费等级" ,"医院"};
        // 从数据库中查询所有数据
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
//        table.setRowSorter(new TableRowSorter<>(tableModel)); // 不可排序
        table.setRowHeight(20); // 设置行高
        table.getTableHeader().setBackground(Color.GRAY); // 表头颜色
        table.setLocation(20, 80);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // 只能选中单行
//        table.setEnabled(false); // 设置表格为不可编辑
        panelTable = new JScrollPane(table);
        panelTable.setBorder(BorderFactory.createTitledBorder("歌曲信息"));
        panelMain.add(panelTable, BorderLayout.CENTER);

        panelBottom = new JPanel(new GridLayout(2, 1, 0, 0));
        panelTextField = new JPanel();
        panelButton = new JPanel();
        labDrugID = new JLabel("药品ID");
        tfDrugID = new JTextField(6);
        labDrugName = new JLabel("药品名");
        tfDrugName = new JTextField(10);
        labUnit = new JLabel("计量单位");
        tfUnit = new JTextField(5);
        labMaxPrice = new JLabel("最高价格");
        tfMaxPrice = new JTextField(4);
        labChargeLevel = new JLabel("收费等级");
        tfChargeLevel = new JTextField(4);
        labHospital = new JLabel("医院");
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
        
        btnDrugID = new JButton("新增ID");
        btnAdd = new JButton("增加");
        btnEdit = new JButton("修改");
        btnDelete = new JButton("删除");
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
     * 添加一行
     */
    private void addTableRow() {
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // String[] rowValues = {"add",tfName.getText(),tfLanguage.getText(),
                // tfCategory.getText(),tfSinger.getText()};
            	String DrugID = tfDrugID.getText().trim();
            	if(DrugService.findByDrugID(DrugID).size() != 0) {
            		JOptionPane.showMessageDialog(panelTable, "该ID已存在，请重新输入！", "信息提示框", JOptionPane.INFORMATION_MESSAGE);
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
                    JOptionPane.showMessageDialog(panelTable, "不能为空", "信息提示框", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                Drug Drug = new Drug(drugID,name,maxPrice,unit,chargeLevel,birthday);
                String[] rowValues = new String[6];
                if (DrugService.addDrug(Drug)) {
                    JOptionPane.showMessageDialog(panelMain, "添加成功", "信息提示框", JOptionPane.INFORMATION_MESSAGE);
                    rowValues[0] = Drug.getDrugID();
                    rowValues[1] = Drug.getDrugName();
                    rowValues[2] = Drug.getMaxPrice();
                    //rowValues[3] = Drug.getName();
                    rowValues[3] = Drug.getUnit();
                    rowValues[4] = Drug.getChargeLevel();
                    rowValues[5] = Drug.getHospital();
                    //rowValues[7] = Drug.getId();
                    // 将输入框清空
                    tfDrugID.setText("");
                    tfDrugName.setText("");
                    tfMaxPrice.setText("");
                    //tfName.setText("");
                    tfUnit.setText("");
                    tfChargeLevel.setText("");
                    tfHospital.setText("");
                    
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
                //Drug Drug = new Drug();
                Drug Drug = new Drug();
                if (selectedRow != -1) { // 判断是否存在被选中行
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
                        JOptionPane.showMessageDialog(panelMain, "修改成功", "信息提示框", JOptionPane.INFORMATION_MESSAGE);
                        tableModel.setValueAt(Drug.getDrugID(), selectedRow, 0);
                        tableModel.setValueAt(Drug.getDrugName(), selectedRow, 1);
                        tableModel.setValueAt(Drug.getMaxPrice(), selectedRow, 2);
                        //tableModel.setValueAt(Drug.getName(), selectedRow, 3);
                        tableModel.setValueAt(Drug.getUnit(), selectedRow, 3);
                        tableModel.setValueAt(Drug.getChargeLevel(), selectedRow, 4);
                        tableModel.setValueAt(Drug.getHospital(), selectedRow, 5);
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
                    if (DrugService.deletDrug(id)) {
                        JOptionPane.showMessageDialog(panelMain, "删除成功", "信息提示框", JOptionPane.INFORMATION_MESSAGE);
                        // 将输入框清空
                        tfDrugName.setText("");
                        tfDrugID.setText("");
                        tfMaxPrice.setText("");
                        tfUnit.setText("");
                        tfChargeLevel.setText("");
                        tfHospital.setText("");
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
                ArrayList<Drug> Drugs = null;
                String type = "歌名";
                for (int i = 0; i < btnSearchTypes.length; i++) {
                    if (btnSearchTypes[i].isSelected()) {
                        type = btnSearchTypes[i].getText();
                        //System.out.println(type+"**********");
                    }
                }
                if ("药品ID".equals(type)) {
                    String DrugID = tfSearch.getText();
                    //System.out.println("name:" + DrugID);
                    Drugs = DrugService.findByDrugID(DrugID);
                    System.out.println("查询出来的"+Drugs);
                } else if ("药品名".equals(type)) {
                    String name = tfSearch.getText();
                    System.out.println(name);
                    Drugs = DrugService.findByName(name);
                    System.out.println("查询出来的"+Drugs);
                } else {
                    JOptionPane.showMessageDialog(panelMain, "请选择查询类型", "信息提示框", JOptionPane.INFORMATION_MESSAGE);
                }
                fillDruginfoToTable(Drugs);
            }
        });
    }
    // 查询时使用，清空表格数据，再重新添加行
    private void fillDruginfoToTable(ArrayList<Drug> Drugs) {
        if (Drugs.isEmpty()) {
            JOptionPane.showMessageDialog(panelMain, "Sorry, 未查询到数据，请从新输入", "信息提示框", JOptionPane.INFORMATION_MESSAGE);
        }
        tableModel.setRowCount(0); // 将表格清空
        String[][] tableValues = new String[Drugs.size()][7];
        // 重新装填数据
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

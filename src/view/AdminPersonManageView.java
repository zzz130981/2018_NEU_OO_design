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
    
    private JLabel labPersonID;
    private JLabel labID_type;
    private JLabel labID_number;
    private JLabel labName;
    private JLabel labSex;
    private JLabel labEthnicity;
    private JLabel labBirthday;
    
    private JTextField tfPersonID; // 输入语言
    private JTextField tfID_type; // 输入类别
    private JTextField tfID_number; // 歌手
    private JTextField tfName; // 输入歌曲名字
    private JTextField tfSex;
    private JTextField tfEthnicity;
    private JTextField tfBirthday;
    
    private JButton btnPersonID;
    private JButton btnAdd; // 添加按钮
    private JButton btnEdit; // 修改按钮
    private JButton btnDelete; // 删除按钮

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
     * 初始化各组件
     */
    private void init() {
        this.setTitle("人员管理");
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
        tfSearch = new JTextField("请输入个人ID",20);
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
        btnSearchTypes[0] = new JRadioButton("个人ID", true);
        btnSearchTypes[1] = new JRadioButton("姓名");
        btnSearchTypes[2] = new JRadioButton("身份证号");
        //btnSearchTypes[3] = new JRadioButton("歌手");

        // System.out.println("名字"+btnSearchTypes[1].getText());
        for (JRadioButton jRadioButton : btnSearchTypes) {
            buttonGroup.add(jRadioButton);
            panelSearchType.add(jRadioButton);
        }
        panelTop.add(panelSearchType);
        panelMain.add(panelTop, BorderLayout.NORTH);

        String[] columnNames = { "个人ID", "证件类型", "证件号码", "姓名", "性别" ,"民族" , "出生日期"};
        // 从数据库中查询所有数据
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
        labName = new JLabel("姓名");
        tfName = new JTextField(5);
        labPersonID = new JLabel("个人ID");
        tfPersonID = new JTextField(6);
        labID_type = new JLabel("证件类型");
        tfID_type = new JTextField(5);
        labID_number = new JLabel("证件号码");
        tfID_number = new JTextField(14);
        labSex = new JLabel("性别");
        tfSex = new JTextField(2);
        labEthnicity = new JLabel("民族");
        tfEthnicity = new JTextField(3);
        labBirthday = new JLabel("出生日期");
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
        
        btnPersonID = new JButton("新增ID");
        btnAdd = new JButton("增加");
        btnEdit = new JButton("修改");
        btnDelete = new JButton("删除");
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
     * 添加一行
     */
    private void addTableRow() {
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // String[] rowValues = {"add",tfName.getText(),tfLanguage.getText(),
                // tfCategory.getText(),tfSinger.getText()};
            	String PersonID = tfPersonID.getText().trim();
            	if(PersonService.findByPersonID(PersonID).size() != 0) {
            		JOptionPane.showMessageDialog(panelTable, "该ID已存在，请重新输入！", "信息提示框", JOptionPane.INFORMATION_MESSAGE);
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
                    JOptionPane.showMessageDialog(panelTable, "不能为空", "信息提示框", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                Person Person = new Person(PersonID,ID_type,ID_number,name,sex,ethnicity,birthday);
                String[] rowValues = new String[7];
                if (PersonService.addPerson(Person)) {
                    JOptionPane.showMessageDialog(panelMain, "添加成功", "信息提示框", JOptionPane.INFORMATION_MESSAGE);
                    rowValues[0] = Person.getPersonID();
                    rowValues[1] = Person.getID_type();
                    rowValues[2] = Person.getID_number();
                    rowValues[3] = Person.getName();
                    rowValues[4] = Person.getSex();
                    rowValues[5] = Person.getEthnicity();
                    rowValues[6] = Person.getBirthday();
                    //rowValues[7] = Person.getId();
                    // 将输入框清空
                    tfPersonID.setText("");
                    tfID_type.setText("");
                    tfID_number.setText("");
                    tfName.setText("");
                    tfSex.setText("");
                    tfEthnicity.setText("");
                    tfBirthday.setText("");
                    
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
                Person Person = new Person();
                if (selectedRow != -1) { // 判断是否存在被选中行
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
                        JOptionPane.showMessageDialog(panelMain, "修改成功", "信息提示框", JOptionPane.INFORMATION_MESSAGE);
                        tableModel.setValueAt(Person.getPersonID(), selectedRow, 0);
                        tableModel.setValueAt(Person.getID_type(), selectedRow, 1);
                        tableModel.setValueAt(Person.getID_number(), selectedRow, 2);
                        tableModel.setValueAt(Person.getName(), selectedRow, 3);
                        tableModel.setValueAt(Person.getSex(), selectedRow, 4);
                        tableModel.setValueAt(Person.getEthnicity(), selectedRow, 5);
                        tableModel.setValueAt(Person.getBirthday(), selectedRow, 6);
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
                    if (PersonService.deletPerson(id)) {
                        JOptionPane.showMessageDialog(panelMain, "删除成功", "信息提示框", JOptionPane.INFORMATION_MESSAGE);
                        // 将输入框清空
                        tfName.setText("");
                        tfPersonID.setText("");
                        tfSearch.setText("");
                        tfID_type.setText("");
                        tfID_number.setText("");
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
                ArrayList<Person> Persons = null;
                String type = "歌名";
                for (int i = 0; i < btnSearchTypes.length; i++) {
                    if (btnSearchTypes[i].isSelected()) {
                        type = btnSearchTypes[i].getText();
                        //System.out.println(type+"**********");
                    }
                }
                if ("个人ID".equals(type)) {
                    String PersonID = tfSearch.getText();
                    //System.out.println("name:" + PersonID);
                    Persons = PersonService.findByPersonID(PersonID);
                    System.out.println("查询出来的"+Persons);
                } else if ("姓名".equals(type)) {
                    String name = tfSearch.getText();
                    System.out.println(name);
                    Persons = PersonService.findByName(name);
                    System.out.println("查询出来的"+Persons);
                } else if ("身份证号".equals(type)) {
                    String id_number= tfSearch.getText();
                    Persons = PersonService.findByID_number(id_number);
                    System.out.println("查询出来的"+Persons);
                } else {
                    JOptionPane.showMessageDialog(panelMain, "请选择查询类型", "信息提示框", JOptionPane.INFORMATION_MESSAGE);
                }
                fillPersoninfoToTable(Persons);
            }
        });
    }
    // 查询时使用，清空表格数据，再重新添加行
    private void fillPersoninfoToTable(ArrayList<Person> Persons) {
        if (Persons.isEmpty()) {
            JOptionPane.showMessageDialog(panelMain, "Sorry, 未查询到数据，请从新输入", "信息提示框", JOptionPane.INFORMATION_MESSAGE);
        }
        tableModel.setRowCount(0); // 将表格清空
        String[][] tableValues = new String[Persons.size()][7];
        // 重新装填数据
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

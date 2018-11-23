package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import entity.User;
public class AdminMainView extends JFrame{
    private static final long serialVersionUID = 1L;
    private JPanel panelMain;
    private JPanel panelTop;
    private JLabel labWelcome;

    private JPanel panelLeft;
    private JButton btnPersonManage;
    private JButton btnDrugManage;
    //private JButton btnEditUserInfo;
    private JButton btnPreSettleManage;
    private JButton btnSettleManage;
    private JButton btnPrescribeManage;
    private JButton btnTreatManage;
    
    private JDesktopPane panelContent;
    private JLabel labImg;
    private User user;
    public AdminMainView(User user) {
        this.user = user;
        System.out.println("测试开始——————————————————————");
        init();
        
        System.out.println("29");
        personManageView();
        System.out.println("32");
        drugManageView();
        adminTreatMangeView();
        adminPrescribeManageView();
        //userInfoEditView();
        preSettleManage();
    }

    private void init() {
        panelMain = new JPanel(new BorderLayout());
        panelTop = new JPanel();
        labWelcome = new JLabel("欢     迎    进    入    报   销    中    心    系    统");
        labWelcome.setFont(new Font("宋体",Font.BOLD,22));
        labWelcome.setForeground(Color.BLUE);
        panelTop.add(labWelcome);
        panelTop.setPreferredSize(new Dimension(1000,100));
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Thread(new DynaminThread()).start();
            }
        });
        panelMain.add(panelTop, BorderLayout.NORTH);

        panelLeft = new JPanel(new GridLayout(6,1,0,40));
        panelLeft.setBorder(BorderFactory.createTitledBorder("菜单栏"));
        btnPersonManage = new JButton("人员管理");
        btnDrugManage = new JButton("药品管理");
        //btnEditUserInfo = new JButton("修改密码");
        btnPreSettleManage = new JButton("预结算");
        btnSettleManage = new JButton("结算");
        btnPrescribeManage = new JButton("处方信息管理");
        btnTreatManage = new JButton("就诊信息管理");
        //panelLeft.add(new JLabel());
        panelLeft.add(btnPersonManage);
        //panelLeft.add(new JLabel());
        panelLeft.add(btnDrugManage);
        //panelLeft.add(new JLabel());
        //panelLeft.add(btnEditUserInfo);
        panelLeft.add(btnPreSettleManage);
        panelLeft.add(btnSettleManage);
        panelLeft.add(btnPrescribeManage);
        panelLeft.add(btnTreatManage);
        panelLeft.setPreferredSize(new Dimension(200,600));
        panelMain.add(panelLeft,BorderLayout.WEST);
        //System.out.println("74");
        panelContent = new JDesktopPane();
        //System.out.println("75");
        ImageIcon image = new ImageIcon("src/shiyan6/img/song.jpg");
        labImg = new JLabel(image);
        labImg.setBounds(15, 15, 750, 550);
        //System.out.println("80");
        panelContent.add(labImg, new Integer(Integer.MIN_VALUE));
        //System.out.println("82");
        panelContent.setBorder(BorderFactory.createTitledBorder("内容"));
        //System.out.println("84");
        panelMain.add(panelContent, BorderLayout.CENTER); // 添加到主界面的中间
        //System.out.println("86");
        this.setTitle("报销中心结算系统");
        this.getContentPane().add(panelMain);
        this.setSize(1000, 800); // 设置窗体大小
        this.setLocationRelativeTo(null); // 让窗体显示在屏幕中央
        this.setResizable(true); // 窗体大小不可变
        this.setDefaultCloseOperation(EXIT_ON_CLOSE); // 设置关闭按钮
        this.setBackground(Color.white);
        this.setVisible(true);  // 让窗体可见
    }
    /**
     * 让欢迎的label动起来
     * 因为swing是单线程的，因此需要启动一个线程
     */
    private class DynaminThread implements Runnable {
    	public void run() {
    		while(true) {
    			for(int i = 1000; i > -980; i--) {
    				try {
    					Thread.sleep(10);
    				} catch(Exception e) {
    					e.printStackTrace();
    				}
    				labWelcome.setLocation(i,30);
    			}
    		}
    	}
    }
    /**
     * 添加事件，跳转管理歌曲的页面
     */
    private void personManageView() {
    	//System.out.println("118");
    	btnPersonManage.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			//System.out.println("121");
    			AdminPersonManageView sManagerView = new AdminPersonManageView();
    			//System.out.println("123");
    			//将指定的试图给添加到JDeskTopPanel中
    			panelContent.add(sManagerView);
    			//System.out.println("126");
    			//将视图放在最前面
    			System.out.println("128");
    			sManagerView.toFront();
    			//System.out.println("130");
    		}
    	});
    }
    private void drugManageView() {
    	btnDrugManage.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			AdminDrugMangeView d = new AdminDrugMangeView();
    			panelContent.add(d);
    			d.toFront();
    		}
    	});
    }
    
    public void adminTreatMangeView() {
    	btnTreatManage.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			AdminTreatMangeView t = new AdminTreatMangeView();
    			panelContent.add(t);
    			t.toFront();
    		}
    	});
    }
    
    public void adminPrescribeManageView() {
    	btnPrescribeManage.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			AdminPrescribeManageView apm = new AdminPrescribeManageView();
    			panelContent.add(apm);
    			apm.toFront();
    		}
    	});
    }
    public void preSettleManage() {
    	btnPreSettleManage.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			settleMange();
    			PreSettleManage psm = new PreSettleManage();
    			panelContent.add(psm);
    			psm.toFront();
    		}
    	}); 
    }
    public void settleMange() {
    	btnSettleManage.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			JOptionPane.showMessageDialog(panelMain, "结算已完成，正在打印结算单.....", "信息提示框", JOptionPane.INFORMATION_MESSAGE);
    		}
    	});
    }
}

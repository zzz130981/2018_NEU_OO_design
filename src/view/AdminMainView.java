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
        System.out.println("���Կ�ʼ��������������������������������������������");
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
        labWelcome = new JLabel("��     ӭ    ��    ��    ��   ��    ��    ��    ϵ    ͳ");
        labWelcome.setFont(new Font("����",Font.BOLD,22));
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
        panelLeft.setBorder(BorderFactory.createTitledBorder("�˵���"));
        btnPersonManage = new JButton("��Ա����");
        btnDrugManage = new JButton("ҩƷ����");
        //btnEditUserInfo = new JButton("�޸�����");
        btnPreSettleManage = new JButton("Ԥ����");
        btnSettleManage = new JButton("����");
        btnPrescribeManage = new JButton("������Ϣ����");
        btnTreatManage = new JButton("������Ϣ����");
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
        panelContent.setBorder(BorderFactory.createTitledBorder("����"));
        //System.out.println("84");
        panelMain.add(panelContent, BorderLayout.CENTER); // ��ӵ���������м�
        //System.out.println("86");
        this.setTitle("�������Ľ���ϵͳ");
        this.getContentPane().add(panelMain);
        this.setSize(1000, 800); // ���ô����С
        this.setLocationRelativeTo(null); // �ô�����ʾ����Ļ����
        this.setResizable(true); // �����С���ɱ�
        this.setDefaultCloseOperation(EXIT_ON_CLOSE); // ���ùرհ�ť
        this.setBackground(Color.white);
        this.setVisible(true);  // �ô���ɼ�
    }
    /**
     * �û�ӭ��label������
     * ��Ϊswing�ǵ��̵߳ģ������Ҫ����һ���߳�
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
     * ����¼�����ת���������ҳ��
     */
    private void personManageView() {
    	//System.out.println("118");
    	btnPersonManage.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			//System.out.println("121");
    			AdminPersonManageView sManagerView = new AdminPersonManageView();
    			//System.out.println("123");
    			//��ָ������ͼ����ӵ�JDeskTopPanel��
    			panelContent.add(sManagerView);
    			//System.out.println("126");
    			//����ͼ������ǰ��
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
    			JOptionPane.showMessageDialog(panelMain, "��������ɣ����ڴ�ӡ���㵥.....", "��Ϣ��ʾ��", JOptionPane.INFORMATION_MESSAGE);
    		}
    	});
    }
}

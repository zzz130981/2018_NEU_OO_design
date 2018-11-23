package view;
import java.awt.Font;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import entity.User;
public class RegisterView extends JFrame {
	private static int count=0;
	private static JButton bt1;//��½��ť
	private static JButton bt2;//�������밴ť
	private static JLabel jl_1;//��¼�İ���
	private static JFrame jf_1;//��½�Ŀ��
    private static JTextField jtext1;//�û���
    private static JPasswordField jtext2;//����
    private static JLabel jl_admin;
    private static JLabel jl_password;
    public RegisterView (){//��ʼ����½����
    	Font font =new Font("����", Font.PLAIN, 20);//��������
	    jf_1=new JFrame("��½����");
		jf_1.setSize(450, 400);
		//����½������ӱ���ͼƬ
		ImageIcon bgim = new ImageIcon(RegisterView.class.getResource("img.jpg")) ;//����ͼ��
		bgim.setImage(bgim.getImage().
				                     getScaledInstance(bgim.getIconWidth(),
												       bgim.getIconHeight(), 
												       Image.SCALE_DEFAULT));  
		jl_1=new JLabel();
		jl_1.setIcon(bgim);
		
		jl_admin=new JLabel("�û���");
		jl_admin.setBounds(20, 50, 60, 50);
		jl_admin.setFont(font);
		
		jl_password=new JLabel("����");
		jl_password.setBounds(20, 120, 60, 50);
		jl_password.setFont(font);
		
		bt1=new JButton("��½");         //���ĳ�loginButton
		bt1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		bt1.setBounds(90, 250, 100, 50);
		bt1.setFont(font);
 
		bt2=new JButton("�˳�");
		bt2.setBounds(250, 250, 100, 50);
		bt2.setFont(font);
 
		//�����ı���
		jtext1=new JTextField("");
		jtext1.setBounds(150, 50, 250, 50);
		jtext1.setFont(font);
		
		jtext2=new JPasswordField("");//���������
		jtext2.setEchoChar('*');
		jtext2.setBounds(150, 120, 250, 50);
		jtext2.setFont(font);
		
		jl_1.add(jtext1);
		jl_1.add(jtext2);
		
		jl_1.add(jl_admin);
		jl_1.add(jl_password);
		jl_1.add(bt1);
		jl_1.add(bt2);
		
		jf_1.getContentPane().add(jl_1);
		jf_1.setVisible(true);
		jf_1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf_1.setLocation(300,200);
	}
	public static void main(String[] args) {
		//��ʼ����½����
		 
		RegisterView hl =new RegisterView();
		/**
		 * �������¼�
		 * 1.��½��ť����¼����ж��˺������Ƿ���ȷ������ȷ�����������Ϣ����
		 * ��������Ӧ����ʱ����Ӧ��
		 * ������ڵ�½�������һ��logLabel��ʾ�û��Ƿ��û�������ȷ
		 * 2.�˳���ť��ֱ���˳�����
		 */
		//��½����¼�
		ActionListener bt1_ls=new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String admin=jtext1.getText();
				char[] password=jtext2.getPassword();
				String str=String.valueOf(password); //��char����ת��Ϊstring����
				
				if(admin.equals("root")&&str.equals("123456"))
				{
					AdminMainView new_c = new AdminMainView(new User(admin,str));//Ϊ��ת�Ľ���
					hl.jf_1.dispose();//���ٵ�ǰ����
				}
				else {
					count++;
					System.out.println("error");
					if(count==3){
						hl.jf_1.dispose();
					}
				}
			}
		};
		bt1.addActionListener(bt1_ls);
		//�˳��¼��Ĵ���
		ActionListener bt2_ls=new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);//��ֹ��ǰ����
			}
		};
        bt2.addActionListener(bt2_ls);		
     }

}

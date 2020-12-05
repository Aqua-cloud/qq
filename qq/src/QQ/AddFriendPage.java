package QQ;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddFriendPage extends JFrame {
	private Admin user;
	int w = Toolkit.getDefaultToolkit().getScreenSize().width / 4;
	int h = Toolkit.getDefaultToolkit().getScreenSize().height / 3;

	public AddFriendPage(Admin user) {
		this.user = user;
		Container page = getContentPane();
		setLayout(null);

		JLabel nameStr = new JLabel("�˺�:");
		nameStr.setBounds(w * 2 / 10, h * 4 / 20, w / 10, h / 10);
		add(nameStr);

		JTextField userID = new JTextField();
		userID.setBounds(w * 3 / 10, h * 4 / 20, w * 5 / 10, h / 10);
		add(userID);

		JButton search = new JButton("����");
		search.setBounds(w * 4 / 10, h * 7 / 10, w / 5, h / 10);
		add(search);

		search.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String ID = userID.getText();  //�����Ƿ������id
				String sql1 = "select * from user where account = ?";

				try {
					Connection conn = JDBCUtils.getConnection();
					PreparedStatement ps = conn.prepareStatement(sql1);
					ps.setInt(1, Integer.parseInt(ID));
					ResultSet rs = ps.executeQuery();

					//System.out.println("����Ӧ���������޴���");
					if (rs.next()) {
						new Success(user,rs.getInt("account"),rs.getString("name"));
					} else fail();
					//JOptionPane.showMessageDialog(null, "��ûд��", "�Բ���", JOptionPane.WARNING_MESSAGE);


				} catch (SQLException e) {
					e.printStackTrace();
				}


			}
		});

		setTitle("����º���");
		setBounds(w * 3 / 2, h, w, h);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
		setResizable(false);
	}

	private class Success extends JFrame {
		private Admin user;
		private int friendId;
		private String friendName;
		public Success(Admin user,int friendId,String friendName) {
			this.user = user;
			this.friendId = friendId;
			this.friendName = friendName;
			setLayout(null);

			String search_result = this.friendName+"("+this.friendId+")";
			JLabel infoJLabel = new JLabel(search_result);
			infoJLabel.setBounds(w * 3 / 16, h * 2 / 24, w / 3, h / 12);
			add(infoJLabel);

			JButton confirm = new JButton("ȷ��");
			confirm.setBounds(w * 4 / 16, h * 5 / 24, w / 5, h / 10);
			add(confirm);
			setTitle("ȷ�����");
			setBounds(w * 5 / 3, h * 5 / 4, w * 2 / 3, h / 2);
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setVisible(true);
			setResizable(false);
			confirm.addActionListener(arg0 -> {

						System.out.println("�����������");
						addFriend();
						JOptionPane.showMessageDialog(null, "�ѷ����������", "", JOptionPane.YES_NO_CANCEL_OPTION);
					}
			);
		}

		public void addFriend(){
					/*
					��Ӻ��ѣ�˫�����
					1.�����趨sql����ǵ�ǰuser��friends�����Ҫ��ӵ�id
					2.ͨ����id�����ݿ����ҵ���Ӧ��user����ʹ��friends��ӵ�ǰuser��id
					 */
			try {
				String sql1 = "select friends from user where account = "+user.getID();  // ��ȡ��ǰ�û������б�
				String sql2 = "update user set friends = ? where account = "+user.getID();//���ĵ�ǰ�û������б�
				String sql3 = "select friends from user where account = "+friendId; //��ȡ���ѵĺ����б�
				String sql4 = "update user set friends = ? where account = "+friendId;//���ĺ��ѵĺ����б�
				Connection conn = JDBCUtils.getConnection();
				PreparedStatement ps1 = conn.prepareStatement(sql1);
				PreparedStatement ps2 = conn.prepareStatement(sql2);
				PreparedStatement ps3 = conn.prepareStatement(sql3);
				PreparedStatement ps4 = conn.prepareStatement(sql4);
				ResultSet rs1 = ps1.executeQuery();
				ResultSet rs2 = ps3.executeQuery();
				rs1.next();
				rs2.next();
				String currentList = rs1.getString("friends");
				String newCurrentList;
				if(currentList!=null){
					newCurrentList = currentList+"?"+friendId;  //1?2
				}else {
					newCurrentList = friendId+"";  //1
				}
				ps2.setString(1,newCurrentList);
				ps2.executeUpdate();



				//�����ǶԺ��ѵĲ���  ͬ��
				String friendCurList = rs2.getString("friends");
				String newFriendCurList;
				if(friendCurList!=null){
					newFriendCurList = friendCurList+"?"+user.getID();  //1?2
				}else {
					newFriendCurList = user.getID()+"";  //1
				}
				ps4.setString(1,newFriendCurList);
				ps4.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			}



		}
	}


	//��Ӻ��ѷ���

	public void fail() {
		JOptionPane.showMessageDialog(null, "�Ҳ������û�", "", JOptionPane.NO_OPTION);
	}
}
//	public static void main(String[] args) {
//		new AddFriendPage(user);
//	}
//}

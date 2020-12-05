package QQ;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.desktop.ScreenSleepEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import layout.AfColumnLayout;
import layout.AfRowLayout;

public class MainPage extends JFrame {
    private Admin user;
    private MyFriend[] friend_list;
    public final int h = Toolkit.getDefaultToolkit().getScreenSize().height * 2 / 3;
    public final int w = Toolkit.getDefaultToolkit().getScreenSize().width;
    private JPanel cards = new JPanel();
    JPanel chatJPanel = new JPanel();
    JPanel friendJPanel = new JPanel();

    public MainPage(Admin admin,MyFriend[] friend_list) {
        this.user = admin;
        for (MyFriend myFriend : friend_list) {
            System.out.println(myFriend.getName() + myFriend.getID() + myFriend.getlog_status());
        }

        JFrame mainFrame = new JFrame("主界面");

        //顶层容器
        Container container = mainFrame.getContentPane();
        container.setLayout(new AfColumnLayout());

        //用户信息区
        JLabel userinfoJLabel = new JLabel(user.getName() + "(" + user.getID() + ")");
        userinfoJLabel.setPreferredSize(new Dimension(0, 100));
        userinfoJLabel.setOpaque(true);
        userinfoJLabel.setBackground(Color.white);
        userinfoJLabel.setHorizontalAlignment(SwingConstants.CENTER);

        //搜索区
        JTextField searchField = new JTextField("搜索");
        searchField.setEditable(false);
        searchField.addKeyListener(new KeyAdapter() {

            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    System.out.println("这里应该搜索");
                }

            }
        });

        searchField.addFocusListener(new FocusListener() {

            @Override
            public void focusLost(FocusEvent arg0) {
                searchField.setText("搜索");

            }

            @Override
            public void focusGained(FocusEvent arg0) {
                searchField.setText("");

            }
        });
        //主要聊天功能区
        //消息面板
        
        chatJPanel.setLayout(new GridLayout(0, 1, 5, 5));
        JScrollPane chat_list = new JScrollPane(chatJPanel);

        //联系人面板
        
        friendJPanel.setLayout(new GridLayout(0, 1, 5, 5));
        //获取好友
        reflashFriendPanel(friend_list);
        

        JScrollPane friends_list = new JScrollPane(friendJPanel);

        //主要聊天功能区容器
        JPanel optionJPanel = new JPanel();
        optionJPanel.setLayout(new BorderLayout());

        //面板选择框
        JPanel selectJPanel = new JPanel();
        selectJPanel.setLayout(new AfRowLayout());

        JLabel chat_listJLabel = new JLabel("消息");
        chat_listJLabel.setHorizontalAlignment(SwingConstants.CENTER);
        chat_listJLabel.setOpaque(true);
        chat_listJLabel.setBackground(Color.white);
        chat_listJLabel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent arg0) {
                change2chat();
            }

            @Override
            public void mouseEntered(MouseEvent arg0) {
                mouthEnterColorChange(chat_listJLabel);
            }

            @Override
            public void mouseExited(MouseEvent arg0) {
                mouthExitColorChange(chat_listJLabel);
            }

        });

        JLabel friend_listJLabel = new JLabel("联系人");
        friend_listJLabel.setHorizontalAlignment(SwingConstants.CENTER);
        friend_listJLabel.setOpaque(true);
        friend_listJLabel.setBackground(Color.white);
        friend_listJLabel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent arg0) {
                change2friend();
            }

            @Override
            public void mouseEntered(MouseEvent arg0) {
                mouthEnterColorChange(friend_listJLabel);
            }

            @Override
            public void mouseExited(MouseEvent arg0) {
                mouthExitColorChange(friend_listJLabel);
            }
        });

        selectJPanel.add(chat_listJLabel, "1w");
        selectJPanel.add(friend_listJLabel, "1w");

        //布局卡片
        cards.setLayout(new CardLayout());
        cards.add(friends_list, "friend");
        cards.add(chat_list, "chat");

        //将面板卡片和选择框放入功能区容器
        optionJPanel.add(selectJPanel, BorderLayout.NORTH);
        optionJPanel.add(cards, BorderLayout.CENTER);

        //底部功能栏
        JPanel buttonJPanel = new JPanel();
        buttonJPanel.setLayout(new AfRowLayout());

        //添加朋友按钮
        JLabel addNewFriend = new JLabel("添加新朋友");
        addNewFriend.setOpaque(true);
        addNewFriend.setBackground(Color.white);
        addNewFriend.setHorizontalAlignment(SwingConstants.CENTER);
        addNewFriend.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    new AddFriendPage(user);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                mouthEnterColorChange(addNewFriend);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mouthExitColorChange(addNewFriend);
            }
        });

        //将各按钮添加进底部栏
        buttonJPanel.add(addNewFriend, "auto");

        //各功能区加进顶层容器
        container.add(userinfoJLabel, h / 8 + "px");
        container.add(searchField, h / 24 + "px");
        container.add(optionJPanel, "1w");
        container.add(buttonJPanel, h / 24 + "px");

        //初始化主窗口
        mainFrame.setBounds(w * 2 / 3, h / 4, h * 2 / 5, h);
        mainFrame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent arg0) {
                // TODO Auto-generated method stub
                super.windowClosing(arg0);
                exit();
            }
        });
        mainFrame.setVisible(true);
    }

    private void change2friend() {
        CardLayout cardLayout = (CardLayout) cards.getLayout();
        cardLayout.show(cards, "friend");
    }

    private void change2chat() {
        CardLayout cardLayout = (CardLayout) cards.getLayout();
        cardLayout.show(cards, "chat");
    }

    final void mouthEnterColorChange(Component l) {
        l.setBackground(new Color(240, 240, 240));
    }

    final void mouthExitColorChange(Component l) {
        l.setBackground(Color.white);
    }

    private class FriendRowJLabel extends JLabel {

        public FriendRowJLabel(MyFriend i, Admin user) {
            super(i.getName() + "(" + i.getID() + ")");
            DrawIcon icon;
            if (!i.getlog_status()) {
                icon = new DrawIcon(Color.gray);
            } else {
                icon = new DrawIcon(Color.green);
            }
            setIcon(icon);
            setOpaque(true);
            setPreferredSize(new Dimension(60, 60));
            setHorizontalAlignment(SwingConstants.LEFT);
            setBackground(Color.white);
            if (!i.getlog_status()) setForeground(Color.gray);
            else setForeground(new Color(0, 100, 0));
            Component l = this;

            JMenuItem delete_friden_option = new JMenuItem("删除好友");
            delete_friden_option.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent arg0) {
                    // TODO 删除好友操作
                    delFriend(i.getID());
                    System.out.println(i.getID() + i.getName() + "这个好友被删了");
                }
            });

            JPopupMenu popup = new JPopupMenu();
            popup.add(delete_friden_option);


            addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) {
                        new ChatPages(i.getID(),new ChatPage(i, user));

                    }
                    if (e.getButton() == MouseEvent.BUTTON3) {

                        popup.show(e.getComponent(), e.getX(), e.getY());

                    }
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    mouthEnterColorChange(l);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    mouthExitColorChange(l);
                }
            });
        }
    }

    private class ChatRowPanel extends JPanel {

        public ChatRowPanel(MyFriend i, Admin user) {
            SpringLayout springLayout = new SpringLayout();
            this.setLayout(springLayout);

            DrawIcon icon;
            if (!i.getlog_status()) {
                icon = new DrawIcon(Color.gray);
            } else {
                icon = new DrawIcon(Color.green);
            }

            JLabel name1 = new JLabel(i.getName(), icon, SwingConstants.LEFT);
            add(name1);
            springLayout.putConstraint(SpringLayout.NORTH, name1, 5, SpringLayout.NORTH, this);
            springLayout.putConstraint(SpringLayout.WEST, name1, 10, SpringLayout.WEST, this);
            JLabel rcJLabel = new JLabel(i.getrecent3chat()[0]);
            rcJLabel.setForeground(Color.LIGHT_GRAY);
            add(rcJLabel);
            springLayout.putConstraint(SpringLayout.WEST, rcJLabel, 10, SpringLayout.WEST, this);
            springLayout.putConstraint(SpringLayout.SOUTH, rcJLabel, -5, SpringLayout.SOUTH, this);
            springLayout.putConstraint(SpringLayout.EAST, rcJLabel, -10, SpringLayout.EAST, this);

            setOpaque(true);
            setPreferredSize(new Dimension(60, 60));
            setBackground(Color.white);
            if (!i.getlog_status()) name1.setForeground(Color.gray);
            else name1.setForeground(new Color(0, 100, 0));
            Component l = this;
            addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) {
                    	new ChatPages(i.getID(),new ChatPage(i, user));
                    }
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    mouthEnterColorChange(l);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    mouthExitColorChange(l);
                }
            });
        }
    }

    //画一个圆
    public class DrawIcon implements Icon {

        int width = 10;
        int height = 10;
        Color color;

        public DrawIcon(Color color) {
            this.color = color;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            // TODO Auto-generated method stub
            g.setColor(color);
            g.fillOval(x, y, width, height);
        }

        @Override
        public int getIconWidth() {
            // TODO Auto-generated method stub
            return this.width;
        }

        @Override
        public int getIconHeight() {
            // TODO Auto-generated method stub
            return this.height;
        }
    }

    //关闭主页面
    public void exit() {
        //这里写退出前的操作   todo 关闭时设置当前账户的status为0
        try {
            String sql1 = "update user set status = 0 where account = ?";
            Connection conn = JDBCUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql1);
            ps.setInt(1, user.getID());
            ps.executeUpdate();
            System.out.println("关闭前需要执行的操作");
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    //删除好友功能
    public void delFriend(int delId) {
        //del为好友id
        int curId = this.user.getID();
        infoDelFri(curId,delId);
        infoDelFri(delId,curId);
    }
    public void infoDelFri(int curId, int delId){
        try {
            Connection conn = JDBCUtils.getConnection();
            String sql1 = "select friends from user where account =" + curId;
            PreparedStatement ps1 = conn.prepareStatement(sql1);
            ResultSet rs1 = ps1.executeQuery();
            rs1.next();
            String curFriends = rs1.getString("friends");
            String[] friendsList = curFriends.split("\\?");
            String newFriends = "";
            String chars = "?";
            for (int i = 0; i < friendsList.length; i++) {
                if (Integer.parseInt(friendsList[i]) != delId) {
                    // if()
                    newFriends += chars + friendsList[i];
                } else continue;
            }
            newFriends = newFriends.substring(1); //获取要添加到当前用户的新好友列表字符串
            //2.将数据库中的此列表进行改变
            String sql2 = "update user set friends = ? where account = "+curId;
            PreparedStatement ps2 = conn.prepareStatement(sql2);
            ps2.setString(1,newFriends);
            ps2.executeUpdate();
            //System.out.println(newFriends);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    //刷新好友列表
    void reflashFriendPanel(MyFriend[] friends_list) {
    	chatJPanel.removeAll();
    	friendJPanel.removeAll();
    	chatJPanel.repaint();
    	friendJPanel.repaint();
    	for (MyFriend i : friends_list) {
            ChatRowPanel a2 = new ChatRowPanel(i, user);
            chatJPanel.add(a2);
        }
        for (MyFriend i : friends_list) {
            FriendRowJLabel a1 = new FriendRowJLabel(i, user);
            friendJPanel.add(a1);
        }
        chatJPanel.revalidate();
        friendJPanel.revalidate();
    }
}

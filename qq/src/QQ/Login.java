package QQ;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.accessibility.AccessibleAttributeSequence;

import com.sun.jdi.PathSearchingVirtualMachine;

public class Login {

	Admin admin;
	
	public void setAdmin(Admin admin) {
		this.admin=admin;
		//System.out.println(this.admin.getPassword()+"   " + this.admin.getID());
	}
	/*
	 * JudgeAdmin()方法
	 * 判断Admin的ID和密码是否正确，如果正确，显示登录成功
	 * 如果错误，弹出一个窗口，显示账号或密码错误
	 */

	public Admin login(Admin user) throws Exception {
		//System.out.println(111);
		String sql1="select * from user where account=? and password=?";
		Connection conn = JDBCUtils.getConnection();
		PreparedStatement ps = conn.prepareStatement(sql1);
		PreparedStatement ps2 = null;
		ps.setInt(1, user.getID());
		ps.setString(2, user.getPassword());
		ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			String sql2 = "update user set status = 1 where account=?";
			ps2 = conn.prepareStatement(sql2);
			ps2.setInt(1,user.getID());
			ps2.executeUpdate();
			//设置当前用户为在线状态
			user.setlog_status(true);
			user.setName(rs.getString("name"));
			user.setFriends(rs.getString("friends"));
			//close
			JDBCUtils.close(null,null,ps2);
			JDBCUtils.close(rs,conn,ps);
			return user;
		}else {
			JDBCUtils.close(null,null,ps2);
			JDBCUtils.close(rs,conn,ps);
			return null;
		}


	}
	//加载用户信息
	public static void loadStatus(Admin user){

	}
}

package QQ;

public class Admin implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;                 //���
	private String name;           //����
	private String password;      //����
	private boolean log_status;     //��½״̬
	private String friends;       //�����б�
	public void setID(int id) {
	    this.id=id;
	}
	public void setName(String name) {
	    this.name=name;
	}
	public void setPassword(String password) {
	    this.password=password;
	}
	public void setlog_status(boolean log_status) {
	    this.log_status=log_status;
	}

	public int getID() {
	    return this.id;
	}

	public String getFriends() {
		return friends;
	}

	public void setFriends(String friends) {
		this.friends = friends;
	}

	public String getName() {
	    return this.name;
	}
	public String getPassword() {
	    return this.password;
	}
	boolean getlog_status() {
		return this.log_status;
	}

}
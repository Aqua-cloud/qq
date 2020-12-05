package QQ;

public class MyFriend  implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;              //账号
	private String name;          //姓名
	private boolean log_status;     //登陆状态
	private String recent3chat[];//最近的三条信息
	
	
	void setID(int id) {
	    this.id=id;
	}
	void setName(String name) {
	    this.name=name;
	}
	void setlog_status(boolean log_status) {
	    this.log_status=log_status;
	}
	void setrecent3chat(String recent3chat[]) {
		this.recent3chat = recent3chat;
	}
	
	
	int getID() {
	    return this.id;
	}

	public MyFriend() {
	}

	public MyFriend(int id, String name, boolean log_status) {
		this.id = id;
		this.name = name;
		this.log_status = log_status;
		this.recent3chat = new String[]{"dad", "mom", "son"};
	}

	public MyFriend(int id, String name, boolean log_status, String[] recent3chat) {
		this.id = id;
		this.name = name;
		this.log_status = log_status;
		this.recent3chat = recent3chat;
	}

	String getName() {
	    return this.name;
	}
	boolean getlog_status() {
		return this.log_status;
	}
	String[] getrecent3chat() {
		return this.recent3chat;
	}

}
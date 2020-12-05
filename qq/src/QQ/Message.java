package QQ;

public class Message implements java.io.Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//以后用来代替格式化字符串的类
	int sender;
	String senderName;
	int receiver;
	String receiverName;
	String date;
	String message;
	
	public Message(int sender,int receiver,String date,String message) {
		this.sender = sender;this.receiver = receiver;
		this.date = date;this.message = message;
	}
	
	public void setSender(int sender) {
		this.sender = sender;
	}
	
	public void setReceiver(int sender) {
		this.receiver = sender;
	}
	
	public void setSenderName(String sender) {
		this.senderName = sender;
	}
	
	public void setReceiverName(String sender) {
		this.receiverName = sender;
	}
	
	public void setDate(String sender) {
		this.date = sender;
	}
	
	public void setMessage(String sender) {
		this.message = sender;
	}
	
	public int getSender() {
		return this.sender;
	}
	
	public int getReceiver() {
		return this.receiver;
	}
	
	public String getSenderName() {
		return this.senderName;
	}
	
	public String getReceiverName() {
		return this.receiverName;
	}
	
	public String getDate() {
		return this.date;
	}
	
	public String getMessage() {
		return this.message;
	}
}

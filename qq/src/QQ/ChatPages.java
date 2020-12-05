package QQ;

import java.util.HashMap;
import java.util.Map;

//存放所有正在进行的对话框

public class ChatPages {

	public static Map<Integer, ChatPage> pages =
			new HashMap<Integer, ChatPage>();
	
	public ChatPages() {}
	
	//管理所有聊天框
	public ChatPages(int id, ChatPage page) {
		pages.put(id, page);
	}
	
	//关闭一个聊天框
	public void ClosePage(int id, ChatPage page) {
		page.dispose();
		pages.remove(id);
	}
	
	//根据id获得该用户的聊天框
	public ChatPage getPage(int id) {
		ChatPage page = pages.get(id);
		if(page!=null) return page;
		else return null;
	}
	
}

package QQ;

import java.util.HashMap;
import java.util.Map;

//����������ڽ��еĶԻ���

public class ChatPages {

	public static Map<Integer, ChatPage> pages =
			new HashMap<Integer, ChatPage>();
	
	public ChatPages() {}
	
	//�������������
	public ChatPages(int id, ChatPage page) {
		pages.put(id, page);
	}
	
	//�ر�һ�������
	public void ClosePage(int id, ChatPage page) {
		page.dispose();
		pages.remove(id);
	}
	
	//����id��ø��û��������
	public ChatPage getPage(int id) {
		ChatPage page = pages.get(id);
		if(page!=null) return page;
		else return null;
	}
	
}

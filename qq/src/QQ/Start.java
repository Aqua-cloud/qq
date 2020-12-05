package QQ;


import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.util.Enumeration;

public class Start {
	public static void main(String[] args) {
		setUIFont (new FontUIResource("΢���ź�", Font.PLAIN,12));
		new Linker();
	}


	//����ҳ����ʽ
	public static void setUIFont (javax.swing.plaf.FontUIResource f){
	    Enumeration keys = UIManager.getDefaults().keys();
	    while (keys.hasMoreElements()) {
	      Object key = keys.nextElement();
	      Object value = UIManager.get (key);
	      if (value instanceof javax.swing.plaf.FontUIResource)
	        UIManager.put (key, f);
	      }
	    } 
	
}

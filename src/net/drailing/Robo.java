package net.drailing;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.HashMap;

public class Robo {

	private static Robot robo;
	
	public static HashMap<String, Integer> mapping = new HashMap<String, Integer>();
	
	private static Robot getRobo() {
		if(robo == null){
			try {
				fillHashMap();
				robo = new Robot();
			} catch (AWTException e) {
				e.printStackTrace();
			}
		}
		return robo;
	}
	
	private static String[] prepareCommand(String c){
		c = c.replaceAll("#", "");
		c = c.toLowerCase();
		c = c.replaceAll("\\s", "");
		String[] tmp = null;
		if(c.contains("+")){
			tmp = c.split("\\+");
		}else{
			tmp = new String[1];
			tmp[0] = c;
		}
		return tmp;
	}
	
	public static void pressKey(String command){
		
		String[] toProcess = prepareCommand(command);
		
		if(toProcess.length == 1){
			getRobo().keyPress(Robo.mapping.get(toProcess[0]).intValue());
			getRobo().keyRelease(Robo.mapping.get(toProcess[0]).intValue());
		}else{
			
			for(String cmd : toProcess){
				getRobo().keyPress(Robo.mapping.get(cmd).intValue());
			}
			
			for(String cmd : toProcess){
				getRobo().keyRelease(Robo.mapping.get(cmd).intValue());
			}
		}
		
		
		
	}
	
	
	private static void fillHashMap(){
		mapping.put("space", new Integer(KeyEvent.VK_SPACE));
		mapping.put("alt", new Integer(KeyEvent.VK_ALT));
		mapping.put("ctrl", new Integer(KeyEvent.VK_CONTROL));
		mapping.put("shift", new Integer(KeyEvent.VK_SHIFT));
		mapping.put("left", new Integer(KeyEvent.VK_LEFT));
		mapping.put("right", new Integer(KeyEvent.VK_RIGHT));
		mapping.put("up", new Integer(KeyEvent.VK_UP));
		mapping.put("down", new Integer(KeyEvent.VK_DOWN));
		mapping.put("tab", new Integer(KeyEvent.VK_TAB));
		mapping.put("win", new Integer(KeyEvent.VK_WINDOWS));
		mapping.put("plus", new Integer(KeyEvent.VK_PLUS));
		mapping.put("minus", new Integer(KeyEvent.VK_MINUS));
		mapping.put("del", new Integer(KeyEvent.VK_DELETE));
		
		mapping.put("1", new Integer(KeyEvent.VK_1));
		mapping.put("2", new Integer(KeyEvent.VK_2));
		mapping.put("3", new Integer(KeyEvent.VK_3));
		mapping.put("4", new Integer(KeyEvent.VK_4));
		mapping.put("5", new Integer(KeyEvent.VK_5));
		mapping.put("6", new Integer(KeyEvent.VK_6));
		mapping.put("7", new Integer(KeyEvent.VK_7));
		mapping.put("8", new Integer(KeyEvent.VK_8));
		mapping.put("9", new Integer(KeyEvent.VK_9));
		mapping.put("0", new Integer(KeyEvent.VK_0));
		
		mapping.put("f1", new Integer(KeyEvent.VK_F1));
		mapping.put("f2", new Integer(KeyEvent.VK_F2));
		mapping.put("f3", new Integer(KeyEvent.VK_F3));
		mapping.put("f4", new Integer(KeyEvent.VK_F4));
		mapping.put("f5", new Integer(KeyEvent.VK_F5));
		mapping.put("f6", new Integer(KeyEvent.VK_F6));
		mapping.put("f7", new Integer(KeyEvent.VK_F7));
		mapping.put("f8", new Integer(KeyEvent.VK_F8));
		mapping.put("f9", new Integer(KeyEvent.VK_F9));
		mapping.put("f10", new Integer(KeyEvent.VK_F10));
		mapping.put("f11", new Integer(KeyEvent.VK_F11));
		mapping.put("f12", new Integer(KeyEvent.VK_F12));
		
		mapping.put("a", new Integer(KeyEvent.VK_A));
		mapping.put("b", new Integer(KeyEvent.VK_B));
		mapping.put("c", new Integer(KeyEvent.VK_C));
		mapping.put("d", new Integer(KeyEvent.VK_D));
		mapping.put("e", new Integer(KeyEvent.VK_E));
		mapping.put("f", new Integer(KeyEvent.VK_F));
		mapping.put("g", new Integer(KeyEvent.VK_G));
		mapping.put("h", new Integer(KeyEvent.VK_H));
		mapping.put("i", new Integer(KeyEvent.VK_I));
		mapping.put("j", new Integer(KeyEvent.VK_J));
		mapping.put("k", new Integer(KeyEvent.VK_K));
		mapping.put("l", new Integer(KeyEvent.VK_L));
		mapping.put("m", new Integer(KeyEvent.VK_M));
		mapping.put("n", new Integer(KeyEvent.VK_N));
		mapping.put("o", new Integer(KeyEvent.VK_O));
		mapping.put("p", new Integer(KeyEvent.VK_P));
		mapping.put("q", new Integer(KeyEvent.VK_Q));
		mapping.put("r", new Integer(KeyEvent.VK_R));
		mapping.put("s", new Integer(KeyEvent.VK_S));
		mapping.put("t", new Integer(KeyEvent.VK_T));
		mapping.put("u", new Integer(KeyEvent.VK_U));
		mapping.put("v", new Integer(KeyEvent.VK_V));
		mapping.put("w", new Integer(KeyEvent.VK_W));
		mapping.put("x", new Integer(KeyEvent.VK_X));
		mapping.put("y", new Integer(KeyEvent.VK_Y));
		mapping.put("z", new Integer(KeyEvent.VK_Z));
	}
	

}


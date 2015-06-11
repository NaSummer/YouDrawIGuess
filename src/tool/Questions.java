package tool;

/**
 * @author Qiufan(Andy) Xu 
 * @date CreateTime: Jun 11, 2015 2:22:13 PM 
 * @version 1.0 
 * @param 
 */

public class Questions {
	
	String[] questions;
	
	public Questions() {
		questions = new String[] {
			"Sun",
			"Apple",
			"Shit",
			"Banana",
			"Window",
			"Tree"
		};
	}
	
	public String getQuestion() {
		return questions[(int)Math.random()*questions.length];
	}
	
}

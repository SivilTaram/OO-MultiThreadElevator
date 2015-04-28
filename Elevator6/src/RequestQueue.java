import java.util.Vector;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Scanner;

import org.elevator.warn.FormatException;

/**
 * The Class will solve the problems as following:
 * 1��Analyze the input stream and convert it to array to store information.
 */
public class RequestQueue { 
	
	private Vector<Request> RequestQ;
	private Request one;
	
	public RequestQueue(){
		RequestQ = new Vector<Request>();
	}
	
	public void AddRequest(Request a){
		RequestQ.addElement(a);
	}
	
	public Vector<Request> getQueue(){
		return RequestQ;
	}
	
	public void RemoveQueue(){
		for(int i=0;i<RequestQ.size();){
			RequestQ.remove(0);
		}
	}
	
	public boolean TimeCheck(String s) throws FormatException{
		String RegexExpression="(\\d+)";
		Pattern p =Pattern.compile(RegexExpression);
		Matcher m =p.matcher(s);
		if(m.matches()){
			if(RequestQ.size()==0)
				return true;
			else if(RequestQ.lastElement().getRequestTime()<=Double.parseDouble(s)){
				return true;
			}
			else
				return false;
		}
		else
			return false;
	}
	
}



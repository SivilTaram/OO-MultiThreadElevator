
public class Request {
	
	private double time=0;
	private String type=null;
	private int ID=0;
	private String direction=null;
	private int n;
	
	public Request(String type,int ID,double time){
		//this is for leave or join
		this.type = type;
		this.ID   = ID;
		this.time = time;
	}
	
	public Request(String type,int n,String direction,double time){
		//(F_R, n, UP/DOWN,t)
		this.type = type;
		this.n = n;
		this.direction = direction;
		this.time = time;
	}
	
	public Request(String type,int n,int ID,double time){
		//(E_R, n, e_ID, t)
		this.type = type;
		this.n = n;
		this.ID = ID;
		this.time = time;
	}
	
	public String getDirection(){
		return direction;
	}
	
	public int getRequestFloor(){
		return n;
	}
	
	public double getRequestTime(){
		return time;
	}
	
	public String getType(){
		return type;
	}
	
	public int getIDIndex(){
		return (ID-1);
	}
	
	public int getID(){
		return ID;
	}
}


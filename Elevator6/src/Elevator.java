import java.util.Vector;

import javax.swing.JTextArea;


public class Elevator extends Thread{
	
	private int DoorFlag;
	private double time;
	//Elevator's time is equal to system time.
	private int EID;
	//EID = elevator ID
	private int work;
	private boolean isLeave;
	private JTextArea textarea;
	/**
	 * true : the elevator can't run;
	 * false : the elevator can run;
	 * @param isLeave
	 *
	 */
	private int currentFloor;
	private int TargetFloor;
	
	private boolean isMove;
	/**
	 * true : the elevator is running;
	 * false : the elevator is idle;
	 * @param isMove
	 */
	
	private boolean isUp;
	
	private Vector<Request> EleQueue;
	
	public int getCurrentFloor(){
		return currentFloor;
	}
	
	public boolean ERQueueCheck(int requestfloor){
		if((requestfloor<=10 && requestfloor >currentFloor) && (isUp==true)){
			return true;
		}
		else if((requestfloor>=1 && requestfloor <currentFloor) && (isUp==false)){
			return true;
		}
		else
			return false;
		
	}
	
	public boolean FRQueueCheck(int requestfloor,String direction){
		if((requestfloor <= TargetFloor && requestfloor >currentFloor) && (isUp==true && direction.equals("UP"))){
			return true;
		}
		else if((requestfloor >= TargetFloor && requestfloor <currentFloor) && (isUp==false && direction.equals("DOWN"))){
			return true;
		}
		else
			return false;
	}
	
	public void SetInitialQueue(int targetFloor,Request a){
		if(currentFloor<=targetFloor){
			isMove = true;
			isUp = true;
		}
		else if(currentFloor>targetFloor){
			isMove = true;
			isUp = false;
		}
		this.TargetFloor = targetFloor;
		EleQueue.addElement(a);
	}
	
	public int getWork(){
		return work;
	}
	
	public void Add2Queue(Request a){
		EleQueue.addElement(a);
		this.TargetFloor = Math.max(TargetFloor, a.getRequestFloor());
	}
	
	public Elevator(int ID,JTextArea textarea){
		this.EID = ID;
		this.isLeave = false;
		this.time = 0;
		this.currentFloor = 1;
		this.isMove = false;
		this.isUp = true;
		this.EleQueue = new Vector<Request>();
		this.work = 0;
		this.DoorFlag = 0;
		this.textarea = textarea;
	}
	
	public boolean isUp(){
		return isUp;
	}
	
	public boolean getStatus(){
		return isMove;
	}
	
	public boolean isLeave(){
		return isLeave;
	}
	
	public void Leave() throws Exception{
		if(isLeave==false)
			isLeave=true;
		else
			throw new Exception("You can't let a elevator leave which is not running!");
		for(int i=0;i<EleQueue.size();)
			EleQueue.remove(i);
		isMove = false;
		TargetFloor = currentFloor;
	}
	
	public void Join(double time) throws Exception{
		if(isLeave==true){
			isLeave=false;
			this.time = time;
		}
		else
			throw new Exception("You can't let a elevator run whichi hasn't left!");
	}
	
	public void Move(){
		if(isMove){
			if(isUp){
				currentFloor+=1;
				work++;
			}
			else{
				currentFloor-=1;
				work++;
			}
		}
		else{
			currentFloor+=0;
		}
	}
	
	public void setMove(){
		isMove = true;
	}
	
	public void display(){
		if(isMove ==true){
			if(isUp ==true){
				textarea.append("Up ... Now at "+currentFloor+". T:"+time+"\n");
			}
			else
				textarea.append("Down ... Now at "+currentFloor+". T:"+time+"\n");
		}
		else
			textarea.append("Stay ...Now At "+currentFloor+". T:"+time+"\n");
	}

	public void run(){
		this.time +=0.5;
		if(isLeave){
			textarea.append("CAN'T USE T:"+time+"\n");
		}
		else{
			for(int k=0;k<EleQueue.size();){
				if(currentFloor == EleQueue.get(k).getRequestFloor()){
					if(DoorFlag==0){
						textarea.append("Arrive "+currentFloor+",door open T:"+time+"\n");
						DoorFlag = 1;//表明要开门了。
						work++;
						break;
					}
					else if(DoorFlag==1){
						textarea.append("Arrive "+currentFloor+",door close T:"+time+"\n");
						DoorFlag=2;
						work++;
						break;
					}
					else
						break;
				}
				else
					k++;
			}
			//只有当什么的时候才能设置为idle?当targetFloor = currentFloor
			if(DoorFlag==2){
				if(currentFloor == TargetFloor)
					isMove = false;//Set the status as IDLE
				for(int j=0;j<EleQueue.size();){
					if(currentFloor == EleQueue.get(j).getRequestFloor()){
						EleQueue.remove(j);
					}
					else
						j++;
				}
				DoorFlag = 0;
			}
			else if(DoorFlag==0){
				Move();
				display();
			}
		}
	}
}
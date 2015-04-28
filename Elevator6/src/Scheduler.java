import java.util.Vector;

import javax.swing.JTextArea;


public class Scheduler {
	
	private Vector<Request> QuestQueue;
	private double Systime;
	private Elevator[] ElevatorThree;
	private boolean ElevatorUse;
	
	public Scheduler(RequestQueue a,Elevator[] elevators){
		this.QuestQueue = (Vector<Request>) a.getQueue().clone();
		Systime = 0;
		ElevatorThree = elevators;
		ElevatorUse = true;
	}

	public void Schedule() throws Exception{
		while(true){
			if(QuestQueue.size()!=0 && ElevatorUse==false)
				throw new Exception("You can't run the elevator again!");
			if(QuestQueue.size()==0 
					&&ElevatorThree[0].getStatus()==false
					&&ElevatorThree[1].getStatus()==false
					&&ElevatorThree[2].getStatus()==false){
				break;
			}
			for(int i=0;i<QuestQueue.size();){
				Request HandleR = QuestQueue.get(i);
				if(HandleR.getRequestTime()>Systime){
					break;
				}
				else{
					if(HandleR.getType().equals("leave")){
						ElevatorThree[HandleR.getIDIndex()].Leave();
						QuestQueue.remove(i);
						if(ElevatorThree[0].isLeave() && ElevatorThree[1].isLeave() && ElevatorThree[1].isLeave())
							ElevatorUse = false;
						else
							ElevatorUse = true;
						continue;
					}
					else if(HandleR.getType().equals("join")){
						ElevatorThree[HandleR.getIDIndex()].Join(Systime);
						QuestQueue.remove(i);
						if(ElevatorThree[0].isLeave() && ElevatorThree[1].isLeave() && ElevatorThree[1].isLeave())
							ElevatorUse = false;
						else
							ElevatorUse = true;
						continue;
					}
					else if(HandleR.getType().equals("E_R")){
					//If the elevator now is Leave.We can't use it.
						Elevator elevator = ElevatorThree[HandleR.getIDIndex()];
						if(elevator.isLeave())
							throw new Exception("You can't use the elevator "+HandleR.getID());
						else{
							if(elevator.getStatus()/*Running Status*/){
								if(elevator.ERQueueCheck(HandleR.getRequestFloor())){
									Request a = QuestQueue.remove(i);
									elevator.Add2Queue(a);
									continue;
								}
								else if(elevator.getCurrentFloor()==HandleR.getRequestFloor()){
									Request a = QuestQueue.remove(i);
									elevator.Add2Queue(a);
									continue;
								}
								else{
									i++;
									continue;
								}
							}
							else/*IDLE Status*/{
								Request a = QuestQueue.remove(i);
								elevator.SetInitialQueue(a.getRequestFloor(),a);
								continue;
							}
						}
					}
					else if(HandleR.getType().equals("F_R")){
						Vector<Elevator> eleV =new Vector<Elevator>();
						Vector<Elevator> eleVFree = new Vector<Elevator>();
						for(int j=0;j<3;j++){
							/*System.out.println(j+" Status:"+ElevatorThree[j].getStatus());
							System.out.println(j+" getDirection:"+HandleR.getDirection());
							System.out.println(j+" getFloor:"+HandleR.getRequestFloor());
							System.out.println(ElevatorThree[j].FRQueueCheck(HandleR.getRequestFloor(), HandleR.getDirection()));*/
							if(ElevatorThree[j].isLeave())
								continue;
							else{
								if(ElevatorThree[j].getStatus() && 
								(ElevatorThree[j].FRQueueCheck(HandleR.getRequestFloor(), HandleR.getDirection()))){
									eleV.add(ElevatorThree[j]);
								}
								else if(!ElevatorThree[j].getStatus()){
									eleVFree.add(ElevatorThree[j]);
								}
								else if(ElevatorThree[j].getCurrentFloor()==HandleR.getRequestFloor())
									eleV.add(ElevatorThree[j]);
							}
						}
						if(eleV.size()!=0){
							for(int k=1;k<eleV.size();){
								if(eleV.get(k).getWork()<eleV.get(0).getWork()){
									Elevator temp = eleV.remove(k);
									eleV.set(0, temp);
								}
								else
									k++;
							}
							eleV.get(0).Add2Queue(HandleR);
							QuestQueue.remove(i);
							continue;
						}
						else if(eleVFree.size()!=0){
							for(int k=1;k<eleVFree.size();){
								if(eleVFree.get(k).getWork()<eleVFree.get(0).getWork()){
									Elevator temp = eleVFree.remove(k);
									eleVFree.set(0, temp);
								}
								else
									k++;
							}
							eleVFree.get(0).SetInitialQueue(HandleR.getRequestFloor(),HandleR);
							QuestQueue.remove(i);
							continue;
						}
						else{
							i++;
							continue;
						}
					}
					else
						throw new Exception("The instr is not valid!");
				}
			}
			for(int i=0;i<3;i++){
				ElevatorThree[i].run();
			}
			Systime+=0.5;
		}
	}
}

/*5、	流程应该是：（两个循环，外面的是等到为空为止）
{{{{{{{{{
    1、取出请求队列的第i个请求;(i从0开始)
2、查看该请求的发出时间是否小于等于系统当前时间。
3、如果该请求的发出时间小于等于系统当前时间：
		1、判断该请求是否为特殊请求，如果是leave，join等请求
		   1、如果是Leave的话，则isLeave必须为false；如果是join的话，则isLeave必		   须为true。否则报错；
		   2、如果判断成功的话，则首先要将isLeave置为false或者true。然后删除该条		   请求。
		2、如果该请求是条内部电梯请求：
			1、取出对应的电梯（由ID号），然后查看该电梯当前是否可以捎带该内部电梯请求，判断原则如下：
		If当前电梯在RUNNING状态：
				当前电梯状态为UP且内部电梯请求楼层大于等于当前楼层，小于等于10层，加入到电梯调度器，删除该条请求。continue；
				当前电梯状态为DOWN且内部电梯请求楼层小于等于当前楼层，大于等于1层，加入到电梯调度器，删除该条请求，continue。
				否则i++。
		Else if当前电梯在IDLE状态：
				把该请求直接加入电梯的调度器中，删除该条请求，continue。
		3、否则该条请求一定是条外部楼层请求：
			1、遍历一下运动的电梯。
			扫描一下看该请求可不可以被任一电梯所捎带。在这个扫描的过程中我觉应该是以如下原则进行判断是否能被捎带：
			该请求的方向是否与当前电梯的运动方向（MoveDirection）一致且该请求楼层处于电梯的当前楼层与目标楼层之间。把能捎带本条指令的电梯加入到待处理	向量中去。
			2、如果待处理向量的size不为0的话，从头到尾遍历，找到运动量小的那个，新建一个，在待处理向量中删除，并插入头结点中去。（当然我们说如果运动量一样的话不执行这一步）。把该条指令加入到待处理向量的第一个电梯的调度器里面去，然后删除该条指令。Continue;
			3、遍历一下空闲的电梯，如果有空电梯的话，新建一个		Vector<>，把空电梯加进去。然后再找运动量小的那个，把该条指令加入到待处理向量的第一个电梯的调度器里去。然后删除该条指令。

4、如果该请求的发出时间不小于等于系统当前时间，
			Break;
	}}}}}}}}}
6、	还在运行的电梯们(isLeave=false)均执行一个TimePassBy的方法，系统当前时间+0.5；	*/
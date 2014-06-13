import java.util.Scanner;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Collections;
import java.lang.Math;

class Connection implements Comparable<Connection>{
    int startTime;
    int endTime;
    String from;
    String to;
    
    public Connection(int s, int e, String f, String t){
	startTime = s;
	endTime = e;
	from = f;
	to = t;
    }
    
    public int compareTo(Connection other){
	if(startTime != other.startTime)
	    return startTime - other.startTime;
	else{
	    return endTime - other.endTime;
	}
    }

    public String toString(){
	return startTime + " " + from + " " + endTime + " " + to;
    }
}


public class Main{   

    private static final int MAX_TIME = 2400;

    public static void main(String[] args){
	Scanner scan = new Scanner(System.in);
	int n = scan.nextInt();
	for(int scen = 1; scen <= n; scen++){
	    Hashtable<String,Integer> cityNum = new Hashtable<String,Integer>();
	    ArrayList<Connection> connections = new ArrayList<Connection>();

	    int numCities = scan.nextInt();
	    scan.nextLine();
	    for(int c = 0; c < numCities; c++){
		String city = scan.nextLine();
		cityNum.put(city,c);		
	    }

	    int max_time = 0;

	    int numTrains = scan.nextInt();
	    while(numTrains > 0){
		int numStops = scan.nextInt();
		int timea, timeb;
		String placea, placeb;
		if(numStops > 0){
		    timea = scan.nextInt();
		    placea = scan.nextLine().trim();
		    numStops--;
		    while(numStops > 0){
			timeb = scan.nextInt();
			placeb = scan.nextLine().trim();
			if(timeb > max_time)
			    max_time = timeb;
			connections.add(new Connection(timea,timeb,placea,placeb));
			timea = timeb;
			placea = placeb;
			numStops--;
		    }
		}
		numTrains--;
	    }

	    int startTime = scan.nextInt();
	    scan.nextLine();
	    int start = 0;
	    int dest = 0;

	    String startS = scan.nextLine();
	    try{
		start = cityNum.get(startS);
	    }
	    catch(Exception e){}

	    String destS = scan.nextLine();
	    try{
		dest = cityNum.get(destS);
	    }
	    catch(Exception e){}

	    // A[x][t] = the max departure time possible to get from
	    //           "start" to "x" at time "t"
	    int[][] A = new int[numCities][max_time];
	    for(int c = 0; c < numCities; c++){
		for(int t = 0; t < max_time; t++){
		    A[c][t] = -1; // -1 means it can't happen
		}
	    }
	    /*    for(int t = startTime-1; t < MAX_TIME; t++){
		A[start][t] = startTime;
		}*/
	    
	    for(Connection c : connections){
		int startInd = cityNum.get(c.from);
		int endInd = cityNum.get(c.to);
		if((startInd == start) && (c.startTime >= startTime)){
		    for(int t = c.endTime-1; t < max_time; t++){
			A[endInd][t] = Math.max(A[endInd][t],
							c.startTime);
		    }
		}
		else if(A[startInd][c.startTime] >= 0){	    
		    for(int t = c.endTime-1; t < max_time; t++){
			A[endInd][t] = Math.max(A[endInd][t],
						A[startInd][c.startTime-1]);
		    }		    
		}
	    }
	    
	    System.out.println("Scenario " + scen);


	    Boolean success = false;
	    for(int t = 0; t < max_time; t++){
		if(A[dest][t] >= 0){
		    success = true;
		    System.out.printf("Departure %04d %s\n", A[dest][t],startS);
		    System.out.printf("Arrival   %04d %s\n", t+1, destS);
		    break;
		}
	    }
	    if(!success){
		System.out.println("No connection");
	    }
	    
	    System.out.println();

	}
    }

}
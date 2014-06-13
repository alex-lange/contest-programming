import java.util.Scanner;
import java.util.LinkedList;
import java.util.ArrayList;

public class Main{ 
    public static void main(String[] args){
	Scanner scan = new Scanner(System.in);

	while(true){

	    int n = scan.nextInt();
	    if(n==0) break;

	    Boolean colorable = true;
	    
	    int numEdges = scan.nextInt();
	    ArrayList<LinkedList<Integer>> adjList = new ArrayList<LinkedList<Integer>>(n);
	    for(int i = 0; i < n; i++)
		adjList.add(new LinkedList<Integer>());

	    for(int i = 0; i < numEdges; i++){
		int u = scan.nextInt();
		int v = scan.nextInt();
		adjList.get(u).add(v);
		adjList.get(v).add(u);
	    }

	    Boolean[] seen = new Boolean[n];
	    Boolean[] color = new Boolean[n];
	    for(int i = 0; i < n; i++)
		seen[i] = false;
	    Boolean curColor = true;

	    LinkedList<Integer> q = new LinkedList<Integer>();
	    q.add(0);
	    color[0] = true;

	    // color nodes with BFS until we're done or it doesn't work
	    while((q.size() != 0) && colorable){
		int cur = q.poll();
		curColor = color[cur];
		for(int u : adjList.get(cur)){
		    if(seen[u] && (curColor == color[u])){
			colorable = false;
			break;
		    }
		    if(!seen[u]){
			seen[u] = true;
			color[u] = !curColor;
			q.add(u);
		    }
		}
	    }

	    if(!colorable)
		System.out.print("NOT ");
	    System.out.println("BICOLORABLE.");
	    
	    
	}

    }


}
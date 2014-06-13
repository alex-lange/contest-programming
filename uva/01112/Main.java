import java.util.Scanner;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.util.Comparator;
import java.lang.Math;
import java.math.BigInteger;
import java.util.PriorityQueue;
import java.lang.Comparable;

class Graph{

    private int numNodes;
    private int numEdges;
    
    
    HashMap<Node,LinkedList<WeightedEdge>> adjList;

    public Graph(){
	adjList = new HashMap<Node,LinkedList<WeightedEdge>>();
	numEdges = 0;
    }

    public void addNode(Node u){
	if(!adjList.containsKey(u)){
	    adjList.put(u, new LinkedList<WeightedEdge>());
	    numNodes++;
	}
    }

    public void addEdge(Node u, Node v, int distance){
	if(!adjList.containsKey(u)){
	    adjList.put(u, new LinkedList<WeightedEdge>());
	    numNodes++;
	}
	if(!adjList.containsKey(v)){
	    adjList.put(v, new LinkedList<WeightedEdge>());
	    numNodes++;
	}

	adjList.get(u).add(new WeightedEdge(v,distance));
	numEdges++;
    }

    public int getOrder(){
	return numNodes;
    }

   

}


class Node{
    int value;
    public Node(int value){
	this.value = value;
    }
    public Boolean equals(Node u){
	return this.value == u.value;
    }
}

class WeightedEdge implements Comparator<WeightedEdge>,Comparable<WeightedEdge>{
    public Node neighbor;
    public int weight;
    public WeightedEdge(Node neighbor, int weight){
	this.neighbor = neighbor;
	this.weight = weight;
    }

    public int compare(WeightedEdge a, WeightedEdge b){
	return a.weight - b.weight;
    }

    public int compareTo(WeightedEdge a){
	return weight - a.weight;
    }
    
}



public class Main{

    public static HashMap<Node,Integer> Dijkstra(Graph G, Node root){
	HashMap<Node,Integer> distance = new HashMap<Node,Integer>();
	LinkedList<Node> nodesLeft = new LinkedList<Node>();

	for(Node v : G.adjList.keySet()){
	    if(v.equals(root)){
		distance.put(v,0);
	    }
	    else{
		distance.put(v,Integer.MAX_VALUE);
	    }

	    nodesLeft.add(v);
	}

	while(nodesLeft.size()>0){

	    Node closestNode = nodesLeft.getFirst();

	    for(Node w : nodesLeft){
		if(distance.get(w) < distance.get(closestNode)){
		    closestNode = w;
		}
	    }
	    nodesLeft.removeFirstOccurrence(closestNode);

	    for(WeightedEdge x : G.adjList.get(closestNode)){
		int potentialDistance = distance.get(closestNode) + x.weight;
		if(potentialDistance < distance.get(x.neighbor)){
		    distance.put(x.neighbor,potentialDistance);
		}
	    }
	}
	return distance;
    }


    public static HashMap<Node,Integer> FloydWarshall(Graph G, Node root){
	HashMap<Node,Integer> distance = new HashMap<Node,Integer>();
	int n = G.getOrder();
	int [][] dist = new int[n][n];
	for(int i = 0; i < n; i++){
	    for( int j = 0; j < n; j++){
		dist[i][j] = Integer.MAX_VALUE/3;
	    }
	}
	
	for(Node v : G.adjList.keySet()){
	    for(WeightedEdge x : G.adjList.get(v)){
		dist[v.value-1][x.neighbor.value-1] = x.weight;
	    }
	}

	for(int k = 0; k < n; k++ ){
	    for(int i = 0; i < n; i++){
		for(int j = 0; j < n; j++){
		    if (dist[i][j] > dist[i][k] + dist[k][j])
			dist[i][j] = dist[i][k] + dist[k][j];
		}
	    }
	}

	for(Node v : G.adjList.keySet()){
	    if(v.equals(root)){
		distance.put(v,0);
	    }
	    else{
		distance.put(v,dist[root.value-1][v.value-1]);

	    }
	}

	return distance;

    }
    


    public static void main( String [] args ){

	Scanner scan = new Scanner(System.in);

	int numTests = scan.nextInt();
	
	while(numTests > 0){
	    int numNodes = scan.nextInt();
	    Node root = new Node(scan.nextInt());
	    int time = scan.nextInt();
	    int numEdges = scan.nextInt();

	    Graph cur = new Graph();

	    ArrayList<Node> nodes = new ArrayList<Node>(numNodes);

	    for(int i = 0; i < numNodes; i++){
		nodes.add(new Node(i+1));
		cur.addNode(nodes.get(i));
	    }
	    
	    for(int i = 0; i < numEdges; i++){
		int u = scan.nextInt();
		int v = scan.nextInt();
		int weight = scan.nextInt();
		cur.addEdge(nodes.get(v-1), nodes.get(u-1), weight);
	    }

	    //HashMap<Node,Integer> distance = Dijkstra(cur,root);
	    HashMap<Node,Integer> distance = FloydWarshall(cur,root);


	    int numGood = 0;
	    for(Node v : nodes){
		if(distance.get(v) <= time)
		    numGood++;
	    }

	    System.out.println(numGood);
	    numTests--;
	    if(numTests > 0)
		System.out.println();
	}

    }
}
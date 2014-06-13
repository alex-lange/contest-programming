import java.util.Scanner;

public class Main{
    public static void main(String [] args){
	Scanner scan = new Scanner(System.in);

	Boolean go = true;

	while(go){

	    int stickLength = scan.nextInt();
	    if(stickLength == 0){
		go = false;
		break;
	    }
	    int numCuts = scan.nextInt();
	    int[] cutSpots = new int[numCuts+2];
	    cutSpots[0] = 0;
	    for(int i = 1; i <= numCuts; i++)
		cutSpots[i] = scan.nextInt();
	    cutSpots[numCuts+1] = stickLength;
	    
	    int[][] A = new int[numCuts+2][numCuts+2];

	    for(int i = 0; i < numCuts+1; i++){
		A[i][i] = 0;
		A[i][i+1] = 0;
	    }
	    A[numCuts+1][numCuts+1]=0;

	    int inc = 2;
	    int minChoice;

	    while(inc <= numCuts+1){
		int j = 0;
		int k = inc+j;
		while( k <= numCuts+1){
		    A[j][k] = cutSpots[k] - cutSpots[j];
		    
		    minChoice = Integer.MAX_VALUE;
		    for(int i = j+1; i < k; i++){
			int minPoss = A[j][i] + A[i][k];
			if(minPoss < minChoice)
			    minChoice = minPoss;
		    }
		    
		    A[j][k] += minChoice;
		    
		    j++;
		    k = j + inc;
		}
		inc++;
	    }

	    System.out.println("The minimum cutting is " 
			       + A[0][numCuts+1] + ".");

	}

    }
}
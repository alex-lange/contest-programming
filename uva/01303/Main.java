import java.util.Scanner;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.ArrayList;
import java.util.Stack;
import java.lang.Math;
import java.math.BigInteger;

class Pt{
    public int x;
    public int y;

    Pt( int xC, int yC ){
	this.x = xC;
	this.y = yC;
    }

    Boolean equals( Pt other ){
	return (this.x == other.x && this.y == other.y );
    }
  

}

class Main{
    
    public static int cpZ( Pt p_1, Pt p_2, Pt p_3 ){
	return (p_2.x - p_1.x)*(p_3.y - p_1.y) 
	    - (p_2.y - p_1.y) * (p_3.x - p_1.x);
    }

    public static final double THRESHOLD = 0.5;// 1.0/3.0;


    public static void main( String [] args ){

	Scanner scan = new Scanner(System.in);

	int numTests = scan.nextInt();
	scan.nextLine();

	for( int i = 0; i < numTests; i++ ){

	    int n = scan.nextInt();
	    int l = scan.nextInt();
	    
	    int minP = 0;
	    int minY = 10000;
	    int minX = 10000;
	    int maxP = 0;
	    int maxY = -10000;
	    int maxX = -10000;
	    
	    int[][] points = new int[n][2];
	    for( int j = 0; j < n; j++ ){
		int curX =  scan.nextInt();
		int curY =  scan.nextInt();

		points[j][0] = curX;
		points[j][1] = curY;
		
		if( (curX < minX) || (curX == minX && curY < minY) ){
		    minP = j;
		    minY = curY;
		    minX = curX;
		}

	    }

	    Pt leftMost = new Pt( points[minP][0], points[minP][1] );
	    

	    ArrayList<Pt> allPoints = new ArrayList<Pt>();
	    ArrayList<Pt> convexHull = new ArrayList<Pt>();

	    for( int j = 0; j < n; j++){
		int k = (j + minP) % n;
		allPoints.add( new Pt( points[k][0], points[k][1] ));
	    }

	    //	    allPoints.add( leftMost);

	    Pt pointOnHull = leftMost;

	    Pt endPoint = allPoints.get(1); // just to make sure the while loop starts

	    int endPointIndex = 1;
	     
	    while( !endPoint.equals(leftMost) ){
		convexHull.add( pointOnHull );
		endPoint = allPoints.get(0);
		for( int j = 0; j < n; j++ ){
		    if( (endPoint.equals( pointOnHull )) 
			|| ( cpZ(pointOnHull, endPoint, allPoints.get(j)) > 0 )
			|| ( cpZ(pointOnHull, endPoint, allPoints.get(j)) == 0 
			     && ( (j > endPointIndex && endPointIndex != 0) || j == 0))){
			endPoint = allPoints.get(j);
			endPointIndex = j;
		    }
		}

		pointOnHull = endPoint;
	    }

	    int k = convexHull.size();


	    double totalLength = 0;


	    for( int j = 0; j < k; j++ ){
		Pt curP = convexHull.get(j);
		Pt lP;
		if( j == (k - 1) ){
		    lP = convexHull.get(0);
		}
		else{
		    lP = convexHull.get(j+1);
		}
			
		Pt rP;
		if( j == 0 )
		    rP = convexHull.get(k-1);
		else
		    rP = convexHull.get(j-1);
		
		
		double a_1 = curP.x - lP.x;
		double a_2 = curP.y - lP.y;
		double b_1 = curP.x - rP.x;
		double b_2 = curP.y - rP.y;

		double angle = Math.acos( (a_1*b_1 + a_2*b_2 ) / 
					  (Math.sqrt( (a_1*a_1+a_2*a_2) )* Math.sqrt(b_1*b_1+b_2*b_2) ) );
		
		
		angle = Math.PI - angle;

		double arcLength = angle * l;

		double sideLength = Math.sqrt( a_1*a_1 + a_2*a_2 );

		totalLength = totalLength + arcLength + sideLength;
		
	    }

	    int totalLengthRound = (int)totalLength;
	    double leftOver = totalLength - totalLengthRound;

	    if( leftOver >= THRESHOLD )
		totalLengthRound++;


	    System.out.println(totalLengthRound);
	    if( i != numTests - 1) System.out.println();
	
	}
    }
}
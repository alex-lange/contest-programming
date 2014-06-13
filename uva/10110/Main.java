import java.util.Scanner;
import java.lang.Math;

/* Light, More Light
   
   - Light switch will get toggled whenever i divides n
   - If a divides n and a < sqrt(n), then ab = n and b > sqrt(n)
   - So, the light switch will get toggled an even number of times
     (and remain off) unless it is a perfect square
 */

public class Main{
 
    public static void main(String[] args){
	Scanner scan = new Scanner(System.in);
	while(true){
	    long n = scan.nextLong();
	    if(n == 0) break;
	    
	    double s = Math.sqrt((double)n);
	    if(Math.round(s) == s)
		System.out.println("yes");
	    else
		System.out.println("no");	
	}	
    }
}
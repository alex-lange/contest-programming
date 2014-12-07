import java.util.Scanner;

/* 11000 - Bee
Pretty straightforward, based on the following observation:
  # males at year X = # of total at X-1
  # of total at year X = 1 + (# males at X-1) + (# males at X)
    (where the 1 is the queen and the middle term is the females)
Uses memoization.
*/

public class Main{
    // since total will not exceed 2^32 this is more than enough
    private static int MAX_YEAR = 50;
    private static long[] total;
    private static long[] males;

    private static long getTotal(int year){
	if(year == 0)
	    return 1;
	if(total[year] == 0)
	    total[year] = 1 + getMales(year - 1) + getMales(year);
	return total[year];
    }

    private static long getMales(int year){
	if(year == 0)
	    return 0;
	if(males[year] == 0)
	    males[year] = getTotal(year-1);
	return males[year];	
    }

    public static void main(String[] args){
	Scanner scan = new Scanner(System.in);
	total = new long[MAX_YEAR];
	males = new long[MAX_YEAR];
	int year;
	while((year = scan.nextInt()) != -1){
	    System.out.println(getMales(year) + " " + getTotal(year));
	}
    }
}

import java.util.Scanner;
import java.util.LinkedList;
import java.util.Stack;

class QuickSort{
    int[] ind;
    double[] val;
    
    public QuickSort(){}

    public void sort(int[] ind, double[] val){
	this.val = val;
	this.ind = ind;
	quickSort(0,ind.length-1);
    }

    private void quickSort(int l, int r){
	if(l < r){
	    int p = partition(l,r);
	    quickSort(l,p-1);
	    quickSort(p+1,r);
	}
    }

    private int partition(int l, int r){
	int temp;
	int pivot = (l+r)/2;
	int pivInd = ind[pivot];
 	double pivVal = val[ind[pivot]];
	temp = ind[r];
	ind[r] = ind[pivot];
	ind[pivot] = temp;
	int storeInd = l;
	for(int i = l; i < r; i++){
	    if((val[ind[i]] < pivVal)  
	       || ((val[ind[i]] == pivVal) && (ind[i] < pivInd))){
		temp = ind[storeInd];
		ind[storeInd] = ind[i];
		ind[i] = temp;
		storeInd++;
	    }
	}

	temp = ind[storeInd];
	ind[storeInd] = ind[r];
	ind[r] = temp;
       
	return storeInd;
    }


}

public class Main{
    public static void main(String[] args){
	Scanner scan = new Scanner(System.in);

	int numTests = scan.nextInt();

	while(numTests > 0){
	    // n is number of jobs
	    int n = scan.nextInt();
	    int[] ind = new int[n];
	    double[] time = new double[n];
	    double[] fine = new double[n];
	    double[] val = new double[n];
	    int totalTime = 0;
	    LinkedList<Integer> jobsLeft = new LinkedList<Integer>();
	    Stack<Integer> schedule = new Stack<Integer>();

	    for(int i = 0; i < n; i++){
		ind[i] = i;
		time[i] = scan.nextInt();
		fine[i] = scan.nextInt();
		val[i] = time[i]/fine[i];
	    }

	    QuickSort sorter = new QuickSort();
	    sorter.sort(ind,val);

	    for(int i = 0; i < n; i++){
		System.out.print(ind[i]+1);
		if(i < n-1)
		    System.out.print(" ");
	    }
	    System.out.println();

	 
	    numTests--;

	    if(numTests > 0)
		System.out.println();
	    
	}
    }
}
import java.util.Scanner;
import java.util.ArrayList;
import java.util.LinkedList;

// 10040 Ouroboros Snake
class OuroMaker{

    private int[] ouro;
    private byte[] seen;
    private int ander;
    private int p;
    private int n;

    public OuroMaker(int n){
	this.n = n;
	p = (1 << n);
	ander = p-1;
	ouro = new int[p];
	seen = new byte[p];
	for(int i = 0; i < p; i++){
	    ouro[i] = -1;
	    seen[i] = 0;
	}
    }

    // returns o(n,k)
    public int getonk(int k){
	int o = 0;
	for(int i = k; i < k+n; i++){
	    o = (o << 1) | ouro[i%p];
	}
	return o;
    }


    /* Function to generate the smallested 

     */
    public void genOuro(){
	int pos = 0;
	while(pos < n){
	    ouro[pos] = 0;
	    pos++;
	}
	seen[0] = 1;

	int x = 0;
	
	while(pos < p+n-1){
	    boolean backtrack = false;
	    if(pos < p){

		// always try 0 first (makes the smallest)
		if(seen[ander & (x << 1)] == 0){
		    x = ander & (x << 1);
		    seen[x] = 1;
		    ouro[pos] = 0;
		    pos++;
		}
		// next try 1
		else if(seen[(ander & (x << 1)) | 1] == 0){
		    x = ander & ((x << 1) | 1);
		    seen[x] = 1;
		    ouro[pos] = 1;
		    pos++;
		}
		// neither work, so backtrack
		else{
		    backtrack = true;
		}
	    }
	    else{
		// now we test that the ints when looping around 
		// haven't been seen before
		if(seen[ander & ((x << 1) | ouro[pos % p])] == 0){
		    x = ander & ((x << 1) | ouro[pos % p]);
		    pos++;
		}
		else{
		    
		    backtrack = true;
		    pos = p;
		}
	    }
	    // we backtrack until we find a 0 we can replace with a 1
	    if(backtrack){
		pos--;
		seen[getonk(pos-n+1)] = 0;
		while(ouro[pos] == 1){
		    pos--;
		    seen[getonk(pos-n+1)] = 0;
		}
		x = getonk(pos-n+1);
		x = x | 1;
		seen[x] = 1;
		ouro[pos] = 1;
		pos++;
	    }
	}
    }

    // functions to do the same thing as genOuru, but with recursion
    // (admits a Stack Overflow sometime around n=11)
    public void genBT(){
	int pos = 0;
	while(pos < n){
	    ouro[pos] = 0;
	    pos++;
	}
	seen[0] = 1;
	
	DFS(1,1,pos);
    }

    private void DFS(int x, int bit, int pos){
	if(pos == p + n){
	    return;
	}
	if(seen[x] == 0){
	    if( pos < p){
		seen[x] = 1;
		ouro[pos] = bit;
		
		// always try to add a zero first
		DFS( ander & (x << 1), 0, pos + 1);
		DFS( ander & ((x << 1) | 1), 1, pos + 1);
	    }
	    else{
		DFS( ander & ((x << 1) | ouro[pos % p]),0,pos + 1);
	    }
	}
	return;
    }

    // makes the bitstring a string
    public String toString(){
	String o = "\0";
	for(int i = 0; i < p; i++){
	    o = o + ouro[i];
	}
	return o;
    }
}

public class Main{

    public static void main(String[] args){
	Scanner scan = new Scanner(System.in);
	int numTests = scan.nextInt();

	while(numTests > 0){
	    
	    int n = scan.nextInt();
	    int k = scan.nextInt();
	   
	    OuroMaker o = new OuroMaker(n);
	    o.genOuro();
	    System.out.println(o.getonk(k));

	    /* Commented out tests that do it the backtracking way
	       and compares the two answers

	    OuroMaker o2 = new OuroMaker(n);
	    o.genOuro();
	    o2.genBT();
	    System.out.println(o.getonk(k) + " " + o2.getonk(k));
	    System.out.println(o.toString() + "\n" + o2.toString());*/

	    numTests--;
	}
    }
}
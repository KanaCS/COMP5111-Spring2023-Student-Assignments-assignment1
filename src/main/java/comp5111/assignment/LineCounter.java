package comp5111.assignment;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Collections;

public class LineCounter {
    //private static int numStmts = 0;
    static List<Integer> iteratedlines = new ArrayList<>();
    static List<String> invokedStmts = new ArrayList<>();
    
//    public static void addNumStmts() {
//    	numStmts += 1;
//    }
//
//    public static int getNumStmts() {
//        return numStmts;
//    }
    
    public static void addlineidx(Integer lidx) {
    	iteratedlines.add(lidx);
    }
    
    public static void addStmtInvoked(int i) {
    	//get the index of stmts that is invoked, transform the result in align with the stmts list
    	//add "no" if the stmts is not invoked, add "yes" if it is invoked
    	while(invokedStmts.size()<=i) {
        	invokedStmts.add("no");
    	}
    	invokedStmts.set(i, "yes");

    }
    
    public static void report() {
    	// align invokedStmts with iteratedStmts cuz invokedStmts.size() may < iteratedStmts.size() when the last stmts is not invoked
    	while(invokedStmts.size()<iteratedlines.size()) {
    		invokedStmts.add("no");
    	}
    	
    	// print the result
    	ListIterator<Integer> it = iteratedlines.listIterator();
    	while(it.hasNext()) {
    		int idx = it.nextIndex();
    		Integer stmt = it.next();
    		System.out.println(stmt + ", " + invokedStmts.get(idx));
    	}
    	int numInvokedStmts = Collections.frequency(invokedStmts, "yes");
    	System.out.println("\nNumber of Lines: " + iteratedlines.size());
    	System.out.println("Number of Invoked Lines: " + numInvokedStmts);
    	System.out.println("Percentage: " + 100.0*numInvokedStmts/iteratedlines.size() + "%");
    }
}
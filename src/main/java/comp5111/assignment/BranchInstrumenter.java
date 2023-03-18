package comp5111.assignment;

import soot.*;
import soot.jimple.*;
import soot.util.Chain;

import java.util.Iterator;
import java.util.Map;
import soot.jimple.internal.JIdentityStmt;
import soot.jimple.internal.JIfStmt;

public class BranchInstrumenter extends BodyTransformer {

    /* some internal fields */
    static SootClass counterClass;
    static SootMethod addStmtInvoked;

    static {
        counterClass = Scene.v().loadClassAndSupport("comp5111.assignment.BranchCounter");
        addStmtInvoked = counterClass.getMethod("void addStmtInvoked(int)");
    }

    /*
     * internalTransform goes through a method body and inserts counter
     * instructions before method returns
     */
    @Override
    protected void internalTransform(Body body, String phase, Map options) {
        // body's method
        SootMethod method = body.getMethod();

        // we dont instrument constructor (<init>) and static initializer (<clinit>)
        // Note that you should instrument the constructor and static initializer in your Assignments.
        if (method.isConstructor() || method.isStaticInitializer()) {
            return;
        }

        // debugging
        //System.out.println("instrumenting method: " + method.getSignature());

        // get body's unit as a chain
        Chain<Unit> units = body.getUnits();

        // get a snapshot iterator of the unit since we are going to
        // mutate the chain when iterating over it.
        //
        Iterator<?> stmtIt = units.snapshotIterator();

        // typical while loop for iterating over each statement
        while (stmtIt.hasNext()) {

            // cast back to a statement.
            Stmt stmt = (Stmt) stmtIt.next();
            
			if (stmt instanceof JIdentityStmt || !(stmt instanceof JIfStmt)) {
				continue;
			}
			
            //store the statement
            BranchCounter.addStmt(stmt);
            
            //store the target of branch
            Stmt tstmt = ((JIfStmt) stmt).getTarget();
            BranchCounter.addStmt(tstmt);
            
            //to count the total number of branch for true and false branch
            BranchCounter.addNumStmts();
            BranchCounter.addNumStmts();
            
            // 1. first, make a new invoke expression
            InvokeExpr incExpr = Jimple.v().newStaticInvokeExpr(addStmtInvoked.makeRef(), IntConstant.v(BranchCounter.getNumStmts()-2));
            // 2. then, make a invoke statement
			Stmt incStmt = Jimple.v().newInvokeStmt(incExpr);
			// then, insert stmt into chain, before the statement
			units.insertAfter(incStmt, stmt);
			
            // 1. first, make a new invoke expression
            InvokeExpr incExpr2 = Jimple.v().newStaticInvokeExpr(addStmtInvoked.makeRef(), IntConstant.v(BranchCounter.getNumStmts()-1));
            // 2. then, make a invoke statement
			Stmt incStmt2 = Jimple.v().newInvokeStmt(incExpr2);
			// then, insert stmt into chain, before the statement
			units.insertAfter(incStmt2, tstmt);
		}
	}

}
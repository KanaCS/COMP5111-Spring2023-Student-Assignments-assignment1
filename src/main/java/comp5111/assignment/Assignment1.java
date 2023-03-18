package comp5111.assignment;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.runner.JUnitCore;
import soot.Pack;
import soot.PackManager;
import soot.Scene;
import soot.Transform;
import soot.options.Options;

public class Assignment1 {
    public static void main(String[] args) throws ClassNotFoundException, IOException {

        /* check the arguments */
        if (args.length <= 1 || (args[0].compareTo("0") != 0 && args[0].compareTo("1") != 0 && args[0].compareTo("2") != 0)) {
            System.err.println("Usage: java comp5111.assignment.Assignment1 [coverage level] test-suite [soot options] " +
                "classname");
            System.err.println("Usage: [coverage level] = 0 for statement coverage");
            System.err.println("Usage: [coverage level] = 1 for branch coverage");
            System.err.println("Usage: [coverage level] = 2 for line coverage");
            System.exit(0);
        }

        // these args will be passed into soot.
        String[] classNames = Arrays.copyOfRange(args, 1, args.length);

        if (args[0].compareTo("0") == 0) {
            // TODO invoke your statement coverage instrument function
        	instrumentWithSoot(classNames, "stmt");
            runJunitTests();
            // TODO run tests on instrumented classes to generate coverage report
            StmtCounter.report();
        } else if (args[0].compareTo("1") == 0) {
            // TODO invoke your branch coverage instrument function
        	instrumentWithSoot(classNames, "branch");
            runJunitTests();
            // TODO run tests on instrumented classes to generate coverage report
        	BranchCounter.report();
        } else if (args[0].compareTo("2") == 0) {
            // TODO invoke your line coverage instrument function
        	instrumentWithSoot(classNames, "line");
            runJunitTests();
            // TODO run tests on instrumented classes to generate coverage report
            LineCounter.report();
        }
    }
    
    private static void instrumentWithSoot(String[] classNames, String typeOfInstrumenter) {
        // the path to the compiled Subject class file
        String classUnderTestPath = "./raw-classes";
        String targetPath = "./target/classes";
        
        String classPathSeparator = ":";
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            classPathSeparator = ";";
        }

        /*Set the soot-classpath to include the helper class and class to analyze*/
        Options.v().set_soot_classpath(Scene.v().defaultClassPath() + classPathSeparator + targetPath + classPathSeparator + classUnderTestPath);

        // we set the soot output dir to target/classes so that the instrumented class can override the class file
        Options.v().set_output_dir(targetPath);

        // retain line numbers
        Options.v().set_keep_line_number(true);
        // retain the original variable names
        Options.v().setPhaseOption("jb", "use-original-names:true");
        Options.v().set_allow_phantom_refs(true);
        
        if (classNames[0].equals("Subject"))
        	Options.v().set_process_dir(List.of(new String[]{"./raw-classes"}));
        
        /* add a phase to transformer pack by call Pack.add */
        Pack jtp = PackManager.v().getPack("jtp");
        
        if (typeOfInstrumenter=="stmt") jtp.add(new Transform("jtp.instrumenter", new StmtInstrumenter()));
        else if (typeOfInstrumenter=="branch") jtp.add(new Transform("jtp.instrumenter", new BranchInstrumenter()));
        else if (typeOfInstrumenter=="line") jtp.add(new Transform("jtp.instrumenter", new LineInstrumenter()));
        for (int i = 0; i < classNames.length; ++i) {
        	classNames[i] = "comp5111.assignment.cut." + classNames[i];
        }
        // pass arguments to soot
        soot.Main.main(classNames);  // added phases will be executed in this method
    }

    private static void runJunitTests() {
        Class<?> testClass = null;
        try {
            // here we programmitically run junit tests
            testClass = Class.forName("comp5111.assignment.cut.RegressionTest");
            JUnitCore junit = new JUnitCore();
            //System.out.println("Running junit test: " + testClass.getName());
            junit.run(testClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
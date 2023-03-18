# COMP5111 (Spring 2023) Assignment 1

## Deadline: 11:55pm 18 March 2023

## student: 20783919

`PROJECT_ROOT=COMP5111-Spring2023-Student-Assignments-assignment1`

### Task 1.1: To learn how to use Randoop for unit test generation.

run the gen-tests.sh script in ${PROJECT_ROOT}/scripts/

```
cd ${PROJECT_ROOT}/scripts/
. gen-tests.sh
```

### Task 1.2: To learn how to use EclEmma for code coverage measurement.
The screenshots are located in `${PROJECT_ROOT}/screenshots/` while the generated test suite are located in  `${PROJECT_ROOT}/src/test/randoop${i}` for i in {0..4}.

### Task 2: Statement coverage measurement using Soot (40%)

The relevant .java file are located in ${PROJECT_ROOT}/src/main/java/comp5111/assignment/, including `Assignment1.java`,  `StmtCounter.java` and `StmtInstrumenter.java`.

To run the analysis, you need to run the instrument-run-test.sh script in ${PROJECT_ROOT}/scripts/.

```
cd ${PROJECT_ROOT}/scripts/

# remark: running the below script also return the result for task 3 and the bonus task, report is generated in scripts/randoop${i}/${typeOfCoverage}Report for different classes to be analyzed
# before running the script, you need to first choose "Build Path" > "Use As Source Folder" for the test suite folder in Eclipse, base on the folder randoop${i} you pick, include the ${i} as a argument for calling instrument-run-test.sh.
# i.e. ". instrument-run-test.sh 0" is to analyze on randoop0 test suite
. instrument-run-test.sh ${i}

# this is to format extract the percentage coverage from the report of different classes and store the overview of coverage result in scripts/randoop${i}/${typeOfCoverage}Report/overview.txt
. get_overview.sh
```

### Task 3: Branch coverage measurement using Soot (30%)

The relevant .java file are located in ${PROJECT_ROOT}/src/main/java/comp5111/assignment/, including `Assignment1.java`,  `BranchCounter.java` and `BranchInstrumenter.java`.

To run the analysis, you need to run the instrument-run-test.sh script in ${PROJECT_ROOT}/scripts/.

```
cd ${PROJECT_ROOT}/scripts/

# remark: running the below script also return the result for task 2 and the bonus task, report is generated in scripts/randoop${i}/${typeOfCoverage}Report for different classes to be analyzed
# before running the script, you need to first choose "Build Path" > "Use As Source Folder" for the test suite folder in Eclipse, base on the folder randoop${i} you pick, include the ${i} as a argument for calling instrument-run-test.sh.
# i.e. ". instrument-run-test.sh 0" is to analyze on randoop0 test suite
. instrument-run-test.sh ${i}

# this is to format extract the percentage coverage from the report of different classes and store the overview of coverage result in scripts/randoop${i}/${typeOfCoverage}Report/overview.txt
. get_overview.sh
```

### Bonus Task: Line coverage measurement using Soot (15%)

The relevant .java file are located in ${PROJECT_ROOT}/src/main/java/comp5111/assignment/, including `Assignment1.java`,  `LineCounter.java` and `LineInstrumenter.java`.

To run the analysis, you need to run the instrument-run-test.sh script in ${PROJECT_ROOT}/scripts/.

```
cd ${PROJECT_ROOT}/scripts/

# remark: running the below script also return the result for task 2 and 3, report is generated in scripts/randoop${i}/${typeOfCoverage}Report for different classes to be analyzed
# before running the script, you need to first choose "Build Path" > "Use As Source Folder" for the test suite folder in Eclipse, base on the folder randoop${i} you pick, include the ${i} as a argument for calling instrument-run-test.sh.
# i.e. ". instrument-run-test.sh 0" is to analyze on randoop0 test suite
. instrument-run-test.sh ${i}

# this is to format extract the percentage coverage from the report of different classes and store the overview of coverage result in scripts/randoop${i}/${typeOfCoverage}Report/overview.txt
. get_overview.sh
```

### Remarks

1. The coverage report is on `${PROJECT_ROOT}/scripts/randoop${i}/${typeOfCoverage}Report/${classname}.txt`, an example is ${PROJECT_ROOT}/scripts/randoop0/BranchReport/Branch_Subject$StringTasks.txt. The report includes 
- coverage of each statement/branch/line (statement/branch/line, yes or no)
- total number of statement/branch/line, number of invoked statement/branch/line, and the percentage coverage

2. The `overview.txt` is generated from scripts/get_overview.sh, which extract the percentage coverage information from the report in (1).

3. The difference between the result from EclEmma and Soot is in `${PROJECT_ROOT}/scripts/difference.txt`. 
The result are extracted from the all the files of overview.txt and the screenshots of running EclEmma in ${PROJECT_ROOT}/screenshots.

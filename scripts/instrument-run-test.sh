#!/bin/bash
# follow the given soot example in course webpage
DIR=$(pwd)
ROOT_DIR="$DIR"/..
cd "$ROOT_DIR" || exit

if [ ! -d "$ROOT_DIR"/raw-classes ]; then
    mkdir -p "$ROOT_DIR"/raw-classes
fi

IDX=0
for typeOfCoverage in Stmt Branch Line
do
  # 1. we compile the class under test castle.comp5111.example.Subject
  echo "compiling comp5111.assignment.cut.Subject ..."
  javac -g -d "$ROOT_DIR"/raw-classes "$ROOT_DIR"/src/main/java/comp5111/assignment/cut/Subject.java

  if [ ! -d "$ROOT_DIR"/scripts/randoop"$@"/"$typeOfCoverage"Report ]; then
      mkdir -p "$ROOT_DIR"/scripts/randoop"$@"/"$typeOfCoverage"Report
  fi

  # 2. we compile the classes to instrument Subject and count invocations using soot
  # wrap a loop to get result for different inner classes
  for className in Subject Subject\$ArrayTasks Subject\$BooleanTasks Subject\$CharTasks Subject\$FilenameTasks Subject\$GregorianTasks Subject\$NumberTasks Subject\$StringTasks
  do
    echo "compiling instrumentation classes ..."
    if [ ! -d "$ROOT_DIR"/target/classes ]; then
        mkdir -p "$ROOT_DIR"/target/classes
    fi
    find "$ROOT_DIR"/src/main/java -name "*.java" -print0 | xargs -0 \
      javac -classpath .:"$ROOT_DIR"/lib/* -d "$ROOT_DIR"/target/classes  

    # 3. we run the main method of comp5111.assignment.Assignment1
    java -classpath .:"$ROOT_DIR"/lib/*:"$ROOT_DIR"/target/classes comp5111.assignment.Assignment1 $IDX $className > scripts/randoop"$@"/"$typeOfCoverage"Report/"$typeOfCoverage"_"$className".txt
    sed -i '' '1,/Soot has run for/d' scripts/randoop"$@"/"$typeOfCoverage"Report/"$typeOfCoverage"_"$className".txt
  done
  IDX=$(($IDX+1))
done

cd scripts

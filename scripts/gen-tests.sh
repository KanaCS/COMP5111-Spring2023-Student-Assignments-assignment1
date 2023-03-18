#!/bin/bash

cd ../src/main/java
DIR=$(pwd)

javac comp5111/assignment/cut/Subject.java

for i in {0..4}
do
java -ea -classpath $DIR/../../../lib/randoop-all-4.3.1.jar:$DIR/ \
   randoop.main.Main gentests \
   --randomseed=$((100*(i+1))) \
   --testclass=comp5111.assignment.cut.Subject \
   --time-limit=60 \
   --junit-package-name=comp5111.assignment.cut \
   --junit-output-dir=$DIR/../../test/randoop$i
done

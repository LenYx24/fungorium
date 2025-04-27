#!/bin/bash

# Run the Java program with input "runall" and "exit"
echo -e "runall\nexit" | java -jar target/fungorium-prototype-1.0.jar

echo "Comparing outputs..."

# Loop from 1 to 34
for i in $(seq 1 34)
do
    if ! diff "src/main/resources/test/test$i/expected.txt" "src/main/resources/test/test$i/output.txt" > /dev/null; then
        echo "Difference found in test $i!"
        diff "src/main/resources/test/test$i/expected.txt" "src/main/resources/test/test$i/output.txt"
        echo "---------------------------------------------"
    else
        echo "Test $i passed."
    fi
done

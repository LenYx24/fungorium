#!/bin/bash

echo
(
echo runall
echo exit
) | java -jar target/fungorium-prototype-1.0.jar

echo "Comparing outputs..."
for i in {1..34}
do
    if ! diff -q "src/main/resources/test/test$i/expected.txt" "src/main/resources/test/test$i/output.txt" > /dev/null
    then
        echo "Difference found in test $i!"
        diff "src/main/resources/test/test$i/expected.txt" "src/main/resources/test/test$i/output.txt"
        echo "---------------------------------------------"
    else
        echo "Test $i passed."
    fi
done
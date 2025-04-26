for i in {1..32}; do
    if ! diff "src/main/resources/test/test${i}/expected.txt" "src/main/resources/test/test${i}/output.txt" > /dev/null; then
        echo "Difference found in test $i!"
        diff "src/main/resources/test/test${i}/expected.txt" "src/main/resources/test/test${i}/output.txt"
        echo "---------------------------------------------"
    else
        echo "Test $i passed."
    fi
done


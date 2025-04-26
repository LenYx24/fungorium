#!/bin/bash

# Loop from 5 to 16
for i in $(seq 5 16); do
    folder="test$i"
    mkdir -p "$folder"            # Create the folder
    touch "$folder/expected.txt"  # Create empty expected.txt
    touch "$folder/input.txt"     # Create empty input.txt
done

echo "Folders and files created successfully."

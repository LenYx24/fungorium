@echo off
echo
(
echo runall
echo exit
) | java -jar target/fungorium-prototype-1.0.jar
echo Comparing outputs...
for /l %%i in (1,1,34) do (
    fc "src\main\resources\test\test%%i\expected.txt" "src\main\resources\test\test%%i\output.txt" >nul
    if errorlevel 1 (
        echo Difference found in test %%i!
        fc "src\main\resources\test\test%%i\expected.txt" "src\main\resources\test\test%%i\output.txt"
        echo ---------------------------------------------
    ) else (
        echo Test %%i passed.
    )
)

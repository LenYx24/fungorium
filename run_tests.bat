@echo off
for /l %%i in (1,1,32) do (
    fc "src\main\resources\test\test%%i\expected.txt" "src\main\resources\test\test%%i\output.txt" >nul
    if errorlevel 1 (
        echo Difference found in test %%i!
        fc "src\main\resources\test\test%%i\expected.txt" "src\main\resources\test\test%%i\output.txt"
        echo ---------------------------------------------
    ) else (
        echo Test %%i passed.
    )
)

@echo off
cd /d "%~dp0"

echo.
echo ========================================
echo   Food Analyzer - Windows Launcher
echo ========================================
echo.

REM Check Java
echo Checking Java...
java -version >nul 2>&1
if errorlevel 1 (
    echo ERROR: Java not installed!
    echo Get it from: https://www.oracle.com/java/
    pause
    exit /b 1
)
echo OK - Java found
echo.

REM Create lib folder
if not exist "lib" mkdir lib

REM Build classpath
set "CLASSPATH=.;lib\HikariCP-5.1.0.jar;lib\slf4j-api-2.0.9.jar;lib\slf4j-simple-2.0.9.jar;lib\gson-2.10.1.jar;lib\mysql-connector-java-8.0.33.jar"

echo Starting Food Analyzer...
echo.

java -cp "%CLASSPATH%" com.foodanalyzer.app.Main

pause

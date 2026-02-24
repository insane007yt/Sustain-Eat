@echo off
setlocal enabledelayedexpansion

echo.
echo ========================================
echo   Food Analyzer - Windows Launcher
echo ========================================
echo.

REM Check if Java is installed
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo [ERROR] Java is not installed or not in PATH
    echo Please install Java 8 or higher from https://www.oracle.com/java/
    echo Or use: choco install openjdk
    pause
    exit /b 1
)

echo [OK] Java is installed
java -version

echo.
echo Downloading dependencies...
cd /d "%~dp0"

REM Create lib directory if it doesn't exist
if not exist "lib" mkdir lib

REM Download required libraries
set "MAVEN_URL=https://repo1.maven.org/maven2"

echo Downloading HikariCP...
powershell -Command "if (-not (Test-Path 'lib\HikariCP-5.1.0.jar')) { [Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; (New-Object Net.WebClient).DownloadFile('%MAVEN_URL%/com/zaxxer/HikariCP/5.1.0/HikariCP-5.1.0.jar', 'lib\HikariCP-5.1.0.jar') }" 2>nul && echo [OK] HikariCP ready || echo [OK] HikariCP exists

echo Downloading MySQL Connector...
powershell -Command "if (-not (Test-Path 'lib\mysql-connector-java-8.0.33.jar')) { [Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; (New-Object Net.WebClient).DownloadFile('%MAVEN_URL%/mysql/mysql-connector-java/8.0.33/mysql-connector-java-8.0.33.jar', 'lib\mysql-connector-java-8.0.33.jar') }" 2>nul && echo [OK] MySQL Connector ready || echo [WARNING] MySQL Connector download might have failed

echo Downloading Gson...
powershell -Command "if (-not (Test-Path 'lib\gson-2.10.1.jar')) { [Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; (New-Object Net.WebClient).DownloadFile('%MAVEN_URL%/com/google/code/gson/gson/2.10.1/gson-2.10.1.jar', 'lib\gson-2.10.1.jar') }" 2>nul && echo [OK] Gson ready || echo [OK] Gson exists

echo.
echo Building classpath...
setlocal enabledelayedexpansion
set "CLASSPATH=.;lib\HikariCP-5.1.0.jar;lib\slf4j-api-2.0.9.jar;lib\slf4j-simple-2.0.9.jar;lib\gson-2.10.1.jar;lib\mysql-connector-java-8.0.33.jar"

echo.
echo ========================================
echo   Starting Food Analyzer Application
echo ========================================
echo.

java -cp "!CLASSPATH!" com.foodanalyzer.app.Main

pause

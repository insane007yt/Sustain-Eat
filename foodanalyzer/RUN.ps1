# Food Analyzer - Windows PowerShell Launcher
# This script downloads dependencies and launches the application

param(
    [switch]$NoDownload = $false,
    [switch]$Help = $false
)

if ($Help) {
    Write-Host @"
Food Analyzer Windows Launcher
==============================

Usage: .\RUN.ps1 [options]

Options:
  -NoDownload  Skip dependency downloads and run directly
  -Help        Show this help message

Setup Instructions:
1. Make sure Java is installed: https://www.oracle.com/java/
2. Configure database settings in config.properties
3. Run: .\RUN.ps1

Database Setup (MySQL):
1. Install MySQL Server from https://dev.mysql.com/downloads/mysql/
2. Create database: CREATE DATABASE food_analyzer;
3. Update config.properties with your MySQL credentials:
   - db.host=localhost
   - db.port=3306
   - db.username=root
   - db.password=your_password

"@
    exit
}

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  Food Analyzer - Windows Launcher" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Check Java installation
Write-Host "Checking Java installation..." -ForegroundColor Yellow
try {
    $javaVersion = java -version 2>&1
    Write-Host "✓ Java is installed" -ForegroundColor Green
    Write-Host $javaVersion[0]
} catch {
    Write-Host "✗ Java is not installed" -ForegroundColor Red
    Write-Host "Please install Java from: https://www.oracle.com/java/" -ForegroundColor Yellow
    exit 1
}

# Create lib directory
if (!(Test-Path "lib")) {
    mkdir lib | Out-Null
}

if (!$NoDownload) {
    Write-Host ""
    Write-Host "Downloading dependencies..." -ForegroundColor Yellow
    $MavenURL = "https://repo1.maven.org/maven2"
    
    $dependencies = @(
        @{ Name = "HikariCP"; URL = "$MavenURL/com/zaxxer/HikariCP/5.1.0/HikariCP-5.1.0.jar"; File = "lib\HikariCP-5.1.0.jar" },
        @{ Name = "SLF4J API"; URL = "$MavenURL/org/slf4j/slf4j-api/2.0.9/slf4j-api-2.0.9.jar"; File = "lib\slf4j-api-2.0.9.jar" },
        @{ Name = "SLF4J Simple"; URL = "$MavenURL/org/slf4j/slf4j-simple/2.0.9/slf4j-simple-2.0.9.jar"; File = "lib\slf4j-simple-2.0.9.jar" },
        @{ Name = "Gson"; URL = "$MavenURL/com/google/code/gson/gson/2.10.1/gson-2.10.1.jar"; File = "lib\gson-2.10.1.jar" },
        @{ Name = "MySQL Connector"; URL = "$MavenURL/mysql/mysql-connector-java/8.0.33/mysql-connector-java-8.0.33.jar"; File = "lib\mysql-connector-java-8.0.33.jar" }
    )
    
    [Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12
    $webclient = New-Object System.Net.WebClient
    
    foreach ($dep in $dependencies) {
        if (Test-Path $dep.File) {
            Write-Host "✓ $($dep.Name) already downloaded" -ForegroundColor Green
        }
        else {
            Write-Host "  Downloading $($dep.Name)..." -ForegroundColor Gray
            try {
                $webclient.DownloadFile($dep.URL, $dep.File)
                Write-Host "  ✓ $($dep.Name) downloaded" -ForegroundColor Green
            }
            catch {
                Write-Host "  ✗ Failed to download $($dep.Name)" -ForegroundColor Yellow
            }
        }
    }
}

Write-Host ""
Write-Host "Building classpath..." -ForegroundColor Yellow

# Build classpath
$jars = @(
    "lib\HikariCP-5.1.0.jar",
    "lib\slf4j-api-2.0.9.jar",
    "lib\slf4j-simple-2.0.9.jar",
    "lib\gson-2.10.1.jar",
    "lib\mysql-connector-java-8.0.33.jar"
)

$CLASSPATH = "." + ";" + ($jars -join ";")

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  Starting Food Analyzer Application" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

java -cp $CLASSPATH com.foodanalyzer.app.Main

Read-Host "Press Enter to exit"

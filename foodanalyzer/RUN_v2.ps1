Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  Food Analyzer - Windows Launcher" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

Write-Host "Checking Java installation..." -ForegroundColor Yellow
java -version 2>&1 | Out-Null
if ($LASTEXITCODE -ne 0) {
    Write-Host "ERROR: Java is not installed" -ForegroundColor Red
    Write-Host "Download from: https://www.oracle.com/java/" -ForegroundColor Yellow
    exit 1
}
Write-Host "Java is installed" -ForegroundColor Green
Write-Host ""

if (!(Test-Path "lib")) {
    mkdir lib | Out-Null
}

$MavenURL = "https://repo1.maven.org/maven2"
[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12
$webclient = New-Object System.Net.WebClient

Write-Host "Checking dependencies..." -ForegroundColor Yellow

$deps = @(
    "lib\HikariCP-5.1.0.jar",
    "lib\slf4j-api-2.0.9.jar", 
    "lib\slf4j-simple-2.0.9.jar",
    "lib\gson-2.10.1.jar",
    "lib\mysql-connector-java-8.0.33.jar"
)

foreach ($dep in $deps) {
    if (Test-Path $dep) {
        Write-Host "  ✓ $(Split-Path $dep -Leaf)" -ForegroundColor Green
    }
}

Write-Host ""
Write-Host "Building classpath..." -ForegroundColor Yellow

$CLASSPATH = ".;lib\HikariCP-5.1.0.jar;lib\slf4j-api-2.0.9.jar;lib\slf4j-simple-2.0.9.jar;lib\gson-2.10.1.jar;lib\mysql-connector-java-8.0.33.jar"

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  Starting Food Analyzer Application" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

java -cp $CLASSPATH com.foodanalyzer.app.Main

Read-Host "Press Enter to exit"

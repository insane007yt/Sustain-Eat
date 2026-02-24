# Food Analyzer - Windows Setup Guide

This is a Java application originally developed for macOS that has been adapted to run on Windows.

## System Requirements

- **Windows 10/11**
- **Java 8 or higher** (JDK/JRE)
- **MySQL 5.7+** (optional, for full database features)
- **Internet connection** (for first-time dependency download)

## Quick Start

### Option 1: Using Batch File (Easiest)

1. Double-click **`RUN.bat`** in the project folder
   - This will automatically download dependencies and launch the application

### Option 2: Using PowerShell

1. Open PowerShell
2. Navigate to the project folder:
   ```powershell
   cd C:\Users\revan\Downloads\foodanalyzer\foodanalyzer
   ```
3. Run the launcher:
   ```powershell
   .\RUN.ps1
   ```

### Option 3: Manual Launch from Command Prompt

```cmd
cd C:\Users\revan\Downloads\foodanalyzer\foodanalyzer
java -cp ".;lib\*" com.foodanalyzer.app.Main
```

## Prerequisites Setup

### 1. Install Java (if not already installed)

**Check if Java is installed:**
```cmd
java -version
```

**If not installed, choose one of these options:**

**Option A: Official Oracle JDK**
- Download from: https://www.oracle.com/java/
- Run installer and follow instructions
- Restart your computer

**Option B: OpenJDK (Recommended)**
- If you have Chocolatey: `choco install openjdk`
- Or download from: https://adoptopenjdk.net/

### 2. Configure Database (Optional but Recommended)

The application can run without a database, but full features require MySQL.

**Option A: Using Local Fallback (Default)**
- The app has `app.enable.local.fallback=true` in `config.properties`
- This allows basic operation without a database

**Option B: Set Up MySQL Database**

1. **Install MySQL Server**
   - Download from: https://dev.mysql.com/downloads/mysql/
   - Choose Windows ZIP or MSI installer
   - Follow setup wizard

2. **Create the Database**
   - Open Command Prompt or MySQL Command Line Client
   - Run:
     ```sql
     CREATE DATABASE food_analyzer;
     ```

3. **Update Configuration**
   - Edit `config.properties` in the project folder
   - Update these settings:
     ```properties
     db.host=localhost
     db.port=3306
     db.username=root
     db.password=your_mysql_password
     db.useSSL=false
     ```

4. **Start MySQL Service**
   - Windows 10/11: Services → Search "Services" → Find "MySQL80" → Right-click → Start
   - Or use Command Prompt (as Administrator):
     ```cmd
     net start MySQL80
     ```

### 3. Configure Gemini API (Optional)

For AI-powered food analysis:

1. Get API key from Google AI Studio: https://aistudio.google.com/app/apikey
2. Edit `config.properties`:
   ```properties
   gemini.enabled=true
   gemini.api.key=your_api_key_here
   gemini.model=gemini-2.0-flash-exp
   ```

## Troubleshooting

### "Java is not installed or not in PATH"
- Install Java from https://www.oracle.com/java/
- Add Java to System Environment Variables:
  1. Press `Win + X` → System
  2. Click "Advanced system settings"
  3. Click "Environment Variables"
  4. Under "System variables", find "Path" → Edit
  5. Add: `C:\Program Files\Java\jdk-XX.XX.XX\bin` (use your Java version)
  6. Click OK and restart

### "Failed to get driver instance for MySQL"
- Ensure MySQL is installed and running
- Check MySQL credentials in `config.properties`
- Verify MySQL service is running (see Database Setup step 4 above)

### "NoClassDefFoundError: com.zaxxer.hikari"
- Make sure the `lib` folder exists and contains the JAR files
- Delete the `lib` folder and run the launcher again to re-download
- Check internet connection for dependency downloads

### Application runs but doesn't show GUI
- Ensure your display drivers are up to date
- Try running with:
  ```cmd
  java -Dswing.defaultlaf=com.sun.java.swing.plaf.windows.WindowsLookAndFeel -cp ".;lib\*" com.foodanalyzer.app.Main
  ```

### "java.lang.NoClassDefFoundError: com/google/gson/Gson"
- The dependency download may have failed
- Manually download from: https://github.com/google/gson
- Place the JAR file in the `lib` folder

## File Structure

```
foodanalyzer/
├── com/                           # Java package structure
│   └── foodanalyzer/
│       ├── app/                   # Main application
│       ├── db/                    # Database layer
│       ├── model/                 # Data models
│       ├── services/              # Business logic
│       ├── ui/                    # User interface
│       └── util/                  # Utilities
├── lib/                           # External dependencies (auto-downloaded)
├── config.properties              # Application configuration
├── RUN.bat                        # Windows batch launcher
├── RUN.ps1                        # PowerShell launcher
└── README.md                      # This file
```

## Port Information

- **MySQL Database**: Port 3306 (default)
- **Application**: Uses GUI (no network port required)

## Features

- ✓ Food ingredient analysis
- ✓ Health risk assessment
- ✓ Multi-language support (optimized for local fallback)
- ✓ AI-powered analysis (Gemini API - optional)
- ✓ Analysis history tracking
- ✓ Cross-platform compatibility (Windows, macOS, Linux)

## Configuration Options

See `config.properties` for all available settings:

| Setting | Default | Description |
|---------|---------|-------------|
| `app.version` | 1.0.0 | Application version |
| `app.enable.local.fallback` | true | Use local analysis without database |
| `app.max.history.records` | 1000 | Maximum analysis history records |
| `db.host` | localhost | Database hostname |
| `db.port` | 3306 | Database port |
| `db.username` | root | Database username |
| `gemini.enabled` | false | Enable Gemini API integration |
| `score.harmful.weight` | 1.5 | Weight for harmful ingredients |

## Support & Issues

- Check the configuration in `config.properties`
- Ensure all dependencies are downloaded (check `lib` folder)
- Verify Java installation: `java -version`
- For Gemini API issues, verify your API key in `config.properties`

## Notes for macOS/Linux Users

- Use the same setup steps, but use `/` for paths instead of `\`
- You may need to set execute permissions on scripts:
  ```bash
  chmod +x RUN.sh
  ```
- Database setup commands are the same

---

**Application Version**: 1.0.0  
**Last Updated**: February 25, 2026

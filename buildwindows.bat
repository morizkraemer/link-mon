@echo off
setlocal

:: Set variables
set APP_NAME=Link-Mon
set APP_VERSION=1.0
set MAIN_JAR=link-mon-1.0-SNAPSHOT.jar
set INPUT_DIR=target
set OUTPUT_DIR=output
set RUNTIME_IMAGE=custom-runtime
set APP_ICON=src\main\resources\icon.ico

:: Ensure output directory exists
rmdir /s "%OUTPUT_DIR%"
mkdir "%OUTPUT_DIR%"

:: Generate a custom runtime image using jlink (optional, but recommended)
if not exist "%RUNTIME_IMAGE%" jlink --output %RUNTIME_IMAGE% --module-path "%JAVA_HOME%\jmods" --add-modules java.base,java.desktop,java.naming

:: Run jpackage to create a standalone application
jpackage ^
    --type exe ^
    --name %APP_NAME% ^
    --app-version %APP_VERSION% ^
    --icon %APP_ICON% ^
    --input %INPUT_DIR% ^
    --main-jar %MAIN_JAR% ^
    --runtime-image %RUNTIME_IMAGE% ^
    --dest %OUTPUT_DIR% ^
    --win-shortcut ^
    --win-menu ^
    --win-dir-chooser ^
    --win-console


echo Build completed. Check the "%OUTPUT_DIR%" directory.
pause

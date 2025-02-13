@echo off
setlocal

for /f "tokens=1,2 delims==" %%a in (build-config.env) do set %%a=%%b

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

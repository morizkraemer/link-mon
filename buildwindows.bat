@echo off
setlocal

:: Load variables from build-config.env
for /f "tokens=1,2 delims==" %%a in (build-config.env) do set %%a=%%b

:: Run jpackage
jpackage ^
    --type exe ^
    --name "%APP_NAME%" ^
    --app-version "%APP_VERSION%" ^
    --icon "%APP_ICON_WINDOWS%" ^
    --input "%INPUT_DIR%" ^
    --main-jar "%MAIN_JAR%" ^
    --runtime-image "%RUNTIME_IMAGE%" ^
    --dest "%OUTPUT_DIR%" ^
    --win-shortcut ^
    --win-menu ^
    --win-dir-chooser ^
    --win-console

echo Build completed. Check the "%OUTPUT_DIR%" directory.
pause

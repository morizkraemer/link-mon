#!/bin/bash
set -e

# Load environment variables
set -a
source build-config.env
set +a

# Ensure JAVA_HOME is set
if [ -z "$JAVA_HOME" ]; then
    export JAVA_HOME=$(/usr/libexec/java_home -v 17)
fi

    if [ ! -d "$RUNTIME_IMAGE" ]; then
    jlink --output "$RUNTIME_IMAGE" --module-path "$JAVA_HOME\jmods" --add-modules java.base,java.desktop,java.naming
fi

# Run jpackage
jpackage \
    --type dmg \
    --name "$APP_NAME" \
    --app-version "$APP_VERSION" \
    --icon "$APP_ICON_MACOS" \
    --input "$INPUT_DIR" \
    --main-jar "$MAIN_JAR" \
    --runtime-image "$RUNTIME_IMAGE" \
    --dest "$OUTPUT_DIR" \
    --mac-package-identifier "$MAC_PACKAGE_ID" \
    --mac-package-name "$APP_NAME"

echo "Build completed. Check the '$OUTPUT_DIR' directory."

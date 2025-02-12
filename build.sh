#!/bin/bash

# Step 1: Clean and package the Maven project
echo "Building the project..."
mvn clean package

# Step 2: Check if the build was successful
if [ $? -eq 0 ]; then
    echo "Build successful."

    # Step 3: Run the generated JAR file
    mv target/link-mon-1.0-SNAPSHOT.jar .
else
    echo "Build failed. Please check the errors above."
fi

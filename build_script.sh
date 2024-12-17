#!/bin/bash

# Define the JAVA_HOME path
export JAVA_HOME=/path/to/your/java/home

# Add JAVA_HOME to the PATH
export PATH=$JAVA_HOME/bin:$PATH

# Navigate to the project directory
cd ./recipe_manager || exit

# Clean and build the Maven project
mvn clean install

# Check if the build was successful
if [ $? -eq 0 ]; then
    echo "Build successful!"
else
    echo "Build failed!"
    exit 1
fi

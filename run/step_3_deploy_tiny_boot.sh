#!/bin/bash

# Change to the directory where tiny-boot is located
cd ../tiny-boot || { echo "Failed to enter tiny-boot directory"; exit 1; }

# Build the project
echo "Building the tiny-boot project..."
mvn clean install  # Replace with the appropriate build command if using a different tool

# Check if the build was successful
if [ $? -ne 0 ]; then
  echo "Build failed. Please check the error messages above."
  exit 1
fi

echo "Building complete."

# Run the tiny-boot application
echo "Starting tiny-boot application..."
java -jar target/tiny-boot.jar  # Adjust the path and filename as needed

# Alternatively, you can run the application in the background:
# nohup java -jar target/tiny-boot.jar > app.log 2>&1 &

echo "tiny-boot application is now running."

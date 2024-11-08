#!/bin/bash

# Load environment variables from .env file
set -a  # Automatically export all variables
source ./env/.env  # Load variables from .env
set +a  # Stop exporting variables

# Define the target directory and files
TARGET_DIR=~/mydata/nginx/conf          # Directory for nginx configuration
TARGET_FILE="$TARGET_DIR/nginx.conf"
SOURCE_FILE="./env/nginx.conf"
ENV_FILE="./env/.env"
EXAMPLE_ENV_FILE="./env/.env.example"

# Define the SQL file and target variables
SQL_FILE=./sql/tiny_admin.sql                    # Path to the SQL file to import
SQL_TARGET_DIR=~/mydata/mysql/sql                # Directory to copy SQL file
SQL_TARGET_FILE=$SQL_TARGET_DIR/tiny_admin.sql  # Target path for the SQL file
CONTAINER_NAME=mysql                             # The name of the MySQL container

# Create the SQL target directory if it does not exist
if [ ! -d "$SQL_TARGET_DIR" ]; then
  echo "Directory $SQL_TARGET_DIR does not exist, creating it..."
  mkdir -p "$SQL_TARGET_DIR"                     # Create the SQL target directory
fi

# Check if the SQL source file exists
if [ -f "$SQL_FILE" ]; then
  echo "SQL source file '$SQL_FILE' exists."
  # Copy the SQL file to the target location
  echo "Copying from '$SQL_FILE' to '$SQL_TARGET_FILE'..."
  cp "$SQL_FILE" "$SQL_TARGET_FILE"              # Copy SQL file to the target location
  echo "SQL file copied successfully."
else
  echo "SQL source file '$SQL_FILE' not found, cannot copy."
  exit 1
fi

# Check if the target directory exists, if not, create it
if [ ! -d "$TARGET_DIR" ]; then
  echo "Directory $TARGET_DIR does not exist, creating it..."
  mkdir -p "$TARGET_DIR"                  # Create the target directory
fi

# Copy nginx.conf from the current directory to the target directory, overwriting it
echo "Copying nginx.conf to $TARGET_FILE, overwriting if it exists..."
cp "$SOURCE_FILE" "$TARGET_FILE"          # Copy the source file to the target file
echo "Copy completed successfully."

# Check if .env file exists, if not, copy .env.example
if [ ! -f "$ENV_FILE" ]; then
  echo ".env file does not exist, copying from .env.example..."
  cp "$EXAMPLE_ENV_FILE" "$ENV_FILE"
else
  echo ".env file already exists, skipping copy."
fi

# Start docker-compose
echo "Starting docker-compose-env.yml..."
docker-compose -f ./env/docker-compose-env.yml up -d

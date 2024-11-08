#!/bin/bash

# Load environment variables from .env file
set -a
source ./env/.env
set +a

# Define the SQL file and target variables
SQL_FILE=./sql/tiny_admin.sql                    # Path to the SQL file to import
SQL_TARGET_DIR=~/mydata/mysql/sql                # Directory to copy SQL file
SQL_TARGET_FILE="$SQL_TARGET_DIR/tiny_admin.sql"  # Target path for the SQL file
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

# Wait for MySQL to start (optional)
#sleep 5  # Wait time may vary depending on the system

# Create the database and import the SQL file
echo "Creating database 'tiny_admin'..."
CREATE_DB_OUTPUT=$(docker exec -i $CONTAINER_NAME mysql -u root -p"$MYSQL_ROOT_PASSWORD" -e "CREATE DATABASE IF NOT EXISTS tiny_admin;" 2>&1)

echo "Output from database creation: $CREATE_DB_OUTPUT"  # Debugging output

# Check if the database was created successfully
if echo "$CREATE_DB_OUTPUT" | grep -q "Database created"; then
  echo "Database 'tiny_admin' created successfully."
elif echo "$CREATE_DB_OUTPUT" | grep -q "Database unchanged"; then
  echo "Database 'tiny_admin' already exists."
else
  echo "Failed to create database. Error: $CREATE_DB_OUTPUT"
fi

# Import the SQL file into the database only if it was created or already exists
if [ -f "$SQL_TARGET_FILE" ]; then
  echo "Importing data from '$SQL_TARGET_FILE'..."

  IMP_OUTPUT=$(docker exec -i "$CONTAINER_NAME" mysql -u root -p"$MYSQL_ROOT_PASSWORD" tiny_admin < "$SQL_TARGET_FILE" 2>&1)

  if [ $? -eq 0 ]; then
    echo "Database import completed successfully."
  else
    echo "Failed to import SQL file. Error: $IMP_OUTPUT"
  fi
fi

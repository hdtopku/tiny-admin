#!/bin/bash

# Get the absolute path of the current script
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
echo "The absolute path of the script is: $SCRIPT_DIR"

# Load environment variables from .env file
set -a
source "$SCRIPT_DIR/../env/.env"
set +a

# Define the SQL file and target variables
SQL_FILE="$SCRIPT_DIR/tiny_admin.sql"              # Path to the SQL file to import
SQL_TARGET_DIR="$HOME/mydata/mysql/sql"            # Directory to copy SQL file
SQL_TARGET_FILE="$SQL_TARGET_DIR/tiny_admin.sql"   # Target path for the SQL file
CONTAINER_NAME="mysql"                             # The name of the MySQL container

# Function to create a directory if it doesn't exist
create_directory() {
  local dir_path="$1"
  if [ ! -d "$dir_path" ]; then
    echo "Directory $dir_path does not exist, creating it..."
    mkdir -p "$dir_path"
    echo "Directory $dir_path created successfully."
  else
    echo "Directory $dir_path already exists, skipping creation."
  fi
}

# Function to copy a file if it exists
copy_file() {
  local source="$1"
  local target="$2"
  cp "$source" "$target"
  echo "SQL file copied successfully."
}

# Create the SQL target directory
create_directory "$SQL_TARGET_DIR"

# Copy the SQL file to the target location
copy_file "$SQL_FILE" "$SQL_TARGET_DIR"

# Wait for MySQL to start
echo "Waiting for MySQL container to be ready..."
sleep 15  # Adjust the sleep time if necessary

# Create the database
echo "Creating database 'tiny_admin'..."
docker exec -i "$CONTAINER_NAME" mysql -u root -p"$MYSQL_ROOT_PASSWORD" -e "CREATE DATABASE IF NOT EXISTS tiny_admin;"

# Import the SQL file into the database
echo "Importing data from '$SQL_TARGET_FILE'..."
docker exec -i "$CONTAINER_NAME" mysql -u root -p"$MYSQL_ROOT_PASSWORD" tiny_admin < "$SQL_TARGET_FILE"
echo "Database import completed successfully."
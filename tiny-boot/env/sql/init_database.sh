#!/bin/bash

# Get the absolute path of the current script
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
echo "The absolute path of the script is: $SCRIPT_DIR"

# Load environment variables from .env file
set -a
source "$SCRIPT_DIR/../.env"
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

# Function to check if a database exists in MySQL
database_exists() {
  local db_name="$1"
  docker exec -i "$CONTAINER_NAME" mysql -u root -p"$MYSQL_ROOT_PASSWORD" -e "SHOW DATABASES LIKE '$db_name';" | grep "$db_name" > /dev/null 2>&1
  return $?
}

# Function to check if a table exists in a MySQL database
table_exists() {
  local db_name="$1"
  local table_name="$2"
  docker exec -i "$CONTAINER_NAME" mysql -u root -p"$MYSQL_ROOT_PASSWORD" -e "SHOW TABLES IN $db_name LIKE '$table_name';" | grep "$table_name" > /dev/null 2>&1
  return $?
}

# Create the SQL target directory
create_directory "$SQL_TARGET_DIR"

# Copy the SQL file to the target location
copy_file "$SQL_FILE" "$SQL_TARGET_DIR"

# Check if the database exists
if database_exists "tiny_admin"; then
  echo "Database 'tiny_admin' already exists. Skipping creation and import tables. If you want to recreate the database, please drop the existing database 'tiny_admin' first."
else
  echo "Database 'tiny_admin' does not exist, creating it..."
  sleep 30
  # Create the database
  docker exec -i "$CONTAINER_NAME" mysql -u root -p"$MYSQL_ROOT_PASSWORD" -e "CREATE DATABASE tiny_admin;"

  # Import the SQL file into the database
  echo "Importing data from '$SQL_TARGET_FILE'..."
  docker exec -i "$CONTAINER_NAME" mysql -u root -p"$MYSQL_ROOT_PASSWORD" tiny_admin < "$SQL_TARGET_FILE"
  echo "Database import completed successfully."
fi

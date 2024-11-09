#!/bin/bash

# Get the absolute path of the current script
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
echo "The absolute path of the script is: $SCRIPT_DIR"

# Define paths and directories
NGINX_TARGET_DIR="$HOME/mydata/nginx/conf"               # Directory for nginx configuration
NGINX_TARGET_FILE="$NGINX_TARGET_DIR/nginx.conf"               # Target nginx.conf file path
NGINX_SOURCE_FILE="$SCRIPT_DIR/nginx.conf"           # Source nginx.conf file path
ENV_FILE="$SCRIPT_DIR/.env"                    # .env file path
EXAMPLE_ENV_FILE="$SCRIPT_DIR/.env.example"    # .env.example file path
SQL_FILE="$SCRIPT_DIR/sql/tiny_admin.sql"          # Path to the SQL file to import
SQL_TARGET_DIR="$HOME/mydata/mysql/sql"            # Directory to copy SQL file
SQL_TARGET_FILE="$SQL_TARGET_DIR/tiny_admin.sql"   # Target SQL file path

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
  if [ -f "$source" ]; then
    echo "Source file '$source' exists. Copying to '$target'..."
    cp "$source" "$target"
    echo "File copied successfully."
  else
    echo "Source file '$source' not found, cannot copy."
    exit 1
  fi
}

# Create directories if they don't exist
create_directory "$SQL_TARGET_DIR"
create_directory "$NGINX_TARGET_DIR"

# Copy SQL file to the target directory
cp "$SQL_FILE" "$SQL_TARGET_FILE"

# Copy nginx configuration file to the target directory
copy_file "$NGINX_SOURCE_FILE" "$NGINX_TARGET_FILE"

# Check if .env file exists, if not, copy .env.example
if [ ! -f "$ENV_FILE" ]; then
  echo ".env file does not exist, copying from .env.example..."
  cp "$EXAMPLE_ENV_FILE" "$ENV_FILE"
  echo ".env file created successfully."
else
  echo ".env file already exists, skipping copy."
fi

# Load environment variables from .env file
set -a  # Automatically export all variables
source "$SCRIPT_DIR/.env"  # Load variables from .env
set +a  # Stop exporting variables

# Start docker-compose
echo "Starting docker-compose-env.yml..."
docker pull openjdk:22-ea-16-jdk-slim
docker-compose -f "$SCRIPT_DIR/docker-compose.yml" up -d

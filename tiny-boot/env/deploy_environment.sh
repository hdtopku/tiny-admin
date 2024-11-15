#!/bin/bash

# Define directories and file paths
SCRIPT_DIR=$(cd "$(dirname "$0")" && pwd)
NGINX_TARGET_DIR="$HOME/mydata/nginx/conf"
NGINX_SOURCE_FILE="${SCRIPT_DIR}/nginx.conf"
ENV_FILE="${SCRIPT_DIR}/.env"
EXAMPLE_ENV_FILE="${SCRIPT_DIR}/.env.example"
SQL_FILE="${SCRIPT_DIR}/sql/tiny_admin.sql"
SQL_TARGET_DIR="$HOME/mydata/mysql/sql"
SQL_CONF_DIR="$HOME/mydata/mysql/conf"
REDIS_TARGET_DIR="$HOME/mydata/redis/data"

# Function to create a directory if it doesn't exist
create_directory() {
  local dir_path="$1"
  if [ ! -d "$dir_path" ]; then
    echo "Directory $dir_path does not exist, creating it..."
    mkdir -p "$dir_path" || { echo "Failed to create directory $dir_path"; exit 1; }
  else
    echo "Directory $dir_path already exists, skipping creation."
  fi
}

# Function to copy a file if it exists
copy_file() {
  local source="$1"
  local target="$2"
  if [ -f "$source" ]; then
    echo "Copying '$source' to '$target'..."
    cp -f "$source" "$target" || { echo "Failed to copy $source to $target"; exit 1; }
  else
    echo "Source file '$source' not found, cannot copy."
    exit 1
  fi
}

# Clean up and create necessary directories
echo "Cleaning up and creating directories..."
rm -rf "$SQL_TARGET_DIR"
create_directory "$NGINX_TARGET_DIR"
create_directory "$SQL_TARGET_DIR"
create_directory "$SQL_CONF_DIR"
create_directory "$REDIS_TARGET_DIR"

# Copy necessary files
copy_file "$SQL_FILE" "$SQL_TARGET_DIR"
copy_file "$NGINX_SOURCE_FILE" "$NGINX_TARGET_DIR"

# Check and create .env file if it doesn't exist
if [ ! -f "$ENV_FILE" ]; then
  echo ".env file does not exist, creating it from .env.example..."
  cp "$EXAMPLE_ENV_FILE" "$ENV_FILE" || { echo "Failed to create .env from .env.example"; exit 1; }
else
  echo ".env file already exists, skipping creation."
fi

# Load environment variables from .env file
# shellcheck source=./.env
echo "Loading environment variables from .env..."
set -a  # Automatically export all variables
# shellcheck disable=SC1090
source "$ENV_FILE"
set +a  # Stop exporting variables

# Start Docker containers
echo "Starting Docker containers with docker-compose..."
docker-compose -f "${SCRIPT_DIR}/docker-compose-env.yml" up -d || { echo "Failed to start Docker containers"; exit 1; }

# Restart nginx container to apply changes to configuration
docker restart nginx

# Pull OpenJDK image and import SQL into MySQL container
docker pull openjdk:22-ea-16-jdk-slim

echo "Waiting for MySQL container to start up before importing SQL file"
sleep 20
echo "Importing SQL file into MySQL container..."
sh "${SCRIPT_DIR}/sql/init_database.sh" || { echo "Failed to import SQL file"; exit 1; }

echo "Environment setup completed successfully."

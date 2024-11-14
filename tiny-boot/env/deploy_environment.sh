#!/bin/bash

SCRIPT_DIR=$(cd "$(dirname "$0")" && pwd)
cd "$SCRIPT_DIR" || { echo "Failed to change directory to $SCRIPT_DIR"; exit 1; }

# Define paths and directories
NGINX_TARGET_DIR="$HOME/mydata/nginx/conf"               # Directory for nginx configuration
NGINX_SOURCE_FILE="${SCRIPT_DIR}/nginx.conf"                            # Source nginx.conf file path
ENV_FILE="${SCRIPT_DIR}/.env"                                           # .env file path
EXAMPLE_ENV_FILE="${SCRIPT_DIR}/.env.example"                           # .env.example file path
SQL_FILE="${SCRIPT_DIR}/sql/tiny_admin.sql"                             # Path to the SQL file to import
SQL_TARGET_DIR="$HOME/mydata/mysql/sql"                   # Directory to copy SQL file
SQL_CONF_DIR="$HOME/mydata/mysql/conf"                 # Directory to copy MySQL configuration file
REDIS_TARGET_DIR="$HOME/mydata/redis/data"                   # Directory to copy Redis configuration file

# Function to create a directory if it doesn't exist
create_directory() {
  local dir_path="$1"
  if [ ! -d "$dir_path" ]; then
    echo "Directory $dir_path does not exist, creating it..."
    mkdir -p "$dir_path" || { echo "Failed to create directory $dir_path"; exit 1; }
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
    cp "$source" "$target" || { echo "Failed to copy $source to $target"; exit 1; }
    echo "File copied successfully."
  else
    echo "Source file '$source' not found, cannot copy."
    exit 1
  fi
}

# Create directories if they don't exist
rm -rf "$NGINX_TARGET_DIR"
rm -rf "$SQL_TARGET_DIR"
create_directory "$NGINX_TARGET_DIR"
create_directory "$SQL_TARGET_DIR"
create_directory "$SQL_CONF_DIR"
create_directory "$REDIS_TARGET_DIR"


# Copy SQL file to the target directory
cp -f "$SQL_FILE" "$SQL_TARGET_DIR"
# Copy nginx configuration file to the target directory
cp -f "$NGINX_SOURCE_FILE" "$NGINX_TARGET_DIR"

# Check if .env file exists, if not, copy .env.example
if [ ! -f "$ENV_FILE" ]; then
  echo ".env file does not exist, copying from .env.example..."
  cp "$EXAMPLE_ENV_FILE" "$ENV_FILE" || { echo "Failed to create .env from .env.example"; exit 1; }
  echo ".env file created successfully."
else
  echo ".env file already exists, skipping copy."
fi

# Load environment variables from .env file
echo "Loading environment variables from .env..."
set -a  # Automatically export all variables
source "$ENV_FILE"  # Load variables from .env
set +a  # Stop exporting variables

# Start docker-compose
echo "Starting docker-compose.yml..."
docker-compose -f "${SCRIPT_DIR}/docker-compose-env.yml" up -d || { echo "Failed to start Docker containers"; exit 1; }

docker pull openjdk:22-ea-16-jdk-slim
# Import SQL file into MySQL container
echo "Importing SQL file into MySQL container..."
sh "${SCRIPT_DIR}/sql/init_database.sh" || { echo "Failed to import SQL file"; exit 1; }
docker restart nginx
echo "Environment setup completed successfully."

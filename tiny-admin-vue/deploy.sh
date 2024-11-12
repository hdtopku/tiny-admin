#!/bin/bash

# Get the script's directory
SCRIPT_DIR=$(cd "$(dirname "$0")" && pwd)
cd "$SCRIPT_DIR" || exit

# Clean up the old build output and create the directory
rm -rf ~/mydata/nginx/html/tiny_admin
mkdir -p ~/mydata/nginx/html/tiny_admin

# Pull the Node.js base image
docker pull node:lts-alpine3.20

# Build and run the Docker container with Docker Compose
docker-compose -f "$SCRIPT_DIR/docker-compose.yml" up --build --remove-orphans
docker restart nginx
# Output success message
echo "Deployed successfully!"

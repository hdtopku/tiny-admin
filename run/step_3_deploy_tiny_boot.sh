#!/bin/bash

# Get the absolute path of the current script
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
echo "The absolute path of the script is: $SCRIPT_DIR"

docker-compose -f "$SCRIPT_DIR/env/docker-compose.yml" up --build -d

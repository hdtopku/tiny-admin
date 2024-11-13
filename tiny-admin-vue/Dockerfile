# Use the official Node.js image
FROM node:lts-alpine3.20

# Set the working directory in the container
WORKDIR /app

# Copy package.json and pnpm-lock.yaml for dependency installation
COPY package.json pnpm-lock.yaml ./

# Install pnpm and project dependencies
RUN npm install -g pnpm && pnpm install

# Copy the rest of the project files
COPY . .

# Build the project using pnpm
RUN pnpm build

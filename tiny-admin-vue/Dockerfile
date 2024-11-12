# Use an official Node.js runtime as a parent image
FROM node:lts-alpine3.20

# Set the working directory in the container
WORKDIR /app

# Copy over only package.json and pnpm-lock.yaml (for efficient caching)
COPY package.json pnpm-lock.yaml ./

# Install pnpm globally
RUN npm install -g pnpm && pnpm install

# Copy the rest of the application code
COPY . .

# Build the Vite project
RUN pnpm build
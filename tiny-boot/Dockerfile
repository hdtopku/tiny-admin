FROM openjdk:22-ea-16-jdk-slim AS build

RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*

WORKDIR /usr/src/app/temp
COPY . .
RUN mvn -B dependency:go-offline && mvn clean install -DskipTests

# Runtime image
FROM openjdk:22-ea-16-jdk-slim
# Ensure /usr/src/app is a directory by removing any file or directory with the same name
COPY --from=build /usr/src/app/temp/tiny-admin/tiny-admin-starter/target/tiny-admin-starter.jar /usr/src/app/
RUN rm -rf /usr/src/app/temp
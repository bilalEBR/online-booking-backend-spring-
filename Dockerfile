# Stage 1: Build the application
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
# Build the JAR file
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM eclipse-temurin:21-jre
WORKDIR /app
# Copy the built JAR from the first stage
COPY --from=build /app/target/*.jar app.jar

# Create the uploads folder for screenshots
RUN mkdir -p uploads

# Expose the port (Render will override this, but 8443 is our local default)
EXPOSE 8443

# Start the application
ENTRYPOINT ["java", "-jar", "app.jar"]
# Build stage
FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /workspace
COPY pom.xml .
RUN mvn -q -e -B -DskipTests dependency:resolve
COPY src ./src
RUN mvn -q -e -B -DskipTests package

# Runtime stage with distroless for minimal surface
FROM gcr.io/distroless/java21-debian12:nonroot
WORKDIR /app
COPY --from=builder /workspace/target/hello-world-1.0.0-jar-with-dependencies.jar /app/app.jar
EXPOSE 8080
USER nonroot
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

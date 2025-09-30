# Build stage
FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /workspace
COPY pom.xml .
RUN mvn -q -e -B -DskipTests dependency:resolve
COPY src ./src
RUN mvn -q -e -B -DskipTests package

# Runtime stage with distroless for minimal surface
FROM gcr.io/distroless/java21@sha256:418b2e2a9e452aa9299511427f2ae404dfc910ecfa78feb53b1c60c22c3b640c
WORKDIR /app
COPY --from=builder /workspace/target/hello-world-1.0.0-jar-with-dependencies.jar /app/app.jar
EXPOSE 8080
USER nonroot
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

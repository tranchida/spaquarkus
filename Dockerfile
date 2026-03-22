####
# Multi-stage Dockerfile for spaquarkus (Quarkus JVM mode)
#
# Build and tag the image with:
#
#   docker build -t spaquarkus:v1 .
#
# Run the container with:
#
#   docker run -i --rm -p 8080:8080 spaquarkus:v1
####

# ---- Build stage ----
FROM maven:3.9-eclipse-temurin-25 AS builder

WORKDIR /build

# Copy dependency descriptor first for better layer caching
COPY pom.xml .

# Download dependencies offline (layer cached unless pom.xml changes)
RUN mvn dependency:go-offline -q

# Copy source and build
COPY src ./src
RUN mvn package -DskipTests -q

# ---- Runtime stage ----
FROM eclipse-temurin:25-jre AS runtime

WORKDIR /deployments

# Create a dedicated non-root user
RUN groupadd -r appgroup && useradd -r -u 1001 -g appgroup appuser

# Copy the Quarkus fast-jar layout (four layers for optimal caching)
COPY --chown=appuser:appgroup --from=builder /build/target/quarkus-app/lib/ /deployments/lib/
COPY --chown=appuser:appgroup --from=builder /build/target/quarkus-app/*.jar /deployments/
COPY --chown=appuser:appgroup --from=builder /build/target/quarkus-app/app/ /deployments/app/
COPY --chown=appuser:appgroup --from=builder /build/target/quarkus-app/quarkus/ /deployments/quarkus/

EXPOSE 8080
USER appuser

ENV JAVA_OPTS_APPEND="-Dquarkus.http.host=0.0.0.0 -Djava.util.logging.manager=org.jboss.logmanager.LogManager"

ENTRYPOINT ["java", "-jar", "/deployments/quarkus-run.jar"]

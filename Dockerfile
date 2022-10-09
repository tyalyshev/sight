#-------------------------------------------------------------------------------
# build project
FROM maven:3.8.6-openjdk-18-slim AS backend-builder

WORKDIR /java
COPY . /java

RUN --mount=type=cache,target=/root/.m2 mvn install --threads 1C \
    --batch-mode --errors --fail-at-end --show-version \
    -DinstallAtEnd=true -DdeployAtEnd=true \
    -DskipTests \
    -Dmaven.repo.local=/root/.m2/repository \
    -Dorg.slf4j.simpleLogger.showDateTime=true -Dorg.slf4j.simpleLogger.dateTimeFormat=HH:mm:ss,SSSa

#-------------------------------------------------------------------------------
# create RELEASE image
FROM openjdk:18-slim AS publisher-release
RUN adduser --system --group appuser
RUN apt-get update \
    && apt-get install -yq --no-install-recommends \
       curl \
       jq \
    && rm -rf /var/*/apt/*
USER appuser
EXPOSE 8082
#HEALTHCHECK --start-period=30s --interval=30s --timeout=3s --retries=3 \
#            CMD curl --silent --fail --request GET http://localhost:8080/actuator/health \
#                | jq --exit-status '.status == "UP"' || exit 1
COPY --from=backend-builder /java/publisher-read/target/publisher-read-*.jar /app.jar
CMD ["java", "-jar", "/app.jar"]

#-------------------------------------------------------------------------------
# create DEV image
FROM openjdk:18-slim AS publisher-dev
RUN adduser --system --group appuser
USER appuser
EXPOSE 8082
COPY --from=backend-builder /java/target/sight-0.0.1-SNAPSHOT.jar /app.jar
CMD ["java", "-jar", "/app.jar"]

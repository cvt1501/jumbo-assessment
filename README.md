# Jumbo Assessment

This project demonstrates:

- Spring Boot with option of AOT compilation using GraalVM Native Image
- Observability via:
    - Actuator endpoints: `/metrics`, `/prometheus`, `/health`
    - Structured logging with `traceId`/`spanId`
    - Lightweight distributed tracing with Micrometer

## Requirements

- **Java 21** (Recommended: Temurin, GraalVM, or Amazon Corretto)
- **Maven 3.9+**
- **Docker** (for containerized native builds)
- (Optional) **GraalVM JDK 21** (for direct native compilation)

## How to build and run

### Option 1: Traditional JVM Execution (Development Mode)
```bash
mvn clean spring-boot:run -Dspring-boot.run.profiles=local
```

This will:
- Start the application on `http://localhost:8080` using local profile - application-local.yml.

### Option 2: Native Image via Docker (Production Recommended)

Build optimized container with native image
```bash
mvn -Pnative -DskipTests spring-boot:build-image
```

Run the production container
```bash
docker run --platform=linux/amd64 -p 8080:8080 assessment:1.0.0
```

This will:
- Start the application on `http://localhost:8080`

## Available Endpoints

### `GET /stores/closest`

This is the only available endpoint in the application.

---

### 🧾 Query parameters:

| Name       | Type    | Required | Description                   |
|------------|---------|----------|-------------------------------|
| `latitude` | decimal | ✅       | Latitude of the current position |
| `longitude`| decimal | ✅       | Longitude of the current position |


## How to run unit tests

```bash
mvn clean test
```

This will:
- Run the unit tests of this application.

## Test the API

```bash
curl http://localhost:8080/stores/closest?longitude=5.804116&latitude=51.456157
```

## Structured Logs

```json
{
  "timestamp": "...",
  "level": "INFO",
  "message": "",
  "traceId": "abc123",
  "spanId": "def456"
}
```

## Metrics

- Prometheus endpoint:  
  [http://localhost:8080/actuator/prometheus](http://localhost:8080/actuator/prometheus)

## Notes

- First-time native image build takes ~1–3 minutes. But once you have the image, you don't have to build the image again unless you make changes in the code.
- All logs include trace/span context for simple distributed tracing
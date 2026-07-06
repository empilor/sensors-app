# Sensors App - Warehouse Monitoring System

Reactive sensor monitoring system for warehouses.


## Quick Start

```bash
cd sensors_app

# Build & run all containers
docker-compose up --build
```

## Modules

| Module | Description |
|--------|-------------|
| `warehouse-service` | Receives UDP sensor data, forwards to Central Service via HTTP |
| `central-service` | Validates thresholds (temp >35°C, humidity >50%), triggers alarms |
| `sensor-simulators` | Bash scripts simulating temperature/humidity sensors (AI-generated) |


## Services

| Service | Port | Debug |
|---------|------|-------|
| central-service | 8080 | 5005 |
| warehouse-service | 8081, UDP 3344/3355 | 5006 |


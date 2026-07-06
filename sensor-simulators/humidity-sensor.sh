#!/bin/sh
# Humidity Sensor Simulator
# Sends UDP messages to warehouse-service on port 3355
# Format: sensor_id=h1; value=40

SENSOR_ID="${SENSOR_ID:-h1}"
HOST="${HOST:-warehouse-service}"
PORT="${PORT:-3355}"
INTERVAL="${INTERVAL:-5}"

echo "Starting Humidity Sensor Simulator"
echo "  Sensor ID: $SENSOR_ID"
echo "  Target: $HOST:$PORT"
echo "  Interval: ${INTERVAL}s"
echo "----------------------------------------"

while true; do
    # Generate random humidity between 30 and 70 (sometimes exceeding threshold of 50)
    VALUE=$(awk -v min=30 -v max=70 'BEGIN{srand(); printf "%.1f", min+rand()*(max-min)}')
    MESSAGE="sensor_id=$SENSOR_ID; value=$VALUE"
    
    echo "[$(date '+%Y-%m-%d %H:%M:%S')] Sending: $MESSAGE"
    echo "$MESSAGE" | nc -u -w1 $HOST $PORT
    
    sleep $INTERVAL
done

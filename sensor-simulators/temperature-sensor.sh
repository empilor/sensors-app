#!/bin/sh
# Temperature Sensor Simulator
# Sends UDP messages to warehouse-service on port 3344
# Format: sensor_id=t1; value=30

SENSOR_ID="${SENSOR_ID:-t1}"
HOST="${HOST:-warehouse-service}"
PORT="${PORT:-3344}"
INTERVAL="${INTERVAL:-5}"

echo "Starting Temperature Sensor Simulator"
echo "  Sensor ID: $SENSOR_ID"
echo "  Target: $HOST:$PORT"
echo "  Interval: ${INTERVAL}s"
echo "----------------------------------------"

while true; do
    # Generate random temperature between 20 and 45 (sometimes exceeding threshold of 35)
    VALUE=$(awk -v min=20 -v max=45 'BEGIN{srand(); printf "%.1f", min+rand()*(max-min)}')
    MESSAGE="sensor_id=$SENSOR_ID; value=$VALUE"
    
    echo "[$(date '+%Y-%m-%d %H:%M:%S')] Sending: $MESSAGE"
    echo "$MESSAGE" | nc -u -w1 $HOST $PORT
    
    sleep $INTERVAL
done

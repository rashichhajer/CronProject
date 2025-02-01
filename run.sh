#!/bin/bash

# ✅ Ensure a cron expression is passed
if [ "$#" -ne 1 ]; then
    echo "Usage: $0 \"cron_expression\""
    exit 1
fi

# ✅ Get the script's directory and locate the JAR
JAR_PATH="$(dirname "$0")/target/CronProject-1.0-SNAPSHOT.jar"

# ✅ Check if the JAR file exists
if [ ! -f "$JAR_PATH" ]; then
    echo "Error: JAR file not found. Please build the project first with:"
    echo "  mvn clean package -DskipTests"
    exit 1
fi

# ✅ Run the JAR with the given cron expression
java -jar "$JAR_PATH" "$1"


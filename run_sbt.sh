#!/bin/bash

# Check if arguments are provided, otherwise set default values
INPUT_PATH=${1:-data}
OUTPUT_PATH=${2:-data/output}

# Run the sbt command with dynamic arguments
sbt "run --input-path $INPUT_PATH --output-path $OUTPUT_PATH"

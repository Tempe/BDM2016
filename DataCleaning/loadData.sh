#!/bin/bash
JSON_FILE="http://130.226.142.195/bigdata/project2/$1"
JAVA_CLEANER="$2"
echo $(wget -O- "$JSON_FILE" | java $JAVA_CLEANER)
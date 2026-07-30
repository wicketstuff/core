#!/bin/bash
set -e

# Allow overriding the maven command via environment variable MVN_CMD (default: mvn)
MVN_CMD=${MVN_CMD:-mvn}

"$MVN_CMD" -Dmaven.javadoc.skip=true -DskipTests clean package -f ..

docker build -t "wicketstuff-jquery-ui" .
docker run -ti -p 8080:8080 wicketstuff-jquery-ui

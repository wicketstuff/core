#!/bin/bash
mvn clean package -f ..

docker build -t "wicketstuff-jquery-ui" .
docker run -ti -p 8080:8080 wicketstuff-jquery-ui

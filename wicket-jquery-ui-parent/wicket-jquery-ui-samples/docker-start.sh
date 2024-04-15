#!/bin/bash
mvn clean package -f ..

docker build -t "wicket-jquery-ui" .
docker run -ti -p 8080:8080 wicket-jquery-ui

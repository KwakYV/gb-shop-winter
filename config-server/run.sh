#!/bin/bash

docker run -p 8888:8888 --name gb-config-server-container -e GIT_URI=/tmp/gb-settings -e ENCRYPT_KEYSTORE_LOCATION=file:///tmp/key/server.jks -v /Users/kvak/server.jks:/tmp/key/server.jks -v /Users/kvak/repos/gb-settings:/tmp/gb-settings -d gb-config-server:latest
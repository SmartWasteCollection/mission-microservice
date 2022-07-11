#!/bin/bash

# Login to Azure Platform
az login --service-principal -u $AZURE_SERVICE_PRINCIPAL_NAME -p $AZURE_SECRET --tenant $AZURE_TENANT -o none

# Installation of openjdk
apk update
apk add openjdk17

# Launch service
./gradlew run
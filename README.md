[![Continuous Integration](https://github.com/SmartWasteCollection/mission-microservice/actions/workflows/ci.yml/badge.svg?event=push)](https://github.com/SmartWasteCollection/mission-microservice/actions/workflows/ci.yml)
[![Continuous Integration](https://github.com/SmartWasteCollection/mission-microservice/actions/workflows/cd.yml/badge.svg?event=push)](https://github.com/SmartWasteCollection/mission-microservice/actions/workflows/cd.yml)
[![GitHub issues](https://img.shields.io/github/issues-raw/SmartWasteCollection/mission-microservice?style=plastic)](https://github.com/SmartWasteCollection/mission-microservice/issues)
[![GitHub pull requests](https://img.shields.io/github/issues-pr-raw/SmartWasteCollection/mission-microservice?style=plastic)](https://github.com/SmartWasteCollection/truck-microservice/pulls)
[![GitHub](https://img.shields.io/github/license/SmartWasteCollection/mission-microservice?style=plastic)](/LICENSE)
[![GitHub release (latest SemVer including pre-releases)](https://img.shields.io/github/v/release/SmartWasteCollection/mission-microservice?include_prereleases&style=plastic)](https://github.com/SmartWasteCollection/mission-microservice/releases)
[![codecov](https://codecov.io/gh/SmartWasteCollection/mission-microservice/branch/main/graph/badge.svg?token=1JEX4QPDYL)](https://codecov.io/gh/SmartWasteCollection/mission-microservice)

# mission-microservice

This repository contains the microservice that handles the generation and management of missions.

---

To run this microservice you can get the system's latest container image:

```
$ docker run -p <port>:8080 -e server.port=8080 --env-file .env -e MONGO_CONNECTION_STRING=<connection-string> ghcr.io/smartwastecollection/mission-microservice:<latestVersion>
```

The `.env` file **must** contain the secrets needed to perform the login into the azure cloud platform:

1. `AZURE_SERVICE_PRINCIPAL_NAME`: UUID that represents the Application (client) ID of the _Service Principal_
2. `AZURE_SECRET`: UUID that identifies the secret used to perform the login from the _Service Principal_
3. `AZURE_TENANT`: UUID that identifies the Directory (tenant) ID of the _Service Principal_

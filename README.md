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
$ docker run -p <port>:<port> -e server.port=<port> -e MONGO_CONNECTION_STRING=<connection-string> ghcr.io/smartwastecollection/mission-microservice:<latestVersion>
```

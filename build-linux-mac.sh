#!/usr/bin/env bash
set -euo pipefail
command -v java >/dev/null || { echo 'JDK 17 gerekli'; exit 1; }
command -v gradle >/dev/null || { echo 'Gradle 8.7+ gerekli'; exit 1; }
gradle clean build
echo "JAR hazir: build/libs/fabric-ai-animation-0.1.0.jar"

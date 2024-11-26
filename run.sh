#!/bin/bash
cd "$(
  cd "$(dirname "$0")" >/dev/null 2>&1
  pwd -P
)/" || exit
set -e

export JAVA_HOME=/d/zoo/jdk-21.0.3
mvn clean spring-boot:run
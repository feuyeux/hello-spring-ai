#!/bin/bash
cd "$(
  cd "$(dirname "$0")" >/dev/null 2>&1
  pwd -P
)/" || exit
set -e

if [[ "$OSTYPE" == "darwin"* ]]; then
  export JAVA_HOME=/Library/Java/JavaVirtualMachines/openjdk-21.jdk/Contents/Home
else
  export JAVA_HOME=/d/zoo/jdk-21.0.3
fi

mvn clean spring-boot:run
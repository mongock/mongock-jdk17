#!/usr/bin/env bash
export GPG_TTY=$(tty)
export MAVEN_OPTS="--add-opens=java.base/java.util=ALL-UNNAMED --add-opens=java.base/java.lang.reflect=ALL-UNNAMED --add-opens=java.base/java.text=ALL-UNNAMED --add-opens=java.desktop/java.awt.font=ALL-UNNAMED"
mvn -B release:prepare release:perform -Dmaven.javadoc.skip=true -Pno-test

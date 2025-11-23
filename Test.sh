#!/bin/bash
./gradlew clean test jacocoTestReport
xdg-open core/build/reports/jacoco/index.html

#!/bin/bash
mvn versions:display-dependency-updates | \
  grep -v checking | \
  grep -v org.jetbrains.kotlin

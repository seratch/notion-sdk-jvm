#!/bin/bash
mvn versions:display-dependency-updates | grep -v checking

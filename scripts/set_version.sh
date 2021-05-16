#!/bin/bash

new_version=$1
# ------------------------------------------
if [[ "$new_version" == "" ]]; then
  echo "Give a version to set (e.g., 1.0.0)"
  exit 1
fi

find . -name pom.xml | xargs gsed -i "s/<version>[0-9]\+\.[0-9]\+\.[^S]\+<\/version>/<version>${new_version}<\/version>/g"
find . -name pom.xml | xargs gsed -i "s/<version>[0-9]\+\.[0-9]\+\.[^S]\+-SNAPSHOT<\/version>/<version>${new_version}<\/version>/g"

echo "package notion.api

object Metadata {
    const val VERSION: String = \"$new_version\"

    fun isLibraryMaintainerMode(): Boolean {
        val value = System.getenv(\"NOTION_SDK_JVM_LIBRARY_MAINTAINER_MODE\")
        return value != null && ((value == \"1\") or (value == \"true\"))
    }
}
" > core/src/main/kotlin/notion/api/Metadata.kt

git diff
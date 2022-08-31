<!-- markdownlint-disable -->
<div align="center">
    <h1>Notion SDK for Any JVM Language</h1>
    <p>
        <b>A simple and easy to use client for the <a href="https://developers.notion.com/">Notion API</a></b>
    </p>
    <br/>
</div>
<!-- markdownlint-enable -->

[![Maven Central](https://img.shields.io/maven-central/v/com.github.seratch/notion-sdk-jvm-core.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.github.seratch%22%20AND%20a:%22notion-sdk-jvm-core%22)
[![CI Build](https://github.com/seratch/notion-sdk-jvm/actions/workflows/ci-build.yml/badge.svg)](https://github.com/seratch/notion-sdk-jvm/actions/workflows/ci-build.yml)

Here is a [Notion API](https://developers.notion.com/) SDK for any JVM language users :wave: 

This project aims to provide a Notion API client for any JVM language developers without hurdles. To realize the goal, its code is written in Kotlin with a nice consideration for Java compatibility.

This SDK works on [Android runtime](https://developer.android.com/) and any distributions based on [OpenJDK](https://openjdk.java.net/). With regard to programming languages, this project provides out-of-the-box supports for Java (of course!) and [Kotlin](https://kotlinlang.org/). We don't have nice wrappers for some other JVM languages such as [Scala](https://www.scala-lang.org/), [Groovy](https://groovy-lang.org/), and [Clojure](https://clojure.org/), but your code using this library should work in the languages too.

### Getting Started

You can start using this library just by adding `notion-sdk-jvm-core` dependency to your project.

For Gradle users:

```gradle
ext.notionSdkVersion = "1.7.1"
dependencies {
  // This dependency is at least required
  implementation("com.github.seratch:notion-sdk-jvm-core:${notionSdkVersion}")
}
```

For Maven users:

```xml
<properties>
  <notion-sdk.version>1.7.1</notion-sdk.version>
</properties>

<dependencies>
  <dependency>
    <groupId>com.github.seratch</groupId>
    <artifactId>notion-sdk-jvm-core</artifactId>
    <version>${notion-sdk.version}</version>
  </dependency>
</dependencies>
```

As this library is in Kotlin, using in the same language is the smoothest :) Let's start with the following code, which manipulates Notion pages :wave:

```kotlin
import notion.api.v1.NotionClient
import notion.api.v1.model.common.ObjectType
import notion.api.v1.model.pages.PageParent
import notion.api.v1.request.search.SearchRequest
import notion.api.v1.model.pages.PageProperty as prop

fun main() {
  val client = NotionClient(token = System.getenv("NOTION_TOKEN"))
  client.use {
    // Find the "Test Database" from the list
    val database = client.search(
      query = "Test Database",
      filter = SearchRequest.SearchFilter("database", property = "object")
    ).results.find { it.asDatabase().properties.containsKey("Severity") }?.asDatabase()
      ?: throw IllegalStateException("Create a database named 'Test Database' and invite this app's user!")

    // All the options for "Severity" property (select type)
    val severityOptions = database.properties?.get("Severity")?.select?.options
    // All the options for "Tags" property (multi_select type)
    val tagOptions = database.properties?.get("Tags")?.multiSelect?.options
    // The user object for "Assignee" property (people type)
    val assignee = client.listUsers().results[0] // just picking the first user up

    // Create a new page in the database
    val newPage = client.createPage(
      // Use the "Test Database" as this page's parent
      parent = PageParent.database(database.id),
      // Set values to the page's properties
      // (these must be pre-defined before this API call)
      properties = mapOf(
        "Title" to prop(title = listOf(prop.RichText(text = prop.RichText.Text(content = "Fix a bug")))),
        "Severity" to prop(select = severityOptions?.find { it.name == "High" }),
        "Tags" to prop(multiSelect = tagOptions),
        "Due" to prop(date = prop.Date(start = "2021-05-13", end = "2021-12-31")),
        "Velocity Points" to prop(number = 3),
        "Assignee" to prop(people = listOf(assignee)),
        "Done" to prop(checkbox = true),
        "Link" to prop(url = "https://www.example.com"),
        "Contact" to prop(email = "foo@example.com"),
      )
    )
    val severityId = newPage.properties["Severity"]!!.id

    // Update properties in the page
    val updatedPage = client.updatePage(
        pageId = newPage.id,
        // Update only "Severity" property
        properties = mapOf(
          severityId to prop(select = severityOptions?.find { it.name == "Medium" }),
        )
      )

    // Fetch the latest data of the page
    val retrievedPage = client.retrievePage(newPage.id)
  }
}
```

#### Using in Java

Even when you use this SDK in Java and other languages, all the classes/methods should be accessible. If you find issues, please let us know the issue in [this project's issue tracker](https://github.com/seratch/notion-sdk-jvm/issues).

```java
package integration_tests;

import notion.api.v1.NotionClient;
import notion.api.v1.model.search.SearchResults;
import org.junit.Assert;

public class Readme {
  public static void main(String[] args) {
    NotionClient client = new NotionClient(System.getenv("NOTION_TOKEN"));
    try {
      SearchResults results = client.search("Test Database");
      Assert.assertNotNull(results);
    } finally {
      client.close();
    }
  }
}
```

### OAuth Support

The Notion app installation via the OAuth flow is also supported. To learn how to implement it, please check [an example app built with Ktor web framework](https://github.com/seratch/notion-sdk-jvm/blob/main/core/src/test/kotlin/OAuthAppExample.kt) in the core library project.

### Plugins

By default, the `NotionClient` utilizes only JDK's `java.net.HttpURLConnection` and [Gson](https://github.com/google/gson) library for JSON serialization.

For HTTP communications and logging, you can easily switch to other implementations.

#### Pluggable HTTP Client

As some may know, `java.net.HttpURLConnection` does not support PATCH request method :cry:. Thus, the default `httpClient` has to make an "illegal reflective access" to overcome the limitation for perfoming PATCH method requests (see [this class](https://github.com/seratch/notion-sdk-jvm/blob/main/core/src/main/kotlin/notion/api/v1/http/HttpUrlConnPatchMethodWorkaround.kt) for details). 

If you use PATH method API calls such as [`PATCH https://api.notion.com/v1/pages/{page_id}`](https://developers.notion.com/reference/patch-page), we recommend other `httpClient` implementations listed below. If you don't use PATCH method APIs at all and don't want to add any extra dependencies, the default `httpClient` works fine for you.

* [`java.net.HttpClient`](https://docs.oracle.com/en/java/javase/11/docs/api/java.net.http/java/net/http/HttpClient.html) in JDK 11+
* `okhttp3.OkHttpClient` in [OkHttp](https://square.github.io/okhttp/) 5.x (still alpha)
* `okhttp3.OkHttpClient` in [OkHttp](https://square.github.io/okhttp/) 4.x
* `okhttp3.OkHttpClient` in [OkHttp](https://square.github.io/okhttp/) 3.x

```gradle
// Add this if you use java.net.http.HttpClient in JDK 11+
// Please note that this module does not work on Android runtime
implementation("com.github.seratch:notion-sdk-jvm-httpclient:${notionSdkVersion}")

// Add this if you use OkHttp 5.x (still alpha)
// If you have other dependencies relying on okhttp 5.x (e.g., Retrofit)
implementation("com.github.seratch:notion-sdk-jvm-okhttp5:${notionSdkVersion}")

// Add this if you use OkHttp 4.x
// Although the package name is `okhttp3`, the latest version is 4.x
implementation("com.github.seratch:notion-sdk-jvm-okhttp4:${notionSdkVersion}")

// Add this if you use OkHttp 3.x
// If you have other dependencies relying on okhttp 3.x (e.g., Retrofit)
implementation("com.github.seratch:notion-sdk-jvm-okhttp3:${notionSdkVersion}")
```

You can switch the `httpClient` in either of the following ways:

```kotlin
import notion.api.v1.NotionClient
import notion.api.v1.http.JavaNetHttpClient

val client = NotionClient(
    token = System.getenv("NOTION_TOKEN"),
    httpClient = JavaNetHttpClient(),
)
```

or

```kotlin
import notion.api.v1.NotionClient
import notion.api.v1.http.OkHttp3Client

val client = NotionClient(token = System.getenv("NOTION_TOKEN"))
client.httpClient = OkHttp3Client()
```

#### Pluggable Logging

You can change the `logger` property of a `NotionClient` instances Currently, this library supports its own stdout logger (default), `java.util.logging`, and slf4j-api based ones. Here are the steps to switch to an slf4j-api logger. Add the following optional module along with your favorite implementation (e.g., logback-classic, slf4j-simple).

```gradle
implementation("com.github.seratch:notion-sdk-jvm-slf4j:${notionSdkVersion}") // slf4j-api 1.7
implementation("org.slf4j:slf4j-simple:1.7.36")
```

Now you can switch to your slf4j logger. As with the `httpClient` example, you can use the setter method too.

```kotlin
import notion.api.v1.NotionClient
import notion.api.v1.http.JavaNetHttpClient
import notion.api.v1.logging.Slf4jLogger

// for slf4j-simple
System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "debug")

val client = NotionClient(
    token = System.getenv("NOTION_TOKEN"),
    httpClient = JavaNetHttpClient(),
    logger = Slf4jLogger(),
)
```

If you desire to use slf4j-api v2 instead, you can use the module for the major version as below:

```gradle
implementation("com.github.seratch:notion-sdk-jvm-slf4j2:${notionSdkVersion}") // slf4j-api 2.0
implementation("org.slf4j:slf4j-simple:2.0.0")
```

#### Why isn't JSON serialization pluggable?

We don't support other JSON libraries yet. There are two reasons:

##### Necessity of polymorphic serializers for list objects

In the early development stage of this SDK, we started with [kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization). It worked well except for the Search API responses. The `results` returned by the API requires polymorphic serializers for `properties: List<DatabaseProperty | PageProperty>` (this is a pseudo-code illustrating the property is a list of union type). We could not find a way to handle the pattern with the library at that time.

##### Easily enabling camelCased property names

We know a few novel Kotlin libraries do not support the conversions between snake_cased keys and camelCased keys. Although we do respect the opinion and see the benefit, we still prefer consistent field naming in the Java world. This is another major reason why we did not go with either of kotlinx.serialization and Moshi.

### Supported Java Runtimes

* OpenJDK 8 or higher
* All Android runtime versions supported by Kotlin 1.5

As `notion-sdk-jvm-httpclient` utilizes `java.net.http.HttpClient`, the module works with JDK 11 and higher versions.

### License

The MIT License

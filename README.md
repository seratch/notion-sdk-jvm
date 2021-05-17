## Unofficial Notion SDK for Any JVM Language

[![Maven Central](https://img.shields.io/maven-central/v/com.github.seratch/notion-sdk-jvm-core.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.github.seratch%22%20AND%20a:%22notion-sdk-jvm-core%22)
[![CI Build](https://github.com/seratch/notion-sdk-jvm/actions/workflows/ci-build.yml/badge.svg)](https://github.com/seratch/notion-sdk-jvm/actions/workflows/ci-build.yml)

Here is an **unofficial** Notion SDK for any JVM language users!

This project aims to provide a tool for any JVM language developers without any hurdles. To realize the goal, its code is written in Kotlin with a nice consideration for Java compatibility.

### Getting Started

You can start using this library just by adding `notion-sdk-jvm-core` dependency to your project!

For Gradle users:

```gradle
notionSdkVersion = "0.1.3"
// This dependency is at least required
implementation("com.github.seratch:notion-sdk-jvm-core:${notionSdkVersion}")
```

For Maven users:

```xml
<properties>
  <notion-sdk.version>0.1.3</notion-sdk.version>
</properties>

<dependencies>
  <dependency>
    <groupId>com.github.seratch</groupId>
    <artifactId>notion-sdk-jvm-core</artifactId>
    <version>${notion-sdk.version}</version>
  </dependency>
</dependencies>
```

As already mentioned, this library is written in Kotlin. Using in the same language would be the smoothest :) Let's start with a simple code manipulating Notion pages :wave:

```kotlin
import notion.api.v1.NotionClient
import notion.api.v1.model.pages.PageProperty
import notion.api.v1.model.pages.PageProperty.*
import notion.api.v1.request.pages.CreatePageRequest
import notion.api.v1.request.pages.UpdatePagePropertiesRequest
import java.lang.IllegalStateException

typealias prop = PageProperty

fun main() {
  val client = NotionClient(token = System.getenv("NOTION_TOKEN"))
  client.use {

    // Look up all databases that this app can access
    val databases = client.listDatabases()
    // Find the "Test Database" from the list
    val database = databases.results.find { it.title?.first()?.plainText == "Test Database" }
      ?: throw IllegalStateException("Create a database named 'Test Database' and invite this app's user!")

    // All the options for "Severity" property (select type)
    val severityOptions = database.properties?.get("Severity")?.select?.options
    // All the options for "Tags" property (multi_select type)
    val tagOptions = database.properties?.get("Tags")?.multiSelect?.options
    // The user object for "Assignee" property (people type)
    val assignee = client.listUsers().results[0] // just picking the first user up

    // Create a new page in the database
    val newPage = client.createPage(CreatePageRequest(
      // Use the "Test Database" as this page's parent
      parent = CreatePageRequest.Parent(type = "database", databaseId = database.id),
      // Set values to the page's properties (these must be pre-defined before this API call)
      properties = mapOf(
        "Title" to prop(
          title = listOf(RichText(
            text = RichText.Text(content = "Fix a bug")
          ))
        ),
        "Severity" to prop(select = severityOptions?.find { it.name == "High" }),
        "Tags" to prop(multiSelect = tagOptions),
        "Due" to prop(date = Date(start = "2021-05-13", end = "2021-12-31")),
        "Velocity Points" to prop(number = 3),
        "Assignee" to prop(people = listOf(assignee)),
        "Done" to prop(checkbox = true),
        "Link" to prop(url = "https://www.example.com"),
        "Contact" to prop(email = "foo@example.com"),
      )
    ))

    // Update properties in the page
    val updatedPage = client.updatePageProperties(UpdatePagePropertiesRequest(
      pageId = newPage.id,
      // Update only "Severity" property
      properties = mapOf(
        "Severity" to prop(select = severityOptions?.find { it.name == "Medium" }),
      )
    ))

    // Fetch the latest data of the page
    val retrievedPage = client.retrievePage(newPage.id)
  }
}
```

If you want to use Java, all the classes/methods should be accessible in Java and other JVM languages. If not, please let us know the issue in this project's issue tracker!

```java
import notion.api.v1.NotionClient;
import notion.api.v1.model.databases.Databases;

public class Readme {
  public static void main(String[] args) {
    try (NotionClient client = new NotionClient(System.getenv("NOTION_TOKEN"))) {
      Databases databases = client.listDatabases();
    }
  }
}
```

If you are looking for further examples, check the core project's test suites!

### Plugins

By default, the `NotionClient` utilizes only JDK's `java.net.HttpURLConnection` and [Gson](https://github.com/google/gson) library for JSON serialization.

For HTTP communications and logging, you can easily switch to other implementations.

#### Pluggable HTTP Client

As you may know, `HttpURLConnection` does not support PATCH request method, the default implementation of `httpClient` in this library does an "illegal reflective access" to remove the limitation (see `notion.api.v1.http.HttpUrlConnPatchMethodWorkaround`). For this reason, we recommend using other HTTP client libraries for production apps. Currently, we support the following libraries and modules:

* [`java.net.HttpClient`](https://docs.oracle.com/en/java/javase/11/docs/api/java.net.http/java/net/http/HttpClient.html) in JDK 11+
* `okhttp3.OkHttpClient` in [OkHttp](https://square.github.io/okhttp/) 4.x
* `okhttp3.OkHttpClient` in [OkHttp](https://square.github.io/okhttp/) 3.x

```gradle
// Add this if you use java.net.http.HttpClient in JDK 11+
// This module is not yet available in Android runtimes
implementation("com.github.seratch:notion-sdk-jvm-httpclient:${notionSdkVersion}")

// Add this if you use OkHttp 4.x
// Although the package name is `okhttp3`, the latest version is 4.x
implementation("com.github.seratch:notion-sdk-jvm-okhttp4:${notionSdkVersion}")

// Add this if you use OkHttp 3.x
// Retrofit etc. depends on this major version. If your app has such dependencies, using this is the way to go
implementation("com.github.seratch:notion-sdk-jvm-okhttp3:${notionSdkVersion}")
```

You can switch the `httpClient` in the following ways:

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

```gradle
implementation("com.github.seratch:notion-sdk-jvm-slf4j:${notionSdkVersion}") // slf4j-api 1.7
```

You can change the `logger` property of `NotionClient` instances.

```kotlin
import notion.api.v1.NotionClient
import notion.api.v1.http.JavaNetHttpClient
import notion.api.v1.logging.Slf4jLogger

val client = NotionClient(
  token = System.getenv("NOTION_TOKEN"),
  httpClient = JavaNetHttpClient(),
  logger = Slf4jLogger(),
)
```

#### Why is not JSON serialization pluggable?

As of today, we don't support other JSON libraries yet. There are several reasons:

##### Necessity of polymorphic serializers for list objects

In the early development stage of this SDK, we started with [kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization). It worked well except for the Search API responses. However, the `results` in the Search API responses requires polymorphic serializers for `properties: List<DatabaseProperty | PageProperty>` (this is a pseudo code illustrating the property is a list of union type). The author (@seratch) was not able to find a way to handle the pattern with the library.

##### The author prefers camelCased property names

I (@seratch) know a few novel libraries intentionally do not support the conversions between snake_cased keys and camelCased keys. I do understand the opinion, but I still prefer consistent field naming in the Java world. This is the main reason why we didn't go with Moshi.

### Supported Java Runtimes

* OpenJDK 8 or higher
* All Android runtime versions supported by Kotlin 1.5

As `notion-sdk-jvm-httpclient` utilizes `java.net.http.HttpClient`, the module works with JDK 11 and higher versions.

### License

The MIT License

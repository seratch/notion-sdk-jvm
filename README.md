## Unofficial Notion SDK for any JVM Language

[![Maven Central](https://img.shields.io/maven-central/v/com.github.seratch/notion-sdk-jvm-core.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.github.seratch%22%20AND%20a:%22notion-sdk-jvm-core%22)

Here is an **unofficial** Notion SDK for any JVM language users!

This project aims to provide a tool for any JVM language developers without any hurdles. To realize the goal, its code is written in Kotlin with a nice consideration for Java compatibility.

### Getting Started

You can start using this library just by adding `notion-sdk-jvm-core` dependency to your project!

For Gradle users:

```gradle
notionSdkVersion = "0.1.1"
// This dependency is at least required
implementation("com.github.seratch:notion-sdk-jvm-core:${notionSdkVersion}")
```

For Maven users:

```xml
<properties>
  <notion-sdk.version>0.1.1</notion-sdk.version>
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
import notion.api.v1.request.pages.CreatePageRequest
import notion.api.v1.request.pages.UpdatePagePropertiesRequest
import java.lang.IllegalStateException

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
    val tagOptions = database.properties?.get("Tags")?.multiSelect?.options

    // Create a new page in the database
    val newPage = client.createPage(CreatePageRequest(
      parent = CreatePageRequest.Parent(type = "database", databaseId = database.id),
      properties = mapOf(
        "Title" to PageProperty(
          title = listOf(PageProperty.RichText(
            text = PageProperty.RichText.Text(content = "Fix a bug")
          ))
        ),
        "Severity" to PageProperty(select = severityOptions?.find { it.name == "High" }),
        "Tags" to PageProperty(multiSelect = tagOptions),
        "Due" to PageProperty(
          date = PageProperty.Date(start = "2021-05-13", end = "2021-12-31")
        ),
        "Velocity Points" to PageProperty(number = 123.5),
        "Assignee" to PageProperty(people = listOf(client.listUsers().results[0])),
        "Done" to PageProperty(checkbox = true),
        "Link" to PageProperty(url = "https://www.example.com"),
        "Contact" to PageProperty(email = "foo@example.com"),
      )
    ))

    // Update properties in the page
    val updatedPage = client.updatePageProperties(UpdatePagePropertiesRequest(
      pageId = newPage.id,
      properties = mapOf(
        "Severity" to PageProperty(select = severityOptions?.find { it.name == "Medium" }),
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

As you may know, `HttpURLConnection` does not support PATCH request method, the implementation does an "illegal reflective access" in `notion.api.v1.http.UrlConnPatchMethodWorkaround`. We recommend using your convenient HTTP client library for prodcution apps. Currently, we support [OkHttp](https://square.github.io/okhttp/) 3.x, [OkHttp](https://square.github.io/okhttp/) 4.x, and JDK's [`java.net.HttpClient`](https://docs.oracle.com/en/java/javase/11/docs/api/java.net.http/java/net/http/HttpClient.html).

```gradle
implementation("com.github.seratch:notion-sdk-jvm-okhttp3:${notionSdkVersion}") // OkHttp 3.x
implementation("com.github.seratch:notion-sdk-jvm-okhttp4:${notionSdkVersion}") // OkHttp 4.x
implementation("com.github.seratch:notion-sdk-jvm-httpclient:${notionSdkVersion}") // java.net.http.HttpClient in JDK 11+
```

You can switch the `httpClient` in the following ways:

```kotlin
import notion.api.v1.NotionClient
import notion.api.v1.http.JavaHttpClient

val client = NotionClient(
  token = System.getenv("NOTION_TOKEN"),
  httpClient = JavaHttpClient(),
)
```

or

```kotlin
import notion.api.v1.NotionClient
import notion.api.v1.http.Okhttp3Client

val client = NotionClient(token = System.getenv("NOTION_TOKEN"))
client.httpClient = Okhttp3Client()
```

#### Pluggable Logging

```gradle
implementation("com.github.seratch:notion-sdk-jvm-slf4j:${notionSdkVersion}") // slf4j-api 1.7
```

You can change the `logger` property of `NotionClient` instances.

```kotlin

import notion.api.v1.NotionClient
import notion.api.v1.http.JavaHttpClient
import notion.api.v1.logging.Slf4jNotionLogger

val client = NotionClient(
  token = System.getenv("NOTION_TOKEN"),
  httpClient = JavaHttpClient(),
  logger = Slf4jNotionLogger(),
)
```

#### Why is not JSON serialization pluggable?

As of today, we don't support other JSON libraries yet. There are several reasons:

##### Necessity of polymorphic serializers for list objects

In the early development stage of this SDK, we started with [kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization). It worked well except for the Search API responses. However, the `results` in the Search API responses requires polymorphic serializers for `properties: List[DatabaseProperty | PageProperty]`. The author (@seratch) was not able to find a way to handle the pattern with the library.

##### The author prefers camelCased property names

I (@seratch) know a few novel libraries intentionally do not support the conversions between snake_cased keys and camelCased keys. I do understand the opinion, but I still prefer consistent field naming in the Java world. This is the main reason why we didn't go with Moshi.

### Supported Java Runtimes

* OpenJDK 8 or higher
* All Android runtime versions supported by Kotlin 1.5

As `notion-sdk-jvm-httpclient` utilizes `java.net.http.HttpClient`, the module works with JDK 11 and higher versions.

### License

The MIT License

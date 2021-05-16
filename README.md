## Unofficial Notion SDK for any JVM Language

Here is an **unofficial** Notion SDK for any JVM language users!

This project aims to provide a tool for any JVM language developers without any hurdles. To realize the goal, its code is written in Kotlin with a nice consideration for Java compatibility.

### Getting Started

You can start using this library just by adding `notion-sdk-jvm-core` dependency to your project!

For Gradle users:

```gradle
notionSdkVersion = "0.1.0"
// This dependency is at least required
implementation("com.github.seratch:notion-sdk-jvm-core:${notionSdkVersion}")
```

For Maven users:

```xml
<properties>
  <notion-sdk.version>0.1.0</notion-sdk.version>
</properties>

<dependencies>
  <dependency>
    <groupId>com.github.seratch</groupId>
    <artifactId>notion-sdk-jvm-core</artifactId>
    <version>${notion-sdk.version}</version>
  </dependency>
</dependencies>
```

As already mentioned, this library is written in Kotlin. Using in the same language would be the smoothest :) Let's start with a simple code manupulating Notion pages :wave:

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
    val options = database.properties?.get("Severity")?.select?.options

    // Create a new page in the database
    val newPage = client.createPage(CreatePageRequest(
      parent = CreatePageRequest.Parent(type = "database", databaseId = database.id),
      properties = mapOf(
        "Title" to PageProperty(
          title = listOf(PageProperty.RichText(
            text = PageProperty.RichText.Text(content = "Fix a bug")
          ))
        ),
        "Severity" to PageProperty(select = options?.find { it.name == "High" }),
      )
    ))

    // Update properties in the page
    val updatedPage = client.updatePageProperties(UpdatePagePropertiesRequest(
      pageId = newPage.id,
      properties = mapOf(
        "Severity" to PageProperty(select = options?.find { it.name == "Medium" }),
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

* Necessity of polymorphic serializers for list objects

We started with [kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization) first and it worked well except for the Search API responses. The `results` in the Search API responses requires polymorphic serializers for `properties: List[DatabaseProperty | PageProperty]`. @seratch was not able to find a way to handle the pattern with the library.

* @seratch still prefers camelCased property names

I know a few novel libraries intentionally do not support the conversions between snake_cased keys and camelCased keys. I do understand the opinion, but I still prefer consistent field naming in the Java world.

### Supported Java Runtimes

* OpenJDK 8 or higher
* All Android runtime versions supported by Kotlin 1.5

As `notion-sdk-jvm-httpclient` utilizes `java.net.http.HttpClient`, the module works with JDK 11 and higher versions.

### License

The MIT License

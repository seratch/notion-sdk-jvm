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

Here is an [Notion API](https://developers.notion.com/) SDK for any JVM language users :wave: 

This project aims to provide a Notion API client for any JVM language developers without hurdles. To realize the goal, its code is written in Kotlin with a nice consideration for Java compatibility.

This SDK works on [Android runtime](https://developer.android.com/) and any distributions based on [OpenJDK](https://openjdk.java.net/). With regard to programming languages, this project provides out-of-the-box supports for Java (of course!), [Kotlin](https://kotlinlang.org/), and the novel [Scala 3](https://docs.scala-lang.org/scala3/)! We don't have nice wrappers for some other JVM lanaguages such as [Groovy](https://groovy-lang.org/) and [Clojure](https://clojure.org/), but your code using this library should work in the lanaguges too.

### Getting Started

You can start using this library just by adding `notion-sdk-jvm-core` dependency to your project.

For Gradle users:

```gradle
ext.notionSdkVersion = "0.1.9"
dependencies {
  // This dependency is at least required
  implementation("com.github.seratch:notion-sdk-jvm-core:${notionSdkVersion}")
}
```

For Maven users:

```xml
<properties>
  <notion-sdk.version>0.1.9</notion-sdk.version>
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
import notion.api.v1.model.pages.PageParent
import notion.api.v1.model.pages.PageProperty as prop

fun main() {
    val client = NotionClient(token = System.getenv("NOTION_TOKEN"))
    client.use {

        // Look up all databases that this app can access
        val databases = client.listDatabases()
        // Find the "Test Database" from the list
        val database = databases.results.find { it.title.any { t -> t.plainText.contains("Test Database") } }
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

        // Update properties in the page
        val updatedPage =
            client.updatePageProperties(
                pageId = newPage.id,
                // Update only "Severity" property
                properties = mapOf(
                    "Severity" to prop(select = severityOptions?.find { it.name == "Medium" }),
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

#### Scala 3 Support

Although many classes are still Java/Kotlin objects, you can seamlessly use this SDK in [Scala 3](https://docs.scala-lang.org/scala3/) too! Here is a simple `build.sbt` example:

```sbt
val notionSdkVersion = "0.1.9"

lazy val root = project
  .in(file("."))
  .settings(
    scalaVersion := "3.0.0",
    libraryDependencies ++= Seq(
      "com.github.seratch" % "notion-sdk-jvm-scala3" % notionSdkVersion,
      "com.github.seratch" % "notion-sdk-jvm-httpclient" % notionSdkVersion,
    )
  )
```

Save the following source code as `Main.scala`. You can run the app by hitting `sbt run`.

```scala
import notion.api.v1.ScalaNotionClient
import notion.api.v1.http.JavaNetHttpClient
import notion.api.v1.model.common.PropertyType
import notion.api.v1.model.databases.query.filter.PropertyFilter
import notion.api.v1.model.databases.query.filter.condition.TextFilter

import scala.jdk.CollectionConverters._

@main def example: Unit = {

  val client = ScalaNotionClient(
    token = System.getenv("NOTION_TOKEN"),
    httpClient = new JavaNetHttpClient()
  )

  val users = client.listUsers(pageSize = 2)

  val databaseId = client
    .listDatabases()
    .getResults
    .asScala
    .find(_.getTitle.get(0).getPlainText == "Test Database")
    .get
    .getId

  val queryResult = client.queryDatabase(
    databaseId = databaseId,
    filter = {
      val filter = new PropertyFilter()
      filter.setProperty(PropertyType.Title)
      filter.setTitle {
        val title = new TextFilter()
        title.setContains("bug")
        title
      }
      filter
    },
    pageSize = 3
  )
}
```

### Plugins

By default, the `NotionClient` utilizes only JDK's `java.net.HttpURLConnection` and [Gson](https://github.com/google/gson) library for JSON serialization.

For HTTP communications and logging, you can easily switch to other implementations.

#### Pluggable HTTP Client

As some may know, `java.net.HttpURLConnection` does not support PATCH request method :cry:. Thus, the default `httpClient` has to make an "illegal reflective access" to overcome the limitation for perfoming PATCH method requests (see [this class](https://github.com/seratch/notion-sdk-jvm/blob/main/core/src/main/kotlin/notion/api/v1/http/HttpUrlConnPatchMethodWorkaround.kt) for details). 

If you use PATH method API calls such as [`PATCH https://api.notion.com/v1/pages/{page_id}`](https://developers.notion.com/reference/patch-page), we recommend other `httpClient` implementations listed below. If you don't use PATCH method APIs at all and don't want to add any extra dependencies, the default `httpClient` works fine for you.

* [`java.net.HttpClient`](https://docs.oracle.com/en/java/javase/11/docs/api/java.net.http/java/net/http/HttpClient.html) in JDK 11+
* `okhttp3.OkHttpClient` in [OkHttp](https://square.github.io/okhttp/) 4.x
* `okhttp3.OkHttpClient` in [OkHttp](https://square.github.io/okhttp/) 3.x

```gradle
// Add this if you use java.net.http.HttpClient in JDK 11+
// Please note that this module does not work on Android runtime
implementation("com.github.seratch:notion-sdk-jvm-httpclient:${notionSdkVersion}")

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

You can change the `logger` property of a `NotionClient` instances Currently, this libarary supports its own stdout logger (default), `java.util.logging`, and slf4j-api based ones. Here are the steps to switch to an slf4j-api logger. Add the following optional module along with your favorite implementation (e.g., logback-classic, slf4j-simple).

```gradle
implementation("com.github.seratch:notion-sdk-jvm-slf4j:${notionSdkVersion}") // slf4j-api 1.7
implementation("org.slf4j:slf4j-simple:1.7.30")
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

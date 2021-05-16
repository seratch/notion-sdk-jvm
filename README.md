## Unofficial Notion SDK for JVM Languages

Here is an unofficial Notion SDK for JVM language users!
This project aims to provide a tool for any JVM language developers without any hurdles. To realize the goal, its code is 100% written in Kotlin with a nice consideration for Java compatibility.

### Supported Java Runtimes

* OpenJDK 8 or higher
* All Android runtime versions supported by Kotlin 1.5

### Project Status

This project is still under development. Using this library in your serious projects is not yet recommended.

### Kotlin Example

```kotlin
import notion.api.v1.NotionClient
import notion.api.v1.http.Okhttp3Client
import notion.api.v1.logging.Slf4jNotionLogger

fun main() {
  NotionClient(token = System.getenv("NOTION_TOKEN")).use { client ->
    client.logger = Slf4jNotionLogger()
    client.httpClient = Okhttp3Client()

    val searchResults = client.search("Getting Started")
    println(searchResults)
  }
}
```

### Java Example

```java
import notion.api.v1.NotionClient;
import notion.api.v1.http.Okhttp3Client;
import notion.api.v1.logging.Slf4jNotionLogger;
import notion.api.v1.model.database.Databases;

try (NotionClient client = new NotionClient(System.getenv("NOTION_TOKEN"))) {
  client.setHttpClient(new Okhttp3Client());
  client.setLogger(new Slf4jNotionLogger());

  Databases databases = client.listDatabases();
  System.out.println(databases);
}
```

### License

The MIT License

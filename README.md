## Unofficial Notion SDK for JVM Languages

Here is an unofficial Notion SDK for JVM language users!
This project aims to provide a tool for any JVM language developers without any hurdles.
To realize the goal, its code is 100% written in Kotlin with a nice consideration for Java compatibility.

### Supported Java Runtimes

* OpenJDK 8 or higher
* All Android runtime versions supported by Kotlin 1.5

### Project Status

That being said, this project is still under development.
Using this library in your serious projects is not yet recommended.

### Kotlin Example

```kotlin
import notion.api.v1.NotionClient

fun main() {
  val client = NotionClient(token = System.getenv("NOTION_TOKEN"))
  val users = client.listUsers()
  users.results.forEach { user ->
    println(client.findUser(user.id))
  }
  val databases = client.listDatabases()
  databases.results.forEach { database ->
    println(client.findDatabase(database.id))
  }

  val searchResults = client.search("Getting Started")
  println(searchResults)
}
```

### Java Example

```java
import notion.api.v1.NotionClient;
import notion.api.v1.logging.impl.Slf4jNotionLogger;

public class Example {
  public static void main(String[] args) throws Exception {
    System.setProperty("org.slf4j.simpleLogger.log.notion-sdk-jvm", "debug");

    var client = new NotionClient(System.getenv("NOTION_TOKEN"));

    // switching logger to an slf4j-api implementation
    var logger = new Slf4jNotionLogger();
    client.setLogger(logger);

    var users = client.listUsers();
    logger.info("users: " + users);
    for (var user : users.getResults()) {
      logger.info("user: " + client.findUser(user.getId()));
    }
    var databases = client.listDatabases();
    logger.info("databases: " + databases);
    for (var database : databases.getResults()) {
      client.findDatabase(database.getId());
    }
    var results = client.search("Getting Started");
    logger.info("search results: " + results);
  }
}
```

### License

The MIT License
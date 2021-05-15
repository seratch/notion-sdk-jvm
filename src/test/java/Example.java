import notion.api.v1.NotionClient;
import notion.api.v1.logging.impl.Slf4jNotionLogger;

public class Example {
    public static void main(String[] args) throws Exception {
        System.setProperty("org.slf4j.simpleLogger.log.notion-sdk-jvm", "debug");

        var client = new NotionClient(System.getenv("NOTION_TOKEN"));

        var logger = new Slf4jNotionLogger();
        client.setLogger(logger);

        var users = client.listUsers();
        logger.info("users: " + users);
        for (var user : users.getResults()) {
            logger.info("user: " + client.retrieveUser(user.getId()));
        }
        var databases = client.listDatabases();
        logger.info("databases: " + databases);
        for (var database : databases.getResults()) {
            client.retrieveDatabase(database.getId());
        }
        var results = client.search("Getting Started");
        logger.info("search results: " + results);
    }
}

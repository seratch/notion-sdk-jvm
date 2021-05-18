package integration_tests;

import notion.api.v1.NotionClient;
import notion.api.v1.logging.Slf4jLogger;
import notion.api.v1.model.databases.Databases;
import org.junit.Assert;

public class SimpleJavaTest {

    public static void main(String[] args) {
        NotionClient client = new NotionClient(System.getenv("NOTION_TOKEN"));
        client.setLogger(new Slf4jLogger());
        try {
            Databases databases = client.listDatabases();
            Assert.assertNotNull(databases);
        } finally {
            client.close();
        }
    }
}

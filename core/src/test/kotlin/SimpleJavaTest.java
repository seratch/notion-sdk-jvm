import notion.api.v1.NotionClient;
import notion.api.v1.model.databases.Databases;
import org.junit.Assert;

public class SimpleJavaTest {

    public static void main(String[] args) {
        try (NotionClient client = new NotionClient(System.getenv("NOTION_TOKEN"))) {
            Databases databases = client.listDatabases();
            Assert.assertNotNull(databases);
        }
    }
}

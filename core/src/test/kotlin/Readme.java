import notion.api.v1.NotionClient;
import notion.api.v1.model.databases.Databases;

public class Readme {
    public static void main(String[] args) {
        try (NotionClient client = new NotionClient(System.getenv("NOTION_TOKEN"))) {
            Databases databases = client.listDatabases();
        }
    }
}

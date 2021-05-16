import notion.api.v1.NotionClient;
import notion.api.v1.http.Okhttp3Client;
import notion.api.v1.logging.Slf4jNotionLogger;
import notion.api.v1.model.database.Databases;

public class Example {

    public static void main(String[] args) {
        try (NotionClient client = new NotionClient(System.getenv("NOTION_TOKEN"))) {
            client.setHttpClient(new Okhttp3Client());
            client.setLogger(new Slf4jNotionLogger());

            Databases databases = client.listDatabases();
            System.out.println(databases);
        }
    }
}

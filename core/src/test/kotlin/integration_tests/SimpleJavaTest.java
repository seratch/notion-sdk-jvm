package integration_tests;

import notion.api.v1.NotionClient;
import notion.api.v1.model.search.SearchResults;
import org.junit.Assert;

public class SimpleJavaTest {

    public static void main(String[] args) {
        NotionClient client = new NotionClient(System.getenv("NOTION_TOKEN"));
        try {
            SearchResults results = client.search("Test Database");
            Assert.assertNotNull(results);
        } finally {
            client.close();
        }
    }
}

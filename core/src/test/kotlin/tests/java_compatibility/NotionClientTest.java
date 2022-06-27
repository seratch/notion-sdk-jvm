package tests.java_compatibility;

import notion.api.v1.NotionClient;
import notion.api.v1.http.HttpUrlConnNotionHttpClient;
import notion.api.v1.logging.StdoutLogger;
import org.junit.Test;

public class NotionClientTest {

    @Test
    public void runInternalAppInitialization() {
        NotionClient client = new NotionClient("token");
        client.setLogger(new StdoutLogger());
        client.setHttpClient(new HttpUrlConnNotionHttpClient());
    }

    @Test
    public void runOAuthAppInitialization() {
        NotionClient client = new NotionClient("clientID", "clientSecret", "redirectUri");
        client.setLogger(new StdoutLogger());
        client.setHttpClient(new HttpUrlConnNotionHttpClient());
    }
}

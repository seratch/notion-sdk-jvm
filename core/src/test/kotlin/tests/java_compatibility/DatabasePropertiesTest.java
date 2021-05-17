package tests.java_compatibility;

import notion.api.v1.model.databases.DatabaseProperty;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class DatabasePropertiesTest {

    @Test
    public void minimumConstructor() {
        DatabaseProperty property = new DatabaseProperty("rich_text", "id-value");
        assertNotNull(property);
    }

}

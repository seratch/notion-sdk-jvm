package tests.java_compatibility;

import notion.api.v1.model.pages.PageParent;
import notion.api.v1.model.pages.PageParentType;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class PageParentTest {

    @Test
    public void creation() {
        PageParent parent = new PageParent(PageParentType.Database);
        parent.setDatabaseId("database-id");
        assertNotNull(parent);
    }
}

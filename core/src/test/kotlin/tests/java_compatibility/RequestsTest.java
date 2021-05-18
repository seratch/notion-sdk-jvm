package tests.java_compatibility;

import notion.api.v1.model.pages.PageParent;
import notion.api.v1.model.pages.PageParentType;
import notion.api.v1.request.blocks.AppendBlockChildrenRequest;
import notion.api.v1.request.blocks.RetrieveBlockChildrenRequest;
import notion.api.v1.request.databases.ListDatabasesRequest;
import notion.api.v1.request.databases.QueryDatabaseRequest;
import notion.api.v1.request.databases.RetrieveDatabaseRequest;
import notion.api.v1.request.pages.CreatePageRequest;
import notion.api.v1.request.pages.RetrievePageRequest;
import notion.api.v1.request.pages.UpdatePagePropertiesRequest;
import notion.api.v1.request.search.SearchRequest;
import notion.api.v1.request.users.RetrieveUserRequest;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertNotNull;

public class RequestsTest {

    @Test
    public void instantiateWithRequiredArgs() {
        assertNotNull(new AppendBlockChildrenRequest("database-id", Collections.emptyList()));

        assertNotNull(new RetrieveBlockChildrenRequest("database-id"));

        assertNotNull(new ListDatabasesRequest());

        assertNotNull(new QueryDatabaseRequest("database-id"));

        assertNotNull(new RetrieveDatabaseRequest("database-id"));

        assertNotNull(new CreatePageRequest(new PageParent(PageParentType.Database), Collections.emptyMap()));
        assertNotNull(new CreatePageRequest(PageParent.database("database-id"), Collections.emptyMap()));
        assertNotNull(new CreatePageRequest(PageParent.page("page-id"), Collections.emptyMap()));

        assertNotNull(new RetrievePageRequest("page-id"));

        assertNotNull(new UpdatePagePropertiesRequest("page-id", Collections.emptyMap()));

        assertNotNull(new SearchRequest("query"));
        assertNotNull(new SearchRequest("query", new SearchRequest.SearchFilter()));
        assertNotNull(new SearchRequest("query", new SearchRequest.SearchFilter(), new SearchRequest.SearchSort()));
        assertNotNull(new SearchRequest("query"));

        assertNotNull(new RetrieveUserRequest("user-id"));
    }
}

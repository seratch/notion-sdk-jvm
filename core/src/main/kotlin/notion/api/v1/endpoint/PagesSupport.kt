package notion.api.v1.endpoint

import notion.api.v1.exception.NotionAPIError
import notion.api.v1.http.NotionHttpClient
import notion.api.v1.json.NotionJsonSerializer
import notion.api.v1.logging.NotionLogger
import notion.api.v1.model.blocks.Block
import notion.api.v1.model.common.Cover
import notion.api.v1.model.common.Icon
import notion.api.v1.model.pages.Page
import notion.api.v1.model.pages.PageParent
import notion.api.v1.model.pages.PageProperty
import notion.api.v1.request.pages.CreatePageRequest
import notion.api.v1.request.pages.RetrievePageRequest
import notion.api.v1.request.pages.UpdatePagePropertiesRequest

interface PagesSupport : EndpointsSupport {
  val httpClient: NotionHttpClient
  val jsonSerializer: NotionJsonSerializer
  val logger: NotionLogger
  val baseUrl: String

  // -----------------------------------------------
  // createPage
  // -----------------------------------------------

  fun createPage(
      parent: PageParent,
      properties: Map<String, PageProperty>,
      children: List<Block>? = null,
      icon: Icon? = null,
      cover: Cover? = null,
  ): Page {
    return createPage(
        CreatePageRequest(
            parent = parent,
            properties = properties,
            children = children,
            icon = icon,
            cover = cover,
        ))
  }

  fun createPage(page: CreatePageRequest): Page {
    val httpResponse =
        httpClient.postTextBody(
            logger = logger,
            url = "$baseUrl/pages",
            body = jsonSerializer.toJsonString(page),
            headers = buildRequestHeaders(contentTypeJson()))
    if (httpResponse.status == 200) {
      return jsonSerializer.toPage(httpResponse.body)
    } else {
      throw NotionAPIError(
          error = jsonSerializer.toError(httpResponse.body),
          httpResponse = httpResponse,
      )
    }
  }

  // -----------------------------------------------
  // retrievePage
  // -----------------------------------------------

  fun retrievePage(pageId: String): Page {
    return retrievePage(RetrievePageRequest(pageId))
  }

  fun retrievePage(request: RetrievePageRequest): Page {
    val httpResponse =
        httpClient.get(
            logger = logger,
            url = "$baseUrl/pages/${urlEncode(request.pageId)}",
            headers = buildRequestHeaders(emptyMap()))
    if (httpResponse.status == 200) {
      return jsonSerializer.toPage(httpResponse.body)
    } else {
      throw NotionAPIError(
          error = jsonSerializer.toError(httpResponse.body),
          httpResponse = httpResponse,
      )
    }
  }

  // -----------------------------------------------
  // updatePageProperties
  // -----------------------------------------------

  fun updatePageProperties(
      pageId: String,
      properties: Map<String, PageProperty>,
      archived: Boolean? = null,
      icon: Icon? = null,
      cover: Cover? = null,
  ): Page {
    return updatePageProperties(
        UpdatePagePropertiesRequest(
            pageId = pageId,
            properties = properties,
            archived = archived,
            icon = icon,
            cover = cover,
        ))
  }

  fun updatePageProperties(request: UpdatePagePropertiesRequest): Page {
    val httpResponse =
        httpClient.patchTextBody(
            logger = logger,
            url = "$baseUrl/pages/${request.pageId}",
            body = jsonSerializer.toJsonString(request),
            headers = buildRequestHeaders(contentTypeJson()))
    if (httpResponse.status == 200) {
      return jsonSerializer.toPage(httpResponse.body)
    } else {
      throw NotionAPIError(
          error = jsonSerializer.toError(httpResponse.body),
          httpResponse = httpResponse,
      )
    }
  }
}

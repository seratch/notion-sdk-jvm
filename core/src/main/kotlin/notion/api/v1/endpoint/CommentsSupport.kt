package notion.api.v1.endpoint

import notion.api.v1.exception.NotionAPIError
import notion.api.v1.http.NotionHttpClient
import notion.api.v1.json.NotionJsonSerializer
import notion.api.v1.logging.NotionLogger
import notion.api.v1.model.comments.*
import notion.api.v1.model.pages.PageParent
import notion.api.v1.model.pages.PageProperty
import notion.api.v1.request.comments.*

interface CommentsSupport : EndpointsSupport {
  val httpClient: NotionHttpClient
  val jsonSerializer: NotionJsonSerializer
  val logger: NotionLogger
  val baseUrl: String

  // -----------------------------------------------
  // createComment
  // -----------------------------------------------

  fun createComment(parent: PageParent, richText: List<PageProperty.RichText>): Comment {
    return createComment(CreateCommentRequest(parent = parent, richText = richText))
  }
  fun createComment(discussionId: String, richText: List<PageProperty.RichText>): Comment {
    return createComment(CreateCommentRequest(discussionId = discussionId, richText = richText))
  }

  fun createComment(comment: CreateCommentRequest): Comment {
    val httpResponse =
        httpClient.postTextBody(
            logger = logger,
            url = "$baseUrl/comments",
            body = jsonSerializer.toJsonString(comment),
            headers = buildRequestHeaders(contentTypeJson()))
    if (httpResponse.status == 200) {
      return jsonSerializer.toComment(httpResponse.body)
    } else {
      throw NotionAPIError(
          error = jsonSerializer.toError(httpResponse.body),
          httpResponse = httpResponse,
      )
    }
  }

  // -----------------------------------------------
  // retrieveComments
  // -----------------------------------------------

  fun retrieveComments(
      blockId: String,
      pageSize: Int? = null,
  ): Comments {
    return retrieveComments(RetrieveCommentsRequest(blockId = blockId, pageSize = pageSize))
  }

  fun retrieveComments(
      blockId: String,
      startCursor: String? = null,
      pageSize: Int? = null,
  ): Comments {
    return retrieveComments(
        RetrieveCommentsRequest(blockId = blockId, startCursor = startCursor, pageSize = pageSize))
  }

  fun retrieveComments(request: RetrieveCommentsRequest): Comments {
    val httpResponse =
        httpClient.get(
            logger = logger,
            url = "$baseUrl/comments",
            query = request.toQuery(),
            headers = buildRequestHeaders(emptyMap()))
    if (httpResponse.status == 200) {
      return jsonSerializer.toComments(httpResponse.body)
    } else {
      throw NotionAPIError(
          error = jsonSerializer.toError(httpResponse.body),
          httpResponse = httpResponse,
      )
    }
  }
}

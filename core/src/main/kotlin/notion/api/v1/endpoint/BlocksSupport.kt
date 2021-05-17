package notion.api.v1.endpoint

import notion.api.v1.exception.NotionAPIError
import notion.api.v1.http.NotionHttpClient
import notion.api.v1.json.NotionJsonSerializer
import notion.api.v1.logging.NotionLogger
import notion.api.v1.model.blocks.Block
import notion.api.v1.model.blocks.Blocks
import notion.api.v1.request.blocks.AppendBlockChildrenRequest
import notion.api.v1.request.blocks.RetrieveBlockChildrenRequest

interface BlocksSupport : EndpointsSupport {
  val httpClient: NotionHttpClient
  val jsonSerializer: NotionJsonSerializer
  val logger: NotionLogger
  val baseUrl: String

  // -----------------------------------------------
  // retrieveBlockChildren
  // -----------------------------------------------

  fun retrieveBlockChildren(request: RetrieveBlockChildrenRequest): Blocks {
    val httpResponse =
        httpClient.get(
            logger = logger,
            query = request.toQuery(),
            url = "$baseUrl/blocks/${urlEncode(request.blockId)}/children",
            headers = buildRequestHeaders(emptyMap()))
    if (httpResponse.status == 200) {
      return jsonSerializer.toBlocks(httpResponse.body)
    } else {
      throw NotionAPIError(
          error = jsonSerializer.toError(httpResponse.body),
          httpResponse = httpResponse,
      )
    }
  }

  // -----------------------------------------------
  // appendBlockChildren
  // -----------------------------------------------

  fun appendBlockChildren(request: AppendBlockChildrenRequest): Block {
    val httpResponse =
        httpClient.patchTextBody(
            logger = logger,
            url = "$baseUrl/pages/${request.blockId}/children",
            body = jsonSerializer.toJsonString(request),
            headers = buildRequestHeaders(contentTypeJson()))
    if (httpResponse.status == 200) {
      return jsonSerializer.toBlock(httpResponse.body)
    } else {
      throw NotionAPIError(
          error = jsonSerializer.toError(httpResponse.body),
          httpResponse = httpResponse,
      )
    }
  }
}

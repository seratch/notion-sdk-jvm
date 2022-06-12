package notion.api.v1.endpoint

import notion.api.v1.exception.NotionAPIError
import notion.api.v1.http.NotionHttpClient
import notion.api.v1.json.NotionJsonSerializer
import notion.api.v1.logging.NotionLogger
import notion.api.v1.model.blocks.Block
import notion.api.v1.model.blocks.BlockElementUpdate
import notion.api.v1.model.blocks.BlockType
import notion.api.v1.model.blocks.Blocks
import notion.api.v1.request.blocks.*

interface BlocksSupport : EndpointsSupport {
  val httpClient: NotionHttpClient
  val jsonSerializer: NotionJsonSerializer
  val logger: NotionLogger
  val baseUrl: String

  // -----------------------------------------------
  // retrieveBlock
  // -----------------------------------------------

  fun retrieveBlock(blockId: String): Block {
    return retrieveBlock(RetrieveBlockRequest(blockId = blockId))
  }

  fun retrieveBlock(request: RetrieveBlockRequest): Block {
    val httpResponse =
        httpClient.get(
            logger = logger,
            url = "$baseUrl/blocks/${urlEncode(request.blockId)}",
            headers = buildRequestHeaders(emptyMap()))
    if (httpResponse.status == 200) {
      return jsonSerializer.toBlock(httpResponse.body)
    } else {
      throw NotionAPIError(
          error = jsonSerializer.toError(httpResponse.body),
          httpResponse = httpResponse,
      )
    }
  }

  // -----------------------------------------------
  // updateBlock
  // -----------------------------------------------

  fun updateBlock(blockId: String, elements: Map<BlockType, BlockElementUpdate>): Block {
    return updateBlock(UpdateBlockRequest(blockId = blockId, elements = elements))
  }

  fun updateBlock(request: UpdateBlockRequest): Block {
    val body = mutableMapOf<String, Any>()
    for (key in request.elements.keys) {
      val value = request.elements[key]
      if (value != null) {
        body[key.value] = value
      }
    }
    if (request.type != null) {
      body["type"] = request.type
    }
    if (request.archived != null) {
      body["archived"] = request.archived
    }
    val httpResponse =
        httpClient.patchTextBody(
            logger = logger,
            url = "$baseUrl/blocks/${urlEncode(request.blockId)}",
            body = jsonSerializer.toJsonString(body),
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

  // -----------------------------------------------
  // deleteBlock
  // -----------------------------------------------

  fun deleteBlock(blockId: String) {
    return deleteBlock(DeleteBlockRequest(blockId = blockId))
  }

  fun deleteBlock(request: DeleteBlockRequest) {
    val httpResponse =
        httpClient.delete(
            logger = logger,
            url = "$baseUrl/blocks/${urlEncode(request.blockId)}",
            headers = buildRequestHeaders(emptyMap()))
    if (httpResponse.status != 200) {
      throw NotionAPIError(
          error = jsonSerializer.toError(httpResponse.body),
          httpResponse = httpResponse,
      )
    }
  }

  // -----------------------------------------------
  // retrieveBlockChildren
  // -----------------------------------------------

  fun retrieveBlockChildren(
      blockId: String,
      startCursor: String? = null,
      pageSize: Int? = null,
  ): Blocks {
    return retrieveBlockChildren(RetrieveBlockChildrenRequest(blockId, startCursor, pageSize))
  }

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

  fun appendBlockChildren(blockId: String, children: List<Block>): Blocks {
    return appendBlockChildren(AppendBlockChildrenRequest(blockId, children))
  }

  fun appendBlockChildren(request: AppendBlockChildrenRequest): Blocks {
    val httpResponse =
        httpClient.patchTextBody(
            logger = logger,
            url = "$baseUrl/blocks/${request.blockId}/children",
            body = jsonSerializer.toJsonString(request),
            headers = buildRequestHeaders(contentTypeJson()))
    if (httpResponse.status == 200) {
      return jsonSerializer.toBlocks(httpResponse.body)
    } else {
      throw NotionAPIError(
          error = jsonSerializer.toError(httpResponse.body),
          httpResponse = httpResponse,
      )
    }
  }
}

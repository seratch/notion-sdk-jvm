package notion.api.v1.model.common

// https://developers.notion.com/reference/file-object
data class File
@JvmOverloads
constructor(
    val type: FileType,
    var external: ExternalFileDetails? = null,
    var file: FileDetails? = null,
) : Icon, Cover {
  companion object {
    @JvmStatic
    fun external(external: ExternalFileDetails): File {
      return File(type = FileType.External, external = external)
    }
    @JvmStatic
    fun external(url: String): File {
      return external(ExternalFileDetails(url = url))
    }

    @JvmStatic
    fun file(file: FileDetails): File {
      return File(type = FileType.File, file = file)
    }

    @JvmStatic
    fun file(url: String, expiryTime: String? = null): File {
      return file(FileDetails(url = url, expiryTime = expiryTime))
    }
  }
}

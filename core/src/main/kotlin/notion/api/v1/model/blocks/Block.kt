package notion.api.v1.model.blocks

import notion.api.v1.model.common.WithObjectType
import notion.api.v1.model.users.User

interface Block : WithObjectType {
  val type: BlockType
  var id: String?
  var createdTime: String?
  var createdBy: User?
  var lastEditedTime: String?
  var lastEditedBy: User?
  var archived: Boolean?
  var hasChildren: Boolean?
  var parent: BlockParent?
  val requestId: String?
  var inTrash: Boolean?

  fun asParagraph(): ParagraphBlock =
      if (type == BlockType.Paragraph) this as ParagraphBlock
      else throw IllegalStateException("Failed to cast $type block to ParagraphBlock")

  fun asHeadingOne(): HeadingOneBlock =
      if (type == BlockType.HeadingOne) this as HeadingOneBlock
      else throw IllegalStateException("Failed to cast $type block to HeadingOneBlock")

  fun asHeadingTwo(): HeadingTwoBlock =
      if (type == BlockType.HeadingTwo) this as HeadingTwoBlock
      else throw IllegalStateException("Failed to cast $type block to HeadingTwoBlock")

  fun asHeadingThree(): HeadingThreeBlock =
      if (type == BlockType.HeadingThree) this as HeadingThreeBlock
      else throw IllegalStateException("Failed to cast $type block to HeadingThreeBlock")

  fun asBulletedListItem(): BulletedListItemBlock =
      if (type == BlockType.BulletedListItem) this as BulletedListItemBlock
      else throw IllegalStateException("Failed to cast $type block to BulletedListItemBlock")

  fun asNumberedListItem(): NumberedListItemBlock =
      if (type == BlockType.NumberedListItem) this as NumberedListItemBlock
      else throw IllegalStateException("Failed to cast $type block to NumberedListItemBlock")

  fun asToDo(): ToDoBlock =
      if (type == BlockType.ToDo) this as ToDoBlock
      else throw IllegalStateException("Failed to cast $type block to ToDoBlock")

  fun asToggle(): ToggleBlock =
      if (type == BlockType.Toggle) this as ToggleBlock
      else throw IllegalStateException("Failed to cast $type block to ToggleBlock")

  fun asChildPage(): ChildPageBlock =
      if (type == BlockType.ChildPage) this as ChildPageBlock
      else throw IllegalStateException("Failed to cast $type block to ChildPageBlock")

  fun asLinkToPage(): LinkToPageBlock =
      if (type == BlockType.LinkToPage) this as LinkToPageBlock
      else throw IllegalStateException("Failed to cast $type block to LinkToPageBlock")

  fun asQuote(): QuoteBlock =
      if (type == BlockType.Quote) this as QuoteBlock
      else throw IllegalStateException("Failed to cast $type block to QuoteBlock")

  fun asCallout(): CalloutBlock =
      if (type == BlockType.Callout) this as CalloutBlock
      else throw IllegalStateException("Failed to cast $type block to CalloutBlock")

  fun asVideo(): VideoBlock =
      if (type == BlockType.Video) this as VideoBlock
      else throw IllegalStateException("Failed to cast $type block to VideoBlock")

  fun asAudio(): AudioBlock =
      if (type == BlockType.Audio) this as AudioBlock
      else throw IllegalStateException("Failed to cast $type block to AudioBlock")

  fun asDivider(): DividerBlock =
      if (type == BlockType.Divider) this as DividerBlock
      else throw IllegalStateException("Failed to cast $type block to DividerBlock")

  fun asSyncedBlock(): SyncedBlock =
      if (type == BlockType.SyncedBlock) this as SyncedBlock
      else throw IllegalStateException("Failed to cast $type block to SyncedBlock")

  fun asTemplate(): TemplateBlock =
      if (type == BlockType.Template) this as TemplateBlock
      else throw IllegalStateException("Failed to cast $type block to TemplateBlock")

  fun asCode(): CodeBlock =
      if (type == BlockType.Code) this as CodeBlock
      else throw IllegalStateException("Failed to cast $type block to CodeBlock")

  fun asLinkPreview(): LinkPreviewBlock =
      if (type == BlockType.LinkPreview) this as LinkPreviewBlock
      else throw IllegalStateException("Failed to cast $type block to LinkPreviewBlock")

  fun asBookmark(): BookmarkBlock =
      if (type == BlockType.Bookmark) this as BookmarkBlock
      else throw IllegalStateException("Failed to cast $type block to BookmarkBlock")

  fun asEquation(): EquationBlock =
      if (type == BlockType.Equation) this as EquationBlock
      else throw IllegalStateException("Failed to cast $type block to EquationBlock")

  fun asTableOfContents(): TableOfContentsBlock =
      if (type == BlockType.TableOfContents) this as TableOfContentsBlock
      else throw IllegalStateException("Failed to cast $type block to TableOfContentsBlock")

  fun asBreadcrumb(): BreadcrumbBlock =
      if (type == BlockType.Breadcrumb) this as BreadcrumbBlock
      else throw IllegalStateException("Failed to cast $type block to BreadcrumbBlock")

  fun asColumn(): ColumnBlock =
      if (type == BlockType.Column) this as ColumnBlock
      else throw IllegalStateException("Failed to cast $type block to ColumnBlock")

  fun asColumnList(): ColumnListBlock =
      if (type == BlockType.ColumnList) this as ColumnListBlock
      else throw IllegalStateException("Failed to cast $type block to ColumnListBlock")

  fun asImage(): ImageBlock =
      if (type == BlockType.Image) this as ImageBlock
      else throw IllegalStateException("Failed to cast $type block to ImageBlock")

  fun asFile(): FileBlock =
      if (type == BlockType.File) this as FileBlock
      else throw IllegalStateException("Failed to cast $type block to FileBlock")

  fun asPDF(): PDFBlock =
      if (type == BlockType.PDF) this as PDFBlock
      else throw IllegalStateException("Failed to cast $type block to PDFBlock")

  fun asEmbed(): EmbedBlock =
      if (type == BlockType.Embed) this as EmbedBlock
      else throw IllegalStateException("Failed to cast $type block to EmbedBlock")

  fun asChildDatabase(): ChildDatabaseBlock =
      if (type == BlockType.ChildDatabase) this as ChildDatabaseBlock
      else throw IllegalStateException("Failed to cast $type block to ChildDatabaseBlock")

  fun asTable(): TableBlock =
      if (type == BlockType.Table) this as TableBlock
      else throw IllegalStateException("Failed to cast $type block to TableBlock")

  fun asTableRow(): TableRowBlock =
      if (type == BlockType.TableRow) this as TableRowBlock
      else throw IllegalStateException("Failed to cast $type block to TableRowBlock")

  fun asUnsupported(): UnsupportedBlock =
      if (type == BlockType.Unsupported) this as UnsupportedBlock
      else throw IllegalStateException("Failed to cast $type block to UnsupportedBlock")
}

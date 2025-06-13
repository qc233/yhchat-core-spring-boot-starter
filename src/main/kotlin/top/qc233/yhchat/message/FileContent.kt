package top.qc233.yhchat.message

class FileContent(override var buttons: List<Button> = emptyList()) : Content {
    var fileKey: String? = null
    override fun getContent(): String {
        return fileKey!!
    }

    override fun setContent(content: String) {
        this.fileKey = content
    }
}
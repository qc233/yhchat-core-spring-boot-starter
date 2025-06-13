package top.qc233.yhchat.message

class MarkdownContent(override var buttons: List<Button> = emptyList()) : Content {
    var text: String? = null
    override fun getContent(): String {
        return text!!
    }

    override fun setContent(content: String) {
        this.text = content
    }
}
package xyz.qc233.yhchat.message

class ImageContent(override var buttons: List<Button> = emptyList()) : Content {
    var imageKey: String? = null
    override fun getContent(): String {
        return imageKey!!
    }

    override fun setContent(content: String) {
        this.imageKey = content
    }
}
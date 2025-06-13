package xyz.qc233.yhchat.message

class VideoContent(override var buttons: List<Button> = emptyList()) : Content {
    var videoKey: String? = null
    override fun getContent(): String {
        return videoKey!!
    }

    override fun setContent(content: String) {
        this.videoKey = content
    }
}
package xyz.qc233.yhchat.message

class EMessage(
    val msgId: String,
    val recvId: String,
    val recvType: String,
    val contentType: String,
    val content: Content,
    val parentId: String? = null,
) {
}
package top.qc233.yhchat.message

import top.qc233.yhchat.event.FollowEvent
import top.qc233.yhchat.event.JoinEvent
import top.qc233.yhchat.event.MessageEvent
import top.qc233.yhchat.member.Sender

class SMessageBuilder {
    var contentType: String? = null
    var content: String? = null
    var buttons: List<Button> = ArrayList()
    var rcvId: List<String>? = null
    var rcvType: String? = null
    var parentId: String? = null

    constructor(sMessage: SMessage? = null){
        if (sMessage != null) {
            this.contentType = sMessage.contentType
            this.content = sMessage.content.getContent()
            this.buttons = sMessage.content.buttons
            this.rcvId = sMessage.recvId
            this.rcvType = sMessage.recvType
            this.parentId = sMessage.parentId
        }
    }

    fun setReceiver(rcvId: String, rcvType: String): SMessageBuilder {
        this.rcvId = List<String>(1, init = {i -> rcvId})
        this.rcvType = rcvType
        return this
    }
    fun setReceiver(me: MessageEvent): SMessageBuilder {

        val (senderId, senderType) = when (me.chat.chatType) {
            "bot" -> me.sender.senderId to "user"
            "group" -> me.chat.chatId to "group"
            else -> error("未知的 chatType: ${me.chat.chatType}")
        }

        setReceiver(senderId.toString(), senderType.toString())
        return this
    }
    fun setReceiver(sd: Sender): SMessageBuilder {
        setReceiver(sd.senderId.toString(), sd.senderType.toString())
        return this
    }
    fun setReceiver(fe: FollowEvent): SMessageBuilder {
        setReceiver(fe.userId, "user")
        return this
    }
    fun setReceiver(je: JoinEvent): SMessageBuilder {
        setReceiver(je.chatId, "group")
        return this
    }
    fun setMultiRecvId(rcvIds: List<String>, rcvType: String): SMessageBuilder {
        this.rcvId = rcvIds
        this.rcvType = rcvType
        return this
    }
    /**
     * 该add方法存在覆盖行为
     * */
    fun addText(content: String): SMessageBuilder {
        contentType = "text"
        this.content = content
        return this
    }
    /**
     * 该add方法存在覆盖行为
     * */
    fun addMarkdown(content: String): SMessageBuilder {
        addText(content)
        this.contentType = "markdown"
        return this
    }
    /**
     * 该add方法存在覆盖行为
     * */
    fun addHtml(content: String): SMessageBuilder {
        addText(content)
        this.contentType = "html"
        return this
    }
    /**
     * 该add方法存在覆盖行为
     * 也可以给入一个本地文件路径或者指向文件的URI
     * */
    fun addImage(imgKey: String): SMessageBuilder {
        contentType = "image"
        this.content = imgKey
        return this
    }
    /**
     * 该add方法存在覆盖行为
     * 也可以给入一个本地文件路径或者指向文件的URI
     * */
    fun addVideo(videoKey: String): SMessageBuilder {
        contentType = "video"
        this.content = videoKey
        return this
    }
    /**
     * 该add方法存在覆盖行为
     * 也可以给入一个本地文件路径或者指向文件的URI
     * */
    fun addFile(fileKey: String): SMessageBuilder {
        contentType = "file"
        this.content = fileKey
        return this
    }

    fun addButton(button: Button): SMessageBuilder {
        this.buttons+=button
        return this
    }
    fun addButton(text: String, action: Int = 1, value: String? = ""): SMessageBuilder {
        this.buttons+= Button(text, action, value)
        return this
    }

    fun setParent(parentId: String): SMessageBuilder {
        this.parentId = parentId
        return this
    }
    fun setParent(m: Message): SMessageBuilder {
        this.parentId = m.msgId
        return this
    }
    fun setParent(me: MessageEvent): SMessageBuilder {
        this.parentId = me.message.msgId
        return this
    }

    fun build(): SMessage {
        val messageContent: Content = when (contentType) {
            "text" -> TextContent(buttons)
            "image" -> ImageContent(buttons)
            "file" -> FileContent(buttons)
            "video" -> VideoContent(buttons)
            "markdown" -> MarkdownContent(buttons)
            "html" -> TextContent(buttons)
            else -> throw IllegalArgumentException("不支持的 contentType: $contentType")
        }

        val message = SMessage(
            recvId = rcvId!!,
            recvType = rcvType!!,
            contentType = contentType!!,
            content = messageContent,
            parentId = parentId
        )
        message.content.setContent(content.toString())
        return message
    }
    fun build(eMessage: EMessage): EMessage {
        val messageContent: Content = when (contentType) {
            "text" -> TextContent(buttons)
            "image" -> ImageContent(buttons)
            "file" -> FileContent(buttons)
            "video" -> VideoContent(buttons)
            "markdown" -> MarkdownContent(buttons)
            "html" -> TextContent(buttons)
            else -> throw IllegalArgumentException("不支持的 contentType: $contentType")
        }

        val message = EMessage(
            msgId = eMessage.msgId,
            recvId = rcvId?.first() ?: "",
            recvType = rcvType!!,
            contentType = contentType!!,
            content = messageContent,
            parentId = parentId
        )
        message.content.setContent(content.toString())
        return message
    }
}
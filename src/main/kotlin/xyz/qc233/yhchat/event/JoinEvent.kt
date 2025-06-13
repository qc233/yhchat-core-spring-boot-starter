package xyz.qc233.yhchat.event

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import xyz.qc233.yhchat.chat.Chat
import xyz.qc233.yhchat.member.Sender
import xyz.qc233.yhchat.message.EMessage
import xyz.qc233.yhchat.message.Message
import xyz.qc233.yhchat.message.NullMessage
import xyz.qc233.yhchat.message.SMessage
import xyz.qc233.yhchat.message.SMessageBuilder
import xyz.qc233.yhchat.sneder.MessageSender

@Component
class JoinEvent (): Event {
    var time: Long = 1L
    var chatId: String = ""
    var chatType: String = ""
    var userId: String = ""
    var nickname: String = ""
    var avatarUrl: String = ""

    override var sender: Sender = Sender()
    override var chat: Chat = Chat()
    override var message: Message = NullMessage

    @Autowired
    lateinit var messageSender: MessageSender


    constructor(
        time: Long,
        chatId: String,
        chatType: String,
        userId: String,
        nickname: String,
        avatarUrl: String
    ) : this() {
        this.time = time
        this.chatId = chatId
        this.chatType = chatType
        this.userId = userId
        this.nickname = nickname
        this.avatarUrl = avatarUrl
    }


    fun reply(content: String): EMessage {
        var sMessage = SMessageBuilder()
            .addText(content)
            .setReceiver(chatId, "group")
            .build()
        return messageSender.send(sMessage)
    }
    fun reply(sMessage: SMessage): EMessage {
        return messageSender.send(sMessage)
    }
}
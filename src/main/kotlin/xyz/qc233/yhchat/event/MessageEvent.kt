package xyz.qc233.yhchat.event

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import xyz.qc233.yhchat.chat.Chat
import xyz.qc233.yhchat.member.Sender
import xyz.qc233.yhchat.message.EMessage
import xyz.qc233.yhchat.message.Message
import xyz.qc233.yhchat.message.SMessage
import xyz.qc233.yhchat.message.SMessageBuilder
import xyz.qc233.yhchat.sneder.MessageSender


@Component
class MessageEvent() : Event {

    override lateinit var sender: Sender
    override lateinit var chat: Chat
    override lateinit var message: Message

    @Autowired
    lateinit var messageSender: MessageSender

    fun getContent(): String {
        return message.content?.getContent() ?: ""
    }
    fun reply(content: String, quote: Boolean = false): EMessage {

        val (senderId, senderType) = when (chat.chatType) {
            "bot" -> sender.senderId to "user"
            "group" -> chat.chatId to "group"
            else -> error("未知的 chatType: ${chat.chatType}")
        }

        var sMessage = SMessageBuilder()
            .addText(content)
            .setReceiver(senderId.toString(), senderType.toString())
            .setParent(if(quote) message.chatId?.toString() ?: "" else "")
            .build()
        return messageSender.send(sMessage)
    }
    fun reply(sMessage: SMessage): EMessage {
        return messageSender.send(sMessage)
    }
}
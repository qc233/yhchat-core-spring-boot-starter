package top.qc233.yhchat.event

import org.springframework.stereotype.Component
import top.qc233.yhchat.chat.Chat
import top.qc233.yhchat.member.Sender
import top.qc233.yhchat.message.Message
import top.qc233.yhchat.message.NullMessage


@Component
class ButtonEvent (): Event {
    var time: Long = 1L
    var msgId: String = ""
    var recvId: String = ""
    var recvType: String = ""
    var userId: String = ""
    var value: String = ""

    override var sender: Sender = Sender()
    override var chat: Chat = Chat()
    override var message: Message = NullMessage

    constructor(
        time: Long,
        msgId: String,
        recvId: String,
        recvType: String,
        userId: String,
        value: String
    ) : this() {
        this.time = time
        this.msgId = msgId
        this.recvId = recvId
        this.recvType = recvType
        this.userId = userId
        this.value = value
    }
}
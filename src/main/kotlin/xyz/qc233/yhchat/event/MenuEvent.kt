package xyz.qc233.yhchat.event

import org.springframework.stereotype.Component
import xyz.qc233.yhchat.chat.Chat
import xyz.qc233.yhchat.member.Sender
import xyz.qc233.yhchat.message.Message
import xyz.qc233.yhchat.message.NullMessage


@Component
class MenuEvent (): Event {
    var botId: String = ""
    var menuId: String = ""
    var menuType: Int = 0
    var menuAction: Int = 0
    var chatId: String = ""
    var chatType: String = ""
    var senderType: String = ""
    var senderId: String = ""
    var sendTime: Long = 0

    override var sender: Sender = Sender()
    override var chat: Chat = Chat()
    override var message: Message = NullMessage

    constructor(
        botId: String,
        menuId: String,
        menuType: Int,
        menuAction: Int,
        chatId: String,
        chatType: String,
        senderType: String,
        senderId: String,
        sendTime: Long
    ) : this() {
        this.botId = botId
        this.menuId = menuId
        this.menuType = menuType
        this.menuAction = menuAction
        this.chatId = chatId
        this.chatType = chatType
        this.senderType = senderType
        this.senderId = senderId
        this.sendTime = sendTime
    }
}
package xyz.qc233.yhchat.event

import xyz.qc233.yhchat.chat.Chat
import xyz.qc233.yhchat.member.Sender
import xyz.qc233.yhchat.message.Message
import xyz.qc233.yhchat.message.NullMessage

class SettingEvent(): Event {
    var botId: String = ""
    var chatId: String = ""
    var chatType: String = ""
    var groupId: String = ""
    var groupName: String = ""
    var time: Long = 0
    var avatarUrl: String = ""
    var settingJson: String = ""

    override var sender: Sender = Sender()
    override var chat: Chat = Chat()
    override var message: Message = NullMessage

    constructor(
        botId: String,
        chatId: String,
        chatType: String,
        groupId: String,
        groupName: String,
        time: Long,
        avatarUrl: String,
        settingJson: String
    ) : this() {
        this.botId = botId
        this.chatId = chatId
        this.chatType = chatType
        this.groupId = groupId
        this.groupName = groupName
        this.time = time
        this.avatarUrl = avatarUrl
        this.settingJson = settingJson
    }
}
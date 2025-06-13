package top.qc233.yhchat.event

import top.qc233.yhchat.chat.Chat
import top.qc233.yhchat.member.Sender
import top.qc233.yhchat.message.Message
import top.qc233.yhchat.message.NullMessage

class UnfollowEvent(
    val time: Long,
    val chatId: String,
    val chatType: String,
    val userId: String,
    val nickname: String,
    val avatarUrl: String
): Event {
    override var sender: Sender = Sender()
    override var chat: Chat = Chat()
    override var message: Message = NullMessage
}
package xyz.qc233.yhchat.event

import xyz.qc233.yhchat.chat.Chat
import xyz.qc233.yhchat.member.Sender
import xyz.qc233.yhchat.message.Message
import xyz.qc233.yhchat.message.NullMessage

class LeaveEvent (
    val time: Long,
    val chatId: String,
    val chatType: String,
    val userId: String,
    val nickname: String,
    val avatarUrl: String,
): Event {
    override var sender: Sender = Sender()
    override var chat: Chat = Chat()
    override var message: Message = NullMessage
}
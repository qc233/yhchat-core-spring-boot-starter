package top.qc233.yhchat.event

import top.qc233.yhchat.chat.Chat
import top.qc233.yhchat.member.Sender
import top.qc233.yhchat.message.Message

class CommandEvent: Event {
    override lateinit var sender: Sender
    override lateinit var chat: Chat
    override lateinit var message: Message


    fun getContent(): String {
        return message.content?.getContent() ?: ""
    }
}
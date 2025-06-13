package top.qc233.yhchat.event

import top.qc233.yhchat.chat.Chat
import top.qc233.yhchat.member.Sender
import top.qc233.yhchat.message.Message


interface Event {
    var sender: Sender
    var chat: Chat
    var message: Message
}
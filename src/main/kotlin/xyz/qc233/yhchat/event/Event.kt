package xyz.qc233.yhchat.event

import xyz.qc233.yhchat.chat.Chat
import xyz.qc233.yhchat.member.Sender
import xyz.qc233.yhchat.message.Message


interface Event {
    var sender: Sender
    var chat: Chat
    var message: Message
}
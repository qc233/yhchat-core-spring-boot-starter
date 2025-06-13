package top.qc233.yhchat.filter

import top.qc233.yhchat.event.MessageEvent

interface MessageEventFilter {
    fun filter(event: MessageEvent, value: String): Boolean
}
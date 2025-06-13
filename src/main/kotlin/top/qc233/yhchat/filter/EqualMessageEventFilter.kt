package top.qc233.yhchat.filter

import top.qc233.yhchat.event.MessageEvent
import top.qc233.yhchat.message.TextContent

class EqualMessageEventFilter: MessageEventFilter {
    override fun filter(
        event: MessageEvent,
        value: String
    ): Boolean {

        lateinit var content: TextContent

        try {
            content = event.message.content as TextContent
        } catch (_: ClassCastException) {
            return false
        }

        return content.text?.equals(value) == true
    }
}
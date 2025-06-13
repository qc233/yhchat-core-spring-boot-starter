package top.qc233.yhchat.filter

import top.qc233.yhchat.event.MessageEvent
import top.qc233.yhchat.message.TextContent

class RegularMessageEventFilter : MessageEventFilter {
    override fun filter(event: MessageEvent, value: String): Boolean {
        val content = event.message.content
        if (content !is TextContent) return false
        return content.text?.matches(Regex(value)) == true
    }
}

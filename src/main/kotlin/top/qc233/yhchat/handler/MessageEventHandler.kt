package top.qc233.yhchat.handler


import top.qc233.yhchat.filter.MessageFilterType
import top.qc233.yhchat.filter.MessageEventFilter
import kotlin.reflect.KFunction

data class MessageEventHandler(
    val value: String,
    val bean: Any,
    val method: KFunction<*>,
    val type: MessageFilterType,
    val priority: Int,
    val filter: MessageEventFilter,
    val propagate: Boolean,
    val target: String,
)
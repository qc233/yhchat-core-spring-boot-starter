package xyz.qc233.yhchat.handler


import xyz.qc233.yhchat.filter.MessageFilterType
import xyz.qc233.yhchat.filter.MessageEventFilter
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
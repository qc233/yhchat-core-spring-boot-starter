package top.qc233.yhchat.handler

import top.qc233.yhchat.filter.MessageFilterType
import kotlin.reflect.KFunction

class CommandEventHandler(
    val value: Int,
    val bean: Any,
    val method: KFunction<*>,
    val type: MessageFilterType,
    val priority: Int,
    val propagate: Boolean,
    val target: String,
) {

}
package xyz.qc233.yhchat.handler

import xyz.qc233.yhchat.filter.MessageFilterType
import kotlin.reflect.KFunction

class CommandEventHandler(
    val value: Int,
    val bean: Any,
    val method: KFunction<*>,
    val type: MessageFilterType,
    val priority: Int,
    val propagate: Boolean,
    val target: String,
    val groupId: String,
    val userId: String
) {

}
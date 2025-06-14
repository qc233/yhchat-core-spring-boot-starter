package xyz.qc233.yhchat.handler

import kotlin.reflect.KFunction

class ButtonEventHandler(
    val value: String,
    val bean: Any,
    val method: KFunction<*>,
    val priority: Int,
    val propagate: Boolean,
    val target: String,
    val groupId: String,
    val userId: String
) {

}
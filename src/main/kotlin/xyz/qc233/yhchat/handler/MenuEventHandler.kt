package xyz.qc233.yhchat.handler

import kotlin.reflect.KFunction

class MenuEventHandler(
    val id: String,
    val type: Int,
    val action: Int,
    val bean: Any,
    val method: KFunction<*>,
    val priority: Int,
    val propagate: Boolean,
    val target: String,
    val groupId: String,
    val userId: String
) {

}
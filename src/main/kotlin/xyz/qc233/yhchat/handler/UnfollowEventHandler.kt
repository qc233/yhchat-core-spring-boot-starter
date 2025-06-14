package xyz.qc233.yhchat.handler

import kotlin.reflect.KFunction

class UnfollowEventHandler(
    val bean: Any,
    val method: KFunction<*>,
    val priority: Int,
    val propagate: Boolean,
    val userId: String,
) {

}
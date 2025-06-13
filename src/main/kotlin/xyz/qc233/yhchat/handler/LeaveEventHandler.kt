package xyz.qc233.yhchat.handler

import kotlin.reflect.KFunction

class LeaveEventHandler(
    val bean: Any,
    val method: KFunction<*>,
    val priority: Int,
    val propagate: Boolean,
) {

}
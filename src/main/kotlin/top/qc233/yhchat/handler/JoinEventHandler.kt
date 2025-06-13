package top.qc233.yhchat.handler

import kotlin.reflect.KFunction

class JoinEventHandler(
    val bean: Any,
    val method: KFunction<*>,
    val priority: Int,
    val propagate: Boolean,
) {

}
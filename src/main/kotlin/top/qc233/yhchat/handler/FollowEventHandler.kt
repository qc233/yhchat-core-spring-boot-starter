package top.qc233.yhchat.handler

import kotlin.reflect.KFunction

class FollowEventHandler(
    val bean: Any,
    val method: KFunction<*>,
    val priority: Int,
    val propagate: Boolean,
) {

}
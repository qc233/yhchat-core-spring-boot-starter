package top.qc233.yhchat.filter

import top.qc233.yhchat.event.CommandEvent

class CommandFilter {
    fun filter(event: CommandEvent, value: String): Boolean {
        return true

    }
}
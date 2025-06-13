package xyz.qc233.yhchat.filter

import xyz.qc233.yhchat.event.CommandEvent

class CommandFilter {
    fun filter(event: CommandEvent, value: String): Boolean {
        return true

    }
}
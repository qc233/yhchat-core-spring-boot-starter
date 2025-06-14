package xyz.qc233.yhchat.annotation

import xyz.qc233.yhchat.filter.MessageFilterType
import xyz.qc233.yhchat.filter.SenderTarget

annotation class OnCommand(
    val value: Int = 0,
    val priority: Int = 0,
    val propagate: Boolean = true,
    val type: MessageFilterType= MessageFilterType.Equal,
    val target: String = SenderTarget.ALL,
    val groupId: String = "",
    val userId: String = ""
)

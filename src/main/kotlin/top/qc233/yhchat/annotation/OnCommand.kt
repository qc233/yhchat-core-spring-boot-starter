package top.qc233.yhchat.annotation

import top.qc233.yhchat.filter.MessageFilterType
import top.qc233.yhchat.filter.SenderTarget

annotation class OnCommand(
    val value: Int = 0,
    val priority: Int = 0,
    val propagate: Boolean = true,
    val type: MessageFilterType= MessageFilterType.Equal,
    val target: String = SenderTarget.ALL,
)

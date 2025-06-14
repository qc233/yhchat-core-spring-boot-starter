package xyz.qc233.yhchat.annotation

import xyz.qc233.yhchat.filter.MessageFilterType
import xyz.qc233.yhchat.filter.SenderTarget

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.ANNOTATION_CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class OnMessage(
    val value: String = "",
    val priority: Int = 0,
    val propagate: Boolean = true,
    val type: MessageFilterType= MessageFilterType.Equal,
    val target: String = SenderTarget.ALL,
    val groupId: String = "",
    val userId: String = ""
)

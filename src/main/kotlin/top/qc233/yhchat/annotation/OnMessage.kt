package top.qc233.yhchat.annotation

import top.qc233.yhchat.filter.MessageFilterType
import top.qc233.yhchat.filter.SenderTarget

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.ANNOTATION_CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class OnMessage(
    val value: String = "",
    val priority: Int = 0,
    val propagate: Boolean = true,
    val type: MessageFilterType= MessageFilterType.Equal,
    val target: String = SenderTarget.ALL,
)

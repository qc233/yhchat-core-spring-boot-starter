package xyz.qc233.yhchat.annotation

import xyz.qc233.yhchat.filter.SenderTarget

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.ANNOTATION_CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class OnSetting (
    val value: String,
    val priority: Int,
    val propagate: Boolean,
    val target: String = SenderTarget.ALL,
    val groupId: String = "",
    val userId: String = ""
)
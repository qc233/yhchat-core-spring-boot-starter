package xyz.qc233.yhchat.annotation

import xyz.qc233.yhchat.filter.SenderTarget

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.ANNOTATION_CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class OnMenu(
    val id: String = "",
    val type: Int = -1,
    val action: Int = -1,
    val priority: Int = 0,
    val propagate: Boolean = true,
    val target: String = SenderTarget.ALL,
    val groupId: String = "",
    val userId: String = ""
)

package xyz.qc233.yhchat.annotation

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.ANNOTATION_CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class OnLeave(
    val priority: Int = 0,
    val propagate: Boolean = true,
)

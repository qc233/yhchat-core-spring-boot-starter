package top.qc233.yhchat.annotation

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.ANNOTATION_CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class OnMenu(
    val id: String = "",
    val type: Int = -1,
    val action: Int = -1,
    val priority: Int = 0,
    val propagate: Boolean = true,
)

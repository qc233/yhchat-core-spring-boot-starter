package top.qc233.yhchat.annotation

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.ANNOTATION_CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class OnSetting (
    val value: String,
    val priority: Int,
    val propagate: Boolean,
)
package xyz.qc233.yhchat.handler

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import xyz.qc233.yhchat.annotation.OnUnfollow
import kotlin.reflect.full.declaredMemberFunctions
import kotlin.reflect.full.findAnnotation

@Component
class UnfollowEventRegistrar(
    private val applicationContext: ApplicationContext
) : BeanPostProcessor {

    @Autowired
    lateinit var ufed: UnfollowEventDispatcher

    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any? {
        val kClass = bean::class

        for (func in kClass.declaredMemberFunctions) {
            val annotation = func.findAnnotation<OnUnfollow>() ?: continue

            // 构建处理器实例
            val handler = UnfollowEventHandler(
                bean = bean,
                method = func,
                priority = annotation.priority,
                propagate = annotation.propagate,
                userId = annotation.userId,
            )

            // 注册到分发器
            ufed.registerHandler(handler)
        }

        return bean
    }
}

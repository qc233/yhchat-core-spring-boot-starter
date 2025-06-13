package xyz.qc233.yhchat.handler

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import xyz.qc233.yhchat.annotation.OnCommand
import kotlin.reflect.full.declaredMemberFunctions
import kotlin.reflect.full.findAnnotation

@Component
class CommandEventRegistrar(
    private val applicationContext: ApplicationContext
) : BeanPostProcessor {

    @Autowired
    lateinit var ced: CommandEventDispatcher

    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any? {
        val kClass = bean::class

        for (func in kClass.declaredMemberFunctions) {
            val annotation = func.findAnnotation<OnCommand>() ?: continue

            // 构建处理器实例
            val handler = CommandEventHandler(
                value = annotation.value,
                bean = bean,
                method = func,
                type = annotation.type,
                priority = annotation.priority,
                propagate = annotation.propagate,
                target = annotation.target,
            )

            // 注册到分发器
            ced.registerHandler(handler)
        }

        return bean
    }
}

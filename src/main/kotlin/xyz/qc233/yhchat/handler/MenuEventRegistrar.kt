package xyz.qc233.yhchat.handler

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import xyz.qc233.yhchat.annotation.OnMenu
import kotlin.reflect.full.declaredMemberFunctions
import kotlin.reflect.full.findAnnotation

@Component
class MenuEventRegistrar(
    private val applicationContext: ApplicationContext
) : BeanPostProcessor {

    @Autowired
    lateinit var med: MenuEventDispatcher

    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any? {
        val kClass = bean::class

        for (func in kClass.declaredMemberFunctions) {
            val annotation = func.findAnnotation<OnMenu>() ?: continue

            // 构建处理器实例
            val handler = MenuEventHandler(
                bean = bean,
                method = func,
                priority = annotation.priority,
                propagate = annotation.propagate,
                id = annotation.id,
                type = annotation.type,
                action = annotation.action,
                target = annotation.target,
                groupId = annotation.groupId,
                userId = annotation.userId,
            )

            // 注册到分发器
            med.registerHandler(handler)
        }

        return bean
    }
}

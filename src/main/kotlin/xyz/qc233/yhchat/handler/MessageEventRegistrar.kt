package xyz.qc233.yhchat.handler

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import xyz.qc233.yhchat.annotation.OnMessage
import xyz.qc233.yhchat.filter.*
import kotlin.reflect.full.declaredMemberFunctions
import kotlin.reflect.full.findAnnotation

@Component
class MessageEventRegistrar(
    private val applicationContext: ApplicationContext
) : BeanPostProcessor {

    @Autowired
    lateinit var med: MessageEventDispatcher

    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any? {
        val kClass = bean::class

        for (func in kClass.declaredMemberFunctions) {
            val annotation = func.findAnnotation<OnMessage>() ?: continue

            // 创建过滤器实例
            val filter = when(annotation.type) {
                MessageFilterType.Equal -> EqualMessageEventFilter()
                MessageFilterType.Contain -> ContainMessageEventFilter()
                MessageFilterType.StartWith -> StartWithMessageEventFilter()
                MessageFilterType.Regular -> RegularMessageEventFilter()
            }

            // 构建处理器实例
            val handler = MessageEventHandler(
                value = annotation.value,
                bean = bean,
                method = func,
                type = annotation.type,
                priority = annotation.priority,
                filter = filter,
                propagate = annotation.propagate,
                target = annotation.target
            )

            // 注册到分发器
            med.registerHandler(handler)
        }

        return bean
    }
}

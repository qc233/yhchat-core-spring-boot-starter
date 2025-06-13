package top.qc233.yhchat.handler

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import top.qc233.yhchat.annotation.OnButton
import top.qc233.yhchat.annotation.OnCommand
import top.qc233.yhchat.annotation.OnFollow
import top.qc233.yhchat.annotation.OnJoin
import top.qc233.yhchat.annotation.OnMessage
import top.qc233.yhchat.filter.*
import kotlin.reflect.full.declaredMemberFunctions
import kotlin.reflect.full.findAnnotation

@Component
class ButtonEventRegistrar(
    private val applicationContext: ApplicationContext
) : BeanPostProcessor {

    @Autowired
    lateinit var bed: ButtonEventDispatcher

    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any? {
        val kClass = bean::class

        for (func in kClass.declaredMemberFunctions) {
            val annotation = func.findAnnotation<OnButton>() ?: continue

            // 构建处理器实例
            val handler = ButtonEventHandler(
                bean = bean,
                method = func,
                priority = annotation.priority,
                propagate = annotation.propagate,
                value = annotation.value
            )

            // 注册到分发器
            bed.registerHandler(handler)
        }

        return bean
    }
}

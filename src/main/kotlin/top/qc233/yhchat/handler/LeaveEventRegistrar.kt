package top.qc233.yhchat.handler

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import top.qc233.yhchat.annotation.OnCommand
import top.qc233.yhchat.annotation.OnFollow
import top.qc233.yhchat.annotation.OnJoin
import top.qc233.yhchat.annotation.OnLeave
import top.qc233.yhchat.annotation.OnMessage
import top.qc233.yhchat.filter.*
import kotlin.reflect.full.declaredMemberFunctions
import kotlin.reflect.full.findAnnotation

@Component
class LeaveEventRegistrar(
    private val applicationContext: ApplicationContext
) : BeanPostProcessor {

    @Autowired
    lateinit var led: LeaveEventDispatcher

    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any? {
        val kClass = bean::class

        for (func in kClass.declaredMemberFunctions) {
            val annotation = func.findAnnotation<OnLeave>() ?: continue

            // 构建处理器实例
            val handler = LeaveEventHandler(
                bean = bean,
                method = func,
                priority = annotation.priority,
                propagate = annotation.propagate,
            )

            // 注册到分发器
            led.registerHandler(handler)
        }

        return bean
    }
}

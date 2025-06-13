package xyz.qc233.yhchat.handler

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import xyz.qc233.yhchat.event.LeaveEvent
import xyz.qc233.yhchat.sneder.MessageSender
import kotlin.reflect.KParameter

@Component
class LeaveEventDispatcher {
    private val handlerMap = mutableMapOf<String, MutableList<LeaveEventHandler>>()

    @Autowired
    lateinit var ms: MessageSender

    fun registerHandler(handler: LeaveEventHandler) {
        handlerMap.computeIfAbsent("LEAVE_EVENT") { mutableListOf() }
            .add(handler)

        // 按优先级排序（大优先）
        handlerMap["LEAVE_EVENT"]?.sortByDescending { it.priority }
    }

    fun dispatch(event: LeaveEvent) {
        val handlers = handlerMap["LEAVE_EVENT"] ?: return

        for (handler in handlers) {

            val params = handler.method.parameters // 包含所有参数（包括 instance）
            val argMap = mutableMapOf<KParameter, Any?>()

            params.forEach { param ->
                when (param.kind) {
                    KParameter.Kind.INSTANCE -> argMap[param] = handler.bean
                    KParameter.Kind.VALUE -> {
                        when (param.type.classifier) {
                            LeaveEvent::class -> argMap[param] = event
                            MessageSender::class -> argMap[param] = ms
                            else -> argMap[param] = null // 可选：跳过或报错
                        }
                    }
                    else -> {} // 可选：跳过 EXTENSION_RECEIVER 等
                }
            }

            handler.method.callBy(argMap)

            if (!handler.propagate) break
        }
    }
}
package xyz.qc233.yhchat.handler

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import xyz.qc233.yhchat.event.JoinEvent
import xyz.qc233.yhchat.sneder.MessageSender
import kotlin.reflect.KParameter

@Component
class JoinEventDispatcher {
    private val handlerMap = mutableMapOf<String, MutableList<JoinEventHandler>>()

    @Autowired
    lateinit var ms: MessageSender

    fun registerHandler(handler: JoinEventHandler) {
        handlerMap.computeIfAbsent("JOIN_EVENT") { mutableListOf() }
            .add(handler)

        // 按优先级排序（大优先）
        handlerMap["JOIN_EVENT"]?.sortByDescending { it.priority }
    }

    fun dispatch(event: JoinEvent) {
        val handlers = handlerMap["JOIN_EVENT"] ?: return

        for (handler in handlers) {

            if (handler.userId != "" && handler.userId != event.userId) continue
            if (handler.groupId != "" && handler.groupId != event.chatId) continue

            val params = handler.method.parameters // 包含所有参数（包括 instance）
            val argMap = mutableMapOf<KParameter, Any?>()

            params.forEach { param ->
                when (param.kind) {
                    KParameter.Kind.INSTANCE -> argMap[param] = handler.bean
                    KParameter.Kind.VALUE -> {
                        when (param.type.classifier) {
                            JoinEvent::class -> argMap[param] = event
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
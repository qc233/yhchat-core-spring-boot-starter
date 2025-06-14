package xyz.qc233.yhchat.handler

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import xyz.qc233.yhchat.event.ButtonEvent
import xyz.qc233.yhchat.filter.SenderTarget
import xyz.qc233.yhchat.sneder.MessageSender
import kotlin.reflect.KParameter

@Component
class ButtonEventDispatcher {
    private val handlerMap = mutableMapOf<String, MutableList<ButtonEventHandler>>()

    @Autowired
    lateinit var ms: MessageSender

    fun registerHandler(handler: ButtonEventHandler) {
        handlerMap.computeIfAbsent("BUTTON_EVENT") { mutableListOf() }
            .add(handler)

        // 按优先级排序（大优先）
        handlerMap["BUTTON_EVENT"]?.sortByDescending { it.priority }
    }

    fun dispatch(event: ButtonEvent) {
        val handlers = handlerMap["BUTTON_EVENT"] ?: return

        for (handler in handlers) {

            if (handler.userId!="" && handler.userId!=event.sender.senderId) continue
            if (handler.target != SenderTarget.ALL) {
                if (event.chat.chatType != handler.target) continue
                if (event.chat.chatType == "group" && handler.groupId != "" && event.chat.chatId != handler.groupId) continue
            }
            if (event.value != handler.value) continue
            val params = handler.method.parameters // 包含所有参数（包括 instance）
            val argMap = mutableMapOf<KParameter, Any?>()

            params.forEach { param ->
                when (param.kind) {
                    KParameter.Kind.INSTANCE -> argMap[param] = handler.bean
                    KParameter.Kind.VALUE -> {
                        when (param.type.classifier) {
                            ButtonEvent::class -> argMap[param] = event
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
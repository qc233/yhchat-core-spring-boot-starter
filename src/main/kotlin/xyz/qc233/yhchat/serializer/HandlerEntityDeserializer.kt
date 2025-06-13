package xyz.qc233.yhchat.serializer

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.config.AutowireCapableBeanFactory
import org.springframework.stereotype.Component
import xyz.qc233.yhchat.event.ButtonEvent
import xyz.qc233.yhchat.event.CommandEvent
import xyz.qc233.yhchat.event.EventHandler.HandlerEntity
import xyz.qc233.yhchat.event.EventHeader
import xyz.qc233.yhchat.event.FollowEvent
import xyz.qc233.yhchat.event.JoinEvent
import xyz.qc233.yhchat.event.LeaveEvent
import xyz.qc233.yhchat.event.MessageEvent
import xyz.qc233.yhchat.event.SettingEvent
import xyz.qc233.yhchat.event.UnfollowEvent


@Component
class HandlerEntityDeserializer(
    @Autowired private val beanFactory: AutowireCapableBeanFactory
) : JsonDeserializer<HandlerEntity>() {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): HandlerEntity {
        val node: JsonNode = p.codec.readTree(p)

        val version = node["version"].asText()
        val headerNode = node["header"]
        val eventNode = node["event"]

        val mapper = (p.codec as ObjectMapper)

        val header = mapper.treeToValue(headerNode, EventHeader::class.java)
        val eventType = header.eventType

        val eventTypeMap = mapOf(
            "message.receive.normal" to MessageEvent::class.java,
            "message.receive.instruction" to CommandEvent::class.java,
            "bot.followed" to FollowEvent::class.java,
            "bot.unfollowed" to UnfollowEvent::class.java,
            "group.join" to JoinEvent::class.java,
            "group.leave" to LeaveEvent::class.java,
            "button.report.inline" to ButtonEvent::class.java,
            "bot.setting" to SettingEvent::class.java
        )
        val eventClass = eventTypeMap[eventType] ?: error("不支持的类型")
        val event = mapper.treeToValue(eventNode, eventClass)

        beanFactory.autowireBean(event)

        val he = HandlerEntity()
        he.header = header
        he.event = event
        he.version = version
        return he

    }
}


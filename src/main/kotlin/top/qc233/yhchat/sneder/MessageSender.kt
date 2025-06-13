package top.qc233.yhchat.sneder

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.node.ObjectNode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import top.qc233.yhchat.message.EMessage
import top.qc233.yhchat.message.SMessage
import top.qc233.yhchat.message.TextContent
import top.qc233.yhchat.serializer.SMessageSerializer

@Component
class MessageSender {

    @Autowired
    lateinit var httpSender: HttpSender

    val module = SimpleModule().apply {
        addSerializer(SMessage::class.java, SMessageSerializer())
    }

    @Transient
    val mapper: ObjectMapper? = ObjectMapper().registerModule(module)

    fun json2eMessage(json: String): EMessage {
        val mapper = ObjectMapper()
        val root: JsonNode = mapper.readTree(json)

        val msgInfo = root.path("data").path("messageInfo")

        val msgId = msgInfo.path("msgId").asText()
        val recvId = msgInfo.path("recvId").asText()
        val recvType = msgInfo.path("recvType").asText()

        // 由于 JSON 中没有 contentType 和 content，这里设默认值
        val contentType = "text"
        val content = TextContent() // 假设你有一个默认构造的 Content 实例

        return EMessage(
            msgId = msgId,
            recvId = recvId,
            recvType = recvType,
            contentType = contentType,
            content = content,
            parentId = null
        )
    }

    fun send(sMessage: SMessage): EMessage {

        if (sMessage.contentType != "text" && sMessage.contentType != "markdown") {
            val filePath = sMessage.content.getContent()
            val key = httpSender.upload(sMessage.contentType.toString(), filePath.toString())
            sMessage.content.setContent(key)
        }

        val jsonString = mapper?.writeValueAsString(sMessage)
        return json2eMessage(httpSender.postJson(if (sMessage.recvId.size == 1) "send" else "batch_send", jsonString.toString()))
    }

    fun edit(eMessage: EMessage): EMessage {
        if (eMessage.contentType != "text" && eMessage.contentType != "markdown") {
            val filePath = eMessage.content.getContent()
            val key = httpSender.upload(eMessage.contentType.toString(), filePath.toString())
            eMessage.content.setContent(key)
        }
        val jsonString = mapper?.writeValueAsString(eMessage)

        return json2eMessage(httpSender.postJson("edit", jsonString.toString()))
    }

    fun recall(eMessage: EMessage): EMessage {
        val eMapper = ObjectMapper()
        val rootNode: ObjectNode = eMapper.createObjectNode()

        rootNode.put("msgId", eMessage.msgId)
        rootNode.put("chatId", eMessage.recvId)
        rootNode.put("chatType", eMessage.recvType)
        val json = eMapper.writeValueAsString(rootNode)

        return json2eMessage(httpSender.postJson("recall", json))
    }
}
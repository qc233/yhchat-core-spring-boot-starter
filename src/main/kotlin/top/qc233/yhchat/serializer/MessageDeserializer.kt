package top.qc233.yhchat.serializer

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import top.qc233.yhchat.message.*

class MessageDeserializer : JsonDeserializer<Message>() {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): Message {
        val node = p.codec.readTree<ObjectNode>(p)
        val mapper = (p.codec as ObjectMapper)

        val contentType = node["contentType"].asText()
        val contentNode = node["content"]

        val content: Content = when (contentType) {
            "text" -> mapper.treeToValue(contentNode, TextContent::class.java)
            "image" -> mapper.treeToValue(contentNode, ImageContent::class.java)
            "video" -> mapper.treeToValue(contentNode, VideoContent::class.java)
            "file" -> mapper.treeToValue(contentNode, FileContent::class.java)
            "markdown" -> mapper.treeToValue(contentNode, MarkdownContent::class.java)
            "html" -> mapper.treeToValue(contentNode, TextContent::class.java)
            "form" -> FormContent(mapper.writeValueAsString(contentNode))

            else -> throw IllegalArgumentException("Unsupported contentType: $contentType")
        }

        return Message(
            msgId = node["msgId"].asText(),
            contentType = contentType,
            content = content,
            parentId = node["parentId"].asText(),
            sendTime = node["sendTime"].asLong(),
            chatId = node["chatId"].asText(),
            chatType = node["chatType"].asText(),
            commandId = node["commandId"].asInt(),
            commandName = node["commandName"].asText(),
        )
    }
}


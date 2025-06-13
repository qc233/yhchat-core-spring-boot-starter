package xyz.qc233.yhchat.serializer

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import xyz.qc233.yhchat.message.SMessage

class SMessageSerializer : JsonSerializer<SMessage>() {
    override fun serialize(value: SMessage, gen: JsonGenerator, serializers: SerializerProvider) {
        gen.writeStartObject()

        if (value.recvId.size == 1){
            gen.writeStringField("recvId", value.recvId.first())
        }else{
            gen.writeFieldName("recvIds")
            gen.writeArray(value.recvId.toTypedArray(), 0, value.recvId.size)
        }

        gen.writeStringField("recvType", value.recvType)
        gen.writeStringField("parentId", value.parentId)
        gen.writeStringField("contentType", value.contentType)

        gen.writeObjectFieldStart("content")

        val contentInner = value.content.getContent()
        when (value.contentType) {
            "text" -> gen.writeStringField("text", contentInner)
            "image" -> gen.writeStringField("imageKey", contentInner)
            else -> {
                // fallback or ignore
            }
        }

        // 写入 buttons（数组）
        val buttons = value.content.buttons
        if (buttons.isNotEmpty()) {
            gen.writeArrayFieldStart("buttons")
            for (button in buttons) {
                gen.writeObject(button)  // 会触发 ButtonSerializer
            }
            gen.writeEndArray()
        }

        gen.writeEndObject() // content
        gen.writeEndObject() // whole object
    }
}


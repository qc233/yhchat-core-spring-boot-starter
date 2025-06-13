package xyz.qc233.yhchat.serializer

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import xyz.qc233.yhchat.message.Button

class ButtonSerializer : StdSerializer<Button>(Button::class.java) {
    override fun serialize(
        button: Button,
        gen: JsonGenerator,
        provider: SerializerProvider
    ) {
        gen.writeStartObject()
        gen.writeStringField("text", button.text)
        gen.writeNumberField("action", button.action)

        if (button.action == Button.ACTION_TO_URL) {
            gen.writeStringField("url", button.value ?: "")
        } else {
            gen.writeStringField("value", button.value ?: "")
        }

        gen.writeEndObject()
    }
}
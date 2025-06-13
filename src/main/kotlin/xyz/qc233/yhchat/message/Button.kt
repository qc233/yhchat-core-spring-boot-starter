package xyz.qc233.yhchat.message

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import xyz.qc233.yhchat.serializer.ButtonSerializer


@JsonSerialize(using = ButtonSerializer::class)
class Button(val text: String, val action: Int, val value: String? = "") {
    companion object{
        const val ACTION_TO_URL = 1
        const val ACTION_COPY = 2
        const val ACTION_SUBMIT = 3
    }
}
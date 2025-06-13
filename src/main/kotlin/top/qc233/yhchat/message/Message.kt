package top.qc233.yhchat.message

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import top.qc233.yhchat.serializer.MessageDeserializer

@JsonDeserialize(using = MessageDeserializer::class)
open class Message (
    var msgId: String?,
    var parentId: String?,
    var sendTime: Long?,
    var chatId: String?,
    var chatType: String?,
    var contentType: String?,
    var content: Content?,
    var commandId: Int?,
    var commandName: String?
){
    fun getEdit(){

    }
}
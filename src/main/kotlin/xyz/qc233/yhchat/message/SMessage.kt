package xyz.qc233.yhchat.message

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import xyz.qc233.yhchat.serializer.SMessageSerializer


@JsonSerialize(using = SMessageSerializer::class)
class SMessage (
    val recvId: List<String>,
    val recvType: String,
    val contentType: String,
    val content: Content,
    val parentId: String? = null,
){

}
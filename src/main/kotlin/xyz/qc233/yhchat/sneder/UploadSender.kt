package xyz.qc233.yhchat.sneder

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.configurationprocessor.json.JSONObject
import org.springframework.stereotype.Component

@Component
class UploadSender {
    @Autowired
    lateinit var httpSender: HttpSender

    fun send(type: String, filepath: String): String{
        val resp = httpSender.upload(filepath, type)
        val json = JSONObject(resp)

        val nodeName: String = when(type){
            "image" -> "imageKey"
            "video" -> "videoKey"
            "file" -> "fileKey"
            else -> {}
        }.toString()

        return if (json.getString("code") == "1") {
            json.getJSONObject("data").getString(nodeName)
        }else {
            throw RuntimeException("Upload Failed: ${json.getString("msg")}")
        }
    }

}
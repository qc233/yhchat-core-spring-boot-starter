package xyz.qc233.yhchat.sneder

import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.apache.tika.Tika
import org.apache.tika.mime.MimeTypes
import org.springframework.stereotype.Component
import xyz.qc233.yhchat.autoconfigure.YoibotProperties
import java.io.File
import java.net.URL
import java.util.concurrent.TimeUnit


@Component
class HttpSender(private val yoibotProperties: YoibotProperties) {

    private val client: OkHttpClient = OkHttpClient.Builder()
        .callTimeout(10, TimeUnit.SECONDS)
        .build()

    fun postJson(api: String, json: String): String {
        val url = "https://chat-go.jwzhd.com/open-apis/v1/bot/$api?token=${yoibotProperties.token}"

        val requestBody = json.toRequestBody("application/json".toMediaType())

        val request = Request.Builder()
            .header("Content-type", "application/json")
            .url(url)
            .post(requestBody)
            .build()

        return try {
            client.newCall(request).execute().use { response ->
                if (response.isSuccessful) {
                    response.body?.string() ?: "请求成功但无响应体"
                } else {
                    "请求失败，状态码: ${response.code}，响应: ${response.body?.string()}"
                }
            }
        } catch (e: Exception) {
            "请求异常：${e.message}"
        }
    }

    fun upload(type: String, source: String): String {
        val (defaultMime, keyName) = when (type) {
            "video" -> "video/mp4" to "videoKey"
            "file" -> "application/octet-stream" to "fileKey"
            "image" -> "image/png" to "imageKey"
            else -> "application/octet-stream" to ""
        }

        var extension = source.substringAfterLast('.', "")
        val requestBody: RequestBody = try {
            if (source.startsWith("http://") || source.startsWith("https://")) {
                handleRemoteFile(source).also {
                    extension = it.second
                }.first
            } else {
                val file = File(source)
                if (!file.exists()) return "文件不存在：$source"
                file.asRequestBody(defaultMime.toMediaType())
            }
        } catch (e: Exception) {
            return "读取文件异常：${e.message}"
        }

        val multipartBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart(type, "content$extension", requestBody)
            .build()

        val url = "https://chat-go.jwzhd.com/open-apis/v1/$type/upload?token=${yoibotProperties.token}"

        val request = Request.Builder()
            .header("Content-Type", "multipart/form-data")
            .url(url)
            .post(multipartBody)
            .build()

        return try {
            client.newCall(request).execute().use { response ->
                if (response.isSuccessful) {
                    val rootNode = ObjectMapper().readTree(response.body?.string())
                    rootNode.path("data").path(keyName).asText()
                } else {
                    "上传失败，状态码: ${response.code}，响应: ${response.body?.string()}"
                }
            }
        } catch (e: Exception) {
            "上传异常：${e.message}"
        }
    }

    /**
     * 下载网络资源并识别 MIME 类型及扩展名
     * @return Pair<RequestBody, 文件扩展名>
     */
    private fun handleRemoteFile(source: String): Pair<RequestBody, String> {
        val url = URL(source)
        url.openStream().use { inputStream ->
            val bytes = inputStream.readBytes()

            val tika = Tika()
            val mimeType = tika.detect(bytes)
            val ext = MimeTypes.getDefaultMimeTypes().forName(mimeType).extension

            val requestBody = bytes.toRequestBody(mimeType.toMediaType())
            return requestBody to ext
        }
    }

}

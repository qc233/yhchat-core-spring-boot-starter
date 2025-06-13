package xyz.qc233.yhchat.message


interface Content {
    var buttons: List<Button>
    fun getContent(): String
    fun setContent(content: String)
}
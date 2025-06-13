package top.qc233.yhchat.message

class FormContent(override var buttons: List<Button> = emptyList()) : Content {
    var formJson: String? = null
    override fun getContent(): String {
        return formJson ?: ""
    }

    override fun setContent(content: String) {
        this.formJson = content
    }
    constructor(json: String) : this() {
        this.formJson = json
    }
}
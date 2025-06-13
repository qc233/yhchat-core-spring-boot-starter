package xyz.qc233.yhchat.autoconfigure

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "yhchat")
open class YoibotProperties {
    var enabled: Boolean = true
    var message: String = "Default Message"
    var token: String = ""
}
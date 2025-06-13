package top.qc233.yhchat.autoconfigure

import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import top.qc233.yhchat.handler.MessageEventDispatcher

@AutoConfiguration
@EnableConfigurationProperties(YoibotProperties::class)
@ComponentScan("top.qc233.yhchat")
@ConditionalOnProperty(prefix = "yhchat", name = ["enabled"], havingValue = "true", matchIfMissing = true)
open class YoibotAutoConfiguration {

    @Bean
    open fun messageEventDispatcher(): MessageEventDispatcher {
        return MessageEventDispatcher()
    }
}
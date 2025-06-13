package xyz.qc233.yhchat.event

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import xyz.qc233.yhchat.handler.ButtonEventDispatcher
import xyz.qc233.yhchat.handler.CommandEventDispatcher
import xyz.qc233.yhchat.handler.FollowEventDispatcher
import xyz.qc233.yhchat.handler.JoinEventDispatcher
import xyz.qc233.yhchat.handler.LeaveEventDispatcher
import xyz.qc233.yhchat.handler.MenuEventDispatcher
import xyz.qc233.yhchat.handler.MessageEventDispatcher
import xyz.qc233.yhchat.handler.SettingEventDispatcher
import xyz.qc233.yhchat.handler.UnfollowEventDispatcher
import xyz.qc233.yhchat.serializer.HandlerEntityDeserializer

@RestController
class EventHandler {

    @Autowired
    lateinit var meDispatcher: MessageEventDispatcher
    @Autowired
    lateinit var ceDispatcher: CommandEventDispatcher
    @Autowired
    lateinit var feDispatcher: FollowEventDispatcher
    @Autowired
    lateinit var ufeDispatcher: UnfollowEventDispatcher
    @Autowired
    lateinit var jeDispatcher: JoinEventDispatcher
    @Autowired
    lateinit var leDispatcher: LeaveEventDispatcher
    @Autowired
    lateinit var beDispatcher: ButtonEventDispatcher
    @Autowired
    lateinit var menuEDispatcher: MenuEventDispatcher
    @Autowired
    lateinit var seDispatcher: SettingEventDispatcher

    @PostMapping("/")
    fun handle(@RequestBody he: HandlerEntity): Any {
        when(he.header?.eventType){
            "message.receive.normal" -> {meDispatcher.dispatch(he.event as MessageEvent)}
            "message.receive.instruction" -> {ceDispatcher.dispatch(he.event as CommandEvent)}
            "bot.followed" -> {feDispatcher.dispatch(he.event as FollowEvent)}
            "bot.unfollowed" -> {ufeDispatcher.dispatch(he.event as UnfollowEvent)}
            "group.join" -> {jeDispatcher.dispatch(he.event as JoinEvent)}
            "group.leave" -> {leDispatcher.dispatch(he.event as LeaveEvent)}
            "button.report.inline" -> {beDispatcher.dispatch(he.event as ButtonEvent)}
            "bot.shortcut.menu" -> {menuEDispatcher.dispatch(he.event as MenuEvent)}
            "bot.setting" -> {seDispatcher.dispatch(he.event as SettingEvent)}
        }
        return "0"
    }

    @JsonDeserialize(using = HandlerEntityDeserializer::class)
    class HandlerEntity {
        var version: String? = null
        var header: EventHeader? = null
        var event: Event? = null
    }
}


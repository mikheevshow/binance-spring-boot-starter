package io.mikheevshow.event

import org.apache.logging.log4j.kotlin.logger
import org.springframework.stereotype.Component

@Component
class EventHandlerImpl : EventHandler {

    private val logger = logger()

    override fun handleEvent(rawData: CharSequence) {
        EventType.events.firstOrNull { rawData.contains(it) }?.let { event ->
        } ?: logger.warn { "Unexpected event $rawData" }
    }
}
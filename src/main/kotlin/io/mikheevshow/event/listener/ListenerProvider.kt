package io.mikheevshow.event.listener

import io.mikheevshow.event.EventType

interface ListenerProvider {
    fun get(eventType: EventType): List<BinanceListener<*>>
}